package server.ourserver.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.ServerFacade;
import server.ourserver.commands.ICommand;
import server.ourserver.commands.PlayRoadBuildingCommand;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MovesRoadBuildingHandler implements HttpHandler
{
	
    private EdgeLocation getEdgeDirectionFromString(String direction, HexLocation myloc)
    {
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
        boolean freebe=true;
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
			String response = "You gotta give me something to work with to play Road Building";
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
        } catch (JSONException e) {
            e.printStackTrace();
			String response = "So you are missing some info to play road building bro.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }
        
        ICommand roadbuildingcommand = new PlayRoadBuildingCommand(playerindex,new HexLocation(x,y),getEdgeDirectionFromString(direction,new HexLocation(x,y)),freebe,gameID);
        roadbuildingcommand.execute();
        
        String response = "You successfully played RoadBuiling!";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

        }
    public int getGameIDfromCookie(String cookie)
    {
        return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

    }
	
}
