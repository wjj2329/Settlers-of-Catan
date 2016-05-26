package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * /**
 * type name of move being executed
 * playerIndex the player's position in the game's turn order
 * vertexLocation the location of the city
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 					   the city location is where you currently have a settlement,
 * 						you have the required resources (2 wheat, 3 ore; 1 city)
 * @post you lost resources required to build a city, the city is on the map at the specified
 * 						location, you got a settlement back
 *
 */

public class MovesBuildCity implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
