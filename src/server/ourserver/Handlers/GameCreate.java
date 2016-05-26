package server.ourserver.Handlers;

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

public class GameCreate
{
}
