package sh.app2.zsalesorder_v2_srv.v1.entitytypes;
/*

 Auto-Generated by SAP NetWeaver Gateway Productivity Accelerator, Version 1.1.1

*/
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.gwpa.proxy.BaseEntityType;
import com.sap.gwpa.proxy.ODataQuery;
import com.sap.gwpa.proxy.TypeConverter;
import com.sap.mobile.lib.parser.IODataEntry;
import com.sap.mobile.lib.parser.IODataProperty;
import com.sap.mobile.lib.parser.IODataSchema;
import com.sap.mobile.lib.parser.IODataServiceDocument;
import com.sap.mobile.lib.parser.IParser;
import com.sap.mobile.lib.parser.ODataEntry;

/**
 * OrderPartners Entity Type 
 *
 * <br>key (PartnRole)
 */
public class OrderPartners extends BaseEntityType 
{
	// OrderPartners properties
    private String PartnNumb;
    private String PartnRole;

	// reference to the parser
	private IParser parser;
	// reference to the schema
	private IODataSchema schema;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


	private static Map<String, String> orderpartnersLabels;

	private static Map<String, String> orderpartnersTypes;	
	
	/**
	 * Constructor
	 * @param entry
	 * @throws MalformedURLException
	 */
	public OrderPartners(final IODataEntry entry, IParser parser, IODataSchema schema) throws MalformedURLException
	{
		super(entry);
		this.PartnNumb =  TypeConverter.getAsString(entry.getPropertyValue("PartnNumb"));
		this.PartnRole =  TypeConverter.getAsString(entry.getPropertyValue("PartnRole"));
	    this.parser = parser;
        this.schema = schema;
 	}
	
	/**
	 * OrderPartners Constructor</br>
	 * Dummy values may apply
	 *
	 */
	public OrderPartners( String PartnRole) 
	{
		super(new ODataEntry());
		
        this.setPartnRole(PartnRole);
	}



	// OrderPartners properties getters and setters
		
	/**
	 * @return - String Customer
	 */
	public String getPartnNumb()
	{
		return this.PartnNumb;
	}
	
	/**
	 * @param PartnNumb - Customer
	 */
	public void setPartnNumb(String PartnNumb)
	{
		this.PartnNumb = PartnNumb;
		
		getEntry().putPropertyValue("PartnNumb", PartnNumb);
	}
		
	/**
	 * @return - String Partner Function
	 */
	public String getPartnRole()
	{
		return this.PartnRole;
	}
	
	/**
	 * @param PartnRole - Partner Function
	 */
	public void setPartnRole(String PartnRole)
	{
		this.PartnRole = PartnRole;
		
		getEntry().putPropertyValue("PartnRole", PartnRole);
	}
	
	/**
	 * @return - representation of the Entity Type object in OData4SAP format
	 */
	public String getStringPayload()  
	{
		String xml = null;
		
		xml = getEntry().toXMLString();
			
		return xml;
	}
	
	/**
	 * @return - self ODataQuery object
	 * @throws MalformedURLException 
	 */
	public ODataQuery getEntityQuery() throws MalformedURLException  
	{
		return new ODataQuery(getEntry().getSelfLink().getUrl());
	}
	
	/**
	 * @return - the date format.
	 */
	public DateFormat getDateFormat()
	{
		return this.dateFormat;
	}


	/**
    * Static method that loads all of the entity type property labels. 
    * This method is called when the service class is initialized.
    * @param service Service document object containing all of the entity type properties.
   	*/	
    public static void loadLabels(IODataServiceDocument service)
    {
    	List<IODataProperty> properties = getSchemaPropertiesFromCollection(service, "OrderPartnersSet" );
        
    	orderpartnersLabels = new HashMap<String, String>();
    	orderpartnersTypes = new HashMap<String, String>();
    	
    	if (properties != null)
    	{
	        for (IODataProperty property : properties) 
	        {
	        	orderpartnersLabels.put(property.getName(), property.getLabel());
	        	orderpartnersTypes.put(property.getName(), property.getType());
			}
    	}
    }
    
    
    /**
    * Static method that returns the type for a given property name.
    * @param propertyName Property name.
    * @return Property label.
   	*/
    public static String getTypeForProperty(String propertyName)
    {
        return getLabelFromDictionary(orderpartnersTypes, propertyName);
    }
    
    
    /**
    * Static method that returns the label for a given property name.
    * @param propertyName Property name.
    * @return Property label.
   	*/
    public static String getLabelForProperty(String propertyName)
    {
        return getLabelFromDictionary(orderpartnersLabels, propertyName);
    }
}