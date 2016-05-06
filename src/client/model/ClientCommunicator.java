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
	 * @param data: any data that is included. 
	 * @custom.mytag1 pre: urlpath and data are not null
	 * @exception: throws exception if URL path or Data are invalid. 
	 */
	void send(String urlpath, String data)
	{
		
	}
}
