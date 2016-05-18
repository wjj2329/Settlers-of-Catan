package client.roll;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import client.base.*;
import client.model.ModelFacade;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.game.map.Hex.Hex;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.HexLocation;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

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
	
	@Override
	public void rollDice()
	{
		Random dice1 = new Random();
		Random dice2 = new Random();
		int diceRoll1 = dice1.nextInt(6) + 1;
		int diceRoll2 = dice2.nextInt(6) + 2;
		int diceRollTotal = diceRoll1 + diceRoll2;
		getResultView().setRollValue(diceRollTotal);
		
		getResultView().showModal();
		diceRoll = diceRollTotal;
		updatePlayerResources(diceRoll);
	}

	/**
	 * This function looks for all the hexes on the map whose corresponding number matches
	 * the dice roll. It will update the resources for any player who has a settlement
	 * on that hex.
	 * @param diceRoll: the dice roll received
     */
	private void updatePlayerResources(int diceRoll)
	{
		Map<HexLocation, Hex> hexes = ModelFacade.facace_currentgame.currentgame.getMymap().getHexes();
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
			Player corresponding = ModelFacade.facace_currentgame.currentgame.getMyplayers().get(settlement.getOwner());
			corresponding.getResources().incrementBasedOnHexType(resType);
		}
	}
}

