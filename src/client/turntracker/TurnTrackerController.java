package client.turntracker;

import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import client.base.*;
import client.model.ModelFacade;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	ITurnTrackerView view;
    private Timer timer;
    private Player localplayer;
    private boolean localcolorset;

	public TurnTrackerController(ITurnTrackerView view)
	{
		
		super(view);
		this.view = view;
		localplayer=null;
		localcolorset = false;
		initFromModel();
	}

	@Override
	public ITurnTrackerView getView()
	{

		return (ITurnTrackerView) super.getView();
	}

	
	@Override
	public void endTurn()
	{
		ModelFacade.facadeCurrentGame.getServer().finishTurn("finishTurn",
				ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber());

	}

	private void initFromModel()
	{
		
		TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                refreshTurnTracker();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1500);

	}

	private void refreshTurnTracker()
	{

		if(localcolorset == false && ModelFacade.facadeCurrentGame.getLocalPlayer() != null)
		{
			localplayer = ModelFacade.facadeCurrentGame.getLocalPlayer();
			for (Player player : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values()) 
			{
				if(player != null)
				{
					if(player.getName().equals(localplayer.getName()))
					{
						getView().setLocalPlayerColor(player.getColor());
						localplayer = player;
						localcolorset =true; 
					}
				}
			}
			
		}
		
	}
	
	private void initFromModel2()
	{
		// sets the current user's color..
		//
////		//This is for testing. comment later
//		Map<Index, Player> players = new HashMap();
//		int currentPlayer = 0;
//		getView().setLocalPlayerColor(CatanColor.BLUE);
		
		
		//This is what is actually suppose to use, but the currentgame.getstuff is null and brings up a nullPointerException
//		int currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber();
//		getView().setLocalPlayerColor(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getColor());
//		Map<Index, Player> players = ModelFacade.facadeCurrentGame.currentgame.getMyplayers();

//		int currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber();
//		getView().setLocalPlayerColor(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getColor());
		int currentPlayer = 0;
		if(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer() != null)
		{
			if(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex() != null){
			currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber();
				getView().setLocalPlayerColor(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getColor());
			}
		}
		else
		{
			getView().setLocalPlayerColor(CatanColor.RED);
		}
		
		Map<Index, Player> players = ModelFacade.facadeCurrentGame.currentgame.getMyplayers();
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
		if (index.getNumber() == ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getLargestArmy().getNumber()) {
			return true;
		}
		return false;
	}

	public boolean hasLongestRoad(Index index) {
		if (index.getNumber() == ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getLongestRoad().getNumber()) {
			return true;
		}
		return false;
	}

}
