/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class AndroidLog implements Loggable {

	private boolean _enable = true;

	/**
	 * Constructor
	 */
	public AndroidLog() {
		super();
	}

	/**
	 * Constructor
	 * @param enable
	 */
	public AndroidLog(boolean enable) {
		super();
		enable(enable);
	}


	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Loggable#enable(boolean)
	 */
	@Override
	public void enable(boolean enable) {
		_enable = enable;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Loggable#d(java.lang.String, java.lang.String)
	 */
	@Override
	public void d(String tag, String msg) {
		if (_enable)
			Log.d(tag, msg);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Loggable#w(java.lang.String, java.lang.String)
	 */
	@Override
	public void w(String tag, String msg) {
		if (_enable)
			Log.w(tag, msg);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Loggable#e(java.lang.String, java.lang.String)
	 */
	@Override
	public void e(String tag, String msg) {
		Log.e(tag, msg);
	}

}
