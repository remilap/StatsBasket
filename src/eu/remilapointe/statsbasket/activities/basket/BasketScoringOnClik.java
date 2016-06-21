/**
 * 
 */
package eu.remilapointe.statsbasket.activities.basket;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author lapoint1
 *
 */
public class BasketScoringOnClik implements OnClickListener {

	private static final String TAG = BasketScoringOnClik.class.getSimpleName();
	private int _button = 0;
	private int _position = 0;
	private TextView _pts = null;

	/**
	 * @param button
	 */
	public BasketScoringOnClik(int button, int position, TextView pts) {
		super();
		_button  = button;
		_position = position;
		_pts = pts;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		Log.d(TAG, "click on " + view.getId() + " for button " + _button + " and line " + _position);
		String txt = (String) _pts.getText();
		int val = 0;
		try {
			val = Integer.parseInt(txt);
		} catch (NumberFormatException e) {
			val = 0;
		}
		val += _button;
		txt = String.valueOf(val);
		_pts.setText(txt);
	}

}
