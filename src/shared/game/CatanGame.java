package shared.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.State.State;
import client.model.*;

import org.json.JSONObject;

import server.proxies.IServer;
import shared.chat.Chat;
import shared.chat.GameHistory;
import shared.definitions.CatanColor;
import shared.game.map.CatanMap;
import shared.game.map.Index;
import shared.game.player.Player;
/**
 * Catan Game object so that we can have a game accessible to be modified. 
 */


public class CatanGame
{
	// The singleton was null so I did this instead
	public static CatanGame singleton = new CatanGame();

	/**
	 * Which state the game is in. Starts at login
	 */
	public State getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState(State currentState)
	{
		this.currentState = currentState;
	}
	private State currentState = State.SetUpState;
	// we will override this eventually. right now this is for testing purposes:
	private Player currentPlayer = new Player("OscarTheSharkSlayer", CatanColor.BLUE, new Index(1));
	private Model gameModel = new Model();
	private ServerPoller poller;
	private IServer server;
	private Map<Index, Player> myplayers=new HashMap<>();
	private CatanMap mymap = new CatanMap(RADIUS);
	private Chat mychat=new Chat();
	private GameHistory myGameHistory = new GameHistory();
	private TurnTracker myturntracker=new TurnTracker(TurnStatus.FIRSTROUND, new Index(0), new Index(1), new Index(2));
	private Index version=new Index(0);
	private Index winner=new Index(0);
	private TradeOffer mytradeoffer=new TradeOffer();
	// If there is a singleton, then this shouldn't exist.
	public CatanGame(IServer server, boolean randomlyPlaceNumbers, boolean randomlyPlaceHexes, boolean randomPorts, String title) throws Exception
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
	
	public Map<Index, Player> getMyplayers() {
		return myplayers;
	}

	public void setMyplayers(Map<Index, Player> myplayers) {
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
		if(canCreatePlayer(player))
		{
			myplayers.put(player.getPlayerID(), player);
		}
	}

	public boolean canCreatePlayer(Player newplayer)
	{
		if(myplayers==null)
		{
			myplayers=new HashMap<>();
		}
		for (Map.Entry<Index, Player> entry : myplayers.entrySet())
		{
			if(entry.getValue() == newplayer)
			{
				return false;
			}
			if(entry.getValue().getColor().equals(newplayer.getColor()))
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
	public TurnTracker getMyturntracker()
	{
		return myturntracker;
	}

	public GameHistory getMyGameHistory()
	{
		return myGameHistory;
	}

	public void setMyGameHistory(GameHistory myGameHistory)
	{
		this.myGameHistory = myGameHistory;
	}

	public Index getVersion()
	{
		return version;
	}

	public Index getWinner()
	{
		return winner;
	}
	public TradeOffer getMytradeoffer()
	{
		return mytradeoffer;
	}

	public Player getCurrentPlayer()
	{
		currentPlayer.setCurrentPlayer(true);
		return currentPlayer;
	}

	public void updateCurrentPlayer(Player currentPlayer)
	{
		currentPlayer.setCurrentPlayer(true);
		this.currentPlayer = currentPlayer;
	}

	public int getGameId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTitle()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
