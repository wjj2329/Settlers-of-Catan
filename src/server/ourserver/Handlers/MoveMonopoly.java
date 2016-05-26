package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * /**
 * type name of move being executed
 *   playerIndex the player's position in the game's turn order
 *  resource the resource being taken from the others players
 *
 * @pre  it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * @post all of the other players have give you all of their resources cards of the specified type
 *
 *
 */

public class MoveMonopoly implements HttpHandler
{

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
