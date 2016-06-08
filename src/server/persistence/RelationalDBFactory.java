package server.persistence;

/**
 * Created by williamjones on 6/7/16.
 */
public class RelationalDBFactory implements IFactory
{
    RelationalDBGameManagerDAO relationalDBGameManagerDAO=new RelationalDBGameManagerDAO();
    RelationalDBUserAccountsDAO relationalDBUserAccountsDAO=new RelationalDBUserAccountsDAO();

}
