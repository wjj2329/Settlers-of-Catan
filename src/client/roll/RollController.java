package client.roll;

import java.util.*;

import client.State.State;
import client.base.*;
import client.model.ModelFacade;
import client.model.TurnStatus;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.game.map.Hex.Hex;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.HexLocation;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController,Observer {

	private IRollResultView resultView;
	private int diceRoll;
	
	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		ModelFacade.facadeCurrentGame.addObserver(this);
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {

		this.resultView = resultView;
	}

	public IRollView getRollView() {

		return (IRollView)getView();
	}
	public static int diceRollTotal;
	public static boolean robberrolled=false;
	
	@Override
	public void rollDice()
	{
		Random dice1 = new Random();
		Random dice2 = new Random();
		int diceRoll1 = dice1.nextInt(6) + 1;
		int diceRoll2 = dice2.nextInt(6) + 1;
		diceRollTotal = diceRoll1 + diceRoll2;
		getResultView().setRollValue(diceRollTotal);
		//if(diceRollTotal!=7) {
			getResultView().showModal();
			diceRoll = diceRollTotal;
		//}
		//updatePlayerResources(diceRoll);
		//if(!getResultView().isModalShowing())
		if(diceRollTotal==7)
		{
			robberrolled=true;
		}
			{
				//System.out.println("MY CURRENT PLAYER WHO ROLLED THIS MAGNIFICENT NUMBER IS "+ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getName());
				ModelFacade.facadeCurrentGame.getServer().rollNumber("rollNumber",
					ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(), diceRoll);
			}
	}

	/**
	 * This function looks for all the hexes on the map whose corresponding number matches
	 * the dice roll. It will update the resources for any player who has a settlement
	 * on that hex.
	 * @param diceRoll: the dice roll received
     */
	private void updatePlayerResources(int diceRoll)
	{
		Map<HexLocation, Hex> hexes = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes();
		for (Map.Entry<HexLocation, Hex> entry : hexes.entrySet())
		{
			Hex cur = entry.getValue();
			if (cur.getResourcenumber() == diceRoll)
			{
				ArrayList<Settlement> curSettlements = cur.getSettlementlist();
				resourceAdding(curSettlements, cur);
			}
		}
	}

	/**
	 * Function to assist with the updatePlayerResources(int diceRoll) function.
	 * @param settlements: all the settlements on the hex
	 * @param cur: the current hex that matches with the dice roll
     */
	private void resourceAdding(ArrayList<Settlement> settlements, Hex cur)
	{
		HexType resType = cur.getResourcetype();
		for (int i = 0; i < settlements.size(); i++)
		{
			Settlement settlement = settlements.get(i);
			Player corresponding = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(settlement.getOwner());
			corresponding.getResources().incrementBasedOnHexType(resType);
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		Player current = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
		//System.out.println(ModelFacade.facadeCurrentGame.currentgame.getCurrentState().toString()+" "+ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus());

		if(current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName())) {
			if (ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus().equals(TurnStatus.ROLLING) && 
					ModelFacade.facadeCurrentGame.getModel().getTurntracker().getCurrentTurn().getNumber() == ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber()) {
				getRollView().showModal();
				ModelFacade.facadeCurrentGame.currentgame.setCurrentState(State.GamePlayingState);
//				//System.out.println("I ROLL MY DICE");
//				if(!getRollView().isModalShowing()){
//					rollDice();
//				}
			}
		}
	}
}

