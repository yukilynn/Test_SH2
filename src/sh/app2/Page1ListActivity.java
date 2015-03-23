package sh.app2;

import java.util.List;

import sh.app2.preferences.MainPreferencesActivity;
import sh.app2.zsalesorder_v2_srv.v1.ZSALESORDER_V2_SRVRequestHandler;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderHeaderIn;
import sh.app2.zsalesorder_v2_srv.v1.helpers.IZSALESORDER_V2_SRVRequestHandlerListener;
import sh.app2.zsalesorder_v2_srv.v1.helpers.ZSALESORDER_V2_SRVRequestID;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sap.gwpa.proxy.RequestStatus;
import com.sap.gwpa.proxy.RequestStatus.StatusType;
import com.sap.gwpa.proxy.connectivity.authenticators.AuthenticatingException;
import com.sap.mobile.lib.request.IResponse;
import com.sap.mobile.lib.supportability.ILogger;
import com.sap.mobile.lib.supportability.Logger;

/**
 * List screen.
 */
public class Page1ListActivity extends ListActivity implements
		IZSALESORDER_V2_SRVRequestHandlerListener
{
	private static final int PREFERENCE_ACTIVITY_REQUEST_CODE = 1;
	public static final String TAG = "Test_SH2";
	private ILogger logger;
	
	// result of List Request
	private List<OrderHeaderIn> entries;

	// need handler for callbacks to the UI thread
	final Handler mHandler = new Handler(); 

	// create runnable for posting
	final Runnable mUpdateResults = new Runnable()
	{
		public void run()
		{
			updateResultsInUi();
		}
	};

	private Page1ListAdapter listAdapter;
	
	// connectivity error message
	private String emessage = "";

	/**
	 * Refresh UI from background thread
	 */
	protected void updateResultsInUi()
	{
		// Error occurred
		if (entries == null)
		{
			View loadingView = findViewById(sh.app2.R.id.loading_view);
			loadingView.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), emessage, Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		
		listAdapter = new Page1ListAdapter(this);
		listAdapter.setEntries(entries);

		setListAdapter(listAdapter);
		RelativeLayout loadingView = (RelativeLayout) findViewById(sh.app2.R.id.loading_view);
		
		// No results - empty set
		if (entries.size() == 0)
		{
			ProgressBar progressBar = (ProgressBar) findViewById(sh.app2.R.id.progressBar1);
			progressBar.setVisibility(View.GONE);
			
			TextView textView = new TextView(getApplicationContext());
			textView.setText(sh.app2.R.string.no_results);
			loadingView.addView(textView);
			return;
			
		}
		
		// No error and has results
		loadingView.setVisibility(View.GONE);
		
		// show search box
		View searchBoxView = findViewById(sh.app2.R.id.linearLayout1);
		searchBoxView.setVisibility(View.VISIBLE);

		EditText searchBox = (EditText) findViewById(sh.app2.R.id.search_box);
		FilterTextWatcher filterTextWatcher = new FilterTextWatcher(listAdapter);
		searchBox.addTextChangedListener(filterTextWatcher);
		searchBox.setVisibility(View.VISIBLE);
	}

	private class FilterTextWatcher implements TextWatcher
	{
		private Page1ListAdapter adapter;

		public FilterTextWatcher(Page1ListAdapter adapter)
		{
			this.adapter = adapter;
		}

		public void afterTextChanged(Editable s)
		{
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}

		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			adapter.filter(s);
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(sh.app2.R.layout.list);
		
		// initialize the Logger
		logger = new Logger();
		
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).register(this,
				ZSALESORDER_V2_SRVRequestID.LOAD_ORDERHEADERINSET);

		// make the request
		// the response should be in "requestCompleted"
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).loadOrderHeaderInSet();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(this, Page2ListActivity.class);
		OrderHeaderIn item = (OrderHeaderIn) listAdapter.getItem(position);
		
		Page2ListActivity.parentEntry = item;
		
		// navigation to next screen
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(sh.app2.R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case sh.app2.R.id.menu_item_settings:
				Intent intent = new Intent(Page1ListActivity.this,MainPreferencesActivity.class);
				startActivityForResult(intent, PREFERENCE_ACTIVITY_REQUEST_CODE);
				return true;
			case sh.app2.R.id.menu_item_logout:
				performLogout();
				return true;
			default:
				super.onOptionsItemSelected(item);
				return false;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == PREFERENCE_ACTIVITY_REQUEST_CODE) 
		{
			if (resultCode == RESULT_OK)
			{      
				if (data.getBooleanExtra(MainPreferencesActivity.PREFERENCES_CHANGED_FLAG_KEY, false))
				{
					// if Preferences state was change then clear the activities from cache and restart the login activity
					Intent intent = new Intent(Page1ListActivity.this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 			
					startActivity(intent);
					finish();
				}       
			}
		}
	}
	
	private void performLogout()
    {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				switch (which)
				{
					case DialogInterface.BUTTON_POSITIVE:
						//Yes button clicked
						(new AsyncTask<Void, Void, Boolean>()
					    {
					         @Override
					         protected Boolean doInBackground(Void... arg0) 
					         {
								try
								{
								       ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).logout();
								       return true;
								}
								catch (AuthenticatingException e)
								{
								       logger.e(TAG, "Logout Failed " + e.getLocalizedMessage());
								       return false;
								}
							 }
							 @Override
							 protected void onPostExecute(Boolean result)
							 {
								// In the first page use finish to quit the application
								finish();
								Process.killProcess(Process.myPid());
							 }
						}).execute();
					break;
				}
			}
		};
		 
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(sh.app2.R.string.logout_title).setMessage(sh.app2.R.string.logout_msg).setPositiveButton(sh.app2.R.string.yes, dialogClickListener)
			.setNegativeButton(sh.app2.R.string.no, dialogClickListener).show();
	}
	

	@SuppressWarnings("unchecked")
	public void requestCompleted(ZSALESORDER_V2_SRVRequestID requestID, List<?> entries, RequestStatus requestStatus) 
	{
		// first check the request's status
		StatusType type = requestStatus.getType();

		if (type == StatusType.OK) 
		{
			if (requestID.equals(ZSALESORDER_V2_SRVRequestID.LOAD_ORDERHEADERINSET))
			{
				// cast to the right type
				this.entries = (List<OrderHeaderIn>) entries;
				// post in the UI
				mHandler.post(mUpdateResults);
			}
		} 
		else 
		{
			// do some error handling
			logger.e(TAG, "The request has returned with an error");
			entries = null;
			emessage = requestStatus.getMessage();
			mHandler.post(mUpdateResults);
		}

	}

	public void authenticationNeeded(String message) 
	{
		logger.e(TAG, "Authentication is needed");
		entries = null;
		emessage = message;
		mHandler.post(mUpdateResults);
		// navigate back to login page
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void batchCompleted(String batchID, IResponse response,
			RequestStatus requestStatus)
	{
		// here you can handle the response of the batch request.
	} 		
}
