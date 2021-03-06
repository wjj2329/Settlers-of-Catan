package server.ourserver;

import client.model.MessageLine;
import client.model.Model;
import client.model.TradeOffer;
import client.model.TurnStatus;
import client.model.TurnTracker;
import server.database.Database;
import server.database.DatabaseException;
import server.ourserver.commands.*;
import server.persistence.PersistenceManager;
import server.persistence.TextDBGameManagerDAO;
import shared.chat.GameHistoryLine;
import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.DevCardList;
import shared.game.ResourceList;
import shared.game.map.CatanMap;
import shared.game.map.Index;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.RoadPiece;
import shared.game.map.Robber;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.io.*;
import java.rmi.ServerException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

	public Model getServerModel()
	{
		return serverModel;
	}

	//public static boolean alreadygone=false;

	/**
	 * The singleton instance of ServerFacade.
	 */
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
	//plugin stored somewhere with the configuration file.

	/**
	 * Constructor is private in order to avoid multiple instantiations.
	 * We have hard-coded the four default players for testing purposes.
	 * Do NOT delete the throws statement.
	 */
	private ServerFacade() throws FileNotFoundException, JSONException
	{
		Database db = new Database();
		/*
		try
		{
			db.initialize();
		} catch (DatabaseException e1)
		{
			e1.printStackTrace();
		}
		*/
		Player sam = new Player("Sam", CatanColor.ORANGE, new Index(0));
		sam.setPlayerIndex(new Index(10));
		sam.setPassword("sam");
		Player mark = new Player("Brooke", CatanColor.BLUE, new Index(1));
		mark.setPlayerIndex(new Index(20));
		mark.setPassword("brooke");
		Player brooke = new Player("Pete", CatanColor.RED, new Index(2));
		mark.setPlayerIndex(new Index(30));
		brooke.setPassword("pete");
		Player pete = new Player("Mark", CatanColor.GREEN, new Index(3));
		pete.setPlayerIndex(new Index(40));
		pete.setPassword("mark");
		allRegisteredUsers.add(sam);
		allRegisteredUsers.add(mark);
		allRegisteredUsers.add(brooke);
		allRegisteredUsers.add(pete);

		
		loadDefaultGame();
		loadEmptyGame();
		if (Server.isTxtdb)
		{
			try {
				loadallplayersfromtextdatabase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			loadGamesFromFileIntoServerModel();
			//if(!alreadygone) {
				updateallgameswithtext();
			//}
		}
		else{
			loadGamesFromDatabase();
		}
	}
	public void loadGamesFromDatabase(){
		//System.out.println("GOING TO CALL THE DAO!");
		try {
			ArrayList<CatanGame> catanGames = null;
			//System.out.println("BEFORE I CALL THE DB");
			catanGames = (ArrayList<CatanGame>) PersistenceManager.getSingleton().getMyfactory().getGameManager().getGameList();
			//System.out.println("AFTER I CALL THE DB STARTING TO ADD WONDERFUL GAMES");
			if(catanGames != null)
			{
				for(CatanGame game : catanGames){
					//System.out.println("ADDING A GAME WITH ID " + game.getGameId());
					serverModel.addGame(game);
					ArrayList<ICommand> commands = null;
					commands = PersistenceManager.getSingleton().getMyfactory().getGameManager().getCommands(game.getGameId());
					for(ICommand command : commands){
						command.executeversion2(game);
					}
				
				}
				
			}
			
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
	
	private void updateallgameswithtext() throws FileNotFoundException, JSONException {
		System.out.println("I have my games "+serverModel.listGames().size());
		for(int i=0; i<serverModel.listGames().size(); i++)
		{
			ArrayList<ICommand>commandsloadedfromdb=new ArrayList<>();
			System.out.println("I check this game with file "+"game"+serverModel.listGames().get(i).getGameId()+".txt");
			File myfile=new File("game"+serverModel.listGames().get(i).getGameId()+".txt");
			if(!myfile.exists())
			{
				continue;
			}
			FileReader myreader=new FileReader(myfile);
			Scanner scan=new Scanner(myreader);
			StringBuilder json=new StringBuilder();
			while(scan.hasNext())
			{
				json.append(scan.next());
			}
			if(json.toString().equals(""))
			{
				continue;
			}
			serverModel.listGames().get(i).updateFromJSON(new JSONObject(json.toString()));
			for(int x=1; x<Server.numberofcommands; x++)
			{
				File commandfile=new File("commands"+i+".txt");
				if(!commandfile.exists())
				{
					continue;
				}
				FileReader reading=new FileReader(commandfile);
				Scanner scanner=new Scanner(reading);
				StringBuilder jsonstuff=new StringBuilder();
				while(scanner.hasNext())
				{
					jsonstuff.append(scanner.next());
				}
				if(jsonstuff.toString().equals(""))
				{
					continue;
				}
				jsonstuff.replace(0,1,"{");
				jsonstuff.append(",}"); // tried: adding the comma AND IT WORKED
				//System.out.println("Let's check the JSON. " + jsonstuff.toString());
				JSONObject mycommandinjson=new JSONObject(jsonstuff.toString());
				if(!mycommandinjson.has(Integer.toString(x)))
				{
					continue;
				}
				JSONObject jsonObject=mycommandinjson.getJSONObject(Integer.toString(x));
				String type= jsonObject.getString("type");
				switch(type)
				{
					case "SendChatCommand":
					{
						String message=jsonObject.getString("message");
						int playerindex=jsonObject.getInt("playerindex");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new SendChatCommand(message,playerindex,gameid));
						break;
					}
					case "RollNumberCommand":
					{
						int rollNumber=jsonObject.getInt("rollNumber");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new RollNumberCommand(rollNumber,gameid));
						break;
					}
					case "RobPlayerCommand":
					{
						HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
						int playerRobbing=jsonObject.getInt("playerRobbing");
						int playerbeingrobbed=jsonObject.getInt("playerbeingrobbed");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new RobPlayerCommand(location,playerRobbing,playerbeingrobbed,gameid));
						break;
					}
					case "PlayYearOfPlentyCommand":
					{
						int gameID=jsonObject.getInt("gameid");
						String resource2=jsonObject.getString("resource2");
						String resource1=jsonObject.getString("resource1");
						int playerindex=jsonObject.getInt("playerindex");
						commandsloadedfromdb.add(new PlayYearOfPlentyCommand(gameID,resource1,resource2,playerindex));
						break;
					}
					case "PlaySoldierCommand":
					{
						HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
						int playerRobbing=jsonObject.getInt("playerRobbing");
						int playerBeingRobbed=jsonObject.getInt("playerBeingRobbed");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new PlaySoldierCommand(location,playerRobbing,playerBeingRobbed,gameid));
						break;
					}
					case "PlayRoadBuildingCommand":
					{
						int playerIndex=jsonObject.getInt("playerIndex");
						HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
						System.out.println("I MAKE A BUILD A ROAD "+jsonObject.get("edge"));
						EdgeLocation edge=new EdgeLocation(location,getDirectionfromStringspecial(jsonObject.getString("edge")));
						boolean free=jsonObject.getBoolean("free");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new PlayRoadBuildingCommand(playerIndex,location,edge,free,gameid));
						break;
					}
					case"PlayMonumentCommand":
					{
						int playerindex=jsonObject.getInt("playerindex");
						int gameID=jsonObject.getInt("gameID");
						commandsloadedfromdb.add(new PlayMonumentCommand(playerindex,gameID));
						break;
					}
					case "PlayMonopolyCommand":
					{
						int playerindex=jsonObject.getInt("playerindex");
						String resource=jsonObject.getString("resource");
						int gameID=jsonObject.getInt("gameID");
						commandsloadedfromdb.add(new PlayMonopolyCommand(playerindex,resource,gameID));
						break;
					}
					case "OfferTradeCommand":
					{
						int gameid=jsonObject.getInt("gameid");
						int playerIndex=jsonObject.getInt("playerIndex");
						ResourceList offer=new ResourceList(jsonObject.getInt("brick"),jsonObject.getInt("ore"),jsonObject.getInt("sheep"),jsonObject.getInt("wheat"), jsonObject.getInt("wood"));
						int receiver=jsonObject.getInt("receiver");
						commandsloadedfromdb.add(new OfferTradeCommand(gameid,playerIndex,offer,receiver));
						break;
					}
					case "MaritimeTradeCommand":
					{
						String getResource=jsonObject.getString("getResource");
						String giveResource=jsonObject.getString("giveResource");
						int playerIndex_NOT_ID=jsonObject.getInt("playerIndex_NOT_ID");
						int ratio=jsonObject.getInt("ratio");
						int gameID=jsonObject.getInt("gameID");
						commandsloadedfromdb.add(new MaritimeTradeCommand(getResource,giveResource,playerIndex_NOT_ID,ratio,gameID));
						break;
					}
					case "FinishTurnCommand":
					{
						int playerIndex=jsonObject.getInt("playerIndex");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new FinishTurnCommand(playerIndex,gameid));
						break;
					}
					case "DiscardCardsCommand":
					{
						int playerIndex=jsonObject.getInt("playerIndex");
						ResourceList cardsToDiscard=new ResourceList(jsonObject.getInt("brick"),jsonObject.getInt("ore"),jsonObject.getInt("sheep"),jsonObject.getInt("wheat"), jsonObject.getInt("wood"));
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new DiscardCardsCommand(playerIndex,cardsToDiscard,gameid));
						break;
					}
					case "BuyDevCardCommand":
					{
						int playerIndex=jsonObject.getInt("playerIndex");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new BuyDevCardCommand(playerIndex,gameid));
						break;
					}
					case"BuildSettlementCommand":
					{
						int playerIndex=jsonObject.getInt("playerIndex");
						HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
						VertexLocation vertex=new VertexLocation(location,convertToVertexDirectionspecial(jsonObject.getString("dir")));
						boolean free=jsonObject.getBoolean("free");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new BuildSettlementCommand(playerIndex,location,vertex,free,gameid));
						break;
					}
					case "BuildRoadCommand":
					{
						int playerIndex=jsonObject.getInt("playerIndex");
						HexLocation location=new HexLocation(jsonObject.getInt("x"), jsonObject.getInt("y"));
						EdgeLocation edge=new EdgeLocation(location, getDirectionfromStringspecial(jsonObject.getString("edge")));
						boolean free=jsonObject.getBoolean("free");
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new BuildRoadCommand(playerIndex,location,edge,free,gameid));
						break;
					}
					case"BuildCityCommand":
					{
						int playerIndex=jsonObject.getInt("playerIndex");
						HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
						VertexLocation vertex=new VertexLocation(location,convertToVertexDirectionspecial(jsonObject.getString("vertex")));
						int gameid=jsonObject.getInt("gameid");
						commandsloadedfromdb.add(new BuildCityCommand(playerIndex,location,vertex,gameid));
						break;
					}
					case"AcceptTradeCommand":
					{
						int gameid=jsonObject.getInt("gameid");
						int playerindex=jsonObject.getInt("playerIndex");
						boolean willaccept=jsonObject.getBoolean("willAccept");
						commandsloadedfromdb.add(new AcceptTradeCommand(gameid,playerindex,willaccept));
						break;
					}
				}

			}
			System.out.println("the number of commands in my array is "+commandsloadedfromdb.size());

			//if(commandsloadedfromdb.size()!=0) {
			TextDBGameManagerDAO.commandNumber=commandsloadedfromdb.size();
				for (int j = 0; j < commandsloadedfromdb.size(); j++)
				{
					System.out.println("I try to execute ");
					System.out.println(commandsloadedfromdb.get(j).toString());
					commandsloadedfromdb.get(j).executeversion2(getGameByID(commandsloadedfromdb.get(j).getGameid()));
				}
			//alreadygone=true;

			//}


		}

	}
	private void loadallplayersfromtextdatabase() throws IOException, JSONException {
		//System.out.println("I construct the TextDBUserAccountsDAO");
		// file reader, scanner, stringBuilder

		File players = new File("allPlayers.txt");
		FileWriter playerFileWriter = new FileWriter(players, true);
		FileReader iReadFiles = new FileReader(players);
		Scanner iScanThings = new Scanner(iReadFiles);
		StringBuilder iBuildStrings = new StringBuilder();
		while (iScanThings.hasNext())
		{
			iBuildStrings.append(iScanThings.next());
		}
		iReadFiles.close();
		iScanThings.close();

		String theString = iBuildStrings.toString();
		//System.out.println("What is the string? " + theString);
		if (!theString.contains("{"))
		{
			//System.out.println("I am writing the first bracket");
			playerFileWriter.write("{"); // why isn't this writing??
			return;
		}
		//System.out.println("I made it here");
		if (theString.length() > 1 && theString.charAt(theString.length() - 1) != '}')
		{
			//System.out.println("We need this");
			playerFileWriter.write("}");
			iBuildStrings.append("}");
		}
		//System.out.println("What does the string for JSON look like? " + iBuildStrings.toString());
		JSONObject jason = new JSONObject(iBuildStrings.toString());

		// this is gonna break something
		for (int i = 0; i < 300; i++)
		{
			String playerObj = "player" + i;
			//System.out.println("the number: " + playerObj);
			if (jason.has(playerObj))
			{
				System.out.println("JSON does have " + playerObj);
				JSONObject playerAttributes = jason.getJSONObject(playerObj);
				Player myPlayer=new Player(playerAttributes.getString("username"),CatanColor.PUCE,new Index(10));
				myPlayer.setPlayerID(new Index(playerAttributes.getInt("playerID")));
				myPlayer.setPassword(playerAttributes.getString("password"));
				System.out.println("I Add a player "+myPlayer.getName());
				allRegisteredUsers.add(myPlayer);
			}
		}


	}

	/**
	 * Do NOT delete this function. This is extremely necessary for my part, and it
	 * 	screws it up if you delete it. Please also do not delete any throws statements.
	 * 	Thanks!
	 *
	 *  This function loads the games into the server model.
	 *
	 * @throws FileNotFoundException: if allGames.txt isn't the correct file
	 * @throws JSONException: if the JSON was formatted incorrectly
     */
	private void loadGamesFromFileIntoServerModel() throws FileNotFoundException, JSONException
	{
		File gameFile = new File("allGames.txt");
		if (!gameFile.isFile())
		{
			return;
		}
		FileReader gameFileReader = new FileReader(gameFile);
		Scanner scanny = new Scanner(gameFileReader);
		scanny.useDelimiter(System.getProperty("line.separator"));
		scanny.useDelimiter("\r\n");
		StringBuilder fileToStringToJson = new StringBuilder();
		while (scanny.hasNext())
		{
			fileToStringToJson.append(scanny.next());
		}
		String res = fileToStringToJson.toString();
		if(res.equals(""))
		{
			return;
		}
		JSONObject jason = new JSONObject(res);

		for (int j = 0; j < 150; j++)
		{
			String possible = "game" + j;
			if (jason.has(possible))
			{
				JSONObject gameObj = jason.getJSONObject(possible);
				// i am not yet setting the randomHexes, etc. attributes - do I need to, or is it already done?
				CatanGame juegoNuevo = new CatanGame();
				juegoNuevo.setID(gameObj.getInt("id"));
				//System.out.println("The title, indeed, comes forth as: " + gameObj.getString("title"));
				juegoNuevo.setTitle(gameObj.getString("title"));
				serverModel.addGame(juegoNuevo);
			}
		}
	}

	public void loadDefaultGame(){
		CatanGame defaultgame = new CatanGame();
		defaultgame.setTitle("Default Game");
		defaultgame.setID(0);
		Player p1 = new Player(allRegisteredUsers.get(0).getName(), allRegisteredUsers.get(0).getColor(), allRegisteredUsers.get(0).getPlayerID());
		Player p2 = new Player(allRegisteredUsers.get(1).getName(), allRegisteredUsers.get(1).getColor(), allRegisteredUsers.get(1).getPlayerID());
		Player p3 = new Player(allRegisteredUsers.get(2).getName(), allRegisteredUsers.get(2).getColor(), allRegisteredUsers.get(2).getPlayerID());
		Player p4 = new Player(allRegisteredUsers.get(3).getName(), allRegisteredUsers.get(3).getColor(), allRegisteredUsers.get(3).getPlayerID());
		defaultgame.addPlayer(p1);
		defaultgame.addPlayer(p2);
		defaultgame.addPlayer(p3);
		defaultgame.addPlayer(p4);
		
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
		emptygame.addPlayer(new Player(allRegisteredUsers.get(0).getName(), allRegisteredUsers.get(0).getColor(), allRegisteredUsers.get(0).getPlayerID()));
		emptygame.addPlayer(new Player(allRegisteredUsers.get(1).getName(), allRegisteredUsers.get(1).getColor(), allRegisteredUsers.get(1).getPlayerID()));
		emptygame.addPlayer(new Player(allRegisteredUsers.get(2).getName(), allRegisteredUsers.get(2).getColor(), allRegisteredUsers.get(2).getPlayerID()));
		emptygame.addPlayer(new Player(allRegisteredUsers.get(3).getName(), allRegisteredUsers.get(3).getColor(), allRegisteredUsers.get(3).getPlayerID()));

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
	 * Do NOT delete the exceptions.
     */
	public void updategameswithcommands()
	{

	}

	public static ServerFacade getInstance() throws FileNotFoundException, JSONException
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
	public Player logIn(String username, String password)
	{
		if(Server.isTxtdb) {
			for (Player p : allRegisteredUsers) {
				if (p.getName().equals(username) && p.getPassword().equals(password)) {
					return p;
				}
			}
		}
		else {
			Player validated = null;
			try {
				validated = PersistenceManager.getSingleton().getMyfactory().getUserAccount().validateUser(new Player(username, password));
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}

			return validated;
		}
		return null;
	}

	/**
	 * Registers a new user.
	 * @param username: name they will log in with
	 * @param password: password that they will use
     */
	public void register(String username, String password) throws IOException, DatabaseException, JSONException {
		Player p=new Player(username,CatanColor.PUCE,new Index(-10));
		adjustPlayerID(); //hmmmmmm
		for (Player p2 : allRegisteredUsers)
		{
			if (p2.getPlayerID().getNumber() == NEXT_USER_ID)
			{
				NEXT_USER_ID++;
			}
		}
		p.setPlayerID(new Index(NEXT_USER_ID));
		NEXT_USER_ID++;
		p.setPassword(password);
		//System.out.println("I add a new player");
		allRegisteredUsers.add(p);
		PersistenceManager.getSingleton().addPlayerInfo(p);
	}

	private void adjustGameID() throws JSONException, IOException
	{
		File theGames = new File("allGames.txt");
		if (theGames.isFile())
		{
			FileReader fr = new FileReader(theGames);
			Scanner scan = new Scanner(fr);
			StringBuilder fileToString = new StringBuilder();
			while (scan.hasNext())
			{
				fileToString.append(scan.next());
			}
			fr.close();
			scan.close();
			String testing = fileToString.toString();
			if (testing.length() < 1)
			{
				return;
			}
			JSONObject jason = new JSONObject(testing);
			ArrayList<Integer> allIntegersInFile = new ArrayList<>();
			for (int i = 0; i < 150; i++)
			{
				String playerObj = "game" + i;
				//System.out.println("the number: " + playerObj);
				if (jason.has(playerObj))
				{
					//System.out.println("JSON does have " + playerObj);
					JSONObject playerAttributes = jason.getJSONObject(playerObj);
					allIntegersInFile.add(playerAttributes.getInt("id"));
				}
			}
			Collections.sort(allIntegersInFile);
			int res = allIntegersInFile.get(allIntegersInFile.size() - 1);
			NEXT_GAME_ID = res + 1;
		}
	}

	private void adjustPlayerID() throws IOException, JSONException
	{
		File thePlayers = new File("allPlayers.txt");
		if (thePlayers.isFile())
		{
			FileReader fr = new FileReader(thePlayers);
			Scanner scan = new Scanner(fr);
			StringBuilder fileToString = new StringBuilder();
			while (scan.hasNext())
			{
				fileToString.append(scan.next());
			}
			fr.close();
			scan.close();
			String testing = fileToString.toString();
			if (testing.length() < 1)
			{
				return;
			}
			JSONObject jason = new JSONObject(testing);
			ArrayList<Integer> allIntegersInFile = new ArrayList<>();
			for (int i = 0; i < 150; i++)
			{
				String playerObj = "player" + i;
				//System.out.println("the number: " + playerObj);
				if (jason.has(playerObj))
				{
					//System.out.println("JSON does have " + playerObj);
					JSONObject playerAttributes = jason.getJSONObject(playerObj);
					allIntegersInFile.add(playerAttributes.getInt("playerID"));
				}
			}
			Collections.sort(allIntegersInFile);
			int res = allIntegersInFile.get(allIntegersInFile.size() - 1);
			NEXT_USER_ID = res + 1;
		}
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
		//System.out.println(games);
		return games;

	}

	/**
	 * Creates a new game and adds it to the server.
	 * @param name: game's name
	 * @param randomHexes: whether or not it uses randomized hexes
	 * @param randomPorts: whether or not it uses randomized ports
	 * @param randomHexValues: whether or not it uses randomized numbers on the hexes
     */
	public void createGame(String name, boolean randomHexes, boolean randomPorts, boolean randomHexValues) throws IOException, JSONException
	{
		CatanGame mynewgame=new CatanGame();
		mynewgame.setTitle(name);
		mynewgame.mybank.setResourceCardslist(19,19,19,19,19); //it has 95 resource cards right?
		mynewgame.getModel().getTurntracker().setLongestRoad(new Index(-1));
		mynewgame.getModel().getTurntracker().setLargestArmy(new Index(-1));
		mynewgame.myrobber=new Robber();
		NEXT_GAME_ID++;
		adjustGameID();
		mynewgame.setID(NEXT_GAME_ID);
		System.out.println("I add a new Catan Game with ID "+NEXT_GAME_ID);
		mynewgame.setMymap(new CatanMap(10));
		mynewgame.setMyplayers(new HashMap<Index, Player>());
		if(randomHexes)
		{
			//System.out.println("I randomize the hexes");
			mynewgame.getMymap().shuffleHexes();
		}
		if(randomPorts)
		{
			//System.out.println("I randomize the ports");
			mynewgame.getMymap().shufflePorts();
		}
		if(randomHexValues)
		{
			//System.out.println("I randomize the values");
			mynewgame.getMymap().shuffleNumbers();
		}
		for(Player player:mynewgame.getMyplayers().values())
		{
			System.out.println(player.toString());
		}
		mynewgame.getModel().getTurntracker().setStatus(TurnStatus.FIRSTROUND);
		mynewgame.setRobberlocation();
		mynewgame.getModel().getTurntracker().setCurrentTurn(new Index(0), mynewgame.getMyplayers());
		mynewgame.getModel().getTurntracker().setLargestArmy(new Index(-1));
		mynewgame.getModel().getTurntracker().setLongestRoad(new Index(-1));
		serverModel.addGame(mynewgame);
		PersistenceManager.getSingleton().addGameInfo(mynewgame);
		if(Server.isTxtdb)
		{
			PersistenceManager.getSingleton().getMyfactory().getGameManager().createnewGameFile(mynewgame.getGameId());
		}else{
			PersistenceManager.getSingleton().getMyfactory().getGameManager().storeGameModel(mynewgame.getGameId());
		}
	}

	/**
	 * JoinGame: A particular player joins a particular game.
	 * Changing the ints to an index.
	 * @param gameID: ID of the game to join.
	 * @param : ID of the player who is joining.
     */
	private static int playerindexforit=0;
	private static int playeridvariable=100;
	public boolean joinGame(int gameID, int playerid, String color)
	{
		if(getGameByID(gameID).getMyplayers().containsKey(new Index(playerid)))
		{
			//getGameByID(gameID).getMyplayers().get(new Index(playerid)).setJoinedGame(true);
			switch(color.toLowerCase())
			{
			case "red":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.RED);
				break;
			case "orange":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.ORANGE);
				break;
			case "yellow":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.YELLOW);
				break;
			case "blue":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.BLUE);
				break;
			case "green":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.GREEN);
				break;
			case "purple":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.PURPLE);
				break;
			case "puce":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.PUCE);
				break;
			case "white":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.WHITE);
				break;
			case "brown":
				getGameByID(gameID).getMyplayers().get(new Index(playerid)).setColor(CatanColor.BROWN);
				break;
			}

			return true;		
		}
		else
		{
			for (Player p : allRegisteredUsers)
			{
				if(p.getPlayerID().getNumber() == playerid)
				{
					Player copy = new Player(p.getName(),p.getColor(),p.getPlayerID());
					switch(color.toLowerCase())
					{
					case "red":
						copy.setColor(CatanColor.RED);
						break;
					case "orange":
						copy.setColor(CatanColor.ORANGE);
						break;
					case "yellow":
						copy.setColor(CatanColor.YELLOW);
						break;
					case "blue":
						copy.setColor(CatanColor.BLUE);
						break;
					case "green":
						copy.setColor(CatanColor.GREEN);
						break;
					case "purple":
						copy.setColor(CatanColor.PURPLE);
						break;
					case "puce":
						copy.setColor(CatanColor.PUCE);
						break;
					case "white":
						copy.setColor(CatanColor.WHITE);
						break;
					case "brown":
						copy.setColor(CatanColor.BROWN);
						break;
					}
					//System.out.println("I ADD THIS PLAYER"+copy.getName()+" WITH PLAYER INDEX"+playerindexforit+"and PLAYER ID"+playeridvariable+" to game with "+gameID);
					copy.setResources(new ResourceList(0,0,0,0,0));
					copy.setPlayerIndex(new Index(serverModel.listGames().get(gameID).getMyplayers().size()));
					//copy.setPlayerID(new Index(playeridvariable));
					playerindexforit++;
					playeridvariable++;
					serverModel.listGames().get(gameID).addPlayer(copy);
					if(Server.isTxtdb) {
						try {
							PersistenceManager.getSingleton().getMyfactory().getGameManager().loadInfo(gameID);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					return true;
				}
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
		//System.out.println("this is the pointer to the game object" +game);
		//System.out.println("THE GAME GETS LOADED");
		//System.out.println("THIS IS MY GAME ID THAT I GET for exporting"+gameID);
		
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
				System.out.println("I Export the name  and source which are "+mensaje.getMessage()+" and "+mensaje.getSource());
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
					//System.out.println("What is the resource type of the port? " + mapa.get(elHex).getPortType().name().toLowerCase());
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
					//System.out.println(" I DO INDEED HAVE A SETTLEMENT ");
					//if(colonia.getOwner().getNumber() >= 0 && colonia.getOwner().getNumber() <= 4)
					{
						//System.out.println("I DO INDEED INSERT SETTLMENT at location "+elHex.getX()+" "+elHex.getY());
						//System.out.println("THE LOCATION OF SAID DIRECTION BEFORE FUNCTION IS THIS "+colonia.getVertexLocation().getDir());
						//System.out.println( "THAT SETTLEMENT IS ALSO AT DIRECTION "+getDirFromVertexDir(colonia.getVertexLocation().getDir()));
						JSONObject settlement = new JSONObject();
						//System.out.println("The owner's playerIndex (or is it playerID?) is " + colonia.getOwner().getNumber());
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
						//System.out.println("I INSERT IN THE SERVER FACADE A CITY WITH OWNER "+cuidad.getOwner().getNumber());
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
				player.put("soldiers", jugador.getArmySize());
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
				//System.out.println("THE PLAYERS SO FAR WIT NULL PLAYAH  " + players.toString());
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
			
			
			updateWinner(game);
			model.put("version", game.getModel().getVersion());
			model.put("winner", game.getWinner().getNumber());

			//System.out.println("THE MODEL SO FAR WIT EVERYTHANG " + model.toString());
			//System.out.println("GAME TITLE" + game.getTitle());
			return model;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println(e.toString());
		}
		return null;
	}
	
	public void updateWinner(CatanGame game){
		Map<Index, Player> players = game.getMyplayers();
		for(Index index : players.keySet()){
			if(players.get(index).getNumVictoryPoints() >= 10){
				game.setWinner(index);
			}
		}
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
		//System.out.println(" I RETURN A NULL LOCATION!");
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

	public void sendChat(String message, int playerindex,int gameid) throws IOException, JSONException {
		SendChatCommand mychat=new SendChatCommand(message,playerindex,gameid);
		mychat.execute();
		if(Server.isTxtdb) {

			//PersistenceManager.getSingleton().addcommandinfo(mychat);
		}
	}

	/**
	 * Rolls a number which will influence a large part of the game.
	 * This number is randomized and is either computed here or on the model side.
	 * @param number: randomized number between 2 and 12.
     */
	public void rollNumber(int number, int gameID) throws IOException, JSONException {
		ICommand command = new RollNumberCommand(number, gameID);
		command.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(command);
		}
		else{

			PersistenceManager.getSingleton().addcommandinfo(command);
		}
	}

	/**
	 * Robs another player
	 * @param location: Where the robber is at
     */

	public void robPlayer(HexLocation location, int playerRobbing, int playerbeingrobbed, int gameid) throws IOException, JSONException {
		RobPlayerCommand robbing=new RobPlayerCommand(location,playerRobbing,playerbeingrobbed, gameid);
		robbing.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(robbing);
		}
		else{
			PersistenceManager.getSingleton().addcommandinfo(robbing);
			
		}
	}

	/**
	 * Finishes the turn and changes who the current player's index is.
	 * @param playerIndex: the player who is finishing the turn
	 * @return: the index of the next player. who will become the current player.
     */
	public void  finishTurn(int playerIndex, int gameid) throws IOException, JSONException {
		FinishTurnCommand endturn=new FinishTurnCommand(playerIndex,gameid);
		endturn.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(endturn);
		}
		else{

			PersistenceManager.getSingleton().addcommandinfo(endturn);
		}
	}

	/**
	 * Buys a dev card
     */
	public void buyDevCard(int playerid, int gameid)
	{
		playerid -= 100;
		CatanGame currentgame = getGameByID(gameid);
		String buyresult = currentgame.mybank.buyDevCard();
		
		Player player = null;
		for(Index myind : currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber() == playerid)
			{
				player = currentgame.getMyplayers().get(myind);
			}
		}
		
		//Update the player with the new card
		switch(buyresult)
		{
		case "soldier":
			player.getNewDevCards().setSoldier(player.getNewDevCards().getSoldier() + 1);
			break;
		case "monument":
			player.getOldDevCards().setMonument(player.getOldDevCards().getMonument() + 1);
			break;
		case "monopoly":
			player.getNewDevCards().setMonopoly(player.getNewDevCards().getMonopoly() + 1);
			break;
		case "roadbuilding":
			player.getNewDevCards().setRoadBuilding(player.getNewDevCards().getRoadBuilding() + 1);
			break;
		case "yearofplenty":
			player.getNewDevCards().setYearOfPlenty(player.getNewDevCards().getYearOfPlenty() + 1);
			break;
			default:
				return;				
		}
		
		System.out.println("Card bought: " + buyresult);
		
		//Remove the resources needed to purchase new card
		ResourceList resources = player.getResources();
		resources.setOre(resources.getOre() - 1);
		currentgame.mybank.getCardslist().setOre(currentgame.mybank.getCardslist().getOre() + 1);
		resources.setWheat(resources.getWheat() - 1);
		currentgame.mybank.getCardslist().setWheat(currentgame.mybank.getCardslist().getWheat() + 1);
		resources.setSheep(resources.getSheep() - 1);
		currentgame.mybank.getCardslist().setSheep(currentgame.mybank.getCardslist().getSheep() + 1);
		
		//Increment game version
		currentgame.getModel().setVersion(currentgame.getModel().getVersion() + 1);
	}

	/**
	 * Plays a year of plenty card
	 * @param gameid
	 * @param resource2 
	 * @param resource1 
	 * @param playerid: player who is playing card
     */
	public void playYearOfPlenty(int playerid, String resource1, String resource2, int gameid)
	{
		System.out.println("Starting year of plenty facade");
		playerid -= 100;
		CatanGame currentgame = getGameByID(gameid);
		String buyresult = currentgame.mybank.buyDevCard();
		
		Player player = null;
		for(Index myind : currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber() == playerid)
			{
				player = currentgame.getMyplayers().get(myind);
			}
		}
		ArrayList<String> obtaining = new ArrayList<String>();
		obtaining.add(resource1);
		obtaining.add(resource2);
		
		System.out.println("Starting my plenty! I want: " + resource1 + " and " + resource2);
		for (String resource : obtaining)
		{
			System.out.println("Starting the year!");
			switch(resource)
			{
			case "wheat":
				player.getResources().setWheat(player.getResources().getWheat() + 1);
				break;
			case "sheep":
				player.getResources().setSheep(player.getResources().getSheep() + 1);
				break;
			case "ore":
				player.getResources().setOre(player.getResources().getOre() + 1);
				break;
			case "brick":
				player.getResources().setBrick(player.getResources().getBrick() + 1);
				break;
			case "wood":
				player.getResources().setWood(player.getResources().getWood() + 1);
				break;
				default:
					return;				
			}
		}
		
		//Take away used card
		player.getOldDevCards().setYearOfPlenty(player.getOldDevCards().getYearOfPlenty() - 1);
		
		//Increment game version
		currentgame.getModel().setVersion(currentgame.getModel().getVersion() + 1);
		System.out.println("Done year of plenty facade");
	}

	/**
	 * Plays a road building card
	 * @param gameID 
	 * @param freebe 
	 * @param edgeDirectionFromString 
	 * @param hexLocation 
	 * @param playerIndex: player who is playing card
     */
	int roadscounter = 1;
	public void playRoadBuilding(int playerid, HexLocation hexLocation, EdgeLocation edgeDirectionFromString, boolean freebe, int gameid) throws IOException, JSONException {
		System.out.println("Starting road building facade");
		//Setup
		playerid -= 100; // this is an incorrect method of obtaining the ID. this is probably what is breaking this.
		CatanGame currentgame = getGameByID(gameid);
		String buyresult = currentgame.mybank.buyDevCard();		
		Player player = null;
		for(Index myind : currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber() == playerid)
			{
				player = currentgame.getMyplayers().get(myind);
			}
		}
		
		System.out.println("Right before I call buildRoad...");
		System.out.println("The player id is " + playerid);
		System.out.println("the edge direction is " + edgeDirectionFromString.getDir().name());
		System.out.println("it should be free here: " + freebe);
		System.out.println("aaaand the gameID is " + gameid);
		//Call build road
		buildRoad(playerid, hexLocation, edgeDirectionFromString, freebe, gameid);
		System.out.println("Right after I call buildRoad!");
		
		//Cleanup after both roads have been built 
		if(roadscounter == 2)
		{	
			System.out.println("Cleaning up - taking away card and incrementing version");
			//Take away used card
			player.getOldDevCards().setRoadBuilding(player.getOldDevCards().getRoadBuilding() - 1);
			
			//Increment game version
			currentgame.getModel().setVersion(currentgame.getModel().getVersion() + 1);
			
			//Reset counter for next time a road building card is played
			roadscounter = 1;
			return;
			
		}
		roadscounter++;
		System.out.println("Done road building facade");
	}

	/**
	 * Plays a soldier card
	 * @param playerRobbing: player who is playing card
     */
	public void playSoldier(HexLocation location, int playerRobbing, int playerBeingRobbed, int gameid) throws IOException, JSONException {
		System.out.println("Starting soldier facade");
		//Setup
		CatanGame currentgame = getGameByID(gameid);
		String buyresult = currentgame.mybank.buyDevCard();		
		Player player = null;
		
		System.out.println("Right before I rob...");
		robPlayer(location, playerRobbing, playerBeingRobbed, gameid);
		System.out.println("Right after I rob!");
		
		System.out.println("id: " + playerRobbing);
		for(Index myind : currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber() == playerRobbing)
			{
				player = currentgame.getMyplayers().get(myind);
			}
		}
		System.out.println(player.getName());
		System.out.println("Army size before: " + player.getArmySize());
		//Increment player's army size
		player.setArmySize(player.getArmySize() + 1);
		System.out.println("Army size after: " + player.getArmySize());
		
		//Take away used card
		player.getOldDevCards().setSoldier(player.getOldDevCards().getSoldier() - 1);
		
		//Increment game version
		currentgame.getModel().setVersion(currentgame.getModel().getVersion() + 1);
		if(player.getArmySize()>=3)
		{
			Index playerindexcurrentlywithlargetstarmy=currentgame.getModel().getTurntracker().getLargestArmy();
			if(playerindexcurrentlywithlargetstarmy.getNumber()==-1)
			{
				currentgame.getModel().getTurntracker().setLargestArmy(player.getPlayerIndex());
				player.setNumVictoryPoints(player.getNumVictoryPoints()+2);
				return;
			}
			Player playerwithlargestarmy=null;
			for(Player player1:currentgame.getMyplayers().values())
			{
				if(playerindexcurrentlywithlargetstarmy.getNumber()==player.getPlayerIndex().getNumber()) {
					playerwithlargestarmy = player1;
				}
			}
			if(player.getArmySize()>playerwithlargestarmy.getArmySize())
			{
				player.setNumVictoryPoints(player.getNumVictoryPoints()+2);
				playerwithlargestarmy.setNumVictoryPoints(playerwithlargestarmy.getNumVictoryPoints()-2);
				currentgame.getModel().getTurntracker().setLongestRoad(player.getPlayerIndex());
			}

		}

		System.out.println("Done soldier facade");
	}

	/**
	 * Plays a monopoly card
	 * @param playerid: player who is playing card
     */
	public void playMonopoly(int playerid, String resource, int gameid)
	{
		playerid -= 100;
		CatanGame currentgame = getGameByID(gameid);
		String buyresult = currentgame.mybank.buyDevCard();		
		Player player = null;
		for(Index myind : currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber() == playerid)
			{
				player = currentgame.getMyplayers().get(myind);
			}
		}
		
		int numStealing;
		for (Player victim : currentgame.getMyplayers().values())
		{
			numStealing = 0;
			if(victim.getPlayerID().getNumber() != playerid)
			{
				switch(resource)
				{
				case "wheat":
					numStealing += victim.getResources().getWheat();
					victim.getResources().setWheat(0);
					player.getResources().setWheat(player.getResources().getWheat() + numStealing);
					break;
				case "sheep":
					numStealing += victim.getResources().getBrick();
					victim.getResources().setBrick(0);
					player.getResources().setBrick(player.getResources().getBrick() + numStealing);
					break;
				case "ore":
					numStealing += victim.getResources().getOre();
					System.out.println("I'm stealing " + numStealing + " from " + victim.getName());
					victim.getResources().setOre(0);
					System.out.println("Victim's resources are gone!");
					player.getResources().setOre(player.getResources().getOre() + numStealing);
					System.out.println("My resources are now at: " + player.getResources().getOre());
					break;
				case "brick":
					numStealing += victim.getResources().getBrick();
					System.out.println("I'm stealing " + numStealing + " from " + victim.getName());
					victim.getResources().setBrick(0);
					System.out.println("Victim's resources are gone!");
					player.getResources().setBrick(player.getResources().getBrick() + numStealing);
					System.out.println("My resources are now at: " + player.getResources().getBrick());
					break;
				case "wood":
					numStealing += victim.getResources().getWood();
					System.out.println("I'm stealing " + numStealing + " from " + victim.getName());
					victim.getResources().setWood(0);
					System.out.println("Victim's resources are gone!");
					player.getResources().setWood(player.getResources().getWood() + numStealing);
					System.out.println("My resources are now at: " + player.getResources().getWood());
					break;
					default:
						return;				
				}				
			}
			System.out.println("Now ready for the next victim!");
		}
		
		//Take away card
		player.getOldDevCards().setMonopoly(player.getOldDevCards().getMonopoly() + 1);
		
		//Increment model version
		currentgame.getModel().setVersion(currentgame.getModel().getVersion() + 1);
		
		System.out.println("Done monopoly facade");
	}

	/**
	 * Plays a monument card
	 * @param playerid: player who is playing card
     */
	public void playMonument(int playerid, int gameid)
	{
		System.out.println("Starting monument facade");
		//Setup
		playerid -= 100;
		CatanGame currentgame = getGameByID(gameid);
		String buyresult = currentgame.mybank.buyDevCard();		
		Player player = null;
		for(Index myind : currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber() == playerid)
			{
				player = currentgame.getMyplayers().get(myind);
			}
		}

		//Increment VP/Take away card
		player.setNumVictoryPoints(player.getNumVictoryPoints() + 1);
		player.getOldDevCards().setMonument(player.getOldDevCards().getMonument() - 1);
		
		//Increment model version
		currentgame.getModel().setVersion(currentgame.getModel().getVersion() + 1);
		System.out.println("Done monument facade");
	}


	/**
	 * Builds a road on the map.
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built
	 * @param edge: the edge it is being built on
     */
	public void buildRoad(int playerIndex, HexLocation location, EdgeLocation edge, boolean free, int gameid) throws IOException, JSONException {
		System.out.println("I AM IN BUILD ROAD!");
		BuildRoadCommand buildRoadCommand=new BuildRoadCommand(playerIndex,location,edge,free, gameid);
		buildRoadCommand.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(buildRoadCommand);
		}
		else{

			PersistenceManager.getSingleton().addcommandinfo(buildRoadCommand);
		}

	}

	/**
	 * Builds a settlement on the map.
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built (which hex)
	 * @param vertex: which vertex it is being built on
     */
	public void buildSettlement(int playerIndex, HexLocation location, VertexLocation vertex, boolean free, int gameid) throws IOException, JSONException {
		System.out.println("YE HAVE CALLED TO BUILD!");
		BuildSettlementCommand buildsettlement=new BuildSettlementCommand(playerIndex,location,vertex,free,gameid);
		buildsettlement.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(buildsettlement);
		}
		else{

			PersistenceManager.getSingleton().addcommandinfo(buildsettlement);
		}

	}

	/**
	 * builds a city on the map
	 * @param playerIndex: player who is playing card
	 * @param location: where it is being built
	 * @param vertex: needs to already have a settlement on it + required resources for player
     */
	public void buildCity(int playerIndex, HexLocation location, VertexLocation vertex, int gameid) throws IOException, JSONException {
		BuildCityCommand buildCityCommand=new BuildCityCommand(playerIndex,location,vertex, gameid);
		buildCityCommand.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(buildCityCommand);
		}
		else{

			PersistenceManager.getSingleton().addcommandinfo(buildCityCommand);
		}
	}

	/**
	 * Function to offer trade to another player
	 *
     */
	public void offerTrade(int gameid, int playerIndex, ResourceList offer,int receiver) throws IOException, JSONException {
		ICommand command = new OfferTradeCommand(gameid, playerIndex, offer, receiver);
		command.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(command);
		}
		else{

			PersistenceManager.getSingleton().addcommandinfo(command);
		}
	}

	/**
	 * Accepts a trade that was offered to you.
	 //* @param getResource: resource player is getting
	 //* @param giveResource: resource
	 * @param playerIndex: player who is playing card
     */
	public void acceptTrade(int gameid, int playerIndex, boolean willAccept) throws IOException, JSONException {
		ICommand command = new AcceptTradeCommand(gameid, playerIndex, willAccept);
		command.execute();
		if(Server.isTxtdb) {
			PersistenceManager.getSingleton().addcommandinfo(command);
		}
		else{
			PersistenceManager.getSingleton().addcommandinfo(command);
			
		}
	}

	/**
	 * Function to make the trade with bank
	 * @param getResource: resource getting
	 * @param giveResource: resource receiving
	 * @param playerIndex: player who is playing card
     * @param ratio: ratio at which we are making the trade
	 * @param gameID: ID for the current game
     */
	public void maritimeTrade(String getResource, String giveResource, int playerIndex, int ratio, int gameID) throws Exception
	{
		MaritimeTradeCommand maritimeTradeCommand = new MaritimeTradeCommand(getResource, giveResource, playerIndex, ratio, gameID);
		maritimeTradeCommand.execute();
		if(Server.isTxtdb) {

			PersistenceManager.getSingleton().addcommandinfo(maritimeTradeCommand);
		}
		else{

			PersistenceManager.getSingleton().addcommandinfo(maritimeTradeCommand);
		}
	}

	/**
	 * Allows us to discard cards
	 * @param playerIndex: player who is playing card
	 * @param cardsToDiscard: which cards player wants to get rid of
	 *                      will probably change the data storage
     */
	public void discardCards(int playerIndex, ResourceList cardsToDiscard, int gameid) throws IOException, JSONException {
		DiscardCardsCommand mydiscard=new DiscardCardsCommand(playerIndex,cardsToDiscard,gameid);
		mydiscard.execute();
		if(Server.isTxtdb) {
			PersistenceManager.getSingleton().addcommandinfo(mydiscard);
		}
		else{
			PersistenceManager.getSingleton().addcommandinfo(mydiscard);
			
		}
	}


	private EdgeDirection getDirectionfromStringspecial(String direction)
	{
		System.out.println("the direction is: " + direction);
		switch (direction)
		{
			case "NorthWest":
				return EdgeDirection.NorthWest;
			case "North":
				return EdgeDirection.North;
			case "NorthEast":
				return EdgeDirection.NorthEast;
			case "SouthWest":
				return EdgeDirection.SouthWest;
			case "South":
				return EdgeDirection.South;
			case "SouthEast":
				return EdgeDirection.SouthEast;
			default:
				//System.out.println("Something is screwed up with the direction");
				//assert false;
				break;
		}
		return null;
	}

	private EdgeDirection getDirectionFromString(String direction)
	{
		System.out.println("the direction is: " + direction);
		switch (direction)
		{
			case "NW":
				return EdgeDirection.NorthWest;
			case "N":
				return EdgeDirection.North;
			case "NE":
				return EdgeDirection.NorthEast;
			case "SW":
				return EdgeDirection.SouthWest;
			case "S":
				return EdgeDirection.South;
			case "SE":
				return EdgeDirection.SouthEast;
			default:
				//System.out.println("Something is screwed up with the direction");
				//assert false;
				break;
		}
		return null;
	}

	private VertexDirection convertToVertexDirectionspecial(String direction)
	{
		System.out.println("MY DIRECTION IS "+direction);
		switch (direction)
		{
			case "West":
				return VertexDirection.West;
			case "NorthWest":
				return VertexDirection.NorthWest;
			case "NorthEast":
				return VertexDirection.NorthEast;
			case "East":
				return VertexDirection.East;
			case "SouthEast":
				return VertexDirection.SouthEast;
			case "SouthWest":
				return VertexDirection.SouthWest;
			default:
				break;
			//assert false;
		}
		return null;
	}

	private VertexDirection convertToVertexDirection(String direction)
	{
		System.out.println("MY DIRECTION IS "+direction);
		switch (direction)
		{
			case "W":
				return VertexDirection.West;
			case "NW":
				return VertexDirection.NorthWest;
			case "NE":
				return VertexDirection.NorthEast;
			case "E":
				return VertexDirection.East;
			case "SE":
				return VertexDirection.SouthEast;
			case "SW":
				return VertexDirection.SouthWest;
			default:
				break;
			//assert false;
		}
		return null;
	}

}
