package client.model;

import java.util.ArrayList;

import shared.game.player.Player;

/**
 * A class to keep track of turns
 */
public class TurnTracker
{

	int currentturn=0;
	String status;
	int longestRoad=0;
	int largestArmy=0;
	 
	public TurnTracker()
	{
		
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
					longestRoad = allplayers.get(i).getArmySize();
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
	public Player updateUserWithLargestRoad(ArrayList<Player> allplayers)
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
					longestRoad = allplayers.get(i).getRoadSize();
				}
			}
		}
		
		return allplayers.get(currentlargest);
	}
	

}
