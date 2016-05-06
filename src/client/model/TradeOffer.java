package client.model;

import java.util.ArrayList;

import javax.smartcardio.Card;

import shared.game.player.Player;

/**
 * 
 * A class that handles trade offers
 *
 */
public class TradeOffer 
{
	int sender;
	int receiver;
	ArrayList<Card>offer=new ArrayList<Card>();
	
	/**
	 * @exception throws exception if player is Invalid/null
	 * @pre: player is not null player is valid.  
	 * @post: returns true if player can trade and false if can't
	 * @return
	 */
	boolean canTrade(Player player)
	{
		return true;
	}
	
	/**
	 * @exception throws exception if player is Invalid/null
	 * @pre: player is not null player is valid.  
	 * @post: returns true if player can trade and false if can't
	 * @param playerid
	 */
	void offerTrade(int playerid)
	{
		return;
	}
	
	/**
	 * makes trade 
	 */
	Card maketrade()
	{
		Card mycard=null;
		return mycard;
	}

}
