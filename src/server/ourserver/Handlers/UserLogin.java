package server.ourserver.handlers;

/**
 * Created by williamjones on 5/26/16.
 * /** Logs the caller in to the server, and sets their catan.userHTTP cookie.
 * The passed-in username and password may correspond to the credentials of
 * any registered user.
 *
 *
 * @pre username is not null, password is not null
 * @post if username and password is valid: 1. The server
 *                returns an HTTP 200 success response with "Success" in the
 *                body. 2. the HTTP response headers set the catan.user
 *                cookie to contain the identity of the logged-in player.
 *                Cookie uses "Path=/", and its value contains a url-encoded
 *                JSON object in the form:
 *                {"name":STRING,"password":STRING,"playerID":INTEGER}. If
 *                username and password are not valid or operation fails for
 *                any reason 1. The server returns an HTTP 400 error
 *                response, and the body contains an error message.
 *
 */

public class UserLogin
{

}
