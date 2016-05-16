package shared.game;

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
}
