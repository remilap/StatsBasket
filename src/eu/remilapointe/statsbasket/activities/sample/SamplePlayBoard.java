/**
 * 
 */
package eu.remilapointe.statsbasket.activities.sample;

import eu.remilapointe.statsbasket.common.play.CommonPlayBoard;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class SamplePlayBoard extends CommonPlayBoard {

	private static final String TAG = SamplePlayBoard.class.getSimpleName();

	public static final int ACTION_SCORE_1P = 1;
	public static final int ACTION_FAUTE = 8;



	/**
	 * Constructor
	 */
	public SamplePlayBoard() {
		super();
	}

	/**
	 * Constructor
	 * @param log
	 */
	public SamplePlayBoard(Loggable log) {
		super(log);
		log.d(TAG, "SamplePlayBoard constructor");
	}



}
