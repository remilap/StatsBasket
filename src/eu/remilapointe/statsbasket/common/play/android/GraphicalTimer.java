/**
 * 
 */
package eu.remilapointe.statsbasket.common.play.android;

import android.widget.TextView;
import eu.remilapointe.statsbasket.common.play.Eventable;
import eu.remilapointe.statsbasket.common.play.GraphicalUpdatable;
import eu.remilapointe.statsbasket.common.play.TimerLifeCyclable;
import eu.remilapointe.statsbasket.sport.Loggable;
import eu.remilapointe.statsbasket.timer.DisplayUpdatable;
import eu.remilapointe.statsbasket.timer.OneTimer;
import eu.remilapointe.statsbasket.timer.TimeHandler;
import eu.remilapointe.statsbasket.timer.TimerStateException;

/**
 * @author lapoint1
 *
 */
/**
 * @author lapoint1
 *
 */
public class GraphicalTimer extends GraphicalTextView implements GraphicalUpdatable, DisplayUpdatable, TimerLifeCyclable, Eventable {

	protected static String TAG = GraphicalTimer.class.getSimpleName();

	protected OneTimer _timer = null;
	protected int _id = 0;
	


	/**
	 * Constructor
	 */
	public GraphicalTimer(int id) {
		super();
		setId(id);
        OneTimer.setLog(_log);
		_log.d(TAG, "Constructor simple");
	}

	/**
	 * Constructor
	 * @param tv
	 */
	public GraphicalTimer(int id, TextView tv) {
		super(tv);
		setId(id);
        OneTimer.setLog(_log);
		_log.d(TAG, "Constructor with TextView");
	}

	/**
	 * Constructor
	 * @param tv
	 * @param log
	 */
	public GraphicalTimer(int id, TextView tv, Loggable log) {
		super(tv, log);
		setId(id);
        OneTimer.setLog(_log);
		_log.d(TAG, "Constructor with TextView and log");
	}


	/**
	 * setId
	 * @param id
	 */
	public void setId(int id) {
		_id = id;
	}

	/**
	 * getId
	 * @return
	 */
	public int getId() {
		return _id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.teammanagement.activities.common.TimerLifeCyclable#initTimer(int)
	 */
	@Override
	public void initTimer(int duration) {
		if (_timer == null) {
			_log.d(TAG, "initTimer(" + duration + ") for id= " + _id + " and timer null");
	        _timer = new OneTimer(_id, this, OneTimer.DIRECTION_UP, duration);
		} else {
			_log.d(TAG, "initTimer(" + duration + ") for id= " + _id + " and timer not null");
			_timer.setMaxDuration(duration);
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.teammanagement.activities.common.TimerLifeCyclable#getTimer()
	 */
	@Override
	public OneTimer getTimer() {
		_log.d(TAG, "getTimer for id= " + _id);
		return _timer;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.teammanagement.activities.common.TimerLifeCyclable#setTimerFromString(java.lang.String)
	 */
	@Override
	public void setTimerFromString(String val) {
		_log.d(TAG, "setTimerFromString(" + val + ") for id= " + _id);
		_timer = OneTimer.OneTimerFromString(this, val);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.DisplayUpdatable#getTimeHandler()
	 */
	@Override
	public TimeHandler getTimeHandler() {
		_log.d(TAG, "getTimeHandler");
		return TimeHandler.getInstance();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.DisplayUpdatable#setCurrentCounter(int, int)
	 */
	@Override
	public void setCurrentCounter(int id, int duration) {
//		_log.d(TAG, "setCurrentDuration with id=" + id + ", seconds=" + duration);
		int minutes = duration / 60;
		int seconds = duration % 60;
		String display = String.format("%02d", Integer.valueOf(minutes)) + ":" + String.format("%02d", Integer.valueOf(seconds));
		setText(display);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.DisplayUpdatable#endMaxDuration(int)
	 */
	@Override
	public void endMaxDuration(int id) {
		_log.d(TAG, "endMaxDuration(" + id + ") for graphical timer id " + _id);
		try {
			_timer.stopTimer();
		} catch (TimerStateException e1) {
			_log.e(TAG, e1.getMessage());
		}
		dispatchEvent(EVENT_END_TIMER + _id, "EndTimer_" + _id);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.DisplayUpdatable#getName()
	 */
	@Override
	public String getName() {
		return String.valueOf(_id);
	}


}
