/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

import eu.remilapointe.statsbasket.sport.DefaultLog;
import eu.remilapointe.statsbasket.sport.Loggable;


/**
 * @author lapoint1
 *
 */
public class CommonPlayer {

	private static final String TAG = CommonPlayer.class.getSimpleName();

	private Loggable _log = new DefaultLog();
	private int _team = 0;
	private String _name = "Unknown";
	private int _num = 0;
	private int _score = 0;
	private int _score_tent = 0;
	private int _score_lf = 0;
	private int _score_lf_tent = 0;
	private int _fault = 0;
	private int _playingTime = 0;   // in seconds


	/**
	 * Constructor
	 */
	public CommonPlayer() {
		super();
		this.getLog().d(TAG, "BasketPlayer()");
	}

	/**
	 * Constructor
	 * @param log
	 */
	public CommonPlayer(final Loggable log) {
		this();
		setLog(log);
		this.getLog().d(TAG, "BasketPlayer(log)");
	}

	/**
	 * Constructor
	 * @param log
	 * @param name
	 * @param num
	 */
	public CommonPlayer(final Loggable log, final int team, final String name, final int num) {
		this(log);
		setTeam(team);
		setName(name);
		setNum(num);
		this.getLog().d(TAG, "BasketPlayer(log, team=" + team + ", name=" + name + ", num=" + num + ")");
	}


	/**
	 * getLog
	 * @return _log
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
	 * getTeam
	 * @return _team
	 */
	public int getTeam() {
		return _team;
	}

	/**
	 * setTeam
	 * @param team
	 */
	public void setTeam(final int team) {
		if (team < 0 || team > 1) {
			this.getLog().e(TAG, "setTeam with an invalid number => keep the default value (" + getTeam() + ")");
			return;
		}
		_team = team;
	}

	/**
	 * getName
	 * @return the _name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * setName
	 * @param name the _name to set
	 */
	public void setName(final String name) {
		if (name == null || name.length() == 0) {
			this.getLog().e(TAG, "setName with a null or empty name => keep the default value (" + this.getName() + ")");
			return;
		}
		this._name = name;
	}

	/**
	 * getNum
	 * @return the _num
	 */
	public int getNum() {
		return _num;
	}

	/**
	 * getNumAsString
	 * @return _num
	 */
	public String getNumAsString() {
		return String.valueOf(_num);
	}

	/**
	 * setNum
	 * @param num the _num to set
	 */
	public void setNum(final int num) {
		if (num < 0) {
			this.getLog().e(TAG, "setNum with a negative number => set the opposite (" + (-num) + ")");
			this._num = -num;
		} else {
			this._num = num;
		}
	}

