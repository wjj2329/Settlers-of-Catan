package server.proxies;


import client.data.*;
import client.model.ClientCommunicator;
import client.model.Model;
import server.param.*;
import shared.definitions.*;
import shared.game.ResourceList;
import shared.locations.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementation for the RealServerProxy
 */
public class ServerProxy implements IServer {

	String usercookie;
	String gamecookie; 
	
	/**
	 * Logs the caller in to the server, and sets their catan.userHTTP cookie.
	 * The passed-in username and password may correspond to the credentials of
	 * any registered user.
	 * 
	 * @param username:
	 *            the player's username
	 * @param password:
	 *            the player's password
	 * 
	 * @pre username is not null, password is not null
	 * @post if username and password is valid: 
	 * 1. The server returns an HTTP 200 success response with "Success" in the body. 
	 * 2. the HTTP response headers set the catan.user cookie to contain the identity of 
	 *  the logged-in player.cookie uses "Path=/", and its value contains a url-encoded
	 *  JSON object in the form: {"name”:STRING,"password”:STRING,"playerID”:INTEGER}. 
	 * If username and password are not valid or operation fails for any reason 
	 * 1. The server returns an HTTP 400 error response, and the body contains an error message.
	 * 
	 */ //GET
	@Override
	public boolean loginUser(String username, String password) throws JSONException {
		final String URL_SUFFIX = "/user/login";
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		Param param = new LoginParam(username, password);
		String responseData = clientCommunicator.send(URL_SUFFIX, param);
		if(responseData.equals("Success")){
			return true;
		}
	
		return false; 
	}

