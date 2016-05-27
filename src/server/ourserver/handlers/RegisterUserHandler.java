package server.ourserver.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

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
		System.out.println("I begin handling Resigster");
		System.out.println("Exchange: " + exchange.getRequestBody().toString());
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
			newPlayer = new Player(username, CatanColor.PUCE, new Index(-1));
			newPlayer.setPassword(password);
			ServerFacade.getInstance().register(username,password);
			if (newPlayer == null)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				// serialize with FAILED message
				System.out.println("User does not exist!");
				data.append("FAILURE", exchange.getResponseBody());
				return;
			}
			System.out.println("User exists!");
			LoginUserResponse loginUserResponse = new LoginUserResponse(newPlayer);
			String userCookie = "catan.user=%7B%22name%22%3A%22" + username + "%22%2C%22password" +
					"%22%3A%22" + password + "%22%2C%22playerID%22%3A" + newPlayer.getPlayerID() + "%7D;Path=/";
			// How to add cookie to response headers?
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			// am not sure what to append or what to do with userCookie
			data.append(userCookie, exchange.getResponseBody());
			exchange.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

}

