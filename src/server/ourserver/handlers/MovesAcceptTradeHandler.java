package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.ourserver.ServerFacade;
import shared.game.ResourceList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by williamjones on 5/26/16.
 /**
 *type name of move being executed
 * playerIndex the player's position in the game's turn order
 *  willAccept whether or not you accept the trade offered
 *
 * @preYou have been offered a domestic trade
 * 						To accept the offered trade, you have the required resources
 * @post If you accepted, you and the player who offered swap the specified resources
 * 						If you declined no resources are exchanged
 * 						The trade offer is remove
 *
 */
public class MovesAcceptTradeHandler implements HttpHandler {
	
	
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
    	String cookie = httpExchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
    	JSONObject data = null;
		httpExchange.getResponseHeaders().add("Content-type", "text/html");
		try
		{
			Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
			data = new JSONObject(result);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			String response = "You gotta give me something to work with if you wanna accept the trade offer.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		}
		//System.out.println("This is our JSON Object: " + data.toString());
		int playerIndex = -1;
		boolean willAccept = false; 
		try
		{
			
			playerIndex = data.getInt("playerIndex");
			willAccept = data.getBoolean("willAccept");
			if (playerIndex == -1)
			{
				//This is how you add a response object (most things need one)
				String response = "Your offer sucks. ";
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				httpExchange.getResponseBody().write(response.getBytes());
				//System.out.println("Something bad happened...");
				httpExchange.close();
				return;
			}		
			ServerFacade.getInstance().acceptTrade(gameID, playerIndex, willAccept);
			String response = "Success! :D";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			//System.out.println("So it worked! :D ");
			httpExchange.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			String response = "So you are missing some info accept a trade offer.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		}
    }
    
    public int getGameIDfromCookie(String cookie){
    	return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));
    }
}
