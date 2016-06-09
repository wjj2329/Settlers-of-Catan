package server.ourserver.commands;

import server.ourserver.ServerFacade;

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
	public Object execute() 
	{
		ServerFacade.getInstance().playYearOfPlenty(playerindex,resource1,resource2,gameID);
		return null;
	}

	@Override
	public String toString() {
		return "PlayYearOfPlentyCommand{" +
				"gameID=" + gameID +
				", resource2='" + resource2 + '\'' +
				", resource1='" + resource1 + '\'' +
				", playerindex=" + playerindex +
				'}';
	}
}
