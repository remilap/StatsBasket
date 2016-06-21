/*
 * TODO
 * - ajouter une exclusion temporaire quand un joueur prend un carton rouge
 * - compter le nb de temps mort par période et par match
 * - enregistrer les avertissements et exclusions des joueurs
 * - enregistrer les événements
 */

package eu.remilapointe.statsbasket.activities.hand;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.activities.main.MainActivity;
import eu.remilapointe.statsbasket.timer.DisplayUpdatable;
import eu.remilapointe.statsbasket.timer.OneTimer;
import eu.remilapointe.statsbasket.timer.TimeHandler;
import eu.remilapointe.statsbasket.timer.TimerStateException;

/**
 * @author lapoint1
 *
 */
public class ScoringBoardPlayerActivity extends Activity implements OnClickListener, OnLongClickListener, DialogInterface.OnClickListener, DisplayUpdatable, OnItemClickListener {

	private static final String TAG = ScoringBoardPlayerActivity.class.getSimpleName();

	private static final int TIMER_MINUTES_INTO_SECONDS = 60;
	private static final int TIMER_DURATION_PERIOD_DEFAULT = 30;  // minutes
	private static final int TIMER_DURATION_PAUSE_DEFAULT = 1;    // minutes
	private static final int TIMER_DURATION_EXCLU_DEFAULT = 2;    // minutes
	private static final int TIMER_DURATION_INTER_DEFAULT = 5;    // minutes
	private static final int NB_PERIOD_DEFAULT = 2;
	private static final String EMPTY_EXCLU_PLAYER = ".";
	private static final int MAX_DISPLAY_EXCLU = 3;
	private static final int MAX_PLAYERS = 14;

	private static final int TIMER_ID_MATCH = 5;
	private static final int TIMER_ID_INTER = 6;
	private static final int TIMER_ID_PAUSE_NONE = 0;
	private static final int TIMER_ID_PAUSE_A = 1;
	private static final int TIMER_ID_PAUSE_B = 2;
	private static final int TIMER_ID_EXCLU_BEGIN = 10;

	private static final String[] CLUB_INDEX = {"A", "B"};

	private static final int ACTION_GOAL = 1;
	private static final int ACTION_STOP_GOAL = 2;
	private static final int ACTION_GOAL_7M = 3;
	private static final int ACTION_STOP_GOAL_7M = 4;
	private static final int ACTION_AVERT = 5;
	private static final int ACTION_EXCLU_TMP = 6;
	private static final int ACTION_EXCLU_DEF = 7;
	private static final int ACTION_DRIBBLE = 8;
	private static final int ACTION_WALK = 9;
	private static final int ACTION_ZONE = 10;
	private static final int ACTION_BALL_LOST = 11;

	protected ScoringBoardPlayerActivity _myActiv = null;
	protected TextView _time = null;
	protected Button _bt_start_stop = null;
	protected Button[] _bt_pause = new Button[2];
//	protected Button _bt_pause = null;
//	protected Button _bt_goal = null;
//	protected Button _bt_stop_goal = null;
//	protected Button _bt_goal_7m = null;
//	protected Button _bt_stop_goal_7m = null;
//	protected Button _bt_avert = null;
//	protected Button _bt_exclu_tmp = null;
//	protected Button _bt_exclu_def = null;
//	protected Button _bt_walk = null;
//	protected Button _bt_dribble = null;
//	protected Button _bt_zone = null;
//	protected Button _bt_ball_lost = null;
	protected List<Button> _bt_list = new ArrayList<Button>();
	protected int[] _id_bt_list = new int[] {
			R.id.bt_goal, R.id.bt_stop_goal, R.id.bt_goal_7m, R.id.bt_stop_goal_7m,
			R.id.bt_avert, R.id.bt_exclu_tmp, R.id.bt_exclu_def, R.id.bt_ball_lost,
			R.id.bt_walk, R.id.bt_dribble, R.id.bt_zone
	};
	protected TextView[] _txt_club = new TextView[2];
	protected TextView[] _txt_score = new TextView[2];
	protected TextView[] _txt_pause = new TextView[2];
	protected TextView _txt_period = null;
//	protected TextView[][] _txt_player_name = new TextView[2][MAX_PLAYERS];
//	protected TextView[][] _txt_player_num = new TextView[2][MAX_PLAYERS];
//	protected TextView[][] _txt_player_exclu = new TextView[2][MAX_PLAYERS];
	protected ListView[] _lv_players = new ListView[2];
	private EditText _et_dialog = null;
//	private ListView _l_view = null;
	private CharSequence[] _list1 = {"Foo", "Bar", "Baz"};


	private int[] _id_bt_pause = new int[] {R.id.bt_pause_A, R.id.bt_pause_B};
//	private int[] _id_bt_goal = new int[] {R.id.bt_goal_A, R.id.bt_goal_B};
//	private int[] _id_bt_stop_goal = new int[] {R.id.bt_stop_goal_A, R.id.bt_stop_goal_B};
//	private int[] _id_bt_goal_7m = new int[] {R.id.bt_goal_7m_A, R.id.bt_goal_7m_B};
//	private int[] _id_bt_stop_goal_7m = new int[] {R.id.bt_stop_goal_7m_A, R.id.bt_stop_goal_7m_B};
//	private int[] _id_bt_avert = new int[] {R.id.bt_avert_A, R.id.bt_avert_B};
//	private int[] _id_bt_exclu_tmp = new int[] {R.id.bt_exclu_tmp_A, R.id.bt_exclu_tmp_B};
//	private int[] _id_bt_exclu_def = new int[] {R.id.bt_exclu_def_A, R.id.bt_exclu_def_B};
	private int[] _id_txt_club = new int[] {R.id.txt_club_A, R.id.txt_club_B};
	private int[] _id_txt_score = new int[] {R.id.txt_score_A, R.id.txt_score_B};
	private int[] _id_txt_pause = new int[] {R.id.txt_pause_A, R.id.txt_pause_B};
//	private int[][] _id_txt_exclu_time = new int[][] {{R.id.txt_exclu_time_A1, R.id.txt_exclu_time_A2, R.id.txt_exclu_time_A3},{R.id.txt_exclu_time_B1, R.id.txt_exclu_time_B2, R.id.txt_exclu_time_B3}};
//	private int[][] _id_txt_exclu_player = new int[][] {{R.id.txt_exclu_player_A1, R.id.txt_exclu_player_A2, R.id.txt_exclu_player_A3},{R.id.txt_exclu_player_B1, R.id.txt_exclu_player_B2, R.id.txt_exclu_player_B3}};

