/**
 * 
 */
package eu.remilapointe.statsbasket.timer;

import java.util.StringTokenizer;

import eu.remilapointe.statsbasket.sport.DefaultLog;
import eu.remilapointe.statsbasket.sport.Loggable;
import eu.remilapointe.statsbasket.timer.android.AndroidClock;

/**
 * @author lapoint1
 *
 */
public class OneTimer implements TimeManageable, TimerUpdatable {

	private final static String TAG = OneTimer.class.getSimpleName();
	private final static int TIMER_STATE_NOT_INIT = 0;
	private final static int TIMER_STATE_INIT = 1;
	private final static int TIMER_STATE_STARTED = 2;
	private final static int TIMER_STATE_PAUSED = 3;
	private final static int TIMER_STATE_STOPPED = 4;
//	private final static int TIMER_RUNNING_PERIOD = 100;   // milliseconds
	private final static int MAX_TIMERS = 20;

	private static int _nextId = 1;
	private static OneTimer[] _timersList = new OneTimer[MAX_TIMERS];
	private static int _nb_timer = 0;

	private int _myId = 0;
	private long _lastTime = 0L;           // milliseconds
	private long _elapseTimeMilli = 0L;    // milliseconds
	private int _elapseTimeSeconds = 0;    // seconds
	private long _currentTimeMilli = 0L;    // milliseconds
	private int _currentTimeSeconds = 0;    // seconds
	private int _maxDuration = 0;          // seconds
	private boolean _direction = DIRECTION_UP;     // upgrade counter
	private DisplayUpdatable _timeDisplay = null;
	private int _timerState = TIMER_STATE_NOT_INIT;

	private static Loggable _log = new DefaultLog(false);


	/**
	 * getTimerById
	 * @param id
	 * @return
	 * returns OneTimer
	 */
	public static OneTimer getTimerById(int id) {
		for (int i = 0; i < _nb_timer; i++) {
			OneTimer curTimer = OneTimer._timersList[i];
			if (curTimer.getId() == id) {
				_log.d(TAG, "getTimerById found with id="+id+" and number "+i);
				return curTimer;
			}
		}
		_log.e(TAG, "getTimerById not found with id="+id);
		return null;
	}

	/**
	 * OneTimer
	 */
	public OneTimer() {
		super();
		_myId = _nextId++;
		newTimer();
		_log.d(TAG, "new OneTimer (numbered "+_nb_timer+") with id="+_myId);
	}

	/**
	 * OneTimer
	 * @param id
	 */
	public OneTimer(int id, DisplayUpdatable timeDisp, boolean direction, int durationSeconds) {
		super();
		_myId = id;
		_timeDisplay = timeDisp;
		_direction = direction;
		_maxDuration = durationSeconds;
		_timerState = TIMER_STATE_INIT;
		_log.d(TAG, "new OneTimer (numbered "+_nb_timer+") with given id="+_myId+", timeDisp="+_timeDisplay.getName()+", direction "+getDirectionString()+", max duration="+_maxDuration+" seconds");
		newTimer();
	}

