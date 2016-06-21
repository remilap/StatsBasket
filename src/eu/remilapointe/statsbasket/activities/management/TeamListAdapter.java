/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.db.TeamDB;
import eu.remilapointe.statsbasket.sport.Team;

/**
 * @author lapoint1
 *
 */
public class TeamListAdapter extends MyCursorAdapter {

	private TeamDB _teamDb = TeamDB.getInstance();

	/**
	 * @param context
	 * @param cursor
	 */
	public TeamListAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		_teamDb.setContentResolver(this._cr);
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView _txt_team_id = (TextView) view.findViewById(R.id.team_id);
		TextView _txt_team_name = (TextView) view.findViewById(R.id.team_name);
		TextView _txt_team_club_name = (TextView) view.findViewById(R.id.team_club_name);

		if (cursor.getPosition() % 2 == 0) {
			view.setBackgroundColor(_darker_gray);
			_txt_team_name.setTextColor(_black);
			_txt_team_club_name.setTextColor(_black);
		}

		Team team = _teamDb.getFromDatabase(cursor);
		Log.d("TeamListAdapter", team.toString());

		_txt_team_id.setText(team.getIdString());

		_txt_team_name.setText(team.getName());

		String club_str = Utilities.getTheClubName((Activity) context, (int) team.getClubId());
		_txt_team_club_name.setText(club_str);

	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = _inflater.inflate(R.layout.team_list_item, parent, false);
		return view;
	}

}
