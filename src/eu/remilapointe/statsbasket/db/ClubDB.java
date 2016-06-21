/**
 * 
 */
package eu.remilapointe.statsbasket.db;

import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import eu.remilapointe.statsbasket.sport.Club;

/**
 * @author lapoint1
 *
 */
public class ClubDB extends BasketDBbase implements DBable {

	private static final String TAG = ClubDB.class.getSimpleName();

	private static final String DB_CLUB_TABLE = "club";
	public static final String CONTENT_URI = "content://" + BasketContentProvider.AUTHORITY + "/" + DB_CLUB_TABLE;

	private static ClubDB _instance = null;
	private static Uri _clubUri = null;

	static {
		_clubUri = Uri.parse(CONTENT_URI);
	}

	/**
	 * ClubDB
	 */
	private ClubDB() {
		super();
	}

	/**
	 * getInstance
	 * @return
	 * returns ClubDB
	 */
	public static ClubDB getInstance() {
		if (_instance == null) {
			_instance = new ClubDB();
		}
		return _instance;
	}

	/**
	 * getClubTableName
	 * @return
	 * returns String
	 */
	public static String getClubTableName() {
		return DB_CLUB_TABLE;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.DBable#getUri()
	 */
	@Override
	public Uri getUri() {
		return _clubUri;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getTableName()
	 */
	@Override
	public String getTableName() {
		return DB_CLUB_TABLE;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getListFields()
	 */
	@Override
	public String[] getColumnsList() {
		return new String[] {
				Club.KEY_ROWID,
				Club.KEY_CLUB_NAME,
				Club.KEY_CLUB_NUMBER,
				Club.KEY_CLUB_TOWN};
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getSQLFields()
	 */
	@Override
	public Vector<String[]> getSQLColumns() {
		Vector<String[]> fields = new Vector<String[]>();
		fields.add(new String[] {Club.KEY_CLUB_NAME, Club.KEY_STRING_FIELD});
		fields.add(new String[] {Club.KEY_CLUB_NUMBER, Club.KEY_STRING_FIELD});
		fields.add(new String[] {Club.KEY_CLUB_TOWN, Club.KEY_STRING_FIELD});
		return fields;
	}

	/**
	 * getFromDatabase
	 * @param c
	 * @return
	 * returns Club
	 */
	public Club getFromDatabase(Cursor c) {
		long id = 0;
		String name = null;
		String number = null;
		String town = null;
		if (c != null) {
			for (int i = 0 ; i < c.getColumnCount() ; i++) {
				if (Club.KEY_ROWID.equals(c.getColumnName(i))) {
					id = c.getLong(i);
				} else if (Club.KEY_CLUB_NAME.equals(c.getColumnName(i))) {
					name = c.getString(i);
				} else if (Club.KEY_CLUB_NUMBER.equals(c.getColumnName(i))) {
					number = c.getString(i);
				} else if (Club.KEY_CLUB_TOWN.equals(c.getColumnName(i))) {
					town = c.getString(i);
				}
			}
		}
		Club club = new Club(name, number, town);
		club.setId(id);
		return club;
	}

	/**
	 * getFromDatabase
	 * @param cr
	 * @param id
	 * @return
	 * returns Club
	 */
	public Club getFromDatabase(long id) {
		Log.d(TAG, "getFromDatabase for " + this.getTableName() + " table and index " + id);
		Cursor c = null;
		Club club = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return club;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					Club.KEY_ROWID+" = ? ",
					new String[]{String.valueOf(id)},
					null);
			if (c.moveToFirst()) {
				club = getFromDatabase(c);
			}
		} catch (Exception e) {
			String msg = "query exception during getFromDatabase() on " + this.getTableName();
			if (e != null && e.getLocalizedMessage() != null) {
				msg += "\n" + e.getLocalizedMessage();
			}
			Log.d(TAG, msg);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return club;
	}

	/**
	 * getAllElementsFromDatabase
	 * @return
	 * returns Club[]
	 */
	public Club[] getAllElementsFromDatabase() {
		Log.d(TAG, "getAllElementsFromDatabase for " + this.getTableName() + " table");
		Club[] clubs = null;
		Cursor c = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return clubs;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					"",
					null,
					null);
			Log.d(TAG, "getAllElementsFromDatabase after query with c is " + (c!=null?"not ":"") + "null");
			clubs = new Club[c.getCount()];
			c.moveToFirst();
			int i = 0;
			while (! c.isAfterLast()) {
				clubs[i] = getFromDatabase(c);
				c.moveToNext();
				i++;
			}
		} catch (Exception e) {
			String msg = "query exception during getAllElementsFromDatabase() on " + this.getTableName() + " table";
			if (e != null && e.getLocalizedMessage() != null) {
				msg += "\n" + e.getLocalizedMessage();
			}
			Log.d(TAG, msg);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return clubs;
	}

	/**
	 * insertInDatabase
	 * @param club
	 * @return
	 * returns boolean
	 */
	public boolean insertInDatabase(Club club) {
		return putToDatabase(true, club);
	}

	/**
	 * updateInDatabase
	 * @param club
	 * @return
	 * returns boolean
	 */
	public boolean updateInDatabase(Club club) {
		return putToDatabase(false, club);
	}

	/**
	 * putToDatabase
	 * @param newRecord
	 * @param club
	 * @return
	 * returns boolean
	 */
	public boolean putToDatabase(boolean newRecord, Club club) {
		boolean res = false;
		ContentValues values = new ContentValues();
		values.put(Club.KEY_ROWID, Long.valueOf(club.getId()));
		values.put(Club.KEY_CLUB_NAME, club.getName());
		values.put(Club.KEY_CLUB_NUMBER, club.getNumber());
		values.put(Club.KEY_CLUB_TOWN, club.getTown());
		if (newRecord) {
			// insert a new row
			Uri res_insert = this.getContentResolver().insert(this.getUri(), values);
			res = (res_insert != null);
		} else {
			// update the row
			String queryString = Club.KEY_ROWID + "=" + Uri.encode(String.valueOf(club.getId()));
			Uri theUri = Uri.parse(ClubDB.CONTENT_URI + "?" + queryString);
//			this.getContentResolver().update(theUri, values, null, null);
			int res_update = this.getContentResolver().update(theUri, values, Club.KEY_ROWID+" = ?", new String[] {String.valueOf(club.getId())});
			res = (res_update > 0);
		}
		return res;
	}

}
