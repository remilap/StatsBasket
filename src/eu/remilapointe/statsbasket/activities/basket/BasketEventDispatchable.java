/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import eu.remilapointe.statsbasket.common.play.CommonEventDispatchable;
import eu.remilapointe.statsbasket.common.play.CommonPlayBoard;

/**
 * @author lapoint1
 *
 */
public interface BasketEventDispatchable extends CommonEventDispatchable {

	public static final int EVENT_BUTTON_SCORE_3PT_MIN = EVENT_BUTTON_FAULT_MAX + 1;
	public static final int EVENT_BUTTON_SCORE_3PT_MAX = EVENT_BUTTON_SCORE_3PT_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public static final int EVENT_BUTTON_SCORE_3PT_TENT_MIN = EVENT_BUTTON_SCORE_3PT_MAX + 1;
	public static final int EVENT_BUTTON_SCORE_3PT_TENT_MAX = EVENT_BUTTON_SCORE_3PT_TENT_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public static final int EVENT_BUTTON_SCORE_2PT_MIN = EVENT_BUTTON_SCORE_3PT_TENT_MIN + 1;
	public static final int EVENT_BUTTON_SCORE_2PT_MAX = EVENT_BUTTON_SCORE_2PT_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

	public static final int EVENT_BUTTON_SCORE_2PT_TENT_MIN = EVENT_BUTTON_SCORE_2PT_MAX + 1;
	public static final int EVENT_BUTTON_SCORE_2PT_TENT_MAX = EVENT_BUTTON_SCORE_2PT_TENT_MIN + CommonPlayBoard.MAX_PLAYERS * 2 - 1;

}
