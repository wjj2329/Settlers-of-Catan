package server.ourserver.commands;

import client.model.TurnStatus;
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
	public void endturn(int playerIndex, int gameid)
	{
		System.out.println("I call the end turn command for player with player index"+playerIndex);
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		switch (playerIndex)
		{
			case(0):
			{
				if(!currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND)) {
					System.out.println("I update for case 0");
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(1), currentgame.getMyplayers());
				}
				else
				{
					currentgame.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(0), currentgame.getMyplayers());
				}
				break;
			}
			case(1):
			{
				if(!currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND)) {
					System.out.println("I update for case 1");
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(2), currentgame.getMyplayers());
				}
				else
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(0), currentgame.getMyplayers());

				}
				break;
			}
			case(2):
			{
				System.out.println("I update for case 2");
				if(!currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND)) {
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(3), currentgame.getMyplayers());
				}
				else
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(1), currentgame.getMyplayers());

				}
				break;
			}
			case(3):
			{
				System.out.println("I update for case 3");
				if(!currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND))
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(0),currentgame.getMyplayers());
				}
				else {
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(2), currentgame.getMyplayers());
				}
				break;
			}
		}
	}

}
