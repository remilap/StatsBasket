/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import eu.remilapointe.statsbasket.db.BasketOpenHelper;

/**
 * @author lapoint1
 *
 */
/**
 * @author lapoint1
 *
 */
/**
 * @author lapoint1
 *
 */
public class Player extends Identifiant implements Identifiable, Playerable, Cloneable {

	private static final String TAG = Player.class.getSimpleName();

	protected String _name = null;
	protected String _firstName = null;
	protected String _license = null;
	protected int _bibNumber = 0;
	protected Date _birthday = null;
	protected String _specific = null;
	protected long _club_id = 0;


	/**
	 * Player
	 */
	public Player() {
		super();
		setLog(new AndroidLog());
		Log.d(TAG, "constructor "+TAG+"()");
		setName(PLAYER_NAME_UNKNOWN);
		setFirstname(PLAYER_FIRSTNAME_UNKNOWN);
		setLicense(PLAYER_LICENSE_UNKNOWN);
		setBibNumber(PLAYER_BIB_NUMBER_UNKNOWN);
		setBirthday(PLAYER_BIRTHDAY_UNKNOWN);
		setSpecific(PLAYER_NO_SPECIFIC);
		setClubId(PLAYER_CLUB_UNKNOWN);
	}

	/**
	 * Player
	 * @param name
	 * @param firstName
	 * @param license
	 * @param bibNumber
	 * @param specific
	 * @param club_id
	 */
	public Player(String name, String firstName, String license, int bibNumber, String specific, int club_id) {
		super();
		Log.d(TAG, "constructor "+TAG+"(name="+name+", firstName="+firstName+", license="+license+", bibNumber="+bibNumber+", club_id="+club_id+")");
		setName(name);
		setFirstname(firstName);
		setLicense(license);
		setBibNumber(bibNumber);
		setBirthday(PLAYER_BIRTHDAY_UNKNOWN);
		setSpecific(specific);
		setClubId(club_id);
	}

	/**
	 * Player
	 * @param name
	 * @param firstName
	 * @param license
	 * @param bibNumber
	 * @param birthday
	 * @param specific
	 * @param club_id
	 */
	public Player(String name, String firstName, String license, int bibNumber, Date birthday, String specific, int club_id) {
		super();
		Log.d(TAG, "constructor "+TAG+"(name="+name+", firstName="+firstName+", license="+license+", bibNumber="+bibNumber+", birthday="+birthday+", club_id="+club_id+")");
		setName(name);
		setFirstname(firstName);
		setLicense(license);
		setBibNumber(bibNumber);
		setBirthday(birthday);
		setSpecific(specific);
		setClubId(club_id);
	}

