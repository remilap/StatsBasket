/**
 * 
 */
package eu.remilapointe.statsbasket.activities.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.activities.basket.BasketScoringBoardActivity;
import eu.remilapointe.statsbasket.activities.hand.ScoringBoardPlayerActivity;
import eu.remilapointe.statsbasket.activities.management.Utilities;
import eu.remilapointe.statsbasket.activities.sample.SampleScoringBoardActivity;
import eu.remilapointe.statsbasket.common.play.CommonPlayBoard;
import eu.remilapointe.statsbasket.sport.ScoreSheet;

/**
 * @author lapoint1
 *
 */
public class ScoreSheetActivity extends Activity implements OnClickListener, DialogInterface.OnClickListener, OnItemClickListener {

	private static final String TAG = ScoreSheetActivity.class.getSimpleName();

	protected static ScoreSheetActivity _myActiv = null;

	private TextView _txtTeamA = null;
//	private TextView _txtTeamB = null;
	private Spinner _spinTeamA = null;
//	private Spinner _spinTeamB = null;
	private EditText _ed_match_date = null;
	private EditText _ed_match_hour = null;
	private EditText _ed_match_location = null;
	private Button _bt_save_match = null;
	private Button bt_scoring_table = null;
	private int _last_button = 0;
	private Cursor _curs_categ = null;
	private Cursor _curs_team = null;

	/** Called when the activity is first created. */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_sheet);

		_txtTeamA = (TextView) findViewById(R.id.txt_team_A);
		_txtTeamA.setText(getResources().getText(R.string.txt_team_title) + " " + getResources().getText(R.string.club_A));
//		_txtTeamB = (TextView) findViewById(R.id.txt_team_B);
//		_txtTeamB.setText(getResources().getText(R.string.txt_team_title) + " " + getResources().getText(R.string.club_B));
		_spinTeamA = (Spinner) findViewById(R.id.spin_team_A);
//		_spinTeamB = (Spinner) findViewById(R.id.spin_team_B);
		_ed_match_date = (EditText) findViewById(R.id.ed_match_date);
		_ed_match_hour = (EditText) findViewById(R.id.ed_match_hour);
		_ed_match_location = (EditText) findViewById(R.id.ed_match_location);
		((Button) findViewById(R.id.bt_save_match)).setOnClickListener(this);
		((Button) findViewById(R.id.bt_scoring_hand)).setOnClickListener(this);
		((Button) findViewById(R.id.bt_scoring_basket)).setOnClickListener(this);
		((Button) findViewById(R.id.bt_scoring_sample)).setOnClickListener(this);

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
    	Log.d(TAG, "onStart");
		super.onStart();

		SpinnerAdapter adapt_team = Utilities.getTeamSpinnerAdapter(this);
//		String[] proj_team = new String[] {
//				Team.KEY_ROWID,
//				Team.KEY_TEAM_NAME,
//				Team.KEY_TEAM_CLUB_ID};
//		this._curs_team = getContentResolver().query(ClubDB.getInstance().getUri(), proj_team, null, null, null);
//		SpinnerAdapter adapt_team = new SimpleCursorAdapter(this,
//				// Use a template that displays a text view
//				android.R.layout.simple_spinner_item,
//				// Give the cursor to the list adapter
//				this._curs_team,
//				// Map the Team Name column in the team database to...
//				new String[] {Team.KEY_TEAM_NAME, Team.KEY_TEAM_CLUB_ID},
//				// The "text1" view defined in the XML template
//				new int[] { android.R.id.text1, android.R.id.text2 });
		_spinTeamA.setAdapter(adapt_team);
