package server.ourserver.commands;

import client.model.TradeOffer;
import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.game.CatanGame;
import shared.game.ResourceList;

import java.io.FileNotFoundException;

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
	public Object execute() throws FileNotFoundException, JSONException {
		
		CatanGame game = ServerFacade.getInstance().getGameByID(gameid);
		
		TradeOffer mytradeoffer = new TradeOffer();
		mytradeoffer.setMylist(offer);
		mytradeoffer.setReceiver(receiver);
		mytradeoffer.setSender(playerIndex);
		//System.out.println(offer.toString());
		game.setMytradeoffer(mytradeoffer);
		game.getModel().setVersion(game.getModel().getVersion()+1);
		
		return null;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," + TextDBGameManagerDAO.commandNumber+":"+"{" +
				"\"type\": \"OfferTradeCommand\"" +
				", \"gameid\":" + gameid +
				", \"playerIndex\":" + playerIndex +
				", \"ore\":" +"\""+ offer.getOre()+"\"" +
				", \"wheat\":"+"\""+offer.getWheat()+"\""+
				", \"brick\":"+"\""+offer.getBrick()+"\""+
				", \"wood\":"+"\""+offer.getWheat()+"\""+
				", \"sheep\":"+"\""+offer.getSheep()+"\""+
				", \"receiver\":" + receiver +
				"}";
	}
	@Override
	public String toJSON() {
		return "{" +
				"\"type\": \"OfferTradeCommand\"" +
				", \"gameid\":" + gameid +
				", \"playerIndex\":" + playerIndex +
				", \"ore\":" +"\""+ offer.getOre()+"\"" +
				", \"wheat\":"+"\""+offer.getWheat()+"\""+
				", \"brick\":"+"\""+offer.getBrick()+"\""+
				", \"wood\":"+"\""+offer.getWheat()+"\""+
				", \"sheep\":"+"\""+offer.getSheep()+"\""+
				", \"receiver\":" + receiver +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;

	}
}
