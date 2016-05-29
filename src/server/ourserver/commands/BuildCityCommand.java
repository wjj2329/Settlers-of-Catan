package server.ourserver.commands;

import org.json.JSONObject;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.map.vertexobject.City;
import shared.game.player.Player;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * The BuildCityCommand class
 */
public class BuildCityCommand implements ICommand {

	/**
	 * Executes the task
	 * 	the player building a city in vertex location where there is a settlemetn
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public void buildCityCommand(int playerIndex, HexLocation location, VertexLocation vertex)
	{
		CatanGame currentgame=new CatanGame();//this won't work I need a current game some how.  Will wait till that is implemented.  will swap this variable with that
		Index owner = new Index(playerIndex);
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerIndex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		Index owner2=null;
		for (Player p : currentgame.getMyplayers().values())
		{
			if (p.getPlayerIndex().equals(playerIndex))
			{
				owner2 = p.getPlayerID();
			}
		}
		ResourceList newlist=playertoupdate.getResources();
		newlist.setSheep(newlist.getOre()-3);
		newlist.setWheat(newlist.getWheat()-2);
		playertoupdate.setResources(newlist);
		City city1 = new City(location, vertex, owner);
		vertex.setHascity(true);
		Hex h = currentgame.getMymap().getHexes().get(city1.getHexLocation());
		h.getCities().add(city1);
		currentgame.getMymap().getCities().add(city1);
		city1.setOwner(owner2);
		vertex.setCity(city1);
		currentgame.getMyplayers().get(owner2).addToCities(city1);
		currentgame.getMyplayers().get(owner2).setNumCitiesRemaining(currentgame.getMyplayers().get(owner2).getNumCitiesRemaining()-1);
	}

}
