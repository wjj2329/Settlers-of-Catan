package server.ourserver.Handlers;

/**
 * Created by williamjones on 5/26/16.
 *  Adds the player to the specified game and sets their catan.game cookie.
 *
 *
 *
 * @pre 1.The user has previously logged in to the server
 *                (i.e.,they have a valid catan.user HTTP cookie). 2.The
 *                player may join the game because 2.a They are already in
 *                the game, OR 2.b There is space in the game to add a new
 *                player 3.The specified game ID is valid 4.The specified
 *                color is
 *                valid(red,green,blue,yellow,puce,brown,white,purple,
 *                orange)
 * @post If the operation succeeds, 1. The server returns an
 *                HTTP 200 success response with "Success" in the body. 2.
 *                The player is in the game with the specified
 *                color(i.e.calls to /games/list method will show the player
 *                in the game with the chosen color). 3. The serve response
 *                includes the "Set cookie" response header setting the
 *                catan.game HTTP cookie If the operation fails, 1.The
 *                server returns an HTTP 400 error response, and the body
 *                contains an error message
 */
public class GamesJoin {
}
