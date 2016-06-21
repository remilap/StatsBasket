package eu.remilapointe.statsbasket.db;

import java.util.Iterator;
import java.util.Vector;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import eu.remilapointe.statsbasket.sport.Identifiable;

public abstract class BasketDBbase implements DBable {

	private static final String TAG = BasketDBbase.class.getSimpleName();

	private ContentResolver _contentRes = null;


	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.DBable#setContentResolver(android.content.ContentResolver)
	 */
	@Override
	public void setContentResolver(ContentResolver cr) {
		_contentRes = cr;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.DBable#getContentResolver()
	 */
	@Override
	public ContentResolver getContentResolver() {
		return _contentRes;
	}

	@Override
	public abstract Uri getUri();

	@Override
	public abstract String getTableName();

	@Override
	public abstract String[] getColumnsList();

	@Override
	public abstract Vector<String[]> getSQLColumns();

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.db.DBable#getCreateTable()
	 */
	@Override
	public String getCreateTable() {
		/** Category table creation request */
		// the _id field must be present for using the SimpleCursorAdpater
		StringBuffer create_table = new StringBuffer();
		create_table.append("create table if not exists "
				+ getTableName()
				+ " ("
				+ Identifiable.KEY_ROWID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
		for (Iterator<String[]> iterator = getSQLColumns().iterator(); iterator.hasNext();) {
			String[] field = iterator.next();
			create_table.append(field[0]);
			create_table.append(" ");
			create_table.append(field[1]);
			create_table.append(",");
		}
		create_table.setCharAt(create_table.length()-1, ')');
		Log.d(TAG, "getCreateTable for " + getTableName() + "table returns >" + create_table.toString() + "<");
		return create_table.toString();
	}

	/**
	 * getNbFields
	 * @return
	 * returns int
	 */
	public int getNbColumns() {
		int n = getSQLColumns().size();
		Log.d(TAG, "getNbColumns for " + getTableName() + "table returns " + n);
		return n;
	}

	/**
	 * getAddTheLastColumn
	 * @return
	 * returns String
	 */
	public String getAddTheLastColumn() {
		int n = getNbColumns();
		return getAddTheColumn(n-1);
	}

	/**
	 * getAddTheColumn
	 * @param column_id
	 * @return
	 * returns String
	 */
	public String getAddTheColumn(int column_id) {
		if ( (column_id < 0) || (column_id >= getNbColumns()) ) {
			Log.e(TAG, "getAddTheColumn with column_id (" + column_id + ") out of bounds (0 - " + (getNbColumns()-1) + ")");
			return null;
		}
		String[] the_field = getSQLColumns().get(column_id);
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ");
		sb.append(getTableName());
		sb.append(" ADD COLUMN ");
		sb.append(the_field[0]);
		sb.append(" ");
		sb.append(the_field[1]);
		Log.d(TAG, "getAddTheColumn(" + column_id + ") for " + getTableName() + "table returns >" + sb.toString() + "<");
		return sb.toString();
	}

	/**
	 * getNbElementsFromDatabase
	 * @return
	 * returns int
	 */
	@Override
	public int getNbElementsFromDatabase() {
		Log.d(TAG, "getNbElementsFromDatabase for " + this.getTableName() + " table");
		Cursor c = null;
		int nb = 0;
		if (this.getContentResolver() == null) {
			Log.e(TAG, "The ContentResolver was not provided !!!");
			return nb;
		}
		try {
			c = this.getContentResolver().query(
					this.getUri(),
					null, //null because the projection is fixed in the implemented content provider
					"",
					null,
					null);
			Log.d(TAG, "getNbElementsFromDatabase after query with c is " + (c!=null?"not ":"") + "null");
			if (c != null) {
				nb = c.getCount();
				Log.d(TAG, "getNbElementsFromDatabase: query result gives count= " + nb);
			}
		} catch (Exception e) {
			String msg = "query exception during getNbElementsFromDatabase() on " + this.getTableName() + " table";
			if (e != null && e.getLocalizedMessage() != null) {
				msg += "\n" + e.getLocalizedMessage();
			}
			Log.e(TAG, msg);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		Log.d(TAG, "getNbElementsFromDatabase for " + getTableName() + " table returns " + nb);
		return nb;
	}

}
