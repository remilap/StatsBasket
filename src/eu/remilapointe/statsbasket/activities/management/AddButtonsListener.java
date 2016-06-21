/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import eu.remilapointe.statsbasket.R;

/**
 * @author lapoint1
 *
 */
public class AddButtonsListener implements OnClickListener {

	private String TAG;
	private Activity _upperActivity;

	public AddButtonsListener(String tag, Activity activ) {
		super();
		TAG = tag;
		_upperActivity = activ;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		Intent intentAddClick = null;
		switch (view.getId()) {
		case R.id.bt_add_club:
			Log.d(TAG, "Click on add club button");
			intentAddClick = new Intent(_upperActivity.getApplicationContext(), ClubItemActivity.class);
			_upperActivity.startActivity(intentAddClick);
			break;

		case R.id.bt_add_team:
			Log.d(TAG, "Click on add team button");
			intentAddClick = new Intent(_upperActivity.getApplicationContext(), TeamItemActivity.class);
			_upperActivity.startActivity(intentAddClick);
			break;

		case R.id.bt_add_player:
			Log.d(TAG, "Click on add player button");
			intentAddClick = new Intent(_upperActivity.getApplicationContext(), PlayerItemActivity.class);
			_upperActivity.startActivity(intentAddClick);
			break;

		case R.id.bt_add_match:
			Log.d(TAG, "Click on add match button");
			intentAddClick = new Intent(_upperActivity.getApplicationContext(), MatchItemActivity.class);
			_upperActivity.startActivity(intentAddClick);
			break;

		default:
			break;
		}
	}

}
