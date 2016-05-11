package shared.chat;

import java.util.ArrayList;

/**
 * @author Alex
 * ChatMessages class: used to represent all chat messages displayed
 * May be redundant if already displayed in client > communications package
 */
public class ChatMessages 
{
	private ArrayList<ChatLine> lines = new ArrayList<>();
	/**
	 * ChatMessages constructor
	 */
	public ChatMessages()
	{
		
	}
	public ArrayList<ChatLine> messages()
	{
		return lines;
	}
	
	/**
	 * Displays all chat lines in chat.
	 */
	public void viewChatMessages()
	{
		for (int i = 0; i < lines.size(); i++)
		{
			lines.get(i).display();
		}
	}
}
