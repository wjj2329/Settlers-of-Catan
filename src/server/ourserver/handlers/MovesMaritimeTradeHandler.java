package server.ourserver.handlers;

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
 * @author Alex
 *
 * MaritimeTradeHandler! This class helps the MaritimeTrade work with our server!
 *
 * type name of move being executed
 *  playerIndex the player's position in the game's turn order
 *  ratio integer(2,3, or 4)
 *  inputResource what you are giving
 * outputResource what you are getting
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
public class MovesMaritimeTradeHandler implements HttpHandler
{
    /**
     * Overriding the handler function. Handler calls the requisite method in the ServerFacade,
     *      which in turn uses the MaritimeTradeCommand to update the server model.
     *
     * @param httpExchange: We retrieve the requisite JSON data from this parameter.
     * @throws IOException: If there was an error parsing the exchange.
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        String cookie = httpExchange.getRequestHeaders().getFirst("Cookie");
        int gameID = getGameIDfromCookie(cookie);
        JSONObject data;
		httpExchange.getResponseHeaders().add("Content-type", "text/html");
        try
        {
            Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            data = new JSONObject(result);
            /* This player index is NOT the player ID. We are not making that mistake again.
                ALWAYS REMEMBER
                ALWAYS
                :O
             */
            int playerIndex_thisIs_NOT_AnID = data.getInt("playerIndex");
            int ratio = data.getInt("ratio");
            String inputResource = data.getString("inputResource");
            String outputResource = data.getString("outputResource"); // get, give - trying to switch them
            ServerFacade.getInstance().maritimeTrade(outputResource, inputResource, playerIndex_thisIs_NOT_AnID, ratio,
                    gameID);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            httpExchange.getResponseBody().write(DEFAULT_RESPONSE.getBytes());
            httpExchange.close();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
			String response = "You gotta give me something to work with if you wanna do maritime trade.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
        }
        // Don't delete this
        catch (Exception e)
        {
            e.printStackTrace();
			String response = "Something just isn't working.";
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			httpExchange.getResponseBody().write(response.getBytes());
			httpExchange.close();
        }
    }

    /**
     * This function is necessary because we need to update the information in the correct game.
     * @param cookie: the cookie used to get the correct game data
     */
    private int getGameIDfromCookie(String cookie)
    {
        return Integer.parseInt(cookie.substring(cookie.indexOf("game=")+5, cookie.length()));

    }

    /**
     * Static data members which we need for various parts throughout the code.
     * Please ignore the actual contents. :D
     */
    private static final String DEFAULT_RESPONSE = "Yo mama is so fat, she had to go to SeaWorld to get baptized.";
}
