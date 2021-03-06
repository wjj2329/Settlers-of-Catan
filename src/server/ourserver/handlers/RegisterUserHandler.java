package server.ourserver.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.database.DatabaseException;
import server.ourserver.ServerFacade;
import server.param.user.LoginParam;
import server.results.LoginUserResponse;
import shared.definitions.CatanColor;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * This method does two things: 1) Creates a new user account 2) Logs the
 * caller in to the server as the new user, and sets their catan.userHTTP
 * cookie
 *
 * username:
 *            the player's username
 *  password:
 *            the player's password
 *
 * @pre username is not null password is not null The
 *                specified username is not already in use
 * @post If there is no existing user with the specified
 *                username, 1. A new user account has been created with the
 *                specified username and password. 2. The server returns an
 *                HTTP 200 success response with "Success" in the body. 3.
 *                The HTTP response headers set the catan.user cookie to
 *                contain the identity of the logged in player. The cookie
 *                uses "Path=/", and its value contains a url encoded
 *                JSONobject of the following form:
 *                {"name":STRING,"password":STRING,"playerID":INTEGER}. If
 *                there is already an existing user with the specified name,
 *                or operation fails for any reason 1. The server returns
 *                and HTTP 400 error response, and the body contains an
 *                error message.
 */
public class RegisterUserHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		//System.out.println("I begin handling loginUser");
		//System.out.println("Exchange: " + exchange.getRequestBody().toString());
		JSONObject data = null;
		try
		{
			/*String result = CharStreams.toString(new InputStreamReader(
					exchange.getRequestBody(), Charsets.UTF_8));*/
			Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
			data = new JSONObject(result);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
	        exchange.getResponseHeaders().add("Content-type", "text/html");
			String response = "Why do you give me empty data? D:";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
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
			newPlayer = new Player(username, CatanColor.PUCE, new Index(1));
			newPlayer.setPassword(password);
			ServerFacade.getInstance().register(username, password);
			if (newPlayer == null)
			{
				//This is how you add a response object (most things need one)
		        exchange.getResponseHeaders().add("Content-type", "text/html");
				String response = "Failed to register. Sad day.";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				exchange.getResponseBody().write(response.getBytes());
				// serialize with FAILED message
				//System.out.println("User does not exist!");
				data.append("FAILURE", exchange.getResponseBody());
				exchange.close();
				return;
			}
			//System.out.println("User exists!");
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
			String response = "Information is missing yo. Rethink your life decisions.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}

}

