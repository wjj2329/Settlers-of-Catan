package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * /**
 * type name of move being executed
 * playerIndex the player's position in the game's turn order
 * location new robber location
 *  victimIndex the player you are robbing or -1 if you are not robbing anyone
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * 						you have not yet played a non-monument dev card this turn
 * 						if robber is not being kept in the same location, if player being robbed they have the resource cards
 * @post robber is in the new location, player being robbed gave you a resource
 * 						card, "largest army" awarded to the player who has played the most soldier card, no allowed to play other deelopment cards
 * 						during this turn
 *
 *
 * Created by williamjones on 5/26/16.
 */
public class MovesSoldierHandler  implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {

    }
}
