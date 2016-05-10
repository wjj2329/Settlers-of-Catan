package server.param;

public class LoadGameParam extends Param{

	String name;
	
	public LoadGameParam(String name) {
		this.name = name;
	}
	
	@Override
	public String getRequest() {
		return "{ name:\"" + name + "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
