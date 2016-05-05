package shared.game.map.vertexobject;

import shared.game.map.Index;
import shared.locations.HexLocation;

/**
 * @author Alex
 * City: players can build a city in order to expand their territory
 */
public class City extends Structure 
{
	/**
	 * owner: Index of the player who owns city
	 */
	private Index owner = null;
	
	/**
	 * Where the city is located on the map
	 */
	private HexLocation location = null;
	
	/**
	 * Direction: string
	 */
	private String direction = "";
	
	/**
	 * Constructor for City
	 * @param buildingCost: cost of building city
	 */
	public City(int buildingCost) 
	{
		super(buildingCost);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Determining whether or not we can place a city.
	 */
	public boolean canPlaceCity()
	{
		return false;
	}
}
