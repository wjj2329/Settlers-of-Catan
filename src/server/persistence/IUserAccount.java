package server.persistence;

import shared.definitions.CatanColor;
import shared.game.player.Player;

/**
 * Created by williamjones on 6/7/16.
 */
public interface IUserAccount {

	public void addUser();
	public Object getAllUsers();
	public void addPlayer();
	public CatanColor getColor();
	public void setColor(Player player, CatanColor color);
}
