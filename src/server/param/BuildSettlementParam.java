package server.param;

import shared.locations.VertexLocation;

public class BuildSettlementParam extends Param{

	String type;
	int playerIndex;
	VertexLocation vertexLocation;
	boolean free;
	
	public BuildSettlementParam(String type, int playerIndex, VertexLocation vertexLocation, boolean free) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
		this.free = free;
	}
	
	@Override
	public String getRequest() {
		String direction ="";
		switch(vertexLocation.getDir()){
		case West:
			direction = "W";
			break;
		case NorthEast:
			direction = "NE";
			break;
		case NorthWest:
			direction = "NW";
			break;
		case East:
			direction = "E";
			break;
		case SouthEast:
			direction = "SE";
			break;
		case SouthWest:
			direction = "SW";
			break;
		default:
			break;
		
		}
		
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\", "+
				"vertexLocation:{" +
					"x:"+ vertexLocation.getHexLoc().getX() + ","+ 
					"y:"+ vertexLocation.getHexLoc().getY() + ","+ 
					"direction:"+ direction + "}," +
				"free:\"" + free + "\"}";
	}

	@Override
	public String getRequestType() {
		return "POST";
	}

}
