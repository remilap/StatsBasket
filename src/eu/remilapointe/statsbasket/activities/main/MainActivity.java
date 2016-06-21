/**
 * 
 */
package eu.remilapointe.statsbasket.activities.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.activities.management.DialogReturnable;
import eu.remilapointe.statsbasket.activities.management.SportTabs;

/**
 * @author lapoint1
 *
 */
public class MainActivity extends Activity implements OnClickListener, DialogReturnable {

	public static final String DATA_ID = "data_id";

	private static final String TAG = MainActivity.class.getSimpleName();

	public static final int REQ_CONFIRM_QUIT = 1;
	public static final int REQ_CONFIRM_DELETE = 2;

	protected static MainActivity _myActiv = null;
//	private Database _db = null;
	private long _lastTimeKeyUp = 0;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        _myActiv = this;

        findViewById(R.id.bt_score_sheet).setOnClickListener(this);
        findViewById(R.id.bt_preferences).setOnClickListener(this);
        findViewById(R.id.bt_sport_management).setOnClickListener(this);
        findViewById(R.id.bt_database_management).setOnClickListener(this);
        findViewById(R.id.bt_about).setOnClickListener(this);

        getTerminalInformation();
//        String termInfo = getTerminalInformation();
//        Toast.makeText(getApplicationContext(), termInfo, Toast.LENGTH_LONG).show();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStart()
     */
    @Override
	protected void onStart() {
		super.onStart();
    	Log.d(TAG, "onStart");
    	_lastTimeKeyUp = System.currentTimeMillis();
    }

    /**
     * getInstance
     * @return
     * returns MainActivity
     */
    public static MainActivity getInstance() {
    	return _myActiv;
    }

    /**
     * getTerminalInformation
     * returns String
     */
    private String getTerminalInformation() {
        int orient = this.getRequestedOrientation();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        StringBuffer sb = new StringBuffer();
        sb.append("Metrics:");
        sb.append("\n- orientation: ");
        switch (orient) {
		case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
			sb.append("landscape");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
			sb.append("portrait");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_USER:
			sb.append("user");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_NOSENSOR:
			sb.append("no sensor");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_SENSOR:
			sb.append("sensor");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_BEHIND:
			sb.append("behind");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED:
			sb.append("unspecified");
			break;
		default:
			sb.append("unknown");
			break;
		}
        sb.append("\n- density: ");
        sb.append(metrics.density);
        sb.append(" / density DPI: ");
        switch (metrics.densityDpi) {
		case DisplayMetrics.DENSITY_LOW:
			sb.append("LOW");
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			sb.append("MEDIUM");
			break;
		case DisplayMetrics.DENSITY_HIGH:
			sb.append("HIGH");
			break;
		default:
			sb.append("DEFAULT");
			break;
		}
        sb.append("\n- height: ");
        sb.append(metrics.heightPixels);
        sb.append(" pixels / ydpi: ");
        sb.append(metrics.ydpi);
        sb.append(" pixels");
        sb.append("\n- width: ");
        sb.append(metrics.widthPixels);
        sb.append(" pixels / xdpi: ");
        sb.append(metrics.xdpi);
        sb.append(" pixels");
        Log.d(TAG, sb.toString());

        return sb.toString();
    }

    /* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_score_sheet:
			Log.d(TAG, "Launch Score Sheet");
			Intent intentScoreSheet = new Intent(getApplicationContext(), ScoreSheetActivity.class);
			startActivity(intentScoreSheet);
			break;

		case R.id.bt_sport_management:
			Log.d(TAG, "Launch Sport Teams Management");
			Intent intentSportManagement = new Intent(this, SportTabs.class);
			startActivity(intentSportManagement);
			break;

		case R.id.bt_database_management:
			Log.d(TAG, "Launch Database Management");
			Intent intentDataManagement = new Intent(getApplicationContext(), DatabaseManagement.class);
			startActivity(intentDataManagement);
			break;

		case R.id.bt_preferences:
			Log.d(TAG, "Launch preferences");
			Intent intentPreferences = new Intent(getApplicationContext(), ScoringBoardPreferences.class);
			startActivity(intentPreferences);
			break;

		case R.id.bt_about:
			Log.d(TAG, "About");
			String s = String.format(getString(R.string.txt_about_message), getString(R.string.txt_user_id), getString(R.string.txt_version));
			Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
			break;

		case R.id.bt_exit:
			Log.d(TAG, "Exit");
	        MainActivity.askConfirm(MainActivity.REQ_CONFIRM_QUIT, _myActiv, _myActiv, getString(R.string.quit_confirm_st), getString(R.string.confirm_st));
			break;

		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//	        event.startTracking();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    		//&& event.isTracking() && !event.isCanceled()) {
	        // *** Your Code ***
	    	long now = System.currentTimeMillis();
	    	long diff = now - _lastTimeKeyUp;
	    	_lastTimeKeyUp = now;
	    	Log.d(TAG, "now is: " + now + ", diff with previous keyUp: " + diff);
	    	if (diff > 400) {
		        MainActivity.askConfirm(MainActivity.REQ_CONFIRM_QUIT, _myActiv, _myActiv, getString(R.string.quit_confirm_st), getString(R.string.confirm_st));
		        return true;
	    	}
	    }
	    return super.onKeyUp(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.activities.management.DialogReturnable#dialogReturn(int, int, android.content.DialogInterface, int)
	 */
	@Override
	public void dialogReturn(int reqId, int res, DialogInterface dialInt, int id) {
		Log.d(TAG, "dialogReturn with reqId: " + reqId + ", res: " + res + ", id: " + id);
		if (res == RESULT_OK) {
			dialInt.cancel();
			this.finish();
		} else if (res == RESULT_CANCELED) {
			dialInt.cancel();
		}
	}

	/**
	 * askConfirm
	 * @param activ
	 * @return
	 * returns boolean
	 */
	public static void askConfirm(final int reqId, final Activity activ, final DialogReturnable res, final String msg, final String title) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(activ);
		dialog.setMessage(msg);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.setPositiveButton(activ.getString(android.R.string.yes), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialInt, int id) {
					res.dialogReturn(reqId, RESULT_OK, dialInt, id);
				}
			});
		dialog.setNegativeButton(activ.getString(android.R.string.no), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialInt, int id) {
					dialInt.cancel();
					res.dialogReturn(reqId, RESULT_CANCELED, dialInt, id);
				}
			});
		dialog.show();
    }

	/**
	 * displayMessage
	 * @param activ
	 * @param msg
	 * @param title
	 */
	public static void displayMessage(final Activity activ, final String msg, final String title) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(activ);
		dialog.setMessage(msg);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.setPositiveButton(activ.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialInt, int id) {
					dialInt.dismiss();
					activ.finish();
				}
			});
		dialog.show();
	}

	/**
	 * deleteConfirm
	 * @param activ
	 * @return
	 * returns boolean
	 */
//	public static boolean deleteConfirm(final Activity activ, final Confirmable rs) {
//		new AlertDialog.Builder(activ)
//			.setMessage(R.string.delete_confirm_st)
//			.setTitle(R.string.delete_st)
//			.setCancelable(false)
//			.setPositiveButton(R.string.yes_st, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int id) {
//					rs.resultConfirm(true);
//					dialog.cancel();
//				}
//			})
//			.setNegativeButton(R.string.no_st, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int id) {
//					rs.resultConfirm(false);
//					dialog.cancel();
//				}
//			})
//			.show();
//    	return true;
//    }

}
