package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * /**
 * type name of move being executed
 *playerIndex the player's position in the game's turn order
 *  resource1 first resource you want to receive
 *  resource2 second resource you want to receive
 *
 * @pre  it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * 						two specified recources are in the bank
 * @post you gained to specifed resources
 *
 *
 */

public class MovesYearOfPlentyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
