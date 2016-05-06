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
	 * @custom.mytag1 pre: longestRoadSize is not negative and is not larger then there
	 * are number of road pieces
	 * @custom.mytag2 post:longestRoadSize is updated. 
	 * @param longestRoadSize
	 */
	private void updatelongestroad(int longestRoadSize)
	{
		this.longestRoadSize=longestRoadSize;
	}
	/**
	 * @custom.mytag1 pre: largestArmySize is not negative and is not larger then there
	 * are number of Knight Cards
	 * @custom.mytag2 post:largestArmySize is updated. 
	 * @param longestRoadSize
	 */
	private void updatelargestarmy(int largestArmySize)
	{
		this.largestArmySize=largestArmySize;
	}
}
