/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import java.util.HashMap;
import java.util.List;

import eu.remilapointe.statsbasket.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author lapoint1
 *
 */
public class BasketScoringListAdapter extends ArrayAdapter<BasketScoringBoardPlayer> {

	private static final String TAG = BasketScoringListAdapter.class.getSimpleName();

	protected Context _context = null;
	protected LayoutInflater _inflater;
	protected int _viewId = 0;
	protected int _side = 0;
	protected HashMap<BasketScoringBoardPlayer, Integer> mIdMap = new HashMap<BasketScoringBoardPlayer, Integer>();

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param player_lines
	 */
	public BasketScoringListAdapter(Context context, int side, int textViewResourceId, List<BasketScoringBoardPlayer> player_lines) {
		super(context, textViewResourceId, player_lines);
		Log.d(TAG, "new BasketScoringListAdapter");
		_context = context;
		_inflater = LayoutInflater.from(context);
		_viewId = textViewResourceId;
		_side = side;
		for (int i = 0; i < player_lines.size(); ++i) {
			mIdMap.put(player_lines.get(i), i);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// This test avoids to build again the view if already created
		if (convertView == null) {
			// Retrieve the elements of the view
			convertView = _inflater.inflate(_viewId, null);
		}
		// Retrieve the fields
		TextView _txt_player_name = (TextView) convertView.findViewById(R.id.txt_player_name);
		TextView _txt_player_num = (TextView) convertView.findViewById(R.id.txt_player_num);
		TextView _txt_player_faults = (TextView) convertView.findViewById(R.id.txt_player_faults);
		TextView _txt_player_points = (TextView) convertView.findViewById(R.id.txt_player_points);

		BasketScoringOnClik listener = new BasketScoringOnClik(1, position, _txt_player_points);
		((Button) convertView.findViewById(R.id.bt_shoot_1p)).setOnClickListener(listener);
		listener = new BasketScoringOnClik(2, position, _txt_player_points);
		((Button) convertView.findViewById(R.id.bt_shoot_2p)).setOnClickListener(listener);
		listener = new BasketScoringOnClik(3, position, _txt_player_points);
		((Button) convertView.findViewById(R.id.bt_shoot_3p)).setOnClickListener(listener);

		// Assign the values
		BasketScoringBoardPlayer item = getItem(position);
		Log.d(TAG, "ScoringPlayersListAdapter.getView " + item.toString());
		_txt_player_name.setText(item.get_player_name());
		_txt_player_num.setText(String.valueOf(item.get_player_num()));
		_txt_player_faults.setText(String.valueOf(item.get_nb_fautes()));
		int pts = item.get_shoot_1p_ok() + 2 * item.get_shoot_2p_ok() + 3 * item.get_shoot_3p_ok();
		_txt_player_points.setText(String.valueOf(pts));

		return convertView;
//		return super.getView(position, convertView, parent);
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		BasketScoringBoardPlayer item = getItem(position);
		return mIdMap.get(item);
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	public void click_shoot_1p(View view) {
		
	}

}
