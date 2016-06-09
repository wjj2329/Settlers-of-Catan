package server.persistence;

import java.io.IOException;

/**
 * Created by williamjones on 6/7/16.
 */
public class TextDBFactory implements IFactory
{
    private TextDBGameManagerDAO textDBGameManagerDAO;
    private TextDBUserAccountsDAO textDBUserAccountsDAO;

	public TextDBFactory()
	{
		try
		{
			textDBGameManagerDAO = new TextDBGameManagerDAO();
			textDBUserAccountsDAO = new TextDBUserAccountsDAO();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public IGameManager getGameManager() 
	{
		return textDBGameManagerDAO;
	}
	
	@Override
	public IUserAccount getUserAccount() 
	{
		return textDBUserAccountsDAO;
	}

}
