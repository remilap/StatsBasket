/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

import eu.remilapointe.statsbasket.common.play.android.GraphicalTextView;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class CommonGraphicalPlayer extends CommonPlayer {

	private static final String TAG = CommonGraphicalPlayer.class.getSimpleName();


	private GraphicalTextView _gtv_name = null;
	private GraphicalTextView _gtv_num = null;
	private GraphicalTextView _gtv_score = null;
	private GraphicalTextView _gtv_score_tent = null;
	private GraphicalTextView _gtv_score_lf = null;
	private GraphicalTextView _gtv_score_lf_tent = null;
	private GraphicalTextView _gtv_fault = null;


	/**
	 * Constructor
	 */
	public CommonGraphicalPlayer() {
		super();
	}

	/**
	 * Constructor
	 * @param log
	 */
	public CommonGraphicalPlayer(Loggable log) {
		super(log);
	}

	/**
	 * Constructor
	 * @param log
	 * @param team
	 * @param name
	 * @param num
	 */
	public CommonGraphicalPlayer(Loggable log, int team, String name, int num) {
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
			GraphicalTextView gtv_score,
			GraphicalTextView gtv_score_tent,
			GraphicalTextView gtv_score_lf,
			GraphicalTextView gtv_score_lf_tent,
			GraphicalTextView gtv_fault) {
		_gtv_name = gtv_name;
		_gtv_num = gtv_num;
		_gtv_score = gtv_score;
		_gtv_score_tent = gtv_score_tent;
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
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScore(int)
	 */
	@Override
	public void setScore(int score) {
		super.setScore(score);
		this.getLog().w(TAG, "setScore(int=" + score + ")");
		if (_gtv_score != null) {
			_gtv_score.setText(getScoreAsString() + " |");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.CommonPlayer#setScoreTent(int)
	 */
	public void setScoreTent(int score_tent) {
		super.setScoreTent(score_tent);
		this.getLog().w(TAG, "setScoreTent(int=" + score_tent + ")");
		if (_gtv_score_tent != null) {
			_gtv_score_tent.setText(getScoreTentAsString());
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
		_gtv_score.setEnabled(enabled);
		_gtv_score_tent.setEnabled(enabled);
		_gtv_score_lf.setEnabled(enabled);
		_gtv_score_lf_tent.setEnabled(enabled);
		_gtv_fault.setEnabled(enabled);
	}

}
