package server.param.user;

import server.param.Param;


public class LoginParam extends Param{
	
	String username;
	String password;
	
	public LoginParam(String username, String password){
		this.username = username;
		this.password = password; 
	}
	
	@Override
	public String getRequest() {
		return "{username:\"" + username + "\", " +
				"password: \"" + password+ "\"}";
	}
	
	@Override
	public String getRequestType(){
		return POST;
	}
	
	

	
}
