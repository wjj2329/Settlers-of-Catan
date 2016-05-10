package server.param;


public class FinishTurnParam extends Param{

	String type;
	int playerIndex;

	public FinishTurnParam(String type, int playerIndex) {
		this.type = type;
		this.playerIndex = playerIndex;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}
}
