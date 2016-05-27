package server.ourserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by williamjones on 5/26/16.
 * * @param type name of move being executed
 * the player's position in the game's turn order
 * whether or not you get this piece for free
 * vertexLocation the location of the settlement
 *
 *
 * @pre it is your turn, the client model's status is 'Playing'
 * 						the settlement location is open, is not on water, is connected to one of your roads
 * 						except during setup. You have the required resources(1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
 * 						the settlement cannot be placed adjacent to another settlement
 * @post You lost the required resources to build a settlement(1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
 * 						the settlement is on the map at the specified location
 *
 */
public class MovesBuildSettlementHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
      String command=  httpExchange.getRequestURI().toString();
        JSONObject myobject=new JSONObject(httpExchange.getRequestBody());//gets body   //get cookie   //get type
            //he's given a ulr he parse thrrough exchange a json a commmand type and a cookie.   The Server Facade looks at the command type
        //and t
    }
}
