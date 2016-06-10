package server.persistence;
import org.json.JSONException;
import server.ourserver.commands.ICommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by williamjones on 6/8/16.
 */
public class  PersistanceManager
{
    private  static PersistanceManager singleton;

    private ArrayList<ICommand> mycommands=new ArrayList<>();

    private IFactory myfactory=new TextDBFactory();//needs to get loaded from


    public PersistanceManager() throws IOException
    {

    }
    public static PersistanceManager getSingleton() throws IOException {
        if(singleton==null)
        {
            singleton =new PersistanceManager();
        }
        return singleton;
    }
    public void setmyfactory (IFactory myfactory)
    {
        this.myfactory=myfactory;
    }

    public  void addcommandinfo(ICommand commandObject) throws IOException, JSONException {
        mycommands.add(commandObject);
        myfactory.getGameManager().addCommand(commandObject);
        checkfor10();
        myfactory.getGameManager().getCommands();
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