	protected SharedPreferences myPrefs = null;

    private boolean _playingTime = false;
	private OneTimer _matchTimer = null;
	private OneTimer _pauseTimer = null;
	private OneTimer[][] _excluTimer = new OneTimer[2][MAX_DISPLAY_EXCLU];
	private OneTimer _interTimer = null;

	private int _timer_duration_period = TIMER_DURATION_PERIOD_DEFAULT * TIMER_MINUTES_INTO_SECONDS;
	private int _timer_duration_pause = TIMER_DURATION_PAUSE_DEFAULT * TIMER_MINUTES_INTO_SECONDS;
	private int _timer_duration_exclu = TIMER_DURATION_EXCLU_DEFAULT * TIMER_MINUTES_INTO_SECONDS;
	private int _timer_duration_inter = TIMER_DURATION_INTER_DEFAULT * TIMER_MINUTES_INTO_SECONDS;
	private int _nb_periods = NB_PERIOD_DEFAULT;

	private int _current_period = 1;
	private int _last_button = 0;
	private int _pauseTeamId = TIMER_ID_PAUSE_NONE;      // 1 => team A, 2 => team B
	protected boolean[] _init_club = new boolean[] {false, false};
	private int[] _pauseNb = new int[] {1, 1};
	private int[] _current_nb_exclu = new int[] {0, 0};
	private int[] _last_exclu_player_id = new int[] {0, 0};
	private String[][] _excluPlayer = new String[2][MAX_DISPLAY_EXCLU];
//	protected String[][] _txt_player_name = new String[2][MAX_PLAYERS];
//	protected String[][] _txt_player_num = new String[2][MAX_PLAYERS];
//	protected String[][] _txt_player_exclu = new String[2][MAX_PLAYERS];
	protected List<List<ScoringBoardPlayer>> _player_lines = new ArrayList<List<ScoringBoardPlayer>>(2);
	protected List<ScoringBoardPlayer> _player_A_lines = new ArrayList<ScoringBoardPlayer>();
	protected int _team_selected = -1;
	protected int _player_selected = -1;
	protected View _view_selected = null;
	protected int _action_selected = -1;
	protected List<ScoringBoardPlayer> _player_B_lines = new ArrayList<ScoringBoardPlayer>();
//	protected int _player_B_selected = -1;
//	protected View _view_B_selected = null;
	protected int _black = 0;
	protected int _gray = 0;
	protected int _white = 0;

	private TimeHandler _time_handler = TimeHandler.getInstance();
//	private AlertDialog.Builder _dialog_build = null;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring_board_players);

        _myActiv = this;
        _black = getResources().getColor(R.color.black);
        _gray = getResources().getColor(R.color.blue);
        _white = getResources().getColor(R.color.gray);
        _time = (TextView) findViewById(R.id.txt_time);

        _bt_start_stop = (Button) findViewById(R.id.bt_start_stop);
        _bt_list.add(_bt_start_stop);
        for (int i = 0; i < _id_bt_list.length; i++) {
        	Button bt_action = (Button) findViewById(_id_bt_list[i]);
            _bt_list.add(bt_action);
            unselectAction(bt_action);
        }
//        _bt_goal_7m = (Button) findViewById(R.id.bt_goal_7m);
//        _bt_list.add(_bt_goal_7m);
//        _bt_stop_goal = (Button) findViewById(R.id.bt_stop_goal);
//        _bt_list.add(_bt_stop_goal);
//        _bt_stop_goal_7m = (Button) findViewById(R.id.bt_stop_goal_7m);
//        _bt_list.add(_bt_stop_goal_7m);
//        _bt_avert = (Button) findViewById(R.id.bt_avert);
//        _bt_list.add(_bt_avert);
//        _bt_exclu_tmp = (Button) findViewById(R.id.bt_exclu_tmp);
//        _bt_list.add(_bt_exclu_tmp);
//        _bt_exclu_def = (Button) findViewById(R.id.bt_exclu_def);
//        _bt_list.add(_bt_exclu_def);
        _lv_players[0] = (ListView) findViewById(R.id.lv_players_A);
        _lv_players[1] = (ListView) findViewById(R.id.lv_players_B);

