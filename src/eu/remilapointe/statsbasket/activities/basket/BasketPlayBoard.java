/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import java.util.ArrayList;

import eu.remilapointe.statsbasket.common.play.CommonPlayBoard;
import eu.remilapointe.statsbasket.common.play.android.EventReceiver;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class BasketPlayBoard extends CommonPlayBoard implements BasketEventDispatchable {

	private static final String TAG = BasketPlayBoard.class.getSimpleName();

	private ArrayList<BasketGraphicalPlayer> _players_A = new ArrayList<BasketGraphicalPlayer>();
	private ArrayList<BasketGraphicalPlayer> _players_B = new ArrayList<BasketGraphicalPlayer>();

	private EventReceiver[][] _score_3pt_event = new EventReceiver[2][MAX_PLAYERS];
	private EventReceiver[][] _score_3pt_tent_event = new EventReceiver[2][MAX_PLAYERS];
	private EventReceiver[][] _score_2pt_event = new EventReceiver[2][MAX_PLAYERS];
	private EventReceiver[][] _score_2pt_tent_event = new EventReceiver[2][MAX_PLAYERS];
//	private EndPeriodEvent _end_period_event = null;


	/**
	 * Constructor
	 */
	public BasketPlayBoard() {
		super();
		setLog(null);
		this.getLog().d(TAG, "BasketPlayBoard simple constructor");
	}

	/**
	 * Constructor
	 * @param log
	 */
	public BasketPlayBoard(final Loggable log) {
		super(log);
		this.getLog().d(TAG, "BasketPlayBoard constructor with log");
	}

	/**
	 * setScore3ptEvent
	 * @param team
	 * @param i
	 * @param score3ptEvt
	 */
	public void setScore3ptEvent(final int team, final int i, final EventReceiver score3ptEvt) {
		if (! this.checkTeam(team, "setScore3ptEvent")) return;
		if (! this.checkPlayerId(i, "setScore3ptEvent")) return;
		if (score3ptEvt == null) {
			this.getLog().e(TAG, "setScore3ptEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_3pt_event[id][i] = score3ptEvt;
		_score_3pt_event[id][i].setId(BasketEventDispatchable.EVENT_BUTTON_SCORE_3PT_MIN + id * MAX_PLAYERS + i);
		_score_3pt_event[id][i].setName("score3pt_" + CLUB_INDEX[id] + "_" + i);
		_score_3pt_event[id][i].addListener(this);
	}

	/**
	 * setScore3ptTentEvent
	 * @param team
	 * @param i
	 * @param score3ptTentEvt
	 */
	public void setScore3ptTentEvent(final int team, final int i, final EventReceiver score3ptTentEvt) {
		if (! this.checkTeam(team, "setScore3ptTentEvent")) return;
		if (! this.checkPlayerId(i, "setScore3ptTentEvent")) return;
		if (score3ptTentEvt == null) {
			this.getLog().e(TAG, "setScore3ptTentEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_3pt_tent_event[id][i] = score3ptTentEvt;
		_score_3pt_tent_event[id][i].setId(BasketEventDispatchable.EVENT_BUTTON_SCORE_3PT_TENT_MIN + id * MAX_PLAYERS + i);
		_score_3pt_tent_event[id][i].setName("score3ptTent_" + CLUB_INDEX[id] + "_" + i);
		_score_3pt_tent_event[id][i].addListener(this);
	}

	/**
	 * setScore2ptEvent
	 * @param team
	 * @param i
	 * @param score2ptEvt
	 */
	public void setScore2ptEvent(final int team, final int i, final EventReceiver score2ptEvt) {
		if (! this.checkTeam(team, "setScore2ptEvent")) return;
		if (! this.checkPlayerId(i, "setScore2ptEvent")) return;
		if (score2ptEvt == null) {
			this.getLog().e(TAG, "setScore2ptEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_2pt_event[id][i] = score2ptEvt;
		_score_2pt_event[id][i].setId(BasketEventDispatchable.EVENT_BUTTON_SCORE_2PT_MIN + id * MAX_PLAYERS + i);
		_score_2pt_event[id][i].setName("score2pt_" + CLUB_INDEX[id] + "_" + i);
		_score_2pt_event[id][i].addListener(this);
	}

	/**
	 * setScore2ptTentEvent
	 * @param team
	 * @param i
	 * @param score2ptTentEvt
	 */
	public void setScore2ptTentEvent(final int team, final int i, final EventReceiver score2ptTentEvt) {
		if (! this.checkTeam(team, "setScore2ptTentEvent")) return;
		if (! this.checkPlayerId(i, "setScore2ptTentEvent")) return;
		if (score2ptTentEvt == null) {
			this.getLog().e(TAG, "setScore2ptTentEvent with a null EventReceiver");
			return;
		}
		int id = team - TEAM_ID_A;
		_score_2pt_tent_event[id][i] = score2ptTentEvt;
		_score_2pt_tent_event[id][i].setId(BasketEventDispatchable.EVENT_BUTTON_SCORE_2PT_TENT_MIN + id * MAX_PLAYERS + i);
		_score_2pt_tent_event[id][i].setName("score2ptTent_" + CLUB_INDEX[id] + "_" + i);
		_score_2pt_tent_event[id][i].addListener(this);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonEventDispatchable#onEvent(int, java.lang.String)
	 */
	@Override
	public void onEvent(final int event, final String msg) {
		this.getLog().d(TAG, "event received: " + event + ", msg: " + msg);

		if ( (event >= EVENT_BUTTON_SCORE_3PT_MIN) && (event <= EVENT_BUTTON_SCORE_3PT_MAX) ) {
			// score 3pt for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_3PT_MIN;
			BasketGraphicalPlayer gpl = null;
			if (event >= EVENT_BUTTON_SCORE_3PT_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				gpl = _players_B.get(id_player);
				gpl.addScore3pt(1);
				_players_B.get(id_player).addScore3pt(1);
				_players_B.get(id_player).addScore3ptTent(1);
				
			} else {
				_players_A.get(id_player).addScore3pt(1);
				_players_A.get(id_player).addScore3ptTent(1);
			}
			updateScore(id_team, 3);
			setGtvScoreText(id_team);
			this.getLog().d(TAG, "score 3pt for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event >= EVENT_BUTTON_SCORE_3PT_TENT_MIN) && (event <= EVENT_BUTTON_SCORE_3PT_TENT_MAX) ) {
			// tentative score 3pt for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_3PT_TENT_MIN;
			if (event >= EVENT_BUTTON_SCORE_3PT_TENT_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				_players_B.get(id_player).addScore3ptTent(1);
				
			} else {
				_players_A.get(id_player).addScore3ptTent(1);
			}
			this.getLog().d(TAG, "tentative score 3pt for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event >= EVENT_BUTTON_SCORE_2PT_MIN) && (event <= EVENT_BUTTON_SCORE_2PT_MAX) ) {
			// score 2pt for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_2PT_MIN;
			if (event >= EVENT_BUTTON_SCORE_2PT_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				_players_B.get(id_player).addScore2pt(1);
				_players_B.get(id_player).addScore2ptTent(1);
				
			} else {
				_players_A.get(id_player).addScore2pt(1);
				_players_A.get(id_player).addScore2ptTent(1);
			}
			updateScore(id_team, 2);
			setGtvScoreText(id_team);
			this.getLog().d(TAG, "score 2pt for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else if ( (event >= EVENT_BUTTON_SCORE_2PT_TENT_MIN) && (event <= EVENT_BUTTON_SCORE_2PT_TENT_MAX) ) {
			// tentative score 2pt for a player of team A or B
			int id_team = TEAM_ID_A;
			int id_player = event - EVENT_BUTTON_SCORE_2PT_TENT_MIN;
			if (event >= EVENT_BUTTON_SCORE_2PT_TENT_MIN + MAX_PLAYERS) {
				id_team = TEAM_ID_B;
				id_player -= MAX_PLAYERS;
				_players_B.get(id_player).addScore2ptTent(1);
				
			} else {
				_players_A.get(id_player).addScore2ptTent(1);
			}
			this.getLog().d(TAG, "tentative score 2pt for player id " + id_player + " of team " + CLUB_INDEX[id_team]);

		} else {
			super.onEvent(event, msg);

		}
	}


}
