/**
 * 
 */
package eu.remilapointe.statsbasket.db;

import java.util.Vector;

import eu.remilapointe.statsbasket.sport.Team;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class TeamDB extends BasketDBbase implements DBable {

	private static final String TAG = TeamDB.class.getSimpleName();

	private static final String DB_TEAM_TABLE = "team";
	public static final String CONTENT_URI = "content://" + BasketContentProvider.AUTHORITY + "/" + DB_TEAM_TABLE;

	private static TeamDB _instance = null;
	private static Uri _teamUri = null;

	static {
		_teamUri = Uri.parse(CONTENT_URI);
	}

	/**
	 * TeamDB
	 */
	private TeamDB() {
		super();
	}

	/**
	 * getInstance
	 * @return
	 * returns TeamDB
	 */
	public static TeamDB getInstance() {
		if (_instance == null) {
			_instance = new TeamDB();
		}
		return _instance;
	}

	/**
	 * getTeamTableName
	 * @return
	 * returns String
	 */
	public static String getTeamTableName() {
		return DB_TEAM_TABLE;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.DBable#getUri()
	 */
	@Override
	public Uri getUri() {
		return _teamUri;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getTableName()
	 */
	@Override
	public String getTableName() {
		return DB_TEAM_TABLE;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getListFields()
	 */
	@Override
	public String[] getColumnsList() {
		return new String[] {Team.KEY_ROWID, Team.KEY_TEAM_NAME, Team.KEY_TEAM_CLUB_ID, Team.KEY_TEAM_PLAYERS_IDS};
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getSQLFields()
	 */
	@Override
	public Vector<String[]> getSQLColumns() {
		Vector<String[]> fields = new Vector<String[]>();
		fields.add(new String[] {Team.KEY_TEAM_NAME, Team.KEY_STRING_FIELD});
		fields.add(new String[] {Team.KEY_TEAM_CLUB_ID, Team.KEY_INT_FIELD});
		fields.add(new String[] {Team.KEY_TEAM_PLAYERS_IDS, Team.KEY_STRING_FIELD});
		return fields;
	}

	/**
	 * getFromDatabase
	 * @param c
	 * @return
	 * returns Team
	 */
	public Team getFromDatabase(final Cursor c) {
		long id = 0;
		String name = null;
		long club_id = 0;
		String players = null;
		if (c != null) {
			Log.d(TAG, "current cursor has " + c.getColumnCount() + " columns");
			for (int i = 0 ; i < c.getColumnCount() ; i++) {
				if (Team.KEY_ROWID.equals(c.getColumnName(i))) {
					id = c.getLong(i);
					Log.d(TAG, "column no " + i + ": " + c.getColumnName(i) + ", val=" + id);
				} else if (Team.KEY_TEAM_NAME.equals(c.getColumnName(i))) {
					name = c.getString(i);
					Log.d(TAG, "column no " + i + ": " + c.getColumnName(i) + ", val=" + name);
				} else if (Team.KEY_TEAM_CLUB_ID.equals(c.getColumnName(i))) {
					club_id = c.getLong(i);
					Log.d(TAG, "column no " + i + ": " + c.getColumnName(i) + ", val=" + club_id);
				} else if (Team.KEY_TEAM_PLAYERS_IDS.equals(c.getColumnName(i))) {
					players = c.getString(i);
					Log.d(TAG, "column no " + i + ": " + c.getColumnName(i) + ", val=" + players);
				}
			}
		} else {
			Log.e(TAG, "current cursor is null !!!");
		}
		Team team = new Team(name, club_id, players);
		team.setId(id);
		return team;
	}

	/**
	 * getFromDatabase
	 * @param cr
	 * @param id
	 * @return
	 * returns Team
	 */
	public Team getFromDatabase(long id) {
		Log.d(TAG, "getFromDatabase for " + this.getTableName() + " table and index " + id);
		Cursor c = null;
		Team team = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return team;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					Team.KEY_ROWID+" = ? ",
					new String[]{String.valueOf(id)},
					null);
			if (c == null) {
				Log.d(TAG, "no team with index " + id);
			} else {
				Log.d(TAG, "team with index " + id + " has " + c.getColumnCount() + " columns");
			}
			if (c.moveToFirst()) {
				team = getFromDatabase(c);
			}
		} catch (Exception e) {
			String msg = "query exception during getTeamFromDatabase() on " + this.getTableName();
			if (e != null && e.getLocalizedMessage() != null) {
				msg += "\n" + e.getLocalizedMessage();
			}
			Log.e(TAG, msg);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return team;
	}

	/**
	 * getAllElementsFromDatabase
	 * @return
	 * returns Team[]
	 */
	public Team[] getAllElementsFromDatabase() {
		Log.d(TAG, "getAllElementsFromDatabase for " + this.getTableName() + " table");
		Team[] teams = null;
		Cursor c = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return teams;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					"",
					null,
					null);
			Log.d(TAG, "getAllElementsFromDatabase after query with c is " + (c!=null?"not ":"") + "null");
			teams = new Team[c.getCount()];
			c.moveToFirst();
			int i = 0;
			while (! c.isAfterLast()) {
				teams[i] = getFromDatabase(c);
				c.moveToNext();
				i++;
			}
		} catch (Exception e) {
			String msg = "query exception during getAllTeamsFromDatabase() on " + this.getTableName() + " table";
			if (e != null && e.getLocalizedMessage() != null) {
				msg += "\n" + e.getLocalizedMessage();
			}
			Log.d(TAG, msg);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return teams;
	}

	/**
	 * insertInDatabase
	 * @param team
	 * @return
	 * returns boolean
	 */
	public boolean insertInDatabase(Team team) {
		return putInDatabase(true, team);
	}

	/**
	 * updateInDatabase
	 * @param team
	 * @return
	 * returns boolean
	 */
	public boolean updateInDatabase(Team team) {
		return putInDatabase(false, team);
	}

	/**
	 * putInDatabase
	 * @param newRecord
	 * @param team
	 * @return
	 * returns boolean
	 */
	public boolean putInDatabase(boolean newRecord, Team team) {
		boolean res = false;
		ContentValues values = new ContentValues();
		values.put(Team.KEY_ROWID, Long.valueOf(team.getId()));
		values.put(Team.KEY_TEAM_NAME, team.getName());
		values.put(Team.KEY_TEAM_CLUB_ID, team.getClubStringId());
		if (newRecord) {
			// insert a new row
			Uri res_insert = this.getContentResolver().insert(this.getUri(), values);
			res = (res_insert != null);
		} else {
			// update the row
			String queryString = Team.KEY_ROWID + "=" + Uri.encode(String.valueOf(team.getId()));
			Uri theUri = Uri.parse(TeamDB.CONTENT_URI + "?" + queryString);
//			this.getContentResolver().update(theUri, values, null, null);
			int res_update = this.getContentResolver().update(theUri, values, Team.KEY_ROWID+" = ?", new String[] {String.valueOf(team.getId())});
			res = (res_update > 0);
		}
		return res;
	}

}
