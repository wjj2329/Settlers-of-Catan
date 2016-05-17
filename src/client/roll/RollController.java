package client.roll;

import java.util.Random;

import client.base.*;


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
	public void rollDice() {
		Random dice1 = new Random();
		Random dice2 = new Random();
		int diceRoll1 = dice1.nextInt(6) + 1;
		int diceRoll2 = dice2.nextInt(6) + 2;
		int diceRollTotal = diceRoll1 + diceRoll2;
		getResultView().setRollValue(diceRollTotal);
		
		getResultView().showModal();
		diceRoll = diceRollTotal;
	}

}

