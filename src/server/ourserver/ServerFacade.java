package server.ourserver;

import client.model.Model;
import server.ourserver.commands.*;
import shared.definitions.CatanColor;
import shared.game.Card;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by williamjones on 5/26/16.
 * @author Alex
 * ServerFacade: Acts as a gateway between the ServerProxy and the actual server.
 * 			Key part of phase 3.
 * 		All methods are of type POST unless otherwise specified.
 *
 * 	This should probably have Command objects that are called inside of the functions. I will fix this.
 * 	This also should be a singleton.
 * 	This is in reality the real server. Essentially. For all intents and purposes.
 */
public class ServerFacade
{
	/**
	 * All the games stored in the server. Until phase 4, this will only persist
	 * 	until we restart the server.
	 */
	//private ArrayList<CatanGame> gamesInServer = new ArrayList<>();
	/**
	 * All the registered users in the server. See above comment.
	 */
	private ArrayList<Player> allRegisteredUsers = new ArrayList<>();

	/**
	 * Model for the server side. Used to process input and keep track
	 * 	of all the requisite data.
	 */
	private Model serverModel = new Model();

	private static ServerFacade singleton = null;
	private static int NEXT_USER_ID = 5;

	/**
	 * Command objects.
	 */
	/*private AcceptTradeCommand acceptTradeCommand;
	private AddAICommand addAICommand;
	private BuildCityCommand buildCityCommand;
	private BuildRoadCommand buildRoadCommand;
	private BuildSettlementCommand buildSettlementCommand;
	private BuyDevCardCommand buyDevCardCommand;
	private CreateGameCommand createGameCommand;
	private DiscardCardsCommand discardCardsCommand;
	private FinishTurnCommand finishTurnCommand;
	private GetModelCommand getModelCommand;
	private MaritimeTradeCommand maritimeTradeCommand;
	private OfferTradeCommand offerTradeCommand;
	private PlayMonopolyCommand playMonopolyCommand;
	private PlayMonumentCommand playMonumentCommand;
	private PlayRoadBuildingCommand playRoadBuildingCommand;
	private PlaySoldierCommand playSoldierCommand;
	private PlayYearOfPlentyCommand playYearOfPlentyCommand;
	private RobPlayerCommand robPlayerCommand;
	private RollNumberCommand rollNumberCommand;
	private SendChatCommand sendChatCommand;*/

	/**
	 * Constructor is private in order to avoid multiple instantiations.
	 * We have hard-coded the four default players for testing purposes.
	 */
	private ServerFacade()
	{
		Player sam = new Player("Sam", CatanColor.BLUE, new Index(1));
		sam.setPassword("sam");
		Player mark = new Player("Mark", CatanColor.GREEN, new Index(2));
		mark.setPassword("mark");
		Player brooke = new Player("Brooke", CatanColor.BROWN, new Index(3));
		brooke.setPassword("brooke");
		Player pete = new Player("Pete", CatanColor.WHITE, new Index(4));
		pete.setPassword("pete");
		allRegisteredUsers.add(sam);
		allRegisteredUsers.add(mark);
		allRegisteredUsers.add(brooke);
		allRegisteredUsers.add(pete);
	}

	/**
	 * Returns the singleton instance of ServerFacade.
     */
	public static ServerFacade getInstance()
	{
		if (singleton == null)
		{
			singleton = new ServerFacade();
		}
		return singleton;
	}

	/**
	 * Initializes the serverFacade
	 * @throws ServerException: if the server is invalid or an error occurs
     */
	public static void initialize() throws ServerException
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Logs in the user.
     * @return the player who has just logged in
	 * Go through array of registered users, to see if it finds something with the same
	 * 	username and password.
	 */
	public Player logIn(Player player)
	{
		for (Player p : allRegisteredUsers)
		{
			if (p.equals(player))
			{
				return p;
			}
		}
		return null;
	}

	/**
	 * Registers a new user.
	 * @param username: name they will log in with
	 * @param password: password that they will use
     */
	public void register(String username, String password)
	{
		Player p=new Player(username,CatanColor.PUCE,new Index(NEXT_USER_ID++));
		p.setPassword(password);
		System.out.println("I add a new player");
		allRegisteredUsers.add(p);
	}

	/**
	 * Gets the list of all the games in the server.
	 * GET request.
	 */
	public void getGameList()
	{

	}

	/**
	 * Creates a new game and adds it to the server.
	 * @param name: game's name
	 * @param randomHexes: whether or not it uses randomized hexes
	 * @param randomPorts: whether or not it uses randomized ports
	 * @param randomHexValues: whether or not it uses randomized numbers on the hexes
     */
	public void createGame(String name, boolean randomHexes, boolean randomPorts, boolean randomHexValues)
	{

	}

