package server.param;

public class PlayMonumentParam extends Param{

	String type;
	int playerIndex;

	public PlayMonumentParam(String type, int playerIndex) {
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
