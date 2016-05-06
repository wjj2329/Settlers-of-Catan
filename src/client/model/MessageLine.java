package client.model;

/**
* a simple class to keep track of messages from the players/server one line at a time
 */
public class MessageLine 
{
	String message;
	String source;
	public MessageLine(String message, String source)
	{
		this.message=message;
		this.source=source;
	}

}
