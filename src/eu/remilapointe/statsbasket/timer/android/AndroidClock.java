/**
 * 
 */
package eu.remilapointe.statsbasket.timer.android;

import android.os.SystemClock;

/**
 * @author lapoint1
 *
 */
public final class AndroidClock {

	/**
	 * elapsedRealtime
	 * @return
	 */
	public static long elapsedRealtime() {
		return SystemClock.elapsedRealtime();
	}

}
