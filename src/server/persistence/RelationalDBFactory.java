package server.persistence;

import server.database.Database;
import server.database.DatabaseException;

/**
 * Created by williamjones on 6/7/16.
 */
public class RelationalDBFactory implements IFactory
{
    private RelationalDBGameManagerDAO relationalDBGameManagerDAO;
    private RelationalDBUserAccountsDAO relationalDBUserAccountsDAO;
	Database db = new Database();
	
    public RelationalDBFactory()
    {
    	try
		{
			db.initialize();
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
    	db.createTables();
    	 
    	relationalDBGameManagerDAO = new RelationalDBGameManagerDAO(db);
    	relationalDBUserAccountsDAO = new RelationalDBUserAccountsDAO(db);
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
