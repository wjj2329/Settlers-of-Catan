package server.persistence;

import com.google.gson.Gson;

import server.ourserver.commands.ICommand;
import shared.game.CatanGame;

import org.json.JSONException;
import org.json.JSONObject;
import shared.game.CatanGame;

import javax.activation.CommandObject;
import java.io.*;
import java.sql.SQLData;
import java.util.ArrayList;

/**
 * Created by williamjones on 6/7/16.
 */
public class TextDBGameManagerDAO implements IGameManager
{
    private File db=new File("textfortest.txt");
    private static int increment=0;

    public TextDBGameManagerDAO() throws IOException
    {

    }

    void addcommandinfo(CommandObject commandObject) throws JSONException
    {
        Gson myobject=new Gson();
        String jobj= (myobject.toJson(commandObject));
        JSONObject my=new JSONObject(jobj);
        //insert into db
        if(increment==10)
        {
            increment=0;
            // TODO clear database of everything then put in a model
        }
    }
    void withdrawinfo()
    {
        // TODO take stuff out of the db to give to server facade model to load
    }
    
	@Override
	public void storeGameModel() {
		// TODO Auto-generated method stub
		
	}
    //private FileOutputStream fileout=new FileOutputStream("myfile.ser");
   // ObjectOutputStream out=new ObjectOutputStream(fileout);


    @Override
    public void addCommand(ICommand commandObject) throws JSONException, IOException {

        System.out.println("I make it here before dying.");
    }

	@Override
	public ICommand getCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CatanGame getGameModel(int gameid) {
		// TODO Auto-generated method stub
		return null;
	}

    public void storeGame() {

    }

    @Override
    public void clearInfo() {

    }

    @Override
    public void loadInfo() {

    }

    @Override
    public ArrayList<CatanGame> getCommandsList() {
        return null;
    }

    @Override
    public void getGameModel() {

    }

    @Override
    public Object getGameList() {
        return "Not yet implemented";
    }
}
