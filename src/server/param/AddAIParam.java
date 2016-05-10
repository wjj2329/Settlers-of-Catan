package server.param;

public class AddAIParam extends Param{

	
	String logLevel;
	
	public AddAIParam(String logLevel){
		this.logLevel = logLevel;
	}
	
	@Override
	public String getRequest() {
		return "{AIType: \"" + logLevel + "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}


}
