package client.model;

import client.State.State;
import client.main.Catan;

import client.map.MapController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.chat.*;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.CatanGame;
import shared.game.DevCardList;
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
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * 
 * Facade for the Model. Every object accessing the model classes should do so through here
 * Note to TA's this isn't completed yet we are just working on it for Phase 2
 *
 */
public class ModelFacade extends Observable
{
	public CatanGame currentgame = new CatanGame();
	public static ModelFacade facace_currentgame = new ModelFacade();
	private Player localplayer;
	IServer server = new ServerProxy();
	
	public void loadGames()
	{
		String JSON = server.getAllCurrentGames().getResponse();
		
		ArrayList<CatanGame> games = new ArrayList<CatanGame>();
		 try {
			JSONArray array = new JSONArray(JSON);
			for(int i = 0; i < array.length(); i++){
				JSONObject jsonObject = array.getJSONObject(i);
				CatanGame game = new CatanGame();
				game.setTitle(jsonObject.getString("title"));
				game.setID(jsonObject.getInt("id"));
				
				JSONArray players = jsonObject.getJSONArray("players");
				for(int p=0; p<players.length(); p++){
					JSONObject playerinfo = players.getJSONObject(p);
					if(!playerinfo.isNull("name"))
					{
						Player player = new Player(playerinfo.getString("name"), 
							stringToCatanColor(playerinfo.getString("color")), 
							new Index(playerinfo.getInt("id")));
						game.addPlayer(player);
					}
				}
				games.add(game);
			}
			
			getModel().setListGames(games);
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}

	public JSONObject serializeModel() throws JSONException
	{
		JSONObject myobject=new JSONObject();
		//bank code
		JSONObject bank=new JSONObject();
		bank.put("brick", currentgame.mybank.getCardslist().getBrick());
		bank.put("ore", currentgame.mybank.getCardslist().getOre());
		bank.put("sheep", currentgame.mybank.getCardslist().getSheep());
		bank.put("wheat", currentgame.mybank.getCardslist().getWheat());
		bank.put("wood", currentgame.mybank.getCardslist().getWood());
		myobject.put("bank", bank);

		//chat code
		JSONObject chat=new JSONObject();
		JSONArray lines=new JSONArray();
		Chat mychat=currentgame.getMychat();
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
		Map<Index, Player>myplayers=currentgame.getMyplayers();
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
		tradeOffer.put("sender",currentgame.getMytradeoffer().getSender());
		tradeOffer.put("receiver", currentgame.getMytradeoffer().getReceiver());
		JSONObject myoffer=new JSONObject();
		myoffer.put("brick", currentgame.getMytradeoffer().getMylist().getBrick());
		myoffer.put("ore", currentgame.getMytradeoffer().getMylist().getOre());
		myoffer.put("sheep", currentgame.getMytradeoffer().getMylist().getSheep());
		myoffer.put("wheat", currentgame.getMytradeoffer().getMylist().getWheat());
		myoffer.put("wood", currentgame.getMytradeoffer().getMylist().getWood());

		//turnTracker
		JSONObject turnTracker=new JSONObject();
		turnTracker.put("currentTurn", currentgame.getModel().getTurntracker().getCurrentTurn());
		turnTracker.put("status", currentgame.getModel().getTurntracker().getStatus().toString());
		turnTracker.put("longestRoad",currentgame.getModel().getTurntracker().getLongestRoad().getNumber());
		turnTracker.put("largestArmy", currentgame.getModel().getTurntracker().getLargestArmy().getNumber());

		//last two things
		myobject.put("version", currentgame.getVersion().getNumber());
		myobject.put("winner", currentgame.getWinner().getNumber());

		return myobject;
	}

		/**
		 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and
		 * puts it into the model.
		 */
	public void updateFromJSON(JSONObject myObject) throws JSONException
	{
		System.out.println("THIS UPDATE FROM JSON IS CALLED AND WILL UPDATE THE MODEL FROM THE SERVER");
		currentgame.clear();
		currentgame=new CatanGame();
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
		int winner_convertToIndex = myObject.getInt("winner");
		this.setChanged();
		notifyObservers();
	}

	private void loadBank(JSONObject bank) throws JSONException
	{
		currentgame.mybank.getCardslist().setBrick(bank.getInt("brick"));
		currentgame.mybank.getCardslist().setOre(bank.getInt("ore"));
		currentgame.mybank.getCardslist().setSheep(bank.getInt("sheep"));
		currentgame.mybank.getCardslist().setWheat(bank.getInt("wheat"));
		currentgame.mybank.getCardslist().setWood(bank.getInt("wood"));
	}

	private void loadChat(JSONObject chat) throws JSONException
	{
		JSONArray chatLines = chat.getJSONArray("lines");
		for (int i = 0; i < chatLines.length(); i++)
		{
			JSONObject obj = chatLines.getJSONObject(i);
			currentgame.getMychat().getChatMessages().getMessages().add(new
					ChatLine(obj.getString("message"), obj.getString("source")));
		}
	}

	private void loadLog(JSONObject log) throws JSONException
	{
		JSONArray logLines = log.getJSONArray("lines");
		for (int i = 0; i < logLines.length(); i++)
		{
			JSONObject obj = logLines.getJSONObject(i);
			currentgame.getMyGameHistory().getLines().add(new
					GameHistoryLine(obj.getString("message"), obj.getString("source")));
		}
	}


	public void loadhexesdifferent(JSONObject hexGrid) throws JSONException
	{
		JSONArray hexes=hexGrid.getJSONArray("hexes");
		for (int i = 0; i < hexes.length(); i++)
		{
			JSONObject Location=hexes.getJSONObject(i).getJSONObject("location");
			HexLocation mylocation=new HexLocation(Location.getInt("x"),Location.getInt("y"));
			boolean isLand=hexes.getJSONObject(i).getBoolean("isLand");
			String landtype;
			if(isLand)
			{
			landtype=hexes.getJSONObject(i).getString("landtype");
			}
			else
			{
				landtype="water";
			}
			Hex myhex=new Hex(mylocation,convertToHexType(landtype),new NumberToken(-1),null);
			currentgame.getMymap().getHexes().put(mylocation,myhex);
		}

	}
public void loadnumbersdifferent(JSONObject numbers) throws JSONException {
	for(Integer i=2; i<12; i++)
	{
		if(i!=7) {
			JSONObject number = numbers.getJSONObject(i.toString());
			HexLocation location=new HexLocation(number.getInt("x"),number.getInt("y"));
			currentgame.getMymap().getHexes().get(location).setResourcenumber(new NumberToken(i));
		}
	}
}

public void loadGameDifferentJson(JSONObject mygame) throws JSONException {

	currentgame.clear();
	JSONObject deck=mygame.getJSONObject("deck");
	int yearofplenty=deck.getInt("yearOfPlenty");
	int monopoly=deck.getInt("monopoly");
	int soldier=deck.getInt("soldier");
	int roadBuilding=deck.getInt("roadBuilding");
	int monument=deck.getInt("monument");
	DevCardList mylist=new DevCardList(monopoly,monument,roadBuilding,soldier,yearofplenty);
	currentgame.mybank.setDevCardList(mylist);
	JSONObject map=mygame.getJSONObject("map");

	JSONObject hexGrid=mygame.getJSONObject("hexGrid");
	loadhexesdifferent(hexGrid);

	JSONObject numbers=mygame.getJSONObject("numbers");
	loadnumbersdifferent(numbers);


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
					currentgame.getMymap().getHexes().put(newLoc, newHex);
				}
			else{
					Hex newHex = new Hex(newLoc, HexType.DESERT, new NumberToken(0), null);
					currentgame.getMymap().getHexes().put(newLoc, newHex);
				}
		}
		JSONArray ports = map.getJSONArray("ports");
		for (int i = 0; i < ports.length(); i++)
		{
			JSONObject obj = ports.getJSONObject(i);
			//System.out.println(obj);
			int ratio=obj.getInt("ratio");
			String resource="3:1";
			if(ratio!=3) {
				 resource = obj.getString("resource");
			}// this is the port type
			JSONObject location = obj.getJSONObject("location");
			String direction = obj.getString("direction");
			EdgeDirection dir = getDirectionFromString(direction);
			assert(dir != null);
			Port newPort = new Port(new HexLocation(location.getInt("x"), location.getInt("y")), dir,
					obj.getInt("ratio"),getPortTypeFromString(resource));//this is not going
			newPort.setType(getPortTypeFromString(resource));
			currentgame.getMymap().getPorts().add(newPort);
		}
		JSONArray roads = map.getJSONArray("roads");
		for (int i = 0; i < roads.length(); i++)
		{
			JSONObject obj = roads.getJSONObject(i);
			//System.out.println(obj);
			Index playerID = new Index(obj.getInt("owner"));
			RoadPiece roadPiece = new RoadPiece(playerID);
			JSONObject location = obj.getJSONObject("location");
			//System.out.println(location);
			HexLocation loc = new HexLocation(location.getInt("x"), location.getInt("y"));
			EdgeLocation edgeLocation = new EdgeLocation(loc, getDirectionFromString(location.getString("direction")));
			roadPiece.setLocation(edgeLocation);
			roadPiece.setPlayerWhoOwnsRoad(playerID);
			Hex hex = currentgame.getMymap().getHexes().get(loc);
			edgeLocation.setRoadPiece(roadPiece);
			hex.buildRoad(edgeLocation, playerID);

			// I AM RIGHT HERE

			//currentgame.getMyplayers().get(roadPiece.getPlayerWhoOwnsRoad()).addToRoadPieces(roadPiece);
			// Alex you need to do something that's not this or maybe inialize it or something
		}
		JSONArray settlements = map.getJSONArray("settlements");
		for (int i = 0; i < settlements.length(); i++)
		{
			JSONObject obj = settlements.getJSONObject(i);
			System.out.println("this is a settlement I have"+obj);
			JSONObject location = obj.getJSONObject("location");
			VertexDirection dir = convertToVertexDirection(location.getString("direction"));
			assert(dir != null);
			VertexLocation mylocation=new VertexLocation(new HexLocation(location.getInt("x"), location.getInt("y")),
				dir);
			mylocation.setHassettlement(true);
			Index myindex=new Index(obj.getInt("owner"));
			Settlement settle1 = new Settlement(new HexLocation(location.getInt("x"), location.getInt("y")),
					mylocation, myindex);
			Hex h = currentgame.getMymap().getHexes().get(settle1.getHexLocation());
			//h.addSettlement(settle1); // yeah we don't want this
			try {
				h.buildSettlement(mylocation,myindex);
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentgame.getMymap().getSettlements().add(settle1);
			Index owner = new Index(obj.getInt("owner"));
			System.out.println("I Successfully get my owner with number "+owner.getNumber());
			settle1.setOwner(owner);
			mylocation.setSettlement(settle1);
			currentgame.getMyplayers().get(owner).addToSettlements(settle1);
			// Alex you need to do something that's not this or maybe inialize it or something
		}
		JSONArray cities = map.getJSONArray("cities");
		for (int i = 0; i < cities.length(); i++)
		{
			JSONObject obj = cities.getJSONObject(i);
			//System.out.println(obj);
			int owner_convertToIndex = obj.getInt("owner");
			Index owner = new Index(owner_convertToIndex);
			JSONObject location = obj.getJSONObject("location");
			VertexDirection dir = convertToVertexDirection(location.getString("direction"));
			HexLocation loc = new HexLocation(location.getInt("x"), location.getInt("y"));
			assert(dir != null);
			VertexLocation vertexLoc = new VertexLocation(loc, dir);
			City city1 = new City(loc, vertexLoc, owner);
			vertexLoc.setHascity(true);
			Hex h = currentgame.getMymap().getHexes().get(city1.getHexLocation());
			h.getCities().add(city1);
			currentgame.getMymap().getCities().add(city1);
			Index owner2 = new Index(obj.getInt("owner"));
			city1.setOwner(owner2);
			vertexLoc.setCity(city1);
			//currentgame.getMyplayers().get(owner).addToCities(city1);
			// Alex you need to do something that's not this or maybe inialize it or something
		}
		currentgame.getMymap().setRadius(map.getInt("radius"));
		JSONObject robber = map.getJSONObject("robber");
		currentgame.myrobber.setLocation(new HexLocation(robber.getInt("x"), robber.getInt("y")));
	}

