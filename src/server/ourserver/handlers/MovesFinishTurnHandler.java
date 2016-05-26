package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 /**
 * type name of move being executed
 *  playerIndex the player's position in the game's turn order
 *
 * @pre it is your turn, the client model's status is 'Playing'
 *
 * @post the cards in your new dev card hand have been transferred to your
 * 						old dev hand, it is the next player's turn.
 *
 *
 */

public class MovesFinishTurnHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
