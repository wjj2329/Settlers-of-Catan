package shared.game.map.Hex;

import shared.game.map.Index;
import shared.locations.EdgeLocation;

import java.util.ArrayList;

/**
 * @author Alex
 * Road class: represents the roads that players can build.
 * Made up of RoadPieces. 
 */
public class Road 
{
	/**
	 * roadPieces: All the road pieces that make up this particular Road.
	 */
	private ArrayList<RoadPiece> roadPieces = new ArrayList<>();

	private EdgeLocation location = null;

	private Index playerWhoOwnsRoad = null;
	
	/**
	 * Road constructor
	 */
	public Road()
	{
		
	}
	
	/**
	 * canBuildRoad: function to determine whether or not the road can be built
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
}
