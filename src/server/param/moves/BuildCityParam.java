package server.param.moves;

import server.param.Param;

import shared.locations.VertexLocation;

public class BuildCityParam extends Param{


	String type;
	int playerIndex;
	VertexLocation vertexLocation;
	
	public BuildCityParam(String type, int playerIndex, VertexLocation vertexLocation) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
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
				"playerIndex:" + playerIndex + ", "+
				"vertexLocation:{" +
					"x:"+ vertexLocation.getHexLoc().getX() + ","+ 
					"y:"+ vertexLocation.getHexLoc().getY() + ","+ 
					"direction:\""+ direction + "\"}" +
				"}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}

}
