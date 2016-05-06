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
	private int largestArmySize = 0;
	/**
	 * Same as above for longestRoad.
	 */
	private int longestRoadSize = 0;
	/**
	 * Constructor for RewardCardList
	 */
	public RewardCardList()
	{
		
	}
	/**
	 * @pre longestRoadSize is not negative and is not larger then there
	 * are number of road pieces
	 * @postlongestRoadSize is updated. 
	 * @param longestRoadSize
	 */
	private void updatelongestroad(int longestRoadSize)
	{
		this.longestRoadSize=longestRoadSize;
	}
	/**
	 * @pre largestArmySize is not negative and is not larger then there
	 * are number of Knight Cards
	 * @postlargestArmySize is updated. 
	 * @param longestRoadSize
	 */
	private void updatelargestarmy(int largestArmySize)
	{
		this.largestArmySize=largestArmySize;
	}
}
