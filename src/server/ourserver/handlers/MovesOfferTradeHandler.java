package server.ourserver.handlers;

import com.sun.corba.se.spi.activation.Server;
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
 * * @param type name of move being executed
 * playerIndex the person making offer
 *  offer negative numbers mean you get those cards
 *  receiver person receiving offer
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						you have the resources you are offering
 * @post the trade is offered to the other player(stored in the server model)
 *
 */
public class MovesOfferTradeHandler implements HttpHandler {
	
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
			String response = "You gotta give me something to work with if you wanna make a trade offer.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		}
		//System.out.println("This is our JSON Object: " + data.toString());
		int playerIndex = -1;
		ResourceList offer = new ResourceList();
		int receiver = -1;
		try
		{
			playerIndex = data.getInt("playerIndex");
			receiver = data.getInt("receiver");
			JSONObject tradeOffer = data.getJSONObject("offer");
			offer = new ResourceList(tradeOffer.getInt("brick"), tradeOffer.getInt("ore"), tradeOffer.getInt("sheep"), 
									tradeOffer.getInt("wheat"), tradeOffer.getInt("wood"));
			if (offer == null || receiver == -1 || playerIndex == -1)
			{
				//This is how you add a response object (most things need one)
				String response = "Your offer sucks. ";
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				httpExchange.getResponseBody().write(response.getBytes());
				//System.out.println("Something bad happened...");
				httpExchange.close();
				return;
			}		
			ServerFacade.getInstance().offerTrade(gameID, playerIndex, offer, receiver);
			String response = "Success! :D";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			//System.out.println("So it worked! :D ");
			httpExchange.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			String response = "So you are missing some info make a trade offer.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		}
    }
    
    public int getGameIDfromCookie(String cookie){
    	return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));
    }
}
