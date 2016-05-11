package client.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import server.proxies.IServer;
import shared.game.CatanGame;

/**
 * ServerPoller class: Polls the server to see if there is an update.
 * Checks at a very rapid basis - very short intervals - in order to evaluate this.
 *
 */
public class ServerPoller 
{
	/**
	 * Timer: keeps track of the time; helps determine when we should poll
	 * the server.
	 */
	Timer requestTimer = null;
	IServer server;
	CatanGame game;
	
	public ServerPoller(CatanGame game, IServer server)
	{
		this.server = server;
		this.game = game;
		requestTimer=new Timer();
	}
	
	/**
	 * Starts the poller. Should only be called once. 
	 */
	public void startPoller()
	{
		requestTimer.scheduleAtFixedRate(new Poll(this), 0, 2*1000);
		
	}
	public void stop()
	{
		requestTimer.cancel();
	}
	
	public class Poll extends TimerTask
	{
		ServerPoller poller;
		
		public Poll(ServerPoller parent)
		{
			poller = parent;
		}
		
		@Override
		public void run()
		{
			Model newModel = server.getGameModel(game.getModel().getVersion());
			if (newModel != null) {
				System.out.println("New version: "+newModel.getVersion());
				if (newModel.getVersion() > game.getModel().getVersion() || game.getModel().getVersion() == 0)
					game.setModel(newModel);
			}
		}
		
	}
	
}

