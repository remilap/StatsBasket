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
public class Team extends Identifiant implements Identifiable, Teamable, Cloneable {

	private static final String TAG = Team.class.getSimpleName();

	protected String _name = null;
	protected long _club_id = 0;
	protected String _list_players_id = null;

	/**
	 * Team
	 */
	public Team() {
		super();
		Log.d(TAG, "constructor "+TAG+"()");
		setName(null);
		setClubId(0);
		setPlayersId(null);
	}

	/**
	 * Team
	 * @param name
	 * @param categ
	 * @param list_players
	 */
	public Team(String name, long club_id, String list_players_id) {
		super();
		Log.d(TAG, "constructor "+TAG+"(name="+name+", club_id="+club_id+", list_players_id="+list_players_id+")");
		setName(name);
		setClubId(club_id);
		setPlayersId(list_players_id);
	}

	/**
	 * Team
	 * @param team
	 * @throws CloneNotSupportedException
	 */
	public Team(Team team) throws CloneNotSupportedException {
		super();
		if (team == null) {
			throw new CloneNotSupportedException(TAG+" constructor with null argument");
		}
		Log.d(TAG, "constructor "+TAG+"(team="+team.toString()+")");
		setName(team.getName());
		setClubId(team.getClubId());
		setPlayersId(team.getPlayersId());
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Teamable#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		if (name == null) {
			this._name = Teamable.TEAM_NAME_UNKNOWN;
			Log.d(TAG, "setName(name=null => "+this._name+")");
		} else if (name.length() >= Teamable.MAXLENGTH_NAME) {
			this._name = name.substring(0, Teamable.MAXLENGTH_NAME);
			Log.d(TAG, "setName(too long name truncated => "+this._name+")");
		} else {
			this._name = name;
			Log.d(TAG, "setName(name="+this._name+")");
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Teamable#getName()
	 */
	@Override
	public String getName() {
		if (this._name == null) {
			setName(null);
		}
		return this._name;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Teamable#setClubId(long)
	 */
	@Override
	public void setClubId(long club_id) {
		this._club_id = club_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Teamable#setClubId(java.lang.String)
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
	 * @see eu.remilapointe.statsbasket.sport.Teamable#getClubId()
	 */
	@Override
	public long getClubId() {
		return this._club_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Teamable#getClubStringID()
	 */
	@Override
	public String getClubStringId() {
		return String.valueOf(this._club_id);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Teamable#setPlayers(java.util.List)
	 */
	@Override
	public void setPlayersId(String list_players_id) {
		if (list_players_id == null) {
			list_players_id = "";
		}
		this._list_players_id = list_players_id;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Teamable#getPlayers()
	 */
	@Override
	public String getPlayersId() {
		if (this._list_players_id == null) {
			this._list_players_id = "";
		}
		return this._list_players_id;
	}

	/**
	 * getPlayersNumber
	 * @return
	 * returns int
	 */
	public int getPlayersNumber() {
		return getPlayersId().split(",").length;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiant#toString()
	 */
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append(TAG);
		res.append(" no ");
		res.append(getId());
		res.append(": ");
		res.append(getName());
		res.append(", club_");
		res.append(getClubId());
		res.append(", players_id: ");
		res.append(getPlayersId());
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
		res.append(getClubId());
		res.append("!");
		res.append(getPlayersId());
		return res.toString();
	}

	/**
	 * fromBackup
	 * @param bck
	 * @return
	 * returns Team
	 */
	public static Team fromBackup(final String bck) {
		Team team = fromBackup(bck, BasketOpenHelper.getDbVersion());
		return team;
	}

	/**
	 * fromBackup
	 * @param bck
	 * @param db_vers
	 * @return
	 * returns Team
	 */
	public static Team fromBackup(final String bck, int db_vers) {
		Team team = null;
		if (bck != null) {
			String[] parts = bck.split("!");
			if (parts != null && parts.length >= 5) {
				if (TAG.equals(parts[0])) {
					team = new Team();
					team.setId(Long.valueOf(parts[1]).longValue());
					team.setName(parts[2]);
					team.setClubId(parts[3]);
					team.setPlayersId(parts[5]);
				}
			}
		}
		return team;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiant#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		super.clone();
		return new Team(this);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiant#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean res = super.equals(o) &&
		Team.class.isInstance(o) &&
		((Team)o).getName().equals(this.getName()) &&
		((Team)o).getClubId() == this.getClubId() &&
		((Team)o).getPlayersId().equals(this.getPlayersId());
		return res;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.sport.Identifiant#getObjectValues()
	 */
	@Override
	public Vector<String[]> getObjectValues() {
		Vector<String[]> objectValues = new Vector<String[]>();
		objectValues.add(new String[] {KEY_TEAM_NAME, getName()});
		objectValues.add(new String[] {KEY_TEAM_CLUB_ID, getClubStringId()});
		objectValues.add(new String[] {KEY_TEAM_PLAYERS_IDS, getPlayersId()});
		return objectValues;
	}

}
