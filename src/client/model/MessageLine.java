package client.model;

/**
* a simple class to keep track of messages from the players/server one line at a time
 */
public class MessageLine 
{
	String message = "";
	String source = "";
	
	/**
	 * Constructor for MessageLine:
	 * @param message: the chat message or game history message being sent
	 * @param source: where the message came from. 
	 */
	public MessageLine(String message, String source)
	{
		this.message=message;
		this.source=source;
	}
}
