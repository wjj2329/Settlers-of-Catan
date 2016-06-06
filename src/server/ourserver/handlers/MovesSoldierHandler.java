package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.ServerFacade;
import server.ourserver.commands.ICommand;
import server.ourserver.commands.PlayRoadBuildingCommand;
import server.ourserver.commands.PlaySoldierCommand;
import shared.locations.HexLocation;

/**
 * /**
 * type name of move being executed
 * playerIndex the player's position in the game's turn order
 * location new robber location
 *  victimIndex the player you are robbing or -1 if you are not robbing anyone
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * 						you have not yet played a non-monument dev card this turn
 * 						if robber is not being kept in the same location, if player being robbed they have the resource cards
 * @post robber is in the new location, player being robbed gave you a resource
 * 						card, "largest army" awarded to the player who has played the most soldier card, no allowed to play other deelopment cards
 * 						during this turn
 *
 *
 * Created by williamjones on 5/26/16.
 */
public class MovesSoldierHandler  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
    	System.out.println("Starting soldier handler");
    	int playerindex=-10;
        int victimindex=-10;
        int x=-100000;
        int y=-100000;
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
            playerindex=data.getInt("playerIndex");
            victimindex=data.getInt("victimIndex");
            JSONObject myobject=data.getJSONObject("location");
            x=myobject.getInt("x");
            y=myobject.getInt("y");
        } 
        catch (JSONException e) 
        {
            e.printStackTrace();
        }

        ICommand soldiercommand = new PlaySoldierCommand(new HexLocation(x,y),playerindex,victimindex,gameID);
        soldiercommand.execute();
        
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
