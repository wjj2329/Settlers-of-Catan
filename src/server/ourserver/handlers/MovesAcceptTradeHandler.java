package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 /**
 *type name of move being executed
 * playerIndex the player's position in the game's turn order
 *  willAccept whether or not you accept the trade offered
 *
 * @preYou have been offered a domestic trade
 * 						To accept the offered trade, you have the required resources
 * @post If you accepted, you and the player who offered swap the specified resources
 * 						If you declined no resources are exchanged
 * 						The trade offer is remove
 *
 */
public class MovesAcceptTradeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
