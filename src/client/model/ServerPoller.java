package client.model;

import java.util.Timer;

/**
 * ServerPoller class: Polls the server to see if there is an update.
 * Checks at a very rapid basis - very short intervals - in order to evaluate this.
 *
 */
public class ServerPoller 
{
	/**
	 * Timer: keeps track of the time; helps determine when we should poll
	 * the server.
	 */
	Timer requestTimer = null;
	
	// Add dependency injection.
	public ServerPoller()
	{
		
	}
	
	/**
	 * Function to determine whether or not we need an update. 
	 */
	boolean needUpdate()
	{
		return true;
	}
	
	/**
	 * Starts the poller. Should only be called once. 
	 */
	void startPoller()
	{
		
	}
}

