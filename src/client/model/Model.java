package client.model;

import java.util.ArrayList;

import shared.game.Bank;
import shared.game.player.Player;
//import shared.game.map.Map;

/**
 * Model: The client that handles talking with the server.
 */
public class Model 
{
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
