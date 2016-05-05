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
	 * Color: Color is received as a String from the JSON file.
	 * However, we need to use the enum type CatanColor.
	 */
	private CatanColor color = null;
	
	/**
	 * discarded: whether or not they discarded a card
	 */
	private boolean discarded = false;
	
	/**
	 * numMonuments: how many monuments a player has.
	 */
	private int numMonuments = 0;
	
	/**
	 * Name: Player's name.
	 * Examples: Jess, Rob, Tiny, Kahuna (max 7 char)
	 * 
	 * According to the JSON this will be read as just a String.
	 * However, we may need a class to encapsulate "Name" in the future
	 * in order to avoid primitive obsession. 
	 */
	private String name = "";
}
