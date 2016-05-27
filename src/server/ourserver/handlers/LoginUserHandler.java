package server.ourserver.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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
		// TODO Auto-generated method stub
		
	}	
}
