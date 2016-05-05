package shared.game;

/**
 * @author Alex
 * List of all the Reward Cards that a player currently has.
 * I.e. largest army, longest road
 */
public class RewardCardList 
{
	/**
	 * We receive this as an integer from the JSON file.
	 * However, we may create a LargestArmyCard class. 
	 */
	private int largestArmy = 0;
	
	/**
	 * Same as above for longestRoad.
	 */
	private int longestRoad = 0;
	
	/**
	 * Constructor for RewardCardList
	 */
	public RewardCardList()
	{
		
	}
}
