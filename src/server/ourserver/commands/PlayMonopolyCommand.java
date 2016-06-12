package server.ourserver.commands;

import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;

import java.io.FileNotFoundException;


/**
 * The PlayMonopolyCommand class
 */
public class PlayMonopolyCommand implements ICommand {

	private int playerindex;
	private String resource;
	private int gameID;

	public PlayMonopolyCommand(int playerindex, String resource, int gameID)
	{
		this.playerindex = playerindex;
		this.resource = resource;
		this.gameID = gameID;
	}

	/**
	 * Executes the task:
	 * 	plays the monopoly card, other players give all of their resource cards 
	 * 	of the specified type. 
	 */
	@Override
	public Object execute() throws FileNotFoundException, JSONException
	{
		ServerFacade.getInstance().playMonopoly(playerindex, resource, gameID);
		return null;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," + TextDBGameManagerDAO.commandNumber+":"+"{" +
				", \"type\":\"PlayMonopolyCommand\"" +
				", \"playerindex\":" + playerindex +
				", \"resource\":" +"\""+ resource + '\"' +
				", \"gameID\":" + gameID +
				"}}";
	}

	@Override
	public String toJSON() {
		return "{" +
				", \"type\":\"PlayMonopolyCommand\"" +
				", \"playerindex\":" + playerindex +
				", \"resource\":" +"\""+ resource + '\"' +
				", \"gameID\":" + gameID +
				"}";
	}

	@Override
	public int getGameid() {
		return gameID;

	}
}
