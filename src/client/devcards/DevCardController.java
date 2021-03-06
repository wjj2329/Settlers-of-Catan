package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.game.DevCardList;
import shared.game.map.Index;
import shared.game.player.Player;
import client.base.*;
import client.model.ModelFacade;
import server.proxies.IServer;

import java.util.Observable;
import java.util.Observer;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController,Observer{

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
		ModelFacade.facadeCurrentGame.addObserver(this);
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
		if(getBuyCardView().isModalShowing()) {
			getBuyCardView().closeModal();
		}
	}

	@Override
	public void buyCard() 
	{		
		Index index = ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID();
    	server.buyDevCard("buyDevCard", index.getNumber());
		if(getBuyCardView().isModalShowing()) {
			getBuyCardView().closeModal();
		}
	}

	@Override
	public void startPlayCard() 
	{
		getPlayCardView().showModal();
		Player player = ModelFacade.facadeCurrentGame.getLocalPlayer();
		
    	DevCardList cards = player.getOldDevCards();
    	
    	DevCardList newcards = player.getNewDevCards();
    	
    	//Set each type of card as enabled or disabled
    	if (cards.getMonopoly() > 0) 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
		}
    	else 
		{
    		getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
		}
    	if (cards.getMonument() > 0) 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
    	}
    	else 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
    	}
    	if (cards.getRoadBuilding() > 0) 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
    	}
    	else 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
    	}
    	if (cards.getSoldier() > 0) 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
    	}
    	else 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
    	}
    	if (cards.getYearOfPlenty() > 0) 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
    	}
    	else 
    	{
    		getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
    	}
		
    	//Set card amounts
		if(cards.getTotalCardNum() + newcards.getTotalCardNum() > 0)
		{
			getPlayCardView().setCardAmount(DevCardType.MONOPOLY, cards.getMonopoly() + newcards.getMonopoly());
			getPlayCardView().setCardAmount(DevCardType.MONUMENT, cards.getMonument() + newcards.getMonument());
			getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, cards.getRoadBuilding() + newcards.getRoadBuilding());
			getPlayCardView().setCardAmount(DevCardType.SOLDIER, cards.getSoldier() + newcards.getSoldier());
			getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, cards.getYearOfPlenty() + newcards.getYearOfPlenty());	
		}
		
		//getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() 
	{
		if(getPlayCardView().isModalShowing()) 
		{
			getPlayCardView().closeModal();
		}
	}

	@Override
	public void playMonopolyCard(ResourceType resource)
	{
		System.out.println("Starting monopoly");
		server.playMonopoly("Monopoly", ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber(), resource.name());
	}

	@Override
	public void playMonumentCard()
	{
		System.out.println("Starting monument");
		server.playMonument("Monument", ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
	}

	@Override
	public void playRoadBuildCard()
	{
		System.out.println("Starting road building");
		getPlayCardView().closeModal();
		roadAction.execute();
		roadAction.execute(); // this is sketchy.
	}

	@Override
	public void playSoldierCard() 
	{
		System.out.println("Starting soldier");
		getPlayCardView().closeModal();
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) 
	{
		System.out.println("Starting year of plenty");
		server.playYearofPlenty("Year_of_Plenty", ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber(), resource1.name(), resource2.name());
	}

	@Override
	public void update(Observable o, Object arg)
	{

	}
}

