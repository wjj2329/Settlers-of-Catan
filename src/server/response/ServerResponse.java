package server.response;


public class ServerResponse {
	
	String response; 
	int responseCode; 
	String userCookie;
	String gameCookie; 
	
	public ServerResponse(String response, int responseCode){
		this.response = response;
		this.responseCode = responseCode;
	}
	
	public ServerResponse() {
		response= "";
		responseCode = 400;
	}

	public void setCookie(String cookie){
		if(cookie!=null){
			if(cookie.contains("catan.user")){
				userCookie = cookie.substring(cookie.indexOf("=")+1, cookie.indexOf(";"));
				
			}
			else if(cookie.contains("catan.game")){
				gameCookie =cookie.substring(cookie.indexOf("=")+1, cookie.indexOf(";"));
			}
			
			
		}
	}
	
	public void setResponseCode(int responseCode){
		this.responseCode = responseCode;
	}
	
	public String getUserCookie(){
		return userCookie;
	}
	
	public String getGameCookie(){
		return gameCookie;
	}
	
	public int getResponseCode(){
		return responseCode;
	}
	

}
