package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * * @param type name of move being executed
 * playerIndex the person making offer
 *  offer negative numbers mean you get those cards
 *  receiver person receiving offer
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						you have the resources you are offering
 * @post the trade is offered to the other player(stored in the server model)
 *
 */
public class MovesOfferTrade implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
