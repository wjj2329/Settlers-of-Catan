package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.commands.ICommand;
import server.ourserver.commands.PlayMonumentCommand;
import server.ourserver.commands.PlayRoadBuildingCommand;
import shared.locations.HexLocation;

/**
 * Created by williamjones on 5/26/16.
 *
 *  type name of move being executed
 *  playerIndex the player's position in the game's turn order
 *
 * @pre  it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * 						you have enough monument cards to win the game
 * @post you gained a victory point
 *
 *
 */

public class MovesMonumentHandler implements HttpHandler 
{
	   @Override
	    public void handle(HttpExchange exchange) throws IOException
	    {
		   System.out.println("Starting monument handler");
	        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
	        int gameID = getGameIDfromCookie(cookie);
	        int playerindex=-50;

	        JSONObject data = null;
	        try
	        {
	            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
	            String result = s.hasNext() ? s.next() : "";
	            data = new JSONObject(result);
	        }
	        catch (JSONException e)
	        {
	            e.printStackTrace();
	        }
	        try 
	        {
	            playerindex=data.getInt("playerIndex");
	        } 
	        catch (JSONException e) 
	        {
	            e.printStackTrace();
	        }
	        
	        ICommand command = new PlayMonumentCommand(playerindex, gameID);
	        command.execute();
	        
	        String response = "Success";
	        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
	        exchange.getResponseBody().write(response.getBytes());
	        exchange.close();

	        }
	    public int getGameIDfromCookie(String cookie)
	    {
	        return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

	    }
}
