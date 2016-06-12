package server.ourserver.commands;


import javax.annotation.Resource;

import client.model.TradeOffer;
import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.player.Player;

import java.io.FileNotFoundException;

/**
 * The AcceptTradeCommand class
 */
public class AcceptTradeCommand implements ICommand {

	
	int gameid; 
	int playerIndex;
	boolean willAccept; 
	
	public AcceptTradeCommand(int gameid, int playerIndex, boolean willAccept) {
		this.gameid = gameid; 
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
	}
	
	/**
	 * Executes the task
	 * 	depending if the player accepts the trade they swap the resources. 
	 */
	@Override
	public Object execute() throws FileNotFoundException, JSONException {
		
		CatanGame game = ServerFacade.getInstance().getGameByID(gameid);
		
		TradeOffer tradeOffer = game.getMytradeoffer(); 
		ResourceList offer = tradeOffer.getMylist();

		//System.out.println("THE LIST " + offer.toString());
		if(willAccept)
		{
			for(Player player : game.getMyplayers().values())
			{
				if(player.getPlayerIndex().getNumber() == tradeOffer.getSender()){
	
					//System.out.println("B4 SENDER: " + player.getName()+ " " + player.getResources().toString());
					player.getResources().setBrick(player.getResources().getBrick() - offer.getBrick());
					player.getResources().setOre(player.getResources().getOre() - offer.getOre());
					player.getResources().setSheep(player.getResources().getSheep() - offer.getSheep());
					player.getResources().setWheat(player.getResources().getWheat() - offer.getWheat());
					player.getResources().setWood(player.getResources().getWood() - offer.getWood());
					//System.out.println("sENDER: " + player.getName()+ " " + player.getResources().toString());
				}
				if(player.getPlayerIndex().getNumber() == tradeOffer.getReceiver()){
					//System.out.println("B4 RECEIVER: " + player.getName()+ " " + player.getResources().toString());
					player.getResources().setBrick(player.getResources().getBrick() + offer.getBrick());
					player.getResources().setOre(player.getResources().getOre() + offer.getOre());
					player.getResources().setSheep(player.getResources().getSheep() + offer.getSheep());
					player.getResources().setWheat(player.getResources().getWheat() + offer.getWheat());
					player.getResources().setWood(player.getResources().getWood() + offer.getWood());
					//System.out.println("RECEIVER: " + player.getName()+ " " + player.getResources().toString());
					
				}
			}
		}
		
		game.setMytradeoffer(null);
		game.getModel().setVersion(game.getModel().getVersion() + 1);
	
		return null;
	}


	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return ","+TextDBGameManagerDAO.commandNumber+":"+"{" +
				"\"type\":"+"\"AcceptTradeCommand\""+
				",\"gameid\":" + gameid +
				", \"playerIndex\":" + playerIndex +
				", \"willAccept\":" + willAccept +
				"}";

	}

	@Override
	public String toJSON() {
		return "{" +
				"\"type\":"+"\"AcceptTradeCommand\""+
				",\"gameid\":" + gameid +
				", \"playerIndex\":" + playerIndex +
				", \"willAccept\":" + willAccept +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;

	}
}
