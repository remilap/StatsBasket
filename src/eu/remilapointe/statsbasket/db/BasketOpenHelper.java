/**
 * 
 */
package eu.remilapointe.statsbasket.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.remilapointe.statsbasket.sport.Club;
import eu.remilapointe.statsbasket.sport.Identifiable;
import eu.remilapointe.statsbasket.sport.Match;
import eu.remilapointe.statsbasket.sport.Player;
import eu.remilapointe.statsbasket.sport.Team;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class BasketOpenHelper extends SQLiteOpenHelper {

	/** database name */
	private static final String DB_NAME = "basket";
	/** current database version */
	private static final int DB_VERSION = 1;
	/** database path */
	private static String DB_PATH = null;
	private static final String TAG = BasketOpenHelper.class.getSimpleName();
	private static final String DB_VERSION_TAG = "DB_VERSION";
	private static final String DB_TABLE_TAG = "DB_TABLE";
	private static final String DB_DROP_TABLE = "DROP TABLE IF EXISTS ";

	private final Context _myContext;
	private static BasketOpenHelper _instance = null;

	/**
	 * BasketOpenHelper
	 * @param context
	 */
	public BasketOpenHelper(Context context) {
		// the 3rd parameter is null for using the default implementation of cursors
		super(context, DB_NAME, null, DB_VERSION);
		this._myContext = context;
		Log.d(TAG, "context.getFilesDir().getPath() = " + context.getFilesDir().getParent());
		DB_PATH = context.getFilesDir().getParent() + "/databases";
		Log.d(TAG, "new DB_PATH = " + DB_PATH);
		_instance = this;
	}

	/**
	 * getInstance
	 * @return
	 * returns BasketOpenHelper
	 */
	public static BasketOpenHelper getInstance() {
		return _instance;
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		// tables creation
		db.execSQL(ClubDB.getInstance().getCreateTable());
		db.execSQL(TeamDB.getInstance().getCreateTable());
		db.execSQL(PlayerDB.getInstance().getCreateTable());
		db.execSQL(MatchDB.getInstance().getCreateTable());
//		this._myContext.
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade with oldVersion=" + oldVersion + " and newVersion=" + newVersion);
		// table update:
		// from version 1 to version 2, add Team table and column Gender of Category is now integer
//		if (oldVersion < 2) {
//			db.execSQL(TeamDB.getInstance().getCreateTable());
//			String sqlCmde = DB_DROP_TABLE + CategoryDB.getCategoryTableName() + ";";
//			db.execSQL(sqlCmde);
//			db.execSQL(CategoryDB.getInstance().getCreateTable());
//		}
//		// from version 2 to version 3, add Player table
//		if (oldVersion < 3) {
//			db.execSQL(PlayerDB.getInstance().getCreateTable());
//		}
//		// from version 3 to version 4, add Bib_number column in Player table
//		if (oldVersion < 4) {
//			db.execSQL(PlayerDB.getInstance().getAddTheColumn(4));
//		}
//		// from version 4 to version 5, add Specific column in Player table
//		if (oldVersion < 5) {
//			db.execSQL(PlayerDB.getInstance().getAddTheColumn(5));
//		}
//		// from version 5 to version 6, add Sport_id column in Team table
//		if (oldVersion < 6) {
//			db.execSQL(TeamDB.getInstance().getAddTheColumn(4));
//		}
//		// from version 6 to version 7, add match table
//		if (oldVersion < 7) {
//			db.execSQL(MatchDB.getInstance().getCreateTable());
//		}
	}

	/**
	 * getTheLargerIndex
	 * @param tableName
	 * @return
	 * returns long
	 */
	public long getTheLargerIndex(String tableName) {
		SQLiteDatabase db = this.getReadableDatabase();
		long max = 0;
		String query = "SELECT MAX(" + Identifiable.KEY_ROWID + ") FROM " + tableName;
		try {
			Cursor c = db.rawQuery(query, null);
			if (c.moveToFirst()) {
				max = c.getLong(0);
			}
		} catch (Exception e) {
			Log.e(TAG, "unable to get the larger index from the " + tableName + " table\n" + e.getMessage());
			max = 0;
		}
		return max;
	}

	/**
	 * getContext
	 * @return
	 * returns Context
	 */
	public Context getContext() {
		return _myContext;
	}

	/**
	 * getDbName
	 * @return
	 * returns String
	 */
	public static String getDbName() {
		return DB_NAME;
	}

	/**
	 * getDbPath
	 * @return
	 * returns String
	 */
	public static String getDbPath() {
		return DB_PATH + File.separator + DB_NAME;
	}

	/**
	 * getDbVersion
	 * @return
	 * returns int
	 */
	public static int getDbVersion() {
		return DB_VERSION;
	}

	/**
	 * saveDatabase
	 * @param pathname
	 * @return
	 * returns boolean
	 */
	public static boolean saveDatabase(String pathname) {
		Log.d(TAG, "saveDatabase with file name: " + pathname);
		String pathWithVersion = pathname + DB_VERSION;
		Log.d(TAG, "saveDatabase with version: " + pathname);
		return copyFile(getDbPath(), pathWithVersion);
	}

	/**
	 * restDatabase
	 * @param pathname
	 * @return
	 * returns boolean
	 */
	public static boolean restDatabase(String pathname) {
		Log.d(TAG, "restDatabase with pathname=" + pathname);
		File fsav = new File(pathname);
		String [] saved_files = fsav.getParentFile().list();
		if (saved_files == null || saved_files.length == 0) {
			return false;
		}
		int vmax = 0;
		String higherFn = pathname;
		for (int i = 0; i < saved_files.length; i++) {
			String fn = saved_files[i];
			Log.d(TAG, "file in the directory: " + fn);
			int dot = fn.lastIndexOf(".");
			if (dot >= 0) {
				String ext = fn.substring(dot + 1);
				if (ext.startsWith("sav") && ext.length() > 3) {
					String enfOfExt = ext.substring(3);
					Log.d(TAG, "file with sav... extension: " + enfOfExt);
					int index = 0;
					try {
						index = Integer.parseInt(enfOfExt);
						if (index > vmax) {
							Log.d(TAG, "The new extension is more recent: " + index + " (was " + vmax + ")");
							vmax = index;
							higherFn = pathname + vmax;
						}
					} catch (NumberFormatException e) {
						// nothing
					}
				}
			}
		}
		boolean res = copyFile(higherFn, getDbPath());
//		if (res && (vmax > 0) && (vmax < DB_VERSION)) {
//			BasketOpenHelper.getInstance().onUpgrade(BasketOpenHelper.getInstance().getReadableDatabase(), vmax, DB_VERSION);
//		}
		return res;
	}

	/**
	 * copyFile
	 * @param inputFN
	 * @param outputFN
	 * @return
	 * returns boolean
	 */
	private static boolean copyFile(String inputFN, String outputFN) {
		Log.d(TAG, "File copy from " + inputFN + " to " + outputFN);
		boolean res = false;
		InputStream myInput = null;
		OutputStream myOutput = null;
		try {
			Log.d(TAG, "Opening " + inputFN + " file for reading");
			myInput = new FileInputStream(inputFN);
			Log.d(TAG, "Opening " + outputFN + " file for writing");
			myOutput = new FileOutputStream(outputFN);
			// transfer bytes from the input to the output
			byte[] buffer = new byte[1024];
			int length;
			int n = 0;
			while ((length = myInput.read(buffer)) > 0) {
				n++;
				Log.d(TAG, "Transfering block number " + n);
				myOutput.write(buffer, 0, length);
			}
			Log.d(TAG, "End of file copy");
			res = true;
		} catch (FileNotFoundException e) {
			Log.e(TAG, "File Not Found Exception during file opening "+e.getLocalizedMessage());
			res = false;
		} catch (IOException e) {
			Log.e(TAG, "IO Exception during reading/writing file "+e.getLocalizedMessage());
			res = false;
		} finally {
			// close the streams
			try {
				if (myInput != null) {
					myInput.close();
				}
				if (myOutput != null) {
					myOutput.flush();
					myOutput.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "IO Exception during closing file "+e.getLocalizedMessage());
				res = false;
			}
		}
		return res;
	}

	/**
	 * exportDatabase
	 * @param pathname
	 * @return
	 * returns boolean
	 */
	public boolean exportDatabase(String pathname) {
		boolean res = false;
		BufferedWriter myOutput = null;
		try {
			myOutput = new BufferedWriter(new FileWriter(pathname, false));
			myOutput.append(DB_VERSION_TAG + "=" + DB_VERSION);
			myOutput.newLine();
			myOutput.append(DB_TABLE_TAG + "=" + ClubDB.getClubTableName());
			myOutput.newLine();
			Club[] clubs = ClubDB.getInstance().getAllElementsFromDatabase();
			for (int i = 0; i < clubs.length; i++) {
				myOutput.append(clubs[i].toBackup());
				myOutput.newLine();
			}
			myOutput.append(DB_TABLE_TAG + "=" + TeamDB.getTeamTableName());
			myOutput.newLine();
			Team[] teams = TeamDB.getInstance().getAllElementsFromDatabase();
			for (int i = 0; i < teams.length; i++) {
				myOutput.append(teams[i].toBackup());
				myOutput.newLine();
			}
			myOutput.append(DB_TABLE_TAG + "=" + PlayerDB.getPlayerTableName());
			myOutput.newLine();
			Player[] players = PlayerDB.getInstance().getAllElementsFromDatabase();
			for (int i = 0; i < players.length; i++) {
				myOutput.append(players[i].toBackup());
				myOutput.newLine();
			}
			res = true;
		} catch (FileNotFoundException e) {
			Log.e(TAG, "File Not Found Exception during file opening "+e.getLocalizedMessage());
		} catch (IOException e) {
			Log.e(TAG, "IO Exception during reading/writing file "+e.getLocalizedMessage());
		} finally {
			// close the streams
			try {
				myOutput.flush();
				myOutput.close();
			} catch (IOException e) {
				Log.e(TAG, "IO Exception during closing file "+e.getLocalizedMessage());
			}
		}
		return res;
	}

	/**
	 * razDatabase
	 * @param db
	 * @return
	 * returns boolean
	 */
	public boolean razDatabase() {
		boolean res = false;
		SQLiteDatabase db = getReadableDatabase();
		try {
			// tables deletion
			db.execSQL(DB_DROP_TABLE + ClubDB.getInstance().getTableName() + ";");
			db.execSQL(DB_DROP_TABLE + TeamDB.getInstance().getTableName() + ";");
			db.execSQL(DB_DROP_TABLE + PlayerDB.getInstance().getTableName() + ";");
			db.execSQL(DB_DROP_TABLE + MatchDB.getInstance().getTableName() + ";");
			// tables creation
			db.execSQL(ClubDB.getInstance().getCreateTable());
			db.execSQL(TeamDB.getInstance().getCreateTable());
			db.execSQL(PlayerDB.getInstance().getCreateTable());
			db.execSQL(MatchDB.getInstance().getCreateTable());
			res = true;
		} catch (SQLException e) {
			Log.e(TAG, "Error during RAZ Database\n" + e.getLocalizedMessage());
			res = false;
		}
		return res;
	}

	/**
	 * importDatabase
	 * @param pathname
	 * @return
	 * returns boolean
	 */
	public boolean importDatabase(String pathname) {
		boolean res = razDatabase();
		if (! res) {
			return res;
		}
		BufferedReader myInput = null;
		try {
			myInput = new BufferedReader(new FileReader(pathname));
			String line = myInput.readLine();
			int db_vers = 0;
			String tbname = null;
			int step = 1;
			while (line != null) {
				Log.d(TAG, "line: " + line);
				switch (step) {
				case 1:
					if (line.startsWith(DB_VERSION_TAG + "=")) {
						String dbv = line.substring(DB_VERSION_TAG.length() + 1);
						db_vers = Integer.parseInt(dbv);
						Log.d(TAG, "Import Database Version: " + db_vers);
						step = 2;
					}
					break;
				case 2:
					if (line.startsWith(DB_TABLE_TAG + "=")) {
						tbname = line.substring(DB_TABLE_TAG.length() + 1);
						Log.d(TAG, "Start to read " + tbname + " table content");
						continue;
					}
					if (tbname.equals(ClubDB.getInstance().getTableName())) {
						Club club = Club.fromBackup(line, db_vers);
						ClubDB.getInstance().insertInDatabase(club);
					}
					if (tbname.equals(TeamDB.getInstance().getTableName())) {
						Team team = Team.fromBackup(line, db_vers);
						TeamDB.getInstance().insertInDatabase(team);
					}
					if (tbname.equals(PlayerDB.getInstance().getTableName())) {
						Player player = Player.fromBackup(line, db_vers);
						PlayerDB.getInstance().insertInDatabase(player);
					}
					if (tbname.equals(ClubDB.getInstance().getTableName())) {
						Match match = Match.fromBackup(line, db_vers);
						MatchDB.getInstance().insertInDatabase(match);
					}
					break;

				default:
					break;
				}
			}
			res = true;
		} catch (FileNotFoundException e) {
			Log.e(TAG, "File Not Found Exception during file opening "+e.getLocalizedMessage());
		} catch (IOException e) {
			Log.e(TAG, "IO Exception during reading/writing file "+e.getLocalizedMessage());
		} finally {
			try {
				myInput.close();
			} catch (IOException e) {
				Log.e(TAG, "IO Exception during closing file "+e.getLocalizedMessage());
			}
		}
		return res;
	}

}
