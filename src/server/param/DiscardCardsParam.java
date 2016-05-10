package server.param;

import org.json.JSONObject;

import shared.game.ResourceList;

public class DiscardCardsParam extends Param{

	String type;
	int playerIndex;
	ResourceList discardedCards;
	
	public DiscardCardsParam(String type, int playerIndex, ResourceList discardedCards) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.discardedCards = discardedCards;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\", "+
				"discardedCards:{" +
					"brick:"+ discardedCards.getBrick() + ","+ 
					"ore:"+ discardedCards.getOre() + ","+ 
					"sheep:"+ discardedCards.getSheep() + ","+ 
					"wheat:"+ discardedCards.getWheat() + ","+ 
					"wood:"+ discardedCards.getWood() + "}"+ 
				"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
