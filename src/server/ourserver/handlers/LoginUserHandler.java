package server.ourserver.handlers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import server.param.user.LoginParam;
import server.results.LoginUserResponse;
import shared.definitions.CatanColor;
import shared.game.map.Index;
import shared.game.player.Player;

/** Logs the caller in to the server, and sets their catan.userHTTP cookie.
 * The passed-in username and password may correspond to the credentials of
 * any registered user.
 *
 *  username:
 *            the player's username
 *  password:
 *            the player's password
 *
 * @pre username is not null, password is not null
 * @post if username and password is valid: 1. The server
 *                returns an HTTP 200 success response with "Success" in the
 *                body. 2. the HTTP response headers set the catan.user
 *                cookie to contain the identity of the logged-in player.
 *                Cookie uses "Path=/", and its value contains a url-encoded
 *                JSON object in the form:
 *                {"name":STRING,"password":STRING,"playerID":INTEGER}. If
 *                username and password are not valid or operation fails for
 *                any reason 1. The server returns an HTTP 400 error
 *                response, and the body contains an error message.
 *
 */
public class LoginUserHandler implements HttpHandler
{
	private static int idTracker = 1;
	/**
	 * Handler:
	 * 1. Deserializes what class is received (input object)
	 * 2. Calls Facade; gives login info
	 * 3. Method in facade needs to return T or F; whether or not was successful - collect result
	 * 4. serialize it - make a cookie - and send it back.
	 * @param exchange: exchange that the ServerProxy is giving to it
	 * @throws IOException: if there was an error deserializing
     */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		//System.out.println("I begin handling loginUser");
		//System.out.println("Exchange: " + exchange.getRequestBody().toString());
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

	        exchange.getResponseHeaders().add("Content-type", "text/html");
			String response = "Why do you give me empty data?";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
			return;
		}
		//System.out.println("This is our JSON Object: " + data.toString());
		LoginParam loginParam = null;
		Player newPlayer = null;
		String username;
		String password;
		try
		{
			username = data.getString("username");
			password = data.getString("password");
			loginParam = new LoginParam(username, password);
			// This might be necessary to create a new playerID for each new player who logs in.
			// However, possibly not. We might want to keep that ID for when they register, actually,
			// in which case we need a method for remembering them.
			newPlayer = new Player(username, CatanColor.PUCE, new Index(1));
			newPlayer.setPassword(password);
			//idTracker++;
			//System.out.println("ID Tracker is now at " + idTracker);
			newPlayer = ServerFacade.getInstance().logIn(username, password);
			if (newPlayer == null)
			{
				//This is how you add a response object (most things need one)

		        exchange.getResponseHeaders().add("Content-type", "text/html");
				String response = "Failed to login - invalid username or password";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().write(response.getBytes());
				// serialize with FAILED message
				//System.out.println("User does not exist!");
				data.append("FAILURE", exchange.getResponseBody());
				exchange.close();
				return;
			}
			//System.out.println("User exists!");
			LoginUserResponse loginUserResponse = new LoginUserResponse(newPlayer);
			//System.out.println("In my Login User thing the username is "+username+" my Password is "+password);
			String userCookie = "catan.user=%7B%22name%22%3A%22" + username + "%22%2C%22password" +
					"%22%3A%22" + password + "%22%2C%22playerID%22%3A" + newPlayer.getPlayerID().getNumber() + "%7D;Path=/;";
			// How to add cookie to response headers? 
			// This how bro~  only needed in login(add usercookie) and joingame(add gamecookie)
			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders.set("Set-Cookie", userCookie);
			
			//How you add a response: send response headers first then getresponsebody.write, you need to put something
			//in order for the clientcommunicator to work. 

	        exchange.getResponseHeaders().add("Content-type", "text/html");
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
			exchange.getResponseHeaders().add("Content-type", "text/html");
			String response = "Information is missing. Rethink your life decisions.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
			return;
		}
	}	
}
