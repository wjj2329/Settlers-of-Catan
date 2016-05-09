package shared.game.map;

import java.util.ArrayList;

import client.main.Catan;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.Road;
import shared.game.map.vertexobject.VertexObject;
import shared.locations.HexLocation;

/**
 * @author Alex
 * Map class: Represents the map of the Catan world - in other words,
 * the playing board. 
 * 
 * Need to change the name to CatanMap so no confusion is present.
 * My b
 */
public class CatanMap 
{
	/**
	 * hexes: List of all the hexes in the map.
	 * This will stay the same throughout the game.
	 */
	ArrayList<Hex> hexes = new ArrayList<>();

	public ArrayList<Port> getPorts() {
		return ports;
	}

	public void setPorts(ArrayList<Port> ports) {
		this.ports = ports;
	}

	public ArrayList<Road> getRoads() {
		return roads;
	}

	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}

	public ArrayList<VertexObject> getCities() {
		return cities;
	}

	public void setCities(ArrayList<VertexObject> cities) {
		this.cities = cities;
	}

	public ArrayList<Hex> getHexes() {
		return hexes;
	}

	public void setHexes(ArrayList<Hex> hexes) {
		this.hexes = hexes;
	}

	public ArrayList<VertexObject> getSettlements() {
		return settlements;
	}

	public void setSettlements(ArrayList<VertexObject> settlements) {
		this.settlements = settlements;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

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
	 * robber: The robber that steals things.
	 */
	private Robber robber = null;
	
	
	/**
	 * Default Map constructor.
	 * @param : radius of the map.
	 * @param desertHexLoc: where the robber should be initially
	 * (in the desert). 
	 * @pre radius should be greater than 0. 
	 * 						desertHexLoc should not be null.
	 * 						we should be able to place Hexes in their corresponding locations.
	 * @post robber should not be null.
	 * 						robber should be placed on the desert hex tile.
	 */

	public void SetDesertHexLoc(HexLocation desertHexLoc)
	{
		Robber.getSingleton().setLocation(desertHexLoc);
		this.desertHexLoc=desertHexLoc;
	}

	public CatanMap(ArrayList<Hex> hexes, int radius)
	{
		this.hexes=hexes;
		this.radius=radius;
	}

	public CatanMap(int radius)
	{
		this.radius = radius;

	}
	HexLocation desertHexLoc;

	
	/**
	 * canPlaceHex: Determines whether or not we can place 
	 * a hex in the given location.
	 *  
	 * NOTE: Will need to edit this with the appropriate parameter!
	 * 
	 */
	private boolean canPlaceHex()
	{
		return false;
	}
}
