package client.model;

import java.util.ArrayList;

/**
* MessageList: A class that keeps a container of Message Line objects.
*/
public class MessageList
{
	/**
	 * All the chat lines and game history log messages.
	 */
	ArrayList<MessageLine>lines;
	
	/**
	 * @param message  Not null string that contains a message
	 * @param line Not null line that contains source of the said message.  
	 * @exception throws invalidLocationException if line doesn't exist as a location
	 * @pre message and line must not be null!
	 */
	public void addtoList(String message, String line)
	{
		
	}
}
