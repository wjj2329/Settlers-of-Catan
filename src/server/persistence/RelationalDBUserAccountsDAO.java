package server.persistence;

import com.google.gson.Gson;

import shared.definitions.CatanColor;
import shared.game.player.Player;

import org.json.JSONException;
import org.json.JSONObject;
import shared.game.player.Player;

import javax.activation.CommandObject;
import java.sql.SQLData;

/**
 * Created by williamjones on 6/7/16.
 */
public class RelationalDBUserAccountsDAO implements IUserAccount
{
    private SQLData db;//TODO figure out our to get SQL lite to work
    void adduserinfo(CommandObject commandObject) throws JSONException
    {
        Gson myobject=new Gson();
        String jobj= (myobject.toJson(commandObject));
        JSONObject my=new JSONObject(jobj);

    }
    void withdrawinfo()
    {
        // TODO take stuff out of the db to give to server facade model to load
    }

    @Override
    public void clearInfo() {

    }

    @Override
    public void loadInfo() {

    }

    @Override
    public void validateUser() {

    }

    @Override
    public void addUser() {

    }

    @Override
    public void getAllUsers() {

    }


    @Override
	public void addPlayer() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void setColor() {

    }

    @Override
    public boolean isUserInGame(Player user) {
        return false;
    }


}
