package shared.game.map;

import java.util.ArrayList;

import shared.game.map.Hex.Hex;
import shared.game.map.Hex.Road;
import shared.game.map.vertexobject.VertexObject;

/**
 * @author Alex
 * Map class: Represents the map of the Catan World - in other words,
 * the playing board. 
 */
public class Map 
{
	/**
	 * hexes: List of all the hexes in the map.
	 * This will stay the same throughout the game.
	 */
	ArrayList<Hex> hexes = new ArrayList<>();
	
	/**
	 * ports: List of all the ports on the map.
	 * This will stay the same throughout the game.
	 */
	ArrayList<Port> ports = new ArrayList<>();
	
	/**
	 * roads: List of all the roads on the map.
	 * This will be dynamically updated as players 
	 * build roads at different map locations.
	 */
	ArrayList<Road> roads = new ArrayList<>();
	
	/**
	 * settlements: List of all current settlements on map.
	 * Dynamically updated.
	 */
	ArrayList<VertexObject> settlements = new ArrayList<>();
	
	/**
	 * cities: List of all current cities on the map.
	 * Also dynamically updated. 
	 */
	ArrayList<VertexObject> cities = new ArrayList<>();
	
	/**
	 * Radius: Radius of the map.
	 */
	int radius = 0;
	
	/**
	 * 
	 */
	
	
	/**
	 * Default Map constructor.
	 * @param radius: radius of the map.
	 * @pre
	 * @post
	 */
	public Map(int radius)
	{
		this.radius = radius;
	}
	
	private boolean canPlaceHex()
	{
		return false;
	}
}
