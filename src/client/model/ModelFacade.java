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

		JSONObject roads=new JSONObject();
		//roads
		for(HexLocation loc:mymap.keySet())
		{

		}
		JSONObject settlements=new JSONObject();
		//settlements
		for(HexLocation loc:mymap.keySet())
		{
			//settlements.put("owner", mymap.get(loc).)
		}

		return myobject;
	}

		/**
		 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and
		 * puts it into the model.
		 */
	public void updateFromJSON(JSONObject myobject)
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
