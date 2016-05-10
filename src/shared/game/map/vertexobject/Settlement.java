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
	 * direction: the direction that the settlement is in.
	 */
	String direction = "";
	
	/**
	 * Constructor
	 * @param buildingCost: cost of building the settlement
	 * @pre buildingCost is nonnegative
	 * @post the superclass is also constructed
	 */
	public Settlement(int buildingCost) 
	{
		super(buildingCost);
	}
	
	/**
	 * Determines whether or not a settlement can be placed at
	 * the given location.
	 * @pre loc is not null
	 */
	public boolean canPlaceSettlement(HexLocation loc)
	{
		return false;
	}

	public HexLocation getLocation()
	{
		return location;
	}

	public void setLocation(HexLocation location)
	{
		this.location = location;
	}
}
