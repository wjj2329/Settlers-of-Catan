package server.param.moves;

import server.param.Param;

import shared.game.ResourceList;

public class OfferTradeParam extends Param{

	String type;
	int playerIndex;
	ResourceList offer;
	int receiver;
	
	public OfferTradeParam(String type, int playerIndex, ResourceList offer,int receiver) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.receiver = receiver;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:" + playerIndex + ", "+
				"offer:{" +
					"brick:"+ offer.getBrick() + ","+ 
					"ore:"+ offer.getOre() + ","+ 
					"sheep:"+ offer.getSheep() + ","+ 
					"wheat:"+ offer.getWheat() + ","+ 
					"wood:"+ offer.getWood() + "},"+ 
				"receiver:"+receiver+"}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}

}
