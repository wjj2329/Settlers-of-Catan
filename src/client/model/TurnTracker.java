package client.model;

import java.io.Serializable;
import java.util.ArrayList;

import shared.game.map.Index;
import shared.game.player.Player;

/**
 * A class to keep track of turns
 */
@SuppressWarnings("serial")
public class TurnTracker implements Serializable
{

	/**
	 * The index of the player whoom the current turn is on.
	 */
	private Index currentTurn;
	/**
	 * The current status of the game.
	 */
	private TurnStatus status;
	/**
	 * The index of the player who currently holds the largest road.
	 */
	private Index longestRoad;
	/**
	 * The index of the player who currently holds the largest army.
	 */
	private Index largestArmy;
	 
	public TurnTracker(TurnStatus status, Index currentTurn, Index longestRoad, Index largestArmy)
	{
		this.status = status;
		this.currentTurn = currentTurn;
		this.longestRoad = longestRoad;
		this.largestArmy = largestArmy;
	}
	/**
	  * A function that updates at the start of the turn the player with 
	  * the largest Army
	  * @pre There must be a player that has at least 3 army knights before this card can be given.
	  * @post Player is updates as having the Largest Army Card. 
	  * 
	  */
	public Player updateUserWithLargestArmy(ArrayList<Player> allplayers)
	{
		int currentlargest = -1;
		for (int i = 0; i < allplayers.size(); i++)
		{
			if(allplayers.get(i).getArmySize() >= 3)
			{
				if(currentlargest == -1 || 
						allplayers.get(i).getArmySize() > allplayers.get(currentlargest).getArmySize())
				{
					currentlargest = i;
					largestArmy.setNumber(allplayers.get(i).getArmySize());
				}
			}
		}
		
		return allplayers.get(currentlargest);
	}
	/**
	  * A function that updates at the start of the turn the player with 
	  * the largest Road
	  *  * @pre There must be a player that has at least 3 continuous road pieces before this card can be given.
	  * @post Player is updates as having the Largest Road Card. 
	  */
	public Player updateUserWithLongestRoad(ArrayList<Player> allplayers)
	{
		int currentlargest = -1;
		for (int i = 0; i < allplayers.size(); i++)
		{
			if(allplayers.get(i).getRoadSize() >= 3)
			{
				if(currentlargest == -1 || 
						allplayers.get(i).getRoadSize() > allplayers.get(currentlargest).getRoadSize())
				{
					currentlargest = i;
					longestRoad.setNumber(allplayers.get(i).getRoadSize());
				}
			}
		}
		
		return allplayers.get(currentlargest);
	}
	/**
	 * @return the currentTurn
	 */
	public Index getCurrentTurn()
	{
		return currentTurn;
	}
	/**
	 * @param currentTurn the currentTurn to set
	 */
	public void setCurrentTurn(Index currentTurn,ArrayList<Player> players)
	{
		this.currentTurn = currentTurn;
		updateUserWithLargestArmy(players);
		updateUserWithLongestRoad(players);
	}
	/**
	 * @return the status
	 */
	public TurnStatus getStatus()
	{
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TurnStatus status)
	{
		this.status = status;
	}
	/**
	 * @return the longestRoad
	 */
	public Index getLongestRoad()
	{
		return longestRoad;
	}
	/**
	 * @param longestRoad the longestRoad to set
	 */
	public void setLongestRoad(Index longestRoad)
	{
		this.longestRoad = longestRoad;
	}
	/**
	 * @return the largestArmy
	 */
	public Index getLargestArmy()
	{
		return largestArmy;
	}
	/**
	 * @param largestArmy the largestArmy to set
	 */
	public void setLargestArmy(Index largestArmy)
	{
		this.largestArmy = largestArmy;
	}
	

}
