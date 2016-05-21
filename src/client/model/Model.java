package client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import server.proxies.IServer;
import server.proxies.MockServer;
import server.proxies.ServerProxy;
import shared.definitions.CatanColor;
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
	private TurnTracker turntracker=new TurnTracker(null,null, null,null);
	private int version;
	private Index winner;
	private ArrayList<CatanGame> gamelist = new ArrayList<CatanGame>();

	/**
	 * Map of players in the game.
	 */
	Map<Index, Player> myplayers = new HashMap<>();
	
	/**
	 * Model Constructor
	 */
	public Model()
	{
		
	}
	
	/**
	 * CreateGame function: Creates a new game.
	 * I changed this to NewMockServer so that nothing breaks.
	 * @param title 
	 * @param randomPorts 
	 * @param randomlyPlaceHexes 
	 * @param randomlyPlaceNumbers 
	 **/
	public void createGame(boolean randomlyPlaceNumbers, boolean randomlyPlaceHexes, boolean randomPorts, String title) throws Exception 
	{
		CatanGame game = new CatanGame(randomlyPlaceNumbers, randomlyPlaceHexes, randomPorts, title);
		ModelFacade.facadeCurrentGame.getServer().createGame(title, randomlyPlaceHexes, randomlyPlaceNumbers, randomPorts);
	}
	
	/**
	 * ListGames: Lists all the games that currently exist.
	 */
	public ArrayList<CatanGame> listGames()
	{
		return gamelist;
	}
	
	public void setListGames(ArrayList<CatanGame> games){
		System.out.println("sdfasdf");
		gamelist = games;
		System.out.println("done");
	}
	
	
	/**
	 * JoinGame: The player can join a game. Games must have 4 players in order
	 * to start!
	 */
	public void joinGame(CatanColor color, int gameindex)
	{
		ModelFacade.facadeCurrentGame.getServer().JoinGame(gameindex, color.name().toLowerCase());
		//gamelist.get(gameindex).addPlayer(player);
	}
	
	public int getVersion()
	{
		return version;
	}
	
	public void setVersion(int newVersion)
	{
		version = newVersion;
	}

	/**
	 * @return the turntracker
	 */
	public TurnTracker getTurntracker()
	{
		return turntracker;
	}

	/**
	 * @return the server
	 */

	
}
