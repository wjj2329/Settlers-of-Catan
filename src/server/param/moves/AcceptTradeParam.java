package server.param.moves;

import server.param.Param;

public class AcceptTradeParam extends Param{

	String type;
	int playerIndex;
	boolean willAccept;
	
	public AcceptTradeParam(String type, int playerIndex, boolean willAccept) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:" + playerIndex + ", "+
				"willAccept:"+ willAccept+ "}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}

}