	private void loadPlayers(JSONArray players) throws JSONException
	{
		for (int i = 0; i < players.length(); i++)
		{
			if(!players.isNull(i))
			{
				JSONObject obj = players.getJSONObject(i);
				System.out.println(obj);
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
				currentgame.addPlayer(newPlayer);
			}
			//System.out.println("I add a player with name " + newPlayer.getName());
		}
	}

	private void loadTradeOffer(JSONObject tradeOffer) throws JSONException
	{
		currentgame.getMytradeoffer().setSender(tradeOffer.getInt("sender"));
		currentgame.getMytradeoffer().setReceiver(tradeOffer.getInt("receiver"));
		currentgame.getMytradeoffer();
		JSONObject offer = tradeOffer.getJSONObject("offer");
		currentgame.getMytradeoffer().getMylist().setBrick(offer.getInt("brick"));
		currentgame.getMytradeoffer().getMylist().setSheep(offer.getInt("sheep"));
		currentgame.getMytradeoffer().getMylist().setOre(offer.getInt("ore"));
		currentgame.getMytradeoffer().getMylist().setWheat(offer.getInt("wheat"));
	}

	private void loadTurnTracker(JSONObject turnTracker) throws JSONException
	{
		Index index = new Index(turnTracker.getInt("currentTurn"));
		System.out.println("MY TURN IS THIS" +index.getNumber());
		currentgame.getModel().getTurntracker().setCurrentTurn(index,
				currentgame.getMyplayers());
		currentgame.setCurrentPlayer(currentgame.getMyplayers().get(index));
		if(currentgame.getMyplayers().size()==4) {// Should stop caring if first player logging in isn't the first to start the game.
			currentgame.getMyplayers().get(index).setCurrentPlayer(true);
		}
		TurnStatus status = convertStringToTurnStatus(turnTracker.getString("status"));
		assert(status != null);
		currentgame.getModel().getTurntracker().setStatus(status);
		// actual player who has the longest road
		currentgame.getModel().getTurntracker().setLongestRoad(new Index(turnTracker.getInt("longestRoad")));
		currentgame.getModel().getTurntracker().setLargestArmy(new Index(turnTracker.getInt("largestArmy")));
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


	public Chat getMychat() 
	{
		return currentgame.getMychat();
	}

	public void setMychat(Chat mychat) {
		currentgame.setMychat(mychat);
	}

	public CatanMap getMymap()
	{
		return currentgame.getMymap();
	}

	public void setMymap(CatanMap mymap) {
		currentgame.setMymap(mymap);
	}
	
	public void addPlayer(Player player)
	{
		if(canCreatePlayer(player))
		{
			currentgame.getMyplayers().put(player.getPlayerID(), player);
		}
	}

	public boolean canCreatePlayer(Player newplayer)
	{
		return currentgame.canCreatePlayer(newplayer);
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
		return currentgame.canStartGame();
	}
	/**
	 * a function that starts the game nothing too fancy.   
	 */
	public void startGame()
	{
		currentgame.startGame();
	}
	public void clear()
	{
		currentgame.clear();
	}

	public Model getModel()
	{
		return currentgame.getModel();
	}

	public void setModel(Model newModel)
	{
		currentgame.setModel(newModel);
		notifyAll();
	}

	public void setLocalPlayer(Player player)
	{
		localplayer = player;		
	}
	
	public Player getLocalPlayer()
	{
		return localplayer;
	}

	public IServer getServer()
	{
		return server;
	}
	public Object listAI()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
