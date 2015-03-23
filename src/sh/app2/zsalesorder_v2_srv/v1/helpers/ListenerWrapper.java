package sh.app2.zsalesorder_v2_srv.v1.helpers;
/*

 Auto-Generated by SAP NetWeaver Gateway Productivity Accelerator, Version 1.1.1

*/
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for the listeners of request handler.
 */
public class ListenerWrapper
{
    // the listener
    private IZSALESORDER_V2_SRVRequestHandlerListener listener;
    // the list of request IDs
    private List<ZSALESORDER_V2_SRVRequestID> requestIDs;
    
    /**
     * Constructs a new listener wrapper with the given listener
     * and request IDs.
     * 
     * @param listener - the request handler listener.
     * @param requestIDs - may be passed as an array or as a sequence of request IDs.
     */
    public ListenerWrapper(IZSALESORDER_V2_SRVRequestHandlerListener listener, ZSALESORDER_V2_SRVRequestID... requestIDs)
    {
        this.listener = listener;
        
        this.requestIDs = new ArrayList<ZSALESORDER_V2_SRVRequestID>();
        for (ZSALESORDER_V2_SRVRequestID requestID : requestIDs)
        {
            this.requestIDs.add(requestID);
        }
    }

    /**
     * Returns the listener.
     * @return the listener.
     */
    public IZSALESORDER_V2_SRVRequestHandlerListener getListener()
    {
        return listener;
    }

    /**
     * Returns the request IDs.
     * @return - the request IDs.
     */
    public List<ZSALESORDER_V2_SRVRequestID> getRequestIDs()
    {
        return requestIDs;
    }
}