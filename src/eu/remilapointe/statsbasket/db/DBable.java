package eu.remilapointe.statsbasket.db;

import java.util.Vector;

import android.content.ContentResolver;
import android.net.Uri;

public interface DBable {

	public void setContentResolver(ContentResolver cr);

	public ContentResolver getContentResolver();

	public Uri getUri();

	public String getTableName();

	public String[] getColumnsList();

	public Vector<String[]> getSQLColumns();

	public String getCreateTable();

	public int getNbElementsFromDatabase();

}
