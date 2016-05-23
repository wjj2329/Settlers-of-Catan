package client.points;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import client.State.State;
import client.base.*;
import client.model.ModelFacade;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) 
	{	
		super(view);
		setFinishedView(finishedView);
		initFromModel();
		ModelFacade.facadeCurrentGame.addObserver(this);
	}
	
	public IPointsView getPointsView() 
	{
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() 
	{
		return finishedView;
	}
	
	public void setFinishedView(IGameFinishedView finishedView) 
	{
		this.finishedView = finishedView;
	}

	private void initFromModel() 
	{
		//<temp>
		getPointsView().setPoints(0);
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		
		Map<Index, Player> jugadores = ModelFacade.facadeCurrentGame.currentgame.getMyplayers();
		if(ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.GamePlayingState) ||
				ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.SetUpState))
		{
			if(ModelFacade.facadeCurrentGame.getLocalPlayer() != null)
			{
				//System.out.println("ID "+ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID().getNumber());
				//System.out.println("Index "+ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber());
				Player local = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID());				
				for(Player player: jugadores.values()){
					System.out.println(player.getName() + " TIENE " + player.getNumVictoryPoints());
				}
				
				getPointsView().setPoints(local.getNumVictoryPoints());
				//System.out.println("THE PERSON " + local.getPlayerID().getNumber() + " HAS THIS MANY POINTS: "+local.getNumVictoryPoints());
				//System.out.println(ModelFacade.facadeCurrentGame.getServer().getGameCurrentState(0).getResponse());	
			}
		}
		Player local = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID());
		int winner = ModelFacade.facadeCurrentGame.currentgame.getWinner().getNumber();
		if(winner != -1)
		{
			Player daWinner = null;
			for(Player player : jugadores.values())
			{
				if(player.getPlayerIndex().getNumber() == winner){
					//System.out.println("DOES THIS EVEN SET THE NUMBER? " + player.getPlayerIndex());
					daWinner = player;
				}
			}
				
			if(winner == local.getPlayerIndex().getNumber())
			{
				getFinishedView().setWinner(daWinner.getName(), true);
				//System.out.println("THE LOCAL PLAYAH WINS!");
			}
			else{
				getFinishedView().setWinner(daWinner.getName(), false);
				//System.out.println("THE LOCAL PLAYAH DOESN'T WINS!");
			}
			getFinishedView().showModal();				
		}
			
		
	}
	
	
}

