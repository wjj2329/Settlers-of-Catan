package client.turntracker;

import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import client.base.*;
import client.model.ModelFacade;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

	ITurnTrackerView view;
    private Timer timer;
    private Player localplayer;
    private boolean localcolorset;
    private boolean playersinitialized;

	public TurnTrackerController(ITurnTrackerView view)
	{
		
		super(view);
		this.view = view;
		localplayer=null;
		localcolorset = false;
		playersinitialized = false; 
		initFromModel();
		ModelFacade.facadeCurrentGame.addObserver(this);
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

	public void initializePlayers(Map<Index, Player> players){
		for (Player jugador: players.values()) 
		{   
			view.initializePlayer(jugador.getPlayerIndex().getNumber(), jugador.getName(), jugador.getColor());
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(localcolorset == false && ModelFacade.facadeCurrentGame.getLocalPlayer() != null){
			
			localplayer = ModelFacade.facadeCurrentGame.getLocalPlayer();
			for (Player player : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values()) {
				if(player != null){
					if(player.getName().equals(localplayer.getName())){
						getView().setLocalPlayerColor(player.getColor());
						localplayer = player;
						localcolorset = true; 
						ModelFacade.facadeCurrentGame.setLocalPlayer(player);
					}
				}
			}
			
		}
		if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size() == 4)
			{
				Map<Index, Player> players = ModelFacade.facadeCurrentGame.currentgame.getMyplayers();
				int currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber();
				if(!playersinitialized){
					initializePlayers(players);
					playersinitialized = true; 
				}
				for (Player jugador: players.values()) 
				{   
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
