/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

/**
 * @author lapoint1
 *
 */
public interface CommonEventDispatchable {

	public static final int EVENT_BUTTON_START_STOP = 1;
	public static final int EVENT_BUTTON_PAUSE_A = 2;
	public static final int EVENT_BUTTON_PAUSE_B = 3;
	public static final int EVENT_SCORE_A = 4;
	public static final int EVENT_SCORE_B = 5;
	public static final int EVENT_LONGCLICK = 900;
	public static final int EVENT_END_TIMER = 50;

	public static final int EVENT_BUTTON_SCORE_MIN = 100;
	public static final int EVENT_BUTTON_SCORE_MAX = EVENT_BUTTON_SCORE_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public static final int EVENT_BUTTON_SCORE_TENT_MIN = EVENT_BUTTON_SCORE_MAX + 1;
	public static final int EVENT_BUTTON_SCORE_TENT_MAX = EVENT_BUTTON_SCORE_TENT_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public static final int EVENT_BUTTON_SCORE_LF_MIN = EVENT_BUTTON_SCORE_TENT_MAX + 1;
	public static final int EVENT_BUTTON_SCORE_LF_MAX = EVENT_BUTTON_SCORE_LF_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public static final int EVENT_BUTTON_SCORE_LF_TENT_MIN = EVENT_BUTTON_SCORE_LF_MAX + 1;
	public static final int EVENT_BUTTON_SCORE_LF_TENT_MAX = EVENT_BUTTON_SCORE_LF_TENT_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public static final int EVENT_BUTTON_FAULT_MIN = EVENT_BUTTON_SCORE_LF_TENT_MAX + 1;
	public static final int EVENT_BUTTON_FAULT_MAX = EVENT_BUTTON_FAULT_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public void onEvent(int event, String msg);

}
