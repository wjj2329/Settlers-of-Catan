package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 * playerIndex the player's position in the game's turn order
 * number integer in the range 2-12 (the number you rolled)
 *
 * @pre It is your turn, the client model's status is 'Rolling'
 * @post The client model's status is now in 'Discarding' or 'Robbing' or 'Playing'
 *
 */
public class MovesRollNumberHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
