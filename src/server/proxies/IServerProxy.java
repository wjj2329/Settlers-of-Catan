package server.proxies;

import client.data.*;
import shared.definitions.*;
import shared.locations.*;

import org.json.JSONObject;

/**
 * Interface for the ServerProxy
 */
public interface IServerProxy {

	//Non-Move APIs
	
	/** 
	 * Logs the caller in to the server, and sets their catan.userHTTP cookie.
	 * The passed-in username and password may correspond to the credentials of any registered user.
	 * 
	 * @param username 
	 * @param password
	 * 
	 */
	String loginUser(String username, String password);
	
	/**
	 * This method does two things:
	 * 	1) Creates a new user account
	 * 	2) Logs the caller in to the server as the new user, and sets their catan.userHTTP cookie
	 * 
	 * @param username 
	 * @param password
	 * 
	 */
	String registerUser(String username, String password);
	
	/**
	 * Returns information about all of the current games on the server.
	 *
	 */
	String getAllCurrentGames();
	
	/**
	 * Creates a new game on the server.
	 * @param gameName
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * 
	 */
	String createAGame(String gameName, boolean randomTiles, boolean randomNumbers, boolean randomPorts);
	
	/** 
	 * Adds the player to the specified game and sets their catan.game cookie.
	 *
	 * @param gameID
	 * @param color
	 * @param player
	 *
	 */
	String addPlayertoGame(int gameID, CatanColor color, PlayerInfo player);
	
	/** 
	 * This method is for testing and debugging purposes. When a bug is found, you can use the 
	 * /games/saves method to save the state of the game to file, and attach the file to a bug
	 * report. A developer can later restore the state of the game when the bug occurred by 
	 * loading the previously saved file using the /game/load/ method. Game files are saved to 
	 * and loaded from the server's saves/directory. 
	 *
	 * @param gameID
	 * @param fileName
	 * 
	 */
	String saveGame(int gameID, String fileName);
	
	/**
	 * This method is for testing and debugging purposes. When a bug is found, you can use the 
	 * /games/saves method to save the state of the game to file, and attach the file to a bug
	 * report. A developer can later restore the state of the game when the bug occurred by 
	 * loading the previously saved file using the /game/load/ method. Game files are saved to 
	 * and loaded from the server's saves/directory. 
	 *
	 * @param name
	 *
	 */
	String loadGame(String name);
	
	/** 
	 * Returns the current state of the game in JSON format, and also includes a "version" number
	 * for the client model.
	 *
	 * @param version
	 * 
	 */
	String getGameCurrentState(int version);
	
	/**
	 * Clears out the command history of the current game.
	 * 
	 */
	String resetCurrentGame();
	
	/**
	 * Returns a list of commands that have been executed in the current game.
	 *
	 */
	String GET();
	
	/**
	 * 
	 * Executes the specified command list in the current game.
	 */
	String POST();
	
	/**
	 * 
	 * Returns a list of supported AI player types
	 */
	String getAIList();
	
	/**
	 * 
	 * Adds an AI player to the current game.
	 */
	String addAIPlayer(String logLevel);
	
	/**
	 * Sets the server's logging level
	 * 
	 * @param logLevel
	 * 
	 */
	String changeLogLevel(String logLevel);
	
	//Move APIs uses /moves/*
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param content 
	 * 	the message you want to send
	 * 
	 */
	void sendChat(String type, int playerIndex, String content);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param willAccept
	 *
	 */
	void acceptTrade(String type, int playerIndex, boolean willAccept);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param discardedCards
	 * 
	 */
	void discardCards(String type, int playerIndex, JSONObject discardedCards);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param number
	 * 
	 */
	void rollNumber(String type, int playerIndex, int number);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param free
	 * @param roadLocation
	 * 
	 */
	void buildRoad(String type, int playerIndex, boolean free, EdgeLocation roadLocation);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param free
	 * @param vertexLocation
	 * 
	 */
	void buildSettlement(String type, int playerIndex, boolean free, VertexLocation vertexLocation);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param vertexLocation
	 * 
	 */
	void buildCity(String type, int playerIndex, VertexLocation vertexLocation);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param offer
	 * @param playerIndex
	 * 
	 */
	void offerTrade(String type, int playerIndex, JSONObject offer, int playerIndex);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param ratio
	 * @param inputResource
	 * @param outputResource
	 * 
	 */
	void maritimeTrade(String type, int playerIndex, int ratio, String inputResource, String outputResource);
	
	/**
	 * @param type
	 * @param location
	 * @param playerIndex
	 * 
	 */
	void robPlayer(String type, HexLocation location, int playerIndex);
	
	/**
	 * @param type
	 * @param playerIndex
	 * 
	 */
	void finishTurn(String type, int playerIndex);
	
	/**
	 * @param type
	 * @param playerIndex
	 * 
	 */
	void buyDevCard(String type, int playerIndex);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param location
	 * @param victimIndex
	 * 
	 */
	void playSoldier(String type, int playerIndex, HexLocation location, int victimIndex);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param resource1
	 * @param resource2
	 * 
	 */
	void playYearofPlenty(String type, int playerIndex, String resource1, String resource2);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param spot1
	 * @param spot2
	 * 
	 */
	void playRoadBuilding(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2);
	
	/**
	 * @param type
	 * @param playerIndex
	 * @param resource
	 * 
	 */
	void playMonopoly(String type, int playerIndex, String resource);
	
	/**
	 * @param type
	 * @param playerIndex
	 * 
	 */
	void playMonument(String type, int playerIndex);

	
	
}
