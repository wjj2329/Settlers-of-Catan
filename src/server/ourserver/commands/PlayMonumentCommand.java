package server.ourserver.commands;

import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.game.CatanGame;

import java.io.FileNotFoundException;

/**
 * The PlayMonumentCommand class
 */
public class PlayMonumentCommand implements ICommand {

	private int playerindex;
	private int gameID;

	public PlayMonumentCommand(int playerindex, int gameID)
	{
		this.playerindex = playerindex;
		this.gameID = gameID;
	}

	/**
	 * Executes the task:
	 * 	player plays monument card have enough to reach 10 victory points to win the game.
	 */
	@Override
	public Object execute() throws FileNotFoundException, JSONException {
		ServerFacade.getInstance().playMonument(playerindex, gameID);
		return null;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," +  TextDBGameManagerDAO.commandNumber+":"+"{" +
				"\"type\": \"PlayMonumentCommand\"" +
				"\"playerindex\":" + playerindex +
				", \"gameID\":" + gameID +
				"}";
	}

	@Override
	public String toJSON() {
		return "{" +
				"\"type\": \"PlayMonumentCommand\"" +
				"\"playerindex\":" + playerindex +
				", \"gameID\":" + gameID +
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
