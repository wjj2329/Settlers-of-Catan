package server.persistence;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import server.database.DatabaseException;
import shared.game.CatanGame;
import shared.game.player.Player;


import shared.game.player.Player;

/**
 * Created by williamjones on 6/7/16.
 * IUserAccount interface.
 */
public interface IUserAccount
{
	Player validateUser(Player player);

	void addUser(Player user) throws DatabaseException, IOException;

	List<Player> getAllUsers() throws IOException;

	void setColor(Player user) throws DatabaseException;

	boolean isUserInGame(Player user);

	void addGameToGameList(CatanGame game) throws IOException, JSONException;

}
