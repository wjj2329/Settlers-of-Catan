package shared.game;

import shared.definitions.ResourceType;
import shared.game.Card;
//import shared.game.PlayerNotFoundException;
import shared.game.ResourceList;

/**
 * Bank: Defines the Bank, where all extra cards are kept
 */
public class Bank
{
	private static Bank singleton = null;
	private ResourceList cardslist= null;
	
	/**
	 * Bank constructor
	 */
	private Bank()
	{
		// TODO Auto-generated constructor stub
	}

	public static Bank getSingleton()
	{
		if (singleton == null)
		{
			singleton = new Bank();
		}
		return singleton;
	}

	public void clear()
	{
		cardslist = null;
	}

	/**
	 * hasCardAvailable: Checks to see if the bank has any of
	 * the requested card available. Returns resulting boolean.
	 */
	public boolean CanBankGiveCard(ResourceType mytype) throws Exception
	{
		if (cardslist == null)
		{
			cardslist = new ResourceList();
		}
		switch(mytype)
		{
			case WOOD:
			{
				if(cardslist.getWood()<0)
				{
					Exception e=new Exception();
					e.printStackTrace();
					throw e;
				}
				if(cardslist.getWood()>0)
				{
					return true;
				}
				else return false;
			}
			case BRICK:
			{
				if(cardslist.getBrick()<0)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(cardslist.getBrick()>0)
				{
					return true;
				}
				else return false;
			}
			case WHEAT:
			{
				if(cardslist.getWheat()<0)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;

				}
				if(cardslist.getWheat()>0)
				{
					return true;
				}
				else return false;
			}
			case SHEEP:
			{
				if(cardslist.getSheep()<0)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(cardslist.getSheep()>0)
				{
					return true;
				}
				else
					return false;

			}
			case ORE:
			{
				if(cardslist.getOre()<0)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(cardslist.getOre()>0)
				{
					return true;
				}
				else
					return false;

			}
		}
		return false;
	}
	/**
	 * @return the cardslist
	 */
	public ResourceList getCardslist()
	{
		if (this.cardslist == null)
		{
			this.cardslist = new ResourceList();
		}
		return cardslist;
	}
	/**
	 * @param cardslist the cardslist to set
	 */
	public void setCardslist(ResourceList cardslist)
	{
		if (this.cardslist == null)
		{
			this.cardslist = new ResourceList();
		}
		this.cardslist = cardslist;
	}

	public void setCardslist(int brick, int wheat, int sheep, int wood, int ore)
	{
		if (this.cardslist == null)
		{
			this.cardslist = new ResourceList();
		}
		cardslist.setBrick(brick);
		cardslist.setWheat(wheat);
		cardslist.setSheep(sheep);
		cardslist.setWood(wood);
		cardslist.setOre(ore);
	}
	
	/**
	 * @return returns a NON null valid Card object to the player
	 * A function that gives a player a card
	 * @throws if there is no corresponding player object
	 * with the said playerid 
	 * @pre playerid is nonnegative
	 */
	Card giveplayercard(int playerid)
	{
		if (this.cardslist == null)
		{
			this.cardslist = new ResourceList();
		}
		Card cardtogive=null;
		return cardtogive;
	}
	
	
}
