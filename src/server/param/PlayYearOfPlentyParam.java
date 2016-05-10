package server.param;


public class PlayYearOfPlentyParam extends Param{

	String type;
	int playerIndex;
	String resource1;
	String resource2;

	public PlayYearOfPlentyParam(String type, int playerIndex, String resource1, String resource2) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.resource1 = resource1;
		this.resource2 = resource2;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\", "+ 
				"resource1:\"" +resource1 + "\", "+ 
				"resource2:\"" +resource2 + "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