	/**
	 * This method does two things: 
	 * 1) Creates a new user account 
	 * 2) Logs the caller in to the server as the new user, and sets their catan.userHTTP
	 * cookie
	 * 
	 * @param username:
	 *            the player's username
	 * @param password:
	 *            the player's password
	 * 
	 * @pre username is not null password is not null The
	 *                specified username is not already in use
	 * @post 
	 * If there is no existing user with the specified username, 
	 *	1. A new user account has been created with the specified username and password.
	 * 	2. The server returns an HTTP 200 success response with "Success” in the body. 
	 *	3. The HTTP response headers set the catan.user cookie to contain the identity of the 
	 * 	logged in player. The cookie uses "Path=/", and its value contains a url encoded
	 * 	JSONobject of the following form: {"name”:STRING,"password”:STRING,"playerID”:INTEGER}.
	 * If there is already an existing user with the specified name, or operation fails for any reason 
	 *  1. The server returns and HTTP 400 error response, and the body contains an error message.
	 */ //POST
	@Override
	public String registerUser(String username, String password) throws JSONException {
		final String URL_SUFFIX = "/user/register";
		
		Param param = new RegisterParam(username, password);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns information about all of the current games on the server.
	 *
	 * @pre None! :D
	 * @post If the operation succeeds, 1.The server returns an
	 *                HTTP 200 success response. 2.The body contains a JSON
	 *                array containing a list of objects that contain
	 *                information about the server’s games If the operation
	 *                fails, 1.The server returns an HTTP 400 error response,
	 *                and the body contains an error message
	 * 
	 *///GET 
	@Override
	public String getAllCurrentGames() throws JSONException {
		final String URL_SUFFIX = "/games/list";
		
		Param param = new ListAllGamesParam();
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		// TODO Auto-generated method stub
		return null;	
	}

	/**
	 * Creates a new game on the server.
	 * 
	 * @param name
	 *            game name
	 * @param randomTiles if random tiles will be created
	 * @param randomNumbers if random numbers will be assigned
	 * @param randomPorts  if random ports will be created
	 * 
	 * @pre name != null, randomeTiles, randomNumbers, and
	 *                randomPorts contain valid boolean values
	 * @post If the operation succeeds, 1.A new game with the
	 *                specified properties has been created 2.The server returns
	 *                an HTTP 200 success response. 3.The body contains a
	 *                JSONobject describing the newly created game. If the
	 *                operation fails, 1.The server returns an HTTP 400 error
	 *                response, and the body contains an error message
	 * 
	 *///POST
	@Override
	public String createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) throws JSONException {
		final String URL_SUFFIX = "/games/create";
		
		Param param = new CreateGameParam(name, randomTiles, randomNumbers, randomPorts);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds the player to the specified game and sets their catan.game cookie.
	 *
	 * @param gameID the id of game
	 * @param color the players color choice
	 *
	 * @pre 
	 * 1.The user has previously logged in to the server (i.e.,they have a valid catan.user HTTP cookie). 
	 * 2.The player may join the game because 
	 * 2.a They are already in the game, OR 
	 * 2.b There is space in the game to add a new player 
	 * 3.The specified game ID is valid 
	 * 4.The specified color is valid(red,green,blue,yellow,puce,brown,white,purple, orange)
	 * @post If the operation succeeds, 1. The server returns an
	 *                HTTP 200 success response with "Success" in the body. 2.
	 *                The player is in the game with the specified
	 *                color(i.e.calls to /games/list method will show the player
	 *                in the game with the chosen color). 3. The serve response
	 *                includes the "Set cookie” response header setting the
	 *                catan.game HTTP cookie If the operation fails, 1.The
	 *                server returns an HTTP 400 error response, and the body
	 *                contains an error message
	 * 
	 *///POST
	@Override
	public String JoinGame(int gameID, String color) throws JSONException {
		final String URL_SUFFIX = "/games/join";
		
		Param param = new JoinGameParam(gameID, color);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method is for testing and debugging purposes. When a bug is found,
	 * you can use the /games/saves method to save the state of the game to
	 * file, and attach the file to a bug report. A developer can later restore
	 * the state of the game when the bug occurred by loading the previously
	 * saved file using the /game/load/ method. Game files are saved to and
	 * loaded from the server's saves/directory.
	 *
	 * @param gameID the id of the game to be saved
	 * @param fileName the file name you want to save it under
	 * 
	 * @pre 1.The specified game ID is valid 2.The specified file
	 *                name is valid(i.e.,not null or empty)
	 * @postIf the operation succeeds, 1. The server returns an
	 *                HTTP 200 success response with "Success" in the body. 2.
	 *                The current state of specified game (including its ID) has
	 *                been saved to the specified file in the server's saves/
	 *                directory If the operation fails, 1.The server returns an
	 *                HTTP 400 error response, and the body contains an error
	 *                message
	 * 
	 *///POST
	@Override
	public String saveGame(int gameID, String fileName) throws JSONException {
		final String URL_SUFFIX = "/games/save";
		
		Param param = new JoinGameParam(gameID, fileName);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		return null;
	}

	/**
	 * This method is for testing and debugging purposes. When a bug is found,
	 * you can use the /games/saves method to save the state of the game to
	 * file, and attach the file to a bug report. A developer can later restore
	 * the state of the game when the bug occurred by loading the previously
	 * saved file using the /game/load/ method. Game files are saved to and
	 * loaded from the server's saves/directory.
	 *
	 * @param name the name of the saved game file that you want to load
	 *
	 * @pre 1.A previously saved game file with the specified
	 *                name exists in the server’s saves/ directory
	 * @post If the operation succeeds, 1. The server returns an
	 *                HTTP 200 success response with "Success" in the body. 2.
	 *                The game in the specified file has been loaded into the
	 *                server and its state restored (including its ID). If the
	 *                operation fails, 1.The server returns an HTTP 400 error
	 *                response, and the body contains an error message
	 * 
	 *///POST
	@Override
	public String loadGame(String name) throws JSONException {
		final String URL_SUFFIX = "/games/load";
		
		Param param = new LoadGameParam(name);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		return null;
	}

	/**
	 * Returns the current state of the game in JSON format, and also includes a
	 * "version" number for the client model.
	 *
	 * @param version
	 *            version number for the client model
	 * 
	 * @pre 1.The caller has previously logged into the server
	 *                and joined a game(i.e.,they have valid catan.user and
	 *                catan.game HTTP cookies). 2.If specified, the version
	 *                number is included as the "version” query parameter in the
	 *                request URL, and its value is a valid integer
	 * @post If the operation succeeds, 1. The server returns an
	 *                HTTP 200 success response 2. The response body contains
	 *                JSON data a. The full client model JSON is returned if the
	 *                caller does not provide a version number or provide
	 *                version number does not match the version on the server.
	 *                b. "true" (true in double quotes) is returned if the
	 *                caller provided a version number, and the version number
	 *                matched the version number on the server If the operation
	 *                fails, 1.The server returns an HTTP 400 error response,
	 *                and the body contains an error message
	 * 
	 *///GET - necesita galleta! haha
	@Override
	public String getGameCurrentState(int version) throws JSONException {
		final String URL_SUFFIX = "/game/model";
		
		Param param = new GetGameCurrentStateParam();
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		return null;
	}

	/**
	 * Clears out the command history of the current game.
	 * 
	 * @pre 1. The caller has previously logged in to the server
	 *                and joined a game (i.e., they have a valid catan.user and
	 *                catan.game HTTP cookies)
	 * @post If the operation succeeds, 1. The game's command
	 *                history has been cleared out 2. The game's players have
	 *                NOT been cleared out 3. The server returns an HTTP 200
	 *                success response. 4. The body contains the game's updated
	 *                client model JSON If the operation fails, 1.The server
	 *                returns an HTTP 400 error response, and the body contains
	 *                an error message
	 * 
	 * 
	 *///POST needs cookie! 
	@Override
	public String resetCurrentGame() throws JSONException {
		final String URL_SUFFIX = "/game/reset";
		
		Param param = new ResetCurrentGameParam();
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		return null;
	}

	/**
	 * Returns a list of commands that have been executed in the current game.
	 *
	 * @pre 1. The caller has previously logged in to the server
	 *                and joined a game (i.e., they have valid catan.user and
	 *                catan.game HTTP cookies).
	 * @post If the operation succeeds, 1. The server returns an
	 *                HTTP 200 success response 2. The body contains a JSON
	 *                array of commands that have been executed in the game. If
	 *                the operation fails, 1.The server returns an HTTP 400
	 *                error response, and the body contains an error message
	 * 
	 *///GET
	@Override
	public String GETCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * Executes the specified command list in the current game.
	 * 
	 * @pre 1. The caller has previously logged in to the server
	 *                and joined a game (i.e., they have valid catan.user and
	 *                catan.game HTTP cookies).
	 * @postIf the operation succeeds, 1. The passed-in command
	 *                list has been applied to the game. 2. The server returns
	 *                an HTTP 200 success response. 3. The body contains the
	 *                game's updated client model JSON If the operation fails,
	 *                1.The server returns an HTTP 400 error response, and the
	 *                body contains an error message
	 * 
	 *///POST needs game cookie
	@Override
	public String POSTCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * Returns a list of supported AI player types
	 * 
	 * @pre NONE! :D
	 * @post If the operation succeeds, 1. The server returns an
	 *                HTTP 200 success response 2. The body contains a JSON
	 *                string array enumerating the different types of AI
	 *                players. These are the values that may be passed to the
	 *                /game/addAI method.
	 * 
	 *///GET
	@Override
	public String listAI() throws JSONException {
		final String URL_SUFFIX = "/game/listAI";
		
		Param param = new ListAIParam();
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		return null;
	}

	/**
	 * 
	 * Adds an AI player to the current game. Must login and join a game before
	 * calling this method.
	 * 
	 * @pre 1. The caller has previously logged in to the server
	 *                and joined a game (i.e., they have valid catan.user and
	 *                catan.game HTTP cookies). 2. There is space in the game
	 *                for another player(i.e., the game is not "full"). 3. The
	 *                specified "AIType" is valid(i.e., one of the values
	 *                returned by the /game/listAImethod).
	 * @post If the operation succeeds, 1. The server returns an
	 *                HTTP 200 success response with "Success" in the body. 2. A
	 *                new AI player of the specified type has been added to the
	 *                current game. The server selected a name and color for the
	 *                player. If the operation fails, 1.The server returns an
	 *                HTTP 400 error response, and the body contains an error
	 *                message
	 * 
	 */
	@Override
	public String addAIPlayer(String logLevel) throws JSONException {
		final String URL_SUFFIX = "/game/addAI";
		
		Param param = new AddAIParam(logLevel);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		return null;
	}

	/**
	 * Sets the server's logging level
	 * 
	 * @param logLevel a logLevel, Valid values include: SEVER, WARNING, INFO, CONFIG, FINE, 
	 * 						FINER, FINEST
	 * 
	 * @pre The caller specifies a valid logging level. 
	 * @post If the operation succeeds, 
	 * 						1. The server returns an HTTP 200 success response with "Success" in the body.
	 * 						2. The Server is using the specified logging level 
	 *                		If the operation fails, 
	 *                		1.The server returns an HTTP 400 error response, and the body contains an error message
	 * 
	 */
	@Override
	public String changeLogLevel(String logLevel) throws JSONException {
		final String URL_SUFFIX = "/util/changeLogLevel";
		
		Param param = new ChangeLogLevelParam(logLevel);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
		return null;
	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param content
	 *            the message you want to send
	 * 
	 * @pre None!
	 * @post The chat contains your message at the end
	 * 
	 */
	@Override
	public void sendChat(String type, int playerIndex, String content) throws JSONException {
		final String URL_SUFFIX = "/moves/sendChat";
		
		Param param = new SendChatParam(type, playerIndex, content);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		
	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param willAccept
	 *            whether or not you accept the trade the offere
	 *
	 * @preYou have been offered a domestic trade To accept the
	 *                offered trade, you have the required resources
	 * @post If you accepted, you and the player who offered swap
	 *                the specified resources If you declined no resources are
	 *                exchanged The trade offer is remove
	 * 
	 */
	@Override
	public void acceptTrade(String type, int playerIndex, boolean willAccept) throws JSONException {
		final String URL_SUFFIX = "/moves/acceptTrade";
		
		Param param = new AcceptTradeParam(type, playerIndex, willAccept);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);

	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param discardedCards
	 *            the cards you are discarding
	 * 
	 * @pre The status of the client model is 'Discarding' You
	 *                have over 7 cards, You have the cards you're choosing to
	 *                discard
	 * @post You gave up the specified resources, If you're the
	 *                last one to discard, the client model status changes to
	 *                'Robbing'
	 * 
	 */
	@Override
	public void discardCards(String type, int playerIndex, ResourceList discardedCards) throws JSONException {
		final String URL_SUFFIX = "/moves/discardCards";
		
		Param param = new DiscardCardsParam(type, playerIndex, discardedCards);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);

	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param number
	 *            integer in the range 2-12 (the number you rolled)
	 * 
	 * @pre It is your turn, the client model's status is
	 *                'Rolling'
	 * @post The client model's status is now in 'Discarding' or
	 *                'Robbing' or 'Playing'
	 * 
	 */
	@Override
	public void rollNumber(String type, int playerIndex, int number) throws JSONException {
		final String URL_SUFFIX = "/moves/rollNumber";
		
		Param param = new RollNumberParam(type, playerIndex, number);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param free
	 *            whether or no you get this piece for free
	 * @param roadLocation
	 *            the new road's location
	 * 
	 * @pre it is your turn, the client model's status is
	 *                'Playing' road location is open, is connected to another
	 *                road owned by the player, is not on the water, you have
	 *                the required resources(1 wood, 1 brick; 1 road)
	 * @post You have the required resources to build a road(1
	 *                wood, 1 brick; 1 road) The road is on the map at the
	 *                specified location if applicable, "longest road" has been
	 *                awarded to the player with the longest road
	 * 
	 */
	@Override
	public void buildRoad(String type, int playerIndex, boolean free, EdgeLocation roadLocation) throws JSONException {
		final String URL_SUFFIX = "/moves/buildRoad";
		
		Param param = new BuildRoadParam(type, playerIndex, roadLocation, free);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param free
	 *            whether or not you get this piece for free
	 * @param vertexLocation
	 *            the location of the settlement
	 * 
	 * 
	 * @pre it is your turn, the client model's status is
	 *                'Playing' the settlement location is open, is not on
	 *                water, is connected to one of your roads except during
	 *                setup. You have the required resources(1 wood, 1 brick, 1
	 *                wheat, 1 sheep; 1 settlement) the settlement cannot be
	 *                placed adjacent to another settlement
	 * @post You lost the required resources to build a
	 *                settlement(1 wood, 1 brick, 1 wheat, 1 sheep; 1
	 *                settlement) the settlement is on the map at the specified
	 *                locatoin
	 * 
	 */
	@Override
	public void buildSettlement(String type, int playerIndex, boolean free, VertexLocation vertexLocation) throws JSONException {
		final String URL_SUFFIX = "/moves/buildSettlement";
		
		Param param = new BuildSettlementParam(type, playerIndex, vertexLocation, free);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);



	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param vertexLocation
	 *            the location of the city
	 * 
	 * @pre it is your turn, the client model's status is
	 *                'Playing' the city location is where you currently have a
	 *                settlement, you have the required resources (2 wheat, 3
	 *                ore; 1 city)
	 * @post you lost resources required to build a city, the
	 *                city is on the map at the specified location, you got a
	 *                settlement back
	 * 
	 */
	@Override
	public void buildCity(String type, int playerIndex, VertexLocation vertexLocation) throws JSONException {
		final String URL_SUFFIX = "/moves/buildCity";
		
		Param param = new BuildCityParam(type, playerIndex, vertexLocation);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the person making offer
	 * @param offer negative numbers mean you get those cards
	 * @param receiver person receiving offer
	 * 
	 * @pre it is your turn, the client model's status is
	 *                'Playing' you have the resources you are offering
	 * @post the trade is offered to the other player(stored in
	 *                the server model)
	 * 
	 */
	@Override
	public void offerTrade(String type, int playerIndex, ResourceList offer,int receiver) throws JSONException {
		final String URL_SUFFIX = "/moves/offerTrade";
		
		Param param = new OfferTradeParam(type, playerIndex, offer, receiver);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);
		

	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player you are robbing or -1 if you are not robbing anyone
	 * @param location new location of robber
	 * @param victimIndex the player you are robbing or -1 if you are not robbing anyone
	 * 
	 * @pre it is your turn, the client model's status is 'Playing'
	 * 					  robber is not being kept in the same location, if a player 
	 * @post the robber is in the new location, the player being robbed(if any) gave one 
	 * 					 one of his resource cards (if randomly selected)
	 * 
	 * 
	 */
	@Override
	public void robPlayer(String type, int playerIndex, HexLocation location, int victimIndex) throws JSONException {
		final String URL_SUFFIX = "/moves/robPlayer";
		
		Param param = new RobPlayerParam(type, playerIndex, location, victimIndex);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);

	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player's position in the game's turn order
	 * 
	 * @pre it is your turn, the client model's status is 'Playing'
	 * 					
	 * @post the cards in your new dev card hand have been transferred to your 
	 * 						old dev hand, it is the next player's turn. 
	 * 
	 * 
	 */
	@Override
	public void finishTurn(String type, int playerIndex) throws JSONException {
		final String URL_SUFFIX = "/moves/finishTurn";
		
		Param param = new FinishTurnParam(type, playerIndex);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player's position in the game's turn order
	 * 
	 * @pre it is your turn, the client model's status is 'Playing'
	 * 					you have the required resources( 1 ore, 1 wheat, 1 sheep) there are dev cards left in the deck
	 * @post you have a new card! if monument it is added to old devcard
	 * 						if non monument card it is added to new deve card hand
	 * 
	 * 
	 */
	@Override
	public void buyDevCard(String type, int playerIndex) throws JSONException {
		final String URL_SUFFIX = "/moves/buyDevCard";
		
		Param param = new BuyDevCardParam(type, playerIndex);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player's position in the game's turn order
	 * @param location new robber location
	 * @param victimIndex the player you are robbing or -1 if you are not robbing anyone
	 * 
	 * @pre it is your turn, the client model's status is 'Playing'
	 * 						you have the card you want to play in old dev card hand, 
	 * 						you have not yet played a non-monument dev card this turn
	 * 						if robber is not being kept in the same location, if player being robbed they have the resource cards
	 * @post robber is in the new location, player being robbed gave you a resource
	 * 						card, "largest army" awarded to the player who has played the most soldier card, no allowed to play other deelopment cards 
	 * 						during this turn
	 * 
	 * 
	 */
	@Override
	public void playSoldier(String type, int playerIndex, HexLocation location, int victimIndex) throws JSONException {
		final String URL_SUFFIX = "/moves/Soldier";
		
		Param param = new PlaySoldierParam(type, playerIndex, location, victimIndex);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player's position in the game's turn order
	 * @param resource1 first resource you want to receive
	 * @param resource2 second resource you want to receive
	 * 
	 * @pre  it is your turn, the client model's status is 'Playing'
	 * 						you have the card you want to play in old dev card hand, 
	 * 						two specified resources are in the bank
	 * @post you gained to specified resources
	 * 
	 * 
	 */
	@Override
	public void playYearofPlenty(String type, int playerIndex, String resource1, String resource2) throws JSONException {
		final String URL_SUFFIX = "/moves/Year_of_Plenty";
		
		Param param = new PlayYearOfPlentyParam(type, playerIndex, resource1, resource2);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player's position in the game's turn order
	 * @param spot1 edge location
	 * @param spot2 edge location 
	 * 
	 * @pre  it is your turn, the client model's status is 'Playing'
	 * 						you have the card you want to play in old dev card hand, 
	 * 						first road location is connected to one of your road, second road location is connected to one of your roads
	 * 						or the first road location, neither road location is on the water, you have atleast 
	 * 						two unused roads
	 * @post you have 2 fewer unused roads, 2 new roads appear on the map at the specified
	 * 						locations, "longest road" has been awarded to the correct user
	 * 
	 */
	@Override
	public void playRoadBuilding(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) throws JSONException {
		final String URL_SUFFIX = "/moves/Road_Building";
		
		Param param = new PlayRoadBuildingParam(type, playerIndex, spot1, spot2);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player's position in the game's turn order
	 * @param resource the resource being taken from the others players
	 * 
	 * @pre  it is your turn, the client model's status is 'Playing'
	 * 						you have the card you want to play in old dev card hand, 
	 * @post all of the other players have give you all of their resources cards of the specified type
	 * 
	 * 
	 */
	@Override
	public void playMonopoly(String type, int playerIndex, String resource) throws JSONException {
		final String URL_SUFFIX = "/moves/Monopoly";
		
		Param param = new PlayMonopolyParam(type, playerIndex, resource);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	/**
	 * @param type name of move being executed
	 * @param playerIndex the player's position in the game's turn order
	 * 
	 * @pre  it is your turn, the client model's status is 'Playing'
	 * 						you have the card you want to play in old dev card hand, 
	 * 						you have enough monument cards to win the game
	 * @post you gained a victory point
	 * 
	 * 
	 */
	@Override
	public void playMonument(String type, int playerIndex) throws JSONException {
		final String URL_SUFFIX = "/moves/Monument";
		
		Param param = new PlayMonumentParam(type, playerIndex);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

	@Override
	public Model getGameModel(double version) {
		return null;
	}

	/**
	 * @param type
	 *            name of move being executed
	 * @param playerIndex
	 *            the player's position in the game's turn order
	 * @param ratio
	 *            integer(2, 3, or 4)
	 * @param inputResource
	 *            what you are giving
	 * @param outputResource
	 *            what you are getting
	 * 
	 * 
	 * @pre it is your turn, the client model's status is
	 *                'Playing' you have the resources you are giving, for
	 *                ratios less than 4, you have the correct port for the
	 *                trade
	 * @post the trade has been executed ( offered resources are
	 *                in the bank, and the requested resource has been received)
	 * 
	 */
	
	@Override
	public void maritimeTrade(String type, int playerIndex, int ratio, String inputResource, String outputResource) throws JSONException {
		final String URL_SUFFIX = "/moves/maritimeTrade";
		
		Param param = new MaritimeTradeParam(type, playerIndex, ratio, inputResource, outputResource);
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		
		clientCommunicator.send(URL_SUFFIX, param);


	}

}
