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

	private boolean haveAllPlayersJoined()
	{
		for (Player p : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values())
		{
			if (!p.hasJoinedGame())
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(localcolorset == false && ModelFacade.facadeCurrentGame.getLocalPlayer() != null){

			localplayer = ModelFacade.facadeCurrentGame.getLocalPlayer();
			System.out.println("This is the local player's color: " + localplayer.getColor().name());
			for (Player player : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values()) {
				if(player != null){
					if(player.getName().equals(localplayer.getName())){
						System.out.println("What is the color of the player in the modelFacade? " + player.getColor());
						getView().setLocalPlayerColor(player.getColor());
						localplayer = player;
						localcolorset = true; 
						ModelFacade.facadeCurrentGame.setLocalPlayer(player);
						System.out.println("Does the player's color randomly get changed? " + player.getColor());
					}
				}
			}
			for (Player p : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values())
			{
				System.out.println("The color HERE for " + p.getName() + " is " + p.getColor());
			}
			System.out.println("This is the local player's color AFTER: " + localplayer.getColor().name());
			
		}
		if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size() == 4 /*&& haveAllPlayersJoined()*/)
			{
				Map<Index, Player> players = ModelFacade.facadeCurrentGame.currentgame.getMyplayers();
				int currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber();
				if(!playersinitialized)
				{
					//System.out.println("I am initializing the players here"); // only gets called once; good
					initializePlayers(players);
					playersinitialized = true; 
				}
				// need to put something here
				for (Player jugador: players.values()) 
				{
					// This is incorrect at times...
					System.out.println("The color for " + jugador.getName() + " is " + jugador.getColor().toString());
					/*view.updateColor(jugador.getPlayerIndex().getNumber(), jugador.getColor(), jugador.getName(),
							jugador.getNumVictoryPoints());*/
					//view.setLocalPlayerColor(jugador.getColor()); // all this does is update the title bar.


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
