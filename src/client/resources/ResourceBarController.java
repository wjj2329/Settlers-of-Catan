package client.resources;

import java.util.*;

import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;
import client.base.*;
import client.model.ModelFacade;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer {

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

		elementActions.put(element, action);
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
	

    @Override
    public void update(Observable o, Object arg)
    {
    	Index currentTurn = ModelFacade.facace_singleton.getModel().getTurntracker().getCurrentTurn();
        Player player = ModelFacade.facace_singleton.getMyplayers().get(currentTurn);
        if (player != null)
        {
            ResourceList resourceCards = player.getResources();
        
            // update the resources you have
            this.getView().setElementAmount(ResourceBarElement.WOOD, resourceCards.getWood());
            this.getView().setElementAmount(ResourceBarElement.BRICK, resourceCards.getBrick());
            this.getView().setElementAmount(ResourceBarElement.SHEEP, resourceCards.getSheep());
            this.getView().setElementAmount(ResourceBarElement.ORE, resourceCards.getOre());
            this.getView().setElementAmount(ResourceBarElement.WHEAT, resourceCards.getWheat());

            // update roads/settlements/cities you have left
            this.getView().setElementAmount(ResourceBarElement.ROAD, player.getRoadPieces().size());
            this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, player.getSettlements().size());
            this.getView().setElementAmount(ResourceBarElement.CITY, player.getCities().size());

            // update soldiers
            this.getView().setElementAmount(ResourceBarElement.SOLDIERS, player.getArmySize());

            // update can build road/settlement/city
            this.getView().setElementEnabled(ResourceBarElement.ROAD,
                    player.canAffordRoad());
            this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, 
                    player.canAffordSettlement());
            this.getView().setElementEnabled(ResourceBarElement.CITY, 
                    player.canAffordCity());

            // update can buy card and can play card
            this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD,
                    true);
            this.getView().setElementEnabled(ResourceBarElement.BUY_CARD,
                    player.canAffordDevCard());
            
            
            if (player.getPlayerIndex() != currentTurn){
                this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
                this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
                this.getView().setElementEnabled(ResourceBarElement.CITY, false);
                this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
                this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
            }      
        }
    }

}

