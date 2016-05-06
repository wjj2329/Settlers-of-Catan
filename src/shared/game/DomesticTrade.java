package shared.game;

import shared.game.player.Player;

/**
 * @author Alex
 * DomesticTrade: class used to represent a domestic trade
 * (i.e., a trade with another player; not on the coast)
 */
public class DomesticTrade extends Trade 
{
	/**
	 * DomesticTrade constructor
	 */
	public DomesticTrade()
	{
		
	}
	
	/**
	 * CanTrade: determines whether or not a trade can
	 * be performed
	 * @pre Player MUST be a valid non null Player that is 
	 * currently playing the current game running on the server. 
	 * @custon.mytag2 post: returns false if not able to trade returns true if is able to trade
	 * @exception throws exception if player is not valid player
	 */
	public boolean canTrade(Player player)
	{
		return false;
	}
}
