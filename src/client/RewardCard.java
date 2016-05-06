package client;

import shared.game.Card;

public abstract class RewardCard extends Card
{
	public RewardCard(String type) 
	{
		super(type);
		// TODO Auto-generated constructor stub
	}

	public abstract boolean canBePossessed();

	public int getPlayerIndex() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public void setPlayerIndex(int playerIndex) 
	{
		// TODO Auto-generated method stub
		
	}	
}
