package server.persistence;
import org.json.JSONException;
import server.database.DatabaseException;
import server.ourserver.commands.ICommand;
import shared.game.player.Player;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by williamjones on 6/8/16.
 * It is now spelled correctly
 * :)
 */
public class PersistenceManager
{
    private  static PersistenceManager singleton;

    private ArrayList<ICommand> mycommands=new ArrayList<>();

    private IFactory myfactory=new TextDBFactory();


    public PersistenceManager() throws IOException
    {

    }
    public static PersistenceManager getSingleton() throws IOException {
        if(singleton==null)
        {
            singleton =new PersistenceManager();
        }
        return singleton;
    }
    public void getmyfactory (IFactory myfactory)
    {
        this.myfactory=myfactory;
    }

    public  void addcommandinfo(ICommand commandObject) throws IOException, JSONException {
        mycommands.add(commandObject);
        myfactory.getGameManager().addCommand(commandObject);
        checkfor10();
        myfactory.getGameManager().getCommands();
    }

    public void addPlayerInfo(Player user) throws DatabaseException, IOException
    {
        myfactory.getUserAccount().addUser(user);
    }

    private void checkfor10()
    {
        if(mycommands.size()==10)
        {
            //clear database serialize model to store in database and clear list.
            mycommands.clear();
            myfactory.getGameManager().clearInfo();
            myfactory.getGameManager().loadInfo();
        }
    }



}
