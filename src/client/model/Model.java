package client.model;

import java.util.ArrayList;

import shared.game.Bank;
import shared.game.CatanGame;
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
	private double version;
	private Index winner;
	private ArrayList<CatanGame> gamelist;
	/**
	 * ArrayList of players in the game.
	 */
	ArrayList<Player> myplayers = new ArrayList<>();
	
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
		gamelist.add(new CatanGame());
	}
	
	/**
	 * ListGames: Lists all the games that currently exist.
	 */
	public void listGames()
	{
		for (CatanGame game : gamelist)
		{
			System.out.println(game.toString());
		}
	}
	
	/**
	 * JoinGame: The player can join a game. Games must have 4 players in order
	 * to start!
	 */
	public void joinGame(Player player, int gameindex)
	{
		gamelist.get(gameindex).addPlayer(player);
	}
	
	public double getVersion()
	{
		return version;
	}
	
	public void setVersion(double newVersion)
	{
		version = newVersion;
	}
}
