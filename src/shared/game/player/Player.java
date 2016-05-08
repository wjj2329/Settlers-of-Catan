package shared.game.player;

import shared.definitions.CatanColor;
import shared.definitions.PortType;
import shared.game.Bank;
import shared.game.DevCardList;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.map.Port;
import shared.game.map.Robber;
import shared.locations.HexLocation;

import java.util.ArrayList;

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
	private DevCardList newDevCards = new DevCardList(DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL);
	
	/**
	 * oldDevCards: list of old development cards
	 */
	private DevCardList oldDevCards= new DevCardList(DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL, DEFAULT_VAL);
	
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
	 * List of all the ports that the player currently has.
	 */
	private ArrayList<Port> playerPorts = null;
	
	/**
	 * How many roads the Player CAN BUILD.
	 * Updated dynamically. 
	 */
	private int numRoads = 0;
	
	/**
	 * How many settlements the player CAN BUILD.
	 */
	private int numSettlements = 0;
	
	/**
	 * How many soldiers (soldier cards) the player CAN BUILD.
	 */
	private int numSoldierCards = 0;
	
	/**
	 * How many victory points the player has
	 * Everyone starts off with 2. 10+ on your turn to win!
	 */
	private int numVictoryPoints = 0;

	/**
	 * CurrentPlayer: Tracks whether or not this player is the current one!
	 */
	private boolean currentPlayer = false;
	
	/**
	 * Player constructor
	 * @pre name is between 3 and 7 characters
	 * 						CatanColor is not null
	 * 						playerID is not null
	 * @post same as above.
	 */
	public Player(String name, CatanColor color, Index playerID)
	{
		this.name = name;
		this.color = color;
		this.playerID = playerID;
		resources = new ResourceList();
	}
	
	/**
	 * Determines whether or not the player has the resources to be robbed
	 * Need to check this before Robber robs player
	 * @pre: Robber's location is not null
	 */
	public boolean canBeRobbed()
	{
		HexLocation currentRobberLocation = Robber.getSingleton().getLocation();
		if (resources.getBrick() == 0 && resources.getOre() == 0 && resources.getSheep() == 0
				&& resources.getWheat() == 0 && resources.getWood() == 0)
		{
			return false;
		}
		if (currentPlayer)
		{
			return false;
		}

		/*
		Need another IF statement here:
			If the player doesn't have a settlement or city on the robber's hex, then they cannot be robbed.
			So we need to have Map as a singleton. However, this will take a while to implement, so I haven't
			done it just yet. ~ Alex
		 */
		return true;
	}

	/**
	 * Function to determine whether or not the player can trade with the bank
	 * based on the parameters of their particular trade.
	 * Need to replace sampleBank with Bank singleton most likely.
	 * @param tradeType
     */
	public boolean canDoTradeWithBank(PortType tradeType)
	{
		Bank sampleBank = new Bank();
		// What kind of trade is it?
		switch (tradeType)
		{
			case WOOD:
				break;
			case BRICK:
				break;
			case SHEEP:
				break;
			case WHEAT:
				break;
			case ORE:
				break;
			case THREE:
				break;
			default:
				break;
		}
		return true;
	}
	
	/**
	 * Determines whether or not the player can play a dev card.
	 */
	public boolean canPlayDevCard()
	{
		return false;
	}
	
	/**
	 * Determines whether or not the player can buy/build a road.
	 */
	public boolean canBuildRoad()
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

	public boolean canBuildSettlement()
	{
		return false;
	}

	public boolean canBuildCity()
	{
		return false;
	}

	/**
	 * Getters and setters:
	 */
	public boolean isCurrentPlayer()
	{
		return currentPlayer;
	}

	public void setCurrentPlayer(boolean currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}

	public ResourceList getResources()
	{
		return resources;
	}

	public void setResources(ResourceList resources)
	{
		this.resources = resources;
	}

	private static final int DEFAULT_VAL = 0;
}
