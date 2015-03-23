package sh.app2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sap.mobile.lib.supportability.ILogger;
import com.sap.mobile.lib.supportability.Logger;

import com.sap.gwpa.proxy.ILoginAsyncTask;
import com.sap.gwpa.proxy.connectivity.ConnectivitySettings;
import com.sap.gwpa.proxy.connectivity.DataVaultHelper;
import com.sap.gwpa.proxy.connectivity.SUPHelper;
import com.sap.gwpa.proxy.connectivity.SUPHelperException;

import sh.app2.preferences.MainPreferencesActivity;
import sh.app2.preferences.PreferencesUtilities;
import sh.app2.zsalesorder_v2_srv.v1.ZSALESORDER_V2_SRVRequestHandler;
import sh.app2.zsalesorder_v2_srv.v1.helpers.ZSALESORDER_V2_SRVLoginAsyncTask;

/**
 * Login screen
 */
public class LoginActivity extends Activity implements ILoginAsyncTask
{
	private static final int PREFERENCE_ACTIVITY_REQUEST_CODE = 1;
	
	public static final String TAG = "Test_SH2";
	private ILogger logger;
	
	/* ****** TODO: Make sure to set these values as desired (use null for default values), or get them from the user. ****** */
    private String vaultPassword = "<vault password value>"; 
    private String vaultSalt = "<salt value>";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(sh.app2.R.layout.login);

		// initialize the Logger
		logger = new Logger();
		
		// load application preferences
		PreferencesUtilities prefUtils = PreferencesUtilities.getInstance(getApplicationContext());		
		ConnectivitySettings connectivitySettings = ConnectivitySettings.getInstance();
		prefUtils.updateConnectivitySettingsFromPreferences(connectivitySettings);
		
		// initialize the request handler
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).setServiceDocumentURL(prefUtils.getServiceURL());
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).setSAPClient(prefUtils.getServiceClient());
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).setUseServiceNegotiation(true);
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).setUseLocalMetadata(false);
		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).setUseJson(false);
		
		try 
		{
			// check if the user's device is already registered
			if (isCredentialsSaved(vaultPassword, vaultSalt)) 
			{
				showProgressBar();
				new ZSALESORDER_V2_SRVLoginAsyncTask(LoginActivity.this, getApplicationContext(), null, null, vaultPassword, vaultSalt).execute();
			}
		} 
		catch (SUPHelperException e) 
		{
			logger.e(TAG, e.getMessage());
		}
		
		Button button = (Button) findViewById(sh.app2.R.id.btnLogin);
		button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				EditText username = (EditText) findViewById(sh.app2.R.id.username);
				String userString = username.getText().toString();
				EditText password = (EditText) findViewById(sh.app2.R.id.password);
				String passwordString = password.getText().toString();
				
				new ZSALESORDER_V2_SRVLoginAsyncTask(LoginActivity.this, getApplicationContext(), userString, passwordString, vaultPassword, vaultSalt).execute();
				
				showProgressBar();
			}
		});
	}
	
	private boolean isCredentialsSaved(String vaultPassword, String vaultSalt) throws SUPHelperException 
	{
		ConnectivitySettings connectivitySettings = ConnectivitySettings.getInstance();
		DataVaultHelper dataVaultHelper = DataVaultHelper.getInstance(getApplicationContext());
		
		if (connectivitySettings.isSUPMode())
		{
			return (SUPHelper.isUserRegistered() && dataVaultHelper.isCredentialsSaved(vaultPassword, vaultSalt));
		}
		else
		{
			return (dataVaultHelper.isCredentialsSaved(vaultPassword, vaultSalt));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(sh.app2.R.menu.main_menu, menu);
		MenuItem logoutMenuItem = menu.findItem(R.id.menu_item_logout);
		logoutMenuItem.setVisible(false);
		
		return true;		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case sh.app2.R.id.menu_item_settings:
				Intent intent = new Intent(LoginActivity.this, MainPreferencesActivity.class);
				startActivityForResult(intent, PREFERENCE_ACTIVITY_REQUEST_CODE);
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
					Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 			
					startActivity(intent);
				}       
			}
		}
	}
	
	public void onLoginResult(Boolean loginResult) 
	{
    	if (loginResult)
    	{
  			// navigation to first screen
  			Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
  			startActivity(intent);
  			finish();
      	}
      	else
      	{
      		Toast.makeText(getApplicationContext(), sh.app2.R.string.login_has_failed, Toast.LENGTH_LONG).show();
      		hideProgressBar();
      	}
	}
	
	
	/**
	 * Hides the progress bar and displays the form view
	 */
	private void hideProgressBar() 
	{
		View formView = findViewById(sh.app2.R.id.form);
		formView.setVisibility(View.VISIBLE);
		
		View loadingView = findViewById(sh.app2.R.id.loading_view);
		loadingView.setVisibility(View.GONE);
	}

	/**
	 * Hides the form view and displays the progress bar
	 */
	private void showProgressBar() 
	{
		View formView = findViewById(sh.app2.R.id.form);
		formView.setVisibility(View.GONE);
		
		View loadingView = findViewById(sh.app2.R.id.loading_view);
		loadingView.setVisibility(View.VISIBLE);
	}
}
