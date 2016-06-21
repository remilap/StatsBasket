/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

import java.util.ArrayList;

import eu.remilapointe.statsbasket.common.play.android.EventReceiver;
import eu.remilapointe.statsbasket.common.play.android.GraphicalButton;
import eu.remilapointe.statsbasket.common.play.android.GraphicalTextView;
import eu.remilapointe.statsbasket.common.play.android.GraphicalTimer;
import eu.remilapointe.statsbasket.sport.DefaultLog;
import eu.remilapointe.statsbasket.sport.Loggable;
import eu.remilapointe.statsbasket.timer.OneTimer;
import eu.remilapointe.statsbasket.timer.TimeHandler;
import eu.remilapointe.statsbasket.timer.TimerStateException;
import eu.remilapointe.statsbasket.timer.android.AndroidHandler;

/**
 * @author lapoint1
 *
 */
public class CommonPlayBoard implements CommonEventDispatchable {

	public static final int TIMER_MINUTES_INTO_SECONDS = 60;
	public static final int TIMER_PERIOD_MIN_DURATION = 1;       // minutes
	public static final int TIMER_PERIOD_MAX_DURATION = 120;     // minutes
	public static final int TIMER_PERIOD_DEFAULT_DURATION = 14;  // minutes
	public static final int TIMER_PAUSE_MIN_DURATION = 1;        // minutes
	public static final int TIMER_PAUSE_MAX_DURATION = 5;        // minutes
	public static final int TIMER_PAUSE_DEFAULT_DURATION = 1;    // minutes
	public static final int TIMER_INTER_MIN_DURATION = 1;        // minutes
	public static final int TIMER_INTER_MAX_DURATION = 60;       // minutes
	public static final int TIMER_INTER_DEFAULT_DURATION = 15;   // minutes
	public static final int TIMER_EXCLU_MIN_DURATION = 1;        // minutes
	public static final int TIMER_EXCLU_MAX_DURATION = 5;        // minutes
	public static final int TIMER_EXCLU_DEFAULT_DURATION = 2;    // minutes
	public static final int NB_MAX_PERIODS = 8;
	public static final int NB_DEFAULT_PERIODS = 2;
	public static final int NB_MAX_PAUSES = 8;
	public static final int NB_DEFAULT_PAUSES = 3;
	public static final String EMPTY_EXCLU_PLAYER = ".";
	public static final int MAX_DISPLAY_EXCLU = 3;
	public static final int MAX_PLAYERS = 20;

	public static final int TIMER_ID_MATCH = 1;
	public static final int TIMER_ID_INTER = 2;
	public static final int TIMER_ID_PAUSE_NONE = 3;
	public static final int TIMER_ID_PAUSE_A = 4;
	public static final int TIMER_ID_PAUSE_B = 5;
	public static final int TIMER_ID_EXCLU_BEGIN = 10;

	public static final String[] CLUB_INDEX = {"A", "B", "0"};
	public static final int TEAM_ID_A = 0;
	public static final int TEAM_ID_B = 1;
	public static final int TEAM_ID_0 = 2;

	public static final String TAG_NB_PLAYERS_TEAM_A = "NB_PLAYERS_TEAM_A";
	public static final String TAG_NB_PLAYERS_TEAM_B = "NB_PLAYERS_TEAM_B";
	public static final String TAG_PLAYERS_TEAM_A = "PLAYERS_TEAM_A";
	public static final String TAG_PLAYERS_TEAM_B = "PLAYERS_TEAM_B";


	private static final String TAG = CommonPlayBoard.class.getSimpleName();

	private Loggable _log = new DefaultLog();

//	private OneTimer _matchTimer = null;
//	private OneTimer _pauseTimer = null;
//	private OneTimer _interTimer = null;
//	private OneTimer[][] _excluTimer = new OneTimer[2][MAX_DISPLAY_EXCLU];

	private int _timer_period_duration = TIMER_PERIOD_DEFAULT_DURATION * TIMER_MINUTES_INTO_SECONDS;
	private int _timer_pause_duration = TIMER_PAUSE_DEFAULT_DURATION * TIMER_MINUTES_INTO_SECONDS;
	private int _timer_inter_duration = TIMER_INTER_DEFAULT_DURATION * TIMER_MINUTES_INTO_SECONDS;
	private int _timer_exclu_duration = TIMER_EXCLU_DEFAULT_DURATION * TIMER_MINUTES_INTO_SECONDS;

	private int _nb_periods = NB_DEFAULT_PERIODS;
	private int _current_period = 1;
    private boolean _playingTime = false;
    private int _nb_pauses = NB_DEFAULT_PAUSES;
	private int _pauseTeamId = TEAM_ID_0;      // => no pause
	private int[] _pauseNb = new int[] {0, 0};
	private int[] _score = new int[] {0, 0};

	private ArrayList<CommonGraphicalPlayer> _players_A = new ArrayList<CommonGraphicalPlayer>();
	private ArrayList<CommonGraphicalPlayer> _players_B = new ArrayList<CommonGraphicalPlayer>();

	protected int _team_selected = -1;
	protected int _player_selected = -1;
	protected int _action_selected = -1;

	private TimeHandler _time_handler = null;

