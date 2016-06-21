/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;


/**
 * @author lapoint1
 *
 */
public interface Eventable {

	public void addListener(CommonEventDispatchable dispatch);

	public boolean dispatchEvent(int event, String msg);

}
