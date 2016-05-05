package shared.game.map.vertexobject;

/**
 * @author Alex
 * Structure: Parent class that encapsulates cities and settlements. 
 */
public class Structure 
{
	/**
	 * BuildingCost: cost of building the particular structure.
	 */
	private int buildingCost = 0;
	
	/**
	 * Constructor for Structure
	 */
	public Structure(int buildingCost)
	{
		this.buildingCost = buildingCost;
	}
	
	/**
	 * Determines whether or not a structure can be placed.
	 */
	public boolean canPlaceStructure()
	{
		return false;
	}
	
}
