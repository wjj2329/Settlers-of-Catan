package client.model;

/**
 * @author William
 * ClientCommunicator class: communicates with the client via the proxy.
 */
public class ClientCommunicator
{
	/**
	 * serverhost: the URL of the server.
	 */
	String serverhost = "";
	
	/**
	 * serverport: which port we are accessing the server from
	 */
	String serverport = "";
	
	/**
	 * The prefix to the URL. 
	 */
	String urlprefix = "";
	
	/**
	 * Send function. Sends the message to the proxy. 
	 * @param urlpath: the URL address.
	 * @param data: any data that is included. Read it from the JSON as a string,
	 * but implemented as an object. 
	 * @pre urlpath and data are not null
	 * @exception: throws exception if URL path or Data are invalid. 
	 */
	Object send(String urlpath, Object data)
	{
		return null;
	}
	
	/**
	 * SerializeModel: Serializes all our data to JSON so that the server can have
	 * the data.
	 */
	public void serializeModel()
	{
		
	}
	
	/**
	 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and 
	 * puts it into the model. 
	 */
	public void updateFromJSON()
	{
		
	}
}
