package server.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.sun.xml.internal.bind.v2.model.core.ID;

import org.json.JSONException;
import org.json.JSONObject;

import server.database.Database;
import server.database.DatabaseException;
import server.ourserver.ServerFacade;
import server.ourserver.commands.AcceptTradeCommand;
import server.ourserver.commands.BuildCityCommand;
import server.ourserver.commands.BuildRoadCommand;
import server.ourserver.commands.BuildSettlementCommand;
import server.ourserver.commands.BuyDevCardCommand;
import server.ourserver.commands.DiscardCardsCommand;
import server.ourserver.commands.FinishTurnCommand;
import server.ourserver.commands.ICommand;
import server.ourserver.commands.MaritimeTradeCommand;
import server.ourserver.commands.OfferTradeCommand;
import server.ourserver.commands.PlayMonopolyCommand;
import server.ourserver.commands.PlayMonumentCommand;
import server.ourserver.commands.PlayRoadBuildingCommand;
import server.ourserver.commands.PlaySoldierCommand;
import server.ourserver.commands.PlayYearOfPlentyCommand;
import server.ourserver.commands.RobPlayerCommand;
import server.ourserver.commands.RollNumberCommand;
import server.ourserver.commands.SendChatCommand;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import javax.activation.CommandObject;
import java.io.FileNotFoundException;
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
		System.out.println("Created a Game Manager Yo.");
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
	public void storeGameModel(int gameid){
		CatanGame game = new CatanGame();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try
		{
//			Gson gson = new Gson();
//			String gamedata = gson.toJson(game);
			db.startTransaction();
			String query = "select id, gamedata from Games where id=?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameid);
			
			keyRS = stmt.executeQuery();
			
			if(keyRS.next()){
				String updateQuery = "update Games set gamedata = ? where id = ?";
				stmt = db.getConnection().prepareStatement(updateQuery);
				stmt.setString(1, ServerFacade.getInstance().getGameModel(gameid).toString());
				stmt.setInt(2, gameid);
				stmt.executeQuery();
			}
			else{
				String insertQuery = "insert into Games (id, gamemodel) values (?, ?)";
				stmt = db.getConnection().prepareStatement(insertQuery);
				stmt.setInt(1,gameid);
				stmt.setString(2, ServerFacade.getInstance().getGameModel(gameid).toString());
				stmt.executeQuery();
			}

			
		} catch (SQLException e)
		{
				try {
					throw new DatabaseException("Could not insert game", e);
				} catch (DatabaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}*/ catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		db.endTransaction(true);
	}

    @Override
    public void addCommand(ICommand commandObject, int gameId) throws JSONException, IOException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try
		{
			db.startTransaction();
			String query = "insert into Commands (id, command) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameId);
			stmt.setString(2, commandObject.toJSON()); //Will change this later
			stmt.executeQuery();
			
		} catch (SQLException e)
		{
				try {
					throw new DatabaseException("Could not insert game", e);
				} catch (DatabaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
		db.endTransaction(true);
    }

    @Override
	public ArrayList<ICommand> getCommands(int gameid) {//needs a gameid;
        logger.entering("server.database.Games", "getting commands from game with id " + gameid);
		
		ArrayList<ICommand> result = new ArrayList<ICommand>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			db.startTransaction();
			String query = "select id, command from Commands where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameid);
			
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int id = rs.getInt(1);
				String commandData = rs.getString(2);
				JSONObject command = new JSONObject(commandData);
				
				result.add(getCommand(command));
			}
		}catch (SQLException e)
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		
		logger.exiting("server.database.Game", "getting commands from game with id " + gameid);
		db.endTransaction(true);
		return result;
	}
    
    public ICommand getCommand(JSONObject jsonObject) throws JSONException{
    	ICommand command = null;
    	String type = jsonObject.getString("type");
    	switch (type) {
	    	case "SendChatCommand":
	        {
	            String message=jsonObject.getString("message");
	            int playerindex=jsonObject.getInt("playerindex");
	            int gameid=jsonObject.getInt("gameid");
	            return new SendChatCommand(message,playerindex,gameid);
	        }
	        case "RollNumberCommand":
	        {
	            int rollNumber=jsonObject.getInt("rollNumber");
	            int gameid=jsonObject.getInt("gameid");
	            return new RollNumberCommand(rollNumber,gameid);
	        }
	        case "RobPlayerCommand":
	        {
	            HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
	            int playerRobbing=jsonObject.getInt("playerRobbing");
	            int playerbeingrobbed=jsonObject.getInt("playerbeingrobbed");
	            int gameid=jsonObject.getInt("gameid");
	            return new RobPlayerCommand(location,playerRobbing,playerbeingrobbed,gameid);
	        }
	        case "PlayYearOfPlentyCommand":
	        {
	            int gameID=jsonObject.getInt("gameid");
	            String resource2=jsonObject.getString("resource2");
	            String resource1=jsonObject.getString("resource1");
	            int playerindex=jsonObject.getInt("playerindex");
	            return new PlayYearOfPlentyCommand(gameID,resource1,resource2,playerindex);
	        }
	        case "PlaySoldierCommand":
	        {
	            HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
	            int playerRobbing=jsonObject.getInt("playerRobbing");
	            int playerBeingRobbed=jsonObject.getInt("playerBeingRobbed");
	            int gameid=jsonObject.getInt("gameid");
	            return new PlaySoldierCommand(location,playerRobbing,playerBeingRobbed,gameid);
	        }
	        case "PlayRoadBuildingCommand":
	        {
	            int playerIndex=jsonObject.getInt("playerIndex");
	            HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
	            EdgeLocation edge=new EdgeLocation(location,getDirectionFromString(jsonObject.getString("edge")));
	            boolean free=jsonObject.getBoolean("free");
	            int gameid=jsonObject.getInt("gameid");
	            return new PlayRoadBuildingCommand(playerIndex,location,edge,free,gameid);
	        }
	        case"PlayMonumentCommand":
	        {
	            int playerindex=jsonObject.getInt("playerindex");
	            int gameID=jsonObject.getInt("gameID");
	            return new PlayMonumentCommand(playerindex,gameID);
	        }
	        case "PlayMonopolyCommand":
	        {
	            int playerindex=jsonObject.getInt("playerindex");
	            String resource=jsonObject.getString("resource");
	            int gameID=jsonObject.getInt("gameID");
	            return new PlayMonopolyCommand(playerindex,resource,gameID);
	        }
	        case "OfferTradeCommand":
	        {
	            int gameid=jsonObject.getInt("gameid");
	            int playerIndex=jsonObject.getInt("playerIndex");
	            ResourceList offer=new ResourceList(jsonObject.getInt("brick"),jsonObject.getInt("ore"),jsonObject.getInt("sheep"),jsonObject.getInt("wheat"), jsonObject.getInt("wood"));
	            int receiver=jsonObject.getInt("receiver");
	            return new OfferTradeCommand(gameid,playerIndex,offer,receiver);
	        }
	        case "MaritimeTradeCommand":
	        {
	            String getResource=jsonObject.getString("getResource");
	            String giveResource=jsonObject.getString("giveResource");
	            int playerIndex_NOT_ID=jsonObject.getInt("playerIndex_NOT_ID");
	            int ratio=jsonObject.getInt("ratio");
	            int gameID=jsonObject.getInt("gameID");
	            return new MaritimeTradeCommand(getResource,giveResource,playerIndex_NOT_ID,ratio,gameID);
	        }
	        case "FinishTurnCommand":
	        {
	            int playerIndex=jsonObject.getInt("playerIndex");
	            int gameid=jsonObject.getInt("gameid");
	            return new FinishTurnCommand(playerIndex,gameid);
	        }
	        case "DiscardCardsCommand":
	        {
	            int playerIndex=jsonObject.getInt("playerIndex");
	            ResourceList cardsToDiscard=new ResourceList(jsonObject.getInt("brick"),jsonObject.getInt("ore"),jsonObject.getInt("sheep"),jsonObject.getInt("wheat"), jsonObject.getInt("wood"));
	            int gameid=jsonObject.getInt("gameid");
	            return new DiscardCardsCommand(playerIndex,cardsToDiscard,gameid);
	        }
	        case "BuyDevCardCommand":
	        {
	            int playerIndex=jsonObject.getInt("playerIndex");
	            int gameid=jsonObject.getInt("gameid");
	            return new BuyDevCardCommand(playerIndex,gameid);
	        }
	        case"BuildSettlementCommand":
	        {
	            int playerIndex=jsonObject.getInt("playerIndex");
	            HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
	            VertexLocation vertex=new VertexLocation(location,convertToVertexDirection(jsonObject.getString("dir")));
	            boolean free=jsonObject.getBoolean("free");
	            int gameid=jsonObject.getInt("gameid");
	            return new BuildSettlementCommand(playerIndex,location,vertex,free,gameid);
	        }
	        case "BuildRoadCommand":
	        {
	            int playerIndex=jsonObject.getInt("playerIndex");
	            HexLocation location=new HexLocation(jsonObject.getInt("x"), jsonObject.getInt("y"));
	            EdgeLocation edge=new EdgeLocation(location, getDirectionFromString(jsonObject.getString("edge")));
	            boolean free=jsonObject.getBoolean("free");
	            int gameid=jsonObject.getInt("gameid");
	            return new BuildRoadCommand(playerIndex,location,edge,free,gameid);
	        }
	        case"BuildCityCommand":
	        {
	            int playerIndex=jsonObject.getInt("playerIndex");
	            HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
	            VertexLocation vertex=new VertexLocation(location,convertToVertexDirection(jsonObject.getString("vertex")));
	            int gameid=jsonObject.getInt("gameid");
	            return new BuildCityCommand(playerIndex,location,vertex,gameid);
	        }
	        case"AcceptTradeCommand":
	        {
	            int gameid=jsonObject.getInt("gameid");
	            int playerindex=jsonObject.getInt("playerIndex");
	            boolean willaccept=jsonObject.getBoolean("willAccept");
	            return new AcceptTradeCommand(gameid,playerindex,willaccept);
	        }
		}
    	return command;
    }
    
    private VertexDirection convertToVertexDirection(String direction)
    {
        switch (direction)
        {
            case "W":
                return VertexDirection.West;
            case "NW":
                return VertexDirection.NorthWest;
            case "NE":
                return VertexDirection.NorthEast;
            case "E":
                return VertexDirection.East;
            case "SE":
                return VertexDirection.SouthEast;
            case "SW":
                return VertexDirection.SouthWest;
            default:
                break;
            //assert false;
        }
        return null;
    }
    
    private EdgeDirection getDirectionFromString(String direction)
    {
        //System.out.println("the direction is: " + direction);
        switch (direction)
        {
            case "NW":
                return EdgeDirection.NorthWest;
            case "N":
                return EdgeDirection.North;
            case "NE":
                return EdgeDirection.NorthEast;
            case "SW":
                return EdgeDirection.SouthWest;
            case "S":
                return EdgeDirection.South;
            case "SE":
                return EdgeDirection.SouthEast;
            default:
                //System.out.println("Something is screwed up with the direction");
                //assert false;
                break;
        }
        return null;
    }
    
	@Override
	public CatanGame getGameModel(int gameid) {
        logger.entering("server.database.Games", "getting game from " + gameid);
		
		CatanGame result = new CatanGame();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			db.startTransaction();
			String query = "select id, gamedata from Games where id=?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, gameid);
			
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int id = rs.getInt(1);
				if(gameid == id)
				{
					String gamedata = rs.getString(2);				
					result.setID(id);
					result.updateFromJSON(new JSONObject(gamedata));
				}
			}
		} catch (SQLException e)
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
			return null;
		} catch(JSONException e){ 
			e.printStackTrace();
			Database.safeClose(rs);
			Database.safeClose(stmt);
			return null;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("server.database.Games", "getting game from " + gameid);
		db.endTransaction(true);
		return result;
	}

	@Override
	public void createnewGameFile(int gameid) {
		
	}

	@Override
	public Object getGameList() {
       logger.entering("server.database.Games", "getAll");
		
		ArrayList<CatanGame> result = new ArrayList<CatanGame>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			db.startTransaction();
			String query = "select id, gamedata from Games";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int id = rs.getInt(1);
				String gamedata = rs.getString(2);//probably in a json format? 
				
				CatanGame game = new CatanGame();
				game.updateFromJSON(new JSONObject(gamedata));
				game.setID(id);
				
				result.add(game);
			}
		} catch (SQLException e)
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
			return null;
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		
		logger.exiting("server.database.Games", "getAll");
		db.endTransaction(true);
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
	public void clearInfo(int gameid) {

	}

	@Override
	public void loadInfo(int gameid) throws IOException, JSONException {

	}


}
