package server.param.games;

import server.param.Param;


public class JoinGameParam extends Param {

	int gameID;
	String color;
	
	public  JoinGameParam(int gameID, String color){
		this.gameID = gameID;
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