		for (int i = 0 ; i < 2 ; i++) {
            _bt_pause[i] = (Button) findViewById(_id_bt_pause[i]);
            _bt_list.add(_bt_pause[i]);
//            _bt_goal[i] = (Button) findViewById(_id_bt_goal[i]);
//            _bt_goal[i].setBackgroundResource(R.color.green);
//            _bt_list.add(_bt_goal[i]);
//            _bt_stop_goal[i] = (Button) findViewById(_id_bt_stop_goal[i]);
//            _bt_stop_goal[i].setBackgroundResource(R.color.green);
//            _bt_list.add(_bt_stop_goal[i]);
//            _bt_goal_7m[i] = (Button) findViewById(_id_bt_goal_7m[i]);
//            _bt_goal_7m[i].setBackgroundResource(R.color.green);
//            _bt_list.add(_bt_goal_7m[i]);
//            _bt_stop_goal_7m[i] = (Button) findViewById(_id_bt_stop_goal_7m[i]);
//            _bt_stop_goal_7m[i].setBackgroundResource(R.color.green);
//            _bt_list.add(_bt_stop_goal_7m[i]);
//            _bt_avert[i] = (Button) findViewById(_id_bt_avert[i]);
//            _bt_avert[i].setBackgroundResource(R.color.yellow);
//            _bt_list.add(_bt_avert[i]);
//            _bt_exclu_tmp[i] = (Button) findViewById(_id_bt_exclu_tmp[i]);
//            _bt_exclu_tmp[i].setBackgroundResource(R.color.orange);
//            _bt_list.add(_bt_exclu_tmp[i]);
//            _bt_exclu_def[i] = (Button) findViewById(_id_bt_exclu_def[i]);
//            _bt_exclu_def[i].setBackgroundResource(R.color.red);
//            _bt_list.add(_bt_exclu_def[i]);
            _txt_club[i] = (TextView) findViewById(_id_txt_club[i]);
            _txt_score[i] = (TextView) findViewById(_id_txt_score[i]);
            _txt_pause[i] = (TextView) findViewById(_id_txt_pause[i]);

//            for (int j = 0 ; j < MAX_DISPLAY_EXCLU ; j++) {
//                _txt_exclu_time[i][j] = (TextView) findViewById(_id_txt_exclu_time[i][j]);
//                _txt_exclu_player[i][j] = (TextView) findViewById(_id_txt_exclu_player[i][j]);
//            }
        }

        for (int p = 0; p < MAX_PLAYERS; p++) {
//        	TextView tv = new TextView(this.getApplicationContext());
        	_player_A_lines.add( new ScoringBoardPlayer("A toto", String.valueOf(p*2+1), ".") );
        	_player_B_lines.add( new ScoringBoardPlayer("B tata", String.valueOf(p*2+2), ".") );
        }
        _player_lines.add(_player_A_lines);
        _player_lines.add(_player_B_lines);

        ScoringPlayersListAdapter players_A_Adapt = new ScoringPlayersListAdapter(getApplicationContext(), 1, R.layout.scoring_player_a_list_item, _player_A_lines);
//		players_A_Adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _lv_players[0].setAdapter(players_A_Adapt);
        _lv_players[0].setOnItemClickListener(this);
        ScoringPlayersListAdapter players_B_Adapt = new ScoringPlayersListAdapter(getApplicationContext(), 2, R.layout.scoring_player_b_list_item, _player_B_lines);
//		players_B_Adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _lv_players[1].setAdapter(players_B_Adapt);
        _lv_players[1].setOnItemClickListener(this);

		for (int i = 0; i < _bt_list.size(); i++) {
			_bt_list.get(i).setOnClickListener(this);
			_bt_list.get(i).setOnLongClickListener(this);
		}

		findViewById(R.id.txt_club_A).setOnClickListener(this);
        findViewById(R.id.txt_club_B).setOnClickListener(this);
        findViewById(R.id.bt_reset).setOnClickListener(this);
        findViewById(R.id.bt_exit).setOnClickListener(this);

        _txt_period = (TextView) findViewById(R.id.txt_period);
        _txt_period.setText(String.valueOf(_current_period));

