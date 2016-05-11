package shared.game.map.Hex;

import shared.game.map.Index;
import shared.locations.EdgeLocation;

import java.util.ArrayList;

/**
 * @author Alex
 * RoadPiece class: represents the roads that players can build.
 * Made up of RoadPieces.
 *
 * I found that the Road class was largely unnecessary. We can just use the RoadPiece.
 * Then, the Player will have an arrayList of RoadPieces.
 */
public class RoadPiece
{

	private EdgeLocation location = null;

	private Index playerWhoOwnsRoad = null;
	
	/**
	 * RoadPiece constructor
	 */
	public RoadPiece()
	{
		
	}
	
	/**
	 * canBuildRoad: function to determine whether or not the road can be built
	 * it's not used yet whoops
	 */
	public boolean canBuildRoad()
	{
		return false;
	}

	public Index getPlayerWhoOwnsRoad()
	{
		return playerWhoOwnsRoad;
	}

	public void setPlayerWhoOwnsRoad(Index playerWhoOwnsRoad)
	{
		this.playerWhoOwnsRoad = playerWhoOwnsRoad;
	}

	public EdgeLocation getLocation()
	{
		return location;
	}

	public void setLocation(EdgeLocation location)
	{
		this.location = location;
	}
}
