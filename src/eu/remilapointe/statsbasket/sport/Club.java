/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

import java.util.Vector;

import eu.remilapointe.statsbasket.db.BasketOpenHelper;

/**
 * @author lapoint1
 *
 */
public class Club extends Identifiant implements Identifiable, Clubable, Cloneable {

	private static final String TAG = Club.class.getSimpleName();

	protected String _name = null;
	protected String _number = null;
	protected String _town = null;

	/**
	 * Club
	 */
	public Club() {
		super();
		Log.d(TAG, "constructor "+TAG+"()");
		setName(null);
		setNumber(null);
		setTown(null);
	}

	/**
	 * Club
	 * @param name
	 * @param number
	 * @param town
	 */
	public Club(String name, String number, String town) {
		super();
		Log.d(TAG, "constructor "+TAG+"(name="+name+", number="+number+", town="+town+")");
		setName(name);
		setNumber(number);
		setTown(town);
	}

	/**
	 * Club
	 * @param club
	 * @throws CloneNotSupportedException
	 */
	public Club(Club club) throws CloneNotSupportedException {
		super();
		if (club == null) {
			throw new CloneNotSupportedException(TAG+" constructor with null argument");
		}
		Log.d(TAG, "constructor "+TAG+"(club="+club.toString()+")");
		setName(club.getName());
		setNumber(club.getNumber());
		setTown(club.getTown());
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Clubable#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		if (name == null) {
			this._name = Clubable.CLUB_NAME_UNKNOWN;
			Log.d(TAG, "setName(name=null => "+this._name+")");
		} else if (name.length() > Clubable.MAXLENGTH_NAME) {
			this._name = name.substring(0, Clubable.MAXLENGTH_NAME);
			Log.d(TAG, "setName(too long name truncated => "+this._name+")");
		} else {
			this._name = name;
			Log.d(TAG, "setName(name="+this._name+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Clubable#getName()
	 */
	@Override
	public String getName() {
		if (_name == null) {
			setName(null);
		}
		return _name;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Clubable#setNumber(java.lang.String)
	 */
	@Override
	public void setNumber(final String number) {
		if (number == null) {
			this._number = Clubable.CLUB_NUMBER_UNKNOWN;
			Log.d(TAG, "setNumber(number=null => "+this._number+")");
		} else if (number.length() > Clubable.MAXLENGTH_NUMBER) {
			this._number = number.substring(0, Clubable.MAXLENGTH_NUMBER);
			Log.d(TAG, "setNumber(too long number truncated => "+this._number+")");
		} else {
			this._number = number;
			Log.d(TAG, "setNumber(number="+this._number+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Clubable#getNumber()
	 */
	@Override
	public String getNumber() {
		if (_number == null) {
			setNumber(null);
		}
		return _number;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Clubable#setTown(java.lang.String)
	 */
	@Override
	public void setTown(final String town) {
		if (town == null) {
			this._town = Clubable.CLUB_TOWN_UNKNOWN;
			Log.d(TAG, "setTown(town=null => "+this._town+")");
		} else if (town.length() > Clubable.MAXLENGTH_TOWN) {
			this._town = town.substring(0, Clubable.MAXLENGTH_TOWN);
			Log.d(TAG, "setTown(too long town truncated => "+this._town+")");
		} else {
			this._town = town;
			Log.d(TAG, "setTown(town="+this._town+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Clubable#getTown()
	 */
	@Override
	public String getTown() {
		if (_town == null) {
			setTown(null);
		}
		return _town;
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
		res.append(getNumber());
		res.append(", ");
		res.append(getTown());
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
		res.append(getNumber());
		res.append("!");
		res.append(getTown());
		return res.toString();
	}

	/**
	 * fromBackup
	 * @param bck
	 * @return
	 * returns Club
	 */
	public static Club fromBackup(final String bck) {
		Club club = fromBackup(bck, BasketOpenHelper.getDbVersion());
		return club;
	}

	/**
	 * fromBackup
	 * @param bck
	 * @param db_vers
	 * @return
	 * returns Club
	 */
	public static Club fromBackup(final String bck, int db_vers) {
		Club club = null;
		if (bck != null) {
			String[] parts = bck.split("!");
			if (parts != null && parts.length == 5) {
				if (TAG.equals(parts[0])) {
					club = new Club();
					club.setId(Long.valueOf(parts[1]).longValue());
					club.setName(parts[2]);
					club.setNumber(parts[3]);
					club.setTown(parts[4]);
				}
			}
		}
		return club;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		super.clone();
		return new Club(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
		Club.class.isInstance(o) &&
			((Club)o).getName().equals(getName()) &&
			((Club)o).getNumber().equals(getNumber()) &&
			((Club)o).getTown().equals(getTown());
		return res;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiant#getObjectValues()
	 */
	@Override
	public Vector<String[]> getObjectValues() {
		Vector<String[]> objectValues = new Vector<String[]>();
		objectValues.add(new String[] {KEY_ROWID, String.valueOf(getId())});
        objectValues.add(new String[] {KEY_CLUB_NAME, getName()});
        objectValues.add(new String[] {KEY_CLUB_NUMBER, getNumber()});
        objectValues.add(new String[] {KEY_CLUB_TOWN, getTown()});
        return objectValues;
	}

}
