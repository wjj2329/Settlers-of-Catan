package client.model;

import client.main.Catan;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import shared.chat.Chat;
import shared.chat.ChatLine;
import shared.chat.ChatMessages;
import shared.chat.GameHistory;
import shared.game.Bank;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.player.Player;
import shared.locations.HexLocation;

import java.util.ArrayList;
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
	public ModelFacade()
	{
		singleton = CatanGame.singleton;
	}

	public JSONObject serializeModel() throws JSONException
	{
		JSONObject myobject=new JSONObject();

		//bank code
		JSONObject bank=new JSONObject();
		bank.put("brick:", Bank.getSingleton().getCardslist().getBrick());
		bank.put("ore:", Bank.getSingleton().getCardslist().getOre());
		bank.put("sheep:", Bank.getSingleton().getCardslist().getSheep());
		bank.put("wheat:", Bank.getSingleton().getCardslist().getWheat());
		bank.put("wood:", Bank.getSingleton().getCardslist().getWood());
		myobject.put("bank:", bank);

		//chat code
		JSONObject chat=new JSONObject();
		JSONArray lines=new JSONArray();
		Chat mychat=CatanGame.singleton.getMychat();
		ChatMessages mymessages=mychat.getChatMessages();
		for(int i=0; i<mymessages.getMessages().size(); i++)
		{
			JSONObject messageandstring=new JSONObject();
			messageandstring.put("message:",mymessages.getMessages().get(i).getMessage());
			messageandstring.put("source:",mymessages.getMessages().get(i).getSource());
			lines.put(messageandstring);
		}
		chat.put("lines:",lines);
		myobject.put("chat:",chat);

		//logcode
		JSONObject log=new JSONObject();
		JSONArray lines2=new JSONArray();
		GameHistory myhistory=mychat.getGameHistory();
		for(int i=0; i<myhistory.getLines().size();i++)
		{
			JSONObject messageandstring=new JSONObject();
			messageandstring.put("message:",myhistory.getLines().get(i).getLine());
			messageandstring.put("source:",myhistory.getLines().get(i).getSource());
			lines2.put(messageandstring);
		}
		log.put("lines:",lines2);
		myobject.put("chat:", log);

		//map
		//hexes
		JSONObject map=new JSONObject();
		JSONObject hexes=new JSONObject();
		Map<HexLocation, Hex> mymap=getMymap().getHexes();
		for(HexLocation loc:mymap.keySet())
		{
			JSONObject locationarray=new JSONObject();
			locationarray.put("x:",loc.getX());
			locationarray.put("y:", loc.getY());
			hexes.put("location:", locationarray);
			hexes.put("resource:", mymap.get(loc).getResourcetype());
			hexes.put("number:", mymap.get(loc).getResourcenumber());
		}
		map.put("hexes:",hexes);
		JSONObject ports=new JSONObject();

		//ports
		for(HexLocation loc:mymap.keySet())
		{
			ports.put("resource", mymap.get(loc).getPortType());
			JSONObject location=new JSONObject();
			location.put("x:",loc.getX());
			location.put("y:", loc.getX());
			ports.put("location:",location);
			ports.put("direction:", mymap.get(loc).getPortLocation());
		}
		map.put("ports:",ports);

		//roads
		JSONObject roads=new JSONObject();
		for(HexLocation loc:mymap.keySet())
		{
			for(int i=0; i<mymap.get(loc).getRoads().size(); i++)
			{
				roads.put("owner:", mymap.get(loc).getRoads().get(i));
				JSONObject location=new JSONObject();
				location.put("x:",loc.getX());
				location.put("y:",loc.getY());
				roads.put("location:",location);
				roads.put("direction:",mymap.get(loc).getRoads().get(i).getLocation());
			}
		}
		myobject.put("roads:",roads);

		//settlements
		JSONObject settlements=new JSONObject();
		for(HexLocation loc:mymap.keySet())
		{
			for(int i=0; i<mymap.get(loc).getSettlementlist().size(); i++)
			{
				settlements.put("owner:", mymap.get(loc).getSettlementlist().get(i).getOwner().getNumber());
				JSONObject location=new JSONObject();
				location.put("x:",loc.getX());
				location.put("y:",loc.getY());
				settlements.put("location:", location);
				settlements.put("direction:", mymap.get(loc).getSettlementlist().get(i).getVertexLocation());
			}
		}
		myobject.put("settlements:",settlements);

		//cities
		JSONObject cities=new JSONObject();
		for(HexLocation loc:mymap.keySet())
		{
			for(int i=0; i<mymap.get(loc).getCities().size();i++)
			{
				cities.put("owner:", mymap.get(loc).getCities().get(i).getOwner().getNumber());
				JSONObject location=new JSONObject();
				location.put("x:",loc.getX());
				location.put("y:",loc.getY());
				cities.put("location:", location);
				cities.put("direction:", mymap.get(loc).getSettlementlist().get(i).getVertexLocation());
			}
		}

		//players
		ArrayList<Player>myplayers=CatanGame.singleton.getMyplayers();
		JSONObject players=new JSONObject();
		for(int i=0; i<myplayers.size(); i++)
		{
			players.put("cities:", myplayers.get(i).getCities().size());
			players.put("color:", myplayers.get(i).getColor().toString());
			players.put("discarded:", myplayers.get(i).getIsDiscarded());
			players.put("monuments:", myplayers.get(i).getNumMonuments());
			players.put("name:", myplayers.get(i).getName());
			JSONObject newDevCards=new JSONObject();
			newDevCards.put("monopoly:", myplayers.get(i).getNewDevCards().getMonopoly());
			newDevCards.put("monument:", myplayers.get(i).getNewDevCards().getMonument());
			newDevCards.put("roadBuilding:", myplayers.get(i).getNewDevCards().getRoadBuilding());
			newDevCards.put("soldier:", myplayers.get(i).getNewDevCards().getSoldier());
			newDevCards.put("yearOfPlenty:", myplayers.get(i).getNewDevCards().getYearOfPlenty());
			players.put("newDevCards:", newDevCards);
			JSONObject oldDevCards=new JSONObject();
			oldDevCards.put("monopoly:", myplayers.get(i).getNewDevCards().getMonopoly());
			oldDevCards.put("monument:", myplayers.get(i).getNewDevCards().getMonument());
			oldDevCards.put("roadBuilding:", myplayers.get(i).getNewDevCards().getRoadBuilding());
			oldDevCards.put("soldier:", myplayers.get(i).getNewDevCards().getSoldier());
			oldDevCards.put("yearOfPlenty:", myplayers.get(i).getNewDevCards().getYearOfPlenty());
			players.put("oldDevCards:", oldDevCards);
			players.put("playerIndex:", myplayers.get(i).getPlayerIndex().getNumber());
			players.put("playedDevCard:", myplayers.get(i).getplayedDevCard());
			players.put("playerID:", myplayers.get(i).getPlayerID().getNumber());
			JSONObject resources=new JSONObject();
			resources.put("brick:", myplayers.get(i).getResources().getBrick());
			resources.put("ore:", myplayers.get(i).getResources().getOre());
			resources.put("sheep:", myplayers.get(i).getResources().getSheep());
			resources.put("wheat:", myplayers.get(i).getResources().getWheat());
			resources.put("wood:", myplayers.get(i).getResources().getWood());
			players.put("resources:", resources);
			players.put("roads:",myplayers.get(i).getRoadPieces().size());
			players.put("settlements:", myplayers.get(i).getSettlements().size());
			players.put("soldiers:", myplayers.get(i).getArmySize());
			players.put("victoryPoints", myplayers.get(i).getNumVictoryPoints());
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
		myobject.put("winner", CatanGame.singleton.getWinner());

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
			if (obj.has("message"))
			{
				CatanGame.singleton.getMychat().getChatMessages().getMessages().add(new
						ChatLine(obj.getString("message"), SOURCE));
				// not sure what source does; we will have to see - for now it is just a default string
			}
			if (obj.has("source"))
			{
				CatanGame.singleton.getMychat().getChatMessages().getMessages().add(new
				ChatLine(obj.getString("message"), SOURCE));
			}
		}
	}

	private void loadLog(JSONObject log) throws JSONException
	{
		JSONArray logLines = log.getJSONArray("lines");
		for (int i = 0; i < logLines.length(); i++)
		{
			JSONObject obj = logLines.getJSONObject(i);
			if (obj.has("message"))
			{
				//CatanGame.singleton;
				String logMessage = obj.getString("message");
			}
			if (obj.has("source"))
			{
				String logSource = obj.getString("source");
			}
		}
	}

	private void loadMap(JSONObject map) throws JSONException
	{
		JSONArray hexes = map.getJSONArray("hexes");
		for (int i = 0; i < hexes.length(); i++)
		{
			JSONObject obj = hexes.getJSONObject(i);
			JSONObject location = obj.getJSONObject("location");
			int x = location.getInt("x");
			int y = location.getInt("y");
			String resource = obj.getString("resource");
			int number = obj.getInt("number");
		}
		JSONArray ports = map.getJSONArray("ports");
		for (int i = 0; i < ports.length(); i++)
		{
			JSONObject obj = ports.getJSONObject(i);
			String resource = obj.getString("resource");
			JSONObject location = obj.getJSONObject("location");
			int x = location.getInt("x");
			int y = location.getInt("y");
			String direction = obj.getString("direction");
			int ratio = obj.getInt("ratio");
		}
		JSONArray roads = map.getJSONArray("roads");
		for (int i = 0; i < roads.length(); i++)
		{
			JSONObject obj = roads.getJSONObject(i);
			// Integer needs to be converted into index.
			int owner_convertToIndex = obj.getInt("owner");
			JSONObject location = obj.getJSONObject("location");
			int x = location.getInt("x");
			int y = location.getInt("y");
			String direction = obj.getString("direction");
		}
		JSONArray settlements = map.getJSONArray("settlements");
		for (int i = 0; i < settlements.length(); i++)
		{
			JSONObject obj = settlements.getJSONObject(i);
			int owner_convertToIndex = obj.getInt("owner");
			JSONObject location = obj.getJSONObject("location");
			int x = location.getInt("x");
			int y = location.getInt("y");
			String direction = obj.getString("direction");
		}
		JSONArray cities = map.getJSONArray("cities");
		for (int i = 0; i < cities.length(); i++)
		{
			JSONObject obj = cities.getJSONObject(i);
			int owner_convertToIndex = obj.getInt("owner");
			JSONObject location = obj.getJSONObject("location");
			int x = location.getInt("x");
			int y = location.getInt("y");
			String direction = obj.getString("direction");
		}
		int radius = map.getInt("radius");
		JSONObject robber = map.getJSONObject("robber");
		int x = robber.getInt("x");
		int y = robber.getInt("y");
	}

	private void loadPlayers(JSONArray players) throws JSONException
	{
		for (int i = 0; i < players.length(); i++)
		{
			JSONObject obj = players.getJSONObject(i);
			int cities = obj.getInt("cities");
			String color = obj.getString("color");
			boolean discarded = obj.getBoolean("discarded");
			int monuments = obj.getInt("monuments"); // so why the heck is this called "monuments"?
			String name = obj.getString("name");
			newDevCards(obj.getJSONObject("newDevCards"));
			oldDevCards(obj.getJSONObject("oldDevCards"));
			int playerIndex_convertToIndex = obj.getInt("playerIndex");
			boolean playedDevCard = obj.getBoolean("playedDevCard");
			int playerID = obj.getInt("playerID");
			resources(obj.getJSONObject("resources"));
			int roads = obj.getInt("roads");
			int settlements = obj.getInt("settlements");
			int soldiers = obj.getInt("soldiers");
			int victoryPoints = obj.getInt("victoryPoints");
		}
	}

	private void loadTradeOffer(JSONObject tradeOffer) throws JSONException
	{
		int sender = tradeOffer.getInt("sender");
		int receiver = tradeOffer.getInt("receiver");
		JSONObject offer = tradeOffer.getJSONObject("offer");
		int brick = offer.getInt("brick");
		int sheep = offer.getInt("sheep");
		int wood = offer.getInt("wood");
		int ore = offer.getInt("ore");
		int wheat = offer.getInt("wheat");
	}

	private void loadTurnTracker(JSONObject turnTracker) throws JSONException
	{
		int currentTurn_convertToIndex = turnTracker.getInt("currentTurn");
		String status = turnTracker.getString("status");
		// actual player who has the longest road
		int longestRoad_convertToIndex = turnTracker.getInt("longestRoad");
		int largestArmy_convertToIndex = turnTracker.getInt("largestArmy");
	}

	private void newDevCards(JSONObject newDevCards) throws JSONException
	{
		int monopoly = newDevCards.getInt("monopoly");
		int monument = newDevCards.getInt("monument");
		int roadBuilding = newDevCards.getInt("roadBuilding");
		int soldier = newDevCards.getInt("soldier");
		int yearOfPlenty = newDevCards.getInt("yearOfPlenty");
	}

	private void oldDevCards(JSONObject oldDevCards) throws JSONException
	{
		int monopoly = oldDevCards.getInt("monopoly");
		int monument = oldDevCards.getInt("monument");
		int roadBuilding = oldDevCards.getInt("roadBuilding");
		int soldier = oldDevCards.getInt("soldier");
		int yearOfPlenty = oldDevCards.getInt("yearOfPlenty");
	}

	private void resources(JSONObject resources) throws JSONException
	{
		int brick = resources.getInt("brick");
		int ore = resources.getInt("ore");
		int sheep = resources.getInt("sheep");
		int wheat = resources.getInt("wheat");
		int wood = resources.getInt("wood");
	}

		// TODO Auto-generated constructor stub
	public ArrayList<Player> getMyplayers()
	{
		return singleton.getMyplayers();
	}

	public void setMyplayers(ArrayList<Player> myplayers)
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
		if(canCreatePlayer(player)) {
			singleton.getMyplayers().add(player);
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

	private static final String SOURCE = "Default";
}
