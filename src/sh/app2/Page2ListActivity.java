package sh.app2;

import java.util.List;

import sh.app2.zsalesorder_v2_srv.v1.ZSALESORDER_V2_SRVRequestHandler;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderHeaderIn;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderItemsIn;
import sh.app2.zsalesorder_v2_srv.v1.helpers.IZSALESORDER_V2_SRVRequestHandlerListener;
import sh.app2.zsalesorder_v2_srv.v1.helpers.ZSALESORDER_V2_SRVRequestID;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sap.gwpa.proxy.RequestStatus;
import com.sap.gwpa.proxy.RequestStatus.StatusType;
import com.sap.mobile.lib.request.IResponse;
import com.sap.mobile.lib.supportability.ILogger;
import com.sap.mobile.lib.supportability.Logger;

/**
 * List screen.
 */
public class Page2ListActivity extends ListActivity implements
		IZSALESORDER_V2_SRVRequestHandlerListener
{
	public static final String TAG = "Test_SH2";
	private ILogger logger;
	protected static OrderHeaderIn parentEntry;
	
	// result of List Request
	private List<OrderItemsIn> entries;

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

	private Page2ListAdapter listAdapter;
	
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
		
		listAdapter = new Page2ListAdapter(this);
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
		private Page2ListAdapter adapter;

		public FilterTextWatcher(Page2ListAdapter adapter)
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
				ZSALESORDER_V2_SRVRequestID.LOAD_HEADER_ITEM_NAV_FOR_ORDERHEADERIN);

		// make the request
		// the response should be in "requestCompleted"
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).loadHeader_Item_NavForOrderHeaderIn((OrderHeaderIn) parentEntry);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent intent = new Intent(this, Page3DetailsActivity.class);
		OrderItemsIn item = (OrderItemsIn) listAdapter.getItem(position);
		
		Page3DetailsActivity.parentEntry = item;
		
		// navigation to next screen
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}

	@SuppressWarnings("unchecked")
	public void requestCompleted(ZSALESORDER_V2_SRVRequestID requestID, List<?> entries, RequestStatus requestStatus) 
	{
		// first check the request's status
		StatusType type = requestStatus.getType();

		if (type == StatusType.OK) 
		{
			if (requestID.equals(ZSALESORDER_V2_SRVRequestID.LOAD_HEADER_ITEM_NAV_FOR_ORDERHEADERIN))
			{
				// cast to the right type
				this.entries = (List<OrderItemsIn>) entries;
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
