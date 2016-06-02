package server.ourserver.commands;

import org.json.JSONObject;
import server.ourserver.ServerFacade;
import shared.chat.GameHistoryLine;
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

	public void buildCityCommand(int playerIndex, HexLocation location, VertexLocation vertex, int gameid)
	{
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
		Index owner = new Index(playerIndex);
		Player playertoupdate=null;
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
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
			if (p.getPlayerIndex().equals(new Index(playerIndex)))
			{
				owner2 = p.getPlayerID();
			}
		}

		//updates player
		ResourceList newlist=playertoupdate.getResources();
		newlist.setOre(newlist.getOre()-3);
		newlist.setWheat(newlist.getWheat()-2);
		playertoupdate.setResources(newlist);

		//updates bank
		ResourceList mybankslist=currentgame.mybank.getCardslist();
		mybankslist.setOre(mybankslist.getOre()+3);
		mybankslist.setWheat(mybankslist.getWheat()+2);
		currentgame.mybank.setResourceCardslist(mybankslist);

		City city1 = new City(location, vertex, playertoupdate.getPlayerIndex());
		vertex.setHascity(true);
		Hex h = currentgame.getMymap().getHexes().get(city1.getHexLocation());
		h.getCities().add(city1);
		h.buildCity(vertex,playertoupdate.getPlayerIndex());
		currentgame.getMymap().getCities().add(city1);
		city1.setOwner(playertoupdate.getPlayerIndex());
		vertex.setCity(city1);
		currentgame.getMyplayers().get(owner2).addToCities(city1);
		currentgame.getMyplayers().get(owner2).setNumCitiesRemaining(currentgame.getMyplayers().get(owner2).getNumCitiesRemaining()-1);
		playertoupdate.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()+1);
		currentgame.getMyplayers().get(owner2).setNumSettlementsRemaining(currentgame.getMyplayers().get(owner2).getNumSettlementsRemaining()+1);
		currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " builds a City",playertoupdate.getName()));

	}

}
