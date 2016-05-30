package shared.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.State.State;
import client.model.*;

import org.json.JSONObject;

import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.chat.Chat;
import shared.chat.GameHistory;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.game.map.CatanMap;
import shared.game.map.Hex.RoadPiece;
import shared.game.map.Index;
import shared.game.map.Robber;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.HexLocation;

/**
 * Catan Game object so that we can have a game accessible to be modified. 
 */


public class CatanGame
{

	public void setMytradeoffer(TradeOffer mytradeoffer) {
		this.mytradeoffer = mytradeoffer;
	}

	public Robber myrobber=new Robber();
	// The singleton was null so I did this instead
	/**
	 * Which state the game is in. Starts at login
	 */
	public State getCurrentState()
	{
		return currentState;
	}

	public Bank mybank=new Bank();
	public void setCurrentState(State currentState)
	{
		this.currentState = currentState;
	}

	/**
	 * I am initializing this to setUp state because we don't seem to need the other state
	 * and it really isn't working the other way.
	 */
	private State currentState = State.SetUpState;
	// we will override this eventually. right now this is for testing purposes:
	private Player currentPlayer = new Player("OscarTheSharkSlayer", CatanColor.BLUE, new Index(1));
	private Model gameModel = new Model();
	private ServerPoller poller;
	private Map<Index, Player> myplayers=new HashMap<>();
	private CatanMap mymap = new CatanMap(RADIUS);
	private Chat mychat=new Chat();
	private GameHistory myGameHistory = new GameHistory();
	private Index winner=new Index(-1);
	private TradeOffer mytradeoffer = null;
	private boolean randomlyPlaceNumbers, randomlyPlaceHexes, randomPorts;
	private String title;
	private static int masterid = 0;
	private int myid;
	
	public void setRobberlocation()
	{
		for(HexLocation loc:mymap.getHexes().keySet())
		{
			if(mymap.getHexes().get(loc).getResourcetype().equals(HexType.DESERT))
			{
				myrobber.setLocation(mymap.getHexes().get(loc).getLocation());
			}
		}
	}

	public CatanGame()
	{
	}

	public CatanGame(boolean randomlyPlaceNumbers, boolean randomlyPlaceHexes, boolean randomPorts, String title) throws Exception
	{
		this.randomlyPlaceNumbers = randomlyPlaceNumbers;
		this.randomlyPlaceHexes = randomlyPlaceHexes;
		this.randomPorts = randomPorts;
		this.title = title;
		myid = masterid++;
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

	public void setTitle(String title){
		this.title = title;
	}
	
	public void setID(int id){
		myid = id;
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
		poller = new ServerPoller(this, ModelFacade.facadeCurrentGame.getServer());
		poller.startPoller();
	}
	public void clear()
	{
		mymap=new CatanMap(RADIUS);
		mychat=new Chat();
		//myplayers=new HashMap<>();
		myGameHistory = new GameHistory();
		mybank = new Bank();
		myrobber = new Robber();
		mytradeoffer=null;

		if (myplayers.size() != 4)
		{
			return;
		}
		// Resetting everything in Player that isn't a primitive, EXCEPT for index and playerID
		for (Player p : myplayers.values())
		{
			p.setNewDevCards(new DevCardList());
			p.setOldDevCards(new DevCardList());
			p.setResources(new ResourceList());

			p.setSettlements(new ArrayList<Settlement>());
			p.setCities(new ArrayList<City>());
			p.setRoadPieces(new ArrayList<RoadPiece>());
		}
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


	public GameHistory getMyGameHistory()
	{
		return myGameHistory;
	}

	public void setMyGameHistory(GameHistory myGameHistory)
	{
		this.myGameHistory = myGameHistory;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}



	public Index getWinner()
	{
		return winner;
	}

	public void setWinner(Index winner)
	{
		this.winner = winner;
	}


	public TradeOffer getMytradeoffer()
	{
		return mytradeoffer;
	}

	public Player getCurrentPlayer()
	{
		if (currentPlayer != null)
		{
			currentPlayer.setCurrentPlayer(true);
		}
		return currentPlayer;
	}

	public void updateCurrentPlayer(Player currentPlayer)
	{
		currentPlayer.setCurrentPlayer(true);
		this.currentPlayer = currentPlayer;
	}

	public int getGameId()
	{
		return myid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setPlayerInfo(Player jugador){
		if(!myplayers.containsKey(jugador.getPlayerID())){
			myplayers.put(jugador.getPlayerID(), jugador);
		}
		myplayers.get(jugador.getPlayerID()).setColor(jugador.getColor());
		myplayers.get(jugador.getPlayerID()).setIsDiscarded(jugador.getIsDiscarded());
		myplayers.get(jugador.getPlayerID()).setNumCitiesRemaining(jugador.getNumCitiesRemaining());
		myplayers.get(jugador.getPlayerID()).setNumMonuments(jugador.getNumMonuments());
		myplayers.get(jugador.getPlayerID()).setPlayedDevCard(jugador.getplayedDevCard());
		myplayers.get(jugador.getPlayerID()).setNumRoadPiecesRemaining(jugador.getNumRoadPiecesRemaining());
		myplayers.get(jugador.getPlayerID()).setNumSettlementsRemaining(jugador.getNumSettlementsRemaining());
		myplayers.get(jugador.getPlayerID()).setNumSoldierCards(jugador.getNumSoldierCards());
		myplayers.get(jugador.getPlayerID()).setNumVictoryPoints(jugador.getNumVictoryPoints());
		myplayers.get(jugador.getPlayerID()).setOldDevCards(jugador.getOldDevCards());
		myplayers.get(jugador.getPlayerID()).setNewDevCards(jugador.getNewDevCards());
		myplayers.get(jugador.getPlayerID()).setResources(jugador.getResources());
		//System.out.println(jugador.getName() + " HASSDF " + jugador.getResources().getBrick() + jugador.getResources().getOre()+ jugador.getResources().getSheep() + jugador.getResources().getWheat() + jugador.getResources().getWood());
		
		
	}
	
}
