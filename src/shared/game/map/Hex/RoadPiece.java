package shared.game.map.Hex;

import shared.locations.HexLocation;

/**
 * @author Alex
 * RoadPiece: The individual components that make up a road.
 * They are bought and placed one at a time. 
 */
public class RoadPiece 
{
	/**
	 * BuildingCost: Price of constructing the road piece.
	 */
	private int buildingCost = 0;
	
	/**
	 * Constructor:
	 */
	public RoadPiece(int buildingCost)
	{
		this.buildingCost = buildingCost;
	}
	
	/**
	 * Determines whether or not we can place a road piece
	 * at a particular given location. 
	 * @param loc: the location we are querying.
	 */
	public boolean canPlaceRoadAtLoc(HexLocation loc)
	{
		return false;
	}
}
