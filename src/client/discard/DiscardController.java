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
	private ResourceList discardList=new ResourceList();
	private int maxDiscardNum;


	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		ModelFacade.facadeCurrentGame.addObserver(this);
		this.waitView = waitView;
		maxDiscardNum = calculateDiscardNum();

	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource)
	{
		discardList.increaseby1(resource);
		updateView();
	}

	@Override
	public void decreaseAmount(ResourceType resource)
	{
		discardList.decreaseby1(resource);
				updateView();
	}

	@Override
	public void discard()
	{
		Player getlocalPlayer=null;
		for(Index index:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
		{
			if(ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index).getName()))
			{
				getlocalPlayer=ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index);
			}
		}
		String response="";
		response=ModelFacade.facadeCurrentGame.getServer().discardCards("discardCards",getlocalPlayer.getPlayerIndex().getNumber(),discardList).getResponse();

		System.out.println("MY RESPONSE IS THIS!"+response);
		if(response!="") {
			// If request succeeded
			try {
				ModelFacade.facadeCurrentGame.currentgame.updateFromJSON(new JSONObject(response));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			getDiscardView().closeModal();
			getWaitView().closeModal();
		}
		else
		{
			getDiscardView().setStateMessage("INVALID AMOUNT!!!!");
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		Player getlocalPlayer=null;
		for(Index index:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
		{
			if(ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index).getName()))
			{
				getlocalPlayer=ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index);
			}
		}
		if(getlocalPlayer==null)
		{
			System.out.println("HE's Dead AND NULL IN THE DISCARD JIM");
			return;
		}
		int totalnumberofcards=getlocalPlayer.getResources().size();
		System.out.println("MY TURN STATUS IS "+ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus().toString());
			if(ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus().equals(TurnStatus.DISCARDING)&&totalnumberofcards>7)
			{
				if(maxDiscardNum == 0) {
					this.getWaitView().showModal();
				} else {
					this.getDiscardView().showModal();
				}
				updateView();
			}
	}
	private void updateView() {
		Player getlocalPlayer=null;
		for(Index index:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
		{
			if(ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index).getName()))
			{
				getlocalPlayer=ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index);
			}
		}
		if(getlocalPlayer==null)
		{
			System.out.println("HE's Dead AND NULL IN THE DISCARD JIM update");
			return;
		}
		ResourceList currentHand = getlocalPlayer.getResources();
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

		switch(checkDiscardNum()) {
			case -1: getDiscardView().setDiscardButtonEnabled(false);
			case 0: getDiscardView().setDiscardButtonEnabled(true);
			case 1: getDiscardView().setDiscardButtonEnabled(false);
		}
	}
	private int calculateDiscardNum() {
		int number;
		Player getlocalPlayer=null;
		for(Index index:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
		{
			if(ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index).getName()))
			{
				getlocalPlayer=ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(index);
			}
		}
		if(getlocalPlayer==null)
		{
			return -1;
		}
		int totalCards = getlocalPlayer.getResources().size()/2;

		if (totalCards > 7) {
			number = totalCards/2;
		} else
		{
			// If they don't need to discard anything, show waitView
			number = 0;
		}

		return number;
	}
	private int checkDiscardNum() {
		int n = -1;

		if (discardList.size() < maxDiscardNum) {
			n = -1;
		} else if (discardList.size() == maxDiscardNum) {
			n = 0;
		} else if (discardList.size() > maxDiscardNum) {
			n = 1;
		}

		return n;
	}
}

