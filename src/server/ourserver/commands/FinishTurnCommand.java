package server.ourserver.commands;

import server.ourserver.ServerFacade;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * The FinishTurnCommand
 */
public class FinishTurnCommand implements ICommand {

	/**
	 * Executes the task: 
	 * 	player to finish turn, new dev cards added to old dev cards
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		
		return null;
	}
	public void  endturn(int playerIndex, int gameid)
	{
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerIndex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
	}

}
