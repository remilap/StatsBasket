/**
 * 
 */
package eu.remilapointe.statsbasket.timer;

/**
 * @author lapoint1
 *
 */
public interface DisplayUpdatable {

	public TimeHandler getTimeHandler();

	/**
	 * setCurrentCounter: the current counter value in seconds
	 * @param id
	 * @param duration
	 * returns void
	 */
	public void setCurrentCounter(int id, int duration);

	/**
	 * endMaxDuration: indicates that the timer has arrived to its max value
	 * @param id
	 * returns void
	 */
	public void endMaxDuration(int id);

	/**
	 * getName: give the name of the recipient
	 * @return
	 * returns String
	 */
	public String getName();

}
