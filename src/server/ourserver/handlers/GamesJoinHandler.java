package server.ourserver.handlers;

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
		System.out.println("I begin handling joinGame");
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
		System.out.println("This is our JSON Object: " + data.toString());
		LoginParam loginParam = null;
		Player newPlayer = null;
		String username;
		String password;
		try
		{
			username = data.getString("username");
			password = data.getString("password");
			loginParam = new LoginParam(username, password);
			newPlayer = new Player(username, CatanColor.PUCE, new Index(1));
			newPlayer.setPassword(password);
			newPlayer = ServerFacade.getInstance().logIn(newPlayer);
			if (newPlayer == null)
			{
				//This is how you add a response object (most things need one)
				String response = "Failed to login - invalid username or password";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().write(response.getBytes());
				// serialize with FAILED message
				System.out.println("User does not exist!");
				data.append("FAILURE", exchange.getResponseBody());
				exchange.close();
				return;
			}
			System.out.println("User exists!");
			LoginUserResponse loginUserResponse = new LoginUserResponse(newPlayer);
			System.out.println("In my Login User thing the username is "+username+" my Password is "+password);
			String gameCookie = "catan.game=" + ";Path=/;";
			// How to add cookie to response headers? 
			// This how bro~  only needed in login(add usercookie) and joingame(add gamecookie)
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Set-Cookie", gameCookie);
			
			//How you add a response: send response headers first then getresponsebody.write, you need to put something
			//in order for the clientcommunicator to work. 
			String response = "Success! :D";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(response.getBytes());

			// am not sure what to append or what to do with userCookie
			// we append to data but we don't do anything with it
			//data.append(userCookie, exchange.getResponseBody());
			exchange.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
    }
}
