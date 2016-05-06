package shared.game;

/**
 * @author Alex
 * Trade class: Objects of type Trade are created whenever 
 * two or more players decide to trade items/cards/resources.
 * 
 * A TradeParameters object might be useful to add to this
 * as a data field. If we need this class, we can create it.
 * None exists right now.
 */
public class Trade 
{
	/**
	 * Trade constructor.
	 */
	public Trade()
	{
		
	}
	
	/**
	 * @custom.mytag1 pre:playid is not negative
	 * @custom.mytag2 post:returns true if successful
	 * Returns whether or not one player can trade with another.
	 * @exception throws exception if player id with corresponding player is not found
	 */
	public boolean canTradeWith(int playerid)
	{
		return false;
	}
}
