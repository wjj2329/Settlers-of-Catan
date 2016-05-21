package client.points;

import client.base.*;
import client.model.ModelFacade;
import shared.game.CatanGame;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		initFromModel();
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		
		int points = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getNumVictoryPoints();
		getPointsView().setPoints(points);
		
		int winner = ModelFacade.facadeCurrentGame.currentgame.getWinner().getNumber();
		if(winner != -1){
			//ends the game...
		}
	}
	
}

