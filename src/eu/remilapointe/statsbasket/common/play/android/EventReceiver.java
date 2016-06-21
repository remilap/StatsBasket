/**
 * 
 */
package eu.remilapointe.statsbasket.common.play.android;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import eu.remilapointe.statsbasket.common.play.EventAbstract;
import eu.remilapointe.statsbasket.common.play.CommonEventDispatchable;
import eu.remilapointe.statsbasket.common.play.Eventable;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class EventReceiver extends EventAbstract implements Eventable, OnClickListener, OnLongClickListener {

	private static final String TAG = EventReceiver.class.getSimpleName();

	private int _previous_long_click_id = 0;


	/**
	 * Constructor
	 */
	public EventReceiver() {
		super();
	}

	/**
	 * Constructor
	 * @param log
	 */
	public EventReceiver(Loggable log) {
		super(log);
	}


	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		_log.d(TAG, "EventReceiver:onClick with id " + getId() + ", named " + getName());
		if (getId() == _previous_long_click_id) {
			_previous_long_click_id = 0;
			_log.d(TAG, "same id than previous long clik => don't dispatch the event");
		} else {
			dispatchEvent(getId(), getName());
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View view) {
		_log.d(TAG, "EventReceiver:onLongClick with id " + getId() + ", named " + getName());
		_previous_long_click_id = getId();
		dispatchEvent(CommonEventDispatchable.EVENT_LONGCLICK + getId(), getName());
		return false;
	}

}
