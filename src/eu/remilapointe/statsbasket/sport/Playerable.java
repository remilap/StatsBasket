/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

import java.util.Date;

/**
 * @author lapoint1
 *
 */
public interface Playerable extends Identifiable, Cloneable {

	public static final String KEY_PLAYER_NAME = "name";
	public static final String KEY_PLAYER_FIRSTNAME = "firstname";
	public static final String KEY_PLAYER_BIRTHDAY = "birthday";
	public static final String KEY_PLAYER_LICENSE = "license";
	public static final String KEY_PLAYER_BIB_NUMBER = "bib_number";
	public static final String KEY_PLAYER_SPECIFIC = "specific";
	public static final String KEY_PLAYER_CLUB_ID = "club_id";

	public static final int MAXLENGTH_NAME = 30;
	public static final int MAXLENGTH_FIRSTNAME = 30;
	public static final int MAXLENGTH_LICENSE = 30;
	public static final int MINIMUM_BIB_NUMBER = 1;
	public static final int MAXIMUM_BIB_NUMBER = 99;
	public static final int MAXLENGTH_SPECIFIC = 10;
	public static final String PLAYER_NAME_UNKNOWN = "Unknown";
	public static final String PLAYER_FIRSTNAME_UNKNOWN = "Unknown";
	public static final Date PLAYER_BIRTHDAY_UNKNOWN = null;
	public static final String PLAYER_LICENSE_UNKNOWN = "Unknown";
	public static final int PLAYER_BIB_NUMBER_UNKNOWN = -1;
	public static final String PLAYER_NO_SPECIFIC = ".";
	public static final String PLAYER_SPECIFIC_GOAL = "G";
	public static final String PLAYER_SPECIFIC_CAPTAIN = "C";
	public static final String PLAYER_SPECIFIC_COACH = "T";
	public static final String PLAYER_SPECIFIC_DOCTOR = "D";
	public static final String PLAYER_SPECIFIC_KINE = "K";
	public static final int PLAYER_CLUB_UNKNOWN = -1;

	public void setName(final String name);
	public String getName();

	public void setFirstname(final String firstname);
	public String getFirstname();

	public void setBirthday(final Date birthday);
	public Date getBirthday();
	public String getBirthdayDay();
	public int getAge();

	public void setLicense(final String licence);
	public String getLicense();

	public void setBibNumber(final int number);
	public int getBibNumber();
	public void setBibNumber(final String number);
	public String getStringBibNumber();

	public void setSpecific(final String specific);
	public String getSpecific();
	public void setGoal(final boolean goal);
	public boolean isGoal();
	public void setCaptain(final boolean captain);
	public boolean isCaptain();
	public void setCoach(final boolean coach);
	public boolean isCoach();
	public void setDoctor(final boolean doctor);
	public boolean isDoctor();
	public void setKine(final boolean kine);
	public boolean isKine();

	public void setClubId(long club_id);
	public void setClubId(String club_id);
	public long getClubId();
	public String getClubStringID();

}
