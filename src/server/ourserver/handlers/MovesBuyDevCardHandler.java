package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.ServerFacade;
import server.ourserver.commands.BuyDevCardCommand;
import shared.game.ResourceList;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 * playerIndex the player's position in the game's turn order
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 					you have the required resources( 1 ore, 1 wheat, 1 sheep) there are dev cards left in the deck
 * @post you have a new card! if monument it is added to old dev card
 * 						if non monument card it is added to new dev card hand
 *
 *
 */
public class MovesBuyDevCardHandler implements HttpHandler 
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
    	int playerindex = -1;
    	int gameid = -1;
        JSONObject data = null;
        try
        {
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            data = new JSONObject(result);
            s.close();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try 
        {
            playerindex = data.getInt("playerIndex");
            gameid = data.getInt("id");
            JSONObject myobject=data.getJSONObject("discardedCards");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        BuyDevCardCommand buyCommand = new BuyDevCardCommand(playerindex, gameid);
        buyCommand.execute();
        String response = "Success";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
