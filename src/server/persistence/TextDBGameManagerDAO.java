package server.persistence;

import com.google.gson.Gson;

import server.ourserver.commands.ICommand;
import shared.game.CatanGame;

import org.json.JSONException;
import org.json.JSONObject;
import shared.game.CatanGame;

import javax.activation.CommandObject;
import java.sql.SQLData;
import java.util.ArrayList;

/**
 * Created by williamjones on 6/7/16.
 */
public class TextDBGameManagerDAO implements IGameManager
{
    private SQLData db;
    private static int increment=0;
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

    @Override
    public void addCommand() {

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
	@Override
	public Object getGameList() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void storeGame() {

    }

    @Override
    public void addCommand() {

    }

    @Override
    public ArrayList<CatanGame> getCommandsList() {
        return null;
    }

    @Override
    public void getGameModel() {

    }

    @Override
    public void getGameList() {

    }
}
