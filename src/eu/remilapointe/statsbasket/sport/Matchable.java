/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author lapoint1
 *
 */
public interface Matchable extends Identifiable, Cloneable {

	public static final String KEY_MATCH_REF = "reference";
	public static final String KEY_MATCH_CLUB_A = "club_a";
	public static final String KEY_MATCH_CLUB_B = "club_b";
	public static final String KEY_MATCH_DATE = "date";
	public static final String KEY_MATCH_LOCATION = "location";

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	public static final int MAXLENGTH_REF = 30;
	public static final int MAXLENGTH_CLUB = 5;
	public static final int MAXLENGTH_DATE = 10;;
	public static final int MAXLENGTH_LOCATION = 30;
	public static final String MATCH_REF_UNKNOWN = "0000";
	public static final String MATCH_CLUB_A_UNKNOWN = "1";
	public static final String MATCH_CLUB_B_UNKNOWN = "2";
	public static final String MATCH_DATE_UNKNOWN = Calendar.getInstance().getTime().toLocaleString();
	public static final String MATCH_LOCATION_UNKNOWN = "Unknown";

	public void setReference(final String reference);
	public String getReference();

	public void setClubAId(final long club_a_id);
	public void setClubAId(final String club_a_id);
	public long getClubAId();
	public String getClubAStringId();

	public void setClubBId(final long club_b_id);
	public void setClubBId(final String club_b_id);
	public long getClubBId();
	public String getClubBStringId();

	public void setDateString(final String date);
	public String getDateString();
	public void setDate(final Date dat);
	public Date getDate();
	public void setDateYear(final int year);
	public int getDateYear();
	public void setDateMonth(final int month);
	public int getDateMonth();
	public void setDateDayOfMonth(final int dayOfMonth);
	public int getDateDayOfMonth();

	public void setLocation(final String location);
	public String getLocation();

}
