package server.param;

import shared.locations.EdgeLocation;

public class PlayRoadBuildingParam extends Param{
	
	String type;
	int playerIndex;
	EdgeLocation spot1;
	EdgeLocation spot2;

	public PlayRoadBuildingParam(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		this.type = type;
		this.playerIndex = playerIndex;
		this.spot1 = spot1;
		this.spot2 = spot2;
	}
	@Override
	public String getRequest() {
		String direction1 = getDirection(spot1);
		String direction2 = getDirection(spot2);
		
		return "{type:\"" + type + "\", "+
				"playerIndex:\"" + playerIndex + "\", "+
				"spot1:{" +
					"x:"+ spot1.getHexLoc().getX() + ","+ 
					"y:"+ spot1.getHexLoc().getY() + ","+ 
					"direction:"+ direction1 + "}," +
				"spot2:{" +
					"x:"+ spot2.getHexLoc().getX() + ","+ 
					"y:"+ spot2.getHexLoc().getY() + ","+ 
					"direction:"+ direction2 + "}," +
				"}";
	}

	public String getDirection(EdgeLocation roadLocation){
		switch(roadLocation.getDir()){
		case North:
			return "N";
		case NorthEast:
			return "NE";
		case NorthWest:
			return "NW";
		case South:
			return "S";
		case SouthEast:
			return "SE";
		case SouthWest:
			return "SW";
		default:
			return null;
		
		}
	}
	
	@Override
	public String getRequestType() {
		// TODO Auto-generated method stub
		return null;
	}


}
