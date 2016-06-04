package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import shared.locations.HexLocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 * playerIndex the player you are robbing or -1 if you are not robbing anyone
 *  location new location of robber
 *  victimIndex the player you are robbing or -1 if you are not robbing anyone
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 					  robber is not being kept in the same location, if a player
 * @postthe robber is in the new location, the player being robbed(if any) gave one
 * 					 one of his resource cards (if randomly selected)
 *
 *
 */

public class MovesRobPlayerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        //System.out.println("I GO TO THE ROB PLAYER HAANDLER");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerFacade.getInstance().robPlayer(new HexLocation(x,y),playerindex,victimindex,gameID);
        String response = "WHY DOES THIS EXIST!!!!!!!!!!";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

    }
    public int getGameIDfromCookie(String cookie)
    {
        return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

    }

}

