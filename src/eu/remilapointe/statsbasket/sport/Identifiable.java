package eu.remilapointe.statsbasket.sport;

import java.util.Vector;

import android.provider.BaseColumns;

/**
 * @author lapoint1
 * @version 1.1
 */
public interface Identifiable extends Cloneable {

	public static final String KEY_ROWID = BaseColumns._ID;

	public static final String KEY_STRING_FIELD = "string";
	public static final String KEY_INT_FIELD = "integer";

	/**
	 * getId
	 * @return the id
	 * returns String
	 */
	public long getId();

	/**
	 * getIdString
	 * @return
	 * returns String
	 */
	public String getIdString();

	/**
	 * setId
	 * @param id id to set
	 * returns void
	 */
	public void setId(long id);

	/**
	 * getObjectValues
	 * @return a table containing String pairs with field name and its value
	 * returns Vector<String[]>
	 */
	public Vector<String[]> getObjectValues();

	/**
	 * getNbFields
	 * @return
	 * returns int
	 */
	public int getNbFields();

	/**
	 * toBackup
	 * @return a string containing the different fields value
	 * returns String
	 */
	public String toBackup();

}
