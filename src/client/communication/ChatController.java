package client.communication;

import client.base.*;
import client.model.ModelFacade;
import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.definitions.CatanColor;
import shared.game.CatanGame;
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
	 * EVER! :O never mind...
	 */
	private Player playerSendingChat = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
	//new Player("Broses", CatanColor.RED, new Index(1));
	//ModelFacade.facadeCurrentGame
	private List<LogEntry> allLogEntries = new ArrayList<>();
	//private IServer server = new ServerProxy();
	private IAction sendChatAction;
	public ChatController(IChatView view)
	{
		super(view);
		ModelFacade.facadeCurrentGame.addObserver(this);

	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message)
	{
		playerSendingChat = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
		LogEntry logEntry = new LogEntry(playerSendingChat.getColor(), message);
		allLogEntries.add(logEntry);
		getView().setEntries(allLogEntries);
		ModelFacade.facadeCurrentGame.getServer().sendChat("sendChat",
				ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber(), message);
	}



	@Override
	public void update(Observable o, Object arg)
	{
		List<LogEntry>entries=new ArrayList<>();
		CatanColor playercolor=CatanColor.PUCE;
		for(int i=0; i< ModelFacade.facadeCurrentGame.currentgame.getMychat().getChatMessages().getMessages().size(); i++)
		{
			for(Index loc:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet()) {
				if (ModelFacade.facadeCurrentGame.currentgame.getMychat().getChatMessages().getMessages().get(i).getSource().equals(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(loc).getName())) {
					playercolor = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(loc).getColor();
				}
			}
			entries.add(new LogEntry(playercolor,ModelFacade.facadeCurrentGame.getMychat().getChatMessages().getMessages().get(i).getMessage()));
		}
		getView().setEntries(entries);

	}
}

