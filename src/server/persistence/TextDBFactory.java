package server.persistence;

/**
 * Created by williamjones on 6/7/16.
 */
public class TextDBFactory implements IFactory
{
    TextDBGameManagerDAO mymanager=new TextDBGameManagerDAO();
    TextDBUserAccountsDAO textDBUserAccountsDAO=new TextDBUserAccountsDAO();
    
	@Override
	public IGameManager getGameManager() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IUserAccount getUserAccount() {
		// TODO Auto-generated method stub
		return null;
	}

}
