package server.ourserver.commands;

import server.ourserver.ServerFacade;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

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
		CatanGame currentgame=ServerFacade.getInstance().getGameByID(gameID);
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerindex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		playertoupdate.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()+1);
		playertoupdate.getOldDevCards().setMonument(playertoupdate.getOldDevCards().getMonument()-1);
		return null;
	}

}
