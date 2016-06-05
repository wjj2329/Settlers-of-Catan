package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.commands.ICommand;
import server.ourserver.commands.PlaySoldierCommand;
import server.ourserver.commands.PlayYearOfPlentyCommand;
import shared.locations.HexLocation;

/**
 * Created by williamjones on 5/26/16.
 * /**
 * type name of move being executed
 *playerIndex the player's position in the game's turn order
 *  resource1 first resource you want to receive
 *  resource2 second resource you want to receive
 *
 * @pre  it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * 						two specified recources are in the bank
 * @post you gained to specifed resources
 *
 *
 */

public class MovesYearOfPlentyHandler implements HttpHandler
{
	@Override
    public void handle(HttpExchange exchange) throws IOException
    {
    	int playerindex=-10;
        String resource1 = "";
        String resource2 = "";
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
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
            playerindex = data.getInt("playerIndex");
            resource1 = data.getString("resource1");
            resource2 = data.getString("resource2");
        } 
        catch (JSONException e) 
        {
            e.printStackTrace();
        }

        ICommand yopcommand = new PlayYearOfPlentyCommand(playerindex,resource1,resource2,gameID);
        yopcommand.execute();
        
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
