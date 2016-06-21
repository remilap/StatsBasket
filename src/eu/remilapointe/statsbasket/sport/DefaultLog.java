/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

/**
 * @author lapoint1
 *
 */
public class DefaultLog implements Loggable {

	private boolean _enable = true;

	/**
	 * Constructor
	 */
	public DefaultLog() {
		super();
	}

	/**
	 * Constructor
	 * @param enable
	 */
	public DefaultLog(boolean enable) {
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
			System.out.println(tag + ": " + msg);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Loggable#w(java.lang.String, java.lang.String)
	 */
	@Override
	public void w(String tag, String msg) {
		if (_enable)
			System.out.println("WARNING " + tag + ": " + msg);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Loggable#e(java.lang.String, java.lang.String)
	 */
	@Override
	public void e(String tag, String msg) {
		System.err.println(tag + ": " + msg);
	}

}
