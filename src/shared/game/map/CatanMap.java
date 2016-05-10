package shared.game.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private Map<HexLocation, Hex> hexes = new HashMap<>();

	/**
	 * ports: List of all the ports on the map.
	 * This will stay the same throughout the game.
	 */
	private ArrayList<Port> ports = new ArrayList<>();
	
	/**
	 * roads: List of all the roads on the map.
	 * This will be dynamically updated as players 
	 * build roads at different map locations.
	 */
	private ArrayList<Road> roads = new ArrayList<>();
	
	/**
	 * settlements: List of all current settlements on map.
	 * Dynamically updated.
	 */
	private ArrayList<VertexObject> settlements = new ArrayList<>();
	
	/**
	 * cities: List of all current cities on the map.
	 * Also dynamically updated. 
	 */
	private ArrayList<VertexObject> cities = new ArrayList<>();
	
	/**
	 * Radius: Radius of the map.
	 */
	private int radius = 0;
	
	/**
	 * robber: The robber that steals things.
	 */
	private Robber robber = null;

	/**
	 * location of the desertHex: we may end up not needing this.
	 */
	private HexLocation desertHexLoc = null;
	
	
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

	public CatanMap(Map<HexLocation, Hex> hexes, int radius)
	{
		this.hexes=hexes;
		this.radius=radius;
	}

	public CatanMap(int radius)
	{
		this.radius = radius;
		//this is the way the map will look by default if shuffling is not enabled.
		HexLocation hexLoc1 = new HexLocation(-3, 2);
		HexLocation hexLoc2 = new HexLocation(-3, 1);
		HexLocation hexLoc3 = new HexLocation(-3, 0);
		HexLocation hexLoc4 = new HexLocation(-3, -1);

		HexLocation hexLoc5 = new HexLocation(-2, -2);
		HexLocation hexLoc6 = new HexLocation(-2, -1);
		HexLocation hexLoc7 = new HexLocation(-2, 0);
		HexLocation hexLoc8 = new HexLocation(-2, 1);
		HexLocation hexLoc9 = new HexLocation(-2, 2);

		HexLocation hexLoc10 = new HexLocation(-1, 3);
		HexLocation hexLoc11 = new HexLocation(-1, 2);
		HexLocation hexLoc12 = new HexLocation(-1, 1);
		HexLocation hexLoc13 = new HexLocation(-1, 0);
		HexLocation hexLoc14 = new HexLocation(-1, -1);
		HexLocation hexLoc15 = new HexLocation(-1, -2);

		HexLocation hexLoc16 = new HexLocation(0,3);
		HexLocation hexLoc17 =  new HexLocation(0, 2);
		HexLocation hexLoc18 = new HexLocation(0, 1);
		HexLocation hexLoc19 = new HexLocation(0, 0);
		HexLocation hexLoc20 = new HexLocation(0, -1);
		HexLocation hexLoc21 = new HexLocation(0, -2);
		HexLocation hexLoc22 = new HexLocation(0, -3);

		HexLocation hexLoc23 = new HexLocation(1, -3);
		HexLocation hexLoc24 = new HexLocation(1, -2);
		HexLocation hexLoc25 = new HexLocation(1, -1);
		HexLocation hexLoc26 = new HexLocation(1, 0);
		HexLocation hexLoc27 = new HexLocation(1, 1);
		HexLocation hexLoc28 = new HexLocation(1, 2);

		HexLocation hexLoc29 = new HexLocation(2, -2);
		HexLocation hexLoc30 = new HexLocation(2, -1);
		HexLocation hexLoc31 = new HexLocation(2,0);
		HexLocation hexLoc32 = new HexLocation(2,1);
		HexLocation hexLoc33 = new HexLocation(2, 2);

		HexLocation hexLoc34 = new HexLocation(3, 2);
		HexLocation hexLoc35 = new HexLocation(3, 1);
		HexLocation hexLoc36 = new HexLocation(3, 0);
		HexLocation hexLoc37 = new HexLocation(3, -1);

		hexes.put(hexLoc1, new Hex(hexLoc1, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc2, new Hex(hexLoc2, HexType.WATER, new NumberToken(0), PortType.WOOD));
		hexes.put(hexLoc3, new Hex(hexLoc3, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc4, new Hex(hexLoc4, HexType.WATER, new NumberToken(0), PortType.THREE));

		hexes.put(hexLoc5, new Hex(hexLoc5, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc6, new Hex(hexLoc6, HexType.ORE, new NumberToken(5), PortType.BLANK));
		hexes.put(hexLoc7, new Hex(hexLoc7, HexType.WHEAT, new NumberToken(2), PortType.BLANK));
		hexes.put(hexLoc8, new Hex(hexLoc8, HexType.WOOD, new NumberToken(6), PortType.BLANK));
		hexes.put(hexLoc9, new Hex(hexLoc9, HexType.WATER, new NumberToken(0), PortType.BRICK));

		hexes.put(hexLoc10, new Hex(hexLoc10, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc11, new Hex(hexLoc11, HexType.ORE, new NumberToken(3), PortType.BLANK));
		hexes.put(hexLoc12, new Hex(hexLoc12, HexType.SHEEP, new NumberToken(9), PortType.BLANK));
		hexes.put(hexLoc13, new Hex(hexLoc13, HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		hexes.put(hexLoc14, new Hex(hexLoc14, HexType.BRICK, new NumberToken(8), PortType.BLANK));
		hexes.put(hexLoc15, new Hex(hexLoc15, HexType.WATER, new NumberToken(0), PortType.WHEAT));

		hexes.put(hexLoc16, new Hex(hexLoc16, HexType.WATER,new NumberToken(0), PortType.THREE));
		hexes.put(hexLoc17, new Hex(hexLoc17, HexType.WHEAT, new NumberToken(8), PortType.BLANK));
		hexes.put(hexLoc18, new Hex(hexLoc18, HexType.WOOD, new NumberToken(4), PortType.BLANK));
		hexes.put(hexLoc19, new Hex(hexLoc19, HexType.WHEAT, new NumberToken(11), PortType.BLANK));
		hexes.put(hexLoc20, new Hex(hexLoc20, HexType.WOOD, new NumberToken(3), PortType.BLANK));
		hexes.put(hexLoc21, new Hex(hexLoc21,HexType.DESERT,new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc22, new Hex(hexLoc22, HexType.WATER, new NumberToken(0), PortType.BLANK));

		hexes.put(hexLoc23, new Hex(hexLoc23, HexType.WATER, new NumberToken(0), PortType.ORE));
		hexes.put(hexLoc24, new Hex(hexLoc24, HexType.BRICK, new NumberToken(4), PortType.BLANK));
		hexes.put(hexLoc25, new Hex(hexLoc25, HexType.ORE, new NumberToken(9), PortType.BLANK));
		hexes.put(hexLoc26, new Hex(hexLoc26, HexType.BRICK, new NumberToken(5), PortType.BLANK));
		hexes.put(hexLoc27, new Hex(hexLoc27, HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		hexes.put(hexLoc28, new Hex(hexLoc28, HexType.WATER, new NumberToken(0), PortType.BLANK));

		hexes.put(hexLoc29, new Hex(hexLoc29, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc30, new Hex(hexLoc30, HexType.WOOD, new NumberToken(11), PortType.BLANK));
		hexes.put(hexLoc31, new Hex(hexLoc31,HexType.SHEEP, new NumberToken(12), PortType.BLANK));
		hexes.put(hexLoc32, new Hex(hexLoc32,HexType.WHEAT, new NumberToken(6), PortType.BLANK));
		hexes.put(hexLoc33, new Hex(hexLoc33, HexType.WATER,new NumberToken(0), PortType.THREE));

		hexes.put(hexLoc34, new Hex(hexLoc34, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc35, new Hex(hexLoc35, HexType.WATER, new NumberToken(0), PortType.SHEEP));
		hexes.put(hexLoc36, new Hex(hexLoc36, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc37, new Hex(hexLoc37, HexType.WATER, new NumberToken(0), PortType.THREE));

	}
	
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

	/**
	 * Getters and setters:
     */

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

	public Map<HexLocation, Hex> getHexes() {
		return hexes;
	}

	public void setHexes(Map<HexLocation, Hex> hexes) {
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
}