        if (savedInstanceState != null) {
        	Log.d(TAG, "onCreate with savedInstanceState not null !");
        	_matchTimer = OneTimer.getTimerById(TIMER_ID_MATCH);
        	if (_matchTimer == null) {
        		Log.d(TAG, "unable to retrieve the matchTimer");
        	} else {
        		Log.d(TAG, "retrieve matchTimer with current value: "+_matchTimer.getCurrentSeconds()+" seconds");
        	}
//            _min = savedInstanceState.getInt("min_val", NombreAleatoire.MIN_DEFAULT);
//            _max = savedInstanceState.getInt("max_val", NombreAleatoire.MAX_DEFAULT);
        } else {
        	Log.d(TAG, "onCreate with no savedInstanceState !");
    		// Retrieve current values from preferences
            myPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            _timer_duration_period = getPrefDurationInSeconds("pref_period_duration", TIMER_DURATION_PERIOD_DEFAULT);
//        	_timer_duration_period = 180; //TIMER_DURATION_PERIOD_DEFAULT;
			_timer_duration_pause = getPrefDurationInSeconds("pref_pause_duration", TIMER_DURATION_PAUSE_DEFAULT);
//        	_timer_duration_pause = 20; //TIMER_DURATION_PAUSE_DEFAULT;
			_timer_duration_exclu = getPrefDurationInSeconds("pref_exclu_duration", TIMER_DURATION_EXCLU_DEFAULT);
//        	_timer_duration_exclu = 30; //TIMER_DURATION_EXCLU_DEFAULT;
			_timer_duration_inter = getPrefDurationInSeconds("pref_inter_duration", TIMER_DURATION_INTER_DEFAULT);
//        	_timer_duration_inter = 90; //TIMER_DURATION_INTER_DEFAULT;
			_nb_periods = getIntPrefValue("pref_nb_period", NB_PERIOD_DEFAULT);

            // enable only the Start button
    		setAllButton(false);
            setOneButton(_bt_start_stop, true);

        	// create all timers
            _matchTimer = new OneTimer(TIMER_ID_MATCH, this, OneTimer.DIRECTION_UP, _timer_duration_period);
        	_pauseTimer = new OneTimer(TIMER_ID_PAUSE_NONE, this, OneTimer.DIRECTION_DOWN, _timer_duration_pause);
        	for (int i = 0; i < 2; i++) {
            	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
        			_excluTimer[i][j] = new OneTimer(TIMER_ID_EXCLU_BEGIN + i*MAX_DISPLAY_EXCLU + j, this, OneTimer.DIRECTION_DOWN, _timer_duration_exclu);
        			_excluPlayer[i][j] = EMPTY_EXCLU_PLAYER;
        		}
    		}
        	_interTimer = new OneTimer(TIMER_ID_INTER, this, OneTimer.DIRECTION_DOWN, _timer_duration_inter);
        }

        _time_handler.startTimeHandler();

    }

    /* (non-Javadoc)
     * @see eu.remilapointe.statsbasket.timer.DisplayUpdatable#getTimeHandler()
     */
    @Override
	public TimeHandler getTimeHandler() {
    	return _time_handler;
    }

    /**
     * getIntPrefValue
     * @param pref_name
     * @param def_value
     * @return
     * returns int
     */
    private int getIntPrefValue(String pref_name, int def_value) {
		String pref_value = myPrefs.getString(pref_name, String.valueOf(def_value));
		int res = def_value;
    	try {
			res = Integer.parseInt(pref_value);
		} catch (NumberFormatException e4) {
			res = def_value;
		}
    	Log.d(TAG, "getIntPrefValue(\""+pref_name+"\", "+def_value+") returns "+res);
		return res;
    }

    /**
     * getPrefDurationInSeconds
     * @param pref_name
     * @param def_value
     * @return
     * returns int
     */
    private int getPrefDurationInSeconds(String pref_name, int def_value) {
		int res = getIntPrefValue(pref_name, def_value) * TIMER_MINUTES_INTO_SECONDS;
		return res;
    }

    /*
	 *                    activity starts
	 *                          |
	 *                      onCreate()
	 *                          |
	 *                          v
	 *                    +-----------+
	 *                    | Created   |
	 *                    +-----------+
	 *                          |
	 *                       onStart()
	 *                          |
	 *                          v
	 *                    +-----------+
	 *      +------------>| Started   |
	 *      |             | (visible) |
	 *      |             +-----------+
	 *  onStart()               |
	 *      ^               onResume()
	 *      |                   |
	 *      |                   v
	 *      |             +-----------+
	 *      |             | Resumed   |<---------------------+
	 *      |             | (visible) |                      |
	 *      |             +-----------+                      |
	 *      |                   |                       onResume()
	 *      |               onPause()                        ^
	 *      |                   |                            |
	 *      |                   v                            |
	 *      |             +-----------+                      |
	 *      |             |  Paused   |                      |
	 *      |             | (partially|--------------------->+
	 *      |             |  visible) |
	 *      |             +-----------+
	 *      |                   |
	 *  onRestart()         onStop()
	 *      ^                   |
	 *      |                   v
	 *      |             +-----------+
	 *      |             |  Stopped  |
	 *      +<------------|  (hidden) |
	 *                    +-----------+
	 *                          |
	 *                      onDestroy()
	 *                          |
	 *                          v
	 *                    +-----------+
	 *                    | Destroyed |
	 *                    +-----------+
	 */

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	Log.d(TAG, "onSaveInstanceState");
        outState.putInt("current_period", _current_period);
        outState.putBoolean("playing_time", _playingTime);
        // save state of all timers
        outState.putString("matchTimer", _matchTimer.saveToString());
        outState.putString("pauseTimer", _pauseTimer.saveToString());
    	outState.putString("interTimer", _interTimer.saveToString());
    	for (int i = 0; i < 2; i++) {
            outState.putInt("current_nb_exclu_"+i, _current_nb_exclu[i]);
            outState.putString("score_"+i, _txt_score[i].getText().toString());
            outState.putString("club_"+i, _txt_club[i].getText().toString());
        	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
        		outState.putString("excluTimer_"+i+"_"+j, _excluTimer[i][j].saveToString());
        		outState.putString("excluPlayer_"+i+"_"+j, _excluPlayer[i][j]);
    		}
		}

    	super.onSaveInstanceState(outState);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        _current_period = savedInstanceState.getInt("current_period");
        _playingTime = savedInstanceState.getBoolean("playing_time");
        if (_playingTime) {
        	_bt_start_stop.setText(R.string.bt_stop);
        } else {
        	_bt_start_stop.setText(R.string.bt_start);
        }
        _matchTimer = OneTimer.OneTimerFromString(this, savedInstanceState.getString("matchTimer"));
        _pauseTimer = OneTimer.OneTimerFromString(this, savedInstanceState.getString("pauseTimer"));
        _interTimer = OneTimer.OneTimerFromString(this, savedInstanceState.getString("interTimer"));
    	for (int i = 0; i < 2; i++) {
            _current_nb_exclu[i] = savedInstanceState.getInt("current_nb_exclu_"+i);
            _txt_score[i].setText(savedInstanceState.getString("score_"+i));
            _txt_club[i].setText(savedInstanceState.getString("club_"+i));
            _init_club[i] = true;
            // restore all timers
        	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
        		_excluTimer[i][j] = OneTimer.OneTimerFromString(this, savedInstanceState.getString("excluTimer_"+i+"_"+j));
        		_excluPlayer[i][j] = savedInstanceState.getString("excluPlayer_"+i+"_"+j);
        		if (! EMPTY_EXCLU_PLAYER.equals(_excluPlayer[i][j])) {
//        			_txt_exclu_player[i][j].setText(_excluPlayer[i][j]);
        		}
    		}
		}
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
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
    	Log.d(TAG, "onResume");
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
    	Log.d(TAG, "onPause");
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
    	Log.d(TAG, "onRestart");
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
    	Log.d(TAG, "onDestroy");
        _time_handler.stopTimeHandler();
		super.onDestroy();
	}


	/* (non-Javadoc)
     * @see eu.remilapointe.statsbasket.timer.DisplayUpdatable#getName()
     */
    @Override
	public String getName() {
    	return TAG;
    }

    /**
     * setAllButton
     * @param enable
     * returns void
     */
    protected void setAllButton(boolean enable) {
    	for (int i = 0; i < _bt_list.size(); i++) {
			setOneButton(_bt_list.get(i), enable);
		}
    }

    /**
     * setOneButton
     * @param bt
     * @param enable
     * returns void
     */
    protected void setOneButton(Button bt, boolean enable) {
//    	bt.setClickable(enable);
    	bt.setEnabled(enable);
    }

	/**
	 * displayNewAlertDialog
	 * @param resIdTitle
	 * @param resIdText
	 * returns void
	 */
	private void displayNewAlertDialog(String stTitle, int resIdText, String stMessage) {
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
		_et_dialog = (EditText) alertDialogView.findViewById(R.id.dialog_input);

		// title
		dialog_build.setTitle(stTitle);
		dialog_build.setMessage(stMessage);
		dialog_build.setPositiveButton(resIdText, this);
		dialog_build.setNegativeButton(R.string.bt_cancel, this);
		dialog_build.show();
	}

	/**
	 * displayNewAlertDialog
	 * @param resIdTitle
	 * @param resIdText
	 * returns void
	 */
	private void displayListDialog(String stTitle, String stMessage) {
		// Layout instantiation as a view
		LayoutInflater factory = LayoutInflater.from(this);
		final View alertDialogView = factory.inflate(R.layout.dialog_input_list, null);

		AlertDialog.Builder _dialog_build = new AlertDialog.Builder(this);

		// affectation with the custom view
		_dialog_build.setView(alertDialogView);

		// icon
		_dialog_build.setIcon(android.R.drawable.ic_dialog_alert);

		// list
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < _list1.length; i++) {
			sb.append(_list1[i]);
			sb.append(", ");
		}
		Log.d(TAG, "dialog list: " + sb.toString());
		_dialog_build.setSingleChoiceItems(_list1, -1, this);
