package server.ourserver.commands;

import client.model.TurnStatus;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.chat.GameHistoryLine;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.RoadPiece;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import java.io.FileNotFoundException;
import java.util.Set;

import static client.model.ModelFacade.facadeCurrentGame;

/**
 * The BuildRoadCommand class
 */
public class BuildRoadCommand implements ICommand {

	/**
	 * Executes the task
	 * 	player building a road at the edge location if its open, not on water, 
	 *  and connected to another road owned by the player.
	 */
	int playerIndex;
	HexLocation location;
	EdgeLocation edge;
	boolean free;
	int gameid;
	private static int turnstogo=1;
	public BuildRoadCommand(int playerIndex, HexLocation location, EdgeLocation edge, boolean free, int gameid)
	{
		this.playerIndex=playerIndex;
		this.location=location;
		this.edge=edge;
		this.free=free;
		this.gameid=gameid;
	}


	@Override
	public Object execute() throws FileNotFoundException, JSONException {
//System.out.println("I CALL THE BUILD ROAD COMMAND RIGHT NOW THIS SECOND");
		CatanGame currentgame= null;
		//try {
			currentgame = ServerFacade.getInstance().getGameByID(gameid);
		/*} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		Index playerID = null;
		for (Player p : currentgame.getMyplayers().values())
		{
			if (p.getPlayerIndex().equals(new Index(playerIndex)))
			{
				playerID = p.getPlayerID();
			}
		}
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerIndex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		if(!free)
		{
			//System.out.println("THIS ISN'T FREE");
			ResourceList newlist = playertoupdate.getResources();
			newlist.setBrick(newlist.getBrick() - 1);
			newlist.setWood(newlist.getWood() - 1);
			playertoupdate.setResources(newlist);//not sure if this is necessary or not.
			//updates banks crap now

			ResourceList mybankslist=currentgame.mybank.getCardslist();
			mybankslist.setBrick(mybankslist.getBrick()+1);
			mybankslist.setWood(mybankslist.getWood()+1);
			currentgame.mybank.setResourceCardslist(mybankslist);
		}
		edge.setHasRoad(true);
		Hex hex = currentgame.getMymap().getHexes().get(location);
		Hex adjacent = computeAdjacentHex(hex, edge,currentgame);
		EdgeLocation adjLoc = computeOppositeEdge(edge, adjacent);
		RoadPiece r1 = hex.buildRoad(edge, playertoupdate.getPlayerIndex());
		RoadPiece r2 = adjacent.buildRoad(adjLoc, playertoupdate.getPlayerIndex());
		edge.setRoadPiece(r1);
		edge.setHasRoad(true);
		adjLoc.setRoadPiece(r2);
		adjLoc.setHasRoad(true);
		//System.out.println(" I SET MY PLAYER "+currentgame.getMyplayers().get(playerID).getName());
		playertoupdate.addToRoadPieces(r1);
		//System.out.println("MY PLAYER NOW HAS "+playertoupdate.getRoadPieces().size()+"Number of roads");
		playertoupdate.setNumRoadPiecesRemaining(currentgame.getMyplayers().get(playerID).getNumRoadPiecesRemaining()-1);
		//System.out.println(" HIS ROAD PEACES ARE NOW "+currentgame.getMyplayers().get(playerID).getNumRoadPiecesRemaining());
		currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " builds a Road",playertoupdate.getName()));
		if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.FIRSTROUND))
		{
			turnstogo++;
			//System.out.println("I INCREASE THE TURNS TO GO STATIC MEMBER to "+turnstogo);
		}
		if(playerIndex==3&&currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.FIRSTROUND))
		{
			currentgame.getModel().getTurntracker().setStatus(TurnStatus.SECONDROUND);
			turnstogo++;
		}
		//Player with largest road might be causing some issues caution is required.
		if(playertoupdate.getNumRoadPiecesRemaining()<=10)
		{
			Index playerindexcurrentlywithlongestroad=currentgame.getModel().getTurntracker().getLongestRoad();
			if(playerindexcurrentlywithlongestroad.getNumber()==-1)
			{
				currentgame.getModel().getTurntracker().setLongestRoad(playertoupdate.getPlayerIndex());
				playertoupdate.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()+2);
				return null;
			}
			Player playerwithlongestroad=null;
			for(Player player:currentgame.getMyplayers().values())
			{
				if(playerindexcurrentlywithlongestroad.getNumber()==player.getPlayerIndex().getNumber()) {
					playerwithlongestroad = player;
				}
			}
			if(playertoupdate.getNumRoadPiecesRemaining()<playerwithlongestroad.getNumRoadPiecesRemaining())
			{
				playertoupdate.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()+2);
				playerwithlongestroad.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()-2);
				currentgame.getModel().getTurntracker().setLongestRoad(playertoupdate.getPlayerIndex());
			}

		}		return null;
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
	private Hex computeAdjacentHex(Hex initial, EdgeLocation edge,CatanGame mycurrentgame)
	{
		Hex adjacent = null;
		switch (edge.getDir())
		{
			case NorthWest:
				HexLocation loc1 = new HexLocation(initial.getLocation().getX() - 1, initial.getLocation().getY());
				adjacent = mycurrentgame.getMymap().getHexes().get(loc1);
				break;
			case North:
				HexLocation loc2 = new HexLocation(initial.getLocation().getX(), initial.getLocation().getY() - 1);
				adjacent = mycurrentgame.getMymap().getHexes().get(loc2);
				break;
			case NorthEast:
				HexLocation loc3 = new HexLocation(initial.getLocation().getX() + 1, initial.getLocation().getY() - 1);
				adjacent = mycurrentgame.getMymap().getHexes().get(loc3);
				break;
			case SouthEast:
				HexLocation loc4 = new HexLocation(initial.getLocation().getX() + 1, initial.getLocation().getY());
				adjacent = mycurrentgame.getMymap().getHexes().get(loc4);
				break;
			case South:
				HexLocation loc5 = new HexLocation(initial.getLocation().getX(), initial.getLocation().getY() + 1);
				adjacent = mycurrentgame.getMymap().getHexes().get(loc5);
				break;
			case SouthWest:
				HexLocation loc6 = new HexLocation(initial.getLocation().getX() - 1, initial.getLocation().getY() + 1);
				adjacent = mycurrentgame.getMymap().getHexes().get(loc6);
				break;
			default:
				break;
			//assert false;
		}
		//assert(adjacent != null);
		return adjacent;
	}


	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;

		return "," + TextDBGameManagerDAO.commandNumber+":"+"{"+
				"\"type\":\"BuildRoadCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"x\":" +"\""+ location.getX()+"\"" +
				", \"y\":"+"\""+location.getY()+"\""+
				", \"edge\":" +"\""+ edge.getDir() +"\""+
				", \"free\":" + free +
				", \"gameid\":" + gameid +
				"}";
	}


	@Override
	public String toJSON() {
		return "{"+
				"\"type\":\"BuildRoadCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"x\":" +"\""+ location.getX()+"\"" +
				", \"y\":"+"\""+location.getY()+"\""+
				", \"edge\":" +"\""+ edge.getDir() +"\""+
				", \"free\":" + free +
				", \"gameid\":" + gameid +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;

	}

	@Override
	public Object executeversion2(CatanGame game)
	{

//System.out.println("I CALL THE BUILD ROAD COMMAND RIGHT NOW THIS SECOND");
		CatanGame currentgame= null;
		//try {
		currentgame = game;
		/*} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		Index playerID = null;
		for (Player p : currentgame.getMyplayers().values())
		{
			if (p.getPlayerIndex().equals(new Index(playerIndex)))
			{
				playerID = p.getPlayerID();
			}
		}
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerIndex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		if(!free)
		{
			//System.out.println("THIS ISN'T FREE");
			ResourceList newlist = playertoupdate.getResources();
			newlist.setBrick(newlist.getBrick() - 1);
			newlist.setWood(newlist.getWood() - 1);
			playertoupdate.setResources(newlist);//not sure if this is necessary or not.
			//updates banks crap now

			ResourceList mybankslist=currentgame.mybank.getCardslist();
			mybankslist.setBrick(mybankslist.getBrick()+1);
			mybankslist.setWood(mybankslist.getWood()+1);
			currentgame.mybank.setResourceCardslist(mybankslist);
		}
		edge.setHasRoad(true);
		Hex hex = currentgame.getMymap().getHexes().get(location);
		Hex adjacent = computeAdjacentHex(hex, edge,currentgame);
		EdgeLocation adjLoc = computeOppositeEdge(edge, adjacent);
		RoadPiece r1 = hex.buildRoad(edge, playertoupdate.getPlayerIndex());
		RoadPiece r2 = adjacent.buildRoad(adjLoc, playertoupdate.getPlayerIndex());
		edge.setRoadPiece(r1);
		edge.setHasRoad(true);
		adjLoc.setRoadPiece(r2);
		adjLoc.setHasRoad(true);
		//System.out.println(" I SET MY PLAYER "+currentgame.getMyplayers().get(playerID).getName());
		playertoupdate.addToRoadPieces(r1);
		//System.out.println("MY PLAYER NOW HAS "+playertoupdate.getRoadPieces().size()+"Number of roads");
		playertoupdate.setNumRoadPiecesRemaining(currentgame.getMyplayers().get(playerID).getNumRoadPiecesRemaining()-1);
		//System.out.println(" HIS ROAD PEACES ARE NOW "+currentgame.getMyplayers().get(playerID).getNumRoadPiecesRemaining());
		currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " builds a Road",playertoupdate.getName()));
		if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.FIRSTROUND))
		{
			turnstogo++;
			//System.out.println("I INCREASE THE TURNS TO GO STATIC MEMBER to "+turnstogo);
		}
		if(playerIndex==3&&currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.FIRSTROUND))
		{
			currentgame.getModel().getTurntracker().setStatus(TurnStatus.SECONDROUND);
			turnstogo++;
		}
		if(playertoupdate.getRoadPieces().size()>=5)
		{
			Index playerindexcurrentlywithlongestroad=currentgame.getModel().getTurntracker().getLongestRoad();
			if(playerindexcurrentlywithlongestroad.getNumber()==-1)
			{
				currentgame.getModel().getTurntracker().setLongestRoad(playertoupdate.getPlayerIndex());
				playertoupdate.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()+2);
				return null;
			}
			Player playerwithlongestroad=null;
			for(Player player:currentgame.getMyplayers().values())
			{
				if(playerindexcurrentlywithlongestroad.getNumber()==player.getPlayerIndex().getNumber()) {
					playerwithlongestroad = player;
				}
			}
			if(playertoupdate.getRoadPieces().size()>playerwithlongestroad.getRoadPieces().size())
			{
				playertoupdate.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()+2);
				playerwithlongestroad.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()-2);
				currentgame.getModel().getTurntracker().setLongestRoad(playertoupdate.getPlayerIndex());
			}

		}		return null;
	}
}
