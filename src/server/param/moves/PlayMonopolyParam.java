package server.param.moves;

import server.param.Param;

public class PlayMonopolyParam extends Param{

	String type;
	int playerIndex;
	String resource;

	public PlayMonopolyParam(String type, int playerIndex, String resource) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.resource = resource;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"resource:\"" +resource + "\", "+ 
				"playerIndex:" + playerIndex + "}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}

}
