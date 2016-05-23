package client.domestic;

import client.State.State;
import client.data.PlayerInfo;
import client.model.Model;
import client.model.ModelFacade;
import org.json.JSONException;
import org.json.JSONObject;
import shared.definitions.*;
import client.base.*;
import client.misc.*;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;

import java.util.*;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController,Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		ModelFacade.facadeCurrentGame.addObserver(this);

		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
	}
	private HashMap<ResourceType, String> resourceMap = new HashMap<>();
	private HashMap<ResourceType, Integer> sendMap = new HashMap<>();
	private HashMap<ResourceType, Integer> receiveMap = new HashMap<>();
	private int tradeWithPlayer;
	private int tradeBrick;
	private int tradeGrain;
	private int tradeOre;
	private int tradeSheep;
	private int tradeWood;

	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {

		ArrayList<PlayerInfo> list = new ArrayList<>();

		for (Index playerid : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
		{
			if(list.size()<5) {
				Player player = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(playerid);
				PlayerInfo myinfo = new PlayerInfo();
				myinfo.setId(player.getPlayerID().getNumber());
				myinfo.setName(player.getName());
				myinfo.setColor(player.getColor());
				myinfo.setPlayerIndex(player.getPlayerIndex().getNumber());
				list.add(myinfo);
			}
		}
		PlayerInfo[] playerInfo = new PlayerInfo[list.size()];
		getTradeOverlay().setPlayers(list.toArray(playerInfo));
		initTrade();
		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource)
	{
		String state = (String) resourceMap.get(resource);
		int amount = 0;
		if (state.equals("SEND")) {
			amount = (int) sendMap.get(resource);
			if (amount != 0) {
				amount--;
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
			} else {
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
			}
			sendMap.put(resource, amount);
		} else if (state.equals("RECEIVE")) {
			amount = (int) receiveMap.get(resource);
			if (amount != 0) {
				amount--;
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
			} else {
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
			}
			receiveMap.put(resource, amount);
		}
		canDoTrade();
		getTradeOverlay().setResourceAmount(resource,Integer.toString(amount));
	}

	private boolean atMaxAmount(ResourceType type, int amount) {
		boolean atMax = false;
		ResourceList resources = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getResources();
		switch(type) {
			case BRICK:
				if(amount >= resources.getBrick()) {
					atMax = true;
				}
				break;
			case WHEAT:
				if(amount >= resources.getWheat()) {
					atMax = true;
				}
				break;
			case ORE:
				if(amount >= resources.getOre()) {
					atMax = true;
				}
				break;
			case SHEEP:
				if(amount >= resources.getSheep()) {
					atMax = true;
				}
				break;
			case WOOD:
				if(amount >= resources.getWood()) {
					atMax = true;
				}
				break;
		}
		return atMax;
	}


	@Override
	public void increaseResourceAmount(ResourceType resource)
	{
		String state = resourceMap.get(resource);
		int amount = 0;
		if (state.equals("SEND")) {
			amount = (int) sendMap.get(resource);
			amount++;
			sendMap.put(resource, amount);
			if (atMaxAmount(resource, amount)) {
				getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
			} else {
				getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
			}
		} else if (state.equals("RECEIVE")) {
			amount = (int) receiveMap.get(resource);
			amount++;
			receiveMap.put(resource, amount);
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		canDoTrade();
		getTradeOverlay().setResourceAmount(resource, Integer.toString(amount));
	}

	private void initTrade() {
		resourceMap.put(ResourceType.BRICK, "NONE");
		resourceMap.put(ResourceType.WHEAT, "NONE");
		resourceMap.put(ResourceType.ORE, "NONE");
		resourceMap.put(ResourceType.SHEEP, "NONE");
		resourceMap.put(ResourceType.WOOD, "NONE");

		sendMap.put(ResourceType.BRICK, 0);
		sendMap.put(ResourceType.WHEAT, 0);
		sendMap.put(ResourceType.ORE, 0);
		sendMap.put(ResourceType.SHEEP, 0);
		sendMap.put(ResourceType.WOOD, 0);

		receiveMap.put(ResourceType.BRICK, 0);
		receiveMap.put(ResourceType.WHEAT, 0);
		receiveMap.put(ResourceType.ORE, 0);
		receiveMap.put(ResourceType.SHEEP, 0);
		receiveMap.put(ResourceType.WOOD, 0);

	}

	public void canDoTrade() {
		if (resourceMap.containsValue("RECEIVE") && resourceMap.containsValue("SEND") && tradeWithPlayer != -1) {
			getTradeOverlay().setStateMessage("Trade!");
			getTradeOverlay().setTradeEnabled(true);
		} else {
			getTradeOverlay().setStateMessage("set the trade you want to make");
			getTradeOverlay().setTradeEnabled(false);
		}
	}

	public void setAmount(ResourceType key, int amount) {
		switch(key) {
			case BRICK:
				tradeBrick=amount;
				break;
			case WHEAT:
				tradeGrain=amount;
				break;
			case ORE:
				tradeOre=amount;
				break;
			case SHEEP:
				tradeSheep=amount;
				break;
			case WOOD:
				tradeWood=amount;
				break;
		}
	}

	@Override
	public void sendTradeOffer() {
			try {
				tradeBrick =0;
				tradeGrain = 0;
				tradeOre = 0;
				tradeSheep = 0;
				tradeWood = 0;
				for (Map.Entry<ResourceType, String> entry : resourceMap.entrySet()) {
					ResourceType key = entry.getKey();
					String value = entry.getValue();
					if (value.equals("SEND")) {
						int amount = ((int) sendMap.get(key));
						setAmount(key, amount);
					} else if (value.equals("RECEIVE")) {
						int amount = (int) receiveMap.get(key);
						setAmount(key, (amount*-1));
					}
				}

			} catch (Exception e) {

			}
			ResourceList list = new ResourceList(tradeBrick, tradeOre, tradeSheep, tradeGrain, tradeWood);
			String response=ModelFacade.facadeCurrentGame.getServer().offerTrade("offerTrade",ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(),list,tradeWithPlayer).getResponse();
			System.out.println("MY response from the server when sending offer is this "+response);

		if(!response.equals("")) {
			getTradeOverlay().closeModal();
			getWaitOverlay().showModal();
			try {
				ModelFacade.facadeCurrentGame.updateFromJSON(new JSONObject(response));
			} catch (JSONException e) {
				e.printStackTrace();
			}


		}
		else
			System.out.println("failure");
			getTradeOverlay().closeModal();

	}

	@Override
	public void setPlayerToTradeWith(int playerIndex)
	{
		tradeWithPlayer = playerIndex;
		canDoTrade();
	}

	@Override
	public void setResourceToReceive(ResourceType resource)
	{
		resourceMap.put(resource, "RECEIVE");
		receiveMap.put(resource, 0);
		getTradeOverlay().setResourceAmount(resource, Integer.toString(0));
		int amount = (int) receiveMap.get(resource);
		if (amount <= 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		} else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		canDoTrade();
	}

	@Override
	public void setResourceToSend(ResourceType resource)
	{
		resourceMap.put(resource, "SEND");
		sendMap.put(resource, 0);
		getTradeOverlay().setResourceAmount(resource, Integer.toString(0));
		int amount = (int) sendMap.get(resource);
		if (atMaxAmount(resource, amount)) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
		} else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		canDoTrade();
	}

	@Override
	public void unsetResource(ResourceType resource) {
		resourceMap.put(resource, "NONE");
		receiveMap.put(resource, 0);
		sendMap.put(resource, 0);
		getTradeOverlay().setResourceAmount(resource, Integer.toString(0));
		canDoTrade();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept)
	{
		String test=ModelFacade.facadeCurrentGame.getServer().acceptTrade("acceptTrade",ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(),willAccept).getResponse();
		try {
			ModelFacade.facadeCurrentGame.updateFromJSON(new JSONObject(test));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		getAcceptOverlay().closeModal();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getName())&& ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.GamePlayingState))
		{

			if (ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getResources().size() == 0)
			{
				this.getTradeView().enableDomesticTrade(false);
			}
			else
			{
				this.getTradeView().enableDomesticTrade(true);
				initTrade();
			}
		}
		else
			this.getTradeView().enableDomesticTrade(false);

		if(ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getName())&& ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.GamePlayingState))
		{
			if (ModelFacade.facadeCurrentGame.currentgame.getMytradeoffer() != null) {
				System.out.println("THE TWO NUMBERS I GET ARE "+ModelFacade.facadeCurrentGame.currentgame.getMytradeoffer()+" and "+ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber());
				if (ModelFacade.facadeCurrentGame.currentgame.getMytradeoffer().getReceiver() == ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber()) {
					ResourceList myresources = ModelFacade.facadeCurrentGame.currentgame.getMytradeoffer().getMylist();
					if (myresources.getBrick() != 0) {
						if (myresources.getBrick() < 0) {
							getAcceptOverlay().addGetResource(ResourceType.BRICK, myresources.getBrick() * -1);
						} else
							getAcceptOverlay().addGetResource(ResourceType.BRICK, myresources.getBrick());
					}
					if (myresources.getOre() != 0) {
						if (myresources.getOre() < 0) {
							getAcceptOverlay().addGetResource(ResourceType.ORE, myresources.getOre() * -1);
						} else
							getAcceptOverlay().addGetResource(ResourceType.ORE, myresources.getOre());
					}
					if (myresources.getSheep() != 0) {
						if (myresources.getSheep() < 0) {
							getAcceptOverlay().addGetResource(ResourceType.SHEEP, myresources.getSheep() * -1);
						} else
							getAcceptOverlay().addGetResource(ResourceType.SHEEP, myresources.getSheep());
					}
					if (myresources.getWheat() != 0) {
						if (myresources.getWheat() < 0) {
							getAcceptOverlay().addGetResource(ResourceType.WHEAT, myresources.getWheat() * -1);
						} else
							getAcceptOverlay().addGetResource(ResourceType.WHEAT, myresources.getWheat());
					}
					if (myresources.getWood() != 0) {
						if (myresources.getWood() < 0) {
							getAcceptOverlay().addGetResource(ResourceType.WOOD, myresources.getWood() * -1);
						} else
							getAcceptOverlay().addGetResource(ResourceType.WOOD, myresources.getWood());
					}
					getAcceptOverlay().showModal();
				}
			}
		}


	}
}

