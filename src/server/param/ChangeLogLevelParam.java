package server.param;

public class ChangeLogLevelParam extends Param{

	
	String logLevel;
	
	public ChangeLogLevelParam(String logLevel) {
		this.logLevel = logLevel;
	}
	
	@Override
	public String getRequest() {
		return "{logLevel: \"" + logLevel + "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
