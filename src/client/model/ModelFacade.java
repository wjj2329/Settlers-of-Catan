package client.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import shared.chat.Chat;
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
		for(int i=0; i<mymessages.messages().size(); i++)
		{
			JSONObject messageandstring=new JSONObject();
			messageandstring.put("message:",mymessages.messages().get(i).getMessage());
			messageandstring.put("source:",mymessages.messages().get(i).getSource());
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
	}

	private void loadBank(JSONObject bank) throws JSONException
	{
		int bankBrickQuantity = bank.getInt("brick");
		int bankOreQuantity = bank.getInt("ore");
		int bankSheepQuantity = bank.getInt("sheep");
		int bankWheatQuantity = bank.getInt("wheat");
		int bankWoodQuantity = bank.getInt("wood");
	}

	private void loadChat(JSONObject chat) throws JSONException
	{
		JSONArray chatLines = chat.getJSONArray("lines");
		for (int i = 0; i < chatLines.length(); i++)
		{
			JSONObject obj = chatLines.getJSONObject(i);
			if (obj.has("message"))
			{
				String chatMessage = obj.getString("message");
			}
			if (obj.has("source"))
			{
				String chatSource = obj.getString("source");
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

	
}
