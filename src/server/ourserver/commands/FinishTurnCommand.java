package server.ourserver.commands;

import server.ourserver.ServerFacade;
import shared.game.CatanGame;
import shared.game.map.Index;

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
		switch (playerIndex)
		{
			case(0):
			{
				currentgame.getModel().getTurntracker().setCurrentTurn(new Index(1),currentgame.getMyplayers());
				break;
			}
			case(1):
			{
				currentgame.getModel().getTurntracker().setCurrentTurn(new Index(2),currentgame.getMyplayers());
				break;
			}
			case(2):
			{
				currentgame.getModel().getTurntracker().setCurrentTurn(new Index(3),currentgame.getMyplayers());
				break;
			}
			case(3):
			{
				currentgame.getModel().getTurntracker().setCurrentTurn(new Index(0),currentgame.getMyplayers());
				break;
			}
		}
	}

}
