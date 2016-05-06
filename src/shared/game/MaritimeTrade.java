package shared.game;

import shared.game.player.Player;

/**
 * @author Alex
 * MaritimeTrade: a trade that takes place at a harbor.
 * You do not need another player to perform a MaritimeTrade,
 * but you do need cards to trade with.
 */
public class MaritimeTrade extends Trade
{
	/**
	 * Default MaritimeTrade constructor
	 */
	public MaritimeTrade()
	{
		
	}
	
	/**
	 * CanTrade: determines whether or not a trade can
	 * be performed
	 * @pre Player MUST be a valid non null Player that is 
	 * currently playing the current game running on the server. 
	 * @post returns false if not able to trade returns true if is able to trade
	 * @exception throws exception if player is not valid player
	 */
	public boolean canTrade(Player player)
	{
		return false;
	}
}
