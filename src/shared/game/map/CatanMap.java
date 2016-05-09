package shared.game.map;

import java.util.ArrayList;

import client.main.Catan;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
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
		//this is the way the map will look by default if shuffling is not enabled. 
		hexes.add(new Hex(new HexLocation(-3, 2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-3, 1), HexType.WATER, new NumberToken(0), PortType.WOOD));
		hexes.add(new Hex(new HexLocation(-3, 0), HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-3, -1), HexType.WATER, new NumberToken(0), PortType.THREE));

		hexes.add(new Hex(new HexLocation(-2, -2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-2, -1), HexType.ORE, new NumberToken(5), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-2, 0), HexType.WHEAT, new NumberToken(2), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-2,1), HexType.WOOD, new NumberToken(6), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-2, 2), HexType.WATER, new NumberToken(0), PortType.BRICK));

		hexes.add(new Hex(new HexLocation(-1,3), HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-1, 2), HexType.ORE, new NumberToken(3), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-1, 1), HexType.SHEEP, new NumberToken(9), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-1, 0), HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-1, -1), HexType.BRICK, new NumberToken(8), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(-1, -2), HexType.WATER, new NumberToken(0), PortType.WHEAT));

		hexes.add(new Hex(new HexLocation(0,3), HexType.WATER,new NumberToken(0), PortType.THREE));
		hexes.add(new Hex(new HexLocation(0, 2), HexType.WHEAT, new NumberToken(8), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(0, 1), HexType.WOOD, new NumberToken(4), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(0, 0), HexType.WHEAT, new NumberToken(11), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(0, -1), HexType.WOOD, new NumberToken(3), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(0, -2),HexType.DESERT,new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(0, -3), HexType.WATER, new NumberToken(0), PortType.BLANK));

		hexes.add(new Hex(new HexLocation(1, -3), HexType.WATER, new NumberToken(0), PortType.ORE));
		hexes.add(new Hex(new HexLocation(1, -2), HexType.BRICK, new NumberToken(4), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(1, -1), HexType.ORE, new NumberToken(9), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(1, 0), HexType.BRICK, new NumberToken(5), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(1, 1), HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(1, 2), HexType.WATER, new NumberToken(0), PortType.BLANK));

		hexes.add(new Hex(new HexLocation(2, -2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(2, -1), HexType.WOOD, new NumberToken(11), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(2,0),HexType.SHEEP, new NumberToken(12), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(2,1),HexType.WHEAT, new NumberToken(6), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(2, 2), HexType.WATER,new NumberToken(0), PortType.THREE));

		hexes.add(new Hex(new HexLocation(3, 2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(3, 1), HexType.WATER, new NumberToken(0), PortType.SHEEP));
		hexes.add(new Hex(new HexLocation(3, 0), HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.add(new Hex(new HexLocation(3, -1), HexType.WATER, new NumberToken(0), PortType.THREE));

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
