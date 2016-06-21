/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.activities.main.MainActivity;
import eu.remilapointe.statsbasket.db.BasketOpenHelper;
import eu.remilapointe.statsbasket.db.ClubDB;
import eu.remilapointe.statsbasket.db.PlayerDB;
import eu.remilapointe.statsbasket.sport.Club;
import eu.remilapointe.statsbasket.sport.Player;

/**
 * @author lapoint1
 *
 */
public class PlayerItemActivity extends Activity implements OnClickListener {

	private static final String TAG = PlayerItemActivity.class.getSimpleName();
	private Player _player = new Player();
	private PlayerDB _playerDB = null;
	private Button _okButton = null;
	private Button _cancelButton = null;
	private EditText _nameEditText = null;
	private EditText _firstnameEditText = null;
	private EditText _bibNumberEditText = null;
	private EditText _licenseEditText = null;
	private Spinner _clubSpinner = null;
	private CheckBox _goalCheckBox = null;
	private CheckBox _captainCheckBox = null;
	private CheckBox _coachCheckBox = null;
	private CheckBox _doctorCheckBox = null;
	private CheckBox _kineCheckBox = null;

	private boolean _newPlayer = true;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_item);

		_okButton = (Button) findViewById(R.id.bt_player_valid);
		_cancelButton = (Button) findViewById(R.id.bt_player_cancel);
		_nameEditText = (EditText) findViewById(R.id.inp_player_name);
		_firstnameEditText = (EditText) findViewById(R.id.inp_player_firstname);
		_bibNumberEditText = (EditText) findViewById(R.id.inp_player_bib_number);
		_licenseEditText = (EditText) findViewById(R.id.inp_player_license);
		_clubSpinner = (Spinner) findViewById(R.id.spin_player_club);
		_goalCheckBox = (CheckBox) findViewById(R.id.chk_player_goal);
		_captainCheckBox = (CheckBox) findViewById(R.id.chk_player_captain);
		_coachCheckBox = (CheckBox) findViewById(R.id.chk_player_coach);
		_doctorCheckBox = (CheckBox) findViewById(R.id.chk_player_doctor);
		_kineCheckBox = (CheckBox) findViewById(R.id.chk_player_kine);

		// events listeners
		_okButton.setOnClickListener(this);
		_cancelButton.setOnClickListener(this);
		_goalCheckBox.setOnClickListener(this);

		int nbClubs = ClubDB.getInstance().getNbElementsFromDatabase();
		if (nbClubs == 0) {
			MainActivity.displayMessage(this, this.getString(R.string.msg_no_club), this.getString(R.string.tit_no_club));
		}
		Club[] theClubs = ClubDB.getInstance().getAllElementsFromDatabase();
		String[] theClubsName = new String[nbClubs];
		for (int i = 0; i < theClubs.length; i++) {
			theClubsName[i] = theClubs[i].getName();
		}
		ArrayAdapter<String> clubAdapt = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, theClubsName);
		clubAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_clubSpinner.setAdapter(clubAdapt);

		_playerDB = PlayerDB.getInstance();
		_playerDB.setContentResolver(getContentResolver());
		Bundle data = this.getIntent().getExtras();
		if (data != null) {
			long data_id = data.getLong(Player.KEY_ROWID);
			_player.setId(data_id);
			Log.d(TAG, "id retrieved from data bundle: "+data_id);
		}
		if (_player.getId() != -1) {
			_player = _playerDB.getFromDatabase(_player.getId());
			Log.d(TAG, "retrieved player: "+_player.toString());
			_newPlayer = false;
			// put values in fields
			_nameEditText.setText(_player.getName());
			_firstnameEditText.setText(_player.getFirstname());
			_bibNumberEditText.setText(_player.getStringBibNumber());
			_licenseEditText.setText(_player.getLicense());
			_clubSpinner.setSelection((int) _player.getClubId());
			_goalCheckBox.setChecked(_player.isGoal());
			_captainCheckBox.setChecked(_player.isCaptain());
			_coachCheckBox.setChecked(_player.isCaptain());
			_doctorCheckBox.setChecked(_player.isDoctor());
			_kineCheckBox.setChecked(_player.isKine());
		} else {
			BasketOpenHelper soh = BasketOpenHelper.getInstance();
			long nextId = 0;
			if (soh != null) {
				nextId = soh.getTheLargerIndex(_playerDB.getTableName()) + 1;
				Log.d(TAG, "nextId=" + nextId + " via BasketOpenHelper");
			} else {
				nextId = Player.getNextId();
				Log.d(TAG, "nextId=" + nextId + " via Player()");
			}
			_player.setId(nextId);
			_newPlayer = true;
		}
	}

	/**
	 * saveCurrentPlayer
	 * returns void
	 */
	private void saveCurrentPlayer() {
		String name = _nameEditText.getText().toString();
		_player.setName(name);
		String firstname = _firstnameEditText.getText().toString();
		_player.setFirstname(firstname);
		String bibNumber = _bibNumberEditText.getText().toString();
		_player.setBibNumber(bibNumber);
		String license = _licenseEditText.getText().toString();
		_player.setLicense(license);
		long clubId = _clubSpinner.getSelectedItemId();
		_player.setClubId(clubId);
		boolean goal = _goalCheckBox.isChecked();
		_player.setGoal(goal);
		boolean captain = _captainCheckBox.isChecked();
		_player.setCaptain(captain);
		boolean coach = _coachCheckBox.isChecked();
		_player.setCoach(coach);
		boolean doctor = _doctorCheckBox.isChecked();
		_player.setDoctor(doctor);
		boolean kine = _kineCheckBox.isChecked();
		_player.setKine(kine);
		Log.d(TAG, "saved player: "+_player.toString());

		ContentValues values = new ContentValues();
		values.put(Player.KEY_ROWID, Long.valueOf(_player.getId()));
		values.put(Player.KEY_PLAYER_NAME, _player.getName());
		values.put(Player.KEY_PLAYER_FIRSTNAME, _player.getFirstname());
		values.put(Player.KEY_PLAYER_BIB_NUMBER, _player.getStringBibNumber());
		values.put(Player.KEY_PLAYER_LICENSE, _player.getLicense());
		values.put(Player.KEY_PLAYER_SPECIFIC, _player.getSpecific());
		values.put(Player.KEY_PLAYER_CLUB_ID, _player.getClubStringID());
		if (_newPlayer) {
			// insert a new row
			this.getContentResolver().insert(_playerDB.getUri(), values);
		} else {
			// update the row
			String queryString = Player.KEY_ROWID + "=" + Uri.encode(String.valueOf(_player.getId()));
			Uri theUri = Uri.parse(PlayerDB.CONTENT_URI + "?" + queryString);
//			this.getContentResolver().update(theUri, values, null, null);
			this.getContentResolver().update(theUri, values, Player.KEY_ROWID+" = ?", new String[] {String.valueOf(_player.getId())});
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_player_valid:
			this.saveCurrentPlayer();
			this.finish();
			break;
		case R.id.bt_player_cancel:
			this.finish();
			break;
		}
	}

}
