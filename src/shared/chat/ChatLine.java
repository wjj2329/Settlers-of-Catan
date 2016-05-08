package shared.chat;

import client.model.MessageLine;

/**
 * @author Alex
 * ChatLine: class used to represent an individual line sent in a chat
 */
public class ChatLine extends MessageLine 
{
	/**
	 * Line: The text of the particular chat line.
	 */
	private String line = "";
	
	/**
	 * Constructor
	 */
	public ChatLine(String message, String source)
	{
		super(message, source);
	}
	
	/**
	 * displays chatLine to screen
	 */
	public void display()
	{
		
	}
}
