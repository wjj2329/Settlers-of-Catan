package shared.game;

import java.util.ArrayList;

public class Bank 
{
	/**
	 * An Array List of cards that the bank has available to offer a player
	 */
	ArrayList<Card>mycards=new ArrayList<>();
	
	/**
	 *  A function that checks to see if the Bank can even give a card to a player
	 */
	boolean HasCardAvailable(String cardtype)
	{
		return true;
	}
	/**
	 *  A function that gives a player a card
	 */
	Card giveplayercard()
	{
		Card cardtogive=null;
		return cardtogive;
	}
	
	
	
	
}
