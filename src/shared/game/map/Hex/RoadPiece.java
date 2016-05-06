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
	 * @custom.mytag1 pre: buildingCost is greater than 0. 
	 * @custom.mytag2 post: same.
	 */
	public RoadPiece(int buildingCost)
	{
		this.buildingCost = buildingCost;
	}
	
	/**
	 * Determines whether or not we can place a road piece
	 * at a particular given location. 
	 * @param loc: the location we are querying.
	 * @custom.mytag1 pre: loc is not null
	 * @custom.mytag2 post: same as pre.
	 * @exception: throws exception if hex location is not found
	 */
	public boolean canPlaceRoadAtLoc(HexLocation loc)
	{
		return false;
	}
}
