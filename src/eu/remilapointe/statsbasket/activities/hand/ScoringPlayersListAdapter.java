/**
 * 
 */
package eu.remilapointe.statsbasket.activities.hand;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.remilapointe.statsbasket.R;

/**
 * @author lapoint1
 *
 */
public class ScoringPlayersListAdapter extends ArrayAdapter<ScoringBoardPlayer> {

	private static final String TAG = ScoringPlayersListAdapter.class.getSimpleName();

	protected Context _context = null;
	protected LayoutInflater _inflater;
	protected int _viewId = 0;
	protected int _side = 0;
	protected HashMap<ScoringBoardPlayer, Integer> mIdMap = new HashMap<ScoringBoardPlayer, Integer>();

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param player_lines
	 */
	public ScoringPlayersListAdapter(Context context, int side, int textViewResourceId, List<ScoringBoardPlayer> player_lines) {
		super(context, textViewResourceId, player_lines);
		Log.d(TAG, "new ScoringPlayersListAdapter");
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
		TextView _txt_player_name = null;
		TextView _txt_player_num = null;
		TextView _txt_player_exclu = null;
		if (_side == 1) {
			_txt_player_name = (TextView) convertView.findViewById(R.id.txt_player_a_name);
			_txt_player_num = (TextView) convertView.findViewById(R.id.txt_player_a_num);
			_txt_player_exclu = (TextView) convertView.findViewById(R.id.txt_player_a_exclu);
		} else {
			_txt_player_name = (TextView) convertView.findViewById(R.id.txt_player_b_name);
			_txt_player_num = (TextView) convertView.findViewById(R.id.txt_player_b_num);
			_txt_player_exclu = (TextView) convertView.findViewById(R.id.txt_player_b_exclu);
		}
		// Assign the values
		ScoringBoardPlayer item = getItem(position);
//		Log.d(TAG, "ScoringPlayersListAdapter.getView " + item.toString());
		_txt_player_name.setText(item.get_player_name());
		_txt_player_num.setText(item.get_player_num());
		_txt_player_exclu.setText(item.get_player_exclu());

		return convertView;
//		return super.getView(position, convertView, parent);
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		ScoringBoardPlayer item = getItem(position);
		return mIdMap.get(item);
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

}
