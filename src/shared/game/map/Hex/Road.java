package shared.game.map.Hex;

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
}
