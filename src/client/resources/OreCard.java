package client.resources;

/**
 * OreCard: Defines an OreCard, a type of ResourceCard
 */
public class OreCard extends ResourceCard
{
	/**
	 * OreCard constructor
	 */
	public OreCard(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * canEarnResourceCard: Return whether a resource card can
	 * be earned.
	 */
	@Override
	public boolean canEarnResourceCard()
	{
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * canSpendResourced: Return whether a resource can be spent.
	 */
	@Override
	public boolean canSpendResources()
	{
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * getPlayerIndex: Return the Index of the player
	 */
	@Override
	public int getPlayerIndex()
	{
		// TODO Auto-generated method stub
		return 0;
	}	
	/**
	 * setPlayerIndex: Set the Index of the player
	 * @param playerIndex the new value for the player Index
	 */
	@Override
	public void setPlayerIndex(int playerIndex)
	{
		// TODO Auto-generated method stub
		
	}
	
}
