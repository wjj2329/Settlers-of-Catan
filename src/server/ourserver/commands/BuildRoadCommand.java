package server.ourserver.commands;

import client.model.TurnStatus;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.RoadPiece;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

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
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
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
	private static int turnstogo=1;
	public void buildRoadincommand(int playerIndex, HexLocation location, EdgeLocation edge, boolean free, int gameid)
	{
		System.out.println("I CALL THE BUILD ROAD COMMAND RIGHT NOW THIS SECOND");
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
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
		if(!free) {
			ResourceList newlist = playertoupdate.getResources();
			newlist.setBrick(newlist.getBrick() - 1);
			newlist.setWood(newlist.getWood() - 1);
			playertoupdate.setResources(newlist);//not sure if this is necessary or not.
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
		System.out.println(" I SET MY PLAYER "+currentgame.getMyplayers().get(playerID).getName());
		playertoupdate.addToRoadPieces(r1);
		System.out.println("MY PLAYER NOW HAS "+playertoupdate.getRoadPieces().size()+"Number of roads");
		playertoupdate.setNumRoadPiecesRemaining(currentgame.getMyplayers().get(playerID).getNumRoadPiecesRemaining()-1);
		System.out.println(" HIS ROAD PEACES ARE NOW "+currentgame.getMyplayers().get(playerID).getNumRoadPiecesRemaining());
		if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.FIRSTROUND))
		{
			turnstogo++;
			System.out.println("I INCREASE THE TURNS TO GO STATIC MEMEBER to "+turnstogo);
		}
		if(turnstogo==5)
		{
			currentgame.getModel().getTurntracker().setStatus(TurnStatus.SECONDROUND);
			turnstogo=1;
			return;
		}
		if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND))
		{
			turnstogo++;
		}
		if(turnstogo==4)
		{
			currentgame.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);
		}

	}

}
