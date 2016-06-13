package server.ourserver.commands;

import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.game.CatanGame;

import java.io.FileNotFoundException;

/**
 * The PlayYearOfPlentyCommand class
 */
public class PlayYearOfPlentyCommand implements ICommand {

	
	private int gameID;
	private String resource2;
	private String resource1;
	private int playerindex;

	public PlayYearOfPlentyCommand(int playerindex, String resource1,
			String resource2, int gameID)
	{
		this.playerindex = playerindex;
		this.resource1 = resource1;
		this.resource2 = resource2;
		this.gameID = gameID;
	}

	/**
	 * Executes the task:
	 * 	when player plays this they are given two specified resources if the bank didn't 
	 * 	run out of it (if it did that would be sad :'C ).
	 */
	@Override
	public Object execute() throws FileNotFoundException, JSONException
	{
		ServerFacade.getInstance().playYearOfPlenty(playerindex,resource1,resource2,gameID);
		return null;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," +"\""+ TextDBGameManagerDAO.commandNumber+"\""+":"+"{" +
				"\"type\": \"PlayYearOfPlentyCommand\"" +
				", \"gameID\":" + gameID +
				", \"resource2\":'" +"\""+ resource2 + '\"' +
				", \"resource1\":" +"\""+ resource1 + '\"' +
				", \"playerindex\":" + playerindex +
				"}";
	}

	@Override
	public String toJSON() {
		return "{" +
				"\"type\": \"PlayYearOfPlentyCommand\"" +
				", \"gameID\":" + gameID +
				", \"resource2\":'" +"\""+ resource2 + '\"' +
				", \"resource1\":" +"\""+ resource1 + '\"' +
				", \"playerindex\":" + playerindex +
				"}";
	}

	@Override
	public int getGameid() {
		return gameID;

	}

	@Override
	public Object executeversion2(CatanGame game) {
		return null;
	}
}
