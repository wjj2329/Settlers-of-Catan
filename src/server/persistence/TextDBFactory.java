package server.persistence;

/**
 * Created by williamjones on 6/7/16.
 */
public class TextDBFactory implements IFactory
{
    TextDBGameManagerDAO mymanager=new TextDBGameManagerDAO();
    TextDBUserAccountsDAO textDBUserAccountsDAO=new TextDBUserAccountsDAO();

}
