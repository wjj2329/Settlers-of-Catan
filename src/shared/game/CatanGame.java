package shared.game;

import java.io.FileNotFoundException;
import java.util.*;

import client.State.State;
import client.model.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.ServerFacade;
import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.chat.Chat;
import shared.chat.ChatLine;
import shared.chat.GameHistory;
import shared.chat.GameHistoryLine;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.game.map.Hex.RoadPiece;
import shared.game.map.Index;
import shared.game.map.Port;
import shared.game.map.Robber;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.*;

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
	private Map<Index, Player> myplayers=new HashMap<>(); // trying to change this to a TreeMap
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

	//THIS SHOULD WORK



	public void updateFromJSON(JSONObject myObject) throws JSONException
	{
		//System.out.println("THIS UPDATE FROM JSON IS CALLED AND WILL UPDATE THE MODEL FROM THE SERVER");
		//this.clear();
		//this=new CatanGame();
		JSONObject bank = myObject.getJSONObject("bank");
		loadBank(bank);

		JSONObject chat = myObject.getJSONObject("chat");
		loadChat(chat);

		JSONObject log = myObject.getJSONObject("log");
		loadLog(log);

		JSONArray players = myObject.getJSONArray("players");
		loadPlayers(players);

		JSONObject map = myObject.getJSONObject("map");
		loadMap(map);




		try
		{
			JSONObject tradeOffer = myObject.getJSONObject("tradeOffer");
			loadTradeOffer(tradeOffer);
		}
		catch(JSONException e)
		{
			//e.printStackTrace();
		}

		JSONObject turnTracker = myObject.getJSONObject("turnTracker");
		loadTurnTracker(turnTracker);

		int version = myObject.getInt("version");
		this.getModel().setVersion(version);
		int winner_convertToIndex = myObject.getInt("winner");
		this.setWinner(new Index(winner_convertToIndex));
	}

	private void loadBank(JSONObject bank) throws JSONException
	{
		this.mybank.getCardslist().setBrick(bank.getInt("brick"));
		this.mybank.getCardslist().setOre(bank.getInt("ore"));
		this.mybank.getCardslist().setSheep(bank.getInt("sheep"));
		this.mybank.getCardslist().setWheat(bank.getInt("wheat"));
		this.mybank.getCardslist().setWood(bank.getInt("wood"));
	}

	private void loadChat(JSONObject chat) throws JSONException
	{
		JSONArray chatLines = chat.getJSONArray("lines");
		for (int i = 0; i < chatLines.length(); i++)
		{
			JSONObject obj = chatLines.getJSONObject(i);
			this.getMychat().getChatMessages().getMessages().add(new
					ChatLine(obj.getString("message"), obj.getString("source")));

		}
	}

	private void loadLog(JSONObject log) throws JSONException
	{
		JSONArray logLines = log.getJSONArray("lines");
		for (int i = 0; i < logLines.length(); i++)
		{
			JSONObject obj = logLines.getJSONObject(i);
			this.getMyGameHistory().getLines().add(new
					GameHistoryLine(obj.getString("message"), obj.getString("source")));
			//System.out.println(" I add some more source"+obj.getString("source"));
		}
	}



	private void loadMap(JSONObject map) throws JSONException
	{
		JSONArray hexes = map.getJSONArray("hexes");
		for (int i = 0; i < hexes.length(); i++)
		{
			JSONObject obj = hexes.getJSONObject(i);
			JSONObject location = obj.getJSONObject("location");
			HexLocation newLoc = new HexLocation(location.getInt("x"), location.getInt("y"));
			//System.out.println(obj);
			//JSONObject resources=hexes.getJSONObject(i+1);
			//JSONObject test=hexes.getJSONObject(i+2);
			//System.out.println(resources);
			//System.out.println(test);
			String resource=null;
			HexType hexType = null;
			try {
				resource = obj.getString("resource");

				hexType = convertToHexType(resource);
			}
			catch(JSONException e)
			{

			}
			if(resource!=null&&hexType!=null) {
				Hex newHex = new Hex(newLoc, hexType, new NumberToken(obj.getInt("number")), null);
				this.getMymap().getHexes().put(newLoc, newHex);
			}
			else{
				Hex newHex = new Hex(newLoc, HexType.DESERT, new NumberToken(0), null);
				this.getMymap().getHexes().put(newLoc, newHex);
			}
		}
		JSONArray ports = map.getJSONArray("ports");
		for (int i = 0; i < ports.length(); i++)
		{
			JSONObject obj = ports.getJSONObject(i);
			int ratio=obj.getInt("ratio");
			//System.out.println("This is the ratio"+ratio);
			String resource="three"; // if not 3:1 port, then it's "wood" or "sheep" or something.
			if(ratio != 3)
			{
				resource = obj.getString("resource");
			}// this is the port type
			JSONObject location = obj.getJSONObject("location");
			String direction = obj.getString("direction");
			EdgeDirection dir = getDirectionFromString(direction);
			HexLocation hexLocation=new HexLocation(location.getInt("x"), location.getInt("y"));
			//System.out.println("I set the resource as " + resource + " with a ratio of " + obj.getInt("ratio"));
			//System.out.println("I SET A HEX PORT LOCATION AS THIS"+getPortTypeFromString(resource).toString());
			this.getMymap().getHexes().get(hexLocation).setPortType(getPortTypeFromString(resource));
			Port newPort = new Port(hexLocation, dir,
					obj.getInt("ratio"), getPortTypeFromString(resource)); //this is not going
			this.getMymap().getHexes().get(hexLocation).setPortlocation(newPort);
			newPort.setType(getPortTypeFromString(resource));
			this.getMymap().getPorts().add(newPort);
		}
		JSONArray roads = map.getJSONArray("roads");
		for (int i = 0; i < roads.length(); i++)
		{
			JSONObject obj = roads.getJSONObject(i);
			Index playerIndex = new Index(obj.getInt("owner"));
			Index playerID = null;
			for (Player p : this.getMyplayers().values())
			{
				if (p.getPlayerIndex().equals(playerIndex))
				{
					playerID = p.getPlayerID();
				}
			}
			assert (playerID != null);
			JSONObject location = obj.getJSONObject("location");
			HexLocation loc = new HexLocation(location.getInt("x"), location.getInt("y"));
			EdgeLocation edgeLocation = new EdgeLocation(loc, getDirectionFromString(location.getString("direction")));
			edgeLocation.setHasRoad(true);
			Hex hex = this.getMymap().getHexes().get(loc);
			Hex adjacent = computeAdjacentHex(hex, edgeLocation);
			EdgeLocation adjLoc = computeOppositeEdge(edgeLocation, adjacent);
			RoadPiece r1 = hex.buildRoad(edgeLocation, playerID);
			RoadPiece r2 = adjacent.buildRoad(adjLoc, playerID);
			edgeLocation.setRoadPiece(r1);
			edgeLocation.setHasRoad(true);
			adjLoc.setRoadPiece(r2);
			adjLoc.setHasRoad(true);
			this.getMyplayers().get(playerID).addToRoadPieces(r1);
		}
		JSONArray settlements = map.getJSONArray("settlements");
		for (int i = 0; i < settlements.length(); i++)
		{
			JSONObject obj = settlements.getJSONObject(i);
			JSONObject location = obj.getJSONObject("location");
			VertexDirection dir = convertToVertexDirection(location.getString("direction"));
			VertexLocation mylocation=new VertexLocation(new HexLocation(location.getInt("x"), location.getInt("y")),
					dir);
			mylocation.setHassettlement(true);
			Index playerindex=new Index(obj.getInt("owner"));
			//System.out.println("MY PLAYER INDEX FROM THE JSON IS THIS" +playerindex.getNumber());
			Index playerid = null;
			for (Player p : this.getMyplayers().values())
			{
				//System.out.println("I COMPARE "+p.getPlayerIndex().getNumber()+" with this "+playerindex.getNumber());
				if (p.getPlayerIndex().equals(playerindex))
				{
					playerid = p.getPlayerID();
				}
			}
			Settlement settle1 = new Settlement(new HexLocation(location.getInt("x"), location.getInt("y")),
					mylocation, playerid);
			Hex h = this.getMymap().getHexes().get(settle1.getHexLocation());
			try {
				h.buildSettlement(mylocation,playerid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//this.getMymap().getSettlements().add(settle1);
			settle1.setOwner(playerid);
			//mylocation.setSettlement(settle1);
			//System.out.println("MY PLAYER ID IS THIS "+playerid);
			this.getMyplayers().get(playerid).addToSettlements(settle1);
			/*if (getModel().getTurntracker().getStatus() == TurnStatus.SECONDROUND && this.getMyplayers().get(myindex).getSettlements().size() == 1)
			{
				settle1.setCanBuildFromMeInRound2(false);
			}*/
			//System.out.println("Is this ever set to false? " + settle1.canBuildFromMeInRound2());
			this.getMymap().getSettlements().add(settle1);
			mylocation.setSettlement(settle1);
			//vertexLocationOnHex(mylocation, h);
		}
		JSONArray cities = map.getJSONArray("cities");
		for (int i = 0; i < cities.length(); i++)
		{
			JSONObject obj = cities.getJSONObject(i);
			int owner_convertToIndex = obj.getInt("owner");
			Index owner = new Index(owner_convertToIndex);
			JSONObject location = obj.getJSONObject("location");
			VertexDirection dir = convertToVertexDirection(location.getString("direction"));
			HexLocation loc = new HexLocation(location.getInt("x"), location.getInt("y"));
			//assert(dir != null);
			VertexLocation vertexLoc = new VertexLocation(loc, dir);
			City city1 = new City(loc, vertexLoc, owner);
			vertexLoc.setHascity(true);
			Hex h = this.getMymap().getHexes().get(city1.getHexLocation());
			h.getCities().add(city1);
			this.getMymap().getCities().add(city1);
			Index owner2 = null;
			Index playerIndex = new Index(obj.getInt("owner"));
			//System.out.println("I AM GRABBING THIS INDEX TO CHECK THORUGH MY PLAYERS"+playerIndex.getNumber());
			for (Player p : this.getMyplayers().values())
			{
				//System.out.println("I COMPARE THIS "+p.getPlayerIndex().getNumber()+" WITH THIS "+playerIndex.getNumber());
				if (p.getPlayerIndex().getNumber()==playerIndex.getNumber())
				{
					owner2 = p.getPlayerID();
				}
			}
			assert (owner2 != null);
			city1.setOwner(owner2);
			vertexLoc.setCity(city1);
			//System.out.println("I AM THE OWNER "+owner2.getNumber());
			this.getMyplayers().get(owner2).addToCities(city1);
			// Alex you need to do something that's not this or maybe inialize it or something
		}
		this.getMymap().setRadius(map.getInt("radius"));
		JSONObject robber = map.getJSONObject("robber");
		this.myrobber.setLocation(new HexLocation(robber.getInt("x"), robber.getInt("y")));
	}


	private void loadPlayers(JSONArray players) throws JSONException
	{
		for (int i = 0; i < players.length(); i++)
		{
			if(!players.isNull(i))
			{
				JSONObject obj = players.getJSONObject(i);
				if(obj.toString().equals("{}"))
				{
					return;
				}
				//System.out.println("THIS IS TO CHECK IF THE COLOR IS INSERTED"+obj.toString());
				CatanColor color = stringToCatanColor(obj.getString("color"));
				//assert(color != null);
				Player newPlayer = new Player(obj.getString("name"), color, new Index(obj.getInt("playerID")));

				newPlayer.setNumCitiesRemaining(obj.getInt("cities"));
				newPlayer.setIsDiscarded(obj.getBoolean("discarded"));
				newPlayer.setNumMonuments(obj.getInt("monuments"));
				newPlayer.setPlayerID(new Index(obj.getInt("playerID")));
				newPlayer.setPlayedDevCard(obj.getBoolean("playedDevCard"));
				newPlayer.setPlayerIndex(new Index(obj.getInt("playerIndex")));
				// ATTENTION: I may be setting the wrong variable here. Will double check when testing.
				// although this makes the most sense to me, y'know?
				newPlayer.setNumRoadPiecesRemaining(obj.getInt("roads"));
				System.out.println("THE PLAYER I'M LOading is"+newPlayer.getName()+" "+newPlayer.getNumRoadPiecesRemaining());
				newPlayer.setNumSettlementsRemaining(obj.getInt("settlements"));
				System.out.println("THE PLAYER I'M LOading is"+newPlayer.getName()+" "+newPlayer.getNumSettlementsRemaining());
				newPlayer.setNumSoldierCards(obj.getInt("soldiers"));
				newPlayer.setNumVictoryPoints(obj.getInt("victoryPoints"));

				newDevCards(obj.getJSONObject("newDevCards"), newPlayer);
				oldDevCards(obj.getJSONObject("oldDevCards"), newPlayer);
				JSONObject resources = obj.getJSONObject("resources");
				ResourceList mylist=new ResourceList(resources.getInt("brick"), resources.getInt("ore"),
						resources.getInt("sheep"), resources.getInt("wheat"), resources.getInt("wood"));
				//System.out.println("HEY SO THIS IS WHAT I GIVE MY PLAYER "+resources.toString());
				newPlayer.setResources(mylist);
				//newPlayer.setJoinedGame(true);
				//System.out.println("HEY SO THIS IS WHAT MY PLAYER HAS! "+newPlayer.getResources().toString());
				//resources(obj, newPlayer.getResources());
				//this.addPlayer(newPlayer);
				this.setPlayerInfo(newPlayer);
				//System.out.println("HEY SO THIS IS WHAT I GIVE MY PLAYER in the map "+this.getMyplayers().get(newPlayer.getPlayerID()).toString());
				//this.addPlayer(newPlayer);
				/*   I DON"T THINK THIS IS NEEDED
				if (newPlayer.getName().equals(localplayer.getName()))
				{
					//System.out.println("THIS IS THE LOCAL PLAYER: " + localplayer.getName());
					localplayer = newPlayer;
				}
				*/
			}
			//System.out.println("I add a player with name " + newPlayer.getName());
		}

	}

	private void loadTradeOffer(JSONObject tradeOffer) throws JSONException
	{
		//System.out.println("I HAVE A trade offer to load");
		TradeOffer mytradeoffer=new TradeOffer();
		mytradeoffer.setSender(tradeOffer.getInt("sender"));
		mytradeoffer.setReceiver(tradeOffer.getInt("receiver"));
		JSONObject offer = tradeOffer.getJSONObject("offer");
		mytradeoffer.setMylist(new ResourceList(offer.getInt("brick"), offer.getInt("ore"),
				offer.getInt("sheep"), offer.getInt("wheat"), offer.getInt("wood")));
		this.setMytradeoffer(mytradeoffer);
	}

	private void loadTurnTracker(JSONObject turnTracker) throws JSONException
	{
		Index index = new Index(turnTracker.getInt("currentTurn"));
		//System.out.println("MY TURN INDEX IS THIS" +index.getNumber());
		Index playerWhoseTurnItIs = null;
		for (Player p : this.getMyplayers().values())
		{
			if (p.getPlayerIndex().equals(index))
			{
				playerWhoseTurnItIs = p.getPlayerID();
			}
		}
		if (playerWhoseTurnItIs == null)
		{
			//System.out.println("Could not find correct player index. Crap");
			return;
		}
		// I don't THINK we need to change the index here.
		this.getModel().getTurntracker().setCurrentTurn(index,
				this.getMyplayers());
		this.setCurrentPlayer(this.getMyplayers().get(playerWhoseTurnItIs));
		//System.out.println("I SET MY CURRENT PLAYERS WHOS TURN IT IS THIS"+this.getMyplayers().get(playerWhoseTurnItIs).getName());
		if(this.getMyplayers().size()==4) {// Should stop caring if first player logging in isn't the first to start the game.
			this.getMyplayers().get(playerWhoseTurnItIs).setCurrentPlayer(true);
		}
		TurnStatus status = convertStringToTurnStatus(turnTracker.getString("status").toLowerCase());
		//assert(status != null);
		this.getModel().getTurntracker().setStatus(status);
		// actual player who has the longest road
		this.getModel().getTurntracker().setLongestRoad(new Index(turnTracker.getInt("longestRoad")));
		this.getModel().getTurntracker().setLargestArmy(new Index(turnTracker.getInt("largestArmy")));
	}

	private CatanColor stringToCatanColor(String color)
	{
		switch (color)
		{
			// the colors: RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN
			case "red":
				return CatanColor.RED;
			case "orange":
				return CatanColor.ORANGE;
			case "yellow":
				return CatanColor.YELLOW;
			case "blue":
				return CatanColor.BLUE;
			case "green":
				return CatanColor.GREEN;
			case "purple":
				return CatanColor.PURPLE;
			case "puce":
				return CatanColor.PUCE;
			case "white":
				return CatanColor.WHITE;
			case "brown":
				return CatanColor.BROWN;
			default:
				//assert false;
				break;
		}
		return null;
	}

	private void newDevCards(JSONObject newDevCards, Player player) throws JSONException
	{
		player.getNewDevCards().setMonopoly(newDevCards.getInt("monopoly"));
		player.getNewDevCards().setMonument(newDevCards.getInt("monument"));
		player.getNewDevCards().setRoadBuilding(newDevCards.getInt("roadBuilding"));
		player.getNewDevCards().setSoldier(newDevCards.getInt("soldier"));
		player.getNewDevCards().setYearOfPlenty(newDevCards.getInt("yearOfPlenty"));
	}

	private void oldDevCards(JSONObject oldDevCards, Player player) throws JSONException
	{
		player.getOldDevCards().setMonopoly(oldDevCards.getInt("monopoly"));
		player.getOldDevCards().setMonument(oldDevCards.getInt("monument"));
		player.getOldDevCards().setRoadBuilding(oldDevCards.getInt("roadBuilding"));
		player.getOldDevCards().setSoldier(oldDevCards.getInt("soldier"));
		player.getOldDevCards().setYearOfPlenty(oldDevCards.getInt("yearOfPlenty"));
	}

	private void resources(JSONObject resources, ResourceList resList) throws JSONException
	{
		JSONObject resourcestrue=resources.getJSONObject("resources");
		//System.out.println(resources);
		//System.out.println(resourcestrue);
		resList.setBrick(resourcestrue.getInt("brick"));
		resList.setOre(resourcestrue.getInt("ore"));
		resList.setSheep(resourcestrue.getInt("sheep"));
		resList.setWheat(resourcestrue.getInt("wheat"));
		resList.setWood(resourcestrue.getInt("wood"));
	}

	private TurnStatus convertStringToTurnStatus(String turnStatus)
	{
		switch (turnStatus)
		{
			case "rolling":
				return TurnStatus.ROLLING;
			case "robbing":
				return TurnStatus.ROBBING;
			case "playing":
				return TurnStatus.PLAYING;
			case "discarding":
				//System.out.println("I DO INDEED MAKE IT DISCARDING");
				return TurnStatus.DISCARDING;
			case "firstround":
				return TurnStatus.FIRSTROUND;
			case "secondround":
				return TurnStatus.SECONDROUND;
			default:
				break;
			//assert false;
		}
		return null;
	}

	private HexType convertToHexType(String resource)
	{
		switch (resource)
		{
			case "wood":
				return HexType.WOOD;
			case "brick":
				return HexType.BRICK;
			case "sheep":
				return HexType.SHEEP;
			case "ore":
				return HexType.ORE;
			case "wheat":
				return HexType.WHEAT;
			case "desert":
				return HexType.DESERT;
			case "water":
				return HexType.WATER;
			default:
				break;
			//assert false;
		}
		return null;
	}

	private VertexDirection convertToVertexDirection(String direction)
	{
		switch (direction)
		{
			case "W":
				return VertexDirection.West;
			case "NW":
				return VertexDirection.NorthWest;
			case "NE":
				return VertexDirection.NorthEast;
			case "E":
				return VertexDirection.East;
			case "SE":
				return VertexDirection.SouthEast;
			case "SW":
				return VertexDirection.SouthWest;
			default:
				break;
			//assert false;
		}
		return null;
	}

	private Hex computeAdjacentHex(Hex initial, EdgeLocation edge)
	{
		Hex adjacent = null;
		switch (edge.getDir())
		{
			case NorthWest:
				HexLocation loc1 = new HexLocation(initial.getLocation().getX() - 1, initial.getLocation().getY());
				adjacent = this.getMymap().getHexes().get(loc1);
				break;
			case North:
				HexLocation loc2 = new HexLocation(initial.getLocation().getX(), initial.getLocation().getY() - 1);
				adjacent = this.getMymap().getHexes().get(loc2);
				break;
			case NorthEast:
				HexLocation loc3 = new HexLocation(initial.getLocation().getX() + 1, initial.getLocation().getY() - 1);
				adjacent = this.getMymap().getHexes().get(loc3);
				break;
			case SouthEast:
				HexLocation loc4 = new HexLocation(initial.getLocation().getX() + 1, initial.getLocation().getY());
				adjacent = this.getMymap().getHexes().get(loc4);
				break;
			case South:
				HexLocation loc5 = new HexLocation(initial.getLocation().getX(), initial.getLocation().getY() + 1);
				adjacent = this.getMymap().getHexes().get(loc5);
				break;
			case SouthWest:
				HexLocation loc6 = new HexLocation(initial.getLocation().getX() - 1, initial.getLocation().getY() + 1);
				adjacent = this.getMymap().getHexes().get(loc6);
				break;
			default:
				break;
			//assert false;
		}
		//assert(adjacent != null);
		return adjacent;
	}

	/**
	 * Converts a String to an EdgeDirection. Useful for loading JSON data into the model.
	 * @pre: the string is only one of those listed in the switch case.
	 * @post: the returned EdgeDirection is one of the six directions
	 * 		listed in EdgeDirection.java.
	 * @param direction: string to be converted to EdgeDirection.
	 */
	private EdgeDirection getDirectionFromString(String direction)
	{
		//System.out.println("the direction is: " + direction);
		switch (direction)
		{
			case "NW":
				return EdgeDirection.NorthWest;
			case "N":
				return EdgeDirection.North;
			case "NE":
				return EdgeDirection.NorthEast;
			case "SW":
				return EdgeDirection.SouthWest;
			case "S":
				return EdgeDirection.South;
			case "SE":
				return EdgeDirection.SouthEast;
			default:
				//System.out.println("Something is screwed up with the direction");
				//assert false;
				break;
		}
		return null;
	}

	/**
	 * This function gets a String object from the JSON and converts it
	 * into an object of type PortType.
	 *
	 * One thing I am uncertain of: The default case. How are
	 * 4:1 ports specified here? My guess is that they are not listed
	 * as ports at all, from the JSON or in the model. The reasoning
	 * behind this is that you don't need to even have a port to do
	 * a 4:1 maritime trade; it's only the reduced ratios of 3:1 and 2:1
	 * that require this.
	 * @param type: the string to be converted into a PortType. Should
	 *            only be one of the port types specified in the PortType.java
	 *            enumerated type class.
	 */
	private PortType getPortTypeFromString(String type)
	{
		switch (type)
		{
			case "wood":
				return PortType.WOOD;
			case "sheep":
				return PortType.SHEEP;
			case "brick":
				return PortType.BRICK;
			case "ore":
				return PortType.ORE;
			case "wheat":
				return PortType.WHEAT;
			case "three":
				return PortType.THREE;
			default:
				return PortType.THREE;
		}
		//return null;
	}

	private EdgeLocation computeOppositeEdge(EdgeLocation original, Hex adjacent)
	{
		switch (original.getDir())
		{
			case NorthWest:
				return adjacent.getSe();
			case North:
				return adjacent.getS();
			case NorthEast:
				return adjacent.getSw();
			case SouthEast:
				return adjacent.getNw();
			case South:
				return adjacent.getN();
			case SouthWest:
				return adjacent.getNe();
			default:
				//assert false;
				break;
		}
		return null;
	}
	/*
	public CatanGame getGameByID(int id) throws FileNotFoundException, JSONException {
		for(CatanGame game : ServerFacade.getInstance().getServerModel().listGames()){
			if(game.getGameId() == id){
				return game;
			}
		}
		return null;
	}
			*/

	public JSONObject getGameModel(int gameID) throws FileNotFoundException, JSONException {
		JSONObject model = new JSONObject();
		CatanGame game = this;
		//System.out.println("this is the pointer to the game object" +game);
		//System.out.println("THE GAME GETS LOADED");
		//System.out.println("THIS IS MY GAME ID THAT I GET for exporting"+gameID);

		/*
		for(Player player:this.getMyplayers().values())
		{
			System.out.println(player.getName()+" index "+player.getPlayerIndex().getNumber()+" id "+player.getPlayerID().getNumber());
			player.setPlayerID(player.getPlayerIndex());
		}
		*/

		try {
			//THE BANK
			JSONObject bank = new JSONObject();
			bank.put("brick", game.mybank.getCardslist().getBrick());
			bank.put("ore", game.mybank.getCardslist().getOre());
			bank.put("sheep", game.mybank.getCardslist().getSheep());
			bank.put("wheat", game.mybank.getCardslist().getWheat());
			bank.put("wood", game.mybank.getCardslist().getWood());
			model.put("bank", bank);
			//System.out.println("THE MODEL SO FAR WIT BANK " + model.toString());

			//THE CHAT
			JSONObject chat = new JSONObject();
			JSONArray chatlines = new JSONArray();
			for(MessageLine mensaje : game.getMychat().getChatMessages().getMessages())
			{
				System.out.println("I Export the name  and source which are "+mensaje.getMessage()+" and "+mensaje.getSource());
				JSONObject chatline = new JSONObject();
				chatline.put("message", mensaje.getMessage());
				chatline.put("source", mensaje.getSource());
				chatlines.put(chatline);
			}
			chat.put("lines", chatlines);
			model.put("chat", chat);
			//System.out.println("THE MODEL SO FAR WIT CHAT " + model.toString());

			//THE LOG
			JSONObject log = new JSONObject();
			JSONArray loglines = new JSONArray();
			for(GameHistoryLine mensaje : game.getMyGameHistory().getLines())
			{
				JSONObject logline = new JSONObject();
				logline.put("message", mensaje.getLine());
				logline.put("source", mensaje.getSource());
				loglines.put(logline);
			}
			log.put("lines", loglines);
			model.put("log", log);
			//System.out.println("THE MODEL SO FAR WIT LOG " + model.toString());

			//THE MAP
			//THE HEXES
			JSONObject map = new JSONObject();
			JSONArray hexes = new JSONArray();
			Map<HexLocation, Hex> mapa = game.getMymap().getHexes();
			for(HexLocation elHex : mapa.keySet())
			{
				JSONObject hex = new JSONObject();
				JSONObject location = new JSONObject();
				location.put("x", elHex.getX());
				location.put("y", elHex.getY());
				hex.put("location", location);

				hex.put("resource", mapa.get(elHex).getResourcetype().name().toLowerCase());
				hex.put("number", mapa.get(elHex).getResourcenumber());
				hexes.put(hex);
			}
			map.put("hexes", hexes);
			//System.out.println("THE MAP SO FAR WIT HEXES " + map.toString());

			//THE PORTS
			JSONArray ports = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				if(mapa.get(elHex).getPortType() != null)
				{
					JSONObject port = new JSONObject();
					//System.out.println("What is the resource type of the port? " + mapa.get(elHex).getPortType().name().toLowerCase());
					port.put("resource", mapa.get(elHex).getPortType().name().toLowerCase());

					JSONObject location = new JSONObject();
					location.put("x", elHex.getX());
					location.put("y", elHex.getY());
					port.put("location", location);
					port.put("direction", getDirFromEdgeDir(mapa.get(elHex).getPort().getDirection()));
					port.put("ratio", mapa.get(elHex).getPort().getRatio());
					ports.put(port);
				}
			}
			map.put("ports", ports);
			//System.out.println("THE MAP SO FAR WIT PORTS " + map.toString());

			//THE ROADS
			JSONArray roads = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				for(RoadPiece calle : mapa.get(elHex).getRoads())
				{
					if(calle.getPlayerWhoOwnsRoad() != null)
					{
						JSONObject road = new JSONObject();
						road.put("owner", calle.getPlayerWhoOwnsRoad().getNumber());
						System.out.println("I MAKE A ROAD" +calle.getPlayerWhoOwnsRoad().getNumber());

						JSONObject location = new JSONObject();
						location.put("x", elHex.getX());
						location.put("y", elHex.getY());

						location.put("direction", getDirFromEdgeDir(calle.getLocation().getDir()));
						road.put("location", location);

						roads.put(road);
					}
				}
			}
			map.put("roads", roads);
			//System.out.println("THE MAP SO FAR WIT ROADS " + map.toString());

			//THE SETTLEMENTS
			JSONArray settlements = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				for(Settlement colonia : mapa.get(elHex).getSettlementlist())
				{
					System.out.println(" I DO INDEED HAVE A SETTLEMENT ");
					//if(colonia.getOwner().getNumber() >= 0 && colonia.getOwner().getNumber() <= 4)
					{
						System.out.println("I DO INDEED INSERT SETTLMENT at location "+elHex.getX()+" "+elHex.getY());
						//System.out.println("THE LOCATION OF SAID DIRECTION BEFORE FUNCTION IS THIS "+colonia.getVertexLocation().getDir());
						//System.out.println( "THAT SETTLEMENT IS ALSO AT DIRECTION "+getDirFromVertexDir(colonia.getVertexLocation().getDir()));
						JSONObject settlement = new JSONObject();
						System.out.println("The owner's playerIndex (or is it playerID?) is " + colonia.getOwner().getNumber());
						settlement.put("owner", colonia.getOwner().getNumber());
						JSONObject location = new JSONObject();
						location.put("x", elHex.getX());
						location.put("y", elHex.getY());
						location.put("direction", getDirFromVertexDir(colonia.getVertexLocation().getDir()));
						settlement.put("location", location);

						settlements.put(settlement);
					}
				}
			}
			map.put("settlements", settlements);
			//System.out.println("THE MAP SO FAR WIT SETTLEMENTS " + map.toString());

			//THE CITIES
			JSONArray cities = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				for(City cuidad : mapa.get(elHex).getCities())
				{
					if(cuidad.getOwner() != null)
					{
						JSONObject city = new JSONObject();
						//System.out.println("I INSERT IN THE Mode of storay "+cuidad.getOwner().getNumber());
						city.put("owner", cuidad.getOwner().getNumber());

						JSONObject location = new JSONObject();
						location.put("x", elHex.getX());
						location.put("y", elHex.getY());
						location.put("direction", getDirFromVertexDir(cuidad.getVertexLocation().getDir()));
						city.put("location", location);

						cities.put(city);
					}
				}
			}
			map.put("cities", cities);
			map.put("radius", game.getMymap().getRadius());
			//System.out.println("THE MAP SO FAR WIT CITIES " + map.toString());

			//THE ROBBER
			JSONObject robber = new JSONObject();
			robber.put("x", game.myrobber.getLocation().getX());
			robber.put("y", game.myrobber.getLocation().getY());
			map.put("robber", robber);
			//System.out.println("THE MAP SO FAR WIT ROBBER " + map.toString());

			model.put("map", map);
			//System.out.println("THE MODEL SO FAR WIT MAP " + model.toString());

			//THE PLAYERS
			Map<Index, Player> jugadores = game.getMyplayers();
			JSONArray players = new JSONArray();
			int numPlayahs = 0;
			for(Player jugador : jugadores.values())
			{
				JSONObject player = new JSONObject();
				player.put("cities", jugador.getNumCitiesRemaining());
				player.put("color", jugador.getColor().name().toLowerCase());
				player.put("discarded", jugador.getIsDiscarded());
				player.put("monuments", jugador.getNumMonuments());
				player.put("name", jugador.getName());
				//System.out.println("THE PLAYER SO FAR WIT INFO  " + player.toString());

				//THE NEW DEVCARDS
				JSONObject newDevCards = new JSONObject();
				DevCardList cartasNuevas = jugador.getNewDevCards();
				newDevCards.put("monopoly", cartasNuevas.getMonopoly());
				newDevCards.put("monument", cartasNuevas.getMonument());
				newDevCards.put("roadBuilding", cartasNuevas.getRoadBuilding());
				newDevCards.put("soldier", cartasNuevas.getSoldier());
				newDevCards.put("yearOfPlenty", cartasNuevas.getYearOfPlenty());
				player.put("newDevCards", newDevCards);
				//System.out.println("THE PLAYER SO FAR WIT NEW DEV CARDS " + player.toString());


				//THE OLD DEVCARDS
				JSONObject oldDevCards = new JSONObject();
				DevCardList cartasViejas = jugador.getOldDevCards();
				oldDevCards.put("monopoly", cartasViejas.getMonopoly());
				oldDevCards.put("monument", cartasViejas.getMonument());
				oldDevCards.put("roadBuilding", cartasViejas.getRoadBuilding());
				oldDevCards.put("soldier", cartasViejas.getSoldier());
				oldDevCards.put("yearOfPlenty", cartasViejas.getYearOfPlenty());
				player.put("oldDevCards", oldDevCards);
				//System.out.println("THE PLAYER SO FAR WIT OLD DEV CARDS " + player.toString());

				player.put("playerIndex", jugador.getPlayerIndex().getNumber());
				//System.out.println("THE PLAYER SO FAR WIT MORE INFO INDEX " + player.toString());
				player.put("playedDevCard", jugador.getplayedDevCard());
				//System.out.println("THE PLAYER SO FAR WIT MORE INFO PLAYED DEVCARD " + player.toString());
				player.put("playerID", jugador.getPlayerID().getNumber());
				//System.out.println("THE PLAYER SO FAR WIT MORE INFO ID " + player.toString());

				JSONObject resources = new JSONObject();
				ResourceList recursos = jugador.getResources();

				resources.put("brick", recursos.getBrick());
				resources.put("ore", recursos.getOre());
				resources.put("sheep", recursos.getSheep());
				resources.put("wheat", recursos.getWheat());
				resources.put("wood", recursos.getWood());
				player.put("resources", resources);
				//System.out.println("THE PLAYER SO FAR WIT RESOURCES " + player.toString());

				player.put("roads", jugador.getNumRoadPiecesRemaining());
				player.put("settlements", jugador.getNumSettlementsRemaining());
				player.put("soldiers", jugador.getArmySize());
				player.put("victoryPoints", jugador.getNumVictoryPoints());

				//System.out.println("THE PLAYER SO FAR WIT MORE RESOURCES " + player.toString());
				players.put(player);
				//System.out.println("THE PLAYERS SO FAR WIT ANOTHER PLAYAH  " + players.toString());
				numPlayahs++;
			}
			while(numPlayahs < 4)
			{
				JSONObject player = new JSONObject();
				players.put(player);
				//System.out.println("THE PLAYERS SO FAR WIT NULL PLAYAH  " + players.toString());
				numPlayahs++;
			}
			model.put("players", players);
			//System.out.println("THE MODEL SO FAR WIT PLAYAHS " + model.toString());

			//THE TRADEOFFER
			if(game.getMytradeoffer() != null)
			{
				JSONObject tradeOffer = new JSONObject();
				TradeOffer negocio = game.getMytradeoffer();
				tradeOffer.put("sender", negocio.getSender());
				tradeOffer.put("receiver", negocio.getReceiver());

				JSONObject offer = new JSONObject();
				ResourceList ofrecimiento = negocio.getMylist();
				offer.put("brick", ofrecimiento.getBrick());
				offer.put("ore", ofrecimiento.getOre());
				offer.put("sheep", ofrecimiento.getSheep());
				offer.put("wheat", ofrecimiento.getWheat());
				offer.put("wood", ofrecimiento.getWood());
				tradeOffer.put("offer", offer);
				model.put("tradeOffer", tradeOffer);
			}
			//System.out.println("THE MODEL SO FAR WIT TRADEOFFER MAYBE " + model.toString());


			//THE TURN TRACKER
			JSONObject turnTracker = new JSONObject();
			TurnTracker turnos = game.getModel().getTurntracker();
			turnTracker.put("currentTurn", turnos.getCurrentTurn().getNumber());
			turnTracker.put("status", turnos.getStatus());
			turnTracker.put("longestRoad", turnos.getLongestRoad().getNumber());
			turnTracker.put("largestArmy", turnos.getLargestArmy().getNumber());
			model.put("turnTracker", turnTracker);
			//System.out.println("THE MODEL SO FAR WIT TURNTRACKER " + model.toString());


			model.put("version", game.getModel().getVersion());
			model.put("winner", game.getWinner().getNumber());

			//model.put("state", game.getCurrentState());

			//System.out.println("THE MODEL SO FAR WIT EVERYTHANG " + model.toString());
			//System.out.println("GAME TITLE" + game.getTitle());
			return model;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println(e.toString());
		}
		return null;
	}

	public String getDirFromVertexDir(VertexDirection direction){
		switch (direction)
		{
			case East:
				return "E";
			case NorthWest:
				return "NW";
			case NorthEast:
				return "NE";
			case SouthWest:
				return "SW";
			case West:
				return "W";
			case SouthEast:
				return "SE";
			default:
				break;
		}
		//System.out.println(" I RETURN A NULL LOCATION!");
		return null;
	}
	public String getDirFromEdgeDir(EdgeDirection direction){
		switch (direction)
		{
			case NorthWest:
				return "NW";
			case North:
				return "N";
			case NorthEast:
				return "NE";
			case SouthWest:
				return "SW";
			case South:
				return "S";
			case SouthEast:
				return "SE";
			default:
				break;
		}
		return null;
	}

}
