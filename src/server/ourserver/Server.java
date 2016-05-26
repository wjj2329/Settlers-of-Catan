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
		
		server.createContext("/login", loginUserHandler);
		server.createContext("/register", registerUserHandler);
		server.createContext("/list", listGamesHandler);
		server.createContext("/create", createGameHandler);
		server.createContext("/join", joinGameHandler);
		server.createContext("/model", getModelHandler);
		server.createContext("/addAI", addAIHandler);
		server.createContext("/listAI", listAIHandler);
		server.createContext("/sendChat", sendChatHandler);
		server.createContext("/rollNumber", rollNumberHandler);
		server.createContext("/robPlayer", robPlayerHandler);
		server.createContext("/finishTurn", finishTurnHandler);
		server.createContext("/buyDevCard", buyDevCardHander);
		server.createContext("/Year_of_Plenty", yearOfPlentyHandler);
		server.createContext("/Road_Building", roadBuildingHandler);
		server.createContext("/Soldier", soldierHandler);
		server.createContext("/Monopoly", monopolyHandler);
		server.createContext("/Monument", monumentHandler);
		server.createContext("/buildRoad", buildRoadHandler);
		server.createContext("/buildSettlement", buildSettlementHandler);
		server.createContext("/buildCity", buildCityHandler);
		server.createContext("/offerTrade", offerTradeHandler);
		server.createContext("/acceptTrade", acceptTradeHandler);
		server.createContext("/maritimeTrade", maritimeTradeHandler);
		server.createContext("/discardCards", discardCardsHandler);		
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
	private HttpHandler listGamesHandler = new ListGamesHandler();
	private HttpHandler createGameHandler = new CreateGameHandler();
	private HttpHandler joinGameHandler = new JoinGameHandler();
	private HttpHandler getModelHandler = new GetModelHandler();
	private HttpHandler addAIHandler = new AddAIHandler();
	private HttpHandler listAIHandler = new ListAIHandler();
	private HttpHandler sendChatHandler = new SendChatHandler();
	private HttpHandler rollNumberHandler = new RollNumberHandler();
	private HttpHandler robPlayerHandler = new RobPlayerHandler();
	private HttpHandler finishTurnHandler = new FinishTurnHandler();
	private HttpHandler buyDevCardHander = new BuyDevCardHandler();
	private HttpHandler yearOfPlentyHandler = new YearOfPlentyHandler();
	private HttpHandler roadBuildingHandler = new RoadBuildingHandler();
	private HttpHandler soldierHandler = new SoldierHandler();
	private HttpHandler monopolyHandler = new MonopolyHandler();
	private HttpHandler monumentHandler = new MonumentHandler();
	private HttpHandler buildRoadHandler = new BuildRoadHandler();
	private HttpHandler buildSettlementHandler = new BuildSettlementHandler();
	private HttpHandler buildCityHandler = new BuildCityHandler();
	private HttpHandler offerTradeHandler = new OfferTradeHandler();
	private HttpHandler acceptTradeHandler = new AcceptTradeHandler();
	private HttpHandler maritimeTradeHandler = new MaritimeTradeHandler();
	private HttpHandler discardCardsHandler = new DiscardCardsHandler();
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
		if(args[0].equals(""))
		{
			new Server().run(8081);
		}
		else
		{
			new Server().run(Integer.parseInt(args[0]));
		}
	}
}