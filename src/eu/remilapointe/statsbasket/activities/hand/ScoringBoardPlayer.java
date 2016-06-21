/**
 * 
 */
package eu.remilapointe.statsbasket.activities.hand;

import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class ScoringBoardPlayer implements Cloneable {

	private static final String TAG = ScoringBoardPlayer.class.getSimpleName();

	protected String _player_name = null;
	protected String _player_num = null;
	protected String _player_exclu = null;

	/**
	 * 
	 */
	public ScoringBoardPlayer() {
		super();
	}

	/**
	 * @param name
	 * @param num
	 * @param exclu
	 */
	public ScoringBoardPlayer(String name, String num, String exclu) {
		this();
		Log.d(TAG, "new Player, name=" + name + ", num=" + num + ", exclu=" + exclu);
		set_player_name(name);
		set_player_num(num);
		set_player_exclu(exclu);
	}

	/**
	 * @return the _name
	 */
	public String get_player_name() {
		return _player_name;
	}

	/**
	 * @param _name the _name to set
	 */
	public void set_player_name(String _player_name) {
		this._player_name = _player_name;
	}

	/**
	 * @return the _num
	 */
	public String get_player_num() {
		return _player_num;
	}

	/**
	 * @param _num the _num to set
	 */
	public void set_player_num(String _player_num) {
		this._player_num = _player_num;
	}

	/**
	 * @return the _exclu
	 */
	public String get_player_exclu() {
		return _player_exclu;
	}

	/**
	 * @param _exclu the _exclu to set
	 */
	public void set_player_exclu(String _player_exclu) {
		this._player_exclu = _player_exclu;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ScoringBoardPlayer: name=");
		sb.append(_player_name);
		sb.append(", num=");
		sb.append(_player_num);
		sb.append(", exclu=");
		sb.append(_player_exclu);
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
			ScoringBoardPlayer.class.isInstance(o) &&
			((ScoringBoardPlayer)o).get_player_name().equals(get_player_name()) &&
			((ScoringBoardPlayer)o).get_player_num().equals(get_player_num()) &&
			((ScoringBoardPlayer)o).get_player_exclu().equals(get_player_exclu());
		return res;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		ScoringBoardPlayer sbp = new ScoringBoardPlayer(this.get_player_name(), this.get_player_num(), this.get_player_exclu());
		return sbp;
	}


}
