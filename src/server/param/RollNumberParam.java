package server.param;

public class RollNumberParam extends Param{

	String type;
	int playerIndex;
	int number;
	
	public RollNumberParam(String type, int playerIndex, int number) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.number = number;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\", "+
				"number:\""+ number+ "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
