package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import shared.game.Card;
import shared.game.ResourceList;
import shared.locations.HexLocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 *  playerIndex the player's position in the game's turn order
 * discardedCards the cards you are discarding
 *
 * @pre The status of the client model is 'Discarding'
 * 						You have over 7 cards, You have the cards you're choosing to discard
 * @post You gave up the specified resources,
 * 						If you're the last one to discard, the client model status changes to 'Robbing'
 *
 */
public class MovesDiscardCardHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
        int playerindex=-50;
        int brick=-10000000;
        int sheep=-10000000;
        int wheat=-10000000;
        int wood=-1000000;
        int ore=-100000;
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
			String response = "You gotta give me something to work with if you wanna discard cards.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }
        try {

            playerindex=data.getInt("playerIndex");
            JSONObject myobject=data.getJSONObject("discardedCards");
            brick=myobject.getInt("brick");
            wheat=myobject.getInt("wheat");
            wood=myobject.getInt("wood");
            ore=myobject.getInt("ore");
            sheep=myobject.getInt("sheep");
        } catch (JSONException e) {
            e.printStackTrace();
			String response = "So you are missing some info to discard cards.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }

        ServerFacade.getInstance().discardCards(playerindex, new ResourceList( brick, ore, sheep, wheat, wood),gameID);
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
