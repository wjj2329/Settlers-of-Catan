package shared.game.player;

import shared.definitions.CatanColor;
import shared.game.DevCardList;
import shared.game.ResourceList;
import shared.game.map.Index;

/**
 * @author Alex
 * Player: Class that represents each individual player in the game.
 * There are 4 players in each game; thus, there are 4 objects of type 
 * Player in each game. 
 */
public class Player 
{
	public Player()
	{
		
	}
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
	 * Examples: Jessie, Rob, Tiny, Kahuna (max 7 char)
	 * 
	 * According to the JSON this will be read as just a String.
	 * However, we may need a class to encapsulate "Name" in the future
	 * in order to avoid primitive obsession. 
	 */
	private String name = "";
	
	/**
	 * newDevCards: list of new development cards
	 */
	private DevCardList newDevCards = new DevCardList(numCities, numCities, numCities, numCities, numCities);
	
	/**
	 * oldDevCards: list of old development cards
	 */
	private DevCardList oldDevCards= new DevCardList(numCities, numCities, numCities, numCities, numCities);
	
	/**
	 * Each player has their own Index.
	 * Recording this information is key for calculating turns
	 * and getting the correct Player pointers.
	 * 
	 * We might even want to add a list of Indexes to the main game map,
	 * representing each player (if the need arises). 
	 */
	private Index playerIndex = null;
	
	/**
	 * ID of the player
	 * received from JSON file
	 * 
	 * NOTE: Keep this until we determine if there's a difference
	 * between playerID and playerIndex. 
	 */
	private Index playerID = null;
	
	/**
	 * ResourceList: List of all the resource cards
	 * (brick, ore, sheep, wheat, wood)
	 */
	private ResourceList resources = null;
	
	/**
	 * How many roads the Player currently has. 
	 * Updated dynamically. 
	 */
	private int numRoads = 0;
	
	/**
	 * How many settlements the player currently has
	 */
	private int numSettlements = 0;
	
	/**
	 * How many soldiers (soldier cards) the player currently has
	 */
	private int numSoldierCards = 0;
	
	/**
	 * How many victory points the player has
	 * Everyone starts off with 2. 10+ on your turn to win!
	 */
	private int numVictoryPoints = 0;
	
	/**
	 * Player constructor
	 * @custom.mytag1 pre: name is between 3 and 7 characters
	 * 						CatanColor is not null
	 * 						playerID is not null
	 * @custom.mytag2 post: same as above.
	 */
	public Player(String name, CatanColor color, Index playerID)
	{
		this.name = name;
		this.color = color;
		this.playerID = playerID;
	}
	
	/**
	 * Determines whether or not the player has the resources to be robbed
	 * Need to check this before Robber robs player
	 */
	public boolean canBeRobbed()
	{
		return false;
	}
	
	/**
	 * Determines whether or not the player can play a dev card.
	 */
	public boolean canPlayDevCard()
	{
		return false;
	}
	
	/**
	 * Determines whether or not the player can buy a road.
	 */
	public boolean canBuyRoad()
	{
		return false;
	}
	
	/**
	 * Determines whether or not the player can accept a current trade.
	 * May need a TradeParameters object in order to assist with this
	 * method.
	 */
	public boolean canAcceptTrade()
	{
		return false;
	}
}
