package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 Creates a new game on the server.
 *
 Returns information about all of the current games on the server.
 *
 * @pre None! :D
 * @post If the operation succeeds, 1.The server returns an
 *                HTTP 200 success response. 2.The body contains a JSON
 *                array containing a list of objects that contain
 *                information about the server’s games @post If the operation
 *                fails, 1.The server returns an HTTP 400 error response,
 *                and the body contains an error message
 *
 */

public class GamesListHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
