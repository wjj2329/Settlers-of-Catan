package client.maritime;

import client.State.State;
import client.model.ModelFacade;
import shared.definitions.*;
import client.base.*;
import shared.game.CatanGame;
import shared.game.player.Player;

import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the maritime trade controller
 * Alex is working on this currently.
 *
 * A few important points about trading here:
 * 1. The button will be disabled during the setup phase. It will be grayed out.
 * 	2. Need to call the canTradeWithBank method on the Player class.
 * 	3. I have not yet implemented the differentiation based on ports (i.e., 2:1, 3:1, and 4:1). 4:1
 * 		is the default, though, so I will probably implement that first!
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer
{
	/**
	 * This variable represents the player who is making the trade with the bank.
	 * AKA the current player.
	 */
	private Player currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay)
	{
		super(tradeView);
		setTradeOverlay(tradeOverlay);
		ModelFacade.facadeCurrentGame.addObserver(this);
	}
	
	public IMaritimeTradeView getTradeView()
	{
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade()
	{
		System.out.println("i start the trade");
		getTradeOverlay().showModal();
		/*if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.SetUpState)
		{
			getTradeOverlay().showGiveOptions(new ResourceType[0]);
			return;
		}*/
		/*if (currentPlayer.canDoTradeWithBank())
		{

		}*/
	}

	@Override
	public void makeTrade()
	{
		System.out.println("i make the trade");
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade()
	{
		System.out.println("i cancel the trade");
		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource)
	{
		System.out.println("This is the type "+resource.toString());

	}

	@Override
	public void setGiveResource(ResourceType resource)
	{
		System.out.println("This is the type "+resource.toString());

	}

	@Override
	public void unsetGetValue()
	{

	}

	@Override
	public void unsetGiveValue()
	{

	}

	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		switch (ModelFacade.facadeCurrentGame.currentgame.getCurrentState())
		{
			case SetUpState:
				getTradeView().enableMaritimeTrade(false);
				break;
			case GamePlayingState:
				getTradeView().enableMaritimeTrade(true);
				if (!currentPlayer.isCurrentPlayer())
				{
					getTradeOverlay().showGiveOptions(new ResourceType[0]);
					// then enable that button that says "not your turn"

				}
				break;
			default:
				getTradeView().enableMaritimeTrade(false);
				break;
		}
	}
}

