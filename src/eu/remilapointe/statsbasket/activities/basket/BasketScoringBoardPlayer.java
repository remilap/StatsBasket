/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import android.util.Log;

/**
 * @author lapoint1
 *
 */
public class BasketScoringBoardPlayer implements Cloneable {

	private static final String TAG = BasketScoringBoardPlayer.class.getSimpleName();

	protected String _name = null;
	protected int _num = 0;
	protected String _exclu = null;
	protected int _shoot_3p = 0;
	protected int _shoot_3p_ok = 0;
	protected int _shoot_2p = 0;
	protected int _shoot_2p_ok = 0;
	protected int _shoot_1p = 0;
	protected int _shoot_1p_ok = 0;
	protected int _nb_fautes = 0;
	protected int _tps_jeu_sec = 0;

	/**
	 * 
	 */
	public BasketScoringBoardPlayer() {
		super();
	}

	/**
	 * @param name
	 * @param num
	 * @param exclu
	 */
	public BasketScoringBoardPlayer(String name, int num, String exclu) {
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
		return _name;
	}

	/**
	 * @param _name the _name to set
	 */
	public void set_player_name(String _player_name) {
		this._name = _player_name;
	}

	/**
	 * @return the _num
	 */
	public int get_player_num() {
		return _num;
	}

	/**
	 * @param _num the _num to set
	 */
	public void set_player_num(int _num) {
		this._num = _num;
	}

	/**
	 * @return the _exclu
	 */
	public String get_player_exclu() {
		return _exclu;
	}

	/**
	 * @param _exclu the _exclu to set
	 */
	public void set_player_exclu(String _player_exclu) {
		this._exclu = _player_exclu;
	}

	/**
	 * @return the _shoot_3p
	 */
	public int get_shoot_3p() {
		return _shoot_3p;
	}

	/**
	 * @param _shoot_3p the _shoot_3p to set
	 */
	public void set_shoot_3p(int _shoot_3p) {
		this._shoot_3p = _shoot_3p;
	}

	/**
	 * @return the _shoot_3p_ok
	 */
	public int get_shoot_3p_ok() {
		return _shoot_3p_ok;
	}

	/**
	 * @param _shoot_3p_ok the _shoot_3p_ok to set
	 */
	public void set_shoot_3p_ok(int _shoot_3p_ok) {
		this._shoot_3p_ok = _shoot_3p_ok;
	}

	/**
	 * @return the _shoot_2p
	 */
	public int get_shoot_2p() {
		return _shoot_2p;
	}

	/**
	 * @param _shoot_2p the _shoot_2p to set
	 */
	public void set_shoot_2p(int _shoot_2p) {
		this._shoot_2p = _shoot_2p;
	}

	/**
	 * @return the _shoot_2p_ok
	 */
	public int get_shoot_2p_ok() {
		return _shoot_2p_ok;
	}

	/**
	 * @param _shoot_2p_ok the _shoot_2p_ok to set
	 */
	public void set_shoot_2p_ok(int _shoot_2p_ok) {
		this._shoot_2p_ok = _shoot_2p_ok;
	}

	/**
	 * @return the _shoot_1p
	 */
	public int get_shoot_1p() {
		return _shoot_1p;
	}

	/**
	 * @param _shoot_1p the _shoot_1p to set
	 */
	public void set_shoot_1p(int _shoot_1p) {
		this._shoot_1p = _shoot_1p;
	}

	/**
	 * @return the _shoot_1p_ok
	 */
	public int get_shoot_1p_ok() {
		return _shoot_1p_ok;
	}

	/**
	 * @param _shoot_1p_ok the _shoot_1p_ok to set
	 */
	public void set_shoot_1p_ok(int _shoot_1p_ok) {
		this._shoot_1p_ok = _shoot_1p_ok;
	}

	/**
	 * @return the _nb_fautes
	 */
	public int get_nb_fautes() {
		return _nb_fautes;
	}

	/**
	 * @param _nb_fautes the _nb_fautes to set
	 */
	public void set_nb_fautes(int _nb_fautes) {
		this._nb_fautes = _nb_fautes;
	}

	/**
	 * @return the _tps_jeu_sec
	 */
	public int get_tps_jeu_sec() {
		return _tps_jeu_sec;
	}

	/**
	 * @param _tps_jeu_sec the _tps_jeu_sec to set
	 */
	public void set_tps_jeu_sec(int _tps_jeu_sec) {
		this._tps_jeu_sec = _tps_jeu_sec;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ScoringBoardPlayer: name=");
		sb.append(_name);
		sb.append(", num=");
		sb.append(_num);
		sb.append(", exclu=");
		sb.append(_exclu);
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
			BasketScoringBoardPlayer.class.isInstance(o) &&
			((BasketScoringBoardPlayer)o).get_player_name().equals(get_player_name()) &&
			((BasketScoringBoardPlayer)o).get_player_num() == get_player_num() &&
			((BasketScoringBoardPlayer)o).get_player_exclu().equals(get_player_exclu());
		return res;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		BasketScoringBoardPlayer sbp = new BasketScoringBoardPlayer(this.get_player_name(), this.get_player_num(), this.get_player_exclu());
		sbp._shoot_1p = this._shoot_1p;
		sbp._shoot_1p_ok = this._shoot_1p_ok;
		sbp._shoot_2p = this._shoot_2p;
		sbp._shoot_2p_ok = this._shoot_2p_ok;
		sbp._shoot_3p = this._shoot_3p;
		sbp._shoot_3p_ok = this._shoot_3p_ok;
		sbp._nb_fautes = this._nb_fautes;
		sbp._tps_jeu_sec = this._tps_jeu_sec;
		return sbp;
	}


}
