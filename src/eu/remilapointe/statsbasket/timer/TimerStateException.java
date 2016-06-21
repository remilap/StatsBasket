/**
 * 
 */
package eu.remilapointe.statsbasket.timer;

/**
 * @author lapoint1
 *
 */
public class TimerStateException extends Exception {

	private static final long serialVersionUID = 1L;

	public TimerStateException(OneTimer curTimer, String oper, int reqState) {
		super(oper+" the "+curTimer.getString()+", its state is "+curTimer.getStatusString()+" but required state is "+OneTimer.statusToString(reqState));
	}

}
