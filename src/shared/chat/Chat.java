package shared.chat;

/**
 * @author Alex
 * Chat class: Used to represent the chat system in the Catan game.
 */
public class Chat 
{

	public  ChatMessages getChatMessages()
	{
		return messages;
	}
	/**
	 * messages: an object that encapsulates all chat messages.
	 * Will be passed to the GUI.
	 */
	private ChatMessages messages = new ChatMessages();
	
	/**
	 * history: an object that encapsulates all the history of 
	 * what has happened in the game. Will be passed to the GUI.
	 */
	private GameHistory history = new GameHistory();
	
	/**
	 * Constructs a new Chat object.
	 */
	public Chat()
	{
		
	}
	
	/**
	 * Function to post a chat message.
	 * This may not be necessary since the GUI already posts messages
	 */
	public void postMessage()
	{
		
	}
}
