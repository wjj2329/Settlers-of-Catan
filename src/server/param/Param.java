package server.param;


public abstract class Param {

	String userCookie;
	String gameCookie; 
	
	
	public abstract String getRequest();
	
	
	public String getCookies(){
		String cookies="";
		if(userCookie != null){
			cookies += "catan.user=" + userCookie +";";
		}
		if(gameCookie != null){
			cookies += "catan.game=" + gameCookie;
		}
		return cookies;
	}
	
	public abstract String getRequestType();
	
}
