package client.model;

import java.util.ArrayList;

import javax.smartcardio.Card;

import shared.game.ResourceList;
import shared.game.player.Player;

/**
 * 
 * A class that handles trade offers
 *
 */
public class TradeOffer 
{
	private int sender;

	public ResourceList getMylist()
	{
		return mylist;
	}
	public void setMylist(ResourceList mylist)
	{
		this.mylist=mylist;
	}
	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}



	private int receiver;
	private ResourceList mylist=new ResourceList();
	
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
