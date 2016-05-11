package server.param.moves;

import server.param.Param;

public class MaritimeTradeParam extends Param{

	String type;
	int playerIndex;
	int ratio;
	String inputResource;
	String outputResource;

	public MaritimeTradeParam(String type, int playerIndex, int ratio, String inputResource, String outputResource) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = inputResource;
		this.outputResource = outputResource;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:" + playerIndex + ", "+ 
				"ratio:" + ratio + ", " +
				"inputResource:\"" +inputResource + "\", "+ 
				"outputResource:\"" +outputResource + "\"}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}

}
