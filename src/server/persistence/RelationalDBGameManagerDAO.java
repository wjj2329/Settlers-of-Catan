package server.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.bind.v2.model.core.ID;

import org.json.JSONException;
import org.json.JSONObject;

import server.database.Database;
import server.database.DatabaseException;
import server.ourserver.commands.ICommand;
import server.ourserver.commands.PlayRoadBuildingCommand;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

import javax.activation.CommandObject;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.IdentityHashMap;
//import android.database.sqlite.SQLiteDatabase;
import java.util.logging.Logger;


/**
 * Created by williamjones on 6/7/16.
 */
public class RelationalDBGameManagerDAO implements IGameManager
{
	private Database db;	    
    private static int increment=0;
    private Logger logger;
    
    public RelationalDBGameManagerDAO(Database database)
	{
		db = database;
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
	public void storeGameModel(){
		CatanGame game = new CatanGame();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try
		{
			Gson gson = new Gson();
			String gamedata = gson.toJson(game);
			
			String query = "insert into Game (id, gamemodel) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, game.getGameId());
			stmt.setString(2, gamedata); //Will change this later

		} catch (SQLException e)
		{
				try {
					throw new DatabaseException("Could not insert game", e);
				} catch (DatabaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}finally
		{
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}

    @Override
    public void addCommand(ICommand commandObject) throws JSONException, IOException {
    	CatanGame game = new CatanGame();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try
		{
			String query = "insert into Commands (id, gamemodel) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, game.getGameId());
			stmt.setString(2, game.getModel().toString()); //Will change this later
			
			
		} catch (SQLException e)
		{
				try {
					throw new DatabaseException("Could not insert game", e);
				} catch (DatabaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}finally
		{
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
    }

    @Override
	public ArrayList<ICommand> getCommands(int gameid) {//needs a gameid;
        logger.entering("server.database.Game", "getting commands from game with id " + gameid);
		
		ArrayList<ICommand> result = new ArrayList<ICommand>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			String query = "select id, command from Commands where id=?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameid);
			
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int id = rs.getInt(1);
				Gson gson = new Gson();
				ICommand command = gson.fromJson(rs.getString(2), ICommand.class);
				
				result.add(command);
			}
		} catch (SQLException e)
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
			return null;
		} finally
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		
		logger.exiting("server.database.Game", "getting commands from game with id " + gameid);
		return result;
	}
    
    
	@Override
	public CatanGame getGameModel(int gameid) {
        logger.entering("server.database.Games", "getting game from " + gameid);
		
		CatanGame result = new CatanGame();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			String query = "select id, title, gamedata from Games where id=?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameid);
			
			rs = stmt.executeQuery();
			while (rs.next())
			{
				if(gameid == rs.getInt(1))
				{
					int id = rs.getInt(1);
					String title = rs.getString(2);
					Gson gson = new Gson();
					result = gson.fromJson(rs.getString(3), CatanGame.class);				
					result.setID(id);
					result.setTitle(title);
				}
			}
		} catch (SQLException e)
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
			return null;
		} finally
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("server.database.Games", "getting game from " + gameid);
		return result;
	}
	
	@Override
	public Object getGameList() {
       logger.entering("server.database.Games", "getAll");
		
		ArrayList<CatanGame> result = new ArrayList<CatanGame>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			String query = "select id, gamedata from Games";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int id = rs.getInt(1);
				String gamedata = rs.getString(2);//probably in a json format? 
				
				CatanGame game = new CatanGame();
				game.setID(id);
				
				result.add(game);
			}
		} catch (SQLException e)
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
			return null;
		} finally
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		
		logger.exiting("server.database.Games", "getAll");
		return result;
	}


    @Override
    public ArrayList<CatanGame> getCommandsList() {
        return null;
    }

    @Override
    public void getGameModel() {

    }

    @Override
    public void clearInfo() {

    }

    @Override
    public void loadInfo() {

    }
}