	/**
	 * newTimer
	 * returns void
	 */
	private void newTimer() {
		_timersList[_nb_timer++] = this;
		_timeDisplay.getTimeHandler().setTimerListener(this);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimerUpdatable#timerUpdate()
	 */
	@Override
	public void timerUpdate() {
		if (! isStarted()) {
			return;
		}
		int elapse = calculateElapseTimeSeconds();
//		if (nbpass >= 20) {
//			_log.d(TAG, "timer with id="+curTimer._myId+" is running, value="+curTimer.getCurrentSeconds());
//		}
		_timeDisplay.setCurrentCounter(_myId, getCurrentSeconds());
//		_log.d(TAG, "run with direction up, duration="+duration+", maxDuration="+_maxDuration);
		if (elapse >= _maxDuration) {
			_log.d(TAG, getString()+" has reached max value:"+_maxDuration);
			_timeDisplay.endMaxDuration(_myId);
		}
	}

	/**
	 * saveToString
	 * @return
	 * returns String
	 */
	public String saveToString() {
		StringBuffer sb = new StringBuffer();
		sb.append(_myId);
		sb.append(";");
		sb.append(getDirectionString());
		sb.append(";");
		sb.append(_maxDuration);
		sb.append(";");
		sb.append(_timerState);
		sb.append(";");
		sb.append(_elapseTimeMilli);
		_log.d(TAG, "saveToString: "+sb.toString());
		if (_timerState == TIMER_STATE_STARTED) {
			try {
				this.pauseTimer();
			} catch (TimerStateException e) {
				_log.e(TAG, "During saveToString, "+e.getMessage());
			}
		}
		// TODO
//		OneTimer.stopTimeHandler();
		return sb.toString();
	}

	/**
	 * OneTimerFromString
	 * @param timeDisp
	 * @param save
	 * @return
	 * returns OneTimer
	 */
	public static OneTimer OneTimerFromString(DisplayUpdatable timeDisp, String save) {
		StringTokenizer st = new StringTokenizer(save, ";");
		int nbTokens = st.countTokens();
		_log.d(TAG, "OneTimerFromString with save: (nb token="+nbTokens+") "+save);
		int id = 0;
		boolean dir = DIRECTION_DOWN;
		int duration = 0;
		int state = 0;
		long elapse = 0L;
		String elem = null;
		for (int i = 0; i < nbTokens; i++) {
			elem = st.nextToken();
//			_log.d(TAG, "token no "+i+" contenu: "+elem);
			switch (i) {
			case 0:
				try {
					id = Integer.parseInt(elem);
//					_log.d(TAG, "token 0: id = "+id);
				} catch (NumberFormatException e) {
					_log.e(TAG, "from \""+save+"\" saved String, the id (1st element) is not an integer");
				}
				break;
			case 1:
				dir = (DIRECTION_UP_ST.equalsIgnoreCase(elem) ? DIRECTION_UP : DIRECTION_DOWN);
//				_log.d(TAG, "token 1: dir = "+dir);
				break;
			case 2:
				try {
					duration = Integer.parseInt(elem);
//					_log.d(TAG, "token 2: duration = "+duration);
				} catch (NumberFormatException e) {
					_log.e(TAG, "from \""+save+"\" saved String, the duration (3rd element) is not an integer");
				}
				break;
			case 3:
				try {
					state = Integer.parseInt(elem);
//					_log.d(TAG, "token 3: state = "+state);
				} catch (NumberFormatException e) {
					_log.e(TAG, "from \""+save+"\" saved String, the state (4th element) is not an integer");
				}
				break;
			case 4:
				try {
					elapse = Integer.parseInt(elem);
//					_log.d(TAG, "token 4: elapse = "+elapse);
				} catch (NumberFormatException e) {
					_log.e(TAG, "from \""+save+"\" saved String, the elapse time (5th element) is not a long integer");
				}
				break;

			default:
				_log.e(TAG, "unknown token no "+i+" contenu: "+elem);
				break;
			}
		}
		OneTimer tim = OneTimer.getTimerById(id);
		if (tim == null) {
			tim = new OneTimer(id, timeDisp, dir, duration);
		} else {
			tim._timeDisplay = timeDisp;
		}
		tim._timerState = state;
		tim._elapseTimeMilli = elapse;
		if (state == TIMER_STATE_STARTED) {
			// TODO
//			OneTimer.startTimeHandler();
		}
		return tim;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#setId(int)
	 */
	@Override
	public void setId(int id) {
		_myId = id;
		_log.d(TAG, "setId "+getString()+" with id="+_myId);
	}

	/**
	 * getId
	 * @return
	 * returns int
	 */
	@Override
	public int getId() {
//		_log.d(TAG, "getId "+getString()+" returns id="+_myId);
		return _myId;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#setMaxDuration(int)
	 */
	@Override
	public void setMaxDuration(int duration) {
		_maxDuration = duration;
		_timerState = TIMER_STATE_INIT;
		_log.d(TAG, "setMaxDuration "+getString()+" with "+_maxDuration+" seconds");
	}

	/**
	 * getMaxDuration
	 * @return
	 * returns int
	 */
	public int getMaxDuration() {
		return _maxDuration;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#setDirection(boolean)
	 */
	@Override
	public void setDirection(boolean up) {
		_direction = up;
		_log.d(TAG, "setDirection from boolean: "+getDirectionString());
	}

	/**
	 * setDirection
	 * @param dir
	 * returns void
	 */
	public void setDirection(String dir) {
		_direction = DIRECTION_DOWN;
		if (DIRECTION_UP_ST.equalsIgnoreCase(dir)) {
			_direction = DIRECTION_UP;
		}
		_log.d(TAG, "setDirection from String: "+getDirectionString());
	}

	/**
	 * getDirectionString
	 * @return
	 * returns String
	 */
	public String getDirectionString() {
		String dir = (_direction ? DIRECTION_UP_ST : DIRECTION_DOWN_ST);
		return dir;
	}

	/**
	 * calculateElapseTimeSeconds
	 * @return
	 * returns int
	 */
	protected int calculateElapseTimeSeconds() {
		long curTime = getCurrentTime();
		_elapseTimeMilli += curTime - _lastTime;
		_elapseTimeSeconds = (int) (_elapseTimeMilli / 1000);
		_lastTime = curTime;
		return _elapseTimeSeconds;
	}

	/**
	 * getElapseTimeSeconds
	 * @return
	 * returns int
	 */
	protected int getElapseTimeSeconds() {
		return _elapseTimeSeconds;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#getDuration()
	 */
	@Override
	public int getCurrentSeconds() {
		getCurrentMilliSeconds();
//		_log.d(TAG, "getCurrentSeconds for "+getString()+" returns "+_currentTimeSeconds+" seconds");
		return _currentTimeSeconds;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#getDurationMilli()
	 */
	@Override
	public long getCurrentMilliSeconds() {
		// upgrade counter => elapse time
		_currentTimeMilli = _elapseTimeMilli;
		if (_direction == DIRECTION_DOWN) {
			// downgrade counter => max duration - elapse time
			_currentTimeMilli = (_maxDuration * 1000) - _elapseTimeMilli;
		}
		_currentTimeSeconds = (int) (_currentTimeMilli / 1000);
//		long milli = _currentTimeMilli - (_currentTimeSeconds * 1000);
//		_log.d(TAG, "getCurrentMilliSeconds for "+getString()+" returns "+_currentTimeMilli+" miliseconds, or "+_currentTimeSeconds+","+milli+" seconds");
		return _currentTimeMilli;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#startTimer()
	 */
	@Override
	public void startTimer() throws TimerStateException {
		if (_timerState != TIMER_STATE_INIT) {
			throw new TimerStateException(this, "start", TIMER_STATE_INIT);
		}
		_timerState = TIMER_STATE_STARTED;
		_lastTime = getCurrentTime();
		_elapseTimeMilli = 0L;
		_elapseTimeSeconds = 0;
		_log.d(TAG, "start "+getString()+" at "+Math.round(_lastTime/1000)+" seconds");
		// TODO
//		startTimeHandler();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#pauseTimer()
	 */
	@Override
	public void pauseTimer() throws TimerStateException {
		if (_timerState != TIMER_STATE_STARTED) {
			throw new TimerStateException(this, "pause", TIMER_STATE_STARTED);
		}
		_timerState = TIMER_STATE_PAUSED;
		int elapse = calculateElapseTimeSeconds();
		_log.d(TAG, "pause "+getString()+" at "+Math.round(_lastTime/1000)+" seconds, current duration: "+elapse+" seconds");
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#restartTimer()
	 */
	@Override
	public void restartTimer() throws TimerStateException {
		if (_timerState != TIMER_STATE_PAUSED) {
			throw new TimerStateException(this, "restart", TIMER_STATE_PAUSED);
		}
		_timerState = TIMER_STATE_STARTED;
		_lastTime = getCurrentTime();
		_log.d(TAG, "restart "+getString()+" at "+Math.round(_lastTime/1000)+" seconds, current duration: "+getElapseTimeSeconds()+" seconds");
		// TODO
//		startTimeHandler();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#stopTimer()
	 */
	@Override
	public void stopTimer() throws TimerStateException {
		if (_timerState != TIMER_STATE_STARTED) {
			throw new TimerStateException(this, "stop", TIMER_STATE_STARTED);
		}
		_timerState = TIMER_STATE_STOPPED;
		int elapse = calculateElapseTimeSeconds();
		_log.d(TAG, "stop "+getString()+" at "+Math.round(_lastTime/1000)+" seconds, current duration: "+elapse+" seconds");
		// TODO
//		stopTimeHandler();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#resetTimer()
	 */
	@Override
	public void resetTimer() throws TimerStateException {
		if (_timerState != TIMER_STATE_STOPPED) {
			throw new TimerStateException(this, "reset", TIMER_STATE_STOPPED);
		}
		_timerState = TIMER_STATE_INIT;
		_lastTime = getCurrentTime();
		_elapseTimeMilli = 0L;
		_elapseTimeSeconds = 0;
		_log.d(TAG, "reset "+getString()+" at "+Math.round(_lastTime/1000)+" seconds");
		_timeDisplay.setCurrentCounter(_myId, 0);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#setRemainingDuration(int)
	 */
	@Override
	public void setRemainingDuration(int seconds) {
		_elapseTimeSeconds = seconds;
	}

	/**
	 * getString
	 * @return
	 * returns String
	 */
	public String getString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDirectionString());
		sb.append("grade timer index ");
		sb.append(_myId);
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#getStatus()
	 */
	@Override
	public int getStatus() {
		return _timerState;
	}

	/**
	 * getStatusString
	 * @return
	 * returns String
	 */
	public String getStatusString() {
		return OneTimer.statusToString(_timerState);
	}

	/**
	 * statusToString
	 * @param status
	 * @return
	 * returns String
	 */
	public static String statusToString(int status) {
		String res = null;
		switch (status) {
		case TIMER_STATE_NOT_INIT:
			res = "not initialized";
			break;
		case TIMER_STATE_INIT:
			res = "just initialized";
			break;
		case TIMER_STATE_STARTED:
			res = "started";
			break;
		case TIMER_STATE_PAUSED:
			res = "paused";
			break;
		case TIMER_STATE_STOPPED:
			res = "stopped";
			break;
		default:
			res = "unknown";
			break;
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#isStarted()
	 */
	@Override
	public boolean isStarted() {
		return (_timerState == TIMER_STATE_STARTED);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.timer.TimeManageable#isPaused()
	 */
	@Override
	public boolean isPaused() {
		return (_timerState == TIMER_STATE_PAUSED);
	}

	/**
     * getCurrentTime: gives the current time in milliseconds
     * @return
     * returns long
     */
	protected long getCurrentTime() {
    	return AndroidClock.elapsedRealtime();
    }

	/**
	 * @return the _log
	 */
	public static Loggable getLog() {
		return _log;
	}

	/**
	 * @param _log the _log to set
	 */
	public static void setLog(Loggable log) {
		_log = log;
	}

	/*
    private static Runnable _updateTimerTask = new Runnable() {
		private int nbpass = 0;
		private int nbrunning = 0;

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 * /
		@Override
		public void run() {
			OneTimer._timeHandler.removeCallbacks(this);
			OneTimer._timeHandler.postDelayed(this, TIMER_RUNNING_PERIOD);
			nbpass++;
			nbrunning = 0;
			for (int i = 0; i < _nb_timer; i++) {
				OneTimer curTimer = _timersList[i];
//				if (nbpass >= 20) {
//					Log.d(TAG, "TOUS TIMER id="+curTimer._myId+" is "+curTimer.getStatusString());
//				}
				if (curTimer.isStarted()) {
					nbrunning++;
					int elapse = curTimer.calculateElapseTimeSeconds();
//					if (nbpass >= 20) {
//						Log.d(TAG, "timer with id="+curTimer._myId+" is running, value="+curTimer.getCurrentSeconds());
//					}
					curTimer._timeDisplay.setCurrentCounter(curTimer._myId, curTimer.getCurrentSeconds());
//					Log.d(TAG, "run with direction up, duration="+duration+", maxDuration="+_maxDuration);
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
/*			} else {
				duration = _maxDuration - _timerDuration - seconds;
				_timeDisplay.setCurrentDuration(_myId, duration);
//				Log.d(TAG, "run with direction down, duration="+duration+", maxDuration="+_maxDuration);
				if (duration > 0) {
					_timeHandler.postDelayed(this, TIMER_RUNNING_PERIOD);
				} else {
					Log.d(TAG, "down timer has reached zero value");
					_timeHandler.removeCallbacks(this);
					_timeDisplay.endMaxDuration(_myId);
				}
			}
* ///			Log.d(TAG, "match time run at "+Math.round(curTime/100)/10+" seconds, current duration: "+duration+" seconds");
//			Log.d(TAG, "call setCurrentDuration with id="+_myId+", timeDisp="+_timeDisplay.getName());
		}
	};
	*/

}
