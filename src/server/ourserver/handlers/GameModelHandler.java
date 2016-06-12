package server.ourserver.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONException;
import server.ourserver.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by williamjones on 5/26/16.
 Returns the current state of the game in JSON format, and also includes a
 * "version" number for the client model.
 *
 *
 * @pre 1.The caller has previously logged into the server and joined a game(i.e.,they have valid
 * 						catan.user and catan.game HTTP cookies).
 * 						in the request URL, and its value is a valid integer
 * @post If the operation succeeds,
 * 						1. The server returns an HTTP 200 success response
 * 						2. The response body contains JSON data
 * 							a. The full client model JSON is returned if the caller does not provide a version
 * 								number or provide version number does not match the version on the server.
 * 							b. "true" (true in double quotes) is returned if the caller provided a version number, and the
 * 								version number matched the version number on the server
 *                		If the operation fails,
 *                		1.The server returns an HTTP 400 error response, and the body contains an error message
 */
public class GameModelHandler implements HttpHandler {
	
	
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
    	String cookie = httpExchange.getRequestHeaders().getFirst("Cookie");
    	int gameID = getGameIDfromCookie(cookie);
		//System.out.println("TIS THE GAME ID FROM COOKIE " + gameID);

        JSONObject model = null;
        try {
            model = ServerFacade.getInstance().getGameModel(gameID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(model == null){
            String response = "Could not get Game model.";
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            httpExchange.close();
            //System.out.println("Well this is embarrasing there was a problem");
            return;
        }
        httpExchange.getResponseHeaders().add("Content-type", "application/json");
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        httpExchange.getResponseBody().write(model.toString().getBytes());
       // System.out.println("YO IT'S DONE!");
        httpExchange.close();
    }
    
    
    public int getGameIDfromCookie(String cookie){
//    	
//    	System.out.println(cookie.indexOf("game="));
//    	System.out.println(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));
    	return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

    }
}
