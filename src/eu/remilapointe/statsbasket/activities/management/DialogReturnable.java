/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.content.DialogInterface;

/**
 * @author lapoint1
 *
 */
public interface DialogReturnable {

	public void dialogReturn(int reqId, int res, DialogInterface dialInt, int id);

}
