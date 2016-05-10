package shared.game.player;

import shared.definitions.CatanColor;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
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
	private int roadSize=0;
	private int armySize=0;
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
	 * I.e. is it your turn?
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
	 * Function to determine whether or not a player can trade with another player.
	 * @param other: the other player
     */
	public boolean canRequestTrade(Player other, int amountRequesting, int amountSending, ResourceType typeRequesting,
								   ResourceType typeSending)
	{
		if (!currentPlayer || other.isCurrentPlayer())
		{
			return false;
		}

		if (amountSending > resources.getRequestedType(typeSending))
		{
			return false;
		}

		/*
		 This next part may need to be split up/adjusted because of the GUI.
		 The player can REQUEST as much as he wants. However, if the other player
		 doesn't have sufficient of the requested resources, then they will have the
		 option to accept the trade grayed out.

		 It is KEY that the operation be performed on Player OTHER! NOT, and I repeat NOT
		  the local player!
		  */
		if (!other.canBeTradedWith(amountRequesting, typeRequesting))
		{
			return false;
		}
		return true;
	}

	/**
	 * Returns true if the player can trade with the quantity requested.
	 * It is interconnected with the similar method canRequestTrade().
	 * @param quantityRequested: how many resources the player wants.
     */
	public boolean canBeTradedWith(int quantityRequested, ResourceType typeRequested)
	{
		if (quantityRequested > resources.getRequestedType(typeRequested))
		{
			return false;
		}
		return true;
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

	public boolean canBuyDevCard()
	{
		if(resources.getOre()>0)
		{
			if(resources.getSheep()>0)
			{
				if(resources.getWheat()>0)
				{
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * Function to determine whether or not the player can trade with the bank
	 * based on the parameters of their particular trade.
	 * Need to replace sampleBank with Bank singleton most likely.
	 * @param tradeType
	 * @param typeFor_3_Or4Way
	 * @param typeRequesting: which type the player wants!
	 *
	 * Second PortType is only necessary if there is a 3:1 or 4:1 trade. This is because it is saved as THREE
	 * or FOUR, and thus we don't know if that means WHEAT, SHEEP, WOOD, etc.
	 *
	 * If the second type is listed as BLANK, then it is a 2:1 trade, and thus said second variable will never be used.
     */

	public boolean canDoTradeWithBank(PortType tradeType, PortType typeFor_3_Or4Way, ResourceType typeRequesting) throws Exception
	{
		// What kind of trade is it?
		switch (tradeType)
		{
			case WOOD: // Then it is a 2:1.
				if (resources.getWood() < TWO_WAY)
				{
					return false; // I think this should be fine.
				}
				break;
			case BRICK:
				if (resources.getBrick() < TWO_WAY)
				{
					return false;
				}
				break;
			case SHEEP:
				if (resources.getSheep() < TWO_WAY)
				{
					return false;
				}
				break;
			case WHEAT:
				if (resources.getWheat() < TWO_WAY)
				{
					return false;
				}
				break;
			case ORE:
				if (resources.getOre() < TWO_WAY)
				{
					return false;
				}
				break;
			case THREE: // 3:1.
				if (!multiWayTrade(typeFor_3_Or4Way, THREE_WAY))
				{
					return false;
				}
				break;
			case FOUR: // 4:1.
				if (!multiWayTrade(typeFor_3_Or4Way, FOUR_WAY))
				{
					return false;
				}
				break;
			default:
				assert(false);
		}
		if (!Bank.getSingleton().CanBankGiveResourceCard(typeRequesting))
		{
			return false;
		}
		return true;
	}

	/**
	 * Handles three or four way trades.
	 * @param theType: type of the trade
	 * @param threeOrFour: whether it is a 3-way or 4-way
     */
	private boolean multiWayTrade(PortType theType, int threeOrFour)
	{
		assert(!theType.equals(PortType.BLANK));
		assert(!theType.equals(PortType.THREE));
		assert(!theType.equals(PortType.FOUR));
		assert(threeOrFour == THREE_WAY || threeOrFour == FOUR_WAY);
		switch (theType)
		{
			case WOOD:
				if (resources.getWood() < threeOrFour)
				{
					return false;
				}
				break;
			case BRICK:
				if (resources.getBrick() < threeOrFour)
				{
					return false;
				}
				break;
			case SHEEP:
				if (resources.getSheep() < threeOrFour)
				{
					return false;
				}
				break;
			case ORE:
				if (resources.getOre() < threeOrFour)
				{
					return false;
				}
				break;
			case WHEAT:
				if (resources.getWheat() < threeOrFour)
				{
					return false;
				}
				break;
			default:
				assert(false);
		}
		return true;
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
	private static final int TWO_WAY = 2;
	private static final int THREE_WAY = 3;
	private static final int FOUR_WAY = 4;

	public int getArmySize() {
		return armySize;
	}

	public int getRoadSize() {
		return roadSize;
	}
}
