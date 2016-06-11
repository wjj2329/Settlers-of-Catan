package server.ourserver.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Created by williamjones on 5/26/16.
 * Creates a new game on the server.
 *
 * @pre name != null, randomeTiles, randomNumbers, and
 *                randomPorts contain valid boolean values
 * @post If the operation succeeds, 1.A new game with the
 *                specified properties has been created 2.The server returns
 *                an HTTP 200 success response. 3.The body contains a
 *                JSONobject describing the newly created game. If the
 *                operation fails, 1.The server returns an HTTP 400 error
 *                response, and the body contains an error message
 */

public class GameCreateHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        //System.out.println("Exchange for Game create handler is now coming alive" + exchange.getRequestBody().toString());
        JSONObject data = null;
        String name=null;
        boolean randomHexes=false;
        boolean randomPorts=false;
        boolean randomHexValues=false;
        try
        {
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            data = new JSONObject(result);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            exchange.getResponseHeaders().add("Content-type", "text/html");
			String response = "Just because your soul is empty doesn't mean you have to give me empty data.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }
        try {
            name=data.getString("name");
            randomHexes=data.getBoolean("randomTiles");
             randomPorts= data.getBoolean("randomPorts");
            randomHexValues=data.getBoolean("randomNumbers");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e);
			exchange.getResponseHeaders().add("Content-type", "text/html");
			String response = "Why do you not meet my information needs? Give me the correct info.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }
        try {
            ServerFacade.getInstance().createGame(name,randomHexes,randomPorts,randomHexValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        exchange.getResponseHeaders().add("Content-type", "text/html");
        String response = "Success! :D   YEA YEA YEA";
        //System.out.println("I COME HERE TO NOW SEND MY A OKAY TO GO WITH THE CREATE GAME HANDLER");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
