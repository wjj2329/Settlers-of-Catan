package shared.game;

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
	 */
	public boolean canTrade()
	{
		return false;
	}
}
