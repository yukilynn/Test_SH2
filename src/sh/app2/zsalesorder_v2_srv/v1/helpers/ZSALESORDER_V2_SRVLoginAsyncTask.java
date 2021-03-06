package sh.app2.zsalesorder_v2_srv.v1.helpers;
/*
 Auto-Generated by SAP NetWeaver Gateway Productivity Accelerator, Version 1.1.1
*/

import sh.app2.zsalesorder_v2_srv.v1.ZSALESORDER_V2_SRVRequestHandler;
import android.content.Context;
import android.os.AsyncTask;

import com.sap.gwpa.proxy.ILoginAsyncTask;
import com.sap.gwpa.proxy.ServiceInitializationException;
import com.sap.gwpa.proxy.connectivity.SUPHelperException;
import com.sap.mobile.lib.supportability.ILogger;
import com.sap.mobile.lib.supportability.Logger;

/**
 * An AsyncTask for performing login to the application. 
 */
public class ZSALESORDER_V2_SRVLoginAsyncTask extends AsyncTask<Void, Void, Boolean> 
{
	private ILoginAsyncTask activity;
	private String userName;
	private String password;
	private String vaultPassword;
	private String vaultSalt;
	private Context context;
	private Logger logger;
	private static final String TAG = "ZSALESORDER_V2_SRVLoginAsyncTask";
	
	/**
	 * Constructs a new LoginAsyncTask with the given parameters.
	 * @param activity - the calling activity.
	 * @param context - application context.
	 * @param userName - user name.
	 * @param password - password.
	 * @param vaultPassword - vault password.
	 * @param vaultSalt - vault salt.
	 */
	public ZSALESORDER_V2_SRVLoginAsyncTask(ILoginAsyncTask activity, Context context, String userName, String password, String vaultPassword, String vaultSalt) 
	{
		this.activity = activity;
		this.userName = userName;
		this.password = password;
		this.vaultPassword = vaultPassword;
		this.vaultSalt = vaultSalt;
		this.context = context;
		
		this.logger = new Logger();
        // set the log output to Android
        this.logger.logToAndroid(true);
        // set the minimum log level stored in logger
        this.logger.setLogLevel(ILogger.INFO);
	}
	
	@Override
	protected Boolean doInBackground(Void... arg0) 
	{
		Boolean result = false;
		try
        {
			result = ZSALESORDER_V2_SRVRequestHandler.getInstance(context).executeLoginUsernamePassword(userName, password, vaultPassword, vaultSalt);
        } 
        catch (ServiceInitializationException e)
        {
        	this.logger.e(TAG, e.getMessage(), e, "doInBackground");
        }
        catch (SUPHelperException e) 
        {
			this.logger.e(TAG, e.getMessage(), e, "doInBackground");
		}
			 
		return result;
	}
	
	@Override
	protected void onPostExecute(Boolean result) 
	{
		this.activity.onLoginResult(result);
	}
}
	