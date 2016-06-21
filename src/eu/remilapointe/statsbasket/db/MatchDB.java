/**
 * 
 */
package eu.remilapointe.statsbasket.db;

import java.util.Vector;

import eu.remilapointe.statsbasket.sport.Match;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class MatchDB extends BasketDBbase implements DBable {

	private static final String TAG = MatchDB.class.getSimpleName();

	private static final String DB_MATCH_TABLE = "match";
	public static final String CONTENT_URI = "content://" + BasketContentProvider.AUTHORITY + "/" + DB_MATCH_TABLE;

	private static MatchDB _instance = null;
	private static Uri _matchUri = null;

	static {
		_matchUri = Uri.parse(CONTENT_URI);
	}

	/**
	 * MatchDB
	 */
	private MatchDB() {
		super();
	}

	/**
	 * getInstance
	 * @return
	 */
	public static MatchDB getInstance() {
		if (_instance == null) {
			_instance = new MatchDB();
		}
		return _instance;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getUri()
	 */
	@Override
	public Uri getUri() {
		return _matchUri;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getTableName()
	 */
	@Override
	public String getTableName() {
		return DB_MATCH_TABLE;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getColumnsList()
	 */
	@Override
	public String[] getColumnsList() {
		return new String[] {
				Match.KEY_ROWID,
				Match.KEY_MATCH_REF,
				Match.KEY_MATCH_CLUB_A,
				Match.KEY_MATCH_CLUB_B,
				Match.KEY_MATCH_DATE,
				Match.KEY_MATCH_LOCATION};
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.BasketDBbase#getSQLColumns()
	 */
	@Override
	public Vector<String[]> getSQLColumns() {
		Vector<String[]> fields = new Vector<String[]>();
		fields.add(new String[] {Match.KEY_MATCH_REF, Match.KEY_STRING_FIELD});
		fields.add(new String[] {Match.KEY_MATCH_CLUB_A, Match.KEY_INT_FIELD});
		fields.add(new String[] {Match.KEY_MATCH_CLUB_B, Match.KEY_INT_FIELD});
		fields.add(new String[] {Match.KEY_MATCH_DATE, Match.KEY_STRING_FIELD});
		fields.add(new String[] {Match.KEY_MATCH_LOCATION, Match.KEY_STRING_FIELD});
		return fields;
	}

	/**
	 * getFromDatabase
	 * @param c
	 * @return Match
	 */
	public Match getFromDatabase(Cursor c) {
		long id = 0;
		String ref = null;
		long clubAId = 0;
		long clubBId = 0;
		String dat = null;
		String location = null;
		if (c != null) {
			for (int i = 0 ; i < c.getColumnCount() ; i++) {
				if (Match.KEY_ROWID.equals(c.getColumnName(i))) {
					id = c.getLong(i);
				} else if (Match.KEY_MATCH_REF.equals(c.getColumnName(i))) {
					ref = c.getString(i);
				} else if (Match.KEY_MATCH_CLUB_A.equals(c.getColumnName(i))) {
					clubAId = c.getLong(i);
				} else if (Match.KEY_MATCH_CLUB_B.equals(c.getColumnName(i))) {
					clubBId = c.getLong(i);
				} else if (Match.KEY_MATCH_DATE.equals(c.getColumnName(i))) {
					dat = c.getString(i);
				} else if (Match.KEY_MATCH_LOCATION.equals(c.getColumnName(i))) {
					location = c.getString(i);
				}
			}
		}
		Match match = new Match(ref, clubAId, clubBId, dat, location);
		match.setId(id);
		return match;
	}

	/**
	 * getFromDatabase
	 * @param cr
	 * @param id
	 * @return Match
	 */
	public Match getFromDatabase(long id) {
		Log.d(TAG, "getFromDatabase for " + this.getTableName() + " table and index " + id);
		Cursor c = null;
		Match match = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return match;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					Match.KEY_ROWID+" = ? ",
					new String[]{String.valueOf(id)},
					null);
			if (c.moveToFirst()) {
				match = getFromDatabase(c);
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
		return match;
	}

	/**
	 * getAllElementsFromDatabase
	 * @return Match[]
	 */
	public Match[] getAllElementsFromDatabase() {
		Log.d(TAG, "getAllElementsFromDatabase for " + this.getTableName() + " table");
		Match[] matches = null;
		Cursor c = null;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return matches;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					"",
					null,
					null);
			Log.d(TAG, "getAllElementsFromDatabase after query with c is " + (c!=null?"not ":"") + "null");
			matches = new Match[c.getCount()];
			c.moveToFirst();
			int i = 0;
			while (! c.isAfterLast()) {
				matches[i] = getFromDatabase(c);
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
		return matches;
	}

	/**
	 * insertInDatabase
	 * @param match
	 * @return boolean
	 */
	public boolean insertInDatabase(Match match) {
		return putToDatabase(true, match);
	}

	/**
	 * updateInDatabase
	 * @param match
	 * @return boolean
	 */
	public boolean updateInDatabase(Match match) {
		return putToDatabase(false, match);
	}

	/**
	 * putToDatabase
	 * @param newRecord
	 * @param match
	 * @return boolean
	 */
	public boolean putToDatabase(boolean newRecord, Match match) {
		boolean res = false;
		ContentValues values = new ContentValues();
		values.put(Match.KEY_ROWID, Long.valueOf(match.getId()));
		values.put(Match.KEY_MATCH_REF, match.getReference());
		values.put(Match.KEY_MATCH_CLUB_A, match.getClubAStringId());
		values.put(Match.KEY_MATCH_CLUB_B, match.getClubBStringId());
		values.put(Match.KEY_MATCH_DATE, match.getDateString());
		values.put(Match.KEY_MATCH_LOCATION, match.getLocation());
		if (newRecord) {
			// insert a new row
			Uri res_insert = this.getContentResolver().insert(this.getUri(), values);
			res = (res_insert != null);
		} else {
			// update the row
			String queryString = Match.KEY_ROWID + "=" + Uri.encode(String.valueOf(match.getId()));
			Uri theUri = Uri.parse(MatchDB.CONTENT_URI + "?" + queryString);
//			this.getContentResolver().update(theUri, values, null, null);
			int res_update = this.getContentResolver().update(theUri, values, Match.KEY_ROWID+" = ?", new String[] {String.valueOf(match.getId())});
			res = (res_update > 0);
		}
		return res;
	}

}
