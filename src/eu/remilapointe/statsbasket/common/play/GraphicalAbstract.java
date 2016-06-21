/**
 * 
 */
package eu.remilapointe.statsbasket.common.play;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lapoint1
 *
 */
/**
 * @author lapoint1
 *
 */
public abstract class GraphicalAbstract extends EventAbstract implements GraphicalTextable, CommonEventDispatchable, Eventable {

	protected static String TAG = GraphicalAbstract.class.getSimpleName();

	private Map<String, Integer> _possible_texts = new HashMap<String, Integer>();


	/**
	 * Constructor
	 */
	public GraphicalAbstract() {
		super(null);
		this.getLog().d(TAG, "GraphicalAbstract simple constructor");
	}


	/**
	 * addPossibleText
	 * @param key
	 * @param txt
	 */
	public void addPossibleText(final String key, final int txt) {
		_possible_texts.put(key, Integer.valueOf(txt));
	}

	/**
	 * convertText
	 * @param text
	 * @return
	 */
	public int convertText(final String text) {
		if (_possible_texts.containsKey(text)) {
			int txt = _possible_texts.get(text).intValue();
			this.getLog().d(TAG, "String '" + text + "' found in possibles => " + txt);
			return txt;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.teammanagement.activities.common.EventDispatchable#onEvent(int, java.lang.String)
	 */
	@Override
	public void onEvent(final int event, final String msg) {
		this.getLog().d(TAG, "onEvent(" + event + ", " + msg + ")");
	}

}
