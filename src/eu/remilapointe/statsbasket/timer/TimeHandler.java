/**
 * 
 */
package eu.remilapointe.statsbasket.timer;

import java.util.ArrayList;

import eu.remilapointe.statsbasket.sport.DefaultLog;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class TimeHandler implements Runnable {

	public final static int DEFAULT_TIMER_RUNNING_PERIOD = 100;   // milliseconds

	private static final String TAG = TimeHandler.class.getSimpleName();

	private static TimeHandler _instance = null;
	private Handlerable _handler = null;
	private boolean _timeHandlerStarted = false;
	private Loggable _log = new DefaultLog(false);
	private int _running_period = DEFAULT_TIMER_RUNNING_PERIOD;   // milliseconds
	private ArrayList<TimerUpdatable> _tu_listeners = new ArrayList<TimerUpdatable>();

//	private int _nbPass = 0;


	/**
	 * Private Constructor
	 * TimeHandler
	 */
	private TimeHandler() {
		super();
	}

	/**
	 * getInstance
	 * @return TimeHandler
	 */
	public static TimeHandler getInstance() {
		if (_instance == null) {
			_instance = new TimeHandler();
		}
		return _instance;
	}

	/**
	 * getInstance
	 * @param handler
	 * @param log
	 * @param running_period_milli
	 * @return
	 */
	public static TimeHandler getInstance(Handlerable handler, Loggable log, int running_period_milli) {
		_instance = getInstance();
		_instance.setHandler(handler);
		_instance.setLog(log);
		_instance.setRunningPeriod(running_period_milli);
		return _instance;
	}

	/**
	 * setHandler
	 * @param handler
	 */
	public void setHandler(Handlerable handler) {
		_handler = handler;
	}

	/**
	 * setLog
	 * @param log
	 */
	public void setLog(Loggable log) {
		_log = log;
	}

	/**
	 * setRunningPeriod
	 * @param running_period   // milliseconds
	 * returns void
	 */
	public void setRunningPeriod(int running_period) {
		_running_period = running_period;
	}

	/**
	 * setTimerListener
	 * @param tu
	 * @return
	 * returns boolean
	 */
	public boolean setTimerListener(TimerUpdatable tu) {
		return _tu_listeners.add(tu);
	}

	/**
	 * startTimeHandler
	 * returns void
	 */
	public void startTimeHandler() {
		if (! _timeHandlerStarted) {
			_handler.postDelayed(this, _running_period);
			_timeHandlerStarted = true;
			_log.d(TAG, "The timer handler is started");
		}
	}

	/**
	 * stopTimeHandler
	 * returns void
	 */
	public void stopTimeHandler() {
		/*
		// check if all declared timers are stopped
		for (int i = 0; i < _nb_timer; i++) {
			OneTimer curTimer = OneTimer._timersList[i];
			if (curTimer._timerState == TIMER_STATE_STARTED || curTimer._timerState == TIMER_STATE_PAUSED) {
				Log.d(TAG, "The timer handler is not stopped because the "+curTimer.getString()+" is not stopped (status is "+curTimer.getStatusString()+")");
				return;
			}
		}
		*/
		_handler.removeCallbacks(this);
		_timeHandlerStarted = false;
		_log.d(TAG, "The timer handler is stopped");
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		_handler.removeCallbacks(this);
		_handler.postDelayed(this, _running_period);

		for (TimerUpdatable timer : _tu_listeners) {
			timer.timerUpdate();
		}
	}
		/*
		nbpass++;
		nbrunning = 0;
		for (int i = 0; i < _nb_timer; i++) {
			OneTimer curTimer = _timersList[i];
//			if (nbpass >= 20) {
//				Log.d(TAG, "TOUS TIMER id="+curTimer._myId+" is "+curTimer.getStatusString());
//			}
			if (curTimer.isStarted()) {
				nbrunning++;
				int elapse = curTimer.calculateElapseTimeSeconds();
//				if (nbpass >= 20) {
//					Log.d(TAG, "timer with id="+curTimer._myId+" is running, value="+curTimer.getCurrentSeconds());
//				}
				curTimer._timeDisplay.setCurrentCounter(curTimer._myId, curTimer.getCurrentSeconds());
//				Log.d(TAG, "run with direction up, duration="+duration+", maxDuration="+_maxDuration);
				if (elapse >= curTimer._maxDuration) {
					Log.d(TAG, curTimer.getString()+" has reached max value:"+curTimer._maxDuration);
					curTimer._timeDisplay.endMaxDuration(curTimer._myId);
				}
			}
		}
		if (nbpass >= 20) {
			Log.d(TAG, "TimeHandler is active with "+nbrunning+" running timer(s) on a total of "+_nb_timer);
			nbpass = 0;
		}
/ *			} else {
			duration = _maxDuration - _timerDuration - seconds;
			_timeDisplay.setCurrentDuration(_myId, duration);
//			Log.d(TAG, "run with direction down, duration="+duration+", maxDuration="+_maxDuration);
			if (duration > 0) {
				_instance.postDelayed(this, DEFAULT_TIMER_RUNNING_PERIOD);
			} else {
				Log.d(TAG, "down timer has reached zero value");
				_instance.removeCallbacks(this);
				_timeDisplay.endMaxDuration(_myId);
			}
		}
* ///			Log.d(TAG, "match time run at "+Math.round(curTime/100)/10+" seconds, current duration: "+duration+" seconds");
//		Log.d(TAG, "call setCurrentDuration with id="+_myId+", timeDisp="+_timeDisplay.getName());
	}
		*/

}
