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
	 * @custom.mytag1 pre: initialLocation is not null
	 * 						initialLocation is the Desert hex
	 * @custom.mytag2 post: same as above.
	 * @exception: Throws exception if initialLocation is an invalid location.
	 */
	public Robber(HexLocation initialLocation)
	{
		location = initialLocation;
	}
	
	/**
	 * robPlayer: Robs the player at the given index.
	 * @param playerToRob: the player to rob.
	 * @custom.mytag1 pre: The canBeRobbed() function for the player must return true.
	 * 						canPlaceRobber must also be true at the particular location of the player.
	 * 						Otherwise, the player cannot be robbed.
	 * @custom.mytag2 post: The player will be deficient of a particular resource.
	 * @exception: Throws exception if the index is invalid.
	 */
	public void robPlayer(Index playerToRob)
	{
		
	}
	
	/**
	 * canPlaceRobber: Determines whether or not the
	 * robber can be placed at the given HexLocation.
	 * @param loc: given hex location, can't be null, can't place 
	 * if robber already exists on said tile. 
	 * 
	 * @custom.mytag1 pre: loc is not null.
	 * @custom.mytag2 post: true if you can place robber there; false otherwise.
	 * @exception: throws if loc is not found.
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
