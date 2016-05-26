package server.ourserver.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 *  type name of move being executed
 * playerIndex the player's position in the game's turn order
 *  resource1 first resource you want to receive
 * resource2 second resource you want to receive
 *
 * @pre  it is your turn, the client model's status is 'Playing'
 * 						you have the card you want to play in old dev card hand,
 * 						two specified resources are in the bank
 * @post you gained to specified resources
 *
 *
 */
public class YearOfPlentyHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		// TODO Auto-generated method stub
		
	}	
}
