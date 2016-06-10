package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.ourserver.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONArray;

/**
 * Created by williamjones on 5/26/16.
 Creates a new game on the server.
 *
 Returns information about all of the current games on the server.
 *
 *
 * None! :D
 *  If the operation succeeds, 1.The server returns an
 *                HTTP 200 success response. 2.The body contains a JSON
 *                array containing a list of objects that contain
 *                information about the server�s games @post If the operation
 *                fails, 1.The server returns an HTTP 400 error response,
 *                and the body contains an error message
 *
 */

public class GamesListHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
       	JSONArray games = ServerFacade.getInstance().getGameList();
        
        if(games == null)
        {
            String response = "Could not get game list";
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            return;
        }
        httpExchange.getResponseHeaders().add("Content-type", "application/json");
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        httpExchange.getResponseBody().write(games.toString().getBytes());
        httpExchange.close();
    }
}