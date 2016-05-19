package client.maritime;

import client.model.ModelFacade;
import shared.definitions.*;
import client.base.*;
import shared.game.player.Player;


/**
 * Implementation for the maritime trade controller
 * Alex is working on this currently.
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController
{
	/**
	 * This variable represents the player who is making the trade with the bank.
	 * This may not be getLocalPlayer
	 */
	private Player currentPlayer = ModelFacade.facace_currentgame.getLocalPlayer();
	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay)
	{
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
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
		if (currentPlayer.isCurrentPlayer())
		{
			
		}
		getTradeOverlay().showModal();
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
}

