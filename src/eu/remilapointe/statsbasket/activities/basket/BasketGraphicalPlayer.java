/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import eu.remilapointe.statsbasket.common.play.android.GraphicalTextView;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class BasketGraphicalPlayer extends BasketPlayer {

	private static final String TAG = BasketGraphicalPlayer.class.getSimpleName();


	private GraphicalTextView _gtv_name = null;
	private GraphicalTextView _gtv_num = null;
	private GraphicalTextView _gtv_score_3pt = null;
	private GraphicalTextView _gtv_score_3pt_tent = null;
	private GraphicalTextView _gtv_score_2pt = null;
	private GraphicalTextView _gtv_score_2pt_tent = null;
	private GraphicalTextView _gtv_score_lf = null;
	private GraphicalTextView _gtv_score_lf_tent = null;
	private GraphicalTextView _gtv_fault = null;


	/**
	 * Constructor
	 */
	public BasketGraphicalPlayer() {
		super();
	}

	/**
	 * Constructor
	 * @param log
	 */
	public BasketGraphicalPlayer(Loggable log) {
		super(log);
	}

	/**
	 * Constructor
	 * @param log
	 * @param team
	 * @param name
	 * @param num
	 */
	public BasketGraphicalPlayer(Loggable log, int team, String name, int num) {
		super(log, team, name, num);
	}


	/**
	 * setGraphicalElements
	 * @param gtv_name
	 * @param gtv_num
	 * @param gtv_score
	 * @param gbt_goal
	 * @param gtv_fault
	 * @param gbt_shoot
	 */
	public void setGraphicalElements(GraphicalTextView gtv_name,
			GraphicalTextView gtv_num,
			GraphicalTextView gtv_score_3pt,
			GraphicalTextView gtv_score_3pt_tent,
			GraphicalTextView gtv_score_2pt,
			GraphicalTextView gtv_score_2pt_tent,
			GraphicalTextView gtv_score_lf,
			GraphicalTextView gtv_score_lf_tent,
			GraphicalTextView gtv_fault) {
		_gtv_name = gtv_name;
		_gtv_num = gtv_num;
		_gtv_score_3pt = gtv_score_3pt;
		_gtv_score_3pt_tent = gtv_score_3pt_tent;
		_gtv_score_2pt = gtv_score_2pt;
		_gtv_score_2pt_tent = gtv_score_2pt_tent;
		_gtv_score_lf = gtv_score_lf;
		_gtv_score_lf_tent = gtv_score_lf_tent;
		_gtv_fault = gtv_fault;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		super.setName(name);
		if (_gtv_name != null) {
			_gtv_name.setText(getName());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setNum(int)
	 */
	@Override
	public void setNum(int num) {
		super.setNum(num);
		if (_gtv_num != null) {
			_gtv_num.setText(getNumAsString());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScore3pt(int)
	 */
	@Override
	public void setScore3pt(int score_3pt) {
		super.setScore3pt(score_3pt);
		this.getLog().w(TAG, "setScore3pt(int=" + score_3pt + ")");
		if (_gtv_score_3pt != null) {
			_gtv_score_3pt.setText(getScore3ptAsString() + " |");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScore3ptTent(int)
	 */
	public void setScore3ptTent(int score_3pt_tent) {
		super.setScore3ptTent(score_3pt_tent);
		this.getLog().w(TAG, "setScore3ptTent(int=" + score_3pt_tent + ")");
		if (_gtv_score_3pt_tent != null) {
			_gtv_score_3pt_tent.setText(getScore3ptTentAsString());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScore2pt(int)
	 */
	public void setScore2pt(int score_2pt) {
		super.setScore2pt(score_2pt);
		if (_gtv_score_2pt != null) {
			_gtv_score_2pt.setText(getScore2ptAsString());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScore2ptTent(int)
	 */
	public void setScore2ptTent(int score_2pt_tent) {
		super.setScore2ptTent(score_2pt_tent);
		if (_gtv_score_2pt_tent != null) {
			_gtv_score_2pt_tent.setText(getScore2ptTentAsString());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScoreLf(int)
	 */
	public void setScoreLf(int score_lf) {
		super.setScoreLf(score_lf);
		if (_gtv_score_lf != null) {
			_gtv_score_lf.setText(getScoreLfAsString());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScoreLfTent(int)
	 */
	public void setScoreLfTent(int score_lf_tent) {
		super.setScoreLfTent(score_lf_tent);
		if (_gtv_score_lf_tent != null) {
			_gtv_score_lf_tent.setText(getScoreLfTentAsString());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setFault(int)
	 */
	@Override
	public void setFault(int fault) {
		super.setFault(fault);
		if (_gtv_fault != null) {
			_gtv_fault.setText(getFaultAsString());
		}
	}

	/**
	 * setEnabled
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		_gtv_score_3pt.setEnabled(enabled);
		_gtv_score_3pt_tent.setEnabled(enabled);
		_gtv_score_2pt.setEnabled(enabled);
		_gtv_score_2pt_tent.setEnabled(enabled);
		_gtv_score_lf.setEnabled(enabled);
		_gtv_score_lf_tent.setEnabled(enabled);
		_gtv_fault.setEnabled(enabled);
	}

}
