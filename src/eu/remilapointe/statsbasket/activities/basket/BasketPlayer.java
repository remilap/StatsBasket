/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import eu.remilapointe.statsbasket.common.play.CommonPlayer;
import eu.remilapointe.statsbasket.sport.DefaultLog;
import eu.remilapointe.statsbasket.sport.Loggable;


/**
 * @author lapoint1
 *
 */
public class BasketPlayer extends CommonPlayer {

	private static final String TAG = BasketPlayer.class.getSimpleName();

	private int _score_3pt = 0;
	private int _score_3pt_tent = 0;
	private int _score_2pt = 0;
	private int _score_2pt_tent = 0;


	/**
	 * Constructor
	 */
	public BasketPlayer() {
		super();
		this.getLog().d(TAG, "BasketPlayer()");
	}

	/**
	 * Constructor
	 * @param log
	 */
	public BasketPlayer(final Loggable log) {
		super(log);
		this.getLog().d(TAG, "BasketPlayer(log)");
	}

	/**
	 * Constructor
	 * @param log
	 * @param name
	 * @param num
	 */
	public BasketPlayer(final Loggable log, final int team, final String name, final int num) {
		super(log, team, name, num);
		this.getLog().d(TAG, "BasketPlayer(log, team=" + team + ", name=" + name + ", num=" + num + ")");
	}


	/**
	 * getScore3pt
	 * @return the _score_3pt
	 */
	public int getScore3pt() {
		return _score_3pt;
	}

	/**
	 * getScore3ptAsString
	 * @return _score_3pt
	 */
	public String getScore3ptAsString() {
		return String.valueOf(this.getScore3pt());
	}

	/**
	 * setScore3pt
	 * @param score_3pt the _score_3pt to set
	 */
	public void setScore3pt(final int score_3pt) {
		this.getLog().w(TAG, "setScore3pt(int=" + score_3pt + ")");
		if (score_3pt < 0) {
			this.getLog().e(TAG, "setScore3pt with a negative number => set to zero");
			this._score_3pt = 0;
		} else {
			this._score_3pt = score_3pt;
		}
	}

	/**
	 * setScore3pt
	 * @param sscore_3pt
	 */
	public void setScore3pt(final String sscore_3pt) {
		this.getLog().w(TAG, "setScore3pt(String=" + sscore_3pt + ")");
		try {
			int n = Integer.parseInt(sscore_3pt);
			this.setScore3pt(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScore3pt with a non integer string");
		}
	}

	/**
	 * addScore3pt
	 * @param diff
	 */
	public void addScore3pt(final int diff) {
		this.setScore3pt(this.getScore3pt() + diff);
	}

	/**
	 * getScore3ptTent
	 * @return the _score_3pt_tent
	 */
	public int getScore3ptTent() {
		return _score_3pt_tent;
	}

	/**
	 * getScore3ptTentAsString
	 * @return _score_3pt_tent
	 */
	public String getScore3ptTentAsString() {
		return String.valueOf(this.getScore3ptTent());
	}

	/**
	 * setScore3ptTent
	 * @param score_3pt_tent the _score_3pt_tent to set
	 */
	public void setScore3ptTent(final int score_3pt_tent) {
		this.getLog().w(TAG, "setScore3ptTent(int=" + score_3pt_tent + ")");
		if (score_3pt_tent < 0) {
			this.getLog().e(TAG, "setScore3ptTent with a negative number => set to zero");
			this._score_3pt_tent = 0;
		} else {
			this._score_3pt_tent = score_3pt_tent;
		}
	}

	/**
	 * setScore3ptTent
	 * @param sscore_3pt_tent
	 */
	public void setScore3ptTent(final String sscore_3pt_tent) {
		this.getLog().w(TAG, "setScore3ptTent(String=" + sscore_3pt_tent + ")");
		try {
			int n = Integer.parseInt(sscore_3pt_tent);
			this.setScore3ptTent(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScore3ptTent with a non integer string");
		}
	}

	/**
	 * addScore3ptTent
	 * @param diff
	 */
	public void addScore3ptTent(final int diff) {
		this.setScore3ptTent(this.getScore3ptTent() + diff);
	}

	/**
	 * getScore2pt
	 * @return the _score_2pt
	 */
	public int getScore2pt() {
		return _score_2pt;
	}

	/**
	 * getScore2ptAsString
	 * @return _score_2pt
	 */
	public String getScore2ptAsString() {
		return String.valueOf(this.getScore2pt());
	}

	/**
	 * setScore2pt
	 * @param score_2pt the _score_2pt to set
	 */
	public void setScore2pt(final int score_2pt) {
		if (score_2pt < 0) {
			this.getLog().e(TAG, "setScore2pt with a negative number => set to zero");
			this._score_2pt = 0;
		} else {
			this._score_2pt = score_2pt;
		}
	}

	/**
	 * setScore2pt
	 * @param sscore_2pt
	 */
	public void setScore2pt(final String sscore_2pt) {
		try {
			int n = Integer.parseInt(sscore_2pt);
			this.setScore2pt(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScore2pt with a non integer string");
		}
	}

	/**
	 * addScore2pt
	 * @param diff
	 */
	public void addScore2pt(final int diff) {
		this.setScore2pt(this.getScore2pt() + diff);
	}

	/**
	 * getScore2ptTent
	 * @return the _score_2pt_tent
	 */
	public int getScore2ptTent() {
		return _score_2pt_tent;
	}

	/**
	 * getScore2ptTentAsString
	 * @return _score_2pt_tent
	 */
	public String getScore2ptTentAsString() {
		return String.valueOf(this.getScore2ptTent());
	}

	/**
	 * setScore2ptTent
	 * @param score_2pt_tent the _score_2pt_tent to set
	 */
	public void setScore2ptTent(final int score_2pt_tent) {
		if (score_2pt_tent < 0) {
			this.getLog().e(TAG, "setScore2ptTent with a negative number => set to zero");
			this._score_2pt_tent = 0;
		} else {
			this._score_2pt_tent = score_2pt_tent;
		}
	}

	/**
	 * setScore2ptTent
	 * @param sscore_2pt_tent
	 */
	public void setScore2ptTent(final String sscore_2pt_tent) {
		try {
			int n = Integer.parseInt(sscore_2pt_tent);
			this.setScore2ptTent(n);
		} catch (NumberFormatException e) {
			this.getLog().e(TAG, "ERROR setScore2ptTent with a non integer string");
		}
	}

	/**
	 * addScore2ptTent
	 * @param diff
	 */
	public void addScore2ptTent(final int diff) {
		this.setScore2ptTent(this.getScore2ptTent() + diff);
	}


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasketPlayer [_name=" + getName() + ", _num=" + getNum()
				+ ", _score_3pt=" + _score_3pt + ", _score_3pt_tent=" + _score_3pt_tent
				+ ", _score_2pt=" + _score_2pt + ", _score_2pt_tent=" + _score_2pt_tent
				+ ", _score_lf=" + getScoreLf() + ",_score_lf_tent=" + getScoreLfTent()
				+ ", _fault=" + getFault() + ", _playingTime=" + getPlayingTime() + "]";
	}


	/**
	 * main
	 * @param args
	 */
	public static void main( String[] args ) {
    	BasketPlayer pl = new BasketPlayer();
    	pl.setName("toto");
    	System.out.println(pl.toString());
    	pl.setNum(55);
    	System.out.println(pl.toString());
    	pl.setNum("27");
    	System.out.println(pl.toString());
    	pl.setScore3pt(8);
    	System.out.println(pl.toString());
    	pl.setScore3pt("12");
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
