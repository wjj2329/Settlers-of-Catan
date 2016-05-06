package client.model;

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
	public Player updateUserWithLargestArmy()
	{
		return null;
	}
	/**
	  * A function that updates at the start of the turn the player with 
	  * the largest Road
	  *  * @pre There must be a player that has at least 3 army continuous road pieces before this card can be given.
	  * @post Player is updates as having the Largest Road Card. 
	  */
	public Player updateUserWithLargestRoad()
	{
		return null;
	}
	

}