	/**
	 * Player
	 * @param player
	 * @throws CloneNotSupportedException
	 */
	public Player(Player player) throws CloneNotSupportedException {
		super();
		if (player == null) {
			throw new CloneNotSupportedException("Player constructor with null argument");
		}
		Log.d(TAG, "constructor "+TAG+"(player="+player.toString()+")");
		setName(player.getName());
		setFirstname(player.getFirstname());
		setLicense(player.getLicense());
		setBibNumber(player.getBibNumber());
		setBirthday(player.getBirthday());
		setSpecific(player.getSpecific());
		setClubId(player.getClubId());
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		if (name == null || name.length() == 0) {
			this._name = Playerable.PLAYER_NAME_UNKNOWN;
			Log.e(TAG, "setName(name=null => "+this._name+")");
		} else if (name.length() > Playerable.MAXLENGTH_NAME) {
			this._name = name.substring(0, Playerable.MAXLENGTH_NAME);
			Log.e(TAG, "setName(too long name truncated => "+this._name+")");
		} else {
			this._name = name;
			Log.d(TAG, "setName(name="+this._name+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getName()
	 */
	@Override
	public String getName() {
		if (this._name == null || this._name.length() == 0) {
			setName(PLAYER_NAME_UNKNOWN);
		}
		return this._name;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setFirstname(java.lang.String)
	 */
	@Override
	public void setFirstname(final String firstname) {
		if (firstname == null || firstname.length() == 0) {
			this._firstName = Playerable.PLAYER_FIRSTNAME_UNKNOWN;
			Log.e(TAG, "setFirstname(firstname=null => "+this._firstName+")");
		} else if (firstname.length() > Playerable.MAXLENGTH_FIRSTNAME) {
			this._firstName = firstname.substring(0, Playerable.MAXLENGTH_FIRSTNAME);
			Log.e(TAG, "setFirstname(too long firstname truncated => "+this._firstName+")");
		} else {
			this._firstName = firstname;
			Log.d(TAG, "setFirstname(firstname="+this._firstName+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getFirstName()
	 */
	@Override
	public String getFirstname() {
		if (this._firstName == null || this._firstName.length() == 0) {
			setFirstname(PLAYER_FIRSTNAME_UNKNOWN);
		}
		return this._firstName;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setBirthday(java.util.Date)
	 */
	@Override
	public void setBirthday(final Date birthday) {
		Calendar today = Calendar.getInstance();
		if (birthday == null || birthday.after(today.getTime())) {
			// the person has 20 years by default
			today.add(Calendar.YEAR, -20);
			this._birthday = today.getTime();
			Log.e(TAG, "setBirthday(birthday is null or later => "+this._birthday+")");
		} else {
			this._birthday = birthday;
			Log.d(TAG, "setBirthday(birthday="+this._birthday+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getBirthday()
	 */
	@Override
	public Date getBirthday() {
		if (this._birthday == null) {
			setBirthday(PLAYER_BIRTHDAY_UNKNOWN);
		}
		return this._birthday;
	}

	/**
	 * setBirthdayDay
	 * @param day in the form dd/mm/yy
	 * returns void
	 */
	public void setBirthdayDay(String day) {
		if (day == null || day.length() == 0) {
			setBirthday(PLAYER_BIRTHDAY_UNKNOWN);
		} else {
			String[] ddmmyy = day.split("/");
			if (ddmmyy.length != 3) {
				setBirthday(PLAYER_BIRTHDAY_UNKNOWN);
			} else {
				Calendar aDay = Calendar.getInstance();
				int i_day = Integer.parseInt(ddmmyy[0]);
				int month = Integer.parseInt(ddmmyy[1]);
				int year = Integer.parseInt(ddmmyy[2]);
				aDay.set(year, month, i_day);
				setBirthday(aDay.getTime());
			}
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getBirthdayDay()
	 */
	@Override
	public String getBirthdayDay() {
		String s = DateFormat.getInstance().format(getBirthday());
		return s;
//		Calendar birthday = Calendar.getInstance();
//		birthday.setTime(getBirthday());
//		StringBuffer sb = new StringBuffer();
//		sb.append(birthday.get(Calendar.DATE));
//		sb.append("/");
//		sb.append(birthday.get(Calendar.MONTH)+1);
//		sb.append("/");
//		sb.append(birthday.get(Calendar.YEAR));
//		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getAge()
	 */
	@Override
	public int getAge() {
		Calendar today = Calendar.getInstance();
		int cette_annee = today.get(Calendar.YEAR);
		today.setTime(getBirthday());
		int annee_naissance = today.get(Calendar.YEAR);
		return cette_annee - annee_naissance;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setLicence(java.lang.String)
	 */
	@Override
	public void setLicense(final String license) {
		if (license == null || license.length() == 0) {
			this._license = Playerable.PLAYER_LICENSE_UNKNOWN;
			Log.e(TAG, "setLicense(license=null => "+this._license+")");
		} else if (license.length() > Playerable.MAXLENGTH_LICENSE) {
			this._license = license.substring(0, Playerable.MAXLENGTH_LICENSE);
			Log.e(TAG, "setLicense(too long license truncated => "+this._license+")");
		} else {
			this._license = license;
			Log.d(TAG, "setLicense(license="+this._license+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getLicence()
	 */
	@Override
	public String getLicense() {
		if (this._license == null || this._license.length() == 0) {
			setLicense(PLAYER_LICENSE_UNKNOWN);
		}
		return this._license;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setBibNumber(int)
	 */
	@Override
	public void setBibNumber(final int bibNumber) {
		if (bibNumber < Playerable.MINIMUM_BIB_NUMBER || bibNumber > Playerable.MAXIMUM_BIB_NUMBER) {
			this._bibNumber = Playerable.MINIMUM_BIB_NUMBER;
			Log.e(TAG, "setBibNumber(bibNumber out of bound => "+this._bibNumber+")");
		} else {
			this._bibNumber = bibNumber;
			Log.d(TAG, "setBibNumber(bibNumber="+this._bibNumber+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getBibNumber()
	 */
	@Override
	public int getBibNumber() {
		if (this._bibNumber < Playerable.MINIMUM_BIB_NUMBER || this._bibNumber > Playerable.MAXIMUM_BIB_NUMBER) {
			this._bibNumber = Playerable.MINIMUM_BIB_NUMBER;
		}
		return this._bibNumber;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setBibNumber(java.lang.String)
	 */
	@Override
	public void setBibNumber(final String bibNumber) {
		int bibNb = 0;
		try {
			bibNb = Integer.parseInt(bibNumber);
		} catch (NumberFormatException e) {
			bibNb = 0;
		}
		setBibNumber(bibNb);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getStringBibNumber()
	 */
	@Override
	public String getStringBibNumber() {
		String res = String.valueOf(getBibNumber());
		return res;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setSpecific(java.lang.String)
	 */
	@Override
	public void setSpecific(final String specific) {
		if (specific == null || specific.length() == 0) {
			this._specific = Playerable.PLAYER_NO_SPECIFIC;
			Log.e(TAG, "setSpecific(specific=null => "+this._specific+")");
		} else if (specific.length() > Playerable.MAXLENGTH_SPECIFIC) {
			this._specific = specific.substring(0, Playerable.MAXLENGTH_SPECIFIC);
			Log.e(TAG, "setSpecific(too long specific truncated => "+this._specific+")");
		} else {
			this._specific = specific;
			Log.d(TAG, "setSpecific(specific="+this._specific+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getSpecific()
	 */
	@Override
	public String getSpecific() {
		if (this._specific == null || this._specific.length() == 0) {
			setSpecific(PLAYER_NO_SPECIFIC);
		}
		return this._specific;
	}

	/**
	 * setSpecificOne
	 * @param add
	 * @param one_spe
	 * returns void
	 */
	private void setSpecificOne(final boolean add, final String one_spe) {
		Log.d(TAG, "setSpecificOne(" + (add?"add":"remove") + ", " + one_spe + "), while current=" + this.getSpecific());
		if (add) {
			// add specific function if not already present
			if (! this.getSpecific().contains(one_spe)) {
				this.setSpecific(this.getSpecific().concat(one_spe));
			}
		} else {
			// remove specific function if already present
			String spe = this.getSpecific().replace(one_spe, "");
			this.setSpecific(spe);
//			StringBuffer new_spe = new StringBuffer();
//			String sub = null;
//			for (int i = 0; i < this.getSpecific().length(); i++) {
//				sub = this.getSpecific().substring(i, i+1);
//				if (! sub.equals(one_spe)) {
//					new_spe.append(sub);
//				}
//			}
//			this.setSpecific(new_spe.toString());
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setGoal(boolean)
	 */
	@Override
	public void setGoal(final boolean goal) {
		this.setSpecificOne(goal, PLAYER_SPECIFIC_GOAL);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#isGoal()
	 */
	@Override
	public boolean isGoal() {
		return this.getSpecific().contains(PLAYER_SPECIFIC_GOAL);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setCaptain(boolean)
	 */
	@Override
	public void setCaptain(final boolean captain) {
		this.setSpecificOne(captain, PLAYER_SPECIFIC_CAPTAIN);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#isCaptain()
	 */
	@Override
	public boolean isCaptain() {
		return this.getSpecific().contains(PLAYER_SPECIFIC_CAPTAIN);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setCoach(boolean)
	 */
	@Override
	public void setCoach(final boolean coach) {
		this.setSpecificOne(coach, PLAYER_SPECIFIC_COACH);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#isCoach()
	 */
	@Override
	public boolean isCoach() {
		return this.getSpecific().contains(PLAYER_SPECIFIC_COACH);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setDoctor(boolean)
	 */
	@Override
	public void setDoctor(final boolean doctor) {
		this.setSpecificOne(doctor, PLAYER_SPECIFIC_DOCTOR);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#isDoctor()
	 */
	@Override
	public boolean isDoctor() {
		return this.getSpecific().contains(PLAYER_SPECIFIC_DOCTOR);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setKine(boolean)
	 */
	@Override
	public void setKine(final boolean kine) {
		this.setSpecificOne(kine, PLAYER_SPECIFIC_KINE);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#isKine()
	 */
	@Override
	public boolean isKine() {
		return this.getSpecific().contains(PLAYER_SPECIFIC_KINE);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setClubId(long)
	 */
	@Override
	public void setClubId(long club_id) {
		this._club_id = club_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#setClubId(java.lang.String)
	 */
	@Override
	public void setClubId(String club_id) {
		int c_id = 0;
		try {
			c_id = Integer.parseInt(club_id);
		} catch (NumberFormatException e) {
			Log.e(TAG, "club_id given in parameter ("+club_id+") is not an integer");
			c_id = 0;
		}
		this._club_id = c_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getClubId()
	 */
	@Override
	public long getClubId() {
		return _club_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Playerable#getClubStringID()
	 */
	@Override
	public String getClubStringID() {
		return String.valueOf(this._club_id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append(TAG);
		res.append(" no ");
		res.append(getId());
		res.append(": ");
		res.append(getName());
		res.append(", ");
		res.append(getFirstname());
		res.append(", ");
		res.append(getLicense());
		res.append(", ");
		res.append(getBirthdayDay());
		res.append(", ");
		res.append(getAge());
		res.append(" ans, ");
		res.append(getSpecific());
		res.append(", ");
		res.append(getClubId());
		return res.toString();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiable#toBackup()
	 */
	@Override
	public String toBackup() {
		StringBuffer res = new StringBuffer();
		res.append(TAG);
		res.append("!");
		res.append(getId());
		res.append("!");
		res.append(getName());
		res.append("!");
		res.append(getFirstname());
		res.append("!");
		res.append(getLicense());
		res.append("!");
		res.append(getBirthdayDay());
		res.append("!");
		res.append(getSpecific());
		res.append("!");
		res.append(getClubId());
		return res.toString();
	}

	/**
	 * fromBackup
	 * @param bck
	 * @return
	 * returns Player
	 */
	public static Player fromBackup(final String bck) {
		Player player = fromBackup(bck, BasketOpenHelper.getDbVersion());
		return player;
	}

	/**
	 * fromBackup
	 * @param bck
	 * @param db_vers
	 * @return
	 * returns Player
	 */
	public static Player fromBackup(final String bck, int db_vers) {
		Player player = null;
		if (bck != null) {
			String[] parts = bck.split("!");
			if (parts != null && parts.length >= 6) {
				if (TAG.equals(parts[0])) {
					player = new Player();
					player.setId(Long.valueOf(parts[1]).longValue());
					player.setName(parts[2]);
					player.setFirstname(parts[3]);
					player.setLicense(parts[4]);
					player.setBirthdayDay(parts[5]);
				}
			}
			switch (db_vers) {
			case 4:
				if (parts != null && parts.length >= 7) {
					player.setSpecific(parts[6]);
				}
				break;
			case 5:
				if (parts != null && parts.length >= 8) {
					player.setClubId(parts[7]);
				}
			default:
				break;
			}
		}
		return player;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		super.clone();
		return new Player(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
			Player.class.isInstance(o) &&
			((Player)o).getName().equals(getName()) &&
			((Player)o).getFirstname().equals(getFirstname()) &&
			((Player)o).getLicense().equals(getLicense()) &&
			((Player)o).getBirthday().equals(getBirthday());
		return res;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiant#getObjectValues()
	 */
	@Override
	public Vector<String[]> getObjectValues() {
		Vector<String[]> objectValues = new Vector<String[]>();
		objectValues.add(new String[] {KEY_ROWID, getIdString()});
        objectValues.add(new String[] {KEY_PLAYER_NAME, getName()});
        objectValues.add(new String[] {KEY_PLAYER_FIRSTNAME, getFirstname()});
        objectValues.add(new String[] {KEY_PLAYER_LICENSE, getLicense()});
        objectValues.add(new String[] {KEY_PLAYER_BIB_NUMBER, getStringBibNumber()});
//        objectValues.add(new String[] {KEY_PLAYER_BIRTHDAY, getBirthdayDay()});
        objectValues.add(new String[] {KEY_PLAYER_SPECIFIC, getSpecific()});
        objectValues.add(new String[] {KEY_PLAYER_CLUB_ID, getClubStringID()});
        return objectValues;
	}

}
