package server.param.moves;

import server.param.Param;

import shared.locations.EdgeLocation;

public class BuildRoadParam extends Param{

	String type;
	int playerIndex;
	EdgeLocation roadLocation;
	boolean free;
	
	public BuildRoadParam(String type, int playerIndex, EdgeLocation roadLocation, boolean free) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.roadLocation = roadLocation;
		this.free = free;
	}
	
	@Override
	public String getRequest() {
		String direction ="";
		switch(roadLocation.getDir()){
		case North:
			direction = "N";
			break;
		case NorthEast:
			direction = "NE";
			break;
		case NorthWest:
			direction = "NW";
			break;
		case South:
			direction = "S";
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
				"roadLocation:{" +
					"x:"+ roadLocation.getHexLoc().getX() + ","+ 
					"y:"+ roadLocation.getHexLoc().getY() + ","+ 
					"direction:\""+ direction + "\"}," +
				"free:" + free + "}";
	}

	@Override
	public String getRequestType() {
		return POST;
	}

}
