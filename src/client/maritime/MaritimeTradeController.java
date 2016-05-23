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
		tradeOverlay.setCancelEnabled(true); // Maybe this will fix something.
		//tradeOverlay.setStateMessage("Hi mom"); // Experiment...
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
		getTradeOverlay().showGetOptions(new ResourceType[0]);
		getTradeOverlay().selectGetOption(resource, 1);

	}

	@Override
	public void setGiveResource(ResourceType resource)
	{
		System.out.println("This is the type "+resource.toString());
		getTradeOverlay().selectGiveOption(resource, 4); // what does this do eh? o.o
		// this may need to be replaced with showGetOptions for JUST that resource.
		ResourceType[] allResources = new ResourceType[5];
		allResources[0] = ResourceType.BRICK;
		allResources[1] = ResourceType.SHEEP;
		allResources[2] = ResourceType.WOOD;
		allResources[3] = ResourceType.ORE;
		allResources[4] = ResourceType.WHEAT;
		getTradeOverlay().showGetOptions(allResources);
		getTradeOverlay().setStateMessage("Choose what to get");
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

	/**
	 * The ArrayList will be placed into the array.
	 * The list is of variable size; however, the array should end up
	 * 		being the same size as the arrayList.
	 */
	private void displayForCurrentTurn()
	{
		ArrayList<ResourceType> resourceTypes = new ArrayList<>();
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWood() >= 4)
		{
			resourceTypes.add(ResourceType.WOOD);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getOre() >= 4)
		{
			resourceTypes.add(ResourceType.ORE);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getBrick() >= 4)
		{
			resourceTypes.add(ResourceType.BRICK);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getSheep() >= 4)
		{
			resourceTypes.add(ResourceType.SHEEP);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWheat() >= 4)
		{
			resourceTypes.add(ResourceType.WHEAT);
		}
		ResourceType[] whichResourcesToDisplay = new ResourceType[resourceTypes.size()];
		// I load the arrayList elements into the array because it's of VARIABLE SIZE
		for (int i = 0; i < resourceTypes.size(); i++)
		{
			whichResourcesToDisplay[i] = resourceTypes.get(i);
		}
		getTradeOverlay().showGiveOptions(whichResourcesToDisplay);
		if (whichResourcesToDisplay.length == 0)
		{
			getTradeOverlay().setStateMessage("You don't have enough resources.");
		}
		else
		{
			getTradeOverlay().setStateMessage("Choose what to give up");
			// I believe that this will only be called when you select something?
			// I *think* that you should be able to RECEIVE anything.
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("The game's current state is: " + ModelFacade.facadeCurrentGame.currentgame.getCurrentState().getState());
		switch (ModelFacade.facadeCurrentGame.currentgame.getCurrentState())
		{
			case SetUpState:
				getTradeView().enableMaritimeTrade(false);
				break;
			case GamePlayingState:
				getTradeView().enableMaritimeTrade(true);
				if (ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(
						ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getName()))
				{
					displayForCurrentTurn();
				}
				else
				{
					getTradeOverlay().showGiveOptions(new ResourceType[0]);
					getTradeOverlay().setStateMessage("not your turn");
				}
				break;
			default:
				getTradeView().enableMaritimeTrade(false);
				break;
		}
	}
}

