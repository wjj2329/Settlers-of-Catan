package client.model;

import client.main.Catan;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import shared.chat.*;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.CatanGame;
import shared.game.ResourceList;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * Facade for the Model. Every object accessing the model classes should do so through here
 * Note to TA's this isn't completed yet we are just working on it for Phase 2
 *
 */
public class ModelFacade
{
	private CatanGame singleton;
	public static ModelFacade facace_singleton = new ModelFacade();
	
	public ModelFacade()
	{
		singleton = CatanGame.singleton;
	}

	public JSONObject serializeModel() throws JSONException
	{

		JSONObject myobject=new JSONObject();

		//bank code
		JSONObject bank=new JSONObject();
		bank.put("brick", Bank.getSingleton().getCardslist().getBrick());
		bank.put("ore", Bank.getSingleton().getCardslist().getOre());
		bank.put("sheep", Bank.getSingleton().getCardslist().getSheep());
		bank.put("wheat", Bank.getSingleton().getCardslist().getWheat());
		bank.put("wood", Bank.getSingleton().getCardslist().getWood());
		myobject.put("bank", bank);

		//chat code
		JSONObject chat=new JSONObject();
		JSONArray lines=new JSONArray();
		Chat mychat=CatanGame.singleton.getMychat();
		ChatMessages mymessages=mychat.getChatMessages();
		for(int i=0; i<mymessages.getMessages().size(); i++)
		{
			JSONObject messageandstring=new JSONObject();
			messageandstring.put("message",mymessages.getMessages().get(i).getMessage());
			messageandstring.put("source",mymessages.getMessages().get(i).getSource());
			lines.put(messageandstring);
		}
		chat.put("lines",lines);
		myobject.put("chat",chat);

		//logcode
		JSONObject log=new JSONObject();
		JSONArray lines2=new JSONArray();
		GameHistory myhistory=mychat.getGameHistory();
		for(int i=0; i<myhistory.getLines().size();i++)
		{
			JSONObject messageandstring=new JSONObject();
			messageandstring.put("message",myhistory.getLines().get(i).getLine());
			messageandstring.put("source",myhistory.getLines().get(i).getSource());
			lines2.put(messageandstring);
		}
		log.put("lines",lines2);
		myobject.put("chat", log);

		//map
		//hexes
		JSONObject map=new JSONObject();
		JSONObject hexes=new JSONObject();
		Map<HexLocation, Hex> mymap=getMymap().getHexes();
		for(HexLocation loc:mymap.keySet())
		{
			JSONObject locationarray=new JSONObject();
			locationarray.put("x",loc.getX());
			locationarray.put("y", loc.getY());
			hexes.put("location", locationarray);
			hexes.put("resource", mymap.get(loc).getResourcetype());
			hexes.put("number", mymap.get(loc).getResourcenumber());
		}
		map.put("hexes",hexes);
		JSONObject ports=new JSONObject();

		//ports
		for(HexLocation loc:mymap.keySet())
		{
			ports.put("resource", mymap.get(loc).getPortType());
			JSONObject location=new JSONObject();
			location.put("x",loc.getX());
			location.put("y", loc.getX());
			ports.put("location",location);
			ports.put("direction", mymap.get(loc).getPortLocation());
		}
		map.put("ports",ports);

		//roads
		JSONObject roads=new JSONObject();
		for(HexLocation loc:mymap.keySet())
		{
			for(int i=0; i<mymap.get(loc).getRoads().size(); i++)
			{
				roads.put("owner", mymap.get(loc).getRoads().get(i));
				JSONObject location=new JSONObject();
				location.put("x",loc.getX());
				location.put("y",loc.getY());
				roads.put("location",location);
				roads.put("direction",mymap.get(loc).getRoads().get(i).getLocation());
			}
		}
		myobject.put("roads",roads);

		//settlements
		JSONObject settlements=new JSONObject();
		for(HexLocation loc:mymap.keySet())
		{
			for(int i=0; i<mymap.get(loc).getSettlementlist().size(); i++)
			{
				settlements.put("owner", mymap.get(loc).getSettlementlist().get(i).getOwner().getNumber());
				JSONObject location=new JSONObject();
				location.put("x",loc.getX());
				location.put("y",loc.getY());
				settlements.put("location", location);
				settlements.put("direction", mymap.get(loc).getSettlementlist().get(i).getVertexLocation());
			}
		}
		myobject.put("settlements",settlements);

		//cities
		JSONObject cities=new JSONObject();
		for(HexLocation loc:mymap.keySet())
		{
			for(int i=0; i<mymap.get(loc).getCities().size();i++)
			{
				cities.put("owner", mymap.get(loc).getCities().get(i).getOwner().getNumber());
				JSONObject location=new JSONObject();
				location.put("x",loc.getX());
				location.put("y",loc.getY());
				cities.put("location", location);
				cities.put("direction", mymap.get(loc).getSettlementlist().get(i).getVertexLocation());
			}
		}

		//players
		Map<Index, Player>myplayers=CatanGame.singleton.getMyplayers();
		JSONObject players=new JSONObject();
		for(Map.Entry<Index, Player> entry : myplayers.entrySet())
		{
			players.put("cities", entry.getValue().getCities().size());
			players.put("color", entry.getValue().getColor().toString());
			players.put("discarded", entry.getValue().getIsDiscarded());
			players.put("monuments", entry.getValue().getNumMonuments());
			players.put("name", entry.getValue().getName());
			JSONObject newDevCards=new JSONObject();
			newDevCards.put("monopoly", entry.getValue().getNewDevCards().getMonopoly());
			newDevCards.put("monument", entry.getValue().getNewDevCards().getMonument());
			newDevCards.put("roadBuilding", entry.getValue().getNewDevCards().getRoadBuilding());
			newDevCards.put("soldier", entry.getValue().getNewDevCards().getSoldier());
			newDevCards.put("yearOfPlenty", entry.getValue().getNewDevCards().getYearOfPlenty());
			players.put("newDevCards", newDevCards);
			JSONObject oldDevCards=new JSONObject();
			oldDevCards.put("monopoly", entry.getValue().getNewDevCards().getMonopoly());
			oldDevCards.put("monument", entry.getValue().getNewDevCards().getMonument());
			oldDevCards.put("roadBuilding", entry.getValue().getNewDevCards().getRoadBuilding());
			oldDevCards.put("soldier", entry.getValue().getNewDevCards().getSoldier());
			oldDevCards.put("yearOfPlenty", entry.getValue().getNewDevCards().getYearOfPlenty());
			players.put("oldDevCards", oldDevCards);
			players.put("playerIndex", entry.getValue().getPlayerIndex().getNumber());
			players.put("playedDevCard", entry.getValue().getplayedDevCard());
			players.put("playerID", entry.getValue().getPlayerID().getNumber());
			JSONObject resources=new JSONObject();
			resources.put("brick", entry.getValue().getResources().getBrick());
			resources.put("ore", entry.getValue().getResources().getOre());
			resources.put("sheep", entry.getValue().getResources().getSheep());
			resources.put("wheat", entry.getValue().getResources().getWheat());
			resources.put("wood", entry.getValue().getResources().getWood());
			players.put("resources", resources);
			players.put("roads",entry.getValue().getRoadPieces().size());
			players.put("settlements", entry.getValue().getSettlements().size());
			players.put("soldiers", entry.getValue().getArmySize());
			players.put("victoryPoints", entry.getValue().getNumVictoryPoints());
		}
		myobject.put("players", players);

		//tradeOffer
		JSONObject tradeOffer=new JSONObject();
		tradeOffer.put("sender",CatanGame.singleton.getMytradeoffer().getSender());
		tradeOffer.put("receiver", CatanGame.singleton.getMytradeoffer().getReceiver());
		JSONObject myoffer=new JSONObject();
		myoffer.put("brick", CatanGame.singleton.getMytradeoffer().getMylist().getBrick());
		myoffer.put("ore", CatanGame.singleton.getMytradeoffer().getMylist().getOre());
		myoffer.put("sheep", CatanGame.singleton.getMytradeoffer().getMylist().getSheep());
		myoffer.put("wheat", CatanGame.singleton.getMytradeoffer().getMylist().getWheat());
		myoffer.put("wood", CatanGame.singleton.getMytradeoffer().getMylist().getWood());

		//turnTracker
		JSONObject turnTracker=new JSONObject();
		turnTracker.put("currentTurn", CatanGame.singleton.getMyturntracker().getCurrentTurn());
		turnTracker.put("status", CatanGame.singleton.getMyturntracker().getStatus().toString());
		turnTracker.put("longestRoad",CatanGame.singleton.getMyturntracker().getLongestRoad().getNumber());
		turnTracker.put("largestArmy", CatanGame.singleton.getMyturntracker().getLargestArmy().getNumber());

		//last two things
		myobject.put("version", CatanGame.singleton.getVersion().getNumber());
		myobject.put("winner", CatanGame.singleton.getWinner().getNumber());

		return myobject;
	}

