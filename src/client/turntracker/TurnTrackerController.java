package client.turntracker;

import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

import java.util.HashMap;
import java.util.Map;

import client.base.*;
import client.model.ModelFacade;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	ITurnTrackerView view;

	public TurnTrackerController(ITurnTrackerView view) {

		super(view);
		this.view = view;
		initFromModel();
	}

	@Override
	public ITurnTrackerView getView() {

		return (ITurnTrackerView) super.getView();
	}

	@Override
	public void endTurn() {
		ModelFacade.facace_currentgame.currentgame.getModel().getServer().finishTurn("finishTurn", 
				ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber());
	}

	private void initFromModel() {
		// sets the current user's color..
		//
////		//This is for testing. comment later
//		Map<Index, Player> players = new HashMap();
//		int currentPlayer = 0;
//		getView().setLocalPlayerColor(CatanColor.BLUE);
		
		//This is what is actually suppose to use, but the currentgame.getstuff is null and brings up a nullPointerException
//		int currentPlayer = ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber();
//		getView().setLocalPlayerColor(ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());
//		Map<Index, Player> players = ModelFacade.facace_currentgame.currentgame.getMyplayers();

//		int currentPlayer = ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber();
//		getView().setLocalPlayerColor(ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());	
		int currentPlayer = 0;
		if(ModelFacade.facace_currentgame.currentgame.getCurrentPlayer() != null)
		{
			if(ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getPlayerIndex() != null){
				currentPlayer = ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber();
				getView().setLocalPlayerColor(ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());			
			}
		}
		else
		{
			getView().setLocalPlayerColor(CatanColor.RED);
		}
		
		Map<Index, Player> players = ModelFacade.facace_currentgame.currentgame.getMyplayers();
		if (players != null) 
		{
			if(players.size() <=4)
			{
				for (Index index : players.keySet()) 
				{
					view.initializePlayer(index.getNumber(), players.get(index).getName(), players.get(index).getColor());
					if (index.getNumber() == currentPlayer) 
					{
						view.updatePlayer(index.getNumber(), players.get(index).getNumVictoryPoints(), true,
							hasLargestArmy(index), hasLongestRoad(index));
					}
					view.updatePlayer(index.getNumber(), players.get(index).getNumVictoryPoints(), false,
						hasLargestArmy(index), hasLongestRoad(index));
				}
			}
		}
	}

	public boolean hasLargestArmy(Index index) {
		if (index.getNumber() == ModelFacade.facace_currentgame.currentgame.getMyturntracker().getLargestArmy().getNumber()) {
			return true;
		}
		return false;
	}

	public boolean hasLongestRoad(Index index) {
		if (index.getNumber() == ModelFacade.facace_currentgame.currentgame.getMyturntracker().getLongestRoad().getNumber()) {
			return true;
		}
		return false;
	}

}
