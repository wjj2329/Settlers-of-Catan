package server.persistence;

import com.google.gson.Gson;

import server.ourserver.ServerFacade;
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
    private File stuff=new File("textfortest.txt");
    private FileWriter db=new FileWriter(stuff);
    private int gameid=-1000;

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public TextDBGameManagerDAO() throws IOException
    {

    }
    
	@Override
	public void storeGameModel() {
		// TODO Auto-generated method stub
		
	}
    @Override
    public void addCommand(ICommand commandObject) throws JSONException, IOException
    {
        db.write((commandObject.toString()));
        db.flush();
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
    public void clearInfo() {
        stuff=new File("textfortest.txt");
        try {
            db=new FileWriter(stuff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadInfo()
    {
        if(gameid!=-1000) {
            try {
                db.write(ServerFacade.getInstance().getGameModel(gameid).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
