/**
 * 
 */
package eu.remilapointe.statsbasket.common.play.android;

import android.widget.TextView;
import eu.remilapointe.statsbasket.common.play.CommonEventDispatchable;
import eu.remilapointe.statsbasket.common.play.GraphicalAbstract;
import eu.remilapointe.statsbasket.common.play.GraphicalUpdatable;
import eu.remilapointe.statsbasket.sport.DefaultLog;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class GraphicalTextView extends GraphicalAbstract implements GraphicalUpdatable, CommonEventDispatchable {

	private static String TAG = GraphicalTextView.class.getSimpleName();

	protected Loggable _log = new DefaultLog();
	protected TextView _tv = null;


	/**
	 * Constructor
	 */
	public GraphicalTextView() {
		super();
		this.getLog().d(TAG, "GraphicalTextView simple constructor");
	}

	/**
	 * Constructor
	 * @param tv
	 */
	public GraphicalTextView(final TextView tv) {
		this();
		setTextView(tv);
		this.getLog().d(TAG, "GraphicalTextView constructor with TextView");
	}

	/**
	 * Constructor
	 * @param tv
	 * @param log
	 */
	public GraphicalTextView(final TextView tv, final Loggable log) {
		this(tv);
		setLog(log);
		this.getLog().d(TAG, "GraphicalTextView constructor with TextView and log");
	}


	/**
	 * setTextView
	 * @param tv
	 */
	public void setTextView(final TextView tv) {
		if (tv == null) {
			this.getLog().e(TAG, "setTextView with a null TextView");
		} else {
			_tv = tv;
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setText(java.lang.String)
	 */
	@Override
	public void setText(final String text) {
		if (_tv == null) {
			this.getLog().e(TAG, "setText(String) with a null TextView");
			return;
		}
		int c = convertText(text);
		if (c == 0) {
			_tv.setText(text);
		} else {
			_tv.setText(c);
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setText(int)
	 */
	@Override
	public void setText(final int id_txt) {
		if (_tv == null) {
			this.getLog().e(TAG, "setText(int) with a null TextView");
			return;
		}
		_tv.setText(id_txt);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		if (_tv == null) {
			this.getLog().e(TAG, "setEnabled(boolean) with a null TextView");
			return;
		}
		_tv.setEnabled(enabled);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setBackgroundColor(int)
	 */
	@Override
	public void setBackgroundColor(final int color) {
		if (_tv == null) {
			this.getLog().e(TAG, "setBackgroundColor(int) with a null TextView");
			return;
		}
		_tv.setBackgroundColor(color);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setTextColor(int)
	 */
	@Override
	public void setTextColor(final int color) {
		if (_tv == null) {
			this.getLog().e(TAG, "setTextColor(int) with a null TextView");
			return;
		}
		_tv.setTextColor(color);
	}

}
