/**
 * 
 */
package eu.remilapointe.statsbasket.timer;

/**
 * @author lapoint1
 *
 */
public interface TimeManageable {

	public final static boolean DIRECTION_UP = true;
	public final static boolean DIRECTION_DOWN = false;
	public final static String DIRECTION_UP_ST = "up";
	public final static String DIRECTION_DOWN_ST = "down";

	/**
	 * setId
	 * @param id
	 * returns void
	 */
	public void setId(int id);

	/**
	 * getId
	 * @return
	 * returns int
	 */
	public int getId();

	/**
	 * setMaxDuration: set the maximum duration in seconds
	 * @param duration
	 * returns void
	 */
	public void setMaxDuration(int duration);

	/**
	 * setDirection
	 * @param up
	 * returns void
	 */
	public void setDirection(boolean up);

	/**
	 * startTimer: start (only the first time) the time duration
	 * returns void
	 */
	public void startTimer() throws TimerStateException;

	/**
	 * pauseTimer: pause (suspend) the timer (will be re-start by restartTimer)
	 * returns void
	 */
	public void pauseTimer() throws TimerStateException;

	/**
	 * restartTimer
	 * returns void
	 */
	public void restartTimer() throws TimerStateException;

	/**
	 * stopTimer: stop the time duration (only when the timer is no more needed)
	 * returns void
	 */
	public void stopTimer() throws TimerStateException;

	/**
	 * resetTimer: reset the timer duration (set to zero, only when the timer is paused)
	 * returns void
	 */
	public void resetTimer() throws TimerStateException;

	/**
	 * setRemainingDuration
	 * @param seconds
	 * returns void
	 */
	public void setRemainingDuration(int seconds);

	/**
	 * getCurrentSeconds: gives the current counter value in seconds
	 * @return
	 * returns int
	 */
	public int getCurrentSeconds();

	/**
	 * getCurrentMilliSeconds: gives the current counter value in milliseconds
	 * @return
	 * returns long
	 */
	public long getCurrentMilliSeconds();

	/**
	 * getStatus
	 * @return
	 * returns int
	 */
	public int getStatus();

	/**
	 * isStarted
	 * @return
	 * returns boolean
	 */
	public boolean isStarted();

	/**
	 * isPaused
	 * @return
	 * returns boolean
	 */
	public boolean isPaused();

}
