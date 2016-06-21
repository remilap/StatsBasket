/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.activities.management.DialogReturnable;
import eu.remilapointe.statsbasket.common.play.CommonPlayBoard;
import eu.remilapointe.statsbasket.common.play.CommonGraphicalPlayer;
import eu.remilapointe.statsbasket.common.play.android.EventReceiver;
import eu.remilapointe.statsbasket.common.play.android.GraphicalButton;
import eu.remilapointe.statsbasket.common.play.android.GraphicalTextView;
import eu.remilapointe.statsbasket.common.play.android.GraphicalTimer;
import eu.remilapointe.statsbasket.sport.AndroidLog;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class BasketScoringBoardActivity extends Activity implements DialogReturnable {

	private static final String TAG = BasketScoringBoardActivity.class.getSimpleName();

	private Loggable _log = new AndroidLog();
	private BasketPlayBoard _game = null;
	protected static BasketScoringBoardActivity _myActiv = null;
	private long _lastTimeKeyUp = 0;

	private static final int[] _players_name_id = new int[] {
		R.id.txt_playA01_name, R.id.txt_playA02_name, R.id.txt_playA03_name, R.id.txt_playA04_name,	R.id.txt_playA05_name, R.id.txt_playA06_name,
		R.id.txt_playA07_name, R.id.txt_playA08_name, R.id.txt_playA09_name, R.id.txt_playA10_name, R.id.txt_playA11_name, R.id.txt_playA12_name
	};
	private static final int[] _players_num_id = new int[] {
		R.id.txt_playA01_num, R.id.txt_playA02_num, R.id.txt_playA03_num, R.id.txt_playA04_num,	R.id.txt_playA05_num, R.id.txt_playA06_num,
		R.id.txt_playA07_num, R.id.txt_playA08_num, R.id.txt_playA09_num, R.id.txt_playA10_num, R.id.txt_playA11_num, R.id.txt_playA12_num
	};
	private static final int[] _players_play_id = new int[] {
		R.id.txt_playA01_play, R.id.txt_playA02_play, R.id.txt_playA03_play, R.id.txt_playA04_play,	R.id.txt_playA05_play, R.id.txt_playA06_play,
		R.id.txt_playA07_play, R.id.txt_playA08_play, R.id.txt_playA09_play, R.id.txt_playA10_play, R.id.txt_playA11_play, R.id.txt_playA12_play
	};
	private static final int[] _players_score_3pt = new int[] {
		R.id.txt_playA01_score_3pt, R.id.txt_playA02_score_3pt, R.id.txt_playA03_score_3pt, R.id.txt_playA04_score_3pt,	R.id.txt_playA05_score_3pt, R.id.txt_playA06_score_3pt,
		R.id.txt_playA07_score_3pt, R.id.txt_playA08_score_3pt, R.id.txt_playA09_score_3pt, R.id.txt_playA10_score_3pt, R.id.txt_playA11_score_3pt, R.id.txt_playA12_score_3pt
	};
	private static final int[] _players_score_3pt_tent = new int[] {
		R.id.txt_playA01_score_3pt_tent, R.id.txt_playA02_score_3pt_tent, R.id.txt_playA03_score_3pt_tent, R.id.txt_playA04_score_3pt_tent,	R.id.txt_playA05_score_3pt_tent, R.id.txt_playA06_score_3pt_tent,
		R.id.txt_playA07_score_3pt_tent, R.id.txt_playA08_score_3pt_tent, R.id.txt_playA09_score_3pt_tent, R.id.txt_playA10_score_3pt_tent, R.id.txt_playA11_score_3pt_tent, R.id.txt_playA12_score_3pt_tent
	};
/*	private static final int[] _players_score_2pt = new int[] {
		R.id.txt_playA01_score_2pt, R.id.txt_playA02_score_2pt, R.id.txt_playA03_score_2pt, R.id.txt_playA04_score_2pt,	R.id.txt_playA05_score_2pt, R.id.txt_playA06_score_2pt,
		R.id.txt_playA07_score_2pt, R.id.txt_playA08_score_2pt, R.id.txt_playA09_score_2pt, R.id.txt_playA10_score_2pt, R.id.txt_playA11_score_2pt, R.id.txt_playA12_score_2pt
	};
	private static final int[] _players_score_2pt_tent = new int[] {
		R.id.txt_playA01_score_2pt_tent, R.id.txt_playA02_score_2pt_tent, R.id.txt_playA03_score_2pt_tent, R.id.txt_playA04_score_2pt_tent,	R.id.txt_playA05_score_2pt_tent, R.id.txt_playA06_score_2pt_tent,
		R.id.txt_playA07_score_2pt_tent, R.id.txt_playA08_score_2pt_tent, R.id.txt_playA09_score_2pt_tent, R.id.txt_playA10_score_2pt_tent, R.id.txt_playA11_score_2pt_tent, R.id.txt_playA12_score_2pt_tent
	};
*/
	private static final int[] _players_score_lf = new int[] {
		R.id.txt_playA01_score_lf, R.id.txt_playA02_score_lf, R.id.txt_playA03_score_lf, R.id.txt_playA04_score_lf,	R.id.txt_playA05_score_lf, R.id.txt_playA06_score_lf,
		R.id.txt_playA07_score_lf, R.id.txt_playA08_score_lf, R.id.txt_playA09_score_lf, R.id.txt_playA10_score_lf, R.id.txt_playA11_score_lf, R.id.txt_playA12_score_lf
	};
	private static final int[] _players_score_lf_tent = new int[] {
		R.id.txt_playA01_score_lf_tent, R.id.txt_playA02_score_lf_tent, R.id.txt_playA03_score_lf_tent, R.id.txt_playA04_score_lf_tent,	R.id.txt_playA05_score_lf_tent, R.id.txt_playA06_score_lf_tent,
		R.id.txt_playA07_score_lf_tent, R.id.txt_playA08_score_lf_tent, R.id.txt_playA09_score_lf_tent, R.id.txt_playA10_score_lf_tent, R.id.txt_playA11_score_lf_tent, R.id.txt_playA12_score_lf_tent
	};
	private static final int[] _players_fault_id = new int[] {
		R.id.txt_playA01_fault, R.id.txt_playA02_fault, R.id.txt_playA03_fault, R.id.txt_playA04_fault,	R.id.txt_playA05_fault, R.id.txt_playA06_fault,
		R.id.txt_playA07_fault, R.id.txt_playA08_fault, R.id.txt_playA09_fault, R.id.txt_playA10_fault, R.id.txt_playA11_fault, R.id.txt_playA12_fault
	};
	private String[] _players_names = new String[_players_name_id.length];
	private int[] _players_nums = new int[_players_num_id.length];
	


	/**
	 * getLog
	 * @return _log
	 */
	public Loggable getLog() {
		return _log;
	}

	/**
	 * setLog
	 * @param log
	 */
	public void setLog(final Loggable log) {
		if (log == null) {
			this.getLog().e(TAG, "setLog with a null log => keep previous value");
			return;
		}
		_log = log;
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket2_scoring_board);

        _myActiv = this;

		for (int i = 0; i < _players_names.length; i++) {
			int j = (int)'A';
			char c = (char)(j+i);
			_players_names[i] = String.valueOf(c) + String.valueOf(c) + String.valueOf(c) + String.valueOf(i+1);
			_players_nums[i] = 50 + i;
		}

		int nb_players_a = 0;
		int nb_players_b = 0;
		Bundle data = this.getIntent().getExtras();
		if (data != null) {
			nb_players_a = data.getInt(CommonPlayBoard.TAG_NB_PLAYERS_TEAM_A, 0);
			nb_players_b = data.getInt(CommonPlayBoard.TAG_NB_PLAYERS_TEAM_B, 0);
			this.getLog().d(TAG, "data retrieved from data bundle: " + nb_players_a + " players for team A, " + nb_players_b + " players for team B");
		}
		if (nb_players_a > 0) {
			String players = data.getString(CommonPlayBoard.TAG_PLAYERS_TEAM_A);
			this.getLog().d(TAG, "players for team A: " + players);
			StringTokenizer st = new StringTokenizer(players, ";");
			int i = 0;
			while (st.hasMoreTokens()) {
				String a_player = st.nextToken();
				String[] name_num = a_player.split(",");
				_players_names[i] = name_num[0];
				try {
					_players_nums[i] = Integer.parseInt(name_num[1]);
				} catch (NumberFormatException e) {
					_players_nums[i] = 50 - i;
				}
				i++;
			}
		} else {
		}

        _game = new BasketPlayBoard(this.getLog());

		// Match timer and Inter period timer
		initMatchAndInterTimers();

        // Period view and Start/Stop button
		initPeriodAndStartButton();

        // Score A
		initScoreView(CommonPlayBoard.TEAM_ID_A);

        // Score B
		initScoreView(CommonPlayBoard.TEAM_ID_B);

        // Pause A timer and button
		initPauseView(CommonPlayBoard.TEAM_ID_A);

        // Pause B timer and button
		initPauseView(CommonPlayBoard.TEAM_ID_B);

        // Player 1 to n
        for (int i = 0; i < _players_names.length; i++) {
        	// one graphical player
        	initOnePlayer(CommonPlayBoard.TEAM_ID_A, i, _players_names[i], _players_nums[i]);
        }
        // Player n+1 to max
        for (int i = _players_names.length; i < _players_name_id.length; i++) {
        	// one graphical player
        	_players_names[i] = "AA" + String.valueOf(i);
        	_players_nums[i] = 50 + i;
        	initOnePlayer(CommonPlayBoard.TEAM_ID_A, i, _players_names[i], _players_nums[i]);
        }

        _game.initGame();

    }

    /**
     * initMatchAndInterTimers
     */
    public void initMatchAndInterTimers() {
    	this.getLog().d(TAG, "initMatchAndInterTimers");
		// Match timer
		TextView tv_time = (TextView) findViewById(R.id.txt_time);
        GraphicalTimer gtv_time = new GraphicalTimer(CommonPlayBoard.TIMER_ID_MATCH, tv_time, this.getLog());
        _game.setMatchTimer(gtv_time);

        // Inter period timer
        GraphicalTimer gtv_inter = new GraphicalTimer(CommonPlayBoard.TIMER_ID_INTER, tv_time, this.getLog());
        _game.setInterTimer(gtv_inter);
    }

    /**
     * initPeriodAndStartButton
     */
    public void initPeriodAndStartButton() {
    	this.getLog().d(TAG, "initPeriodAndStartButton");
        // Period view
        TextView tv_period = (TextView) findViewById(R.id.txt_period);
        GraphicalTextView gtv_period = new GraphicalTextView(tv_period, this.getLog());
        _game.setPeriodView(gtv_period);

        // Start/Stop button
        EventReceiver stEvent = new EventReceiver(this.getLog());
        Button bt_start_stop = (Button) findViewById(R.id.bt_start_stop);
        bt_start_stop.setOnClickListener(stEvent);
        GraphicalButton gbt_start_stop = new GraphicalButton(bt_start_stop, "Start-Stop", this.getLog());
        gbt_start_stop.addPossibleText("Start", R.string.bt_start);
        gbt_start_stop.addPossibleText("Stop", R.string.bt_stop);
        gbt_start_stop.addPossibleText("End", R.string.bt_end);
        _game.setStartStopButton(gbt_start_stop, stEvent);
    }

    /**
     * initScoreView
     * @param team
     */
    public void initScoreView(final int team) {
    	this.getLog().d(TAG, "initScoreView(team=" + team + ")");
    	int txt_id = R.id.txt_score_A;
    	if (team == CommonPlayBoard.TEAM_ID_B) {
    		txt_id = R.id.txt_score_B;
    	}
        // Score view
        TextView tv_score = (TextView) findViewById(txt_id);
        GraphicalTextView gtv_score = new GraphicalTextView(tv_score, this.getLog());
        EventReceiver scoreEvent = new EventReceiver(this.getLog());
        tv_score.setOnClickListener(scoreEvent);
        tv_score.setOnLongClickListener(scoreEvent);
        _game.setScoreView(team, gtv_score, scoreEvent);
    }

    /**
     * initPauseView
     * @param team
     */
    public void initPauseView(final int team) {
    	this.getLog().d(TAG, "initPauseView(team=" + team + ")");
    	int txt_id = R.id.txt_pause_A;
    	int bt_id = R.id.bt_pause_A;
    	if (team == CommonPlayBoard.TEAM_ID_B) {
    		txt_id = R.id.txt_pause_B;
    		bt_id = R.id.bt_pause_B;
    	}
        // Pause timer
        TextView tv_pause = (TextView) findViewById(txt_id);
        GraphicalTimer gtv_pause = new GraphicalTimer(CommonPlayBoard.TIMER_ID_PAUSE_A, tv_pause, this.getLog());
        _game.setPauseTimer(team, gtv_pause);

        // Pause button
        EventReceiver pauseEvent = new EventReceiver(this.getLog());
        _game.setPauseEvent(team, pauseEvent);
        Button bt_pause = (Button) findViewById(bt_id);
        bt_pause.setOnClickListener(pauseEvent);
        GraphicalButton gbt_pause = new GraphicalButton(bt_pause, "pause_" + CommonPlayBoard.CLUB_INDEX[team], this.getLog());
        _game.setPauseButton(team, gbt_pause);
    }

    /**
     * initOnePlayer
     * @param team
     * @param pl_id
     * @param name
     * @param num
     */
    public void initOnePlayer(final int team, final int pl_id, final String name, final int num) {
    	this.getLog().d(TAG, "initOnePlayer(team=" + team + ", pl_id=" + pl_id + ", name=" + name + ", num=" + num);
    	// one graphical player
    	CommonGraphicalPlayer gpl = _game.addOnePlayer(team, name, num);
    	// its name
        TextView tv_player_name = (TextView) findViewById(_players_name_id[pl_id]);
        GraphicalTextView gtv_player_name = new GraphicalTextView(tv_player_name, this.getLog());
        gtv_player_name.setText(name);

        // its bib number
        TextView tv_player_num = (TextView) findViewById(_players_num_id[pl_id]);
        GraphicalTextView gtv_player_num = new GraphicalTextView(tv_player_num, this.getLog());
        gtv_player_num.setText(String.valueOf(num));

        // its play time
        TextView tv_player_play = (TextView) findViewById(_players_play_id[pl_id]);
        GraphicalTextView gtv_player_play = new GraphicalTextView(tv_player_play, this.getLog());
        gtv_player_play.setText("0:00");

        // its 3pt score
        TextView tv_player_score_3pt = (TextView) findViewById(_players_score_3pt[pl_id]);
        GraphicalTextView gtv_player_score_3pt = new GraphicalTextView(tv_player_score_3pt, this.getLog());
        EventReceiver score3ptEvent = new EventReceiver(this.getLog());
        tv_player_score_3pt.setOnClickListener(score3ptEvent);
        _game.setScoreEvent(gpl.getTeam(), pl_id, score3ptEvent);

        // its 3pt tentative score
        TextView tv_player_score_3pt_tent = (TextView) findViewById(_players_score_3pt_tent[pl_id]);
        GraphicalTextView gtv_player_score_3pt_tent = new GraphicalTextView(tv_player_score_3pt_tent, this.getLog());
        EventReceiver score3ptTentEvent = new EventReceiver(this.getLog());
        tv_player_score_3pt_tent.setOnClickListener(score3ptTentEvent);
        _game.setScoreTentEvent(gpl.getTeam(), pl_id, score3ptTentEvent);

        // its 2pt score
/*        TextView tv_player_score_2pt = (TextView) findViewById(_players_score_2pt[pl_id]);
        GraphicalTextView gtv_player_score_2pt = new GraphicalTextView(tv_player_score_2pt, this.getLog());
        EventReceiver score2ptEvent = new EventReceiver(this.getLog());
        tv_player_score_2pt.setOnClickListener(score2ptEvent);
        _game.setScore2ptEvent(gpl.getTeam(), pl_id, score2ptEvent);
*/

        // its 2pt tentative score
/*        TextView tv_player_score_2pt_tent = (TextView) findViewById(_players_score_2pt_tent[pl_id]);
        GraphicalTextView gtv_player_score_2pt_tent = new GraphicalTextView(tv_player_score_2pt_tent, this.getLog());
        EventReceiver score2ptTentEvent = new EventReceiver(this.getLog());
        tv_player_score_2pt_tent.setOnClickListener(score2ptTentEvent);
        _game.setScore2ptTentEvent(gpl.getTeam(), pl_id, score2ptTentEvent);
*/

        // its lf score
        TextView tv_player_score_lf = (TextView) findViewById(_players_score_lf[pl_id]);
        GraphicalTextView gtv_player_score_lf = new GraphicalTextView(tv_player_score_lf, this.getLog());
        EventReceiver scoreLfEvent = new EventReceiver(this.getLog());
        tv_player_score_lf.setOnClickListener(scoreLfEvent);
        _game.setScoreLfEvent(gpl.getTeam(), pl_id, scoreLfEvent);

        // its lf tentative score
        TextView tv_player_score_lf_tent = (TextView) findViewById(_players_score_lf_tent[pl_id]);
        GraphicalTextView gtv_player_score_lf_tent = new GraphicalTextView(tv_player_score_lf_tent, this.getLog());
        EventReceiver scoreLfTentEvent = new EventReceiver(this.getLog());
        tv_player_score_lf_tent.setOnClickListener(scoreLfTentEvent);
        _game.setScoreLfTentEvent(gpl.getTeam(), pl_id, scoreLfTentEvent);

        // its faults
        TextView tv_player_fault = (TextView) findViewById(_players_fault_id[pl_id]);
        GraphicalTextView gtv_player_fault = new GraphicalTextView(tv_player_fault, this.getLog());
        EventReceiver faultEvent = new EventReceiver(this.getLog());
        tv_player_fault.setOnClickListener(faultEvent);
        _game.setFaultEvent(gpl.getTeam(), pl_id, faultEvent);

        // set the graphical elements
        gpl.setGraphicalElements(gtv_player_name, gtv_player_num,
                                 gtv_player_score_3pt, gtv_player_score_3pt_tent,
//                                 gtv_player_score_2pt, gtv_player_score_2pt_tent,
                                 gtv_player_score_lf, gtv_player_score_lf_tent,
                                 gtv_player_fault);
		gpl.setEnabled(false);
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
        outState.putInt("current_period", _game.getCurrentPeriod());
        outState.putBoolean("playing_time", _game.isPlayingTime());
        // save state of all timers
//        outState.putString("matchTimer", _gbt_start_stop.getTimer().saveToString());
//        outState.putString("pauseTimer", _pauseTimer.saveToString());
//    	outState.putString("interTimer", _interTimer.saveToString());
//    	for (int i = 0; i < 2; i++) {
//            outState.putInt("current_nb_exclu_"+i, _current_nb_exclu[i]);
//            outState.putString("score_"+i, _txt_score[i].getText().toString());
//            outState.putString("club_"+i, _txt_club[i].getText().toString());
//        	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
//        		outState.putString("excluTimer_"+i+"_"+j, _excluTimer[i][j].saveToString());
//        		outState.putString("excluPlayer_"+i+"_"+j, _excluPlayer[i][j]);
//    		}
//		}

    	super.onSaveInstanceState(outState);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        _game.setCurrentPeriod(savedInstanceState.getInt("current_period"));
        _game.setPlayingTime(savedInstanceState.getBoolean("playing_time"));
        if (_game.isPlayingTime()) {
//        	_game.getStartStopButton().setText("Stop");
        } else {
//        	_game.getStartStopButton().setText("Start");
        }
//        _matchTimer = OneTimer.OneTimerFromString(this, savedInstanceState.getString("matchTimer"));
//        _pauseTimer = OneTimer.OneTimerFromString(this, savedInstanceState.getString("pauseTimer"));
//        _interTimer = OneTimer.OneTimerFromString(this, savedInstanceState.getString("interTimer"));
//    	for (int i = 0; i < 2; i++) {
//            _current_nb_exclu[i] = savedInstanceState.getInt("current_nb_exclu_"+i);
//            _txt_score[i].setText(savedInstanceState.getString("score_"+i));
//            _txt_club[i].setText(savedInstanceState.getString("club_"+i));
//            _init_club[i] = true;
//            // restore all timers
//        	for (int j = 0; j < MAX_DISPLAY_EXCLU; j++) {
//        		_excluTimer[i][j] = OneTimer.OneTimerFromString(this, savedInstanceState.getString("excluTimer_"+i+"_"+j));
//        		_excluPlayer[i][j] = savedInstanceState.getString("excluPlayer_"+i+"_"+j);
//        		if (! EMPTY_EXCLU_PLAYER.equals(_excluPlayer[i][j])) {
////        			_txt_exclu_player[i][j].setText(_excluPlayer[i][j]);
//        		}
//    		}
//		}
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
//        _timeHandler.stopTimeHandler();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ( (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME)  && event.getRepeatCount() == 0) {
//	        event.startTracking();
	    	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    		Toast.makeText(getApplicationContext(), "touche down BACK", Toast.LENGTH_SHORT).show();
	    	} else {
	    		Toast.makeText(getApplicationContext(), "touche down HOME", Toast.LENGTH_SHORT).show();
	    	}
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if ( (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME)  && event.getRepeatCount() == 0) {
	    		//&& event.isTracking() && !event.isCanceled()) {
	        // *** Your Code ***
	    	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    		Toast.makeText(getApplicationContext(), "touche up BACK", Toast.LENGTH_SHORT).show();
	    	} else {
	    		Toast.makeText(getApplicationContext(), "touche up HOME", Toast.LENGTH_SHORT).show();
	    	}
	    	if (_game.isPlayingTime()) {
		    	long now = System.currentTimeMillis();
		    	long diff = now - _lastTimeKeyUp;
		    	_lastTimeKeyUp = now;
		    	Log.d(TAG, "now is: " + now + ", diff with previous keyUp: " + diff);
		    	if (diff > 400) {
		    		Toast.makeText(getApplicationContext(), "the time must be stopped before leaving", Toast.LENGTH_LONG).show();
//			        MainActivity.askConfirm(MainActivity.REQ_CONFIRM_QUIT, _myActiv, _myActiv, getString(R.string.quit_confirm_st), getString(R.string.confirm_st));
			        return true;
		    	}
	    	} else {
	    		Toast.makeText(getApplicationContext(), "back during stopped play", Toast.LENGTH_LONG).show();
	    		_myActiv.finish();
	    		return false;
	    	}
	    }
	    return super.onKeyUp(keyCode, event);
	}

	@Override
	public void dialogReturn(int reqId, int res, DialogInterface dialInt, int id) {
		// TODO Auto-generated method stub
		
	}


}
