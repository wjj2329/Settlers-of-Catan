package server.ourserver.commands;

import client.model.TradeOffer;
import server.ourserver.ServerFacade;
import shared.game.CatanGame;
import shared.game.ResourceList;

/**
 * The OfferTradeCommand class
 */
public class OfferTradeCommand implements ICommand {

	int gameid;
	int playerIndex;
	ResourceList offer;
	int receiver;
	
	public OfferTradeCommand(int gameid, int playerIndex, ResourceList offer, int receiver ) {
		this.gameid = gameid;
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.receiver = receiver;
	}
	/**
	 * Executes the task:
	 * 	player offering their trade offer to specified player
	 */
	@Override
	public Object execute() {
		
		CatanGame game = ServerFacade.getInstance().getGameByID(gameid);
		
		TradeOffer mytradeoffer = new TradeOffer();
		mytradeoffer.setMylist(offer);
		mytradeoffer.setReceiver(receiver);
		mytradeoffer.setSender(playerIndex);
		System.out.println(offer.toString());
		game.setMytradeoffer(mytradeoffer);
		game.getModel().setVersion(game.getModel().getVersion()+1);
		
		return null;
	}

}
