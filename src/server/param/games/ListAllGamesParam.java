package server.param.games;

import server.param.Param;


public class ListAllGamesParam extends Param {

	@Override
	public String getRequest(){
		return "";
	}

	@Override
	public String getRequestType() {
		return GET;
	}

}
