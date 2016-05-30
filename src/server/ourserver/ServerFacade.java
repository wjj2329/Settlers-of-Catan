package server.ourserver;

import client.main.Catan;
import client.model.MessageLine;
import client.model.Model;
import client.model.TradeOffer;
import client.model.TurnStatus;
import client.model.TurnTracker;
import client.points.IGameFinishedView;
import server.ourserver.commands.*;
import shared.chat.GameHistoryLine;
import shared.definitions.CatanColor;
import shared.game.Card;
import shared.game.CatanGame;
import shared.game.DevCardList;
import shared.game.ResourceList;
import shared.game.map.CatanMap;
import shared.game.map.Index;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.RoadPiece;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.glassfish.external.statistics.annotations.Reset;
import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;

import javax.activation.CommandObject;

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
	public static int NEXT_USER_ID = 5;
	public static int NEXT_GAME_ID=1;

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
		Player sam = new Player("Sam", CatanColor.ORANGE, new Index(0));
		sam.setPassword("sam");
		Player mark = new Player("Brooke", CatanColor.BLUE, new Index(1));
		mark.setPassword("brooke");
		Player brooke = new Player("Pete", CatanColor.RED, new Index(2));
		brooke.setPassword("pete");
		Player pete = new Player("Mark", CatanColor.GREEN, new Index(3));
		pete.setPassword("mark");
		allRegisteredUsers.add(sam);
		allRegisteredUsers.add(mark);
		allRegisteredUsers.add(brooke);
		allRegisteredUsers.add(pete);
		
		loadDefaultGame();
		loadEmptyGame();
	}
	
	public void loadDefaultGame(){
		CatanGame defaultgame = new CatanGame();
		defaultgame.setTitle("Default Game");
		defaultgame.setID(0);
		defaultgame.addPlayer(allRegisteredUsers.get(0));
		defaultgame.addPlayer(allRegisteredUsers.get(1));
		defaultgame.addPlayer(allRegisteredUsers.get(2));
		defaultgame.addPlayer(allRegisteredUsers.get(3));
		
		defaultgame.mybank.setResourceCardslist(19,19,19,19,19); //it has 95 resource cards right? 
		defaultgame.getMyplayers().get(new Index(0)).setPlayerIndex(new Index(0));
		defaultgame.getMyplayers().get(new Index(1)).setPlayerIndex(new Index(1));
		defaultgame.getMyplayers().get(new Index(2)).setPlayerIndex(new Index(2));
		defaultgame.getMyplayers().get(new Index(3)).setPlayerIndex(new Index(3));
		
		defaultgame.getMyplayers().get(new Index(0)).setResources(new ResourceList(0,0,1,1,1));
		defaultgame.getMyplayers().get(new Index(1)).setResources(new ResourceList(1,1,1,0,0));
		defaultgame.getMyplayers().get(new Index(2)).setResources(new ResourceList(0,0,1,1,1));
		defaultgame.getMyplayers().get(new Index(3)).setResources(new ResourceList(0,1,1,0,1));
		
		//Players have some settlements and roads right?
		//trying to add settlements
		ArrayList<Settlement> settlements = new ArrayList<>();

		try {
			defaultgame.getMymap().getHexes().get(new HexLocation(0, 1)).buildSettlement(new VertexLocation(new HexLocation(0, 1), VertexDirection.SouthEast), new Index(0));
			defaultgame.getMymap().getHexes().get(new HexLocation(2, 0)).buildSettlement(new VertexLocation(new HexLocation(2, 0), VertexDirection.SouthWest), new Index(0));
			defaultgame.getMymap().getHexes().get(new HexLocation(-1, -1)).buildSettlement(new VertexLocation(new HexLocation(-1, -1), VertexDirection.SouthWest), new Index(1));
			defaultgame.getMymap().getHexes().get(new HexLocation(-2, 1)).buildSettlement(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthWest), new Index(1));
			defaultgame.getMymap().getHexes().get(new HexLocation(0, 0)).buildSettlement(new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest), new Index(2));
			defaultgame.getMymap().getHexes().get(new HexLocation(1, -1)).buildSettlement(new VertexLocation(new HexLocation(1, -1), VertexDirection.SouthWest), new Index(2));
			defaultgame.getMymap().getHexes().get(new HexLocation(1, -2)).buildSettlement(new VertexLocation(new HexLocation(1, -2), VertexDirection.SouthEast), new Index(3));
			defaultgame.getMymap().getHexes().get(new HexLocation(-1, 1)).buildSettlement(new VertexLocation(new HexLocation(-1, 1), VertexDirection.SouthWest), new Index(3));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		defaultgame.getMyplayers().get(new Index(0)).setNumSettlementsRemaining(3);
		defaultgame.getMyplayers().get(new Index(1)).setNumSettlementsRemaining(3);
		defaultgame.getMyplayers().get(new Index(2)).setNumSettlementsRemaining(3);
		defaultgame.getMyplayers().get(new Index(3)).setNumSettlementsRemaining(3);
				
		defaultgame.getMymap().getHexes().get(new HexLocation(0, 1)).buildRoad(new EdgeLocation(new HexLocation(0, 1), EdgeDirection.South), new Index(0));
		defaultgame.getMymap().getHexes().get(new HexLocation(2, 0)).buildRoad(new EdgeLocation(new HexLocation(2, 0), EdgeDirection.SouthWest), new Index(0));
		defaultgame.getMymap().getHexes().get(new HexLocation(-2, 1)).buildRoad(new EdgeLocation(new HexLocation(-2, 1), EdgeDirection.SouthWest), new Index(1));
		defaultgame.getMymap().getHexes().get(new HexLocation(-1, -1)).buildRoad(new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.South), new Index(1));
		defaultgame.getMymap().getHexes().get(new HexLocation(0, 0)).buildRoad(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.South), new Index(2));
		defaultgame.getMymap().getHexes().get(new HexLocation(1, -1)).buildRoad(new EdgeLocation(new HexLocation(1, -1), EdgeDirection.South), new Index(2));
		defaultgame.getMymap().getHexes().get(new HexLocation(2, -2)).buildRoad(new EdgeLocation(new HexLocation(2, -2), EdgeDirection.SouthWest), new Index(3));
		defaultgame.getMymap().getHexes().get(new HexLocation(-1, 1)).buildRoad(new EdgeLocation(new HexLocation(-1, 1), EdgeDirection.SouthWest), new Index(3));

		defaultgame.getMyplayers().get(new Index(0)).setNumRoadPiecesRemaining(13);
		defaultgame.getMyplayers().get(new Index(1)).setNumRoadPiecesRemaining(13);
		defaultgame.getMyplayers().get(new Index(2)).setNumRoadPiecesRemaining(13);
		defaultgame.getMyplayers().get(new Index(3)).setNumRoadPiecesRemaining(13);
		
		defaultgame.getMyplayers().get(new Index(0)).setNumVictoryPoints(2);
		defaultgame.getMyplayers().get(new Index(1)).setNumVictoryPoints(2);
		defaultgame.getMyplayers().get(new Index(2)).setNumVictoryPoints(2);
		defaultgame.getMyplayers().get(new Index(3)).setNumVictoryPoints(2);
		
		defaultgame.myrobber.setLocation(new HexLocation(0, -2));
		
		defaultgame.getModel().getTurntracker().setStatus(TurnStatus.ROLLING);
		defaultgame.getModel().getTurntracker().setLongestRoad(new Index(-1));
		defaultgame.getModel().getTurntracker().setLargestArmy(new Index(-1));
		defaultgame.getModel().getTurntracker().setCurrentTurn(new Index(0), defaultgame.getMyplayers());
		serverModel.addGame(defaultgame);
	}
	
	public void loadEmptyGame(){
		CatanGame emptygame = new CatanGame();
		emptygame.setTitle("Empty Game");
		emptygame.setID(1);
		emptygame.addPlayer(allRegisteredUsers.get(0));
		emptygame.addPlayer(allRegisteredUsers.get(1));
		emptygame.addPlayer(allRegisteredUsers.get(2));
		emptygame.addPlayer(allRegisteredUsers.get(3));
		
		emptygame.mybank.setResourceCardslist(19,19,19,19,19); //it has 95 resource cards right? 
		emptygame.getMyplayers().get(new Index(0)).setPlayerIndex(new Index(0));
		emptygame.getMyplayers().get(new Index(1)).setPlayerIndex(new Index(1));
		emptygame.getMyplayers().get(new Index(2)).setPlayerIndex(new Index(2));
		emptygame.getMyplayers().get(new Index(3)).setPlayerIndex(new Index(3));
		
		emptygame.myrobber.setLocation(new HexLocation(0, -2));
		
		emptygame.getModel().getTurntracker().setStatus(TurnStatus.FIRSTROUND);
		emptygame.getModel().getTurntracker().setLongestRoad(new Index(-1));
		emptygame.getModel().getTurntracker().setLargestArmy(new Index(-1));
		emptygame.getModel().getTurntracker().setCurrentTurn(new Index(0), emptygame.getMyplayers());
		serverModel.addGame(emptygame);
	}
	
	public CatanGame getGameByID(int id){
		for(CatanGame game : serverModel.listGames()){
			if(game.getGameId() == id){
				return game;
			}
		}
		return null;
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
		Player p=new Player(username,CatanColor.PUCE,new Index(-10));
		p.setPlayerID(new Index(NEXT_USER_ID++));
		p.setPassword(password);
		System.out.println("I add a new player");
		allRegisteredUsers.add(p);
	}

	/**
	 * Gets the list of all the games in the server.
	 * GET request so I made it return something...
	 */
	public JSONArray getGameList()
	{

		JSONArray games = new JSONArray();
		try {
			for(CatanGame juego : serverModel.listGames())
			{
				JSONObject game = new JSONObject();
				game.put("title", juego.getTitle());
				game.put("id", juego.getGameId());
				JSONArray players = new JSONArray();
				for(Player jugador : juego.getMyplayers().values())
				{
					JSONObject player = new JSONObject();
					if(jugador != null)
					{
						player.put("color", jugador.getColor().name().toLowerCase());
						player.put("name", jugador.getName());
						player.put("id", jugador.getPlayerID().getNumber());
					}
					players.put(player);
				}
				game.put("players", players);
				games.put(game);
			}
			return games;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(games);
		return games;

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
		System.out.println("I add a new Catan Game");
		CatanGame mynewgame=new CatanGame();
		mynewgame.setTitle(name);
		NEXT_GAME_ID++;
		mynewgame.setID(NEXT_GAME_ID);
		mynewgame.setMymap(new CatanMap(10));
		if(randomHexes)
		{
			mynewgame.getMymap().shuffleHexes();
		}
		if(randomPorts)
		{
			mynewgame.getMymap().shufflePorts();
		}
		if(randomHexValues)
		{
			mynewgame.getMymap().shuffleNumbers();
		}


		serverModel.addGame(mynewgame);
	}

	/**
	 * JoinGame: A particular player joins a particular game.
	 * @param gameID: ID of the game to join.
	 * @param : ID of the player who is joining.
     */
	public boolean joinGame(int gameID, int playerid, String color)
	{
		for (Player p : allRegisteredUsers)
		{
			if(p.getPlayerID().getNumber() == playerid)
			{
				serverModel.listGames().get(gameID).addPlayer(p);
				return true;
			}
		}
		return false;
		
	}

	/**
	 * Gets the game model.
	 */
	public JSONObject getGameModel(int gameID)
	{
		JSONObject model = new JSONObject();
		CatanGame game = getGameByID(gameID);
		System.out.println("THE GAME GETS LOADED");
		
		try {
			//THE BANK
			JSONObject bank = new JSONObject();
			bank.put("brick", game.mybank.getCardslist().getBrick());
			bank.put("ore", game.mybank.getCardslist().getOre());
			bank.put("sheep", game.mybank.getCardslist().getSheep());
			bank.put("wheat", game.mybank.getCardslist().getWheat());
			bank.put("wood", game.mybank.getCardslist().getWood());
			model.put("bank", bank);
			//System.out.println("THE MODEL SO FAR WIT BANK " + model.toString());
			
			//THE CHAT
			JSONObject chat = new JSONObject();
			JSONArray chatlines = new JSONArray();
			for(MessageLine mensaje : game.getMychat().getChatMessages().getMessages())
			{
				JSONObject chatline = new JSONObject();
				chatline.put("message", mensaje.getMessage());
				chatline.put("source", mensaje.getSource());
				chatlines.put(chatline);
			}
			chat.put("lines", chatlines);
			model.put("chat", chat); 
			//System.out.println("THE MODEL SO FAR WIT CHAT " + model.toString());
			
			//THE LOG 
			JSONObject log = new JSONObject();
			JSONArray loglines = new JSONArray();
			for(GameHistoryLine mensaje : game.getMyGameHistory().getLines())
			{
				JSONObject logline = new JSONObject();
				logline.put("message", mensaje.getLine());
				logline.put("source", mensaje.getSource());
				loglines.put(logline);
			}
			log.put("lines", loglines);
			model.put("log", log); 
			//System.out.println("THE MODEL SO FAR WIT LOG " + model.toString());
			
			//THE MAP
			//THE HEXES
			JSONObject map = new JSONObject();
			JSONArray hexes = new JSONArray();
			Map<HexLocation, Hex> mapa = game.getMymap().getHexes();
			for(HexLocation elHex : mapa.keySet())
			{
				JSONObject hex = new JSONObject();				
				JSONObject location = new JSONObject();
				location.put("x", elHex.getX());
				location.put("y", elHex.getY()); 
				hex.put("location", location);
				
				hex.put("resource", mapa.get(elHex).getResourcetype().name().toLowerCase());
				hex.put("number", mapa.get(elHex).getResourcenumber());
				hexes.put(hex);
			}
			map.put("hexes", hexes);
			//System.out.println("THE MAP SO FAR WIT HEXES " + map.toString());
			
			//THE PORTS
			JSONArray ports = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				if(mapa.get(elHex).getPortType() != null)
				{
					JSONObject port = new JSONObject();		
					port.put("resource", mapa.get(elHex).getPortType().name().toLowerCase());

					JSONObject location = new JSONObject();
					location.put("x", elHex.getX());
					location.put("y", elHex.getY()); 
					port.put("location", location);
					port.put("direction", getDirFromEdgeDir(mapa.get(elHex).getPort().getDirection()));
					port.put("ratio", mapa.get(elHex).getPort().getRatio());
					ports.put(port);
				}
			}
			map.put("ports", ports);
			//System.out.println("THE MAP SO FAR WIT PORTS " + map.toString());
			
			//THE ROADS 
			JSONArray roads = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				for(RoadPiece calle : mapa.get(elHex).getRoads())
				{
					if(calle.getPlayerWhoOwnsRoad() != null)
					{
						JSONObject road = new JSONObject();
						road.put("owner", calle.getPlayerWhoOwnsRoad().getNumber());
						
						JSONObject location = new JSONObject();
						location.put("x", elHex.getX());
						location.put("y", elHex.getY());

						location.put("direction", getDirFromEdgeDir(calle.getLocation().getDir()));
						road.put("location", location);
						
						roads.put(road);
					}
				}
			}
			map.put("roads", roads);
			//System.out.println("THE MAP SO FAR WIT ROADS " + map.toString());
			
			//THE SETTLEMENTS
			JSONArray settlements = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				for(Settlement colonia : mapa.get(elHex).getSettlementlist())
				{
					if(colonia.getOwner().getNumber() >= 0 && colonia.getOwner().getNumber() <= 4)
					{
						JSONObject settlement = new JSONObject();
						settlement.put("owner", colonia.getOwner().getNumber());
						
						JSONObject location = new JSONObject();
						location.put("x", elHex.getX());
						location.put("y", elHex.getY());						
						location.put("direction", getDirFromVertexDir(colonia.getVertexLocation().getDir()));
						settlement.put("location", location);
						
						settlements.put(settlement);
					}
				}
			}
			map.put("settlements", settlements);
			//System.out.println("THE MAP SO FAR WIT SETTLEMENTS " + map.toString());
			
			//THE CITIES
			JSONArray cities = new JSONArray();
			for(HexLocation elHex : mapa.keySet())
			{
				for(City cuidad : mapa.get(elHex).getCities())
				{
					if(cuidad.getOwner() != null)
					{
						JSONObject city = new JSONObject();
						city.put("owner", cuidad.getOwner().getNumber());
						
						JSONObject location = new JSONObject();
						location.put("x", elHex.getX());
						location.put("y", elHex.getY());
						location.put("direction", getDirFromVertexDir(cuidad.getVertexLocation().getDir()));
						city.put("location", location);
						
						cities.put(city);
					}
				}
			}
			map.put("cities", cities);
			map.put("radius", game.getMymap().getRadius());
			//System.out.println("THE MAP SO FAR WIT CITIES " + map.toString());
			
			//THE ROBBER
			JSONObject robber = new JSONObject();
			robber.put("x", game.myrobber.getLocation().getX());
			robber.put("y", game.myrobber.getLocation().getY());
			map.put("robber", robber);
			//System.out.println("THE MAP SO FAR WIT ROBBER " + map.toString());

			model.put("map", map);
			//System.out.println("THE MODEL SO FAR WIT MAP " + model.toString());
			
			//THE PLAYERS 
			Map<Index, Player> jugadores = game.getMyplayers();
			JSONArray players = new JSONArray();
			int numPlayahs = 0;
			for(Player jugador : jugadores.values())
			{
				JSONObject player = new JSONObject();
				player.put("cities", jugador.getNumCitiesRemaining());
				player.put("color", jugador.getColor().name().toLowerCase());
				player.put("discarded", jugador.getIsDiscarded());
				player.put("monuments", jugador.getNumMonuments());
				player.put("name", jugador.getName());
				//System.out.println("THE PLAYER SO FAR WIT INFO  " + player.toString());
				
				//THE NEW DEVCARDS
				JSONObject newDevCards = new JSONObject();
				DevCardList cartasNuevas = jugador.getNewDevCards();
				newDevCards.put("monopoly", cartasNuevas.getMonopoly());
				newDevCards.put("monument", cartasNuevas.getMonument());
				newDevCards.put("roadBuilding", cartasNuevas.getRoadBuilding());
				newDevCards.put("soldier", cartasNuevas.getSoldier());
				newDevCards.put("yearOfPlenty", cartasNuevas.getYearOfPlenty());
				player.put("newDevCards", newDevCards);
				//System.out.println("THE PLAYER SO FAR WIT NEW DEV CARDS " + player.toString());


				//THE OLD DEVCARDS
				JSONObject oldDevCards = new JSONObject();
				DevCardList cartasViejas = jugador.getOldDevCards();
				oldDevCards.put("monopoly", cartasViejas.getMonopoly());
				oldDevCards.put("monument", cartasViejas.getMonument());
				oldDevCards.put("roadBuilding", cartasViejas.getRoadBuilding());
				oldDevCards.put("soldier", cartasViejas.getSoldier());
				oldDevCards.put("yearOfPlenty", cartasViejas.getYearOfPlenty());
				player.put("oldDevCards", oldDevCards);
				//System.out.println("THE PLAYER SO FAR WIT OLD DEV CARDS " + player.toString());
				
				player.put("playerIndex", jugador.getPlayerIndex().getNumber());
				//System.out.println("THE PLAYER SO FAR WIT MORE INFO INDEX " + player.toString());
				player.put("playedDevCard", jugador.getplayedDevCard());
				//System.out.println("THE PLAYER SO FAR WIT MORE INFO PLAYED DEVCARD " + player.toString());
				player.put("playerID", jugador.getPlayerID().getNumber());
				//System.out.println("THE PLAYER SO FAR WIT MORE INFO ID " + player.toString());
				
				JSONObject resources = new JSONObject();
				ResourceList recursos = jugador.getResources();
				
				resources.put("brick", recursos.getBrick());
				resources.put("ore", recursos.getOre());
				resources.put("sheep", recursos.getSheep());
				resources.put("wheat", recursos.getWheat());
				resources.put("wood", recursos.getWood());
				player.put("resources", resources);
				//System.out.println("THE PLAYER SO FAR WIT RESOURCES " + player.toString());
				
				player.put("roads", jugador.getNumRoadPiecesRemaining());
				player.put("settlements", jugador.getNumSettlementsRemaining());
				player.put("soldiers", jugador.getNumSoldierCards());
				player.put("victoryPoints", jugador.getNumVictoryPoints());

				//System.out.println("THE PLAYER SO FAR WIT MORE RESOURCES " + player.toString());
				players.put(player);
				//System.out.println("THE PLAYERS SO FAR WIT ANOTHER PLAYAH  " + players.toString());
				numPlayahs++;
			}
			while(numPlayahs < 4)
			{
				JSONObject player = new JSONObject();
				players.put(player);
				System.out.println("THE PLAYERS SO FAR WIT NULL PLAYAH  " + players.toString());
				numPlayahs++;
			}
			model.put("players", players);
			//System.out.println("THE MODEL SO FAR WIT PLAYAHS " + model.toString());
			
			//THE TRADEOFFER
			if(game.getMytradeoffer() != null)
			{
				JSONObject tradeOffer = new JSONObject();
				TradeOffer negocio = game.getMytradeoffer();
				tradeOffer.put("sender", negocio.getSender());
				tradeOffer.put("receiver", negocio.getReceiver());
				
				JSONObject offer = new JSONObject();
				ResourceList ofrecimiento = negocio.getMylist();
				offer.put("brick", ofrecimiento.getBrick());
				offer.put("ore", ofrecimiento.getOre());
				offer.put("sheep", ofrecimiento.getSheep());
				offer.put("wheat", ofrecimiento.getWheat());
				offer.put("wood", ofrecimiento.getWood());
				tradeOffer.put("offer", offer);
				model.put("tradeOffer", tradeOffer);
			}
			//System.out.println("THE MODEL SO FAR WIT TRADEOFFER MAYBE " + model.toString());
			
			//THE TURN TRACKER
			JSONObject turnTracker = new JSONObject();
			TurnTracker turnos = game.getModel().getTurntracker();
			turnTracker.put("currentTurn", turnos.getCurrentTurn().getNumber());
			turnTracker.put("status", turnos.getStatus());
			turnTracker.put("longestRoad", turnos.getLongestRoad().getNumber());
			turnTracker.put("largestArmy", turnos.getLargestArmy().getNumber());
			model.put("turnTracker", turnTracker);
			//System.out.println("THE MODEL SO FAR WIT TURNTRACKER " + model.toString());
			
			model.put("version", game.getModel().getVersion());
			model.put("winner", game.getWinner().getNumber());

			System.out.println("THE MODEL SO FAR WIT EVERYTHANG " + model.toString());
			//System.out.println("GAME TITLE" + game.getTitle());
			return model;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}

	public String getDirFromEdgeDir(EdgeDirection direction){
		switch (direction)
		{
			case NorthWest:
				return "NW";
			case North:
				return "N";
			case NorthEast:
				return "NE";
			case SouthWest:
				return "SW";
			case South:
				return "S";
			case SouthEast:
				return "SE";
			default:
				break;
		}
		return null;
	}
	
	public String getDirFromVertexDir(VertexDirection direction){
		switch (direction)
		{
			case East:
				return "E";
			case NorthWest:
				return "NW";
			case NorthEast:
				return "NE";
			case SouthWest:
				return "SW";
			case West:
				return "W";
			case SouthEast:
				return "SE";
			default:
				break;
		}
		return null;
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
	private SendChatCommand mychat=new SendChatCommand();
	public void sendChat(String message, int playerindex)
	{
		mychat.sendChat(message,playerindex);
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
	private BuildRoadCommand buildRoadCommand=new BuildRoadCommand();
	public void buildRoad(int playerIndex, HexLocation location, EdgeLocation edge, boolean free)
	{
		buildRoadCommand.buildRoadincommand(playerIndex,location,edge,free);
	}

	/**
	 * Builds a settlement on the map.
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built (which hex)
	 * @param vertex: which vertex it is being built on
     */
	private BuildSettlementCommand buildsettlement=new BuildSettlementCommand();
	public void buildSettlement(int playerIndex, HexLocation location, VertexLocation vertex, boolean free)
	{
		buildsettlement.buildsettlement(playerIndex,location,vertex,free);
	}

	/**
	 * builds a city on the map
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built
	 * @param vertex: needs to already have a settlement on it + required resources for player
     */
	private BuildCityCommand buildCityCommand=new BuildCityCommand();
	public void buildCity(int playerIndex, HexLocation location, VertexLocation vertex)
	{
		buildCityCommand.buildCityCommand(playerIndex,location,vertex);
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
	private DiscardCardsCommand mydiscard=new DiscardCardsCommand();
	public void discardCards(int playerIndex, ResourceList cardsToDiscard)
	{
		mydiscard.discardCards(playerIndex,cardsToDiscard);
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
