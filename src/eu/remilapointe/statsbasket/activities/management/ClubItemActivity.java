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
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.db.BasketOpenHelper;
import eu.remilapointe.statsbasket.db.ClubDB;
import eu.remilapointe.statsbasket.sport.Club;

/**
 * @author lapoint1
 *
 */
public class ClubItemActivity extends Activity implements OnClickListener {

	private static final String TAG = ClubItemActivity.class.getSimpleName();
	private Club _club = new Club();
	private ClubDB _clubDB = null;
	private Button _okButton = null;
	private Button _cancelButton = null;
	private EditText _nameEditText = null;
	private EditText _numberEditText = null;
	private EditText _townEditText = null;
	private boolean _newClub = true;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.club_item);

		_okButton = (Button) findViewById(R.id.bt_club_valid);
		_cancelButton = (Button) findViewById(R.id.bt_club_cancel);
		_nameEditText = (EditText) findViewById(R.id.inp_club_name);
		_numberEditText = (EditText) findViewById(R.id.inp_club_number);
		_townEditText = (EditText) findViewById(R.id.inp_club_town);

		// events listeners
		_okButton.setOnClickListener(this);
		_cancelButton.setOnClickListener(this);

		_clubDB = ClubDB.getInstance();
		_clubDB.setContentResolver(getContentResolver());
		Bundle data = this.getIntent().getExtras();
		if (data != null) {
			long data_id = data.getLong(Club.KEY_ROWID);
			_club.setId(data_id);
			Log.d(TAG, "id retrieved from data bundle: "+data_id);
		}
		if (_club.getId() != -1) {
			_club = _clubDB.getFromDatabase(_club.getId());
			Log.d(TAG, "retrieved club: "+_club.toString());
			_newClub = false;
			// put values in fields
			_nameEditText.setText(_club.getName());
			_numberEditText.setText(_club.getNumber());
			_townEditText.setText(_club.getTown());
		} else {
			BasketOpenHelper soh = BasketOpenHelper.getInstance();
			long nextId = 0;
			if (soh != null) {
				nextId = soh.getTheLargerIndex(_clubDB.getTableName()) + 1;
				Log.d(TAG, "nextId=" + nextId + " via BasketOpenHelper");
			} else {
				nextId = Club.getNextId();
				Log.d(TAG, "nextId=" + nextId + " via Club.getNextId()");
			}
			_club.setId(nextId);
			_newClub = true;
		}
	}

	/**
	 * saveCurrentCategory
	 * returns void
	 */
	private void saveCurrentClub() {
		String name = _nameEditText.getText().toString();
		_club.setName(name);
		String number = _numberEditText.getText().toString();
		_club.setNumber(number);
		String town = _townEditText.getText().toString();
		_club.setTown(town);
		Log.d(TAG, "saved club: "+_club.toString());

		_clubDB.putToDatabase(_newClub, _club);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_club_valid:
			this.saveCurrentClub();
			this.finish();
			break;
		case R.id.bt_club_cancel:
			this.finish();
			break;
		}
	}

}
