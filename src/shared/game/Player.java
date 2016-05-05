package shared.game;

import shared.definitions.CatanColor;

/**
 * @author Alex
 * Player: Class that represents each individual player in the game.
 * There are 4 players in each game; thus, there are 4 objects of type 
 * Player in each game. 
 */
public class Player 
{
	/**
	 * numCities: How many cities an individual player has.
	 */
	private int numCities = 0;
	/**
	 * Color: Color is received as a String from the JSON.
	 * However, we need to use the enum type CatanColor.
	 */
	private CatanColor color = null;
}
