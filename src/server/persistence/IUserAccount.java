package server.persistence;

<<<<<<< HEAD
=======
import shared.definitions.CatanColor;
>>>>>>> origin/master
import shared.game.player.Player;

/**
 * Created by williamjones on 6/7/16.
 * IUserAccount interface.
 */
<<<<<<< HEAD
public interface IUserAccount
{
	
	public void clearInfo();
	
	public void loadInfo();

	void validateUser();

	void addUser();

	void getAllUsers();

	void addPlayer();

	void setColor();

	boolean isUserInGame(Player user);
=======
public interface IUserAccount {

	public void addUser();
	public Object getAllUsers();
	public void addPlayer();
	public CatanColor getColor();
	public void setColor(Player player, CatanColor color);
>>>>>>> origin/master
}
