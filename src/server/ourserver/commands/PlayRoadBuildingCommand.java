package server.ourserver.commands;

import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import java.io.IOException;

/**
 * The PlayRoadBuildingCommand class
 */
public class PlayRoadBuildingCommand implements ICommand 
{
	int playerindex;
	HexLocation hexLocation;
	EdgeLocation edgeDirectionFromString;
	boolean freebe;
	int gameID;
	
	public PlayRoadBuildingCommand(int playerindex, HexLocation hexLocation,
			EdgeLocation edgeDirectionFromString, boolean freebe, int gameID)
	{
		this.playerindex = playerindex;
		this.hexLocation = hexLocation;
		this.edgeDirectionFromString = edgeDirectionFromString;
		this.freebe = freebe;
		this.gameID = gameID;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," + TextDBGameManagerDAO.commandNumber+":"+"{" +
				"\"type\": \"playRoadBuildingCommand\"" +
				"\"playerindex\":" + playerindex +
				", \"x\":"+"\"" + hexLocation.getX() +"\""+
				", \"y\":"+"\""+hexLocation.getY()+"\""+
				", \"edgeDirectionFromString\":" +"\""+ edgeDirectionFromString.getDir()+"\"" +
				", \"freebe\":" + freebe +
				", \"gameID\":" + gameID +
				"}";
	}

	/**
	 * Executes the task:
	 * 	when player plays roadbuilding card they build 2 free roads on the edgelocations specified
	 */
	@Override
	public Object execute() throws IOException, JSONException {
		ServerFacade.getInstance().playRoadBuilding(playerindex, hexLocation, edgeDirectionFromString, freebe, gameID);
		return null;
	}

	@Override
	public String toJSON() {
		return "{" +
				"\"type\": \"playRoadBuildingCommand\"" +
				"\"playerindex\":" + playerindex +
				", \"x\":"+"\"" + hexLocation.getX() +"\""+
				", \"y\":"+"\""+hexLocation.getY()+"\""+
				", \"edgeDirectionFromString\":" +"\""+ edgeDirectionFromString.getDir()+"\"" +
				", \"freebe\":" + freebe +
				", \"gameID\":" + gameID +
				"}";
	}

	@Override
	public int getGameid() {
		return gameID;

	}

}
