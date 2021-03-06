package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 *  Returns a list of supported AI player types
 *
 * @pre NONE! :D
 * @post If the operation succeeds,
 * 						1. The server returns an HTTP 200 success response
 * 						2. The body contains a JSON string array enumerating the different types of AI players. These are the values
 * 							that may be passed to the /game/addAI method.
 */
public class GameListAiHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
