package client.maritime;

import client.State.State;
import client.model.ModelFacade;
import shared.definitions.*;
import client.base.*;
import shared.game.CatanGame;
import shared.game.player.Player;

import java.util.ArrayList;
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
	private Player localPlayer = ModelFacade.facadeCurrentGame.getLocalPlayer();
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

				// the below code doesn't quite work
				for (Player p : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values())
				{
					if (!p.isCurrentPlayer())
					{
						getTradeOverlay().showGiveOptions(new ResourceType[0]);
						getTradeOverlay().setStateMessage("not your turn");
					}
					else
					{
						// right now, just hard-coding a 4:1 trade until I implement the other types of trades.
						ArrayList<ResourceType> arraysSuck = new ArrayList<>();
						if (p.getResources().getWood() >= 4)
						{
							arraysSuck.add(ResourceType.WOOD);
						}
						if (p.getResources().getOre() >= 4)
						{
							arraysSuck.add(ResourceType.ORE);
						}
						if (p.getResources().getBrick() >= 4)
						{
							arraysSuck.add(ResourceType.BRICK);
						}
						if (p.getResources().getSheep() >= 4)
						{
							arraysSuck.add(ResourceType.SHEEP);
						}
						if (p.getResources().getWheat() >= 4)
						{
							arraysSuck.add(ResourceType.WHEAT);
						}
						ResourceType[] whichResourcesToDisplay = new ResourceType[arraysSuck.size()];
						// I load the arrayList elements into the array because it's of VARIABLE SIZE
						// Because arrays SUCK and arrayLists are superior! :o
						for (int i = 0; i < arraysSuck.size(); i++)
						{
							whichResourcesToDisplay[i] = arraysSuck.get(i);
						}
						getTradeOverlay().showGiveOptions(whichResourcesToDisplay);
						if (whichResourcesToDisplay.length == 0)
						{
							getTradeOverlay().setStateMessage("You don't have enough resources.");
						}
						else
						{
							getTradeOverlay().setStateMessage("Choose what to give up");
						}
					}
				}
				break;
			default:
				getTradeView().enableMaritimeTrade(false);
				break;
		}
	}
}

