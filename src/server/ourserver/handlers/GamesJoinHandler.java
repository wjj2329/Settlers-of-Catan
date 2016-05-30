package server.ourserver.handlers;

import client.model.ModelFacade;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import server.ourserver.ServerFacade;
import server.param.user.LoginParam;
import server.results.LoginUserResponse;
import shared.definitions.CatanColor;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * Created by williamjones on 5/26/16.
 *  Adds the player to the specified game and sets their catan.game cookie.
 *
 *
 *
 * @pre 1.The user has previously logged in to the server
 *                (i.e.,they have a valid catan.user HTTP cookie). 2.The
 *                player may join the game because 2.a They are already in
 *                the game, OR 2.b There is space in the game to add a new
 *                player 3.The specified game ID is valid 4.The specified
 *                color is
 *                valid(red,green,blue,yellow,puce,brown,white,purple,
 *                orange)
 * @post If the operation succeeds, 1. The server returns an
 *                HTTP 200 success response with "Success" in the body. 2.
 *                The player is in the game with the specified
 *                color(i.e.calls to /games/list method will show the player
 *                in the game with the chosen color). 3. The serve response
 *                includes the "Set cookie" response header setting the
 *                catan.game HTTP cookie If the operation fails, 1.The
 *                server returns an HTTP 400 error response, and the body
 *                contains an error message
 */
public class GamesJoinHandler implements HttpHandler 
{
    @Override
    public void handle(HttpExchange exchange) throws IOException 
    {
    	String cookie = exchange.getRequestHeaders().getFirst("Cookie");
    	int userID = Integer.parseInt(cookie.substring(cookie.indexOf("playerID")+14, cookie.length()-3));
    	
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
		System.out.println("This is our JSON Object: " + data.toString());
		Player newPlayer = null;
		String username = "";
		int gameid;
		String color;
		try
		{
			gameid = data.getInt("id"); //What game am I joining?
			color = data.getString("color"); //My color
			boolean success = ServerFacade.getInstance().joinGame(gameid,userID,color);
			System.out.println("Joined successfully?: " + success);
			if (!success)
			{
				System.out.println("I FAIL TO JOIN THE GAME");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().write("Failed to join".getBytes());
				data.append("FAILURE", exchange.getResponseBody());
				exchange.close();
				return;
			}
			
			String gameCookie = "catan.game=" + gameid + ";Path=/;";
			
			//Add gamecookie to response header
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Set-Cookie", gameCookie);
			
			//How you add a response: send response headers first then getresponsebody.write, you need to put something
			//in order for the clientcommunicator to work. 
			String response = "Success!";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(response.getBytes());

			exchange.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
    }
}
