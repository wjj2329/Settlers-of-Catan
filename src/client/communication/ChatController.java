package client.communication;

import client.base.*;
import client.model.ModelFacade;
import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.definitions.CatanColor;
import shared.game.map.Index;
import shared.game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer
{
	/**
	 * Please note that the localPlayer object does NOT get a current color until later!
	 * That's why, down below, I don't use the playerSendingChat variable. Do not change this!
	 * EVER! :O
	 */
	private Player playerSendingChat = ModelFacade.facadeCurrentGame.getLocalPlayer();
	//new Player("Broses", CatanColor.RED, new Index(1));
	//ModelFacade.facadeCurrentGame
	private List<LogEntry> allLogEntries = new ArrayList<>();
	//private IServer server = new ServerProxy();
	private IAction sendChatAction;
	public ChatController(IChatView view)
	{
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message)
	{
		LogEntry logEntry = new LogEntry(ModelFacade.facadeCurrentGame.getLocalPlayer().getColor(), message);
		allLogEntries.add(logEntry);
		getView().setEntries(allLogEntries);
		/*ModelFacade.facadeCurrentGame.getServer().sendChat("Chat",
				ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber(), message);*/
	}

	public Player getPlayerSendingChat()
	{
		return playerSendingChat;
	}

	public void setPlayerSendingChat(Player playerSendingChat)
	{
		this.playerSendingChat = playerSendingChat;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		// We NEED to update the server communication so that everyone can see it!
		// This doesn't quite do it. I don't know why it doesn't work...
		sendChatAction = new IAction()
		{
			@Override
			public void execute()
			{
				for (int i = 0; i < allLogEntries.size(); i++)
				{
					String message = allLogEntries.get(i).getMessage();
					 ModelFacade.facadeCurrentGame.getServer().sendChat("Chat",
							ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber(), message);
				}

			}
		};

		/*for (int i = 0; i < allLogEntries.size(); i++)
		{
			String message = allLogEntries.get(i).getMessage();
			ModelFacade.facadeCurrentGame.getServer().sendChat("Chat",
					ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber(), message);
		}*/
	}
}

