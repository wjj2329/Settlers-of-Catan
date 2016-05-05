package client.model;

import java.util.ArrayList;

import javax.smartcardio.Card;

public class TradeOffer 
{
	int sender;
	int receiver;
	ArrayList<Card>offer=new ArrayList<Card>();
	
	boolean canTrade()
	{
		return true;
	}
	
	void offerTrade(int playerid)
	{
		return;
	}
	
	Card maketrade()
	{
		Card mycard=null;
		return mycard;
	}

}
