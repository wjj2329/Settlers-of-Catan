package server.param;

public class SendChatParam extends Param{

	String type;
	int playerIndex;
	String content;
	
	public SendChatParam(String type, int playerIndex, String content) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.content = content;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\", "+
				"content:\""+ content+ "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}



}
