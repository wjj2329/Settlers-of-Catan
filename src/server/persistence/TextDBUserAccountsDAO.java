package server.persistence;

import java.util.List;

import server.database.DatabaseException;
import shared.game.player.Player;


import shared.game.player.Player;

/**
 * Created by williamjones on 6/7/16.
 *
 */
public class TextDBUserAccountsDAO implements IUserAccount
{

	@Override
	public Player validateUser(Player player)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(Player user) throws DatabaseException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Player> getAllUsers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Player user) throws DatabaseException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUserInGame(Player user)
	{
		// TODO Auto-generated method stub
		return false;
	}

   
}
