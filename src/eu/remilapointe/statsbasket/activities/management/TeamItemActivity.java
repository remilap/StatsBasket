/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.db.BasketOpenHelper;
import eu.remilapointe.statsbasket.db.TeamDB;
import eu.remilapointe.statsbasket.sport.Team;

/**
 * @author lapoint1
 *
 */
public class TeamItemActivity extends Activity implements OnClickListener {

	private static final String TAG = TeamItemActivity.class.getSimpleName();
	private Team _team = new Team();
	private TeamDB _teamDb = null;
	private Button _okButton = null;
	private Button _cancelButton = null;
	private EditText _nameEditText = null;
	private Spinner _clubSpinner = null;
//	private Spinner _players_Spinner = null;
	private boolean _newTeam = true;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team_item);

		_okButton = (Button) findViewById(R.id.bt_team_valid);
		_cancelButton = (Button) findViewById(R.id.bt_team_cancel);
		_nameEditText = (EditText) findViewById(R.id.inp_team_name);
		_clubSpinner = (Spinner) findViewById(R.id.spin_team_club);

		// events listeners
		_okButton.setOnClickListener(this);
		_cancelButton.setOnClickListener(this);

		_clubSpinner.setAdapter(Utilities.getClubSpinnerAdapter(this));

		_teamDb = TeamDB.getInstance();
		_teamDb.setContentResolver(this.getContentResolver());
		Bundle data = this.getIntent().getExtras();
		if (data != null) {
			long data_id = data.getLong(Team.KEY_ROWID);
			_team.setId(data_id);
			Log.d(TAG, "id retrieved from data bundle: "+data_id);
		}
		if (_team.getId() != -1) {
			Team getTeam = _teamDb.getFromDatabase(_team.getId());
			if (getTeam == null) {
				Log.e(TAG, "No team found with this index");
			} else {
				_team = getTeam;
				Log.d(TAG, "retrieved team: "+_team.toString());
				_newTeam = false;
				// put values in fields
				_nameEditText.setText(_team.getName());
				_clubSpinner.setSelection((int) _team.getClubId() - 1);
			}
		} else {
			BasketOpenHelper soh = BasketOpenHelper.getInstance();
			long nextId = 0;
			if (soh != null) {
				nextId = soh.getTheLargerIndex(_teamDb.getTableName()) + 1;
				Log.d(TAG, "nextId=" + nextId + " via BasketOpenHelper");
			} else {
				nextId = Team.getNextId();
				Log.d(TAG, "nextId=" + nextId + " via Team()");
			}
			_team.setId(nextId);
			_newTeam = true;
		}
	}

	/**
	 * saveCurrentTeam
	 * returns void
	 */
	private void saveCurrentTeam() {
		String name = _nameEditText.getText().toString();
		_team.setName(name);
		long clubId = _clubSpinner.getSelectedItemId();
		_team.setClubId(clubId + 1);
		Log.d(TAG, "saved team: "+_team.toString());

		_teamDb.putInDatabase(_newTeam, _team);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_team_valid:
			this.saveCurrentTeam();
			this.finish();
			break;
		case R.id.bt_team_cancel:
			this.finish();
			break;
		}
	}

}
