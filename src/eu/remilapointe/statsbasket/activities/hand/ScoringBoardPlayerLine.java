/**
 * 
 */
package eu.remilapointe.statsbasket.activities.hand;

import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class ScoringBoardPlayerLine implements Cloneable {

	private static final String TAG = ScoringBoardPlayerLine.class.getSimpleName();

	protected ScoringBoardPlayer _player_A = null;
	protected ScoringBoardPlayer _player_B = null;

	/**
	 * 
	 */
	public ScoringBoardPlayerLine() {
		super();
	}

	/**
	 * @param playerA
	 * @param playerB
	 */
	public ScoringBoardPlayerLine(ScoringBoardPlayer playerA, ScoringBoardPlayer playerB) {
		this();
		Log.d(TAG, "new player line");
		set_player_A(playerA);
		set_player_B(playerB);
	}

	/**
	 * @return the _player_A
	 */
	public ScoringBoardPlayer get_player_A() {
		return _player_A;
	}

	/**
	 * @param _player_A the _player_A to set
	 */
	public void set_player_A(ScoringBoardPlayer _player_A) {
		this._player_A = _player_A;
	}

	/**
	 * @return the _player_B
	 */
	public ScoringBoardPlayer get_player_B() {
		return _player_B;
	}

	/**
	 * @param _player_B the _player_B to set
	 */
	public void set_player_B(ScoringBoardPlayer _player_B) {
		this._player_B = _player_B;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ScoringBoardPlayerLine: player A=");
		sb.append(_player_A.toString());
		sb.append(", player B=");
		sb.append(_player_B.toString());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
			ScoringBoardPlayerLine.class.isInstance(o) &&
			((ScoringBoardPlayerLine)o).get_player_A().equals(get_player_A()) &&
			((ScoringBoardPlayerLine)o).get_player_B().equals(get_player_B());
		return res;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		super.clone();
		ScoringBoardPlayerLine sbpl = new ScoringBoardPlayerLine();
		sbpl.set_player_A((ScoringBoardPlayer) this.get_player_A().clone());
		sbpl.set_player_B((ScoringBoardPlayer) this.get_player_B().clone());
		return sbpl;
	}


}
