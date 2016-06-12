package server.ourserver.commands;

import client.model.TurnStatus;
import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.chat.GameHistoryLine;
import shared.game.CatanGame;
import shared.game.DevCardList;
import shared.game.map.Index;
import shared.game.player.Player;

import java.io.FileNotFoundException;

/**
 * The FinishTurnCommand
 */
public class FinishTurnCommand implements ICommand {

	/**
	 * Executes the task: 
	 * 	player to finish turn, new dev cards added to old dev cards
	 */
	int playerIndex;
	int gameid;
	public FinishTurnCommand(int playerIndex, int gameid)
	{
		this.playerIndex=playerIndex;
		this.gameid=gameid;
	}
	@Override
	public Object execute() throws FileNotFoundException, JSONException
	{
//System.out.println("I call the end turn command for player with player index"+playerIndex);
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

		//Code for dev cards
		DevCardList cards = playertoupdate.getOldDevCards();
		DevCardList newcards = playertoupdate.getNewDevCards();

		if(newcards.getTotalCardNum() > 0)
		{
			cards.setMonopoly(cards.getMonopoly() + newcards.getMonopoly());
			cards.setMonument(cards.getMonument() + newcards.getMonument());
			cards.setRoadBuilding(cards.getRoadBuilding() + newcards.getRoadBuilding());
			cards.setSoldier(cards.getSoldier() + newcards.getSoldier());
			cards.setYearOfPlenty(cards.getYearOfPlenty() + newcards.getYearOfPlenty());

			newcards.clear();
		}

		switch (playerIndex)
		{
			case(0):
			{
				if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.PLAYING)||currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.ROBBING))
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(1), currentgame.getMyplayers());
					currentgame.getModel().getTurntracker().setStatus(TurnStatus.ROLLING);
					currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " ends his or her turn",playertoupdate.getName()));

					return null;
				}
				if(!currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND)) {
					//System.out.println("I update for case 0");
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(1), currentgame.getMyplayers());
				}
				else
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(0), currentgame.getMyplayers());
					currentgame.getModel().getTurntracker().setStatus(TurnStatus.ROLLING);
					currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " ends his or her turn",playertoupdate.getName()));

				}
				break;
			}
			case(1):
			{
				if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.PLAYING))
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(2), currentgame.getMyplayers());
					currentgame.getModel().getTurntracker().setStatus(TurnStatus.ROLLING);
					currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " ends his or her turn",playertoupdate.getName()));

					return null;
				}
				if(!currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND)) {
					//System.out.println("I update for case 1");
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
				if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.PLAYING))
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(3), currentgame.getMyplayers());
					currentgame.getModel().getTurntracker().setStatus(TurnStatus.ROLLING);
					currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " ends his or her turn",playertoupdate.getName()));

					return null;
				}
				//System.out.println("I update for case 2");
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
				if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.PLAYING))
				{
					currentgame.getModel().getTurntracker().setCurrentTurn(new Index(0), currentgame.getMyplayers());
					currentgame.getModel().getTurntracker().setStatus(TurnStatus.ROLLING);
					currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " ends his or her turn",playertoupdate.getName()));
					return null;
				}
				//System.out.println("I update for case 3");
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

		currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " ends his or her turn",playertoupdate.getName()));
		return null;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," + TextDBGameManagerDAO.commandNumber+":"+"{"
				+"\"type\":\"FinishTurnCommand\"," +
				"\"playerIndex\":" + playerIndex +
				", \"gameid\":" + gameid +
				"}";
	}
	@Override
	public String toJSON() {
		return "{"
				+"\"type\":\"FinishTurnCommand\"" +
				"\"playerIndex\":" + playerIndex +
				", \"gameid\":" + gameid +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;

	}
}
