package server.ourserver.handlers;

import com.sun.corba.se.spi.activation.Server;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import server.ourserver.ServerFacade;
import server.param.user.LoginParam;
import server.results.LoginUserResponse;
import shared.definitions.CatanColor;
import shared.game.map.Index;
import shared.game.player.Player;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

import javax.management.relation.Role;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *  type name of move being executed
 * playerIndex the player's position in the game's turn order
 * number integer in the range 2-12 (the number you rolled)
 *
 * @pre It is your turn, the client model's status is 'Rolling'
 * @post The client model's status is now in 'Discarding' or 'Robbing' or 'Playing'
 *
 */
public class MovesRollNumberHandler implements HttpHandler {
	
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
    	String cookie = httpExchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
    	JSONObject data = null;
		httpExchange.getResponseHeaders().add("Content-type", "text/html");
		try
		{
			Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
			data = new JSONObject(result);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			String response = "You gotta give me something to work with to roll.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		}
		//System.out.println("This is our JSON Object: " + data.toString());
		int rolledNumber = -1;
		try
		{
			rolledNumber = data.getInt("number");
			if (rolledNumber < 2 || rolledNumber > 12)
			{
				//This is how you add a response object (most things need one)
				String response = "Roll number not valid";
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
				httpExchange.getResponseBody().write(response.getBytes());
				//System.out.println("Something bad happened...");
				httpExchange.close();
				return;
			}			
			
			ServerFacade.getInstance().rollNumber(rolledNumber, gameID);
			String response = "You just rolled! :D";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			//System.out.println("So it worked! :D ");
			httpExchange.close();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			String response = "Looks like your are missing something.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
		}
    }
    
    public int getGameIDfromCookie(String cookie){
    	return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));
    }
}
