package shared.game.map.vertexobject;

import shared.game.map.Index;
import shared.locations.HexLocation;

public class Settlement extends Structure 
{
	/**
	 * Index: The playerID or playerIndex of the player who owns this settlement.
	 */
	private Index owner = null;
	
	/**
	 * Location: Where this settlement resides on the map. 
	 */
	HexLocation location = null;
	
	/**
	 * direction:
	 */
	String direction = "";
	
	/**
	 * Constructor
	 * @param buildingCost: cost of building the settlement
	 */
	public Settlement(int buildingCost) 
	{
		super(buildingCost);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Determines whether or not a settlement can be placed at
	 * the given location.
	 */
	public boolean canPlaceSettlement(HexLocation loc)
	{
		return false;
	}
}
