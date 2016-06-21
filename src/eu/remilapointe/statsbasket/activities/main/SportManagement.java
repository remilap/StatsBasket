/**
 * 
 */
package eu.remilapointe.statsbasket.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.activities.management.ClubListActivity;
import eu.remilapointe.statsbasket.activities.management.PlayerListActivity;
import eu.remilapointe.statsbasket.activities.management.TeamListActivity;

/**
 * @author lapoint1
 *
 */
public class SportManagement extends Activity implements OnClickListener {

	private static final String TAG = SportManagement.class.getSimpleName();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sport_management);

		((Button) findViewById(R.id.bt_club_mgt2)).setOnClickListener(this);
		((Button) findViewById(R.id.bt_team_mgt2)).setOnClickListener(this);
		((Button) findViewById(R.id.bt_player_mgt2)).setOnClickListener(this);

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
    	Log.d(TAG, "onStart");
		super.onStart();

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
    	Log.d(TAG, "onStop");
		super.onStop();

	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		Log.d(TAG, "click on " + view.getId());
		switch (view.getId()) {
		case R.id.bt_club_mgt2:
			Log.d(TAG, "Launch Club Management");
//			_last_button = R.id.bt_club_mgt;
//	    	msg = String.format(getString(R.string.bt_club), getString(R.string.club_A));
//			displayNewAlertDialog(msg, R.string.bt_ok_club);
			Intent intentClubList = new Intent(getApplicationContext(), ClubListActivity.class);
			startActivity(intentClubList);
			break;

		case R.id.bt_team_mgt2:
			Log.d(TAG, "Launch Team Management");
//			Toast.makeText(getApplicationContext(), "Launch Team Management not yet implemented", Toast.LENGTH_LONG).show();
			Intent intentTeamList = new Intent(getApplicationContext(), TeamListActivity.class);
			startActivity(intentTeamList);
			break;

		case R.id.bt_player_mgt2:
			Log.d(TAG, "Launch Player Management");
//			Toast.makeText(getApplicationContext(), "Launch Player Management not yet implemented", Toast.LENGTH_LONG).show();
			Intent intentPlayerList = new Intent(getApplicationContext(), PlayerListActivity.class);
			startActivity(intentPlayerList);
			break;

		default:
			break;
		}
	}

}