	private GraphicalButton _gbt_start_stop = null;
	private GraphicalButton[] _gbt_pause = new GraphicalButton[2];

	private GraphicalTimer _gtv_match_timer = null;
	private GraphicalTimer _gtv_inter_timer = null;
	private GraphicalTimer[] _gtv_pause_timer = new GraphicalTimer[2];
	private GraphicalTextView[] _gtv_score = new GraphicalTextView[2];
	private GraphicalTextView _gtv_period = null;

	private EventReceiver _st_event = null;
	private EventReceiver[] _pause_event = new EventReceiver[2];
	private EventReceiver[][] _score_event = new EventReceiver[2][MAX_PLAYERS];
	private EventReceiver[][] _score_tent_event = new EventReceiver[2][MAX_PLAYERS];
	private EventReceiver[][] _score_lf_event = new EventReceiver[2][MAX_PLAYERS];
	private EventReceiver[][] _score_lf_tent_event = new EventReceiver[2][MAX_PLAYERS];
	private EventReceiver[][] _fault_event = new EventReceiver[2][MAX_PLAYERS];
//	private EndPeriodEvent _end_period_event = null;


	/**
	 * Constructor
	 */
	public CommonPlayBoard() {
		super();
		setLog(null);
		this.getLog().d(TAG, "BasketPlayBoard simple constructor");
	}

	/**
	 * Constructor
	 * @param log
	 */
	public CommonPlayBoard(final Loggable log) {
		super();
		setLog(log);
		this.getLog().d(TAG, "BasketPlayBoard constructor with log");
	}

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

	/**
	 * checkTeam
	 * @param team
	 * @param method
	 * @return
	 */
	public boolean checkTeam(final int team, final String method) {
		if ( (team != TEAM_ID_A) && (team != TEAM_ID_B) ) {
			this.getLog().e(TAG, method + " with invalid team id, must be " + TEAM_ID_A + " for team A and " + TEAM_ID_B + " for team B");
			return false;
		}
		return true;
	}

	/**
	 * checkPlayerId
	 * @param id
	 * @param method
	 * @return
	 */
	public boolean checkPlayerId(final int id, final String method) {
		if ( (id < 0) || (id >= MAX_PLAYERS) ) {
			this.getLog().e(TAG, method + " with invalid player id, must be in the range 0.." + (MAX_PLAYERS-1));
			return false;
		}
		return true;
	}

	/**
	 * setMatchTimer
	 * @param gtv
	 */
	public void setMatchTimer(final GraphicalTimer gtv) {
		if (gtv == null) {
			this.getLog().e(TAG, "setMatchTimer with a null GraphicalTimer");
			return;
		}
		_gtv_match_timer = gtv;
		_timer_period_duration = 3 * TIMER_MINUTES_INTO_SECONDS;
        _gtv_match_timer.initTimer(_timer_period_duration);
        _gtv_match_timer.addListener(this);
	}

	/**
	 * setInterTimer
	 * @param gtv
	 */
	public void setInterTimer(final GraphicalTimer gtv) {
		if (gtv == null) {
			this.getLog().e(TAG, "setInterTimer with a null GraphicalTimer");
			return;
		}
		_gtv_inter_timer = gtv;
		_timer_inter_duration = 3 * TIMER_MINUTES_INTO_SECONDS;
        _gtv_inter_timer.initTimer(_timer_inter_duration);
        _gtv_inter_timer.addListener(this);
	}

	/**
	 * setStartStopButton
	 * @param gbt
	 */
	public void setStartStopButton(final GraphicalButton gbt, final EventReceiver stEvent) {
		if (gbt == null) {
			this.getLog().e(TAG, "setStartStopButton with a null GraphicalButton");
			return;
		}
		if (stEvent == null) {
			this.getLog().e(TAG, "setStartStopButton with a null EventReceiver");
			return;
		}
		_gbt_start_stop = gbt;
		_st_event = stEvent;
		_st_event.setId(CommonEventDispatchable.EVENT_BUTTON_START_STOP);
		_st_event.setName("start-stop");
		_st_event.addListener(this);
	}

	/**
	 * setPeriodView
	 * @param gtv
	 */
	public void setPeriodView(final GraphicalTextView gtv) {
		if (gtv == null) {
			this.getLog().e(TAG, "setPeriodView with a null GraphicalTimer");
			return;
		}
		_gtv_period = gtv;
		_gtv_period.addListener(this);
	}

