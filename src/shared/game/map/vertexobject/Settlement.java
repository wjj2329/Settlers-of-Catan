package shared.game.map.vertexobject;

import shared.game.map.Index;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class Settlement
{
	private VertexLocation vertexLocation;
	private HexLocation hexLocation;
	/**
	 * Index: The playerID or playerIndex of the player who owns this settlement.
	 */
	private Index owner = new Index(1924);
	
	/**
	 * Location: Where this settlement resides on the map. 
	 */
	//HexLocation location = null;
	
	/**
	 * direction: the direction that the settlement is in.
	 */
	private String direction = "";

	private boolean canBuildFromMeInRound2 = true;
	
	/**
	 * Constructor
	 * @pre buildingCost is nonnegative
	 * @post the superclass is also constructed
	 */
	public Settlement(HexLocation hexLocation, VertexLocation vertexLocation, Index owner)
	{
		this.hexLocation=hexLocation;
		this.vertexLocation=vertexLocation;
		this.owner=owner;
		//Log.i("Index for owner is now " + owner.getNumber());
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

	public HexLocation getHexLocation()
	{
		return hexLocation;
	}

	public void setLocation(HexLocation location)
	{
		this.hexLocation = location;
	}

	public VertexLocation getVertexLocation()
	{
		return vertexLocation;
	}

	public void setVertexLocation(VertexLocation vertexLocation)
	{
		this.vertexLocation = vertexLocation;
	}

	public Index getOwner()
	{
		return owner;
	}

	public void setOwner(Index owner)
	{
		this.owner = owner;
	}

	public boolean canBuildFromMeInRound2()
	{
		return canBuildFromMeInRound2;
	}

	public void setCanBuildFromMeInRound2(boolean canBuildFromMeInRound2)
	{
		this.canBuildFromMeInRound2 = canBuildFromMeInRound2;
	}
}