//		_dialog_build.setItems(_list1, this);

		// retrieve the ListView for input
//		_l_view = (ListView) findViewById(R.id.list_input);
//		_et_dialog = (EditText) alertDialogView.findViewById(R.id.dialog_input);

		// title
		_dialog_build.setTitle(stTitle);
		_dialog_build.setMessage(stMessage);
//		_dialog_build.setPositiveButton(resIdText, this);
//		_dialog_build.setNegativeButton(R.string.bt_cancel, this);
		_dialog_build.create();
		_dialog_build.show();
	}

	/**
	 * setClubName
	 * @param c
	 * @param txt
	 * returns void
	 */
	private void setClubName(int c, String txt) {
		Log.d(TAG, "new name for Club " + c + ":" + txt);
//		ScoreSheet.getInstance().set_club_name(c, txt);
		_txt_club[c].setText(txt);
		_init_club[c] = true;
//		_txt_club_A.invalidate();
	}

	/**
	 * setExcluPlayer
	 * @param c
	 * @param txt
	 * returns void
	 */
	private void setExcluPlayer(int c, String txt) {
		Log.d(TAG, "new name for exclusion team " + c + ":" + txt + " with id="+_last_exclu_player_id[c]);
		_excluPlayer[c][_last_exclu_player_id[c]] = txt;
//		_txt_exclu_player[c][_last_exclu_player_id[c]].setText(txt);
	}

	/**
	 * onClick for DialogInterface (club name)
	 * @param dialog
	 * @param which
	 * returns void
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// retrieve the given text
		String txt = null;
		// treatment depends on which button is pressed
		Log.d(TAG, "onClick with which="+which+" and id="+_last_button);
		switch (_last_button) {
		case R.id.txt_club_A:
			if (which == DialogInterface.BUTTON_POSITIVE) {
				txt = _et_dialog.getText().toString();
				setClubName(0, txt);
			}
			break;

		case R.id.txt_club_B:
			if (which == DialogInterface.BUTTON_POSITIVE) {
				txt = _et_dialog.getText().toString();
				setClubName(1, txt);
			}
			break;

		case R.id.bt_exclu_tmp_A:
//			txt = _et_dialog.getText().toString();
			dialog.dismiss();
			txt = (String) _list1[which];
			setExcluPlayer(0, txt);
			break;

		case R.id.bt_exclu_tmp_B:
//			txt = _et_dialog.getText().toString();
			dialog.dismiss();
			txt = (String) _list1[which];
			setExcluPlayer(1, txt);
			break;

		default:
			break;
		}
	}

	/**
	 * onClick for the different buttons
	 * @param view
	 * returns void
	 */
	@Override
	public void onClick(View view) {
		String title = null;
		String msg = null;
		switch (view.getId()) {
		case R.id.bt_exit:
			stopMatchTimer();
			stopPauseTimer();
			stopAllExcluTimer();
	        MainActivity.askConfirm(MainActivity.REQ_CONFIRM_QUIT, _myActiv, MainActivity.getInstance(), getString(R.string.quit_confirm_st), getString(R.string.confirm_st));
			break;

		case R.id.bt_start_stop:
			if (! (_init_club[0] && _init_club[1]) ) {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    	builder.setMessage(R.string.txt_club_non_init)
		    	       .setCancelable(false)
		    	       .setPositiveButton(R.string.yes_st, new DialogInterface.OnClickListener() {
		    	           @Override
						public void onClick(DialogInterface dialog, int id) {
		    	        	   _init_club[0] = true;
		    	        	   _init_club[1] = true;
			   					setAllButton(true);
								startMatchTimer();
								startAllExcluTimer();
		    	                dialog.cancel();
		    	           }
		    	       })
		    	       .setNegativeButton(R.string.no_st, new DialogInterface.OnClickListener() {
		    	           @Override
						public void onClick(DialogInterface dialog, int id) {
		    	                dialog.cancel();
		    	           }
		    	       });
		    	AlertDialog alert = builder.create();
		    	alert.show();
			} else {
				if (_playingTime) {
					pauseMatchTimer();
					pauseAllExcluTimer();
				} else {
					setAllButton(true);
					startMatchTimer();
					startAllExcluTimer();
				}
			}
			break;

		case R.id.txt_club_A:
			_last_button = view.getId();
	    	title = String.format(getString(R.string.txt_club_title), getString(R.string.club_A));
	    	msg = String.format(getString(R.string.txt_give_club), getString(R.string.club_A));
			displayNewAlertDialog(title, R.string.bt_ok_club, msg);
			break;

		case R.id.txt_club_B:
			_last_button = view.getId();
	    	title = String.format(getString(R.string.txt_club_title), getString(R.string.club_B));
	    	msg = String.format(getString(R.string.txt_give_club), getString(R.string.club_B));
			displayNewAlertDialog(title, R.string.bt_ok_club, msg);
			break;

		case R.id.bt_pause_A:
			if (_pauseTeamId == TIMER_ID_PAUSE_NONE) {
				pauseMatchTimer();
				pauseAllExcluTimer();
				setAllButton(false);
				setOneButton(_bt_pause[0], true);
				_pauseTeamId = TIMER_ID_PAUSE_A;
				startPauseTimer();
			} else {
				stopPauseTimer();
				endMaxDuration(TIMER_ID_PAUSE_A);
			}
			break;

		case R.id.bt_pause_B:
			if (_pauseTeamId == TIMER_ID_PAUSE_NONE) {
				pauseMatchTimer();
				pauseAllExcluTimer();
				setAllButton(false);
				setOneButton(_bt_pause[1], true);
				_pauseTeamId = TIMER_ID_PAUSE_B;
				startPauseTimer();
			} else {
				stopPauseTimer();
				endMaxDuration(TIMER_ID_PAUSE_B);
			}
			break;

		case R.id.bt_goal:
			manageAction(ACTION_GOAL, view);
			break;

		case R.id.bt_stop_goal:
			manageAction(ACTION_STOP_GOAL, view);
			break;

		case R.id.bt_goal_7m:
			manageAction(ACTION_GOAL_7M, view);
			break;

		case R.id.bt_stop_goal_7m:
			manageAction(ACTION_STOP_GOAL_7M, view);
			break;

		case R.id.bt_avert:
			manageAction(ACTION_AVERT, view);
			break;

		case R.id.bt_exclu_tmp:
			manageAction(ACTION_EXCLU_TMP, view);
			break;

		case R.id.bt_exclu_def:
			manageAction(ACTION_EXCLU_TMP, view);
			break;

		case R.id.bt_reset:
			stopMatchTimer();
			Log.d(TAG, "reset so duration: "+_matchTimer.getCurrentSeconds());
			_time.setText("00:00");
			break;

		default:
			break;
		}
		
	}

	/**
	 * setScore
	 * @param team
	 * returns void
	 */
	private void setScore(int team, int add) {
		String st_score = _txt_score[team].getText().toString();
		try {
			int score = Integer.parseInt(st_score);
			st_score = String.valueOf(score + add);
			if (score + add >= 0) {
				_txt_score[team].setText(st_score);
			}
		} catch (NumberFormatException e) {
			Log.e(TAG, "unable to retrieve the score " + team);
		}
	}

	/**
	 * setExcluTmp
	 * @param c
	 * returns void
	 */
	private void setExcluTmp(int team) {
		int exclu = getFirstFreeExcluTeam(team);
		_last_exclu_player_id[team] = exclu;
		_excluPlayer[team][exclu] = CLUB_INDEX[team]+CLUB_INDEX[team];
		selectPlayer(team);
		setCurrentCounter(TIMER_ID_EXCLU_BEGIN + team*MAX_DISPLAY_EXCLU + exclu, _excluTimer[team][exclu].getCurrentSeconds());
		startExcluTimer(team, exclu);
		_current_nb_exclu[team]++;
	}

	private void selectPlayer(int team) {
    	String title = String.format(getString(R.string.bt_exclu_tmp), CLUB_INDEX[team]);
    	String msg = String.format(getString(R.string.txt_exclu_tmp), CLUB_INDEX[team]);
//		displayNewAlertDialog(title, R.string.bt_ok_club, msg);
		displayListDialog(title, msg);
	}

	/**
	 * onLongClick
	 * @param v
	 * @return
	 * returns boolean
	 */
	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.bt_score_A:
			setScore(0, -1);
			break;

		case R.id.bt_score_B:
			setScore(1, -1);
			break;

		default:
			break;
		}
		return true;
	}

	/**
	 * getFirstFreeExcluTeamA
	 * @return
	 * returns int
	 */
	private int getFirstFreeExcluTeam(int team) {
		if (_current_nb_exclu[team] == 0) {
			return 0;
		}
		for (int i = 0; i < MAX_DISPLAY_EXCLU; i++) {
			if (EMPTY_EXCLU_PLAYER.equals(_excluPlayer[team][i])) {
				return i;
			}
		}
		return -1;
	}

	/**
     * startTimer
     * returns void
     */
    protected void startMatchTimer() {
    	Log.d(TAG, "startMatchTimer with current duration="+_matchTimer.getCurrentSeconds()+" seconds");
		try {
			if (_matchTimer.isPaused()) {
				_matchTimer.restartTimer();
			} else {
				_matchTimer.startTimer();
			}
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
		_bt_start_stop.setText(R.string.bt_stop);
		_playingTime = true;
    }

    /**
     * stopTimer
     * returns void
     */
    protected void pauseMatchTimer() {
    	Log.d(TAG, "pauseMatchTimer with current duration="+_matchTimer.getCurrentSeconds()+" seconds");
		try {
			_matchTimer.pauseTimer();
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
		_bt_start_stop.setText(R.string.bt_start);
		_playingTime = false;
    }

    /**
     * stopTimer
     * returns void
     */
    protected void stopMatchTimer() {
    	Log.d(TAG, "stopMatchTimer with current duration="+_matchTimer.getCurrentSeconds()+" seconds");
		try {
			_matchTimer.stopTimer();
			_matchTimer.resetTimer();
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
//		_bt_start_stop.setText(R.string.bt_start);
    }

    /**
     * startPauseTimer
     * returns void
     */
    protected void startPauseTimer() {
    	Log.d(TAG, "startPauseTimer with team="+_pauseTeamId+" and current duration="+_pauseTimer.getCurrentSeconds()+" seconds");
    	_pauseTimer.setId(_pauseTeamId);
    	try {
    		if (_pauseTimer.isPaused()) {
    			_pauseTimer.restartTimer();
    		} else {
    			_pauseTimer.startTimer();
    		}
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
    }

    /**
     * stopPauseTimer
     * returns void
     */
    protected void stopPauseTimer() {
    	Log.d(TAG, "stopPauseTimer with team="+_pauseTeamId+" and current duration="+_pauseTimer.getCurrentSeconds()+" seconds");
    	try {
			_pauseTimer.stopTimer();
	    	_pauseTimer.resetTimer();
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
    	_pauseTeamId = TIMER_ID_PAUSE_NONE;
    }

    /**
     * startExcluTimer
     * @param numExclu
     * returns void
     */
    protected void startExcluTimer(int team, int numExclu) {
    	Log.d(TAG, "startExcluTimer with num="+numExclu+" and current duration="+_excluTimer[team][numExclu].getCurrentSeconds()+" seconds");
    	try {
    		if (_excluTimer[team][numExclu].isPaused()) {
    			_excluTimer[team][numExclu].restartTimer();
    		} else {
    			_excluTimer[team][numExclu].startTimer();
    			if (! _playingTime) {
    				_excluTimer[team][numExclu].pauseTimer();
    			}
    		}
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
    }

    /**
     * pauseExcluTimer
     * @param numExclu
     * returns void
     */
    protected void pauseExcluTimer(int team, int numExclu) {
    	Log.d(TAG, "pauseExcluTimer with num="+numExclu+" and current duration="+_excluTimer[team][numExclu].getCurrentSeconds()+" seconds");
    	try {
    		if (_excluTimer[team][numExclu].isStarted()) {
    			_excluTimer[team][numExclu].pauseTimer();
    		}
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
    }

    /**
     * stopExcluTimer
     * @param numExclu
     * returns void
     */
    protected void stopExcluTimer(int team, int numExclu) {
    	Log.d(TAG, "stopExcluTimer with num="+numExclu+" and current duration="+_excluTimer[team][numExclu].getCurrentSeconds()+" seconds");
    	try {
    		if (_excluTimer[team][numExclu].isStarted()) {
    			_excluTimer[team][numExclu].stopTimer();
    	    	_excluTimer[team][numExclu].resetTimer();
    		}
		} catch (TimerStateException e) {
			Log.e(TAG, e.getMessage());
		}
    }

    /**
     * startAllExcluTimer
     * returns void
     */
    protected void startAllExcluTimer() {
    	for (int i = 0; i < 2; i++) {
        	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
        		if (_excluTimer[i][j].isPaused()) {
        			startExcluTimer(i, j);
        		}
    		}
		}
    }

    /**
     * pauseAllExcluTimer
     * returns void
     */
    protected void pauseAllExcluTimer() {
    	for (int i = 0; i < 2; i++) {
        	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
        		if (_excluTimer[i][j].isStarted()) {
        			pauseExcluTimer(i, j);
        		}
    		}
		}
    }

    /**
     * stopAllExcluTimer
     * returns void
     */
    protected void stopAllExcluTimer() {
    	for (int i = 0; i < 2; i++) {
        	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
    			stopExcluTimer(i, j);
    		}
		}
    }

	/**
	 * setCurrentDuration
	 * @param seconds
	 * returns void
	 */
	@Override
	public void setCurrentCounter(int id, int duration) {
//		Log.d(TAG, "setCurrentDuration with id="+id+", seconds="+duration);
		int minutes = duration / 60;
		int seconds = duration % 60;
		String display = String.format("%02d", Integer.valueOf(minutes)) + ":" + String.format("%02d", Integer.valueOf(seconds));
		if (id == TIMER_ID_MATCH) {
			_time.setText(display);
		} else if (id == TIMER_ID_INTER) {
			_time.setText(display);
		} else if (id == TIMER_ID_PAUSE_A) {
			_txt_pause[0].setText(display);
		} else if (id == TIMER_ID_PAUSE_B) {
			_txt_pause[1].setText(display);
		} else if (id >= TIMER_ID_EXCLU_BEGIN && id < TIMER_ID_EXCLU_BEGIN + MAX_DISPLAY_EXCLU) {
//			_txt_exclu_time[0][id-TIMER_ID_EXCLU_BEGIN].setText(display);
		} else if (id >= TIMER_ID_EXCLU_BEGIN + MAX_DISPLAY_EXCLU && id < TIMER_ID_EXCLU_BEGIN + MAX_DISPLAY_EXCLU*2) {
//			_txt_exclu_time[1][id-TIMER_ID_EXCLU_BEGIN-MAX_DISPLAY_EXCLU].setText(display);
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.DisplayUpdatable#endMaxDuration(int)
	 */
	@Override
	public void endMaxDuration(int id) {
		Log.d(TAG, "endMaxDuration for id="+id);
		if (id == TIMER_ID_MATCH) {
			// end of period
			try {
				_matchTimer.stopTimer();
			} catch (TimerStateException e1) {
				Log.e(TAG, e1.getMessage());
			}
			_playingTime = false;
			setCurrentCounter(id, 0);
			setAllButton(false);
			pauseAllExcluTimer();
			if (_current_period < _nb_periods) {
				_bt_start_stop.setText(R.string.bt_start);
				_current_period++;
				_txt_period.setText(String.valueOf(_current_period));
				try {
					_matchTimer.resetTimer();
					_interTimer.startTimer();
				} catch (TimerStateException e2) {
					Log.e(TAG, e2.getMessage());
				}
			} else {
				_bt_start_stop.setText(R.string.bt_end);
			}
		} else if (id == TIMER_ID_INTER) {
			try {
				_interTimer.stopTimer();
				_interTimer.resetTimer();
			} catch (TimerStateException e) {
				Log.e(TAG, e.getMessage());
			}
			setOneButton(_bt_start_stop, true);
		} else if (id == TIMER_ID_PAUSE_A) {
			stopPauseTimer();
			_pauseNb[0]++;
			_bt_pause[0].setText("T"+String.valueOf(_pauseNb[0]));
			setCurrentCounter(id, 0);
			setAllButton(true);
		} else if (id == TIMER_ID_PAUSE_B) {
			stopPauseTimer();
			_pauseNb[1]++;
			_bt_pause[1].setText("T"+String.valueOf(_pauseNb[1]));
			setCurrentCounter(id, 0);
			setAllButton(true);
		} else if (id >= TIMER_ID_EXCLU_BEGIN && id < TIMER_ID_EXCLU_BEGIN + MAX_DISPLAY_EXCLU) {
			// end of exclusion A
			stopExcluTimer(0, id - TIMER_ID_EXCLU_BEGIN);
			setCurrentCounter(id, 0);
			_excluPlayer[0][id - TIMER_ID_EXCLU_BEGIN] = EMPTY_EXCLU_PLAYER;
//			_txt_exclu_player[0][id - TIMER_ID_EXCLU_BEGIN].setText(EMPTY_EXCLU_PLAYER);
			_current_nb_exclu[0]--;
		} else if (id >= TIMER_ID_EXCLU_BEGIN + MAX_DISPLAY_EXCLU && id < TIMER_ID_EXCLU_BEGIN + MAX_DISPLAY_EXCLU*2) {
			// end of exclusion B
			stopExcluTimer(1, id - TIMER_ID_EXCLU_BEGIN);
			setCurrentCounter(id, 1);
			_excluPlayer[1][id - TIMER_ID_EXCLU_BEGIN] = EMPTY_EXCLU_PLAYER;
//			_txt_exclu_player[1][id - TIMER_ID_EXCLU_BEGIN].setText(EMPTY_EXCLU_PLAYER);
			_current_nb_exclu[1]--;
		}
	}

	/**
	 * manageAction
	 * @param action
	 * @param view
	 * @return
	 */
	private boolean manageAction(int action, View view) {
		Log.d(TAG, "manageAction(" + action + "), _team_selected=" + _team_selected + ", _player_selectedd=" + _player_selected);
		if (_team_selected >= 0) {
			// one player is selected => prepare a new record
			prepareRecord(action, _team_selected, _player_selected);
			switch (action) {
			case ACTION_GOAL:
				setScore(_team_selected, 1);
				break;
			case ACTION_GOAL_7M:
				setScore(_team_selected, 1);
				break;
			case ACTION_STOP_GOAL:
				break;
			case ACTION_STOP_GOAL_7M:
				break;
			case ACTION_AVERT:
				// yellow card for selected player
				break;
			case ACTION_EXCLU_TMP:
				// temporary exclusion for selected player
				_last_button = view.getId();
				setExcluTmp(_team_selected);
				break;
			case ACTION_EXCLU_DEF:
				// definitive exclusion for selected player
				_last_button = view.getId();
				setExcluTmp(_team_selected);
				break;
			case ACTION_WALK:
				break;
			case ACTION_DRIBBLE:
				break;
			case ACTION_ZONE:
				break;
			case ACTION_BALL_LOST:
				break;

			default:
				break;
			}
			unselectPlayer(_view_selected);
			unselectAction(view);
			return true;
		}
		// no current player
		if (_action_selected < 0) {
			// no current action => new action selected
			setSelectedAction(action, view);
		} else if (_action_selected == action) {
			// same selected action => unselect this action
			unselectAction(_view_selected);
		} else {
			// different previous selected action => unselect this action
			unselectAction(_view_selected);
			// one new current action => new action selected
			setSelectedAction(action, view);
		}
		return false;
	}

	/**
	 * setSelectedAction
	 * @param action
	 * @param view
	 */
	private void setSelectedAction(int action, View view) {
		((Button)view).setBackgroundColor(_gray);
		_action_selected = action;
		_view_selected = view;
	}

	/**
	 * unselectAction
	 * @param view
	 */
	private void unselectAction(View view) {
		((Button)view).setBackgroundColor(_white);
		_action_selected = -1;
		_view_selected = null;
	}

	/**
	 * setSelectedPlayer
	 * @param team
	 * @param position
	 * @param view
	 */
	private void setSelectedPlayer(int team, int position, View view) {
		view.setBackgroundColor(_gray);
		_team_selected = team;
		_player_selected = position;
		_view_selected = view;
	}

	/**
	 * unselectPlayer
	 * @param view
	 */
	private void unselectPlayer(View view) {
		view.setBackgroundColor(_black);
		_team_selected = -1;
		_player_selected = -1;
		_view_selected = null;
	}

	/**
	 * prepareRecord
	 * @param action
	 * @param team
	 * @param position
	 */
	private void prepareRecord(int action, int team, int position) {
		Log.d(TAG, "prepareRecord(" + action + ", " + team + ", " + position + ")");
		
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
		final ScoringBoardPlayer item = (ScoringBoardPlayer) parent.getItemAtPosition(position);
//		Log.d(TAG, "item selected: " + item.get_player_num() + ", parent view: " + parent.getId() + ", lvA: " + _lv_players[0].getId() + ", lvB: " + _lv_players[1].getId());
		for (int t = 0; t < 2; t++) {
			if (parent.getId() == _lv_players[t].getId()) {
				if (_player_selected < 0) {
					Log.d(TAG, "team " + t + ": no current player selected, new selected: " + position);
					if (_action_selected < 0) {
						// no current action selected => show selected player
						setSelectedPlayer(t, position, view);
					} else {
						// one action is selected => prepare the record
						_team_selected = t;
						_player_selected = position;
						manageAction(_action_selected, _view_selected);
//						prepareRecord(_action_selected, t, position);
					}
				} else if ( (_player_selected == position) && (_team_selected == t) ) {
					Log.d(TAG, "team " + t + ": current player " + _player_selected + " selected, same selected => unselect");
					unselectPlayer(view);
				} else {
					Log.d(TAG, "team " + t + ": current player " + _player_selected + " selected, new selected: " + position);
					// ancienne position en noir
					unselectPlayer(_view_selected);
					// nouvelle position en gris
					setSelectedPlayer(t, position, view);
				}
			}
		}
	}

}