		/**
		 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and
		 * puts it into the model.
		 */
	public void updateFromJSON(JSONObject myObject) throws JSONException
	{
		JSONObject bank = myObject.getJSONObject("bank");
		loadBank(bank);

		JSONObject chat = myObject.getJSONObject("chat");
		loadChat(chat);

		JSONObject log = myObject.getJSONObject("log");
		loadLog(log);

		JSONObject map = myObject.getJSONObject("map");
		loadMap(map);

		JSONArray players = myObject.getJSONArray("players");
		loadPlayers(players);

		JSONObject tradeOffer = myObject.getJSONObject("tradeOffer");
		loadTradeOffer(tradeOffer);

		JSONObject turnTracker = myObject.getJSONObject("turnTracker");
		loadTurnTracker(turnTracker);

		int version = myObject.getInt("version");
		int winner_convertToIndex = myObject.getInt("winner");
	}

	private void loadBank(JSONObject bank) throws JSONException
	{
		Bank.getSingleton().getCardslist().setBrick(bank.getInt("brick"));
		Bank.getSingleton().getCardslist().setOre(bank.getInt("ore"));
		Bank.getSingleton().getCardslist().setSheep(bank.getInt("sheep"));
		Bank.getSingleton().getCardslist().setWheat(bank.getInt("wheat"));
		Bank.getSingleton().getCardslist().setWood(bank.getInt("wood"));
	}

