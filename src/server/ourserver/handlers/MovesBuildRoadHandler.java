package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import shared.locations.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Created by williamjones on 5/26/16.
 * * @param type name of move being executed
 * the player's position in the game's turn order
 *  whether or no you get this piece for free
 * the new road's location
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						road location is open, is connected to another road owned by the player, is not on
 * 						the water, you have the required resources(1 wood, 1 brick; 1 road)
 * @post You have the required resources to build a road(1 wood, 1 brick; 1 road)
 * 						The road is on the map at the specified location
 * 						if applicable, "longest road" has been awarded to the player with the longest road
 *
 */
public class MovesBuildRoadHandler implements HttpHandler
{

    private EdgeLocation getEdgeDirectionFromString(String direction, HexLocation myloc)
    {
        //System.out.println("the direction is: " + direction);
        switch (direction)
        {
            case "NW":
                return new EdgeLocation(myloc,EdgeDirection.NorthWest);
            case "N":
                return new EdgeLocation(myloc,EdgeDirection.North);
            case "NE":
                return new EdgeLocation(myloc,EdgeDirection.NorthEast);
            case "SW":
                return new EdgeLocation(myloc,EdgeDirection.SouthWest);
            case "S":
                return new EdgeLocation(myloc,EdgeDirection.South);
            case "SE":
                return new EdgeLocation(myloc,EdgeDirection.SouthEast);
            default:
                //System.out.println("Something is screwed up with the direction");
                //assert false;
                break;
        }
        return null;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
        int playerindex=-50;
        int x=-10000000;
        int y=-10000000;
        String direction=null;
        JSONObject data = null;
        boolean freebe=false;
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
			String response = "You gotta give me something to work with if you wanna build a road.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }
        try {

            playerindex=data.getInt("playerIndex");
            JSONObject myobject=data.getJSONObject("roadLocation");
            x=myobject.getInt("x");
            y=myobject.getInt("y");
            direction=myobject.getString("direction");
            freebe=data.getBoolean("free");
        } catch (JSONException e) {
            e.printStackTrace();
			String response = "So you are missing some info to build roads.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }

        try {
            ServerFacade.getInstance().buildRoad(playerindex,new HexLocation(x,y),getEdgeDirectionFromString(direction,new HexLocation(x,y)),freebe,gameID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

