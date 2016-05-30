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
 * * @param type name of move being executed
 * the player's position in the game's turn order
 * whether or not you get this piece for free
 * vertexLocation the location of the settlement
 *
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						the settlement location is open, is not on water, is connected to one of your roads
 * 						except during setup. You have the required resources(1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
 * 						the settlement cannot be placed adjacent to another settlement
 * @post You lost the required resources to build a settlement(1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
 * 						the settlement is on the map at the specified location
 *
 */
public class MovesBuildSettlementHandler implements HttpHandler
{

    private VertexLocation convertToVertexDirection(String direction, HexLocation myhexloc)
    {
        switch (direction)
        {
            case "W":
                return new VertexLocation(myhexloc,VertexDirection.West);
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
    public void handle(HttpExchange exchange) throws IOException
    {

        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
        int playerindex=-50;
        int x=-10000000;
        int y=-10000000;
        String direction=null;
        boolean freebe=false;
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
            freebe=data.getBoolean("free");
            direction=myobject.getString("direction");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerFacade.getInstance().buildSettlement(playerindex,new HexLocation(x,y),convertToVertexDirection(direction, new HexLocation(x,y)),freebe,gameID);
        String response = "WHY DOES THIS EXIST!!!!!!!!!!";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

    }
    public int getGameIDfromCookie(String cookie){
//
//    	System.out.println(cookie.indexOf("game="));
//    	System.out.println(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));
        return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

    }
}
