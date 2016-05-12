package shared.game;

import java.util.ArrayList;

import org.json.JSONObject;

import client.model.Model;
import client.model.ServerPoller;
import server.proxies.IServer;
import shared.chat.Chat;
import shared.game.map.CatanMap;
import shared.game.player.Player;
/**
 * Catan Game object so that we can have a game accessible to be modified. 
 */


public class CatanGame
{
	// The singleton was null so I did this instead
	public static CatanGame singleton = new CatanGame();
	private Model gameModel = new Model();
	private ServerPoller poller;
	private IServer server;
	private ArrayList<Player>myplayers=new ArrayList();
	private CatanMap mymap = new CatanMap(RADIUS);
	private Chat mychat=new Chat();
	// If there is a singleton, then this shouldn't exist.
	public CatanGame(IServer server) throws Exception
	{
		 if(singleton == null) // this doesn't work
		 {
			 singleton = this;
			 this.server = server;
		 }
		 else
		 {
			 throw new Exception();
		 }
	}
	// If this is a singleton, then this shouldn't exist.
	//for testting purposes only
	public CatanGame()
	{
		singleton=this;
	}
	
	public ArrayList<Player> getMyplayers() {
		return myplayers;
	}

	public void setMyplayers(ArrayList<Player> myplayers) {
		this.myplayers = myplayers;
	}

	public Chat getMychat() {
		return mychat;
	}

	public void setMychat(Chat mychat) {
		this.mychat = mychat;
	}

	public CatanMap getMymap() {
		return mymap;
	}

	public void setMymap(CatanMap mymap) {
		this.mymap = mymap;
	}
	
	public void addPlayer(Player player)
	{
		if(canCreatePlayer(player)) {
			myplayers.add(player);
		}
	}

	public boolean canCreatePlayer(Player newplayer)
	{
		if(myplayers==null)
		{
			myplayers=new ArrayList<Player>();
		}
		for(int i=0; i<myplayers.size(); i++)
		{
			if(myplayers.get(i)==newplayer)
			{
				return false;
			}
			if(myplayers.get(i).getColor().equals(newplayer.getColor()))
			{
				return false;
			}
		}
		return true;
	}	
	/**
	 *  a function to see if we can start the game
	 *  @exception throws exception if not able to start the game for anything that would prevent from starting
	 *  such as not enough players, invalid Map,  Internet problems,  Server failure etc. 
	 *  @post return true if we can start the game. 
	 * 	@return
	 */
	public boolean canStartGame() throws Exception
	{

		if(mymap==null)
		{
			return false;
		}
		if(myplayers==null)
		{
			return false;
		}
		if(myplayers.size()>4)
		{
			Exception e = new Exception();
			e.printStackTrace();
			throw e;
		}
		if(myplayers.size()<4)
		{
			return false;
		}
		return true;
	}
	/**
	 * a function that starts the game nothing too fancy.   
	 */
	public void startGame()
	{
		poller = new ServerPoller(this, server);
		poller.startPoller();
	}
	public void clear()
	{
		mymap=null;
		mychat=null;
		myplayers=null;
	}

	public Model getModel()
	{
		return gameModel;
	}

	public void setModel(Model newModel)
	{
		gameModel = newModel;
		
	}
	public JSONObject serializeModel()
	{
		return null;
	}

		/**
		 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and
		 * puts it into the model.
		 */
	public void updateFromJSON(JSONObject myobject)
	{

	}

	/**
	 * I am not sure what the radius should be yet so I am just putting
	 * in this value. Feel free to change this
	 */
	private static final int RADIUS = 10;

}
