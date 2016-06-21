/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import eu.remilapointe.statsbasket.common.play.CommonPlayBoard;
import eu.remilapointe.statsbasket.timer.DisplayUpdatable;
import eu.remilapointe.statsbasket.timer.OneTimer;
import eu.remilapointe.statsbasket.timer.TimeHandler;

/**
 * @author lapoint1
 *
 */
public class BasketPlay extends CommonPlayBoard implements DisplayUpdatable {

	private static final String TAG = BasketPlay.class.getSimpleName();

	public static final int ACTION_SHOOT_1P = 1;
	public static final int ACTION_SHOOT_2P = 2;
	public static final int ACTION_SHOOT_3P = 3;
	public static final int ACTION_REBOND_OFF = 4;
	public static final int ACTION_REBOND_DEF = 5;
	public static final int ACTION_MARCHER = 6;
	public static final int ACTION_DRIBLE = 7;
	public static final int ACTION_FAUTE = 8;
	public static final int ACTION_FAUTE_TECH = 9;
	public static final int ACTION_PASS_FORCE = 10;
	public static final int ACTION_BALL_LOST = 11;
	public static final int ACTION_INTERCEPTION = 12;


	protected List<BasketScoringBoardPlayer> _player_lines = new ArrayList<BasketScoringBoardPlayer>();



	/**
	 * Constructor
	 */
	public BasketPlay() {
		super();

	}


	/**
	 * initFakePlayerLines
	 */
	public void initFakePlayerLines() {
		for (int p = 0; p < MAX_PLAYERS; p++) {
        	_player_lines.add( new BasketScoringBoardPlayer("A toto", p+1, ".") );
        }
	}

	/**
	 * getPlayerLines
	 * @return
	 */
	public List<BasketScoringBoardPlayer> getPlayerLines() {
		return _player_lines;
	}

	/**
	 * setPlayerLines
	 * @param players
	 */
	public boolean setPlayerLines(List<BasketScoringBoardPlayer> players) {
		_player_lines = players;
		return true;
	}

	/**
	 * addOnePlayer
	 * @param name
	 * @param num
	 * @return
	 */
	public boolean addOnePlayer(String name, int num) {
		boolean res = _player_lines.add( new BasketScoringBoardPlayer(name, num, "") );
		return res;
	}


	@Override
	public void setCurrentCounter(int id, int duration) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void endMaxDuration(int id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
