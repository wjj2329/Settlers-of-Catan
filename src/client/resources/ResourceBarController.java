package client.resources;

import java.util.*;

import client.State.State;
import client.login.LoginController;
import client.model.Model;
import client.model.TurnStatus;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;
import client.base.*;
import client.model.ModelFacade;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.player.Player;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;

	public ResourceBarController(IResourceBarView view) {

		super(view);

		elementActions = new HashMap<ResourceBarElement, IAction>();
		ModelFacade.facadeCurrentGame.addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView) super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 *
	 * @param element The resource bar element with which the action is associated
	 * @param action  The action to be executed
	 */

	//needs to get update to work to overwrite the inital state when stuff happens in other controllers.
	public void setElementAction(ResourceBarElement element, IAction action) {
		elementActions.put(element, action);
		Player player = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();


		if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.SetUpState)) {
			this.getView().setElementEnabled(ResourceBarElement.CITY, false);
			this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		}
		if (player != null) {
			ResourceList resourceCards = player.getResources();

			// update the resources you have
			this.getView().setElementAmount(ResourceBarElement.WOOD, resourceCards.getWood());
			this.getView().setElementAmount(ResourceBarElement.BRICK, resourceCards.getBrick());
			this.getView().setElementAmount(ResourceBarElement.SHEEP, resourceCards.getSheep());
			this.getView().setElementAmount(ResourceBarElement.ORE, resourceCards.getOre());
			this.getView().setElementAmount(ResourceBarElement.WHEAT, resourceCards.getWheat());

			// update roads/settlements/cities you have left
			this.getView().setElementAmount(ResourceBarElement.ROAD, player.getNumRoadPiecesRemaining());
			this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, player.getNumSettlementsRemaining());
			this.getView().setElementAmount(ResourceBarElement.CITY, player.getNumCitiesRemaining());

			// update soldiers
			this.getView().setElementAmount(ResourceBarElement.SOLDIERS, player.getArmySize());

		}
		if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() != State.SetUpState) {
			if (player.getResources().getBrick() < 1 || player.getResources().getWood() < 1) {
				this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
			}
			if (player.getResources().getSheep() < 1 || player.getResources().getWood() < 1 || player.getResources().getBrick() < 1 || player.getResources().getWheat() < 1) {
				this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			}
			if (player.getResources().getWheat() < 2 || player.getResources().getOre() < 3) {
				this.getView().setElementEnabled(ResourceBarElement.CITY, false);
			}
			if (player.getResources().getOre() < 1 || player.getResources().getSheep() < 1 || player.getResources().getWheat() < 1) {
				this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			}
			if (player.getOldDevCards().getMonopoly() < 1 && player.getOldDevCards().getMonument() < 1 && player.getOldDevCards().getRoadBuilding() < 1 && player.getOldDevCards().getSoldier() < 0 && player.getOldDevCards().getYearOfPlenty() < 0) {
				this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
			}
		}

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
			if (ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getName()))
			{
				//System.out.println("Sam should get in here ");
				//Player player=ModelFacade.facadeCurrentGame.getLocalPlayer(); //this really needs to be the current player!!!!!!!!!
				//System.out.println("Sam IS HERE YEA!!!!");
				ResourceList resourceCards = ModelFacade.facadeCurrentGame.getLocalPlayer().getResources();
				this.getView().setElementAmount(ResourceBarElement.WOOD, resourceCards.getWood());
				this.getView().setElementAmount(ResourceBarElement.BRICK, resourceCards.getBrick());
				this.getView().setElementAmount(ResourceBarElement.SHEEP, resourceCards.getSheep());
				this.getView().setElementAmount(ResourceBarElement.ORE, resourceCards.getOre());
				this.getView().setElementAmount(ResourceBarElement.WHEAT, resourceCards.getWheat());

				// update roads/settlements/cities you have left
				this.getView().setElementAmount(ResourceBarElement.ROAD, ModelFacade.facadeCurrentGame.getLocalPlayer().getNumRoadPiecesRemaining());
				this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, ModelFacade.facadeCurrentGame.getLocalPlayer().getNumSettlementsRemaining());
				this.getView().setElementAmount(ResourceBarElement.CITY,ModelFacade.facadeCurrentGame.getLocalPlayer().getNumCitiesRemaining());

				// update soldiers
				this.getView().setElementAmount(ResourceBarElement.SOLDIERS, ModelFacade.facadeCurrentGame.getLocalPlayer().getArmySize());

				// update can build road/settlement/city
				this.getView().setElementEnabled(ResourceBarElement.ROAD,
						ModelFacade.facadeCurrentGame.getLocalPlayer().canAffordRoad());
				this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT,
						ModelFacade.facadeCurrentGame.getLocalPlayer().canAffordSettlement());
				this.getView().setElementEnabled(ResourceBarElement.CITY,
						ModelFacade.facadeCurrentGame.getLocalPlayer().canAffordCity());

				// update can buy card and can play card
				this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD,
						true);
				this.getView().setElementEnabled(ResourceBarElement.BUY_CARD,
						ModelFacade.facadeCurrentGame.getLocalPlayer().canAffordDevCard());

				if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.SetUpState||ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus().equals(TurnStatus.DISCARDING))
				{

					this.getView().setElementEnabled(ResourceBarElement.ROAD, false);

					this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);

					this.getView().setElementEnabled(ResourceBarElement.CITY, false);

					this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);

					this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
				}
				if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() != State.SetUpState) {
					if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getBrick() < 1 || ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWood() < 1) {
						this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
					}
					if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getSheep() < 1 || ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWood() < 1 || ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getBrick() < 1 ||ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWheat() < 1) {
						this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
					}
					if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWheat() < 2 || ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getOre() < 3) {
						this.getView().setElementEnabled(ResourceBarElement.CITY, false);
					}
					if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getOre() < 1 || ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getSheep() < 1 || ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWheat() < 1) {
						this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
					}
					if (ModelFacade.facadeCurrentGame.getLocalPlayer().getOldDevCards().getMonopoly() < 1 && ModelFacade.facadeCurrentGame.getLocalPlayer().getOldDevCards().getMonument() < 1 && ModelFacade.facadeCurrentGame.getLocalPlayer().getOldDevCards().getRoadBuilding() < 1 && ModelFacade.facadeCurrentGame.getLocalPlayer().getOldDevCards().getSoldier() < 0 && ModelFacade.facadeCurrentGame.getLocalPlayer().getOldDevCards().getYearOfPlenty() < 0) {
						this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
					}


				}

			} else {
				this.getView().setElementEnabled(ResourceBarElement.ROAD, false);

				this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);

				this.getView().setElementEnabled(ResourceBarElement.CITY, false);

				this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);

				this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
				ResourceList resourceCards = ModelFacade.facadeCurrentGame.getLocalPlayer().getResources();

				this.getView().setElementAmount(ResourceBarElement.WOOD, resourceCards.getWood());
				this.getView().setElementAmount(ResourceBarElement.BRICK, resourceCards.getBrick());
				this.getView().setElementAmount(ResourceBarElement.SHEEP, resourceCards.getSheep());
				this.getView().setElementAmount(ResourceBarElement.ORE, resourceCards.getOre());
				this.getView().setElementAmount(ResourceBarElement.WHEAT, resourceCards.getWheat());

				// update roads/settlements/cities you have left
				this.getView().setElementAmount(ResourceBarElement.ROAD, ModelFacade.facadeCurrentGame.getLocalPlayer().getNumRoadPiecesRemaining());
				this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, ModelFacade.facadeCurrentGame.getLocalPlayer().getNumSettlementsRemaining());
				this.getView().setElementAmount(ResourceBarElement.CITY, ModelFacade.facadeCurrentGame.getLocalPlayer().getNumCitiesRemaining());

				// update soldiers
				this.getView().setElementAmount(ResourceBarElement.SOLDIERS, ModelFacade.facadeCurrentGame.getLocalPlayer().getArmySize());

			}

			}

}


