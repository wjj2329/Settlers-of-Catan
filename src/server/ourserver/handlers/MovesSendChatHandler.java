package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import server.ourserver.ServerFacade;
import shared.locations.HexLocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * Created by williamjones on 5/26/16.
 * /**
 *
 * @pre None!
 * @post The chat contains your message at the end
 *
 */
public class MovesSendChatHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
        int playerindex=-1;
        String message="";
        JSONObject data = null;
		exchange.getResponseHeaders().add("Content-type", "text/html");
        try
        {
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            data = new JSONObject(result);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
			String response = "You gotta give me something to work with to send chat.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }
        try {

            playerindex=data.getInt("playerIndex");
            message=data.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
			String response = "So you are missing some info to send chat.";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.close();
        }

        try {
            ServerFacade.getInstance().sendChat(message,playerindex,gameID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = "SSSucccccesssssssssssssss (a snake said it)";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

    }
    private int getGameIDfromCookie(String cookie){
        return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

    }
}
