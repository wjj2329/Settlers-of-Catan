package shared.game;

import java.util.ArrayList;

/** A class to keep track of cards that the "Bank" has avaible and can 
 * give to players
 * 
 * @author williamjones
 *
 */
public class Bank 
{
	/**
	 * An Array List of cards that the bank has available to offer a player
	 */
	ArrayList<Card>mycards=new ArrayList<>();
	
	/**
	 * @param cardtype the non null type that the card is. 
	 *  A function that checks to see if the Bank can even give a card to a player
	 *  @return returns false if card is not available
	 */
	boolean HasCardAvailable(String cardtype)
	{
		return true;
	}
	
	/**
	 * @return returns a NON null valid Card object to the player
	 * A function that gives a player a card
	 * @throws PlayerNotFoundException if there is no corresponding player object
	 * with the said playerid 
	 */
	Card giveplayercard(int playerid)
	{
		Card cardtogive=null;
		return cardtogive;
	}
	
	
}
