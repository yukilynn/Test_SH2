package sh.app2.zsalesorder_v2_srv.v1;

/*
 Auto-Generated by SAP NetWeaver Gateway Productivity Accelerator, Version 1.1.1
*/

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderHeaderIn;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderItemsIn;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderPartners;
import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderScheduleIn;
import android.content.res.Resources.NotFoundException;

import com.sap.gwpa.proxy.ODataQuery;
import com.sap.mobile.lib.configuration.IPreferences;
import com.sap.mobile.lib.configuration.Preferences;
import com.sap.mobile.lib.configuration.PreferencesException;
import com.sap.mobile.lib.parser.IODataEntry;
import com.sap.mobile.lib.parser.IODataSchema;
import com.sap.mobile.lib.parser.IODataServiceDocument;
import com.sap.mobile.lib.parser.IParser;
import com.sap.mobile.lib.parser.Parser;
import com.sap.mobile.lib.parser.ParserException;
import com.sap.mobile.lib.supportability.ILogger;
import com.sap.mobile.lib.supportability.Logger;


/**
 * ZSALESORDER_V2_SRVService Proxy Class 
 * <br>Sales Order Version 2
 * <br>Service Version: v1. */
public class ZSALESORDER_V2_SRVService
{
    private String baseUrl;
	
    private ILogger logger;
    private IPreferences preferences;
    private IParser parser;

    private IODataServiceDocument serviceDocument;
    private IODataSchema schema;
   
    /**
     * Constructs a new service (proxy) class, with the given parameters.
     * @param context - application context.
     * @param aServiceDocument - service document.
     * @param aServiceMetadata - service metadata.
     */
   	public ZSALESORDER_V2_SRVService(android.content.Context context, String aServiceDocument, String aServiceMetadata)
   	{
    	initISDMParameters(context);
    	parseServiceDocumentAndMetadata(context, aServiceDocument, aServiceMetadata);
   	}
   	
    /**
     * Constructs a new service (proxy) class, with the given parameter.
     * @param context - application context.
     */
    public ZSALESORDER_V2_SRVService(android.content.Context context)
    {
    	this(context, null, null);
    }
	
    /*
     * Parses the service document and metadata.
     */
    private void parseServiceDocumentAndMetadata(android.content.Context context, String aServiceDocument, String aServiceMetadata)
    {
    	try 
    	{
			if (aServiceDocument == null || aServiceMetadata == null)
			{
				serviceDocument = parser.parseODataServiceDocument(context.getResources().openRawResource(sh.app2.R.raw.zsalesorder_v2_srvv1document));
				schema = parser.parseODataSchema(context.getResources().openRawResource(sh.app2.R.raw.zsalesorder_v2_srvv1metadata),serviceDocument);
			}
			else
			{
				serviceDocument = parser.parseODataServiceDocument(aServiceDocument);
				schema = parser.parseODataSchema(aServiceMetadata,serviceDocument);
			}	
            			
			setUrl(serviceDocument.getBaseUrl());
			loadLabels();
        }
 		catch (NotFoundException e)
		{
			throw new ExceptionInInitializerError(e);
		} 
		catch (IllegalArgumentException e) 
		{
			throw new ExceptionInInitializerError(e);
		} 
		catch (ParserException e) 
		{
			throw new ExceptionInInitializerError(e);
		}	
    }
    
    private void initISDMParameters(android.content.Context context)
    {
    	logger = new Logger();
		preferences = new Preferences(context, logger);
		
		try 
		{
			preferences.setBooleanPreference(IPreferences.PERSISTENCE_SECUREMODE, false);
			parser = new Parser(preferences, logger);
		} 
		catch (PreferencesException e) 
		{
			throw new ExceptionInInitializerError(e);
		}
		catch (ParserException e) 
		{
			throw new ExceptionInInitializerError(e);
		}
    }
	
    /**
     * Returns the parser of this proxy class.
     * @return IParser.
     */
	public IParser getParser() 
	{
		return this.parser;
	}

	/**
	 * Returns the service document.
	 * @return - IODataServiceDocument.
	 */
	public IODataServiceDocument getServiceDocument() 
	{
		return this.serviceDocument;
	}

