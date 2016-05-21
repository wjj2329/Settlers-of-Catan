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

	private void refreshTurnTracker(){

		if(localcolorset == false && ModelFacade.facadeCurrentGame.getLocalPlayer() != null){
			localplayer = ModelFacade.facadeCurrentGame.getLocalPlayer();
			for (Player player : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values()) {
				if(player != null){
					if(player.getName().equals(localplayer.getName())){
						getView().setLocalPlayerColor(player.getColor());
						localplayer = player;
						localcolorset =true; 
						ModelFacade.facadeCurrentGame.setLocalPlayer(player);
					}
				}
			}
			
		}
		else{
			if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size() == 4)
			{
				Map<Index, Player> players = ModelFacade.facadeCurrentGame.currentgame.getMyplayers();
				int currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber();
				for (Player jugador: players.values()) 
				{
					view.initializePlayer(jugador.getPlayerIndex().getNumber(), jugador.getName(), jugador.getColor());
					if (jugador.getPlayerIndex().getNumber() == currentPlayer) 
					{ 
						view.updatePlayer(jugador.getPlayerIndex().getNumber(), jugador.getNumVictoryPoints(), true,
						hasLargestArmy(jugador.getPlayerIndex()), hasLongestRoad(jugador.getPlayerIndex()));
					}
					else
					{
						view.updatePlayer(jugador.getPlayerIndex().getNumber(), jugador.getNumVictoryPoints(), false,
							hasLargestArmy(jugador.getPlayerIndex()), hasLongestRoad(jugador.getPlayerIndex()));
					}
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
			if (index.getNumber() == ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getLongestRoad().getNumber()) 
			{
				return true;
			}
		return false;
	}

}
