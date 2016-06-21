/**
 * 
 */
package eu.remilapointe.statsbasket.sport;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import eu.remilapointe.statsbasket.db.BasketOpenHelper;

/**
 * @author lapoint1
 *
 */
public class Match extends Identifiant implements Identifiable, Cloneable, Matchable {

	private static final String TAG = Match.class.getSimpleName();

	protected String _ref = null;
	protected long _club_a_id = 0;
	protected long _club_b_id = 0;
	protected String _date = null;
	protected String _location = null;

	/**
	 * 
	 */
	public Match() {
		super();
		Log.d(TAG, "constructor "+TAG+"()");
		setReference(null);
		setClubAId(0);
		setClubBId(0);
		setDateString(null);
		setLocation(null);
	}

	/**
	 * @param ref
	 * @param clubA
	 * @param clubB
	 * @param date
	 * @param location
	 */
	public Match(String ref, long clubAId, long clubBId, String date, String location) {
		super();
		Log.d(TAG, "constructor "+TAG+"(ref="+ref+", clubAId="+clubAId+", clubBId="+clubBId+", date="+date+", location="+location+")");
		setReference(ref);
		setClubAId(clubAId);
		setClubBId(clubBId);
		setDateString(date);
		setLocation(location);
	}

	/**
	 * @param match
	 * @throws CloneNotSupportedException
	 */
	public Match(Match match) throws CloneNotSupportedException {
		super();
		if (match == null) {
			throw new CloneNotSupportedException(TAG+" constructor with null argument");
		}
		Log.d(TAG, "constructor "+TAG+"(match="+match.toString()+")");
		setReference(match.getReference());
		setClubAId(match.getClubAId());
		setClubBId(match.getClubBId());
		setDateString(match.getDateString());
		setLocation(match.getLocation());
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setReference(java.lang.String)
	 */
	public void setReference(final String reference) {
		if (reference == null) {
			this._ref = Matchable.MATCH_REF_UNKNOWN;
			Log.d(TAG, "setReference(reference=null => "+this._ref+")");
		} else if (reference.length() > Matchable.MAXLENGTH_REF) {
			this._ref = reference.substring(0, Matchable.MAXLENGTH_REF);
			Log.d(TAG, "setReference(too long reference truncated => "+this._ref+")");
		} else {
			this._ref = reference;
			Log.d(TAG, "setReference(reference="+this._ref+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getReference()
	 */
	public String getReference() {
		if (_ref == null) {
			setReference(null);
		}
		return _ref;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setClubAId(long)
	 */
	public void setClubAId(final long club_a_id) {
		this._club_a_id = club_a_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setClubAId(java.lang.String)
	 */
	public void setClubAId(final String club_a_id) {
		int c_id = 0;
		try {
			c_id = Integer.parseInt(club_a_id);
		} catch (NumberFormatException e) {
			Log.e(TAG, "club_id given in parameter ("+club_a_id+") is not an integer");
			c_id = 0;
		}
		this._club_a_id = c_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getClubAId()
	 */
	public long getClubAId() {
		return this._club_a_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getClubAStringId()
	 */
	public String getClubAStringId() {
		return String.valueOf(this._club_a_id);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setClubBId(long)
	 */
	public void setClubBId(final long club_b_id) {
		this._club_b_id = club_b_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setClubBId(java.lang.String)
	 */
	public void setClubBId(final String club_b_id) {
		int c_id = 0;
		try {
			c_id = Integer.parseInt(club_b_id);
		} catch (NumberFormatException e) {
			Log.e(TAG, "club_id given in parameter ("+club_b_id+") is not an integer");
			c_id = 0;
		}
		this._club_b_id = c_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getClubBId()
	 */
	public long getClubBId() {
		return this._club_b_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getClubBStringId()
	 */
	public String getClubBStringId() {
		return String.valueOf(this._club_b_id);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setDateString(java.lang.String)
	 */
	public void setDateString(final String date) {
		if (date == null) {
			Calendar c = Calendar.getInstance();
			this._date = Matchable.DATE_FORMAT.format(c.getTime());
			Log.d(TAG, "setDate(date=null => "+this._date+")");
		} else if (date.length() > Matchable.MAXLENGTH_DATE) {
			this._date = date.substring(0, Matchable.MAXLENGTH_DATE);
			Log.d(TAG, "setDate(too long date truncated => "+this._date+")");
		} else {
			this._date = date;
			Log.d(TAG, "setDate(date="+this._date+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getDateString()
	 */
	public String getDateString() {
		if (_date == null) {
			setDateString(null);
		}
		return _date;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setDate(java.util.Date)
	 */
	public void setDate(final Date dat) {
		String d = Matchable.DATE_FORMAT.format(dat);
		setDateString(d);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getDate()
	 */
	public Date getDate() {
		Date dat = new Date();
		try {
			dat = Matchable.DATE_FORMAT.parse(getDateString());
		} catch (ParseException e) {
			dat = Calendar.getInstance().getTime();
		}
		return dat;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setDateYear(int)
	 */
	public void setDateYear(final int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(getDate());
		c.set(Calendar.YEAR, year);
		String d = Matchable.DATE_FORMAT.format(c.getTime());
		setDateString(d);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getDateYear()
	 */
	public int getDateYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(getDate());
		return c.get(Calendar.YEAR);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setDateMonth(int)
	 */
	public void setDateMonth(final int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(getDate());
		c.set(Calendar.MONTH, month);
		String d = Matchable.DATE_FORMAT.format(c.getTime());
		setDateString(d);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getDateMonth()
	 */
	public int getDateMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(getDate());
		return c.get(Calendar.MONTH);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setDateDayOfMonth(int)
	 */
	public void setDateDayOfMonth(final int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.setTime(getDate());
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		String d = Matchable.DATE_FORMAT.format(c.getTime());
		setDateString(d);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getDateDayOfMonth()
	 */
	public int getDateDayOfMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(getDate());
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#setLocation(java.lang.String)
	 */
	public void setLocation(final String location) {
		if (location == null) {
			this._location = Matchable.MATCH_LOCATION_UNKNOWN;
			Log.d(TAG, "setLocation(location=null => "+this._location+")");
		} else if (location.length() > Matchable.MAXLENGTH_LOCATION) {
			this._location = location.substring(0, Matchable.MAXLENGTH_LOCATION);
			Log.d(TAG, "setLocation(too long location truncated => "+this._location+")");
		} else {
			this._location = location;
			Log.d(TAG, "setLocation(location="+this._location+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Matchable#getLocation()
	 */
	public String getLocation() {
		if (_location == null) {
			setLocation(null);
		}
		return _location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append(TAG);
		res.append(" no ");
		res.append(getId());
		res.append(": ");
		res.append(getReference());
		res.append(", ");
		res.append(getClubAId());
		res.append(", ");
		res.append(getClubBId());
		res.append(", ");
		res.append(getDateString());
		res.append(", ");
		res.append(getLocation());
		return res.toString();
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiable#toBackup()
	 */
	public String toBackup() {
		StringBuffer res = new StringBuffer();
		res.append(TAG);
		res.append("!");
		res.append(getId());
		res.append("!");
		res.append(getReference());
		res.append("!");
		res.append(getClubAId());
		res.append("!");
		res.append(getClubBId());
		res.append("!");
		res.append(getDateString());
		res.append("!");
		res.append(getLocation());
		return res.toString();
	}

	/**
	 * fromBackup
	 * @param bck
	 * @return
	 * returns Match
	 */
	public static Match fromBackup(final String bck) {
		Match match = fromBackup(bck, BasketOpenHelper.getDbVersion());
		return match;
	}

	/**
	 * fromBackup
	 * @param bck
	 * @param db_vers
	 * @return
	 * returns Match
	 */
	public static Match fromBackup(final String bck, int db_vers) {
		Match match = null;
		if (bck != null) {
			String[] parts = bck.split("!");
			if (parts != null && parts.length >= 6) {
				if (TAG.equals(parts[0])) {
					match = new Match();
					match.setId(Long.valueOf(parts[1]).longValue());
					match.setReference(parts[2]);
					match.setClubAId(parts[3]);
					match.setClubBId(parts[4]);
					match.setDateString(parts[5]);
					match.setLocation(parts[6]);
				}
			}
			switch (db_vers) {
			case 2:
				if (parts != null && parts.length >= 7) {
					match.setReference(parts[2]);
				}
				break;
			default:
				break;
			}
		}
		return match;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		super.clone();
		return new Match(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
			Match.class.isInstance(o) &&
			((Match)o).getReference().equals(getReference()) &&
			((Match)o).getClubAId() == getClubAId() &&
			((Match)o).getClubBId() == getClubBId() &&
			((Match)o).getDateString().equals(getDateString());
		return res;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiant#getObjectValues()
	 */
	@Override
	public Vector<String[]> getObjectValues() {
		Vector<String[]> objectValues = new Vector<String[]>();
		objectValues.add(new String[] {KEY_ROWID, getIdString()});
        objectValues.add(new String[] {KEY_MATCH_REF, getReference()});
        objectValues.add(new String[] {KEY_MATCH_CLUB_A, getClubAStringId()});
        objectValues.add(new String[] {KEY_MATCH_CLUB_B, getClubBStringId()});
        objectValues.add(new String[] {KEY_MATCH_DATE, getDateString()});
        objectValues.add(new String[] {KEY_MATCH_LOCATION, getLocation()});
        return objectValues;
	}

}
