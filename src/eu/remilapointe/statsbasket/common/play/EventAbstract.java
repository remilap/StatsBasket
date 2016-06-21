/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

import java.util.ArrayList;
import java.util.List;

import eu.remilapointe.statsbasket.sport.DefaultLog;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public abstract class EventAbstract implements Eventable {

	private static String TAG = EventAbstract.class.getSimpleName();

	private List<CommonEventDispatchable> _listeners = new ArrayList<CommonEventDispatchable>();
	private String _name = "Unknown";
	private int _id = 0;
	protected Loggable _log = new DefaultLog();


	/**
	 * Constructor
	 */
	public EventAbstract() {
		super();
	}

	/**
	 * Constructor
	 * @param log
	 */
	public EventAbstract(final Loggable log) {
		this();
		setLog(log);
	}


	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.Eventable#addListener(eu.remilapointe.statsbasket.common.play.CommonEventDispatchable)
	 */
	@Override
	public void addListener(final CommonEventDispatchable dispatch) {
		_listeners.add(dispatch);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.Eventable#dispatchEvent(int, java.lang.String)
	 */
	@Override
	public boolean dispatchEvent(final int event, final String msg) {
		for (int i = 0; i < _listeners.size(); i++) {
			this.getLog().d(TAG, "dispatchEvent to " + _listeners.get(i).getClass().getSimpleName());
			_listeners.get(i).onEvent(event, msg);
		}
		return false;
	}

	/**
	 * getLog
	 * @return
	 */
	public Loggable getLog() {
		return _log;
	}

	/**
	 * setLog
	 * @param log
	 */
	public void setLog(final Loggable log) {
		if (log == null) {
			this.getLog().e(TAG, "setLog with a null log => keep previous value");
			return;
		}
		this._log = log;
	}

	/**
	 * setName
	 * @param name
	 */
	public void setName(final String name) {
		if (name == null || name.length() == 0) {
			this.getLog().e(TAG, "setName with a null or empty name => keep the default value (" + this.getName() + ")");
			return;
		}
		this._name = name;
	}

	/**
	 * getName
	 * @return
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @return the _id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void setId(final int id) {
		this._id = id;
	}

}
