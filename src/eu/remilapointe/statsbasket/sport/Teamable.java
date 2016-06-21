/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

/**
 * @author lapoint1
 *
 */
public interface Teamable extends Identifiable, Cloneable {

	public static final String KEY_TEAM_NAME = "name";
	public static final String KEY_TEAM_CLUB_ID = "club_id";
	public static final String KEY_TEAM_PLAYERS_IDS = "players_ids";

	public static final int MAXLENGTH_NAME = 30;
	public static final String TEAM_NAME_UNKNOWN = "Unknown";
	public static final int TEAM_CLUB_UNKNOWN = -1;
	public static final String TEAM_PLAYERS_UNKNOWN = "";

	public void setName(String name);
	public String getName();

	public void setClubId(long club_id);
	public void setClubId(String club_id);
	public long getClubId();
	public String getClubStringId();

	public void setPlayersId(String list_players_id);
	public String getPlayersId();

}
