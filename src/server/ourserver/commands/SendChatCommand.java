package server.ourserver.commands;

import server.ourserver.ServerFacade;
import shared.chat.Chat;
import shared.chat.ChatLine;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

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
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerindex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		System.out.println("MY message is this"+message);
		System.out.println("The player who sent this message is this "+playertoupdate.getName());
		mychat.getChatMessages().getMessages().add(playerindex,new ChatLine(playertoupdate.getName()+": "+message,playertoupdate.getName()));//not sure if this is correct. lol
	}

}
