package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.game.DevCardList;
import client.base.*;
import client.model.ModelFacade;
import server.proxies.IServer;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	private IServer server;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		server = ModelFacade.facadeCurrentGame.getServer();
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		if(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().canBuyDevCard()){
			getBuyCardView().showModal();
		}
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		
		DevCardList oldDevList = new DevCardList(2, 2, 2, 2, 1);
//		DevCardList oldDevlist = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getOldDevCards();
		
		if(oldDevList.getTotalCardNum()>0){
			getPlayCardView().setCardAmount(DevCardType.MONOPOLY, oldDevList.getMonopoly());
			getPlayCardView().setCardAmount(DevCardType.MONUMENT, oldDevList.getMonument());
			getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, oldDevList.getRoadBuilding());
			getPlayCardView().setCardAmount(DevCardType.SOLDIER, oldDevList.getSoldier());
			getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, oldDevList.getYearOfPlenty());	
		}
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		server.playMonopoly("Monopoly", ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber(), resource.name());
	}

	@Override
	public void playMonumentCard() {
		server.playMonument("Monument", ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
	}

	@Override
	public void playRoadBuildCard() {
		
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		server.playYearofPlenty("Year_of_Plenty", ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber(), resource1.name(), resource2.name());
	}

}

