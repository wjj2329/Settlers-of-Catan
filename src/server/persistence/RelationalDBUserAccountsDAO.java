package server.persistence;

import com.google.gson.Gson;

import server.database.Database;
import server.database.DatabaseException;
import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;

import org.json.JSONException;
import org.json.JSONObject;

import shared.game.player.Player;

import javax.activation.CommandObject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by williamjones on 6/7/16.
 */
public class RelationalDBUserAccountsDAO implements IUserAccount
{
    private Database db;
	//private Logger logger;
    
    public RelationalDBUserAccountsDAO(Database database)
	{
		db = database;
	}

	void adduserinfo(CommandObject commandObject) throws JSONException
    {
        Gson myobject=new Gson();
        String jobj= (myobject.toJson(commandObject));
        JSONObject my=new JSONObject(jobj);

    }

    @Override
    public Player validateUser(Player user) 
    {
    	try
		{
			db.startTransaction();
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
    	Player validated = null;
		
		List<Player> allUsers = null;
		
		allUsers = getAllUsers();
		
		for (Player candidate : allUsers)
		{
			if (candidate.equals(user))
			{
				validated = candidate;
			}
		}
		
		db.endTransaction(true);
		return validated;
    }
    
    @Override
    public List<Player> getAllUsers() 
    {
		//logger.entering("server.database.User", "getAll");
		
		ArrayList<Player> result = new ArrayList<Player>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			String query = "select id, username, password from User";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				
				result.add(new Player(id, username, password));
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
		
		//logger.exiting("server.database.User", "getAll");
		return result;
    }


    @Override
	public void addUser(Player user) throws DatabaseException
    {
    	System.out.println("Here we go");
    	db.startTransaction();
    	
    	ArrayList<Player> allUsers = (ArrayList<Player>) getAllUsers();
    	
    	for (Player candidate : allUsers)
		{
			if (candidate.equals(user))
			{
				db.endTransaction(true);
				return;
			}
		}
    	
    	PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try
		{
			String query = "insert into User (id, username, password) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getPlayerID().getNumber());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getPassword());
			
			
			if (stmt.executeUpdate() == 1)
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setPlayerID(new Index(id));
			} 
			else
			{
				throw new DatabaseException("Could not insert user");
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Could not insert user", e);
		} finally
		{
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		db.endTransaction(true);
		
	}

    @Override
    public void setColor(Player user) throws DatabaseException 
    {
    	PreparedStatement stmt = null;
		
		try
		{
			String query = "update user set color = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getColor().name());
			stmt.setInt(2, user.getPlayerID().getNumber());
			
			if (stmt.executeUpdate() != 1)
			{
				throw new DatabaseException("Could not update user");
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Could not update user", e);
		} finally
		{
			Database.safeClose(stmt);
		}
    }

    @Override
    public boolean isUserInGame(Player user) 
    {
        return false;
    }

	@Override
	public void addGameToGameList(CatanGame game) throws IOException
	{

	}
}
