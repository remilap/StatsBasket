/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

import eu.remilapointe.statsbasket.timer.OneTimer;

/**
 * @author lapoint1
 *
 */
public interface TimerLifeCyclable {

	public void initTimer(int duration);

	public OneTimer getTimer();

	public void setTimerFromString(String val);

}
