/**
 * 
 */
package eu.remilapointe.statsbasket.timer.android;

import eu.remilapointe.statsbasket.timer.Handlerable;
import android.os.Handler;

/**
 * @author lapoint1
 *
 */
public class AndroidHandler extends Handler implements Handlerable {


	/**
	 * Constructor
	 */
	public AndroidHandler() {
		super();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.Handlerable#getNewHandler()
	 */
	@Override
	public Handlerable getNewHandler() {
		return new AndroidHandler();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.Handlerable#postDelayed(eu.remilapointe.statsbasket.timer.Handlerable, int)
	 */
	@Override
	public void postDelayed(Runnable timeHandler, int _running_period) {
		super.postDelayed(timeHandler, _running_period);
	}

}
