package server.ourserver.commands;

import server.ourserver.ServerFacade;

/**
 * The BuyDevCardCommand class
 */
public class BuyDevCardCommand implements ICommand 
{
	private int playerIndex;
	private int gameid;
	/**
	 * Executes the task 
	 * player buys a devcard if they have required resources
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		ServerFacade.getInstance().buyDevCard(playerIndex, gameid);
		return null;
	}
	
	public BuyDevCardCommand(int playerIndex, int gameid)
	{
		this.playerIndex = playerIndex;
		this.gameid = gameid;
	}

}
