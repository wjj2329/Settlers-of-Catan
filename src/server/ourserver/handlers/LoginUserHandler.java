package server.ourserver.handlers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
		System.out.println("I begin handling loginUser");
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
			newPlayer = ServerFacade.getInstance().logIn(newPlayer);
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
			System.out.println("In my Login User thing the username is "+username+" my Password is "+password);
			String userCookie = "catan.user=%7B%22name%22%3A%22" + username + "%22%2C%22password" +
					"%22%3A%22" + password + "%22%2C%22playerID%22%3A" + newPlayer.getPlayerID() + "%7D;Path=/;";
			// How to add cookie to response headers?
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			// set responseHeader

			// am not sure what to append or what to do with userCookie
			// we append to data but we don't do anything with it
			//data.append(userCookie, exchange.getResponseBody());
			exchange.getRequestHeaders().set("Set-cookie",userCookie);
			exchange.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}	
}
