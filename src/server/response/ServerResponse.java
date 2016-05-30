package server.response;


public class ServerResponse {
	
	String response; 
	int responseCode; 
	String userCookie;
	String gameCookie;

	public ServerResponse(int responseCode, String response){
		//System.out.println(response);
		this.response = response;
		this.responseCode = responseCode;
	}
	
	public ServerResponse() {
		response= "";
		responseCode = 400;
	}

	public void setCookie(String cookie){
		if(cookie!=null){
			//System.out.println("We be setting cookie");
			if(cookie.contains("catan.user")){
				userCookie = cookie.substring(cookie.indexOf("=")+1, cookie.indexOf(";"));
				
			}
			else if(cookie.contains("catan.game")){
				gameCookie =cookie.substring(cookie.indexOf("=")+1, cookie.indexOf(";"));
				//System.out.println("HERE IS THE GAME COOKIE HOMEBRO: " + gameCookie);
			}
			
			
		}
		else
		{
			//System.out.println("This cookie was null");
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
	
	public String getResponse(){
		return response;
	}
	

}
