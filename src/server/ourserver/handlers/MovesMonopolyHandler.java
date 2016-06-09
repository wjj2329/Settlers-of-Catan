package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.commands.ICommand;
import server.ourserver.commands.PlayMonopolyCommand;
import server.ourserver.commands.PlayYearOfPlentyCommand;

/**
 * Created by williamjones on 5/26/16.
 * /**
 * type name of move being executed
 *   playerIndex the player's position in the game's turn order
 *  resource the resource being taken from the others players
 *
 * @pre  it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * @post all of the other players have give you all of their resources cards of the specified type
 *
 *
 */

public class MovesMonopolyHandler implements HttpHandler
{
	@Override
    public void handle(HttpExchange exchange) throws IOException
    {
		System.out.println("Starting monopoly handler");
    	int playerindex=-10;
        String resource = "";
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
        JSONObject data = null;
		exchange.getResponseHeaders().add("Content-type", "text/html");
        try
        {
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            data = new JSONObject(result);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
			String response = "You gotta give me something to work with if you wanna play monopoly.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }
        try
        {
            playerindex = data.getInt("playerIndex");
            resource = data.getString("resource");
        } 
        catch (JSONException e) 
        {
            e.printStackTrace();
			String response = "So you are missing some info to play monopoly bro.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }

        ICommand monopolyCommand = new PlayMonopolyCommand(playerindex,resource.toLowerCase(),gameID);
        try {
            monopolyCommand.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