	/**
	 * Returns the schema of the service.
	 * @return - IODataSchema.
	 */
	public IODataSchema getSchema() 
	{
		return this.schema;
	}

    /**
     * Returns the base URL of the service.
     * @return - the base URL of the service.
     */
    public String getBaseUrl() 
	{
		return this.baseUrl;
    }
    
    /**
     * Sets the base URL of the service.
     * @param url - the url to set.
     */
    public void setUrl(String url) 
	{
		if (url.endsWith("/"))
    	{
    		baseUrl = url;
    	}
    	else
    	{
    		baseUrl = url + "/";
    	}
    }
	
	/**
     * Loads the labels for each entity type.
     * This method is called by the class initialisers.
     */
    public void loadLabels()
	{
		OrderHeaderIn.loadLabels(this.serviceDocument);
		OrderPartners.loadLabels(this.serviceDocument);
		OrderItemsIn.loadLabels(this.serviceDocument);
		OrderScheduleIn.loadLabels(this.serviceDocument);
	}
	
// service collections

    /**
     * @return - the OrderPartnersSetQuery
     * @throws - MalformedURLException
     */
    public ODataQuery getOrderPartnersSetQuery() throws MalformedURLException
    {
		return new ODataQuery(getBaseUrl() + "OrderPartnersSet");
    }

    /**
     * @return - the OrderItemsInSetQuery
     * @throws - MalformedURLException
     */
    public ODataQuery getOrderItemsInSetQuery() throws MalformedURLException
    {
		return new ODataQuery(getBaseUrl() + "OrderItemsInSet");
    }

    /**
     * @return - the OrderScheduleInSetQuery
     * @throws - MalformedURLException
     */
    public ODataQuery getOrderScheduleInSetQuery() throws MalformedURLException
    {
		return new ODataQuery(getBaseUrl() + "OrderScheduleInSet");
    }

    /**
     * @return - the OrderHeaderInSetQuery
     * @throws - MalformedURLException
     */
    public ODataQuery getOrderHeaderInSetQuery() throws MalformedURLException
    {
		return new ODataQuery(getBaseUrl() + "OrderHeaderInSet");
    }
	

   /**
    * OrderPartnersSetQuery with Key
    *
    * @throws MalformedURLException 
    */
    public ODataQuery getOrderPartnersSetEntryQuery( String PartnRole)  throws MalformedURLException, UnsupportedEncodingException
    {
		PartnRole = URLEncoder.encode(PartnRole, "UTF-8"); 

		return new ODataQuery(getBaseUrl() + "OrderPartnersSet("+"PartnRole='"+PartnRole+"')");
    }

   /**
    * OrderItemsInSetQuery with Key
    *
    * @throws MalformedURLException 
    */
    public ODataQuery getOrderItemsInSetEntryQuery( String ItmNumber)  throws MalformedURLException, UnsupportedEncodingException
    {
		ItmNumber = URLEncoder.encode(ItmNumber, "UTF-8"); 

		return new ODataQuery(getBaseUrl() + "OrderItemsInSet("+"ItmNumber='"+ItmNumber+"')");
    }

   /**
    * OrderScheduleInSetQuery with Key
    *
    * @throws MalformedURLException 
    */
    public ODataQuery getOrderScheduleInSetEntryQuery( String ItmNumber)  throws MalformedURLException, UnsupportedEncodingException
    {
		ItmNumber = URLEncoder.encode(ItmNumber, "UTF-8"); 

		return new ODataQuery(getBaseUrl() + "OrderScheduleInSet("+"ItmNumber='"+ItmNumber+"')");
    }

   /**
    * OrderHeaderInSetQuery with Key
    *
    * @throws MalformedURLException 
    */
    public ODataQuery getOrderHeaderInSetEntryQuery( String DocType)  throws MalformedURLException, UnsupportedEncodingException
    {
		DocType = URLEncoder.encode(DocType, "UTF-8"); 

		return new ODataQuery(getBaseUrl() + "OrderHeaderInSet("+"DocType='"+DocType+"')");
    }
	 
	
	/**
	 * @return - the OrderPartnersSet
	 * @throws - ParserException
	 * @throws - MalformedURLException 
	 */
	public List<OrderPartners> getOrderPartnersSet(String data) throws ParserException, MalformedURLException
	{
		List<OrderPartners> returnList  = new LinkedList<OrderPartners>();
		List<IODataEntry> list  = parser.parseODataEntries(data, "OrderPartnersSet", schema);
		
		for (IODataEntry isdmoDataEntry : list) 
		{
			returnList.add(new OrderPartners(isdmoDataEntry, parser, schema));
		}
		return returnList;
	 }
	
