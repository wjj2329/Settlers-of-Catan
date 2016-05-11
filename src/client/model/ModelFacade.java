package client.model;

import java.util.ArrayList;

import org.json.JSONObject;

import shared.chat.Chat;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.player.Player;

/**
 * 
 * Facade for the Model. Every object accessing the model classes should do so through here 
 *
 */
public class ModelFacade
{
	public static CatanGame singleton=new CatanGame();
	public ModelFacade() 
	{
		
	}

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
	
	public JSONObject serializeModel()
	{
		return singleton.serializeModel();
	}

		/**
		 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and
		 * puts it into the model.
		 */
	public void updateFromJSON(JSONObject myobject)
	{
		singleton.updateFromJSON(myobject);
	}

	
}
