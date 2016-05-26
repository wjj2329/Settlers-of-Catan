package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 *
 * Adds an AI player to the current game. Must login and join a game before calling this method.
 *
 * @pre 1. The caller has previously logged in to the server and joined a game (i.e., they have valid
 * 						  catan.user and catan.game HTTP cookies).
 * 					   2. There is space in the game for another player(i.e., the game is not "full").
 * 					   3. The specified "AIType" is valid(i.e., one of the values returned by the /game/listAImethod).
 * @post If the operation succeeds,
 * 						1. The server returns an HTTP 200 success response with "Success" in the body.
 * 						2. A new AI player of the specified type has been added to the current game. The server selected a name
 * 							and color for the player.
 *                		If the operation fails,
 *                		1.The server returns an HTTP 400 error response, and the body contains an error message
 *
 */


public class GameAddAi implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
