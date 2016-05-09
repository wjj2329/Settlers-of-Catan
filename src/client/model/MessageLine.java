package client.model;

/**
* a simple class to keep track of messages from the players/server one line at a time
 */
public class MessageLine 
{
	private String message = "";
	private String source = "";
	
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

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}
	
	
}
