package shared.game;

/**
 * @author Alex
 * List of development cards belonging to a player.
 * They are listed as integers from the JSON.
 * However, this may indicate quantity, so we might need to include
 * pointers to the actual cards as well. 
 */
public class DevCardList 
{
	private int monopoly = 0;
	
	private int monument = 0;
	
	private int roadBuilding = 0;
	
	private int soldier = 0;
	
	private int yearOfPlenty = 0;
	
	/**
	 * Constructor for DevCardList
	 */
	public DevCardList(int monopoly, int monument, int roadBuilding,
			int soldier, int yearOfPlenty)
	{
		this.monopoly = monopoly;
		this.monument = monument;
		this.roadBuilding = roadBuilding;
		this.soldier = soldier;
		this.yearOfPlenty = yearOfPlenty;
	}
}
