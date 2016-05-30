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
        int playerindex=-1;
        String message="";
        JSONObject data = null;
        try
        {
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            data = new JSONObject(result);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try {

            playerindex=data.getInt("playerIndex");
            message=data.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerFacade.getInstance().sendChat(message,playerindex);
        String response = "WHY DOES THIS EXIST!!!!!!!!!!";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();

    }
}
