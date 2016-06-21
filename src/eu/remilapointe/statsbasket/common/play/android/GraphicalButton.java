/**
 * 
 */
package eu.remilapointe.statsbasket.common.play.android;

import android.widget.Button;
import eu.remilapointe.statsbasket.common.play.Eventable;
import eu.remilapointe.statsbasket.common.play.GraphicalAbstract;
import eu.remilapointe.statsbasket.common.play.GraphicalUpdatable;
import eu.remilapointe.statsbasket.sport.Loggable;

/**
 * @author lapoint1
 *
 */
public class GraphicalButton extends GraphicalAbstract implements GraphicalUpdatable, Eventable {

	protected static String TAG = GraphicalButton.class.getSimpleName();

	private Button _bt = null;

	/**
	 * Constructor
	 */
	public GraphicalButton() {
		super();
		_log.d(TAG, "Constructor simple");
	}

	/**
	 * Constructor
	 * @param bt
	 */
	public GraphicalButton(Button bt) {
		this();
		_bt = bt;
		_log.d(TAG, "Constructor with Button");
	}

	/**
	 * Constructor
	 * @param bt
	 * @param log
	 */
	public GraphicalButton(Button bt, String name, Loggable log) {
		this(bt);
		setName(name);
		setLog(log);
		_log.d(TAG, "Constructor with Button and log");
	}


	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		int c = convertText(text);
		if (c == 0) {
			_bt.setText(text);
		} else {
			_bt.setText(c);
		}
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setText(int)
	 */
	@Override
	public void setText(int id_txt) {
		_bt.setText(id_txt);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		_bt.setEnabled(enabled);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setBackgroundColor(int)
	 */
	@Override
	public void setBackgroundColor(int color) {
		_bt.setBackgroundColor(color);
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.common.play.GraphicalUpdatable#setTextColor(int)
	 */
	@Override
	public void setTextColor(int color) {
		_bt.setTextColor(color);
	}

}
