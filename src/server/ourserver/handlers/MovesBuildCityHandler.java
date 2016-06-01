package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Created by williamjones on 5/26/16.
 * /**
 * type name of move being executed
 * playerIndex the player's position in the game's turn order
 * vertexLocation the location of the city
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 					   the city location is where you currently have a settlement,
 * 						you have the required resources (2 wheat, 3 ore; 1 city)
 * @post you lost resources required to build a city, the city is on the map at the specified
 * 						location, you got a settlement back
 *
 */


public class MovesBuildCityHandler implements HttpHandler {
    private VertexLocation convertToVertexDirection(String direction, HexLocation myhexloc)
    {
        switch (direction)
        {
            case "W":
                return new VertexLocation(myhexloc, VertexDirection.West);
            case "NW":
                return new VertexLocation(myhexloc,VertexDirection.NorthWest);
            case "NE":
                return new VertexLocation(myhexloc,VertexDirection.NorthEast);
            case "E":
                return new VertexLocation(myhexloc,VertexDirection.East);
            case "SE":
                return new VertexLocation(myhexloc,VertexDirection.SouthEast);
            case "SW":
                return new VertexLocation(myhexloc,VertexDirection.SouthWest);
            default:
                break;
            //assert false;
        }
        return null;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        int playerindex=-50;
        int x=-10000000;
        int y=-10000000;
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
        String direction=null;
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
        try {

            playerindex=data.getInt("playerIndex");
            JSONObject myobject=data.getJSONObject("vertexLocation");
            x=myobject.getInt("x");
            y=myobject.getInt("y");
            direction=myobject.getString("direction");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerFacade.getInstance().buildCity(playerindex,new HexLocation(x,y),convertToVertexDirection(direction, new HexLocation(x,y)),gameID);
        String response = "WHY DOES THIS EXIST!!!!!!!!!!";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

    }
    private int getGameIDfromCookie(String cookie){
        return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

    }
}
