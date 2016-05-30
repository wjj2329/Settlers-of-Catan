package server.ourserver.commands;

import server.ourserver.ServerFacade;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * The BuildSettlementCommand class
 */
public class BuildSettlementCommand implements ICommand {

	
	/**
	 * Executes the task
	 * 	player builds a settlement at the vertex location 
	 */
	@Override
	public Object execute()
	{

		// TODO Auto-generated method stub
		return null;
	}

	public void buildsettlement(int playerIndex, HexLocation location, VertexLocation vertex, boolean free, int gameid)
	{

	}

}
