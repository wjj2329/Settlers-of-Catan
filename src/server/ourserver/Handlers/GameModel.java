package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 Returns the current state of the game in JSON format, and also includes a
 * "version" number for the client model.
 *
 *
 * @pre 1.The caller has previously logged into the server and joined a game(i.e.,they have valid
 * 						catan.user and catan.game HTTP cookies).
 * 						in the request URL, and its value is a valid integer
 * @post If the operation succeeds,
 * 						1. The server returns an HTTP 200 success response
 * 						2. The response body contains JSON data
 * 							a. The full client model JSON is returned if the caller does not provide a version
 * 								number or provide version number does not match the version on the server.
 * 							b. "true" (true in double quotes) is returned if the caller provided a version number, and the
 * 								version number matched the version number on the server
 *                		If the operation fails,
 *                		1.The server returns an HTTP 400 error response, and the body contains an error message
 */
public class GameModel implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