	/**
	 * @return - the OrderItemsInSet
	 * @throws - ParserException
	 * @throws - MalformedURLException 
	 */
	public List<OrderItemsIn> getOrderItemsInSet(String data) throws ParserException, MalformedURLException
	{
		List<OrderItemsIn> returnList  = new LinkedList<OrderItemsIn>();
		List<IODataEntry> list  = parser.parseODataEntries(data, "OrderItemsInSet", schema);
		
		for (IODataEntry isdmoDataEntry : list) 
		{
			returnList.add(new OrderItemsIn(isdmoDataEntry, parser, schema));
		}
		return returnList;
	 }
	
	/**
	 * @return - the OrderScheduleInSet
	 * @throws - ParserException
	 * @throws - MalformedURLException 
	 */
	public List<OrderScheduleIn> getOrderScheduleInSet(String data) throws ParserException, MalformedURLException
	{
		List<OrderScheduleIn> returnList  = new LinkedList<OrderScheduleIn>();
		List<IODataEntry> list  = parser.parseODataEntries(data, "OrderScheduleInSet", schema);
		
		for (IODataEntry isdmoDataEntry : list) 
		{
			returnList.add(new OrderScheduleIn(isdmoDataEntry, parser, schema));
		}
		return returnList;
	 }
	
	/**
	 * @return - the OrderHeaderInSet
	 * @throws - ParserException
	 * @throws - MalformedURLException 
	 */
	public List<OrderHeaderIn> getOrderHeaderInSet(String data) throws ParserException, MalformedURLException
	{
		List<OrderHeaderIn> returnList  = new LinkedList<OrderHeaderIn>();
		List<IODataEntry> list  = parser.parseODataEntries(data, "OrderHeaderInSet", schema);
		
		for (IODataEntry isdmoDataEntry : list) 
		{
			returnList.add(new OrderHeaderIn(isdmoDataEntry, parser, schema));
		}
		return returnList;
	 }

		
		
	/**
	 * @return - OrderPartners when sending the key of OrderPartners
	 * @throws - ParserException
	 * @throws MalformedURLException
	 */
	public OrderPartners getOrderPartnersSetEntry(String data) throws ParserException, MalformedURLException
	{
		List<IODataEntry> list = parser.parseODataEntries(data, "OrderPartnersSet", schema);
		return new OrderPartners(list.get(0), parser, schema);
	}
		
		
	/**
	 * @return - OrderItemsIn when sending the key of OrderItemsIn
	 * @throws - ParserException
	 * @throws MalformedURLException
	 */
	public OrderItemsIn getOrderItemsInSetEntry(String data) throws ParserException, MalformedURLException
	{
		List<IODataEntry> list = parser.parseODataEntries(data, "OrderItemsInSet", schema);
		return new OrderItemsIn(list.get(0), parser, schema);
	}
		
		
	/**
	 * @return - OrderScheduleIn when sending the key of OrderScheduleIn
	 * @throws - ParserException
	 * @throws MalformedURLException
	 */
	public OrderScheduleIn getOrderScheduleInSetEntry(String data) throws ParserException, MalformedURLException
	{
		List<IODataEntry> list = parser.parseODataEntries(data, "OrderScheduleInSet", schema);
		return new OrderScheduleIn(list.get(0), parser, schema);
	}
		
		
	/**
	 * @return - OrderHeaderIn when sending the key of OrderHeaderIn
	 * @throws - ParserException
	 * @throws MalformedURLException
	 */
	public OrderHeaderIn getOrderHeaderInSetEntry(String data) throws ParserException, MalformedURLException
	{
		List<IODataEntry> list = parser.parseODataEntries(data, "OrderHeaderInSet", schema);
		return new OrderHeaderIn(list.get(0), parser, schema);
	}

} 