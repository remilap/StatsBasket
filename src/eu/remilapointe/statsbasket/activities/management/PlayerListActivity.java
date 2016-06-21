/**
 * 
 */
package eu.remilapointe.statsbasket.activities.management;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import eu.remilapointe.statsbasket.R;
import eu.remilapointe.statsbasket.activities.main.MainActivity;
import eu.remilapointe.statsbasket.db.PlayerDB;
import eu.remilapointe.statsbasket.sport.Player;

/**
 * @author lapoint1
 *
 */
public class PlayerListActivity extends Activity implements OnItemClickListener, OnItemLongClickListener, DialogReturnable {

	private static final String TAG = PlayerListActivity.class.getSimpleName();
	private ListView _listPlayer = null;
	private ContentResolver _cr = null;
	private SimpleCursorAdapter _adapter = null;
	private PlayerListActivity _myActiv = null;
	private Cursor _cursor = null;
	private long _delId = 0;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.player_list);
		_myActiv = this;
		int black = getResources().getColor(R.color.black);

		// buttons management
		OnClickListener cl = new AddButtonsListener(TAG, this);
		( (Button) findViewById(R.id.bt_add_club) ).setOnClickListener(cl);
		( (Button) findViewById(R.id.bt_add_team) ).setOnClickListener(cl);
		( (Button) findViewById(R.id.bt_add_player) ).setOnClickListener(cl);
		( (Button) findViewById(R.id.bt_add_match) ).setOnClickListener(cl);

		// list management
		this._listPlayer = (ListView) findViewById(R.id.list_players);
		this._listPlayer.setOnItemClickListener(this);
		this._listPlayer.setOnItemLongClickListener(this);

		View header = getLayoutInflater().inflate(R.layout.player_list_item, null);
		header.setClickable(false);
		header.setEnabled(false);
		header.setBackgroundColor(getResources().getColor(R.color.orange));
		((TextView) header.findViewById(R.id.player_name)).setTextColor(black);
		((TextView) header.findViewById(R.id.player_firstname)).setTextColor(black);
		((TextView) header.findViewById(R.id.player_bib_number)).setTextColor(black);
		((TextView) header.findViewById(R.id.player_license)).setTextColor(black);
		this._listPlayer.addHeaderView(header);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
    	Log.d(TAG, "onStart");
		super.onStart();

		this._cr = this.getContentResolver();
		String[] projection = new String[] {
				Player.KEY_ROWID,
				Player.KEY_PLAYER_NAME,
				Player.KEY_PLAYER_FIRSTNAME,
				Player.KEY_PLAYER_BIB_NUMBER,
				Player.KEY_PLAYER_LICENSE};

		String order = Player.KEY_PLAYER_BIB_NUMBER + " ASC";
		this._cursor = this._cr.query(PlayerDB.getInstance().getUri(), projection, null, null, order);

		this._adapter = new SimpleCursorAdapter(this,
				R.layout.player_list_item,
				this._cursor,
				projection,
				new int[] {R.id.player_id, R.id.player_name, R.id.player_firstname, R.id.player_bib_number, R.id.player_license});
		this._listPlayer.setAdapter(_adapter);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
    	Log.d(TAG, "onStop");
		super.onStop();

		if (this._cursor != null) {
			this._cursor.close();
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "onItemClick on position "+position+" and id="+id);
		if (position == 0) return;
		Intent intentItemClick = new Intent(getApplicationContext(), PlayerItemActivity.class);
//		TextView player_id = (TextView) findViewById(R.id.player_id);
//		String the_id = player_id.getText().toString();
//		Log.d(TAG, "onItemClick with id="+the_id);
		intentItemClick.putExtra(Player.KEY_ROWID, id);
		this.startActivity(intentItemClick);
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "onItemLongClick on position "+position+" and id="+id);
		if (position == 0) return true;
		this._delId = id;
        MainActivity.askConfirm(MainActivity.REQ_CONFIRM_QUIT, _myActiv, _myActiv, getString(R.string.delete_confirm_st), getString(R.string.delete_st));
//		MainActivity.deleteConfirm(_myActiv, this);
		return true;
	}

	/* (non-Javadoc)
	 * @see eu.remilapointe.statsbasket.activities.management.Confirmable#resultConfirm(boolean)
	 */
	@Override
	public void dialogReturn(int reqId, int res, DialogInterface dialInt, int id) {
		String msg = String.format(getString(R.string.deleted_no_record), getString(R.string.txt_player_title));
		if (res == RESULT_OK) {
			try {
				int nbDel = this.getContentResolver().delete(PlayerDB.getInstance().getUri(), Player.KEY_ROWID+" = ?", new String[] {String.valueOf(this._delId)});
				if (nbDel > 0) {
					Log.d(TAG, "Number of deleted record(s): " + nbDel);
					msg = String.format(getString(R.string.deleted_records), Integer.valueOf(nbDel), getString(R.string.txt_player_title));
					Toast.makeText(_myActiv, msg, Toast.LENGTH_SHORT).show();
				} else {
					Log.e(TAG, "No record deleted");
					Toast.makeText(_myActiv, msg, Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				Log.e(TAG, "No record deleted\n" + e.getLocalizedMessage());
				Toast.makeText(_myActiv, msg, Toast.LENGTH_SHORT).show();
			}
		} else {
			Log.d(TAG, "Deletion canceled");
			Toast.makeText(_myActiv, R.string.deleted_canceled, Toast.LENGTH_SHORT).show();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}
