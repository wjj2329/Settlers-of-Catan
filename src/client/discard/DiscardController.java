package client.discard;

import client.model.ModelFacade;
import client.model.TurnStatus;
import client.roll.RollController;
import org.json.JSONException;
import org.json.JSONObject;
import shared.definitions.*;
import client.base.*;
import client.misc.*;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController,Observer {

	private IWaitView waitView;
	private ResourceList discardList = new ResourceList();
	private int maxDiscardNum;


	/**
	 * DiscardController constructor
	 *
	 * @param view     View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {

		super(view);
		ModelFacade.facadeCurrentGame.addObserver(this);
		this.waitView = waitView;

	}

	public IDiscardView getDiscardView() {
		return (IDiscardView) super.getView();
	}

	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		discardList.increaseby1(resource);
		updateView();
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		discardList.decreaseby1(resource);
		updateView();
	}

	@Override
	public void discard()
	{
		ModelFacade.facadeCurrentGame.getServer().discardCards("discardCards",ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber(),discardList);
		getDiscardView().closeModal();
		if(getDiscardView().isModalShowing()) {
			getDiscardView().closeModal();
		}

		discardList = new ResourceList();
		ModelFacade.facadeCurrentGame.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(!ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus().equals(TurnStatus.DISCARDING))
		{
			return;
		}
		if(ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().size()<8)
		{
			return;
		}
		maxDiscardNum=ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().size()/2;
		updateView();

	}

	private boolean increaseallowed(ResourceType type)
	{
		Player localPlayer=ModelFacade.facadeCurrentGame.getLocalPlayer();
		switch(type)
		{
			case BRICK:
			{
				return discardList.getBrick()<localPlayer.getResources().getBrick();
			}
			case WHEAT:
			{
				return discardList.getWheat()<localPlayer.getResources().getWheat();

			}
			case WOOD:
			{
				return discardList.getWood()<localPlayer.getResources().getWood();

			}
			case ORE:
			{
				return discardList.getOre()<localPlayer.getResources().getOre();

			}
			case SHEEP:
			{
				return discardList.getSheep()<localPlayer.getResources().getSheep();

			}
		}
		return false;
	}


	private boolean decreaseallowed(ResourceType type)
	{
		switch(type)
		{
			case BRICK:
			{
				return discardList.getBrick()>0;
			}
			case WHEAT:
			{
				return discardList.getWheat()>0;

			}
			case WOOD:
			{
				return discardList.getWood()>0;

			}
			case ORE:
			{
				return discardList.getOre()>0;

			}
			case SHEEP:
			{
				return discardList.getSheep()>0;

			}
		}
		return false;

	}

	private void updateView()
	{
		getDiscardView().closeModal();
		ResourceList currentHand = ModelFacade.facadeCurrentGame.getLocalPlayer().getResources();
		int currentBricks = currentHand.getBrick();
		int currentOres = currentHand.getOre();
		int currentSheeps = currentHand.getSheep();
		int currentWoods = currentHand.getWood();
		int currentWheats = currentHand.getWheat();

		int discardBricks = discardList.getBrick();
		int discardOres = discardList.getOre();
		int discardSheeps = discardList.getSheep();
		int discardWoods = discardList.getWood();
		int discardWheats = discardList.getWheat();

		getDiscardView().setStateMessage("Discarding: " + discardList.size() + "/" + maxDiscardNum);

		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, currentBricks);
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, currentOres);
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, currentSheeps);
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, currentWoods);
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, currentWheats);

		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, discardBricks);
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, discardOres);
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, discardSheeps);
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, discardWoods);
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, discardWheats);

		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK,increaseallowed(ResourceType.BRICK),decreaseallowed(ResourceType.BRICK));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD,increaseallowed(ResourceType.WOOD),decreaseallowed(ResourceType.WOOD));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT,increaseallowed(ResourceType.WHEAT),decreaseallowed(ResourceType.WHEAT));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE,increaseallowed(ResourceType.ORE),decreaseallowed(ResourceType.ORE));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP,increaseallowed(ResourceType.SHEEP),decreaseallowed(ResourceType.SHEEP));



		if(discardList.size()==maxDiscardNum)
		{
			getDiscardView().setDiscardButtonEnabled(true);
		}
		else
		{
			getDiscardView().setDiscardButtonEnabled(false);
		}

		if(getDiscardView().isModalShowing())
		{
			getDiscardView().closeModal();
			getDiscardView().showModal();
		}
		else
			getDiscardView().showModal();

	}
}

