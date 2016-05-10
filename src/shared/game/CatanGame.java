package shared.game;

import java.util.ArrayList;

import shared.chat.Chat;
import shared.game.map.CatanMap;
import shared.game.player.Player;
/**
 * Catan Game object so that we can have a game accessible to be modified. 
 */


public class CatanGame
{
	public static CatanGame singleton=new CatanGame();

	public ArrayList<Player> getMyplayers() {
		return myplayers;
	}

	public void setMyplayers(ArrayList<Player> myplayers) {
		this.myplayers = myplayers;
	}

	public Chat getMychat() {
		return mychat;
	}

	public void setMychat(Chat mychat) {
		this.mychat = mychat;
	}

	public CatanMap getMymap() {
		return mymap;
	}

	public void setMymap(CatanMap mymap) {
		this.mymap = mymap;
	}
	
	public void addPlayer(Player player)
	{
		myplayers.add(player);
	}

	public boolean canCreatePlayer(Player newplayer)
	{
		for(int i=0; i<myplayers.size(); i++)
		{
			System.out.println("i compare "+myplayers.get(i).getColor()+" and this "+newplayer.getColor());
			if(myplayers.get(i)==newplayer)
			{
				return false;
			}
			if(myplayers.get(i).getColor().equals(newplayer.getColor()))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * an array containing the players for the game. 
	 */
	private ArrayList<Player>myplayers=new ArrayList();
	/**
	 * the map for the game. 
	 */
	CatanMap mymap=new CatanMap(0);
	/**
	 * the chat system
	 */
	Chat mychat=new Chat();
	
	/**
	 *  a function to see if we can start the game
	 *  @exception throws exception if not able to start the game for anything that would prevent from starting
	 *  such as not enough players, invalid Map,  Internet problems,  Server failure etc. 
	 *  @post return true if we can start the game. 
	 * 	@return
	 */
	boolean canStartGame()
	{
		return true;	
	}
	/**
	 * a function that starts the game nothing too fancy.   
	 */
	void startGame()
	{
		
	}
	void clear()
	{
		mymap=null;
		mychat=null;
		myplayers=null;
	}

}
