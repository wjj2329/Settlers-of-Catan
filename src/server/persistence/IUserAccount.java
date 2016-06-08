package server.persistence;

import shared.game.player.Player;

/**
 * Created by williamjones on 6/7/16.
 * IUserAccount interface.
 */
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
}
