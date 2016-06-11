package server.persistence;

/**
 * Created by williamjones on 6/7/16.
 */
public class RelationalDBFactory implements IFactory
{
    private RelationalDBGameManagerDAO relationalDBGameManagerDAO;
    private RelationalDBUserAccountsDAO relationalDBUserAccountsDAO;
	
    public RelationalDBFactory()
    {
    	relationalDBGameManagerDAO = new RelationalDBGameManagerDAO(null);
    	relationalDBUserAccountsDAO = new RelationalDBUserAccountsDAO(null);
    }
    @Override
	public IGameManager getGameManager() 
    {
		return relationalDBGameManagerDAO;
	}
	@Override
	public IUserAccount getUserAccount() 
	{
		return relationalDBUserAccountsDAO;
	}

}
