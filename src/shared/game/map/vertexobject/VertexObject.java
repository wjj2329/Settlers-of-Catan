package shared.game.map.vertexobject;

import shared.game.map.Index;
import shared.locations.EdgeLocation;

/**
 * @author William
 * VertexObject class: Encapsulates Structure: Cities and Settlements.
 */
public class VertexObject 
{
	/**
	 * owner: index of the player who owns the vertexObject
	 */
	private Index owner = null;
	
	/**
	 * location: Where it is located on the map
	 */
	private EdgeLocation location = null;
	
	/**
	 * Constructor
	 */
	public VertexObject()
	{
		
	}
	
	/**
	 * Tests whether or not we can build our VertexObject
	 */
	private boolean ObjectAvaibletoBuild()
	{
		return true;
	}
}
