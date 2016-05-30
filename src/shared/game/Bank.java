package shared.game;

import java.util.Random;

import client.resources.ResourceCard;
import server.ourserver.ServerFacade;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.game.Card;
//import shared.game.PlayerNotFoundException;
import shared.game.ResourceList;

/**
 * Bank: Defines the Bank, where all extra cards are kept
 */
public class Bank
{
	private ResourceList resourcelist;
	private DevCardList devcardlist;
	
	
	public DevCardList getDevCardList()
	{
		return devcardlist;
	}

	
	
	/**
	 * Bank constructor
	 */
	public Bank()
	{
		//Initialize cards to full deck
		clear();
	}



	public void clear()
	{
		resourcelist = new ResourceList(19,19,19,19,19);
		devcardlist = new DevCardList(2,5,2,14,2);
	}

	/**
	 * hasCardAvailable: Checks to see if the bank has any of
	 * the requested card available. Returns resulting boolean.
	 */


	public boolean CanBankGiveDevelopmentCard(DevCardType mytype)
	{
		switch(mytype)
		{
			case MONOPOLY:
				if(devcardlist.getMonopoly()>0)
				{
					return true;
				}
				else
				{
					return false;
				}				
			case SOLDIER:
				if(devcardlist.getSoldier()>0)
				{
					return true;
				}
				else
				{
					return false;
				}
			case YEAR_OF_PLENTY:
				if(devcardlist.getYearOfPlenty()>=1)
				{
					return true;
				}
				else
				{
					return false;
				}
			case ROAD_BUILD:		
				if(devcardlist.getRoadBuilding()>=1)
				{
					return true;
				}
				else
				{
					return false;
				}
			case MONUMENT:			
				if(devcardlist.getMonument()>0)
				{
					return true;
				}
				else
				{
					return false;
				}
		}

		return false;
	}
	public boolean CanBankGiveResourceCard(ResourceType mytype)
	{
		switch(mytype)
		{
			case WOOD:
				if(resourcelist.getWood()>0)
				{
					return true;
				}
				else
				{
					return false;
				}
			case BRICK:
				if(resourcelist.getBrick()>0)
				{
					return true;
				}
				else
				{
					return false;
				}
			case WHEAT:
				if(resourcelist.getWheat()>0)
				{
					return true;
				}
				else
				{
					return false;
				}
			case SHEEP:
				if(resourcelist.getSheep()>0)
				{
					return true;
				}
				else
				{
					return false;
				}
			case ORE:
				if(resourcelist.getOre()>0)
				{
					return true;
				}
				else
				{
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
		if (this.resourcelist == null)
		{
			this.resourcelist = new ResourceList();
		}
		return resourcelist;
	}


	public void setDevCardList(DevCardList devCardList)
	{
		this.devcardlist=devCardList;
	}
	public void setDevCardList(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty)
	{
		devcardlist.setMonopoly(monopoly);
		devcardlist.setMonument(monument);
		devcardlist.setRoadBuilding(roadBuilding);
		devcardlist.setSoldier(soldier);
		devcardlist.setYearOfPlenty(yearOfPlenty);
	}
	/**
	 * @param cardslist the cardslist to set
	 */
	public void setResourceCardslist(ResourceList cardslist)
	{
		this.resourcelist = cardslist;
	}

	public void setResourceCardslist(int brick, int wheat, int sheep, int wood, int ore)
	{
		resourcelist.setBrick(brick);
		resourcelist.setWheat(wheat);
		resourcelist.setSheep(sheep);
		resourcelist.setWood(wood);
		resourcelist.setOre(ore);
	}
	

	public String buyDevCard()
	{
		if(devcardlist.isEmpty())
		{
			return "dry";
		}
		Random rnd = new Random();
		int random = rnd.nextInt(5);
		String type = "";
		switch(random)
		{
		case 0:
			if(CanBankGiveDevelopmentCard(DevCardType.MONOPOLY))
			{
				devcardlist.setMonopoly(devcardlist.getMonopoly()-1);
				type = "monopoly";
			}
			else
			{
				type = buyDevCard();
			}
			break;
		case 1:
			if(CanBankGiveDevelopmentCard(DevCardType.MONUMENT))
			{
				devcardlist.setMonument(devcardlist.getMonument()-1);
				type = "monument";
			}
			else
			{
				type = buyDevCard();
			}
			break;
		case 2:
			if(CanBankGiveDevelopmentCard(DevCardType.ROAD_BUILD))
			{
				devcardlist.setRoadBuilding(devcardlist.getRoadBuilding()-1);
				type = "roadbuilding";
			}
			else
			{
				type = buyDevCard();
			}
			break;
		case 3:
			if(CanBankGiveDevelopmentCard(DevCardType.SOLDIER))
			{
				devcardlist.setSoldier(devcardlist.getSoldier()-1);
				type = "soldier";
			}
			else
			{
				type = buyDevCard();
			}
			break;
		case 4:
			if(CanBankGiveDevelopmentCard(DevCardType.YEAR_OF_PLENTY))
			{
				devcardlist.setYearOfPlenty(devcardlist.getYearOfPlenty()-1);
				type = "yearofplenty";
			}
			break;
		}
		
		return type;
	}
	
	public boolean getResourceCard(HexType type)
	{
		 switch(type)
		 {
		 case WOOD:
			 if(CanBankGiveResourceCard(ResourceType.WOOD))
			 {
				 resourcelist.setWood(resourcelist.getWood()-1);
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		 case SHEEP:
			 if(CanBankGiveResourceCard(ResourceType.SHEEP))
			 {
				 resourcelist.setSheep(resourcelist.getSheep()-1);
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		 case ORE:
			 if(CanBankGiveResourceCard(ResourceType.ORE))
			 {
				 resourcelist.setOre(resourcelist.getOre()-1);
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		 case WHEAT:
			 if(CanBankGiveResourceCard(ResourceType.WHEAT))
			 {
				 resourcelist.setWheat(resourcelist.getWheat()-1);
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		 case BRICK:
			 if(CanBankGiveResourceCard(ResourceType.BRICK))
			 {
				 resourcelist.setBrick(resourcelist.getBrick()-1);
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		 }
		 
		 return false;
	}
	
	
	
	
	
	
}
