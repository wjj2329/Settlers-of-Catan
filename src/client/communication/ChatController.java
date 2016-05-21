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


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController
{
	private Player playerSendingChat = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
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
		allLogEntries.add(new LogEntry(playerSendingChat.getColor(), message));
		getView().setEntries(allLogEntries);
	}

	public Player getPlayerSendingChat()
	{
		return playerSendingChat;
	}

	public void setPlayerSendingChat(Player playerSendingChat)
	{
		this.playerSendingChat = playerSendingChat;
	}
}

