package server.persistence;

import shared.game.player.Player;

<<<<<<< HEAD
=======
import shared.definitions.CatanColor;
>>>>>>> origin/master
import shared.game.player.Player;

/**
 * Created by williamjones on 6/7/16.
<<<<<<< HEAD
 *
 */
public class TextDBUserAccountsDAO implements IUserAccount
{

    @Override
    public void clearInfo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadInfo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void validateUser() {

    }

    @Override
    public void addUser() {

    }

    @Override
    public void getAllUsers() {

    }

    @Override
    public void addPlayer() {

    }

    @Override
    public void setColor() {

    }

    @Override
    public boolean isUserInGame(Player user) {
        return false;
    }
======= // this is poop
 */
public class TextDBUserAccountsDAO implements IUserAccount {

	@Override
	public void addUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CatanColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Player player, CatanColor color) {
		// TODO Auto-generated method stub
		
	}

	
>>>>>>> origin/master
}
