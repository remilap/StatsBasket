/**
 * 
 */
package eu.remilapointe.statsbasket.db;

import java.util.Map;
import java.util.TreeMap;

import eu.remilapointe.statsbasket.sport.Club;
import eu.remilapointe.statsbasket.sport.Identifiable;
import eu.remilapointe.statsbasket.sport.Player;
import eu.remilapointe.statsbasket.sport.Team;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class BasketContentProvider extends ContentProvider {

	public static final String AUTHORITY = "eu.remilapointe.statsbasket.provider";
	private static final String TAG = BasketContentProvider.class.getSimpleName();
	
	private static UriMatcher _um = new UriMatcher(UriMatcher.NO_MATCH);

	private static final int IDATA_CLUB = 3;
	private static final int IDATA_CLUB_ID = 4;
	private static Map<String, String> _club_proj = new TreeMap<String, String>();
	private static ClubDB _clubDb = ClubDB.getInstance();

	private static final int IDATA_TEAM = 5;
	private static final int IDATA_TEAM_ID = 6;
	private static Map<String, String> _team_proj = new TreeMap<String, String>();
	private static TeamDB _teamDb = TeamDB.getInstance();

	private static final int IDATA_PLAYER = 7;
	private static final int IDATA_PLAYER_ID = 8;
	private static Map<String, String> _player_proj = new TreeMap<String, String>();
	private static PlayerDB _playerDb = PlayerDB.getInstance();

	private static final int IDATA_MATCH = 9;
	private static final int IDATA_MATCH_ID = 10;
	private static Map<String, String> _match_proj = new TreeMap<String, String>();
	private static MatchDB _matchDb = MatchDB.getInstance();

	static
    {
    	String[] fields = null;

    	_um.addURI(AUTHORITY, _clubDb.getTableName(), IDATA_CLUB);
    	_um.addURI(AUTHORITY, _clubDb.getTableName()+"/#", IDATA_CLUB_ID);
    	fields = _clubDb.getColumnsList();
    	for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			_club_proj.put(field, field);
		}

    	_um.addURI(AUTHORITY, _teamDb.getTableName(), IDATA_TEAM);
    	_um.addURI(AUTHORITY, _teamDb.getTableName()+"/#", IDATA_TEAM_ID);
    	fields = _teamDb.getColumnsList();
    	for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			_team_proj.put(field, field);
		}

    	_um.addURI(AUTHORITY, _playerDb.getTableName(), IDATA_PLAYER);
    	_um.addURI(AUTHORITY, _playerDb.getTableName()+"/#", IDATA_PLAYER_ID);
    	fields = _playerDb.getColumnsList();
    	for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			_player_proj.put(field, field);
		}

    	_um.addURI(AUTHORITY, _matchDb.getTableName(), IDATA_MATCH);
    	_um.addURI(AUTHORITY, _matchDb.getTableName()+"/#", IDATA_MATCH_ID);
    	fields = _matchDb.getColumnsList();
    	for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			_match_proj.put(field, field);
		}

    }

    private BasketOpenHelper _soh = null;

    /**
     * BasketContentProvider
     */
    public BasketContentProvider() {
    	super();
    	Log.d(TAG, "constructor");
    }

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d(TAG, TAG+".delete( uri="+uri.toString());
		SQLiteDatabase db = this._soh.getWritableDatabase();
		int count;
		switch (_um.match(uri)) {
		case IDATA_CLUB:
			count = db.delete(_clubDb.getTableName(), selection, selectionArgs);
	        break;

		case IDATA_TEAM:
			count = db.delete(_teamDb.getTableName(), selection, selectionArgs);
	        break;

		case IDATA_PLAYER:
			count = db.delete(_playerDb.getTableName(), selection, selectionArgs);
	        break;

		case IDATA_MATCH:
			count = db.delete(_matchDb.getTableName(), selection, selectionArgs);
	        break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		//vnd.android.cursor.item for a single record, or vnd.android.cursor.dir
		Log.d(TAG, TAG+".getType");
		switch (_um.match(uri)) {
		case IDATA_CLUB:
			return "vnd.android.cursor.dir/"+AUTHORITY+"."+_clubDb.getTableName();
		case IDATA_CLUB_ID:
			return "vnd.android.cursor.item/"+AUTHORITY+"."+_clubDb.getTableName();

		case IDATA_TEAM:
			return "vnd.android.cursor.dir/"+AUTHORITY+"."+_teamDb.getTableName();
		case IDATA_TEAM_ID:
			return "vnd.android.cursor.item/"+AUTHORITY+"."+_teamDb.getTableName();

		case IDATA_PLAYER:
			return "vnd.android.cursor.dir/"+AUTHORITY+"."+_playerDb.getTableName();
		case IDATA_PLAYER_ID:
			return "vnd.android.cursor.item/"+AUTHORITY+"."+_playerDb.getTableName();

		case IDATA_MATCH:
			return "vnd.android.cursor.dir/"+AUTHORITY+"."+_matchDb.getTableName();
		case IDATA_MATCH_ID:
			return "vnd.android.cursor.item/"+AUTHORITY+"."+_matchDb.getTableName();

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		Log.d(TAG, TAG+".insert( uri="+uri.toString());
		ContentValues values = null;
		int id = -99;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
			if (values.containsKey(Identifiable.KEY_ROWID)) {
				id = values.getAsInteger(Identifiable.KEY_ROWID).intValue();
				if (id == -1) {
					values.remove(Identifiable.KEY_ROWID);
				}
			}
		} else {
			values = new ContentValues();
		}
		SQLiteDatabase db = this._soh.getWritableDatabase();
		long rowId = 0;
		Uri resUri = null;

		switch (_um.match(uri)) {
		case IDATA_CLUB:
			rowId = db.insert(_clubDb.getTableName(), null, values);
			if (rowId > 0) {
				Log.d(TAG, "club inserted rowId: "+rowId+", while ID= "+id);
				resUri = ContentUris.withAppendedId(_clubDb.getUri(), rowId);
				getContext().getContentResolver().notifyChange(resUri, null);
			}
			break;

		case IDATA_TEAM:
			rowId = db.insert(_teamDb.getTableName(), null, values);
			if (rowId > 0) {
				Log.d(TAG, "team inserted rowId: "+rowId+", while ID= "+id);
				resUri = ContentUris.withAppendedId(_teamDb.getUri(), rowId);
				getContext().getContentResolver().notifyChange(resUri, null);
			}
			break;

		case IDATA_PLAYER:
			try {
				rowId = db.insert(_playerDb.getTableName(), null, values);
				if (rowId > 0) {
					Log.d(TAG, "player inserted rowId: "+rowId+", while ID= "+id);
					resUri = ContentUris.withAppendedId(_playerDb.getUri(), rowId);
					getContext().getContentResolver().notifyChange(resUri, null);
				} else {
					Log.e(TAG, "player not inserted ???");
				}
			} catch (Exception e) {
				Log.e(TAG, "Error during player insertion\n" + e.getMessage());
			}
			break;

		case IDATA_MATCH:
			rowId = db.insert(_matchDb.getTableName(), null, values);
			if (rowId > 0) {
				Log.d(TAG, "match inserted rowId: "+rowId+", while ID= "+id);
				resUri = ContentUris.withAppendedId(_matchDb.getUri(), rowId);
				getContext().getContentResolver().notifyChange(resUri, null);
			}
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		Log.d(TAG, TAG+".onCreate");
		this._soh = new BasketOpenHelper(this.getContext());
		SQLiteDatabase db = this._soh.getReadableDatabase();
		Log.d(TAG, "Database path: "+db.getPath()+" with version "+db.getVersion());
		return true;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d(TAG, TAG+".query( uri="+uri.toString());
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		long theId = 0;
		switch (_um.match(uri)) {
		case IDATA_CLUB:
			qb.setTables(_clubDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._club_proj);
			break;

		case IDATA_CLUB_ID:
			qb.setTables(_clubDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._club_proj);
			theId = ContentUris.parseId(uri);
			qb.appendWhere(Club.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'));
			break;

		case IDATA_TEAM:
			qb.setTables(_teamDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._team_proj);
			break;

		case IDATA_TEAM_ID:
			qb.setTables(_teamDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._team_proj);
			theId = ContentUris.parseId(uri);
			qb.appendWhere(Club.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'));
			break;

		case IDATA_PLAYER:
			qb.setTables(_playerDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._player_proj);
			break;

		case IDATA_PLAYER_ID:
			qb.setTables(_playerDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._player_proj);
			theId = ContentUris.parseId(uri);
			qb.appendWhere(Club.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'));
			break;

		case IDATA_MATCH:
			qb.setTables(_matchDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._match_proj);
			break;

		case IDATA_MATCH_ID:
			qb.setTables(_matchDb.getTableName());
			qb.setProjectionMap(BasketContentProvider._match_proj);
			theId = ContentUris.parseId(uri);
			qb.appendWhere(Club.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'));
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		SQLiteDatabase db = this._soh.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Log.d(TAG, "update( uri="+uri.toString());
		getContext().getContentResolver().notifyChange(uri, null);

		SQLiteDatabase db = this._soh.getWritableDatabase();
		int count = 0;
		String theId = null;
		Log.d(TAG, "switch about: " + _um.match(uri));
		switch (_um.match(uri)) {
		case IDATA_CLUB:
			try {
				count = db.update(_clubDb.getTableName(), values, selection, selectionArgs);
				Log.d(TAG, _clubDb.getTableName()+" dir: "+count+" updated row(s)");
			} catch (Exception e_club) {
				Log.e(TAG, "Exception on update IDATA_CLUB with values="+values+"\n"+e_club.getMessage());
			}
			break;

		case IDATA_CLUB_ID:
			try {
				theId = uri.getPathSegments().get(1);
				Log.d(TAG, "update club id: " + theId);
				count = db.update(_clubDb.getTableName(), values, Club.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'), selectionArgs);
				Log.d(TAG, _clubDb.getTableName()+" item: "+count+" updated row(s)");
			} catch (Exception e_club_id) {
				Log.e(TAG, "Exception on update IDATA_CLUB_ID with values="+values+"\n"+e_club_id.getMessage());
			}
			break;

		case IDATA_TEAM:
			try {
				count = db.update(_teamDb.getTableName(), values, selection, selectionArgs);
				Log.d(TAG, _teamDb.getTableName()+" dir: "+count+" updated row(s)");
			} catch (Exception e_team) {
				Log.e(TAG, "Exception on update IDATA_TEAM with values="+values+"\n"+e_team.getMessage());
			}
			break;

		case IDATA_TEAM_ID:
			try {
				theId = uri.getPathSegments().get(1);
				count = db.update(_teamDb.getTableName(), values, Team.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'), selectionArgs);
				Log.d(TAG, _teamDb.getTableName()+" item: "+count+" updated row(s)");
			} catch (Exception e_team_id) {
				Log.e(TAG, "Exception on update IDATA_TEAM_ID with values="+values+"\n"+e_team_id.getMessage());
			}
			break;

		case IDATA_PLAYER:
			try {
				count = db.update(_playerDb.getTableName(), values, selection, selectionArgs);
				Log.d(TAG, _playerDb.getTableName()+" dir: "+count+" updated row(s)");
			} catch (Exception e_player) {
				Log.e(TAG, "Exception on update IDATA_PLAYER with values="+values+"\n"+e_player.getMessage());
			}
			break;

		case IDATA_PLAYER_ID:
			try {
				theId = uri.getPathSegments().get(1);
				Log.d(TAG, "update player id: " + theId);
				count = db.update(_playerDb.getTableName(), values, Player.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'), selectionArgs);
				Log.d(TAG, _playerDb.getTableName()+" item: "+count+" updated row(s)");
			} catch (Exception e_player_id) {
				Log.e(TAG, "Exception on update IDATA_PLAYER_ID with values="+values+"\n"+e_player_id.getMessage());
			}
			break;

		case IDATA_MATCH:
			try {
				count = db.update(_matchDb.getTableName(), values, selection, selectionArgs);
				Log.d(TAG, _matchDb.getTableName()+" dir: "+count+" updated row(s)");
			} catch (Exception e_club) {
				Log.e(TAG, "Exception on update IDATA_MATCH with values="+values+"\n"+e_club.getMessage());
			}
			break;

		case IDATA_MATCH_ID:
			try {
				theId = uri.getPathSegments().get(1);
				Log.d(TAG, "update match id: " + theId);
				count = db.update(_matchDb.getTableName(), values, Club.KEY_ROWID + " = " + theId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')'), selectionArgs);
				Log.d(TAG, _matchDb.getTableName()+" item: "+count+" updated row(s)");
			} catch (Exception e_match_id) {
				Log.e(TAG, "Exception on update IDATA_MATCH_ID with values="+values+"\n"+e_match_id.getMessage());
			}
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
