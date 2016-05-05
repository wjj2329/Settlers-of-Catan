package shared.game.map.Hex;

import shared.game.map.Index;
import shared.locations.EdgeLocation;

/**
 * @author Alex
 * EdgeValue: class used to encapsulate edges of hexes
 */
public class EdgeValue 
{
	/**
	 * Index of the particular player who owns the EdgeValue
	 */
	private Index owner = null;
	
	/**
	 * location: Where the edge is located on the map
	 */
	private EdgeLocation location = null;
	
	/**
	 * Constructor
	 */
	public EdgeValue()
	{
		
	}
}
