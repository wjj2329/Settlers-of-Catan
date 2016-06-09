package server.persistence;

import java.io.IOException;

/**
 * Created by williamjones on 6/7/16.
 */
public class TextDBFactory implements IFactory
{
    TextDBGameManagerDAO mymanager=new TextDBGameManagerDAO();
    TextDBUserAccountsDAO textDBUserAccountsDAO=new TextDBUserAccountsDAO();

	public TextDBFactory() throws IOException {
	}

	@Override
	public IGameManager getGameManager() {
		// TODO Auto-generated method stub
		return mymanager;
	}
	@Override
	public IUserAccount getUserAccount() {
		// TODO Auto-generated method stub
		return textDBUserAccountsDAO;
	}

}
