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

	}

	@Override
	public void decreaseResourceAmount(ResourceType resource)
	{

	}




	@Override
	public void increaseResourceAmount(ResourceType resource)
	{

	}





	public void canDoTrade() {

	}

	public void setAmount(ResourceType key, int amount) {

	}

	@Override
	public void sendTradeOffer() {


	}

	@Override
	public void setPlayerToTradeWith(int playerIndex)
	{

	}

	@Override
	public void setResourceToReceive(ResourceType resource)
	{

	}

	@Override
	public void setResourceToSend(ResourceType resource)
	{

	}

	@Override
	public void unsetResource(ResourceType resource) {

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
				//start it
			}
		}
		else
			this.getTradeView().enableDomesticTrade(false);



	}
}

