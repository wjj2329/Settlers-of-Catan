package server.ourserver.commands;

import server.ourserver.ServerFacade;


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
	public Object execute() 
	{
		ServerFacade.getInstance().playMonopoly(playerindex, resource, gameID);
		return null;
	}

	@Override
	public String toString() {
		return "PlayMonopolyCommand{" +
				"playerindex=" + playerindex +
				", resource='" + resource + '\'' +
				", gameID=" + gameID +
				'}';
	}
}
