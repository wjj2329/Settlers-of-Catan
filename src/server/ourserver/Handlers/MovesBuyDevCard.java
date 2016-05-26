package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 * playerIndex the player's position in the game's turn order
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 					you have the required resources( 1 ore, 1 wheat, 1 sheep) there are dev cards left in the deck
 * @post you have a new card! if monument it is added to old dev card
 * 						if non monument card it is added to new dev card hand
 *
 *
 */
public class MovesBuyDevCard implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
