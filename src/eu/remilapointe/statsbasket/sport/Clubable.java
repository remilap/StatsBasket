package eu.remilapointe.statsbasket.sport;

public interface Clubable extends Identifiable, Cloneable {

	public static final String KEY_CLUB_NAME = "name";
	public static final String KEY_CLUB_NUMBER = "number";
	public static final String KEY_CLUB_TOWN = "town";

	public static final int MAXLENGTH_NAME = 30;
	public static final int MAXLENGTH_NUMBER = 30;
	public static final int MAXLENGTH_TOWN = 30;
	public static final String CLUB_NAME_UNKNOWN = "Unknown";
	public static final String CLUB_NUMBER_UNKNOWN = "Unknown";
	public static final String CLUB_TOWN_UNKNOWN = "Unknown";

	public void setName(final String name);
	public String getName();

	public void setNumber(final String number);
	public String getNumber();

	public void setTown(final String town);
	public String getTown();

}
