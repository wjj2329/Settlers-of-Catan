package server.ourserver;

import java.io.*;
import java.net.*;
import java.rmi.ServerException;
import java.util.logging.*;
import server.ourserver.handlers.*;

import com.sun.net.httpserver.*;

public class Server
{
	
	private static Logger logger;
	
	static
	{
		try
		{
			initLog();
		} catch (IOException e)
		{
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException
	{
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("SettlersOfCatan");
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);
		
		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
	
	private HttpServer server;
	
	private Server()
	{
		return;
	}
	
	private void run(int serverPortNumber)
	{
		
		logger.info("Initializing Model");
		
		try
		{
			ServerFacade.initialize();
		} catch (ServerException e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try
		{
			server = HttpServer.create(new InetSocketAddress(serverPortNumber),
					16);
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		server.setExecutor(null); // use the default executor
		
		server.createContext("/user/login", loginUserHandler);
		server.createContext("/user/register", registerUserHandler);
		server.createContext("/games/list", listGamesHandler); // maybe this?
		server.createContext("/games/create", createGameHandler);
		server.createContext("/games/join", joinGameHandler);
		server.createContext("/game/model", getModelHandler);
		server.createContext("/game/addAI", addAIHandler);
		server.createContext("/game/listAI", listAIHandler);
		server.createContext("/moves/sendChat", sendChatHandler);
		server.createContext("/moves/rollNumber", rollNumberHandler);
		server.createContext("/moves/robPlayer", robPlayerHandler);
		server.createContext("/moves/finishTurn", finishTurnHandler);
		server.createContext("/moves/buyDevCard", buyDevCardHander);
		server.createContext("/moves/Year_of_Plenty", yearOfPlentyHandler);
		server.createContext("/moves/Road_Building", roadBuildingHandler);
		server.createContext("/moves/Soldier", soldierHandler);
		server.createContext("/moves/Monopoly", monopolyHandler);
		server.createContext("/moves/Monument", monumentHandler);
		server.createContext("/moves/buildRoad", buildRoadHandler);
		server.createContext("/moves/buildSettlement", buildSettlementHandler);
		server.createContext("/moves/buildCity", buildCityHandler);
		server.createContext("/moves/offerTrade", offerTradeHandler);
		server.createContext("/moves/acceptTrade", acceptTradeHandler);
		server.createContext("/moves/maritimeTrade", maritimeTradeHandler);
		server.createContext("/moves/discardCards", discardCardsHandler);
		/*
		 * Uncomment for PHASE 4 
		 * server.createContext("/save", SaveGameHandler);
		 * server.createContext("/load", submitBatchHandler);
		 * server.createContext("/reset", downloadFileHandler);
		 * server.createContext("/commands", downloadFileHandler);
		 * server.createContext("/commands", downloadFileHandler);
		 * server.createContext("/changeLogLevel", downloadFileHandler);
		 */
		
		logger.info("Starting HTTP Server");
		
		server.start();
	}
	
	private HttpHandler loginUserHandler = new LoginUserHandler();
	private HttpHandler registerUserHandler = new RegisterUserHandler();
	private HttpHandler listGamesHandler = new GamesListHandler();
	private HttpHandler createGameHandler = new GameCreateHandler();
	private HttpHandler joinGameHandler = new GamesJoinHandler();
	private HttpHandler getModelHandler = new GameModelHandler();
	private HttpHandler addAIHandler = new GameAddAiHandler();
	private HttpHandler listAIHandler = new GameListAiHandler();
	private HttpHandler sendChatHandler = new MovesSendChatHandler();
	private HttpHandler rollNumberHandler = new MovesRollNumberHandler();
	private HttpHandler robPlayerHandler = new MovesRobPlayerHandler();
	private HttpHandler finishTurnHandler = new MovesFinishTurnHandler();
	private HttpHandler buyDevCardHander = new MovesBuyDevCardHandler();
	private HttpHandler yearOfPlentyHandler = new MovesYearOfPlentyHandler();
	private HttpHandler roadBuildingHandler = new MovesYearOfPlentyHandler();
	private HttpHandler soldierHandler = new MovesSoldierHandler();
	private HttpHandler monopolyHandler = new MovesMonopolyHandler();
	private HttpHandler monumentHandler = new MovesMonumentHandler();
	private HttpHandler buildRoadHandler = new MovesBuildRoadHandler();
	private HttpHandler buildSettlementHandler = new MovesBuildSettlementHandler();
	private HttpHandler buildCityHandler = new MovesBuildCityHandler();
	private HttpHandler offerTradeHandler = new MovesOfferTradeHandler();
	private HttpHandler acceptTradeHandler = new MovesAcceptTradeHandler();
	private HttpHandler maritimeTradeHandler = new MovesMaritimeTradeHandler();
	private HttpHandler discardCardsHandler = new MovesYearOfPlentyHandler();
	/*
	 * Uncomment for PHASE 4
	 * private HttpHandler saveGameHandler = new ListAIHandler();
	 * private HttpHandler loadGameHandler = new ListAIHandler();
	 * private HttpHandler resetGameHandler = new ListAIHandler();
	 * private HttpHandler commandsHandler = new ListAIHandler();
	 * private HttpHandler getCommandsHandler = new ListAIHandler();
	 * private HttpHandler changelogLevelHandler = new ListAIHandler();
	 */
	
	public static void main(String[] args)
	{
		if(args.length == 0 || args[0].equals(""))
		{
			new Server().run(8081);
		}
		else
		{
			new Server().run(Integer.parseInt(args[0]));
		}
	}
}