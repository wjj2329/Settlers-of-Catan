package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 *  playerIndex the player's position in the game's turn order
 * discardedCards the cards you are discarding
 *
 * @pre The status of the client model is 'Discarding'
 * 						You have over 7 cards, You have the cards you're choosing to discard
 * @post You gave up the specified resources,
 * 						If you're the last one to discard, the client model status changes to 'Robbing'
 *
 */
public class MovesDiscardCardHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
