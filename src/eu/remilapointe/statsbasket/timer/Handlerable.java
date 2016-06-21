/**
 * 
 */
package eu.remilapointe.statsbasket.timer;

/**
 * @author lapoint1
 *
 */
public interface Handlerable {

	public Handlerable getNewHandler();

	public void postDelayed(Runnable timeHandler, int _running_period);

	public void removeCallbacks(Runnable timeHandler);

}
