/**
 * 
 */
package eu.remilapointe.statsbasket.activities.main;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.db.BasketOpenHelper;

/**
 * @author lapoint1
 *
 */
public class DatabaseManagement extends Activity implements OnClickListener {

	private static final String TAG = DatabaseManagement.class.getSimpleName();
	protected Activity _myActiv = null;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_management);

        _myActiv = this;

        findViewById(R.id.bt_save_database).setOnClickListener(this);
        findViewById(R.id.bt_rest_database).setOnClickListener(this);
        findViewById(R.id.bt_raz_database).setOnClickListener(this);
        findViewById(R.id.bt_export_database).setOnClickListener(this);
        findViewById(R.id.bt_import_database).setOnClickListener(this);

    }

    /**
     * getSaveDirectory
     * @param write
     * @return
     * returns String
     */
    private String getSaveFile(boolean write) {
		String saveDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + BasketOpenHelper.getDbName();
		File fileDir = new File(saveDir);
		String saveFile = saveDir + File.separator + BasketOpenHelper.getDbName() + ".sav";
		Log.d(TAG, "External file: " + saveFile);
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Log.d(TAG, "External file is writable");
			fileDir.mkdirs();
			return saveFile;
		}
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Log.d(TAG, "External file is readable");
			if (write == false) {
				fileDir.mkdirs();
				return saveFile;
			}
			return null;
		}
		Log.d(TAG, "External file is not accessible (" + state + ")");
		return null;
    }

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_save_database:
			Log.d(TAG, "Save database");
//			_db = new Database(BasketOpenHelper.getDbPath(), _myActiv);
//			Log.d(TAG, "Db created: "+_db.isDatabase);
//			_db.exportDatabase();
			String saveFile = getSaveFile(true);
			if (saveFile == null) {
				Toast.makeText(_myActiv, "External directory is not available for saving the database", Toast.LENGTH_LONG).show();
			} else {
				if (BasketOpenHelper.saveDatabase(saveFile)) {
					Toast.makeText(_myActiv, "Database successfuly saved into " + saveFile, Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(_myActiv, "ERROR: DATABASE NOT SAVED", Toast.LENGTH_LONG).show();
				}
			}
			break;

		case R.id.bt_rest_database:
			Log.d(TAG, "Restore database");
//			_db = new Database(BasketOpenHelper.getDbPath(), _myActiv);
//			_db.restoreDatabase();
			String restFile = getSaveFile(false);
			if (restFile == null) {
				Toast.makeText(_myActiv, "External directory is not available for restoring the database", Toast.LENGTH_LONG).show();
			} else {
				if (BasketOpenHelper.restDatabase(restFile)) {
					Toast.makeText(_myActiv, "Database successfuly restored", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(_myActiv, "ERROR: DATABASE NOT RESTORED", Toast.LENGTH_LONG).show();
				}
			}
			break;

		case R.id.bt_raz_database:
			Log.d(TAG, "Launch RAZ Database");
			//TODO ask confirmation for RAZ
			if (BasketOpenHelper.getInstance().razDatabase()) {
				Toast.makeText(getBaseContext(), "RAZ Database done", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getBaseContext(), "ERROR during RAZ Database", Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.bt_export_database:
			Log.d(TAG, "Launch Export Database");
			Toast.makeText(getBaseContext(), "Export Database not yet available", Toast.LENGTH_LONG).show();
//			Intent intentScoringTable = new Intent(getApplicationContext(), ScoringBoardActivity.class);
//			startActivity(intentScoringTable);
			break;

		case R.id.bt_import_database:
			Log.d(TAG, "Launch Import Database");
			Toast.makeText(getBaseContext(), "Import Database not yet available", Toast.LENGTH_LONG).show();
//			Intent intentPreferences = new Intent(getApplicationContext(), ScoringBoardPreferences.class);
//			startActivity(intentPreferences);
			break;

		default:
			break;
		}
	}


}