	/**
	 * setScoreView
	 * @param team
	 * @param gtv
	 */
	public void setScoreView(final int team, final GraphicalTextView gtv, final EventReceiver scoreEvt) {
		if (! this.checkTeam(team, "setScoreView")) return;
		if (gtv == null) {
			this.getLog().e(TAG, "setScoreView with a null GraphicalTimer");
			return;
		}
		if (scoreEvt == null) {
			this.getLog().e(TAG, "setScoreView with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_gtv_score[id] = gtv;
        scoreEvt.setId(CommonEventDispatchable.EVENT_SCORE_A + id);
        scoreEvt.setName("score_" + CLUB_INDEX[id]);
		scoreEvt.addListener(this);
	}

	/**
	 * setGtvScoreText
	 * @param team
	 */
	public void setGtvScoreText(final int team) {
		_gtv_score[team].setText(getScoreAsString(team));
	}

	/**
	 * setPauseTimer
	 * @param gtv
	 */
	public void setPauseTimer(final int team, final GraphicalTimer gtv) {
		if (! this.checkTeam(team, "setPauseTimer")) return;
		if (gtv == null) {
			this.getLog().e(TAG, "setPauseTimer with a null GraphicalTimer");
			return;
		}
		int id = team - TEAM_ID_A;
		_gtv_pause_timer[id] = gtv;
		gtv.initTimer(_timer_pause_duration);
		gtv.addListener(this);
	}

	/**
	 * setPauseButton
	 * @param id
	 * @param gbt
	 */
	public void setPauseButton(final int team, final GraphicalButton gbt) {
		if (! this.checkTeam(team, "setPauseButton")) return;
		if (gbt == null) {
			this.getLog().e(TAG, "setPauseButton with a null GraphicalButton");
			return;
		}
		_gbt_pause[team - TEAM_ID_A] = gbt;
		gbt.setEnabled(false);
		gbt.setText( 'T' + String.valueOf( getNbPauses(team - TEAM_ID_A) ) );
	}

	/**
	 * setPauseEvent
	 * @param id
	 * @param pauseEvt
	 */
	public void setPauseEvent(final int team, final EventReceiver pauseEvt) {
		if (! this.checkTeam(team, "setPauseEvent")) return;
		if (pauseEvt == null) {
			this.getLog().e(TAG, "setPauseEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_pause_event[id] = pauseEvt;
		_pause_event[id].setId(CommonEventDispatchable.EVENT_BUTTON_PAUSE_A + id);
		_pause_event[id].setName("pause_" + CLUB_INDEX[id]);
		_pause_event[id].addListener(this);
	}

	/**
	 * setScoreEvent
	 * @param team
	 * @param i
	 * @param scoreEvt
	 */
	public void setScoreEvent(final int team, final int i, final EventReceiver scoreEvt) {
		if (! this.checkTeam(team, "setScoreEvent")) return;
		if (! this.checkPlayerId(i, "setScoreEvent")) return;
		if (scoreEvt == null) {
			this.getLog().e(TAG, "setScoreEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_event[id][i] = scoreEvt;
		_score_event[id][i].setId(CommonEventDispatchable.EVENT_BUTTON_SCORE_MIN + id * MAX_PLAYERS + i);
		_score_event[id][i].setName("score_" + CLUB_INDEX[id] + "_" + i);
		_score_event[id][i].addListener(this);
	}

	/**
	 * setScoreTentEvent
	 * @param team
	 * @param i
	 * @param score3ptTentEvt
	 */
	public void setScoreTentEvent(final int team, final int i, final EventReceiver scoreTentEvt) {
		if (! this.checkTeam(team, "setScoreTentEvent")) return;
		if (! this.checkPlayerId(i, "setScoreTentEvent")) return;
		if (scoreTentEvt == null) {
			this.getLog().e(TAG, "setScoreTentEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_tent_event[id][i] = scoreTentEvt;
		_score_tent_event[id][i].setId(CommonEventDispatchable.EVENT_BUTTON_SCORE_TENT_MIN + id * MAX_PLAYERS + i);
		_score_tent_event[id][i].setName("scoreTent_" + CLUB_INDEX[id] + "_" + i);
		_score_tent_event[id][i].addListener(this);
	}

	/**
	 * setScoreLfEvent
	 * @param team
	 * @param i
	 * @param scoreLfEvt
	 */
	public void setScoreLfEvent(final int team, final int i, final EventReceiver scoreLfEvt) {
		if (! this.checkTeam(team, "setScoreLfEvent")) return;
		if (! this.checkPlayerId(i, "setScoreLfEvent")) return;
		if (scoreLfEvt == null) {
			this.getLog().e(TAG, "setScoreLfEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_lf_event[id][i] = scoreLfEvt;
		_score_lf_event[id][i].setId(CommonEventDispatchable.EVENT_BUTTON_SCORE_LF_MIN + id * MAX_PLAYERS + i);
		_score_lf_event[id][i].setName("scorelf_" + CLUB_INDEX[id] + "_" + i);
		_score_lf_event[id][i].addListener(this);
	}

	/**
	 * setScoreLfTentEvent
	 * @param team
	 * @param i
	 * @param scoreLfTentEvt
	 */
	public void setScoreLfTentEvent(final int team, final int i, final EventReceiver scoreLfTentEvt) {
		if (! this.checkTeam(team, "setScoreLfTentEvent")) return;
		if (! this.checkPlayerId(i, "setScoreLfTentEvent")) return;
		if (scoreLfTentEvt == null) {
			this.getLog().e(TAG, "setScoreLfTentEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_lf_tent_event[id][i] = scoreLfTentEvt;
		_score_lf_tent_event[id][i].setId(CommonEventDispatchable.EVENT_BUTTON_SCORE_LF_TENT_MIN + id * MAX_PLAYERS + i);
		_score_lf_tent_event[id][i].setName("scoreLfTent_" + CLUB_INDEX[id] + "_" + i);
		_score_lf_tent_event[id][i].addListener(this);
	}

	/**
	 * setFaultEvent
	 * @param team
	 * @param i
	 * @param faultEvt
	 */
	public void setFaultEvent(final int team, final int i, final EventReceiver faultEvt) {
		if (! this.checkTeam(team, "setFaultEvent")) return;
		if (! this.checkPlayerId(i, "setFaultEvent")) return;
		if (faultEvt == null) {
			this.getLog().e(TAG, "setFaultEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_fault_event[id][i] = faultEvt;
		_fault_event[id][i].setId(CommonEventDispatchable.EVENT_BUTTON_FAULT_MIN + id * MAX_PLAYERS + i);
		_fault_event[id][i].setName("fault_" + CLUB_INDEX[id] + "_" + i);
		_fault_event[id][i].addListener(this);
	}

	/**
	 * initGame
	 */
	public void initGame() {
		_time_handler = TimeHandler.getInstance(new AndroidHandler(), this.getLog(), TimeHandler.DEFAULT_TIMER_RUNNING_PERIOD);
		_time_handler.startTimeHandler();
	}

	/**
	 * addOnePlayer
	 * @param team
	 * @param name
	 * @param num
	 * @return
	 */
	public CommonGraphicalPlayer addOnePlayer(final int team, final String name, final int num) {
		CommonGraphicalPlayer pl = null;
		if (! this.checkTeam(team, "addOnePlayer")) return pl;
		pl = new CommonGraphicalPlayer(this.getLog(), team, name, num);
		if (team == TEAM_ID_A) {
			_players_A.add(pl);
		} else if (team == TEAM_ID_B) {
			_players_B.add(pl);
		}
		return pl;
	}

	/**
	 * getNbPeriods
	 * @return
	 */
	public int getNbPeriods() {
		return _nb_periods;
	}

	/**
	 * getNbPeriodsAsString
	 * @return
	 */
	public String getNbPeriodsAsString() {
		return String.valueOf(this.getNbPeriods());
	}

	/**
	 * setNbPeriods
	 * @param n
	 * @return
	 */
	public boolean setNbPeriods(final int n) {
		this.getLog().d(TAG, "setNbPeriods(int " + n + ")");
		if ( (n < 1) || (n > NB_MAX_PERIODS) ) {
			_nb_periods = NB_DEFAULT_PERIODS;
			this.getLog().w(TAG, "Nb periods is out of bounds 1.." + NB_MAX_PERIODS + ", so is set to " + _nb_periods);
			return false;
		}
		_nb_periods = n;
		return true;
	}

	/**
	 * setNbPeriods
	 * @param np
	 * @return
	 */
	public boolean setNbPeriods(final String np) {
		this.getLog().d(TAG, "setNbPeriods(String " + np + ")");
		int n = 0;
		try {
			n = Integer.parseInt(np);
			return setNbPeriods(n);
		} catch (NumberFormatException e) {
			_nb_periods = NB_DEFAULT_PERIODS;
			this.getLog().w(TAG, "Provided nb periods (" + np + ") is not an integer, so is set to " + _nb_periods);
			return false;
		}
	}

	/**
	 * getCurrentPeriod
	 * @return
	 */
	public int getCurrentPeriod() {
		return _current_period;
	}

	/**
	 * getCurrentPeriod
	 * @return
	 */
	public String getCurrentPeriodAsString() {
		return String.valueOf(this.getCurrentPeriod());
	}

	/**
	 * isLastPeriod
	 * @return
	 */
	public boolean isLastPeriod() {
		if (_current_period < _nb_periods) {
			this.getLog().d(TAG, "isLastPeriod() result false, current=" + _current_period + ", nb=" + _nb_periods);
			return false;
		}
		this.getLog().d(TAG, "isLastPeriod() result true, current=" + _current_period + ", nb=" + _nb_periods);
		return true;
	}

	/**
	 * setCurrentPeriod
	 * @param period
	 */
	public boolean setCurrentPeriod(final int period) {
		this.getLog().d(TAG, "setCurrentPeriod(int " + period + ")");
		if ( (period < 1) || (period > getNbPeriods()) ) {
			this.getLog().e(TAG, "Unable to set current period because out of bounds 1.." + getNbPeriods());
			return false;
		}
		_current_period = period;
		return true;
	}

	/**
	 * setCurrentPeriod
	 * @param period
	 */
	public boolean setCurrentPeriod(final String period) {
		this.getLog().d(TAG, "setCurrentPeriod(String " + period + ")");
		int p = 0;
		try {
			p = Integer.parseInt(period);
			return setCurrentPeriod(p);
		} catch (NumberFormatException e) {
			p = 1;
		}
		this.getLog().e(TAG, "Unable to set current period because " + period + " is not an integer");
		return false;
	}

	/**
	 * nextPeriod
	 * @return
	 */
	public boolean nextPeriod() {
		if (_current_period < _nb_periods) {
			_current_period++;
			this.getLog().d(TAG, "nextPeriod() return true with current period=" + _current_period);
			return true;
		}
		this.getLog().d(TAG, "nextPeriod() return false, it is already the last period");
		return false;
	}

	/**
	 * getPlayingTime
	 * @return
	 */
	public boolean isPlayingTime() {
		return _playingTime;
	}

	/**
	 * setPlayingTime
	 * @param pl
	 */
	public void setPlayingTime(final boolean pl) {
		this.getLog().d(TAG, "setPlayingTime(" + pl + ")");
		_playingTime = pl;
	}

	/**
	 * getPauseTeamId
	 * @return
	 */
	public int getPauseTeamId() {
		return _pauseTeamId - TEAM_ID_A;
	}

	/**
	 * isPauseNone
	 * @return
	 */
	public boolean isPauseNone() {
		if (_pauseTeamId == TEAM_ID_0) {
			return true;
		}
		return false;
	}

	/**
	 * isPauseTeam
	 * @param team: 0 for team A, 1 for team B
	 * @return
	 */
	public boolean isPauseTeam(final int team) {
		if ( (team == TEAM_ID_A) || (team == TEAM_ID_B) ) {
			if ( (team - TEAM_ID_A == _pauseTeamId) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * setPauseTeamId
	 * @param team: 0 for team A, 1 for team B, 2 for none
	 */
	public void setPauseTeamId(final int team) {
		this.getLog().d(TAG, "setPauseTeamId(int " + team + ")");
		if ( (team == TEAM_ID_A) || (team == TEAM_ID_B) ) {
			_pauseTeamId = team - TEAM_ID_A;
		} else {
			_pauseTeamId = TEAM_ID_0;
		}
	}

	/**
	 * getNbPauses
	 * @param team: 0 for team A, 1 for team B
	 * @return
	 */
	public int getNbPauses(final int team) {
		if (! this.checkTeam(team, "getNbPauses")) return 0;
		return _pauseNb[team - TEAM_ID_A];
	}

	/**
	 * isLastPause
	 * @param team
	 * @return
	 */
	public boolean isLastPause(final int team) {
		if (! this.checkTeam(team, "isLastPause")) return true;
		if (_pauseNb[team - TEAM_ID_A] < _nb_pauses) {
			return false;
		}
		return true;
	}

	/**
	 * newPause
	 * @param team
	 * @return
	 */
	public boolean newPause(final int team) {
		if (! this.checkTeam(team, "newPause")) return false;
		if (! isLastPause(team)) {
			_pauseNb[team - TEAM_ID_A]++;
			return true;
		}
		return false;
	}

	/**
	 * getScore
	 * @param team: 0 for team A, 1 for team B
	 * @return
	 */
	public int getScore(final int team) {
		if (! this.checkTeam(team, "getScore")) return 0;
		return _score[team - TEAM_ID_A];
	}

	/**
	 * getScoreAsString
	 * @param team
	 * @return
	 */
	public String getScoreAsString(final int team) {
		return String.valueOf(getScore(team));
	}

	/**
	 * setScore
	 * @param team: 0 for team A, 1 for team B
	 * @param score
	 */
	public void setScore(final int team, final int score) {
		this.getLog().d(TAG, "setScore(int " + team + ", int " + score + ")");
		if (! this.checkTeam(team, "setScore")) return;
		_score[team - TEAM_ID_A] = score;
//		_display.setScore(team, score);
	}

	/**
	 * updateScore
	 * @param team: 0 for team A, 1 for team B
	 * @param diff
	 */
	public void updateScore(final int team, final int diff) {
		if (! this.checkTeam(team, "updateScore")) return;
		int score = _score[team - TEAM_ID_A] + diff;
		setScore(team, score);
	}

	/**
	 * setDurationPeriod
	 * @param t in minutes
	 * @return
	 */
	public boolean setPeriodDuration(final int t) {
		if ( (t < TIMER_PERIOD_MIN_DURATION) || (t > TIMER_PERIOD_MAX_DURATION) ) {
			_timer_period_duration = TIMER_PERIOD_DEFAULT_DURATION;
			this.getLog().w(TAG, "period duration is out of bounds " + TIMER_PERIOD_MIN_DURATION + ".." + TIMER_PERIOD_MAX_DURATION + ", so it is set to " + _timer_period_duration);
			return false;
		}
		_timer_period_duration = t;
		return true;
	}

	/**
	 * setPauseDuration
	 * @param t in minutes
	 * @return
	 */
	public boolean setPauseDuration(final int t) {
		if ( (t < TIMER_PAUSE_MIN_DURATION) || (t > TIMER_PAUSE_MAX_DURATION) ) {
			_timer_pause_duration = TIMER_PAUSE_DEFAULT_DURATION;
			this.getLog().w(TAG, "pause duration is out of bounds " + TIMER_PAUSE_MIN_DURATION + ".." + TIMER_PAUSE_MAX_DURATION + ", so it is set to " + _timer_pause_duration);
			return false;
		}
		_timer_pause_duration = t;
		return true;
	}

	/**
	 * setInterDuration
	 * @param t in minutes
	 * @return
	 */
	public boolean setInterDuration(final int t) {
		if ( (t < TIMER_INTER_MIN_DURATION) || (t > TIMER_INTER_MAX_DURATION) ) {
			_timer_inter_duration = TIMER_INTER_DEFAULT_DURATION;
			this.getLog().w(TAG, "inter-periods duration is out of bounds " + TIMER_INTER_MIN_DURATION + ".." + TIMER_INTER_MAX_DURATION + ", so it is set to " + _timer_inter_duration);
			return false;
		}
		_timer_inter_duration = t;
		return true;
	}

	/**
	 * setExcluDuration
	 * @param t in minutes
	 * @return
	 */
	public boolean setExcluDuration(final int t) {
		if ( (t < TIMER_EXCLU_MIN_DURATION) || (t > TIMER_EXCLU_MAX_DURATION) ) {
			_timer_exclu_duration = TIMER_EXCLU_DEFAULT_DURATION;
			this.getLog().w(TAG, "exclusion duration is out of bounds " + TIMER_EXCLU_MIN_DURATION + ".." + TIMER_EXCLU_MAX_DURATION + ", so it is set to " + _timer_exclu_duration);
			return false;
		}
		_timer_exclu_duration = t;
		return true;
	}

	/**
	 * initTimer
	public void initTimer(int timer_id) {
		switch (timer_id) {
		case TIMER_ID_MATCH:
//	        _matchTimer = new OneTimer(TIMER_ID_MATCH, _display, OneTimer.DIRECTION_UP, _timer_period_duration);
			break;
		case TIMER_ID_INTER:
//	    	_interTimer = new OneTimer(TIMER_ID_INTER, _display, OneTimer.DIRECTION_DOWN, _timer_inter_duration);
	    	break;
		case TIMER_ID_PAUSE_NONE:
		case TIMER_ID_PAUSE_A:
		case TIMER_ID_PAUSE_B:
//	    	_pauseTimer = new OneTimer(TIMER_ID_PAUSE_NONE, _display, OneTimer.DIRECTION_DOWN, _timer_pause_duration);
	    	break;

		default:
			break;
		}
	}
	 */

	/**
	 * retrieveTimer
	 * @param timer_id
	 * @return
	public boolean retrieveTimer(int timer_id) {
		switch (timer_id) {
		case TIMER_ID_MATCH:
			_matchTimer = OneTimer.getTimerById(TIMER_ID_MATCH);
	    	if (_matchTimer == null) {
	    		_log.d(TAG, "unable to retrieve the matchTimer");
	    		return false;
	    	}
	    	_log.d(TAG, "retrieve matchTimer with current value: "+_matchTimer.getCurrentSeconds()+" seconds");
			break;
		case TIMER_ID_INTER:
			_interTimer = OneTimer.getTimerById(TIMER_ID_INTER);
	    	if (_interTimer == null) {
	    		_log.d(TAG, "unable to retrieve the interTimer");
	    		return false;
	    	}
	    	_log.d(TAG, "retrieve interTimer with current value: "+_interTimer.getCurrentSeconds()+" seconds");
			break;
		case TIMER_ID_PAUSE_A:
		case TIMER_ID_PAUSE_B:
			_pauseTimer = OneTimer.getTimerById(timer_id);
	    	if (_pauseTimer == null) {
	    		_log.d(TAG, "unable to retrieve the pauseTimer");
	    		return false;
	    	}
	    	_log.d(TAG, "retrieve pauseTimer with current value: "+_pauseTimer.getCurrentSeconds()+" seconds");
		default:
			_log.e(TAG, "unknown timer id (" + timer_id + ")");
			return false;
		}
		return true;
	}
	 */

	/**
	 * getTimer
	 * @param timer_id
	 * @return
	public OneTimer getTimer(int timer_id) {
		switch (timer_id) {
		case TIMER_ID_MATCH:
			return _matchTimer;
		case TIMER_ID_INTER:
			return _interTimer;
		case TIMER_ID_PAUSE_NONE:
		case TIMER_ID_PAUSE_A:
		case TIMER_ID_PAUSE_B:
			return _pauseTimer;
		default:
			_log.e(TAG, "unknown timer id (" + timer_id + ")");
			return null;
		}
	}
	 */

	/**
	 * setTimerFromString
	 * @param timer_id
	 * @param val
	public void setTimerFromString(int timer_id, String val) {
		switch (timer_id) {
		case TIMER_ID_MATCH:
//			_matchTimer = OneTimer.OneTimerFromString(_display, val);
			break;
		case TIMER_ID_INTER:
//			_interTimer = OneTimer.OneTimerFromString(_display, val);
			break;
		case TIMER_ID_PAUSE_NONE:
		case TIMER_ID_PAUSE_A:
		case TIMER_ID_PAUSE_B:
//			_pauseTimer = OneTimer.OneTimerFromString(_display, val);
			break;
		default:
			_log.e(TAG, "unknown timer id (" + timer_id + ")");
			break;
		}
	}
	 */

	/**
	 * startMatch
	 */
	public void startMatch() {
		OneTimer matchTimer = _gtv_match_timer.getTimer();
		this.getLog().d(TAG, "startMatch with current duration=" + matchTimer.getCurrentSeconds() + " seconds");
		try {
			if (matchTimer.isPaused()) {
				matchTimer.restartTimer();
			} else {
				matchTimer.startTimer();
				_gbt_pause[0].setEnabled(true);
				_gbt_pause[1].setEnabled(true);
			}
		} catch (TimerStateException e) {
			this.getLog().e(TAG, e.getMessage());
		}
		setPlayingTime(true);
		_gtv_match_timer.setCurrentCounter(TIMER_ID_MATCH, matchTimer.getCurrentSeconds());
		for (int i = 0; i < _players_A.size(); i++) {
			CommonGraphicalPlayer gpl = _players_A.get(i);
			gpl.setEnabled(true);
		}
	}

	/**
	 * pauseMatch
	 */
	public void pauseMatch() {
		OneTimer matchTimer = _gtv_match_timer.getTimer();
		this.getLog().d(TAG, "pauseMatchTimer with current duration="+matchTimer.getCurrentSeconds()+" seconds");
		try {
			matchTimer.pauseTimer();
		} catch (TimerStateException e) {
			this.getLog().e(TAG, e.getMessage());
		}
		setPlayingTime(false);
		_gtv_match_timer.setCurrentCounter(TIMER_ID_MATCH, matchTimer.getCurrentSeconds());
	}

	/**
	 * stopMatch
	 */
	public void stopMatch() {
		OneTimer matchTimer = _gtv_match_timer.getTimer();
		this.getLog().d(TAG, "stopMatchTimer with current duration="+matchTimer.getCurrentSeconds()+" seconds");
		try {
			matchTimer.stopTimer();
			matchTimer.resetTimer();
			_gbt_pause[0].setEnabled(false);
			_gbt_pause[1].setEnabled(false);
			setPlayingTime(false);
			_gtv_match_timer.setCurrentCounter(TIMER_ID_MATCH, matchTimer.getCurrentSeconds());
		} catch (TimerStateException e) {
			this.getLog().e(TAG, e.getMessage());
		}
	}

	/**
     * startPause
     */
	public void startPause(final int team) {
		if (! this.checkTeam(team, "startPause")) return;
		int id = team - TEAM_ID_A;
		OneTimer pauseTimer = _gtv_pause_timer[id].getTimer();
    	this.getLog().d(TAG, "startPauseTimer with team=" + id + " and current duration=" + pauseTimer.getCurrentSeconds() + " seconds");
		if (isPlayingTime()) {
			pauseMatch();
			_gbt_start_stop.setText("Start");
		}
		_gbt_start_stop.setEnabled(false);
		_gbt_pause[1 - id].setEnabled(false);
		setPauseTeamId(id);
		newPause(id);
		_gbt_pause[id].setEnabled(true);
		_gbt_pause[id].setText( 'T' + String.valueOf( getNbPauses(id) ) );
    	try {
    		if (pauseTimer.isPaused()) {
    			pauseTimer.restartTimer();
    		} else {
    			pauseTimer.startTimer();
    		}
		} catch (TimerStateException e) {
			this.getLog().e(TAG, e.getMessage());
		}
    }

    /**
     * stopPause
     */
	public void stopPause(final int team) {
		if (! this.checkTeam(team, "stopPause")) return;
		int id = team - TEAM_ID_A;
		OneTimer pauseTimer = _gtv_pause_timer[id].getTimer();
    	this.getLog().d(TAG, "stopPauseTimer with team=" + id + " and current duration=" + pauseTimer.getCurrentSeconds() + " seconds");
    	try {
    		if (pauseTimer.isStarted()) {
    			pauseTimer.stopTimer();
    		}
	    	pauseTimer.resetTimer();
			_gtv_pause_timer[id].setCurrentCounter(TIMER_ID_MATCH, pauseTimer.getCurrentSeconds());
			_gbt_pause[1 - getPauseTeamId()].setEnabled(true);
			if (! isLastPause(getPauseTeamId())) {
				_gbt_pause[getPauseTeamId()].setEnabled(true);
			} else {
				_gbt_pause[getPauseTeamId()].setEnabled(false);
			}
			_gbt_start_stop.setEnabled(true);
			setPauseTeamId(TEAM_ID_0);
		} catch (TimerStateException e) {
			this.getLog().e(TAG, e.getMessage());
		}
    }

	/**
	 * getTimeHandler
	 * @return
	 */
	public TimeHandler getTimeHandler() {
		return _time_handler;
	}

	/**
	 * startTimeHandler
	 */
	public void startTimeHandler() {
		getTimeHandler().startTimeHandler();
	}

	/**
	 * stopTimeHandler
	 */
	public void stopTimeHandler() {
		getTimeHandler().stopTimeHandler();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonEventDispatchable#onEvent(int, java.lang.String)
	 */
	@Override
	public void onEvent(final int event, final String msg) {
		this.getLog().d(TAG, "event received: " + event + ", msg: " + msg);

		if (event == EVENT_BUTTON_START_STOP) {
			if (isPlayingTime()) {
				this.getLog().d(TAG, "play on-going => stop action");
				pauseMatch();
				_gbt_start_stop.setText("Start");
			} else {
				this.getLog().d(TAG, "play stopped => start action");
				startMatch();
				_gbt_start_stop.setText("Stop");
			}

		} else if ( (event == EVENT_BUTTON_PAUSE_A) || (event == EVENT_BUTTON_PAUSE_B) ) {
			int id_team = event - EVENT_BUTTON_PAUSE_A;
			if (isPauseNone()) {
				this.getLog().d(TAG, "no pause on-going => start new pause for team id " + id_team);
				startPause(id_team);
			} else if (getPauseTeamId() != id_team) {
				this.getLog().d(TAG, "different button than the current pause => do nothing");
			} else {
				this.getLog().d(TAG, "same pause button than the current one => stop the current pause");
				stopPause(getPauseTeamId());
			}

		} else if ( (event >= EVENT_BUTTON_SCORE_MIN) && (event <= EVENT_BUTTON_SCORE_MAX) ) {
			// score for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_MIN;
			CommonGraphicalPlayer gpl = null;
			if (event >= EVENT_BUTTON_SCORE_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				gpl = _players_B.get(id_player);
				gpl.addScore(1);
				_players_B.get(id_player).addScore(1);
				_players_B.get(id_player).addScoreTent(1);
				
			} else {
				_players_A.get(id_player).addScore(1);
				_players_A.get(id_player).addScoreTent(1);
			}
			updateScore(id_team, 3);
			setGtvScoreText(id_team);
			this.getLog().d(TAG, "score for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event >= EVENT_BUTTON_SCORE_TENT_MIN) && (event <= EVENT_BUTTON_SCORE_TENT_MAX) ) {
			// tentative score for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_TENT_MIN;
			if (event >= EVENT_BUTTON_SCORE_TENT_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				_players_B.get(id_player).addScoreTent(1);
				
			} else {
				_players_A.get(id_player).addScoreTent(1);
			}
			this.getLog().d(TAG, "tentative score for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event >= EVENT_BUTTON_SCORE_LF_MIN) && (event <= EVENT_BUTTON_SCORE_LF_MAX) ) {
			// lancer franc for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_LF_MIN;
			if (event >= EVENT_BUTTON_SCORE_LF_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				_players_B.get(id_player).addScoreLf(1);
				_players_B.get(id_player).addScoreLfTent(1);
				
			} else {
				_players_A.get(id_player).addScoreLf(1);
				_players_A.get(id_player).addScoreLfTent(1);
			}
			updateScore(id_team, 1);
			setGtvScoreText(id_team);
			this.getLog().d(TAG, "score lancer franc for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event >= EVENT_BUTTON_SCORE_LF_TENT_MIN) && (event <= EVENT_BUTTON_SCORE_LF_TENT_MAX) ) {
			// tentative lancer franc for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_LF_TENT_MIN;
			if (event >= EVENT_BUTTON_SCORE_LF_TENT_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				_players_B.get(id_player).addScoreLfTent(1);
				
			} else {
				_players_A.get(id_player).addScoreLfTent(1);
			}
			this.getLog().d(TAG, "tentative lancer franc for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event >= EVENT_BUTTON_FAULT_MIN) && (event <= EVENT_BUTTON_FAULT_MAX) ) {
			// fault for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_FAULT_MIN;
			if (event >= EVENT_BUTTON_FAULT_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				_players_B.get(id_player).addFault(1);
				
			} else {
				_players_A.get(id_player).addFault(1);
			}
			this.getLog().d(TAG, "fault for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event == EVENT_SCORE_A) || (event == EVENT_SCORE_B) ) {
			int id_team = event - EVENT_SCORE_A;
			this.getLog().d(TAG, "simple click on score of team " + CLUB_INDEX[id_team]);
			updateScore(id_team, 1);
			setGtvScoreText(id_team);

		} else if ( (event == EVENT_SCORE_A + EVENT_LONGCLICK) || (event == EVENT_SCORE_B + EVENT_LONGCLICK) ) {
			int id_team = event - EVENT_SCORE_A - EVENT_LONGCLICK;
			this.getLog().d(TAG, "long click on score of team " + CLUB_INDEX[id_team]);
			updateScore(id_team, -1);
			setGtvScoreText(id_team);

		} else if (event == EVENT_END_TIMER + TIMER_ID_MATCH) {
			this.getLog().d(TAG, "end timer match");
			setPlayingTime(false);
			if (nextPeriod()) {
				this.getLog().d(TAG, "the previous period " + (getCurrentPeriod()-1) + " was not the last one");
				_gbt_start_stop.setText("Start");
				_gtv_period.setText(String.valueOf(getCurrentPeriod()));
				try {
					_gtv_match_timer.getTimer().resetTimer();
//					_interTimer.startTimer();
				} catch (TimerStateException e2) {
					this.getLog().e(TAG, e2.getMessage());
				}
			} else {
				this.getLog().d(TAG, "this was the last period");
				_gbt_start_stop.setText("End");
				_gbt_start_stop.setEnabled(false);
				_gbt_pause[TEAM_ID_A].setEnabled(false);
				_gbt_pause[TEAM_ID_B].setEnabled(false);
			}

		} else if ( (event == EVENT_END_TIMER + TIMER_ID_PAUSE_A) || (event == EVENT_END_TIMER + TIMER_ID_PAUSE_B) ) {
			this.getLog().d(TAG, "end timer pause with team " + getPauseTeamId());
			stopPause(getPauseTeamId());

		}
	}


}
