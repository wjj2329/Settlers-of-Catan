package server.param;

import java.util.HashMap;
import java.util.Map;

public abstract class Param {

	Map<String, String> headers;
	
	protected final String POST = "POST";
	protected final String GET = "GET";
	
	public abstract String getRequest();
	
	public void addHeader(String key, String value){
		if(headers==null){
			headers = new HashMap<String, String>();
		}
		headers.put(key, value);
	}
	
	public Map<String, String> getHeaders(){
		return headers;
	}
	
	public abstract String getRequestType();
	
}