	private void loadChat(JSONObject chat) throws JSONException
	{
		JSONArray chatLines = chat.getJSONArray("lines");
		for (int i = 0; i < chatLines.length(); i++)
		{
			JSONObject obj = chatLines.getJSONObject(i);
			CatanGame.singleton.getMychat().getChatMessages().getMessages().add(new
					ChatLine(obj.getString("message"), obj.getString("source")));
		}
	}

	private void loadLog(JSONObject log) throws JSONException
	{
		JSONArray logLines = log.getJSONArray("lines");
		for (int i = 0; i < logLines.length(); i++)
		{
			JSONObject obj = logLines.getJSONObject(i);
			CatanGame.singleton.getMyGameHistory().getLines().add(new
					GameHistoryLine(obj.getString("message"), obj.getString("source")));
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
			String resource = obj.getString("resource");
			HexType hexType = convertToHexType(resource);
			assert(hexType != null); // it must NOT return null or something is wrong with our JSON
			// The last parameter is null for now. NEED to fix ports for now.
			Hex newHex = new Hex(newLoc, hexType, new NumberToken(obj.getInt("number")), null);
			CatanGame.singleton.getMymap().getHexes().put(newLoc, newHex);
		}
		JSONArray ports = map.getJSONArray("ports");
		for (int i = 0; i < ports.length(); i++)
		{
			JSONObject obj = ports.getJSONObject(i);
			String resource = obj.getString("resource"); // this is the port type
			JSONObject location = obj.getJSONObject("location");
			String direction = obj.getString("direction");
			EdgeDirection dir = getDirectionFromString(direction);
			assert(dir != null);
			//Port newPort = new Port(new HexLocation(location.getInt("x"), location.getInt("y")), dir,
					//obj.getInt("ratio"));
			//newPort.setType(getPortTypeFromString(resource));
			//CatanGame.singleton.getMymap().getPorts().add(newPort);
		}
		JSONArray roads = map.getJSONArray("roads");
		for (int i = 0; i < roads.length(); i++)
		{
			JSONObject obj = roads.getJSONObject(i);
			RoadPiece roadPiece = new RoadPiece(new Index(obj.getInt("owner")));
			JSONObject location = obj.getJSONObject("location");
			roadPiece.setLocation(new EdgeLocation(new HexLocation(location.getInt("x"), location.getInt("y")),
					getDirectionFromString(obj.getString("direction"))));
			CatanGame.singleton.getMyplayers().get(roadPiece.getPlayerWhoOwnsRoad()).addToRoadPieces(roadPiece);
		}
		JSONArray settlements = map.getJSONArray("settlements");
		for (int i = 0; i < settlements.length(); i++)
		{
			JSONObject obj = settlements.getJSONObject(i);
			JSONObject location = obj.getJSONObject("location");
			VertexDirection dir = convertToVertexDirection(obj.getString("direction"));
			assert(dir != null);
			Settlement settle1 = new Settlement(new HexLocation(location.getInt("x"), location.getInt("y")),
					new VertexLocation(new HexLocation(location.getInt("x"), location.getInt("y")),
							dir), new Index(obj.getInt("owner")));
			CatanGame.singleton.getMymap().getSettlements().add(settle1);
			CatanGame.singleton.getMyplayers().get(new Index(obj.getInt("owner"))).addToSettlements(settle1);
		}
		JSONArray cities = map.getJSONArray("cities");
		for (int i = 0; i < cities.length(); i++)
		{
			JSONObject obj = cities.getJSONObject(i);
			int owner_convertToIndex = obj.getInt("owner");
			Index owner = new Index(owner_convertToIndex);
			JSONObject location = obj.getJSONObject("location");
			VertexDirection dir = convertToVertexDirection(obj.getString("direction"));
			HexLocation loc = new HexLocation(location.getInt("x"), location.getInt("y"));
			assert(dir != null);
			City city1 = new City(loc, new VertexLocation(loc, dir), owner);
			CatanGame.singleton.getMymap().getCities().add(city1);
			CatanGame.singleton.getMyplayers().get(owner).addToCities(city1);
		}
		CatanGame.singleton.getMymap().setRadius(map.getInt("radius"));
		JSONObject robber = map.getJSONObject("robber");
		Robber.getSingleton().setLocation(new HexLocation(robber.getInt("x"), robber.getInt("y")));
	}

