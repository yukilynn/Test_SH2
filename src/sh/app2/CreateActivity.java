package sh.app2;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import com.sap.gwpa.proxy.RequestStatus;
import com.sap.mobile.lib.parser.ParserException;
import com.sap.mobile.lib.request.IResponse;

import sh.app2.zsalesorder_v2_srv.v1.ZSALESORDER_V2_SRVRequestHandler;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderHeaderIn;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderItemsIn;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderPartners;
import sh.app2.zsalesorder_v2_srv.v1.helpers.IZSALESORDER_V2_SRVRequestHandlerListener;
import sh.app2.zsalesorder_v2_srv.v1.helpers.ZSALESORDER_V2_SRVRequestID;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends Activity implements IZSALESORDER_V2_SRVRequestHandlerListener {
	
	private String strDocType;
	private String strSalesOrg;
	private String strDistrib;
	private String strDivision;
	private Button button;
	private EditText textDocType;
	private EditText textSalesOrg;
	private EditText textDistrib;
	private EditText textDivision;
	private String testing;
	
	protected static OrderHeaderIn parentEntry;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_entry);
		
		fieldAssign();

		ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).register(this,
				ZSALESORDER_V2_SRVRequestID.CREATE_ORDERHEADERINSET_ENTRY);
	}
	
	public void fieldAssign() {
		button = (Button) findViewById(R.id.submitBtn);
		button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitForm(v);
				
			}
		});
		
		textDocType = (EditText) findViewById(R.id.newDocTypeField);
		textSalesOrg = (EditText) findViewById(R.id.newSalesOrgField);
		textDistrib = (EditText) findViewById(R.id.newDistribField);
		textDivision = (EditText) findViewById(R.id.newDivisionField);
	}
	
	public Boolean emptyFields() {
		strDocType = textDocType.getText().toString();
		strSalesOrg = textSalesOrg.getText().toString();
		strDistrib = textDistrib.getText().toString();
		strDivision = textDivision.getText().toString();
		
		if	((strDocType == "") && (strSalesOrg == "") && (strDistrib == "") && (strDivision == "")) {
	    	return true;
		} else {
			return false;
		}
	}
	
	public void submitForm(View v)  {
		boolean fields = emptyFields();
		if	(fields == false) {
			OrderPartners newOP = new OrderPartners("RE");
			newOP.setPartnRole("RE");
			newOP.setPartnNumb("0000001000");
			
			testing = newOP.getStringPayload();
			Log.i("First Partner", testing);
			
			OrderPartners newOP2 = new OrderPartners("AG");
			newOP2.setPartnRole("AG");
			newOP2.setPartnNumb("0000001000");	
			
			testing = newOP2.getStringPayload();
			Log.i("Second Partner", testing);
			
			List<OrderPartners> header_partner_nav = new LinkedList<OrderPartners>();
			header_partner_nav.add(newOP);
			header_partner_nav.add(newOP2);
			
			OrderHeaderIn newOrderHeaderIn = new OrderHeaderIn(strDocType);
			newOrderHeaderIn.setDocType(strDocType);
			newOrderHeaderIn.setSalesOrg(strSalesOrg);
			newOrderHeaderIn.setDistrChan(strDistrib);
			newOrderHeaderIn.setDivision(strDivision);
			
//			testing = newOrderHeaderIn.getStringPayload();
//			Log.e("Header Only", testing);
			
//			OrderItemsIn newItems = new OrderItemsIn("000010");
//			newItems.setItmNumber("000010");
//			newItems.setMaterial("000000000000002000");
//			newItems.setPlant("0005");
			
//			List<OrderItemsIn> item_partner_nav = new LinkedList<OrderItemsIn>();
//			item_partner_nav.add(newItems);
			
			newOrderHeaderIn.setHeader_Partner_Nav(header_partner_nav);
//			newOrderHeaderIn.setHeader_Item_Nav(item_partner_nav);
			

			testing = newOrderHeaderIn.getStringPayload();
			Log.i("Final Header", testing);
			
	    	ZSALESORDER_V2_SRVRequestHandler.getInstance(getApplicationContext()).createOrderHeaderInSetEntry(newOrderHeaderIn);
	    	
		} else {
			Toast.makeText(getApplicationContext(), "Please enter the necessary fields", Toast.LENGTH_SHORT).show();
		}
	}

	public void requestCompleted(ZSALESORDER_V2_SRVRequestID requestID,
			List<?> entries, RequestStatus requestStatus) {
		// TODO Auto-generated method stub
		
	}

	public void batchCompleted(String batchID, IResponse response,
			RequestStatus requestStatus) {
		// TODO Auto-generated method stub
		
	}

	public void authenticationNeeded(String message) {
		// TODO Auto-generated method stub
		
	}

}
