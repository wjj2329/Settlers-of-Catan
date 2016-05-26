package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 **
 *  type name of move being executed
 * playerIndex the player's position in the game's turn order
 *  spot1 edge location
 *  spot2 edge location
 *
 * @pre  it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * 						first road location is connected to one of your road, second road location is connected to one of your roads
 * 						or the first road location, neither road location is on the water, you have atleast
 * 						two unused roads
 * @post you have 2 fewer unused roads, 2 new roads appear on the map at the specified
 * 						locations, "longest road" has been awarded to the correct user
 *
 */

public class MovesRoadBuildingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