	private void loadPlayers(JSONArray players) throws JSONException
	{
		for (int i = 0; i < players.length(); i++)
		{
			JSONObject obj = players.getJSONObject(i);
			CatanColor color = stringToCatanColor(obj.getString("color"));
			assert(color != null);
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
			newPlayer.setNumSettlementsRemaining(obj.getInt("settlements"));
			newPlayer.setNumSoldierCards(obj.getInt("soldiers"));
			newPlayer.setNumVictoryPoints(obj.getInt("victoryPoints"));

			newDevCards(obj.getJSONObject("newDevCards"), newPlayer);
			oldDevCards(obj.getJSONObject("oldDevCards"), newPlayer);
			resources(obj, newPlayer.getResources());
			CatanGame.singleton.addPlayer(newPlayer);
		}
	}

	private void loadTradeOffer(JSONObject tradeOffer) throws JSONException
	{
		CatanGame.singleton.getMytradeoffer().setSender(tradeOffer.getInt("sender"));
		CatanGame.singleton.getMytradeoffer().setReceiver(tradeOffer.getInt("receiver"));
		CatanGame.singleton.getMytradeoffer();
		JSONObject offer = tradeOffer.getJSONObject("offer");
		CatanGame.singleton.getMytradeoffer().getMylist().setBrick(offer.getInt("brick"));
		CatanGame.singleton.getMytradeoffer().getMylist().setSheep(offer.getInt("sheep"));
		CatanGame.singleton.getMytradeoffer().getMylist().setOre(offer.getInt("ore"));
		CatanGame.singleton.getMytradeoffer().getMylist().setWheat(offer.getInt("wheat"));
	}

	private void loadTurnTracker(JSONObject turnTracker) throws JSONException
	{
		CatanGame.singleton.getMyturntracker().setCurrentTurn(new Index(turnTracker.getInt("currentTurn")),
				CatanGame.singleton.getMyplayers());
		TurnStatus status = convertStringToTurnStatus(turnTracker.getString("status"));
		assert(status != null);
		CatanGame.singleton.getMyturntracker().setStatus(status);
		// actual player who has the longest road
		CatanGame.singleton.getMyturntracker().setLongestRoad(new Index(turnTracker.getInt("longestRoad")));
		CatanGame.singleton.getMyturntracker().setLargestArmy(new Index(turnTracker.getInt("largestArmy")));
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
				assert false;
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
		resList.setBrick(resources.getInt("brick"));
		resList.setOre(resources.getInt("ore"));
		resList.setSheep(resources.getInt("sheep"));
		resList.setWheat(resources.getInt("wheat"));
		resList.setWood(resources.getInt("wood"));
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
				return TurnStatus.DISCARDING;
			case "firstround":
				return TurnStatus.FIRSTROUND;
			case "secondround":
				return TurnStatus.SECONDROUND;
			default:
				assert false;
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
				assert false;
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
				assert false;
		}
		return null;
	}

	private EdgeDirection getDirectionFromString(String direction)
	{
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
			case "SE:":
				return EdgeDirection.SouthEast;
			default:
				assert false;
		}
		return null;
	}

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
				assert false;
		}
		return null;
	}

		// TODO Auto-generated constructor stub
	public Map<Index, Player> getMyplayers()
	{
		return singleton.getMyplayers();
	}

	public void setMyplayers(Map<Index, Player> myplayers)
	{
		singleton.setMyplayers(myplayers);
	}

	public Chat getMychat() 
	{
		return singleton.getMychat();
	}

	public void setMychat(Chat mychat) {
		singleton.setMychat(mychat);
	}

	public CatanMap getMymap()
	{
		return singleton.getMymap();
	}

	public void setMymap(CatanMap mymap) {
		singleton.setMymap(mymap);
	}
	
	public void addPlayer(Player player)
	{
		if(canCreatePlayer(player))
		{
			singleton.getMyplayers().put(player.getPlayerID(), player);
		}
	}

	public boolean canCreatePlayer(Player newplayer)
	{
		return singleton.canCreatePlayer(newplayer);
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
		return singleton.canStartGame();
	}
	/**
	 * a function that starts the game nothing too fancy.   
	 */
	public void startGame()
	{
		singleton.startGame();
	}
	public void clear()
	{
		singleton.clear();
	}

	public Model getModel()
	{
		return singleton.getModel();
	}

	public void setModel(Model newModel)
	{
		singleton.setModel(newModel);		
	}

}
