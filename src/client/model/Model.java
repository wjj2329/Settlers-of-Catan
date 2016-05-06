package client.model;

import java.util.ArrayList;

import shared.game.Bank;
import shared.game.map.CatanMap;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * Model: The client that handles talking with the server.
 */
public class Model 
{
	private Bank bank;
	private MessageList chat;
	private MessageList log;
	private CatanMap map;
	private TradeOffer tradeoffer;
	private TurnTracker turntracker;
	private Index version;
	private Index winner;
	//this is a change
	/**
	 * ArrayList of players in the game.
	 */
	ArrayList<Player> myplayers=new ArrayList<>();
	
	/**
	 * Model Constructor
	 */
	public Model()
	{
		
	}
	
	/**
	* CreateGame function: Creates a new game. 
	**/
	public void createGame()
	{
		
	}
	
	/**
	 * ListGames: Lists all the games that currently exist. 
	 */
	public void listGames()
	{
		
	}
	
	/**
	 * JoinGame: The player can join a game. Games must have 4 players in order 
	 * to start!
	 */
	public void joinGame()
	{
		
	}
	
	/**
	 * SerializeModel: Serializes all our data to JSON so that the server can have
	 * the data.
	 */
	public void serializeModel()
	{
		
	}
	
	/**
	 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and 
	 * puts it into the model. 
	 */
	public void updateFromJSON()
	{
		
	}
}
