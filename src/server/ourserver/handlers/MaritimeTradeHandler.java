package server.ourserver.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
/**
 *  type name of move being executed
 * playerIndex the player's position in the game's turn order
 *  ratio integer(2,3, or 4)
 *  inputResource what you are giving
 *  outputResource what you are getting
 *
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						you have the resources you are giving, for ratios less than 4, you
 * 						have the correct port for the trade
 * @post the trade has been executed ( offered resources are in the bank, and the requested resource
 * 						has been received)
 *
 *
 */

public class MaritimeTradeHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		// TODO Auto-generated method stub
		
	}	
}
