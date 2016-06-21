/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.db.BasketOpenHelper;
import eu.remilapointe.statsbasket.db.MatchDB;
import eu.remilapointe.statsbasket.sport.Match;

/**
 * @author lapoint1
 *
 */
public class MatchItemActivity extends Activity implements OnClickListener {

	private static final String TAG = MatchItemActivity.class.getSimpleName();
	private static final int DATE_PICKER_ID = 1111;

	private Match _match = new Match();
	private MatchDB _matchDB = null;
	private Button _okButton = null;
	private Button _cancelButton = null;
	private EditText _refEditText = null;
//	private EditText _clubAEditText = null;
//	private EditText _clubBEditText = null;
	private Spinner _clubASpinner = null;
	private Spinner _clubBSpinner = null;
	private EditText _dateEditText = null;
	private Button _datePickerButton = null;
//	private DatePicker _datePicker = null;
	private EditText _locationEditText = null;
	private boolean _newMatch = true;
	private int _year;
	private int _month;
	private int _day;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.match_item);

		_okButton = (Button) findViewById(R.id.bt_match_valid);
		_cancelButton = (Button) findViewById(R.id.bt_match_cancel);
		_refEditText = (EditText) findViewById(R.id.inp_match_ref);
//		_clubAEditText = (EditText) findViewById(R.id.inp_match_club_a);
//		_clubBEditText = (EditText) findViewById(R.id.inp_match_club_b);
		_clubASpinner = (Spinner) findViewById(R.id.spin_match_club_a);
		_clubBSpinner = (Spinner) findViewById(R.id.spin_match_club_b);
		_dateEditText = (EditText) findViewById(R.id.inp_match_date);
		_datePickerButton = (Button) findViewById(R.id.bt_match_date);
//		_datePicker = (DatePicker) findViewById(R.id.datpic_match_date);
		_locationEditText = (EditText) findViewById(R.id.inp_match_location);

		// events listeners
		_okButton.setOnClickListener(this);
		_cancelButton.setOnClickListener(this);
		_datePickerButton.setOnClickListener(this);

		_clubASpinner.setAdapter(Utilities.getClubSpinnerAdapter(this));
		_clubBSpinner.setAdapter(Utilities.getClubSpinnerAdapter(this));

		_matchDB = MatchDB.getInstance();
		_matchDB.setContentResolver(getContentResolver());
		Bundle data = this.getIntent().getExtras();
		if (data != null) {
			long data_id = data.getLong(Match.KEY_ROWID);
			_match.setId(data_id);
			Log.d(TAG, "id retrieved from data bundle: "+data_id);
		}
		if (_match.getId() != -1) {
			_match = _matchDB.getFromDatabase(_match.getId());
			Log.d(TAG, "retrieved match: "+_match.toString());
			_newMatch = false;
			// put values in fields
			_refEditText.setText(_match.getReference());
//			_clubAEditText.setText(_match.getClubA());
//			_clubBEditText.setText(_match.getClubB());
			_clubASpinner.setSelection((int) _match.getClubAId());
			_clubBSpinner.setSelection((int) _match.getClubBId());
			
			_dateEditText.setText(_match.getDateString());
			_datePickerButton.setBackgroundResource(android.R.drawable.ic_menu_day);
			_year = _match.getDateYear();
			_month = _match.getDateMonth();
			_day = _match.getDateDayOfMonth();
			//_datePicker.setContentDescription(contentDescription);
			_locationEditText.setText(_match.getLocation());
		} else {
			BasketOpenHelper soh = BasketOpenHelper.getInstance();
			long nextId = 0;
			if (soh != null) {
				nextId = soh.getTheLargerIndex(_matchDB.getTableName()) + 1;
				Log.d(TAG, "nextId=" + nextId + " via BasketOpenHelper");
			} else {
				nextId = Match.getNextId();
				Log.d(TAG, "nextId=" + nextId + " via Match.getNextId()");
			}
			_match.setId(nextId);
			_newMatch = true;
		}
	}

	/**
	 * saveCurrentMatch
	 * returns void
	 */
	private void saveCurrentMatch() {
		String ref = _refEditText.getText().toString();
		_match.setReference(ref);
//		String clubA = _clubAEditText.getText().toString();
//		_match.setClubA(clubA);
//		String clubB = _clubBEditText.getText().toString();
//		_match.setClubB(clubB);
		long clubAId = _clubASpinner.getSelectedItemId();
		_match.setClubAId(clubAId);
		long clubBId = _clubBSpinner.getSelectedItemId();
		_match.setClubBId(clubBId);
		String dat = _dateEditText.getText().toString();
		_match.setDateString(dat);
		String location = _locationEditText.getText().toString();
		_match.setLocation(location);
		Log.d(TAG, "saved match: "+_match.toString());

		_matchDB.putToDatabase(_newMatch, _match);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_match_valid:
			this.saveCurrentMatch();
			this.finish();
			break;
		case R.id.bt_match_cancel:
			this.finish();
			break;
		case R.id.bt_match_date:
			// On button click show datepicker dialog
			showDialog(DATE_PICKER_ID);
			break;
		}
	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			_year  = selectedYear;
			_match.setDateYear(_year);
			_month = selectedMonth;
			_match.setDateMonth(_month);
			_day   = selectedDay;
			_match.setDateDayOfMonth(_day);

			// Show selected date
			_dateEditText.setText(_match.getDateString());
		}
	};

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:
			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listener to date picker
			_year = _match.getDateYear();
			_month = _match.getDateMonth();
			_day = _match.getDateDayOfMonth();
			Log.d(TAG, "before launching DatePickerDialog, year: "+_year+", month: "+_month+", day: "+_day);
			return new DatePickerDialog(this, pickerListener, _year, _month, _day);
		}
		return null;
	}

}
