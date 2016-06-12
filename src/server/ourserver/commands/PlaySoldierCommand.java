package server.ourserver.commands;

import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.locations.HexLocation;

import java.io.IOException;

/**
 * The PlaySoldierCommand class
 */
public class PlaySoldierCommand implements ICommand 
{
	private HexLocation location;
	private int playerRobbing;
	private int playerBeingRobbed;
	private int gameid;
	
	public PlaySoldierCommand(HexLocation location, int playerRobbing, int playerBeingRobbed, int gameid)
	{
		this.location = location;
		this.playerRobbing = playerRobbing;
		this.playerBeingRobbed = playerBeingRobbed;
		this.gameid = gameid;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," + TextDBGameManagerDAO.commandNumber+":"+"{" +
				"\"type\": \"PlaySoldierCommand\"" +
				", \"x\":" +"\""+ location.getX() +"\""+
				", \"y\":"+"\""+location.getY()+"\""+
				", \"playerRobbing\":" + playerRobbing +
				", \"playerBeingRobbed\":" + playerBeingRobbed +
				", \"gameid\":" + gameid +
				"}";
	}

	/**
	 * Executes the task:
	 * 	player moves robber to given hexlocation, robs other player(if any) and they have
	 *  resource cards and "largest army" is given to play who has PLAYED most soldier cards
	 *  no other dev cards are played during the turn soldier is played
	 */
	@Override
	public Object execute() throws IOException, JSONException {
		ServerFacade.getInstance().playSoldier(location, playerRobbing, playerBeingRobbed, gameid);
		return null;
	}

	@Override
	public String toJSON() {
		return "{" +
				"\"type\": \"PlaySoldierCommand\"" +
				", \"x\":" +"\""+ location.getX() +"\""+
				", \"y\":"+"\""+location.getY()+"\""+
				", \"playerRobbing\":" + playerRobbing +
				", \"playerBeingRobbed\":" + playerBeingRobbed +
				", \"gameid\":" + gameid +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;

	}

}
