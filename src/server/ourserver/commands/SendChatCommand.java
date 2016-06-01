package server.ourserver.commands;

import server.ourserver.ServerFacade;
import shared.chat.Chat;
import shared.chat.ChatLine;
import shared.game.CatanGame;

/**
 * The SendChatCommand
 */
public class SendChatCommand implements ICommand {

	/**
	 * Execute the task:
	 * 	at anytime the player can send a string (their message) and the chat will contain it	
	 */
	@Override
	public Object execute()
	{
		// TODO Auto-generated method stub

		return null;
	}

	public void sendChat(String message, int playerindex, int gameid)
	{
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		Chat mychat=currentgame.getMychat();
		mychat.getChatMessages().getMessages().add(playerindex,new ChatLine(message,message));//not sure if this is correct. lol
	}

}