	/**
	 * setNum
	 * @param snum
	 */
	public void setNum(final String snum) {
		try {
			int n = Integer.parseInt(snum);
			this.setNum(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setNum with a non integer string");
		}
	}

	/**
	 * getScore
	 * @return the _score
	 */
	public int getScore() {
		return _score;
	}

	/**
	 * getScoreAsString
	 * @return _score
	 */
	public String getScoreAsString() {
		return String.valueOf(this.getScore());
	}

	/**
	 * setScore
	 * @param score_ the _score to set
	 */
	public void setScore(final int score) {
		this.getLog().w(TAG, "setScore(int=" + score + ")");
		if (score < 0) {
			this.getLog().e(TAG, "setScore with a negative number => set to zero");
			this._score = 0;
		} else {
			this._score = score;
		}
	}

	/**
	 * setScore
	 * @param sscore
	 */
	public void setScore(final String sscore) {
		this.getLog().w(TAG, "setScore(String=" + sscore + ")");
		try {
			int n = Integer.parseInt(sscore);
			this.setScore(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScore with a non integer string");
		}
	}

	/**
	 * addScore
	 * @param diff
	 */
	public void addScore(final int diff) {
		this.setScore(this.getScore() + diff);
	}

	/**
	 * getScoreTent
	 * @return the _score_tent
	 */
	public int getScoreTent() {
		return _score_tent;
	}

	/**
	 * getScoreTentAsString
	 * @return _score_tent
	 */
	public String getScoreTentAsString() {
		return String.valueOf(this.getScoreTent());
	}

	/**
	 * setScoreTent
	 * @param score_tent the _score_tent to set
	 */
	public void setScoreTent(final int score_tent) {
		this.getLog().w(TAG, "setScoreTent(int=" + score_tent + ")");
		if (score_tent < 0) {
			this.getLog().e(TAG, "setScoreTent with a negative number => set to zero");
			this._score_tent = 0;
		} else {
			this._score_tent = score_tent;
		}
	}

	/**
	 * setScoreTent
	 * @param sscore_tent
	 */
	public void setScoreTent(final String sscore_tent) {
		this.getLog().w(TAG, "setScoreTent(String=" + sscore_tent + ")");
		try {
			int n = Integer.parseInt(sscore_tent);
			this.setScoreTent(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScoreTent with a non integer string");
		}
	}

	/**
	 * addScoreTent
	 * @param diff
	 */
	public void addScoreTent(final int diff) {
		this.setScoreTent(this.getScoreTent() + diff);
	}

	/**
	 * getScoreLf
	 * @return the _score_lf
	 */
	public int getScoreLf() {
		return _score_lf;
	}

	/**
	 * getScoreLfAsString
	 * @return _score_lf
	 */
	public String getScoreLfAsString() {
		return String.valueOf(this.getScoreLf());
	}

	/**
	 * setScoreLf
	 * @param score_lf the _score_lf to set
	 */
	public void setScoreLf(final int score_lf) {
		if (score_lf < 0) {
			this.getLog().e(TAG, "setScoreLf with a negative number => set to zero");
			this._score_lf = 0;
		} else {
			this._score_lf = score_lf;
		}
	}

	/**
	 * setScoreLf
	 * @param sscore_lf
	 */
	public void setScoreLf(final String sscore_lf) {
		try {
			int n = Integer.parseInt(sscore_lf);
			this.setScoreLf(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScoreLf with a non integer string");
		}
	}

	/**
	 * addScoreLf
	 * @param diff
	 */
	public void addScoreLf(final int diff) {
		this.setScoreLf(this.getScoreLf() + diff);
	}

	/**
	 * getScoreLfTent
	 * @return the _score_lf_tent
	 */
	public int getScoreLfTent() {
		return _score_lf_tent;
	}

	/**
	 * getScoreLfTentAsString
	 * @return _score_lf_tent
	 */
	public String getScoreLfTentAsString() {
		return String.valueOf(this.getScoreLfTent());
	}

	/**
	 * setScoreLfTent
	 * @param score_lf_tent the _score_lf_tent to set
	 */
	public void setScoreLfTent(final int score_lf_tent) {
		if (score_lf_tent < 0) {
			this.getLog().e(TAG, "setScoreLfTent with a negative number => set to zero");
			this._score_lf_tent = 0;
		} else {
			this._score_lf_tent = score_lf_tent;
		}
	}

	/**
	 * setScoreLfTent
	 * @param sscore_lf_tent
	 */
	public void setScoreLfTent(final String sscore_lf_tent) {
		try {
			int n = Integer.parseInt(sscore_lf_tent);
			this.setScoreLfTent(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScoreLfTent with a non integer string");
		}
	}

	/**
	 * addScoreLfTent
	 * @param diff
	 */
	public void addScoreLfTent(final int diff) {
		this.setScoreLfTent(this.getScoreLfTent() + diff);
	}

	/**
	 * getFault
	 * @return the _fault
	 */
	public int getFault() {
		return _fault;
	}

	/**
	 * getFaultAsString
	 * @return _fault
	 */
	public String getFaultAsString() {
		return String.valueOf(this.getFault());
	}

	/**
	 * @param _fault the _fault to set
	 */
	public void setFault(final int fault) {
		if (fault < 0) {
			this.getLog().e(TAG, "setFault with a negative number => set to zero");
			this._fault = 0;
		} else {
			this._fault = fault;
		}
	}

	/**
	 * setFault
	 * @param sfault
	 */
	public void setFault(final String sfault) {
		try {
			int n = Integer.parseInt(sfault);
			this.setFault(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setFault with a non integer string");
		}
	}

	/**
	 * addFault
	 * @param diff
	 */
	public void addFault(final int diff) {
		this.setFault(this.getFault() + diff);
	}

	/**
	 * @return the _playingTime in seconds
	 */
	public int getPlayingTime() {
		return _playingTime;
	}

	/**
	 * getFormattedPlayingTime
	 * @return "mm:ss" or "h:mm:ss"
	 */
	public String getFormattedPlayingTime() {
		int h = _playingTime / 3600;
		int r = _playingTime % 3600;
		int m = r / 60;
		int s = r % 60;
		StringBuilder res = new StringBuilder();
		if (h > 0) {
			res.append(h);
			res.append(':');
		}
		if (m < 10) {
			res.append('0');
		}
		res.append(m);
		res.append(':');
		if (s < 10) {
			res.append('0');
		}
		res.append(s);
		return res.toString();
	}

	/**
	 * @param _playingTime the _playingTime to set
	 */
	public void setPlayingTime(final int playingTime) {
		this._playingTime = playingTime;
	}



    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasketPlayer [_name=" + _name + ", _num=" + _num
				+ ", _score=" + _score + ", _score_tent=" + _score_tent
				+ ", _score_lf=" + _score_lf + ",_score_lf_tent=" + _score_lf_tent
				+ ", _fault=" + _fault + ", _playingTime=" + _playingTime + "]";
	}


	/**
	 * main
	 * @param args
	 */
	public static void main( String[] args ) {
    	CommonPlayer pl = new CommonPlayer();
    	pl.setName("toto");
    	System.out.println(pl.toString());
    	pl.setNum(55);
    	System.out.println(pl.toString());
    	pl.setNum("27");
    	System.out.println(pl.toString());
    	pl.setFault(3);
    	System.out.println(pl.toString());
    	pl.setFault("11");
    	System.out.println(pl.toString());
    	pl.setPlayingTime(5);
    	System.out.println(pl.toString() + " " + pl.getFormattedPlayingTime());
    	pl.setPlayingTime(23);
    	System.out.println(pl.toString() + " " + pl.getFormattedPlayingTime());
    	pl.setPlayingTime(75);
    	System.out.println(pl.toString() + " " + pl.getFormattedPlayingTime());
    	pl.setPlayingTime(307);
    	System.out.println(pl.toString() + " " + pl.getFormattedPlayingTime());
    	pl.setPlayingTime(4028);
    	System.out.println(pl.toString() + " " + pl.getFormattedPlayingTime());
    }

}
