/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

/**
 * @author lapoint1
 *
 */
public interface Loggable {

	public void enable(boolean enable);

	public void d(String tag, String msg);

	public void w(String tag, String msg);

	public void e(String tag, String msg);

}
