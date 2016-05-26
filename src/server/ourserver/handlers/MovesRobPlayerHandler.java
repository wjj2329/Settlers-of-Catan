package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 * playerIndex the player you are robbing or -1 if you are not robbing anyone
 *  location new location of robber
 *  victimIndex the player you are robbing or -1 if you are not robbing anyone
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 					  robber is not being kept in the same location, if a player
 * @postthe robber is in the new location, the player being robbed(if any) gave one
 * 					 one of his resource cards (if randomly selected)
 *
 *
 */

public class MovesRobPlayerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
