/**
 * 
 */
package eu.remilapointe.statsbasket.activities.main;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import eu.remilapointe.statsbasket.R;

/**
 * @author lapoint1
 *
 */
public class ScoringBoardPreferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.scoring_prefs);
	}

}
