package shared.game;

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
	 * CanTrade: determines whether or not a trade
	 * can be performed
	 */
	public boolean canTrade()
	{
		return false;
	}
}
