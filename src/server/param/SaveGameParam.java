package server.param;

public class SaveGameParam extends Param{

	int gameID;
	String fileName;
	
	
	public SaveGameParam(int gameID, String fileName) {
		this.gameID = gameID;
		this.fileName = fileName;
	}
	
	@Override
	public String getRequest() {
		return "{id:\"" + gameID + "\", "+
				"name: \"" + fileName + "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}
	
	

}
