package server.ourserver.commands;

import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
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

import java.io.FileNotFoundException;

/**
 * The BuildCityCommand class
 */
public class BuildCityCommand implements ICommand {

	/**
	 * Executes the task
	 * 	the player building a city in vertex location where there is a settlemetn
	 */
	int playerIndex;
	HexLocation location;
	VertexLocation vertex;
	int gameid;
	public BuildCityCommand(int playerIndex, HexLocation location, VertexLocation vertex, int gameid)
	{
		this.playerIndex=playerIndex;
		this.location=location;
		this.vertex=vertex;
		this.gameid=gameid;
	}
	@Override
	public Object execute() throws FileNotFoundException, JSONException {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return ","+ TextDBGameManagerDAO.commandNumber+":"+"{"+
				"\"type\":\"BuildCityCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"x\":"+"\"" + location.getX() +"\""+
				", \"y\":"+"\""+location.getY()+"\""+
				", \"vertex\":"+"\"" + vertex.getDir()+"\"" +
				", \"gameid\":" + gameid +
				"}";
	}
	@Override
	public String toJSON() {
		return "{"+
				"\"type\":\"BuildCityCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"x\":"+"\"" + location.getX() +"\""+
				", \"y\":"+"\""+location.getY()+"\""+
				", \"vertex\":"+"\"" + vertex.getDir()+"\"" +
				", \"gameid\":" + gameid +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;

	}
}
