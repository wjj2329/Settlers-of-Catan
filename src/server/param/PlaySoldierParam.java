package server.param;

import shared.locations.HexLocation;

public class PlaySoldierParam extends Param{

	String type;
	int playerIndex;
	HexLocation location;
	int victimIndex;

	public PlaySoldierParam(String type, int playerIndex, HexLocation location, int victimIndex) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.location = location;
		this.victimIndex = victimIndex;
	}
	
	@Override
	public String getRequest() {
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\", "+
				"victimIndex:\"" + victimIndex + "\", "+
				"location:{" +
					"x:"+ String.valueOf(location.getX()) + ","+ 
					"y:"+ String.valueOf(location.getY()) + "}"+ 
				"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
