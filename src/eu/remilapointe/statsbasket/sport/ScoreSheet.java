/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lapoint1
 *
 */
public class ScoreSheet {

	private static ScoreSheet _instance = null;
	private List<String> _club_name = new ArrayList<String>();
	private List<String> _team_name = new ArrayList<String>();

	/**
	 * ScoreSheet
	 */
	private ScoreSheet() {
		super();
	}

	/**
	 * getInstance
	 * @return
	 * returns ScoreSheet
	 */
	public static ScoreSheet getInstance() {
		if (_instance == null) {
			_instance = new ScoreSheet();
		}
		return _instance;
	}

	/**
	 * get_club_name
	 * @param c
	 * @return
	 * returns String
	 */
	public String get_club_name(final int c) {
		if ((c >= 0) && (c < _club_name.size())) {
			return _club_name.get(c);
		}
		return null;
	}

	/**
	 * set_club_name
	 * @param c
	 * @param club_name
	 * returns void
	 */
	public void set_club_name(final int c, final String club_name) {
		if ((c >= 0) && (c < _club_name.size())) {
			_club_name.add(club_name);
		}
	}

	/**
	 * get_team_name
	 * @param c
	 * @return
	 * returns String
	 */
	public String get_team_name(final int c) {
		if ((c >= 0) && (c < _team_name.size())) {
			return _team_name.get(c);
		}
		return null;
	}

	/**
	 * set_team_name
	 * @param c
	 * @param team_name
	 * returns void
	 */
	public void set_team_name(final int c, final String team_name) {
		if ((c >= 0) && (c < _team_name.size())) {
			_team_name.add(team_name);
		}
	}

	
}
