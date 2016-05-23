package shared.game;

import shared.definitions.HexType;
import shared.definitions.ResourceType;

/**
 * @author Alex
 * ResourceList: list of resource cards owned by a player
 */
public class ResourceList 
{
	/**
	 * The following private data members need to be investigated
	 * to ensure that they should be integers
	 */
	private int brick = 0;

	@Override
	public String toString() {
		return "ResourceList{" +
				"brick=" + brick +
				", ore=" + ore +
				", sheep=" + sheep +
				", wheat=" + wheat +
				", wood=" + wood +
				'}';
	}

	private int ore = 0;
	
	private int sheep = 0;
	
	private int wheat = 0;
	
	private int wood = 0;
	
	/**
	 * ResourceList constructor
	 */
	public ResourceList()
	{
		
	}

	public ResourceList(int brick, int ore, int sheep, int wheat, int wood)
	{
		this.brick=brick;
		this.ore=ore;
		this.sheep=sheep;
		this.wheat=wheat;
		this.wood=wood;
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public String convertresroucetypetostring(ResourceType resourceType)
	{
		switch (resourceType)
		{
			case BRICK:
				return "brick";
			case ORE:
				return "ore";
			case SHEEP:
				return "sheep";
			case WHEAT:
				return "wheat";
			case WOOD:
				return "wood";

		}
		return null;
	}
	public int getRequestedType(ResourceType resourceType)
	{
		switch (resourceType)
		{
			case BRICK:
				return brick;
			case ORE:
				return ore;
			case SHEEP:
				return sheep;
			case WHEAT:
				return wheat;
			case WOOD:
				return wood;
			default:
				return -1;
		}
	}
	public void increaseby1(ResourceType mytype)
	{
		switch (mytype) {
			case WOOD:
				wood++;
				return;
			case ORE:
				ore++;
				return;
			case BRICK:
				brick++;
				return;
			case WHEAT:
				wheat++;
				return;
			case SHEEP:
				sheep++;
				return;
		}
	}
	public void decreaseby1(ResourceType mytype)
	{
		switch (mytype) {
			case WOOD:
				wood--;
				return;
			case ORE:
				ore--;
				return;
			case BRICK:
				brick--;
				return;
			case WHEAT:
				wheat--;
				return;
			case SHEEP:
				sheep--;
				return;
		}
	}
	public int size()
	{
		return (ore+brick+sheep+wood+wheat);
	}

	/**
	 * Helps with players obtaining the proper resources based on the hexes that they own.
	 * @param hexType: the HexType we are passing in; which resource it has
	 * @pre: The HexType passed in is NOT of type Desert or Water. It must have the proper resources.
     */
	public void incrementBasedOnHexType(HexType hexType)
	{
		switch (hexType)
		{
			case WOOD:
				wood++;
			case ORE:
				ore++;
			case BRICK:
				brick++;
			case WHEAT:
				wheat++;
			case SHEEP:
				sheep++;
			default:
				break;
				//assert false; // should NOT be any desert or water types passed here
		}
	}
}
