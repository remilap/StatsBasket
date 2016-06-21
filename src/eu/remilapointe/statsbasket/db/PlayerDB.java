/**
 * 
 */
package eu.remilapointe.statsbasket.db;

import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import eu.remilapointe.statsbasket.sport.Player;

/**
 * @author lapoint1
 *
 */
public class PlayerDB extends BasketDBbase implements DBable {

	private static final String TAG = PlayerDB.class.getSimpleName();

	private static final String DB_PLAYER_TABLE = "player";
	public static final String CONTENT_URI = "content://" + BasketContentProvider.AUTHORITY + "/" + DB_PLAYER_TABLE;

	private static PlayerDB _instance = null;
	private static Uri _playerUri = null;

	static {
		_playerUri = Uri.parse("content://" + BasketContentProvider.AUTHORITY + "/" + DB_PLAYER_TABLE);
	}

	/**
	 * PlayerDB
	 */
	private PlayerDB() {
		super();
	}

	/**
	 * getInstance
	 * @return
	 * returns PlayerDB
	 */
	public static PlayerDB getInstance() {
		if (_instance == null) {
			_instance = new PlayerDB();
		}
		return _instance;
	}

	/**
	 * getPlayerTableName
	 * @return
	 * returns String
	 */
	public static String getPlayerTableName() {
		return DB_PLAYER_TABLE;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.DBable#getUri()
	 */
	@Override
	public Uri getUri() {
		return _playerUri;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getTableName()
	 */
	@Override
	public String getTableName() {
		return DB_PLAYER_TABLE;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getListFields()
	 */
	@Override
	public String[] getColumnsList() {
		return new String[] {
				Player.KEY_ROWID,
				Player.KEY_PLAYER_NAME,
				Player.KEY_PLAYER_FIRSTNAME,
				Player.KEY_PLAYER_LICENSE,
				Player.KEY_PLAYER_BIB_NUMBER,
				Player.KEY_PLAYER_SPECIFIC};
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getSQLFields()
	 */
	@Override
	public Vector<String[]> getSQLColumns() {
		Vector<String[]> fields = new Vector<String[]>();
		fields.add(new String[] {Player.KEY_PLAYER_NAME, Player.KEY_STRING_FIELD});
		fields.add(new String[] {Player.KEY_PLAYER_FIRSTNAME, Player.KEY_STRING_FIELD});
		fields.add(new String[] {Player.KEY_PLAYER_LICENSE, Player.KEY_STRING_FIELD});
		fields.add(new String[] {Player.KEY_PLAYER_BIB_NUMBER, Player.KEY_STRING_FIELD});
		fields.add(new String[] {Player.KEY_PLAYER_SPECIFIC, Player.KEY_STRING_FIELD});
		fields.add(new String[] {Player.KEY_PLAYER_CLUB_ID, Player.KEY_STRING_FIELD});
		return fields;
	}

	/**
	 * getFromDatabase
	 * @param c
	 * @return
	 * returns Player
	 */
	public Player getFromDatabase(Cursor c) {
		long id = 0;
		String name = null;
		String first_name = null;
		String license = null;
		int bib_number = 0;
		String specific = null;
		int club_id = 0;
		if (c != null) {
			for (int i = 0 ; i < c.getColumnCount() ; i++) {
				if (Player.KEY_ROWID.equals(c.getColumnName(i))) {
					id = c.getLong(i);
				} else if (Player.KEY_PLAYER_NAME.equals(c.getColumnName(i))) {
					name = c.getString(i);
				} else if (Player.KEY_PLAYER_FIRSTNAME.equals(c.getColumnName(i))) {
					first_name = c.getString(i);
				} else if (Player.KEY_PLAYER_LICENSE.equals(c.getColumnName(i))) {
					license = c.getString(i);
				} else if (Player.KEY_PLAYER_BIB_NUMBER.equals(c.getColumnName(i))) {
					bib_number = Integer.parseInt(c.getString(i));
				} else if (Player.KEY_PLAYER_SPECIFIC.equals(c.getColumnName(i))) {
					specific = c.getString(i);
				} else if (Player.KEY_PLAYER_CLUB_ID.equals(c.getColumnName(i))) {
					club_id = Integer.parseInt(c.getString(i));
				}
			}
		}
		Player player = new Player(name, first_name, license, bib_number, specific, club_id);
		player.setId(id);
		return player;
	}

	/**
	 * getFromDatabase
	 * @param cr
	 * @param id
	 * @return
	 * returns Player
	 */
	public Player getFromDatabase(long id) {
		Log.d(TAG, "getFromDatabase for " + this.getTableName() + " table and index " + id);
		Cursor c = null;
		Player player = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return player;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					Player.KEY_ROWID+" = ? ",
					new String[]{String.valueOf(id)},
					null);
			if (c.moveToFirst()) {
				player = getFromDatabase(c);
			}
		} catch (Exception e) {
			String msg = "query exception during getPlayerFromDatabase() on " + this.getTableName();
			if (e != null && e.getLocalizedMessage() != null) {
				msg += "\n" + e.getLocalizedMessage();
			}
			Log.d(TAG, msg);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return player;
	}

	/**
	 * getAllElementsFromDatabase
	 * @return
	 * returns Player[]
	 */
	public Player[] getAllElementsFromDatabase() {
		Log.d(TAG, "getAllElementsFromDatabase for " + this.getTableName() + " table");
		Player[] players = null;
		Cursor c = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return players;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					"",
					null,
					null);
			Log.d(TAG, "getAllElementsFromDatabase after query with c is " + (c!=null?"not ":"") + "null");
			players = new Player[c.getCount()];
			c.moveToFirst();
			int i = 0;
			while (! c.isAfterLast()) {
				players[i] = getFromDatabase(c);
				c.moveToNext();
				i++;
			}
		} catch (Exception e) {
			String msg = "query exception during getAllPlayersFromDatabase() on " + this.getTableName() + " table";
			if (e != null && e.getLocalizedMessage() != null) {
				msg += "\n" + e.getLocalizedMessage();
			}
			Log.d(TAG, msg);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return players;
	}

	/**
	 * insertInDatabase
	 * @param player
	 * @return
	 * returns boolean
	 */
	public boolean insertInDatabase(Player player) {
		return putInDatabase(true, player);
	}

	/**
	 * updateInDatabase
	 * @param player
	 * @return
	 * returns boolean
	 */
	public boolean updateInDatabase(Player player) {
		return putInDatabase(false, player);
	}

	/**
	 * putInDatabase
	 * @param newRecord
	 * @param player
	 * @return
	 * returns boolean
	 */
	public boolean putInDatabase(boolean newRecord, Player player) {
		boolean res = false;
		ContentValues values = new ContentValues();
		values.put(Player.KEY_ROWID, Long.valueOf(player.getId()));
		values.put(Player.KEY_PLAYER_NAME, player.getName());
		values.put(Player.KEY_PLAYER_FIRSTNAME, player.getFirstname());
		values.put(Player.KEY_PLAYER_BIB_NUMBER, player.getStringBibNumber());
		values.put(Player.KEY_PLAYER_LICENSE, player.getLicense());
		values.put(Player.KEY_PLAYER_SPECIFIC, player.getSpecific());
		values.put(Player.KEY_PLAYER_CLUB_ID, player.getClubStringID());
		if (newRecord) {
			// insert a new row
			Uri res_insert = this.getContentResolver().insert(this.getUri(), values);
			res = (res_insert != null);
		} else {
			// update the row
			String queryString = Player.KEY_ROWID + "=" + Uri.encode(String.valueOf(player.getId()));
			Uri theUri = Uri.parse(PlayerDB.CONTENT_URI + "?" + queryString);
//			this.getContentResolver().update(theUri, values, null, null);
			int res_update = this.getContentResolver().update(theUri, values, Player.KEY_ROWID+" = ?", new String[] {String.valueOf(player.getId())});
			res = (res_update > 0);
		}
		return res;
	}

}
