package server.ourserver.commands;

import server.ourserver.ServerFacade;

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
	public Object execute() {
		ServerFacade.getInstance().playMonument(playerindex, gameID);
		return null;
	}

	@Override
	public String toString() {
		return "PlayMonumentCommand{" +
				"playerindex=" + playerindex +
				", gameID=" + gameID +
				'}';
	}
}
