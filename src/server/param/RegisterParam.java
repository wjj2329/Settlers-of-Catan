package server.param;


public class RegisterParam extends Param{
	
	String username;
	String password;
	

	public RegisterParam(String username, String password){
		assert username != null;
		assert username.length() > 0;
		assert password != null;
		assert password.length() > 0; 
		
		this.username = username;
		this.password = password; 
	}
	
	
	@Override
	public String getRequest() {
		return "{username:\"" + username + "\", "+
				"password: \"" + password+ "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
