package server.ourserver.Handlers;

/**
 * Created by williamjones on 5/26/16.
 * /**
 * This method does two things: 1) Creates a new user account 2) Logs the
 * caller in to the server as the new user, and sets their catan.userHTTP
 * cookie
 *
 * @pre username is not null password is not null The
 *                specified username is not already in use
 * @post If there is no existing user with the specified
 *                username, 1. A new user account has been created with the
 *                specified username and password. 2. The server returns an
 *                HTTP 200 success response with "Success" in the body. 3.
 *                The HTTP response headers set the catan.user cookie to
 *                contain the identity of the logged in player. The cookie
 *                uses "Path=/", and its value contains a url encoded
 *                JSONobject of the following form:
 *                {"name":STRING,"password":STRING,"playerID":INTEGER}. If
 *                there is already an existing user with the specified name,
 *                or operation fails for any reason 1. The server returns
 *                and HTTP 400 error response, and the body contains an
 *                error message.
 */

public class UserRegister {
}
