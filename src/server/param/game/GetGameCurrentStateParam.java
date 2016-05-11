package server.param.game;

import server.param.Param;


public class GetGameCurrentStateParam extends Param{
	//Needs Cookie
	
	@Override
	public String getRequest() {
		return "";
	}

	@Override
	public String getRequestType() {
		return GET;
	}


}
