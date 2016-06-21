package eu.remilapointe.statsbasket.sport;

import java.util.Vector;


/**
 * @author lapoint1
 * @version 1.1
 */
public class Identifiant implements Identifiable, Cloneable {

	private static final String TAG = Identifiant.class.getSimpleName();
	protected static Loggable Log = new DefaultLog();

	private static final long NO_ID = -1;

	protected static long _last_id = 0;

	protected long _id = 0;

	/**
	 * setLog
	 * @param log
	 * returns void
	 */
	public static void setLog(Loggable log) {
		Log = log;
	}

	/**
	 * Identifiant
	 */
	public Identifiant() {
		super();
		setId(NO_ID);
	}

	/**
	 * Identifiant
	 * @param ident
	 */
	public Identifiant(Identifiable ident) throws CloneNotSupportedException {
		super();
		if (ident == null) {
			throw new CloneNotSupportedException(TAG+" constructor with null argument");
		}
		Log.d(TAG, "constructor "+TAG+"(ident="+ident.toString()+")");
		_id = ident.getId();
		setId(getNextId());
	}

	/**
	 * getLastId
	 * @return the _last_id
	 * returns long
	 */
	public static long getLastId() {
		return _last_id;
	}

	/**
	 * getNextId
	 * @return the id after the last one
	 * returns long
	 */
	public static long getNextId() {
		return ++_last_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.general.Identifiable#getId()
	 */
	@Override
	public long getId() {
		return _id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiable#getIdString()
	 */
	@Override
	public String getIdString() {
		return String.valueOf(_id);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.general.Identifiable#setId(long)
	 */
	@Override
	public void setId(long id) {
		_id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append(TAG);
		res.append(":");
		res.append(getId());
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
		return res.toString();
	}

	/**
	 * fromBackup
	 * @param bck
	 * @return
	 * returns Identifiant
	 */
	public static Identifiant fromBackup(final String bck) {
		Identifiant id = null;
		if (bck != null) {
			String[] parts = bck.split("!");
			if (parts != null && parts.length == 2) {
				if (TAG.equals(parts[0])) {
					id = new Identifiant();
					id.setId(Long.valueOf(parts[1]).longValue());
				}
			}
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		super.clone();
		return new Identifiant(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
			((Identifiant)o).getId() == getId();
		return res;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.general.Identifiable#getObjectValues()
	 */
	@Override
	public Vector<String[]> getObjectValues() {
		Vector<String[]> objectValues = new Vector<String[]>();
		objectValues.add(new String[] {KEY_ROWID, String.valueOf(getId())});
		return objectValues;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiable#getNbFields()
	 */
	@Override
	public int getNbFields() {
		return getObjectValues().size();
	}

}
