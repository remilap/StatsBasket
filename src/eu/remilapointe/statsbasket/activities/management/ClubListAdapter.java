/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.db.ClubDB;
import eu.remilapointe.statsbasket.sport.Club;

/**
 * @author lapoint1
 *
 */
public class ClubListAdapter extends MyCursorAdapter {

	private ClubDB _clubDb = ClubDB.getInstance();

	/**
	 * @param context
	 * @param cursor
	 */
	public ClubListAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		_clubDb.setContentResolver(this._cr);
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView _txt_club_id = (TextView) view.findViewById(R.id.club_id);
		TextView _txt_club_name = (TextView) view.findViewById(R.id.club_name);
		TextView _txt_club_number = (TextView) view.findViewById(R.id.club_number);
		TextView _txt_club_town = (TextView) view.findViewById(R.id.club_town);

		if (cursor.getPosition() % 2 == 0) {
			view.setBackgroundColor(_darker_gray);
			_txt_club_name.setTextColor(_black);
			_txt_club_number.setTextColor(_black);
			_txt_club_town.setTextColor(_black);
		}

		Club club = _clubDb.getFromDatabase(cursor);

		_txt_club_id.setText(club.getIdString());

		_txt_club_name.setText(club.getName());

		_txt_club_number.setText(club.getNumber());

		_txt_club_town.setText(club.getTown());
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = _inflater.inflate(R.layout.club_list_item, parent, false);
		return view;
	}

}
