package server.ourserver.commands;

import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.player.Player;

/**
 * The MaritimeTradeCommand!
 * @author Alex
 */
public class MaritimeTradeCommand implements ICommand {

	/**
	 * Executes the task:
	 * 	of maritime trade, right ratios and the trade was done correctly.
	 */
	private String getResource;
	private String giveResource;
	private int playerIndex_NOT_ID;
	private int ratio;
	private int gameID;
	public MaritimeTradeCommand(String getResource, String giveResource, int playerIndex_NOT_ID, int ratio, int gameID)
	{
		this.getResource=getResource;
		this.giveResource=giveResource;
		this.playerIndex_NOT_ID=playerIndex_NOT_ID;
		this.ratio=ratio;
		this.gameID=gameID;
	}
	@Override
	public Object execute()
	{
		CatanGame currentCatan = ServerFacade.getInstance().getGameByID(gameID);
		incrementVersion(currentCatan);
		Player playerWhoIsTrading = getPlayerFromPlayerIndex(playerIndex_NOT_ID, currentCatan);
		if (playerWhoIsTrading == null)
		{
			// Exception thrown if no player with given playerIndex exists in the map.
			try {
				throw new Exception(NOT_FOUND_MSG);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		ResourceList playerResources = playerWhoIsTrading.getResources();
		Bank myBank = currentCatan.mybank;
		ResourceList bankResources = myBank.getCardslist();
		ResourceType resType = stringToResourceType(getResource);
		if (resType == null)
		{
			try {
				throw new Exception(INVALID_RES);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Only execute the actions if the bank can actually give the card.
		// Ideally, we would gray out the options, but we don't have a client-side
		if (myBank.CanBankGiveResourceCard(resType))
		{
			//System.out.println("Here are the player resources before: " + playerResources.toString());
			updateResourceList(playerResources, -ratio, giveResource);
			updateResourceList(playerResources, PLAYER_INCREMENT, getResource);
			updateResourceList(bankResources, BANK_DECREMENT, getResource);
			updateResourceList(bankResources, ratio, giveResource);
			//System.out.println("Here are the player resources after: " + playerResources.toString());
		}		return null;
	}

	/**
	 * Executes the actual command.
	 * Need to figure out how this works with the .execute() method for phase 4.
	 * @param getResource: resource receiving
	 * @param giveResource: resource giving. Make sure these two aren't switched.
	 * @param playerIndex_NOT_ID: the index, NOT the ID, of the player
	 * @param ratio: 2:1, 3:1, or 4:1 (most common). Subtract r amount of giveResource from the player.
     * @param gameID: the id of the current game.
     */


	/**
	 * Returns a player from a player index, since we don't get to work with the Player ID with the
	 * 	server.
	 * @param playerIndexAsInt: player index, simplified to integer form
	 * @param currentCatan: the current game
     */
	private Player getPlayerFromPlayerIndex(int playerIndexAsInt, CatanGame currentCatan)
	{
		for (Player p : currentCatan.getMyplayers().values())
		{
			if (p.getPlayerIndex().getNumber() == playerIndexAsInt)
			{
				return p;
			}
		}
		return null;
	}

	/**
	 * Function to update the ResourceList, usually of the player or the bank.
	 * @param res: resource list we are updating
	 * @param ratio: the ratio by which we are updating the list
	 * @param resource: which resource we are updating
     */
	private void updateResourceList(ResourceList res, int ratio, String resource)
	{
		switch (resource.toLowerCase())
		{
			case BRICK:
				res.setBrick(res.getBrick() + ratio);
				break;
			case ORE:
				res.setOre(res.getOre() + ratio);
				break;
			case SHEEP:
				res.setSheep(res.getSheep() + ratio);
				break;
			case WHEAT:
				res.setWheat(res.getWheat() + ratio);
				break;
			case WOOD:
				res.setWood(res.getWood() + ratio);
				break;
			default:
				break;
		}
	}

	/**
	 * Increments the version of the given CatanGame.
	 * @param currentCatan: the current CatanGame.
     */
	private void incrementVersion(CatanGame currentCatan)
	{
		currentCatan.getModel().setVersion(currentCatan.getModel().getVersion() + 1);
	}

	/**
	 * Converts a string to a resource type.
     */
	private ResourceType stringToResourceType(String res)
	{
		switch (res.toLowerCase())
		{
			case BRICK:
				return ResourceType.BRICK;
			case ORE:
				return ResourceType.ORE;
			case SHEEP:
				return ResourceType.SHEEP;
			case WHEAT:
				return ResourceType.WHEAT;
			case WOOD:
				return ResourceType.WOOD;
			default:
				return null;
		}
	}

	/**
	 * Private static data members, stored here for ease of reference.
	 */
	private static final String NOT_FOUND_MSG = "Player not found in map!";
	private static final String INVALID_RES = "Invalid resource type!";
	private static final int PLAYER_INCREMENT = 1;
	private static final int BANK_DECREMENT = -1;
	private static final String BRICK = "brick";
	private static final String ORE = "ore";
	private static final  String SHEEP = "sheep";
	private static final String WHEAT = "wheat";
	private static final String WOOD = "wood";

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return ","+ TextDBGameManagerDAO.commandNumber+":"+"{" +
				"type: MaritimeTradeCommand" +
				", getResource:'" + getResource + '\'' +
				", giveResource:'" + giveResource + '\'' +
				", playerIndex_NOT_ID:" + playerIndex_NOT_ID +
				", ratio:" + ratio +
				", gameID:" + gameID +
				"}}";
	}
}