//		_spinTeamB.setAdapter(adapt_team);
//		_spinTeamA.setOnItemClickListener(this);
//		_spinTeamB.setOnItemClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
    	Log.d(TAG, "onStop");
		super.onStop();

		if (this._curs_categ != null) {
			this._curs_categ.close();
		}
		if (this._curs_team != null) {
			this._curs_team.close();
		}
	}

	/**
	 * displayNewAlertDialog
	 * @param resIdTitle
	 * @param resIdText
	 * returns void
	 *
	private void displayNewAlertDialog(String stTitle, int resIdText) {
		// Layout instantiation as a view
		LayoutInflater factory = LayoutInflater.from(this);
		final View alertDialogView = factory.inflate(R.layout.dialog_input_text, null);

		// AlertDialog creation
		AlertDialog.Builder dialog_build = new AlertDialog.Builder(this);

		// affectation with the custom view
		dialog_build.setView(alertDialogView);

		// icon
		dialog_build.setIcon(android.R.drawable.ic_dialog_alert);

		// retrieve the EditText for input
		_dialog_et = (EditText) alertDialogView.findViewById(R.id.dialog_input);

		// title
		dialog_build.setTitle(stTitle);
		dialog_build.setPositiveButton(resIdText, this);
		dialog_build.setNegativeButton(R.string.bt_cancel, this);
		dialog_build.show();
	}
	 */

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		Log.d(TAG, "click on " + view.getId());
		switch (view.getId()) {
		case R.id.bt_save_match:
			Log.d(TAG, "Save Match");
			break;

		case R.id.bt_scoring_hand:
			Log.d(TAG, "Go to scoring table for handball");
//			Intent intentScoringBoard = new Intent(getApplicationContext(), ScoringBoardActivity.class);
			Intent intentScoringBoardHand = new Intent(getApplicationContext(), ScoringBoardPlayerActivity.class);
			startActivity(intentScoringBoardHand);
			break;

		case R.id.bt_scoring_basket:
			Log.d(TAG, "Go to scoring table for basket");
			// Retrieve selected team
			int teamId = (int) _spinTeamA.getSelectedItemId();
			String teamName = Utilities.getTheTeamName(_myActiv, teamId);
			Log.d(TAG, "Selected team: " + teamName + " (id=" + teamId + ")");
			// Retrieve the players list (name + bib number)
			String[] players = Utilities.getPlayers(_myActiv, teamId);
			StringBuilder plList = new StringBuilder();
			for (int p = 0; p < players.length; p++) {
				if (plList.length() > 0) {
					plList.append(";");
				}
				plList.append(players[p]);
			}
			Intent intentScoringBoardBasket = new Intent(getApplicationContext(), BasketScoringBoardActivity.class);
			intentScoringBoardBasket.putExtra(CommonPlayBoard.TAG_NB_PLAYERS_TEAM_A, players.length);
			intentScoringBoardBasket.putExtra(CommonPlayBoard.TAG_PLAYERS_TEAM_A, plList.toString());
			startActivity(intentScoringBoardBasket);
			break;

		case R.id.bt_scoring_sample:
			Log.d(TAG, "Go to scoring table example");
//			Intent intentScoringBoard = new Intent(getApplicationContext(), ScoringBoardActivity.class);
			Intent intentScoringBoardSample = new Intent(getApplicationContext(), SampleScoringBoardActivity.class);
			String playersSample = "toto,1;titi,4;tutu,6;tyty,3;tata,25;tete,12";
			intentScoringBoardSample.putExtra(CommonPlayBoard.TAG_NB_PLAYERS_TEAM_A, playersSample.split(";").length);
			intentScoringBoardSample.putExtra(CommonPlayBoard.TAG_PLAYERS_TEAM_A, playersSample);
			startActivity(intentScoringBoardSample);
			break;

//			case R.id.bt_club_mgt:
//			Log.d(TAG, "Launch Club Management");
//			_last_button = R.id.bt_club_mgt;
//	    	msg = String.format(getString(R.string.bt_club), getString(R.string.club_A));
//			displayNewAlertDialog(msg, R.string.bt_ok_club);
//			Intent intentClubList = new Intent(getApplicationContext(), ClubListActivity.class);
//			startActivity(intentClubList);
//			break;

//		case R.id.bt_team_mgt:
//			Log.d(TAG, "Launch Team Management");
//			Toast.makeText(getApplicationContext(), "Launch Team Management not yet implemented", Toast.LENGTH_LONG).show();
//			Intent intentTeamList = new Intent(getApplicationContext(), TeamListActivity.class);
//			startActivity(intentTeamList);
//			break;

//		case R.id.bt_category_mgt:
//			Log.d(TAG, "Launch Category Management");
//			Toast.makeText(getApplicationContext(), "Launch Team Management not yet implemented", Toast.LENGTH_LONG).show();
//			Intent intentCategoryList = new Intent(getApplicationContext(), CategoryListActivity.class);
//			startActivity(intentCategoryList);
//			break;

//		case R.id.bt_player_mgt:
//			Log.d(TAG, "Launch Player Management");
//			Toast.makeText(getApplicationContext(), "Launch Player Management not yet implemented", Toast.LENGTH_LONG).show();
//			Intent intentPlayerList = new Intent(getApplicationContext(), PlayerListActivity.class);
//			startActivity(intentPlayerList);
//			break;

		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// retrieve the given text
//		String txt = _dialog_et.getText().toString();
		// treatment depends on which button is pressed
		Log.d(TAG, "onClick with which="+which);
		if (which == DialogInterface.BUTTON_POSITIVE) {
//			switch (_last_button) {
//			case R.id.bt_club_mgt:
//				Log.d(TAG, "new name for ClubA:" + txt);
//				ScoreSheet.getInstance().set_club_name(0, txt);
//				_btClubA.setText(txt);
//				_btClubA.invalidate();
//				break;

//			case R.id.bt_club_B:
//				ScoreSheet.getInstance().set_club_name(0, txt);
//				_btClubB.setText(txt);
//				break;

//			default:
//				break;
//			}
		}
		
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "onItemClick on position "+position+" and id="+id);
		TextView club_text = (TextView) findViewById(android.R.id.text1);
		String club_name = club_text.getText().toString();
		Log.d(TAG, "onItemClick on club with name="+club_name);
		switch (view.getId()) {
		case R.id.spin_team_A:
			Log.d(TAG, "set club A with name="+club_name);
			ScoreSheet.getInstance().set_club_name(0, club_name);
			break;

		case R.id.spin_team_B:
			Log.d(TAG, "set club B with name="+club_name);
			ScoreSheet.getInstance().set_club_name(1, club_name);
			break;

		default:
			break;
		}
	}

}