	/**
	 * JoinGame: A particular player joins a particular game.
	 * @param gameID: ID of the game to join.
	 * @param playerIndex: ID of the player who is joining.
     */
	public void joinGame(int gameID, Player player)
	{
		serverModel.listGames().get(gameID).addPlayer(player);
	}

	/**
	 * Gets the game model.
	 */
	public void getGameModel()
	{

	}

	/**
	 * Adds a computer player to a particular game.
	 */
	public void addAI(int gameID)
	{

	}

	/**
	 * Shows all the AI/computer player options.
	 */
	public void listAI()
	{

	}

	/**
	 * Sends a chat to the server and stores it there.
	 * @param message: the chat message we are sending
     */
	public void sendChat(String message)
	{

	}

	/**
	 * Rolls a number which will influence a large part of the game.
	 * This number is randomized and is either computed here or on the model side.
	 * @param number: randomized number between 2 and 12.
     */
	public void rollNumber(int number)
	{

	}

	/**
	 * Robs another player
	 * @param location: Where the robber is at
	 * @param playerIndex: Player who is doing the robber
     */
	public void robPlayer(HexLocation location, int playerIndex)
	{

	}

	/**
	 * Finishes the turn and changes who the current player's index is.
	 * @param playerIndex: the player who is finishing the turn
	 * @return: the index of the next player. who will become the current player.
     */
	public int finishTurn(int playerIndex)
	{
		return -1;
	}

	/**
	 * Buys a dev card
	 * @param playerIndex: player who is buying
     */
	public void buyDevCard(int playerIndex)
	{

	}

	/**
	 * Plays a year of plenty card
	 * @param playerIndex: player who is playing card
     */
	public void playYearOfPlenty(int playerIndex)
	{

	}

	/**
	 * Plays a road building card
	 * @param playerIndex: player who is playing card
     */
	public void playRoadBuilding(int playerIndex)
	{

	}

	/**
	 * Plays a soldier card
	 * @param playerIndex: player who is playing card
     */
	public void playSoldier(int playerIndex)
	{

	}

	/**
	 * Plays a monopoly card
	 * @param playerIndex: player who is playing card
     */
	public void playMonopoly(int playerIndex)
	{

	}

	/**
	 * Plays a monument card
	 * @param playerIndex: player who is playing card
     */
	public void playMonument(int playerIndex)
	{

	}

	/**
	 * Builds a road on the map.
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built
	 * @param edge: the edge it is being built on
     */
	public void buildRoad(int playerIndex, HexLocation location, EdgeLocation edge)
	{

	}

	/**
	 * Builds a settlement on the map.
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built (which hex)
	 * @param vertex: which vertex it is being built on
     */
	public void buildSettlement(int playerIndex, HexLocation location, VertexLocation vertex)
	{

	}

	/**
	 * builds a city on the map
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built
	 * @param vertex: needs to already have a settlement on it + required resources for player
     */
	public void buildCity(int playerIndex, HexLocation location, VertexLocation vertex)
	{

	}

	/**
	 * Function to offer trade to another player
	 * @param getResource: what you want
	 * @param giveResource: what you give
	 * @param playerIndex: player offering
	 * @param playerOffered: player receiving
     */
	public void offerTrade(String getResource, String giveResource, int playerIndex, int playerOffered)
	{

	}

	/**
	 * Accepts a trade that was offered to you.
	 * @param getResource: resource player is getting
	 * @param giveResource: resource
	 * @param playerIndex: player who is playing card
     */
	public void acceptTrade(String getResource, String giveResource, int playerIndex)
	{

	}

	/**
	 * Function to make the trade with bank
	 * @param getResource: resource getting
	 * @param giveResource: resource receiving
	 * @param playerIndex: player who is playing card
     * @param ratio: ratio at which we are making the trade
     */
	public void maritimeTrade(String getResource, String giveResource, int playerIndex, int ratio)
	{

	}

	/**
	 * Allows us to discard cards
	 * @param playerIndex: player who is playing card
	 * @param cardsToDiscard: which cards player wants to get rid of
	 *                      will probably change the data storage
     */
	public void discardCards(int playerIndex, Collection<Card> cardsToDiscard)
	{

	}

	/**
	 * Getters and setters:
	 */

	/*public ArrayList<CatanGame> getGamesInServer()
	{
		return gamesInServer;
	}

	public void setGamesInServer(ArrayList<CatanGame> gamesInServer)
	{
		this.gamesInServer = gamesInServer;
	}*/

	public ArrayList<Player> getAllRegisteredUsers()
	{
		return allRegisteredUsers;
	}

	public void setAllRegisteredUsers(ArrayList<Player> allRegisteredUsers)
	{
		this.allRegisteredUsers = allRegisteredUsers;
	}
}
