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
	public void discard() {

	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(!ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus().equals(TurnStatus.DISCARDING))
		{
			System.out.println("IT isn't discarding time");
			return;
		}
		if(ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().size()<8)
		{
			return;
		}
		updateView();
		//getDiscardView().showModal();

	}

	private void updateView()
	{
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

		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK
				, (currentBricks > 0 && discardBricks < currentBricks)
				, (discardBricks > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE
				, (currentOres > 0 && discardOres < currentOres)
				, (discardOres > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP
				, (currentSheeps > 0 && discardSheeps < currentSheeps)
				, (discardSheeps > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD
				, (currentWoods > 0 && discardWoods < currentWoods)
				, (discardWoods > 0));
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT
				, (currentWheats > 0 && discardWheats < currentWheats)
				, (discardWheats > 0));
		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, discardBricks);
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, discardOres);
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, discardSheeps);
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, discardWoods);
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, discardWheats);




	}
}

