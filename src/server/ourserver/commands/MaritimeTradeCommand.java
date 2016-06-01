package server.ourserver.commands;

import server.ourserver.ServerFacade;
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
	@Override
	public Object execute()
	{
		// TODO Auto-generated method stub
		return null;
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
	public void doMaritimeTradeCommand(String getResource, String giveResource, int playerIndex_NOT_ID, int ratio, int gameID)
		throws Exception
	{
		CatanGame currentCatan = ServerFacade.getInstance().getGameByID(gameID);
		// Incrementing version number
		incrementVersion(currentCatan);
		Bank cashFlow = currentCatan.mybank;
		Player playerWhoIsTrading = getPlayerFromPlayerIndex(playerIndex_NOT_ID, currentCatan);
		if (playerWhoIsTrading == null)
		{
			// Exception thrown if no player with given playerIndex exists in the map.
			throw new Exception(NOT_FOUND_MSG);
		}
		decrementMyResources(playerWhoIsTrading, ratio, giveResource);
		incrementMyResources(playerWhoIsTrading, getResource);
		// May need to work with bank here. DO NOT FORGET. We must NOT run out of cards!
	}

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
	 * Decrements the amount of the given resource by the given ratio, r, in the player's resourceList.
	 * @param p: the player making the maritime trade
	 * @param ratio: the ratio at which the maritime trade is being performed
	 * @param giveResource: The resource that the player is giving to the bank in exchange
     */
	private void decrementMyResources(Player p, int ratio, String giveResource)
	{
		ResourceList resList = p.getResources();
		switch (giveResource.toLowerCase())
		{
			case "brick":
				resList.setBrick(resList.getBrick() - ratio);
				break;
			case "ore":
				resList.setOre(resList.getOre() - ratio);
				break;
			case "sheep":
				resList.setSheep(resList.getSheep() - ratio);
				break;
			case "wheat":
				resList.setWheat(resList.getWheat() - ratio);
				break;
			case "wood":
				resList.setWood(resList.getWood() - ratio);
				break;
			default:
				break;
		}
	}

	/**
	 * The ratio is always 1 here!
	 * @param p: the player who is making the trade
	 * @param getResource: which resource the player is getting
     */
	private void incrementMyResources(Player p, String getResource)
	{
		ResourceList resList = p.getResources();
		switch (getResource.toLowerCase())
		{
			case "brick":
				resList.setBrick(resList.getBrick() + 1);
				break;
			case "ore":
				resList.setOre(resList.getOre() + 1);
				break;
			case "sheep":
				resList.setSheep(resList.getSheep() + 1);
				break;
			case "wheat":
				resList.setWheat(resList.getWheat() + 1);
				break;
			case "wood":
				resList.setWood(resList.getWood() + 1);
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
	 * Private static data members, stored here for ease of reference.
	 */
	private static final String NOT_FOUND_MSG = "Player not found in map!";
}
