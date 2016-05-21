package client.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import server.proxies.IServer;
import server.response.ServerResponse;
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
	CatanGame game = ModelFacade.facadeCurrentGame.currentgame;
	public static Boolean stop=false;
	
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
		System.out.println("Polling has started...");
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
			//System.out.println("polling server");
			int version = 0;
			ServerResponse json = ModelFacade.facadeCurrentGame.getServer().getGameCurrentState(game.getModel().getVersion());
			try
			{
				JSONObject object = new JSONObject(json.getResponse());
				version = object.getInt("version");
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Model newModel = server.getGameModel(game.getModel().getVersion());
			//if (newModel != null) 
			//{
//				System.out.println("New version: "+newModel.getVersion());
//				if (newModel.getVersion() > game.getModel().getVersion() || game.getModel().getVersion() == 0)
//				{
//					game.setModel(newModel);
//				}
			//}			
			//System.out.println("New version: " + version);
			if (version > game.getModel().getVersion() || game.getModel().getVersion() == 0)
			{
				if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size()<4) {
					try
					{
						System.out.println("Old version: " + game.getModel().getVersion());
						ModelFacade.facadeCurrentGame.updateFromJSON(new JSONObject(json.getResponse()));
						System.out.println("New version: " + game.getModel().getVersion());
						System.out.println(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size());
						stop = true;

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}

