package client.model;

import shared.game.ResourceList;

/**
 * Bank: Defines the Bank, where all extra cards are kept
 */
public class Bank
{
	private ResourceList cardslist;
	
	/**
	 * Bank constructor
	 */
	public Bank()
	{
		// TODO Auto-generated constructor stub
	}
	/**
	 * hasCardAvailable: Checks to see if the bank has any of
	 * the requested card available. Returns resulting boolean.
	 */
	public boolean hasCardAvailable()
	{
		return false;
	}
	/**
	 * @return the cardslist
	 */
	public ResourceList getCardslist()
	{
		return cardslist;
	}
	/**
	 * @param cardslist the cardslist to set
	 */
	public void setCardslist(ResourceList cardslist)
	{
		this.cardslist = cardslist;
	}
	
	
}
