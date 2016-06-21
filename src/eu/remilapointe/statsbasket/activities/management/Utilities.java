/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import eu.remilapointe.statsbasket.db.ClubDB;
import eu.remilapointe.statsbasket.db.PlayerDB;
import eu.remilapointe.statsbasket.db.TeamDB;
import eu.remilapointe.statsbasket.sport.Club;
import eu.remilapointe.statsbasket.sport.Player;
import eu.remilapointe.statsbasket.sport.Team;
import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

/**
 * @author lapoint1
 *
 */
public class Utilities {

	private static final String TAG = Utilities.class.getSimpleName();

	private static String[] _theClubsName = null;
	private static String[] _theTeamsName = null;
	private static Team[] theTeams_ = null;

	/**
	 * getTheClubsName
	 * @param activity
	 * @return
	 */
	public static String[] getTheClubsName(Activity activity) {
		if (_theClubsName != null) return _theClubsName;

		if (activity == null) return null;

		ClubDB clubDb = ClubDB.getInstance();
		clubDb.setContentResolver(activity.getContentResolver());
		int nbClubs = clubDb.getNbElementsFromDatabase();
		Log.d(TAG, "Number of clubs in the database: " + nbClubs);
		Club[] theClubs = clubDb.getAllElementsFromDatabase();
		if ( (theClubs == null) || (theClubs.length == 0) ) {
			Log.e(TAG, "No club found");
			theClubs = new Club[1];
			theClubs[0] = new Club();
		} else {
			Log.d(TAG, "Number of clubs in the list: " + theClubs.length);
		}
		nbClubs = theClubs.length;
		_theClubsName = new String[nbClubs];
		for (int i = 0; i < nbClubs; i++) {
			_theClubsName[i] = theClubs[i].getName();
		}
		return _theClubsName;
	}

	/**
	 * getTheClubName
	 * @param activity
	 * @param i
	 * @return
	 */
	public static String getTheClubName(Activity activity, int i) {
		if (activity == null) return null;
		Log.d(TAG, "getTheClubName(" + activity.getLocalClassName() + ", " + i + ")");
		String[] theClubsName = getTheClubsName(activity);
		if ((i <= 0) || (i > theClubsName.length)) {
			return theClubsName[0];
		}
		Log.d(TAG, "getTheClubName returns " + theClubsName[i-1]);
		return theClubsName[i-1];
	}

	/**
	 * invalidTheClubsName
	 */
	public static void invalidTheClubsName() {
		_theClubsName = null;
	}

	/**
	 * getClubSpinnerAdapter
	 * @param activity
	 * @return
	 */
	public static SpinnerAdapter getClubSpinnerAdapter(Activity activity) {
		if (activity == null) return null;
		ArrayAdapter<String> clubAdapt = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, getTheClubsName(activity));
		clubAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		return clubAdapt;
	}

	/**
	 * getTheTeamsName
	 * @param activity
	 * @return
	 */
	public static String[] getTheTeamsName(Activity activity) {
		if (_theTeamsName != null) return _theTeamsName;

		if (activity == null) return null;

		TeamDB teamDb = TeamDB.getInstance();
		teamDb.setContentResolver(activity.getContentResolver());
		int nbTeams = teamDb.getNbElementsFromDatabase();
		Log.d(TAG, "Number of teams in the database: " + nbTeams);
		theTeams_ = teamDb.getAllElementsFromDatabase();
		if ( (theTeams_ == null) || (theTeams_.length == 0) ) {
			Log.e(TAG, "No team found");
			theTeams_ = new Team[1];
			theTeams_[0] = new Team();
		} else {
			Log.d(TAG, "Number of teams in the list: " + theTeams_.length);
		}
		nbTeams = theTeams_.length;
		_theTeamsName = new String[nbTeams];
		for (int i = 0; i < theTeams_.length; i++) {
			_theTeamsName[i] = theTeams_[i].getName();
		}
		return _theTeamsName;
	}

	/**
	 * getTheTeamName
	 * @param activity
	 * @param i
	 * @return
	 */
	public static String getTheTeamName(Activity activity, int i) {
		if (activity == null) return null;
		Log.d(TAG, "getTheTeamName(" + activity.getLocalClassName() + ", " + i + ")");
		String[] theTeamsName = getTheTeamsName(activity);
		if ((i <= 0) || (i > theTeamsName.length)) {
			return theTeamsName[0];
		}
		Log.d(TAG, "getTheTeamName returns " + theTeamsName[i-1]);
		return theTeamsName[i-1];
	}

	/**
	 * invalidTheTeamsName
	 */
	public static void invalidTheTeamsName() {
		_theTeamsName = null;
	}

	/**
	 * getTeamSpinnerAdapter
	 * @param activity
	 * @return
	 */
	public static SpinnerAdapter getTeamSpinnerAdapter(Activity activity) {
		if (activity == null) return null;
		ArrayAdapter<String> teamAdapt = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, getTheTeamsName(activity));
		teamAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		return teamAdapt;
	}

	public static String[] getPlayers(Activity activity, int i) {
		String[] result = null;
		int nbPlayers = theTeams_[i].getPlayersNumber();
		Log.d(TAG, "In the " + theTeams_[i].getName() + " team, there are " + nbPlayers + " player(s)");
		String playersId = theTeams_[i].getPlayersId();
		if (playersId != null && playersId.length() > 0) {
			String[] plsIds = playersId.split(",");
			result = new String[plsIds.length];
			PlayerDB playerDb = PlayerDB.getInstance();
			playerDb.setContentResolver(activity.getContentResolver());
			for (int p = 0; p < plsIds.length; p++) {
				int idPl = 0;
				try {
					idPl = Integer.parseInt(plsIds[p]);
				} catch (Exception e) {
					idPl = 0;
				}
				Player pl = playerDb.getFromDatabase(idPl);
				result[p] = pl.getName() + "," + pl.getBibNumber();
				Log.d(TAG, "Player no " + (p+1) + ": " + result[p]);
			}
			
		}

		return result;
	}

}
