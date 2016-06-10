package server.persistence;

import client.join.JoinGameController;
import com.google.gson.Gson;

import com.sun.corba.se.impl.orbutil.ObjectWriter;
import server.ourserver.ServerFacade;
import server.ourserver.commands.*;
import shared.game.CatanGame;

import org.json.JSONException;
import org.json.JSONObject;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.locations.*;

import javax.activation.CommandObject;
import java.io.*;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by williamjones on 6/7/16.
 *
 */
public class TextDBGameManagerDAO implements IGameManager
{
    private int gameid=-1000;
    private File commands=new File("commands"+gameid+".txt");
    private File game=new File("game"+gameid+".txt");
    private FileWriter db=new FileWriter(commands);
    private FileWriter dbg=new FileWriter(game);
    public static int commandNumber=-1;


    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {

        this.gameid = gameid;
        commands=new File("commands"+gameid+".txt");
        try {
            db=new FileWriter(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextDBGameManagerDAO() throws IOException
    {
            db.write("{");
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
	public ArrayList<ICommand> getCommands() throws JSONException {
        ArrayList<ICommand> commandsloadedfromdb=new ArrayList<>();
        Scanner myscanner=null;
        try {
             myscanner=new Scanner(commands);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder getting=new StringBuilder();
        while(myscanner.hasNext())
        {
            getting.append(myscanner.next());
        }
        if(getting.charAt(1)==',')
        {
            getting.deleteCharAt(1);
        }
        getting.append("}");
        System.out.println("MY NICE JSON FILE IS THIS "+getting.toString());
        JSONObject mycommands=null;
        try {
             mycommands=new JSONObject(getting.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(mycommands.toString());
        for(int i=0; i<9;i++)
        {
            JSONObject jsonObject=null;
            try {
                jsonObject=mycommands.getJSONObject(Integer.toString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String type= jsonObject.getString("type");
            switch(type)
            {
                case "SendChatCommand":
                {

                    break;
                }
                case "RollNumberCommand":
                {
                    break;
                }
                case "RobPlayerCommand":
                {
                    break;
                }
                case "PlayYearOfPlentyCommand":
                {
                    break;
                }
                case "PlaySoldierCommand":
                {
                    break;
                }
                case "PlayRoadBuildingCommand":
                {
                    break;
                }
                case"PlayMonumentCommand":
                {
                    break;
                }
                case "PlayMonopolyCommand":
                {
                    break;
                }
                case "OfferTradeCommand":
                {
                    break;
                }
                case "MaritimeTradeCommand":
                {
                    break;
                }
                case "FinishTurnCommand":
                {
                    int playerIndex=jsonObject.getInt("playerIndex");
                    int gameid;
                    break;
                }
                case "DiscardCardsCommand":
                {
                    int playerIndex=jsonObject.getInt("playerIndex");
                    ResourceList cardsToDiscard=new ResourceList(jsonObject.getInt("brick"),jsonObject.getInt("ore"),jsonObject.getInt("sheep"),jsonObject.getInt("wheat"), jsonObject.getInt("wood"));
                    int gameid=jsonObject.getInt("gameid");
                    commandsloadedfromdb.add(new DiscardCardsCommand(playerIndex,cardsToDiscard,gameid));
                    break;
                }
                case "BuyDevCardCommand":
                {
                    int playerIndex=jsonObject.getInt("playerIndex");
                    int gameid=jsonObject.getInt("gameid");
                    commandsloadedfromdb.add(new BuyDevCardCommand(playerIndex,gameid));
                    break;
                }
                case"BuildSettlementCommand":
                {
                    int playerIndex=jsonObject.getInt("playerIndex");
                    HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
                    VertexLocation vertex=new VertexLocation(location,convertToVertexDirection(jsonObject.getString("dir")));
                    boolean free=jsonObject.getBoolean("free");
                    int gameid=jsonObject.getInt("gameid");
                    commandsloadedfromdb.add(new BuildSettlementCommand(playerIndex,location,vertex,free,gameid));
                    break;
                }
                case "BuildRoadCommand":
                {
                    int playerIndex=jsonObject.getInt("playerIndex");
                    HexLocation location=new HexLocation(jsonObject.getInt("x"), jsonObject.getInt("y"));
                    EdgeLocation edge=new EdgeLocation(location, getDirectionFromString(jsonObject.getString("edge")));
                    boolean free=jsonObject.getBoolean("free");
                    int gameid=jsonObject.getInt("gameid");
                    commandsloadedfromdb.add(new BuildRoadCommand(playerIndex,location,edge,free,gameid));
                    break;
                }
                case"BuildCityCommand":
                {
                    int playerIndex=jsonObject.getInt("playerIndex");
                    HexLocation location=new HexLocation(jsonObject.getInt("x"),jsonObject.getInt("y"));
                    VertexLocation vertex=new VertexLocation(location,convertToVertexDirection(jsonObject.getString("vertex")));
                    int gameid=jsonObject.getInt("gameid");
                    commandsloadedfromdb.add(new BuildCityCommand(playerIndex,location,vertex,gameid));
                    break;
                }
                case"AcceptTradeCommand":
                {
                    int gameid=jsonObject.getInt("gameid");
                    int playerindex=jsonObject.getInt("playerIndex");
                    boolean willaccept=jsonObject.getBoolean("willAccept");
                    commandsloadedfromdb.add(new AcceptTradeCommand(gameid,playerindex,willaccept));
                    break;
                }
            }
        }
        return commandsloadedfromdb;
	}
	@Override
	public CatanGame getGameModel(int gameid) {
		// TODO Auto-generated method stub
		return null;
	}


    @Override
    public void clearInfo() {
        commands=new File("game"+gameid+".txt");
        try
        {
            db=new FileWriter(commands);
            db.write("{");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
    @Override
    public void loadInfo()
    {
        if(gameid!=-1000) {
            try
            {
                dbg.write(ServerFacade.getInstance().getGameModel(gameid).toString());
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
    public Object getGameList() {
        return "Not yet implemented";
    }
}
