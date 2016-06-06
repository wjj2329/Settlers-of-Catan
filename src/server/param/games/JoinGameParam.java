package server.param.games;

import server.param.Param;


public class JoinGameParam extends Param {

	int gameID;
	int userID;
	String color;
	
	public  JoinGameParam(int gameID, int userID, String color){
		this.gameID = gameID;
		this.userID = userID;
		this.color = color; 
		
	}

	/**
	 * User ID is not necessary here.
     */
	@Override
	public String getRequest() {
		return "{id:" + gameID +
				", color: \"" + color + "\"}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}
	

}
