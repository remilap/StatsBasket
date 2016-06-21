/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.widget.CursorAdapter;
import eu.remilapointe.statsbasket.R;

/**
 * @author lapoint1
 *
 */
public abstract class MyCursorAdapter extends CursorAdapter {

	protected Context _context = null;
	protected LayoutInflater _inflater;
	protected ContentResolver _cr = null;
	protected Resources _res = null;
	protected int _black = 0;
	protected int _darker_gray = 0;

	/**
	 * @param context
	 * @param c
	 */
	public MyCursorAdapter(Context context, Cursor c) {
		super(context, c);
		_context = context;
		_inflater = LayoutInflater.from(context);
		_cr = context.getContentResolver();
		_res = context.getResources();
		_black = _res.getColor(R.color.black);
		_darker_gray = _res.getColor(android.R.color.darker_gray);
	}


}
