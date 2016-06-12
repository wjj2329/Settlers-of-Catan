package server.persistence;
import org.json.JSONException;
import org.json.JSONObject;
import server.database.DatabaseException;
import server.ourserver.commands.ICommand;
import shared.game.CatanGame;
import shared.game.player.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by williamjones on 6/8/16.
 * It is now spelled correctly
 * :)
 */
public class PersistenceManager
{
    private  static PersistenceManager singleton;

    private ArrayList<ICommand> mycommands=new ArrayList<>();

    private IFactory myfactory=null;//needs to get loaded from


    public PersistenceManager() throws IOException, JSONException {


    }
    public static PersistenceManager getSingleton() throws IOException, JSONException
    {
        if(singleton==null)
        {
            singleton =new PersistenceManager();
        }
        return singleton;
    }
    public void setmyfactory (IFactory myfactory)
    {
        this.myfactory=myfactory;
    }

    public  void addcommandinfo(ICommand commandObject) throws IOException, JSONException {
         myfactory.getGameManager().addCommand(commandObject,commandObject.getGameid());
        checkfor10(commandObject.getGameid());
        //myfactory.getGameManager().getCommands(commandObject.getGameid());
    }

    public void addPlayerInfo(Player user) throws DatabaseException, IOException
    {
        //System.out.println("I add the player info for " + user.getName());
        myfactory.getUserAccount().addUser(user);
    }

    public void addGameInfo(CatanGame game) throws IOException, JSONException
    {
        myfactory.getUserAccount().addGameToGameList(game);
    }

    public IFactory getMyfactory()
    {
        return myfactory;
    }

    private void checkfor10(int gameid) throws FileNotFoundException
    {
        FileReader db=new FileReader("commands"+gameid+".txt");
        Scanner myscanner=null;
        myscanner=new Scanner(db);
        StringBuilder getting=new StringBuilder();
        while(myscanner.hasNext())
        {
            getting.append(myscanner.next());
        }
        if(getting.charAt(0)==',')
        {
            getting.replace(0,1,"{");
        }
        getting.append("}");
        System.out.println("MY NICE JSON FILE IS THIS "+getting.toString());
        JSONObject mycommands1=null;
        try
        {
            mycommands1=new JSONObject(getting.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        for(int i=1; i<10;i++)
        {
            JSONObject jsonObject=mycommands1;
            if(!jsonObject.has(Integer.toString(i)))
            {
                System.out.println("I don't have "+i);
                return;
            }
        }

            System.out.println("I SHOULD CLEAR IT NOW");
            myfactory.getGameManager().clearInfo(gameid);
            myfactory.getGameManager().loadInfo();
    }



}
