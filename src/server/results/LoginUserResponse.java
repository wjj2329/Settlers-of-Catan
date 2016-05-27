package server.results;

import shared.game.player.Player;

/**
 * Created by Alex on 5/27/16.
 * Response for the login command on ServerFacade.
 */
public class LoginUserResponse
{
    /**
     * Whether or not it worked successfully
     */
    private boolean success;
    private String username;
    private String password;
    private Player player;

    public LoginUserResponse(Player player)
    {
        this.player = player;
        username = player.getName();
        password = player.getPassword();
    }

    public boolean isSuccessful()
    {
        return success;
    }

    public void setSuccessful(boolean success)
    {
        this.success = success;
    }
}
