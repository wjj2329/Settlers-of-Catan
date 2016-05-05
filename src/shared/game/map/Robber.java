package shared.game.map;

import shared.locations.HexLocation;

/**
 * @author Alex
 * Robber: Class to represent the Robber, which moves about
 * the map when 7s are rolled. The Robber then proceeds to 
 * purloin large sums of money (well, goods) from the selected player. 
 */
public class Robber 
{
	/**
	 * location: Represents the Robber's current location
	 * on the playing board. (i.e., which hex s/he is on)
	 */
	private HexLocation location = null;
	
	/**
	 * Robber constructor
	 * @param initialLocation: where the robber starts
	 * (should be on the Desert Hex). 
	 */
	public Robber(HexLocation initialLocation)
	{
		location = initialLocation;
	}
	
	/**
	 * canPlaceRobber: Determines whether or not the
	 * robber can be placed at the given HexLocation.
	 * @param loc: given hex location, can't be null,  can't place 
	 * if robber already exists on said tile. 
	 */
	boolean canPlaceRobber(HexLocation loc)
	{
		return false;
	}

	/**
	 * Getters and Setters follow:
	 */
	public HexLocation getLocation() 
	{
		return location;
	}

	public void setLocation(HexLocation location) 
	{
		this.location = location;
	}
	
	
}
