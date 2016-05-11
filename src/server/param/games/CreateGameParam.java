package server.param.games;

import server.param.Param;


public class CreateGameParam extends Param{
	
	String name;
	boolean randomTiles;
	boolean randomNumbers;
	boolean randomPorts;
		

	public CreateGameParam(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		assert name != null;
		assert name.length() > 0;
		
		this.name = name;
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
	}
	
	
	@Override
	public String getRequest() {
		return "{randomTiles:\"" + randomTiles + "\", "+
				"randomNumbers:\"" + randomNumbers + "\", "+
				"randomPorts:\""+ randomPorts+ "\", "+
				"name:\"" + name + "\"}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}

	
}
