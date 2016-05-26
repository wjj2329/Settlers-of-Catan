package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * * @param type name of move being executed
 * the player's position in the game's turn order
 *  whether or no you get this piece for free
 * the new road's location
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						road location is open, is connected to another road owned by the player, is not on
 * 						the water, you have the required resources(1 wood, 1 brick; 1 road)
 * @post You have the required resources to build a road(1 wood, 1 brick; 1 road)
 * 						The road is on the map at the specified location
 * 						if applicable, "longest road" has been awarded to the player with the longest road
 *
 */
public class MovesBuildRoad implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
