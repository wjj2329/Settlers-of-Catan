package client;

public class LargestArmyCard extends RewardCard
{
	/**
	 * KnightCard constructor
	 */
	public LargestArmyCard(String type)
	{
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * canBePossessed: Return whether the LargestArmyCard can be possessed
	 */
	@Override
	public boolean canBePossessed()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getPlayerIndex()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void setPlayerIndex(int playerIndex)
	{
		// TODO Auto-generated method stub
		
	}
	
}
