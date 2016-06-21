/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import eu.remilapointe.statsbasket.R;

/**
 * @author lapoint1
 *
 */
public class SportTabs extends TabActivity {

	private static final String TAG = SportTabs.class.getSimpleName();

	private TabHost tabHost;
	private TabSpec tabSpec;
	private String[] _tabsName = new String[10];
	private boolean _tabInit = false;

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.tabbed_sport);

		initTabHost();

	}

	/**
	 * initTabHost
	 */
	private void initTabHost() {
		if (_tabInit) {
			return;
		}
		tabHost = getTabHost();
		if (tabHost == null) {
			Log.e(TAG, "tabHost is null !!!");
			this.finish();
			return;
		}
		String txt = null;
		txt = getResources().getString(R.string.txt_club_title);
		Log.d(TAG, "new tab panel for " + txt);
		_tabsName[1] = txt;
		tabSpec = tabHost.newTabSpec(txt);
		if (tabSpec == null) {
			Log.e(TAG, "tabSpec for club is null !!!");
			this.finish();
			return;
		}
		tabSpec.setIndicator(txt);
		tabSpec.setIndicator(txt, getResources().getDrawable(android.R.drawable.ic_menu_add));
		Intent intentClubList = new Intent(this, ClubListActivity.class);
		tabSpec.setContent(intentClubList);
		tabHost.addTab(tabSpec);

		txt = getResources().getString(R.string.txt_team_title);
		Log.d(TAG, "new tab panel for " + txt);
		_tabsName[2] = txt;
		tabSpec = tabHost.newTabSpec(txt);
		if (tabSpec == null) {
			Log.e(TAG, "tabSpec for team is null !!!");
			this.finish();
			return;
		}
		tabSpec.setIndicator(txt);
		tabSpec.setIndicator(txt, getResources().getDrawable(android.R.drawable.ic_menu_day));
		Intent intentTeamList = new Intent(this, TeamListActivity.class);
		tabSpec.setContent(intentTeamList);
		tabHost.addTab(tabSpec);

		txt = getResources().getString(R.string.txt_player_title);
		Log.d(TAG, "new tab panel for " + txt);
		_tabsName[3] = txt;
		tabSpec = tabHost.newTabSpec(txt);
		if (tabSpec == null) {
			Log.e(TAG, "tabSpec for player is null !!!");
			this.finish();
			return;
		}
		tabSpec.setIndicator(txt);
		tabSpec.setIndicator(txt, getResources().getDrawable(android.R.drawable.ic_menu_edit));
		Intent intentPlayerList = new Intent(this, PlayerListActivity.class);
		tabSpec.setContent(intentPlayerList);
		tabHost.addTab(tabSpec);

		txt = getResources().getString(R.string.txt_match_title);
		Log.d(TAG, "new tab panel for " + txt);
		_tabsName[3] = txt;
		tabSpec = tabHost.newTabSpec(txt);
		if (tabSpec == null) {
			Log.e(TAG, "tabSpec for match is null !!!");
			this.finish();
			return;
		}
		tabSpec.setIndicator(txt);
		tabSpec.setIndicator(txt, getResources().getDrawable(android.R.drawable.ic_menu_info_details));
		Intent intentMatchList = new Intent(this, MatchListActivity.class);
		tabSpec.setContent(intentMatchList);
		tabHost.addTab(tabSpec);

		_tabInit = true;
	}

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		initTabHost();
	}

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		initTabHost();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		initTabHost();
	}

	/**
	 * getSportTabHost
	 * @return
	 */
	public TabHost getSportTabHost() {
		return tabHost;
	}

	/**
	 * getTabPanel
	 * @param idx
	 * @return
	 */
	public View getTabPanel(int idx) {
		return tabHost.getChildAt(idx);
	}

	/**
	 * getTabPanel
	 * @param tab
	 * @return
	 */
	public View getTabPanel(String tab) {
		for (int i = 0; i < _tabsName.length; i++) {
			if (tab.equals(_tabsName[i])) {
				return tabHost.getChildAt(i);
			}
		}
		return null;
	}

}
