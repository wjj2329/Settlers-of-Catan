package client.resources;

import java.util.*;

import client.base.*;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.player.Player;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		CatanGame.singleton.getCurrentPlayer().setResources(new ResourceList(5,5,5,5,5));
		elementActions.put(element, action);
		this.getView().setElementAmount(ResourceBarElement.BRICK, CatanGame.singleton.getCurrentPlayer().getResources().getBrick());
		this.getView().setElementAmount(ResourceBarElement.WHEAT, CatanGame.singleton.getCurrentPlayer().getResources().getWheat());
		this.getView().setElementAmount(ResourceBarElement.WOOD, CatanGame.singleton.getCurrentPlayer().getResources().getWood());
		this.getView().setElementAmount(ResourceBarElement.SHEEP, CatanGame.singleton.getCurrentPlayer().getResources().getSheep());
		this.getView().setElementAmount(ResourceBarElement.ORE,CatanGame.singleton.getCurrentPlayer().getResources().getOre());
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

}

