package server.persistence;

/**
 * Created by williamjones on 6/7/16.
 */
public class RelationalDBFactory implements IFactory
{
    RelationalDBGameManagerDAO relationalDBGameManagerDAO=new RelationalDBGameManagerDAO();
    RelationalDBUserAccountsDAO relationalDBUserAccountsDAO=new RelationalDBUserAccountsDAO();
	
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
