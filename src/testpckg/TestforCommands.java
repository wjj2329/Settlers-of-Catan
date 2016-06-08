package testpckg;

import client.domestic.DomesticTradeController;
import client.model.ModelFacade;
import client.model.TurnStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.ourserver.ServerFacade;
import server.ourserver.commands.*;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.*;

import javax.activation.CommandObject;
import javax.activation.DataHandler;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestforCommands
{
    CatanGame mygame;

    Player sam = new Player("Sam", CatanColor.ORANGE, new Index(0));

    Player mark = new Player("Brooke", CatanColor.BLUE, new Index(1));

    Player brooke = new Player("Pete", CatanColor.RED, new Index(2));

    Player pete = new Player("Mark", CatanColor.GREEN, new Index(3));
    private ArrayList<Player> allRegisteredUsers = new ArrayList<>();


    @Before
    public void setUp() throws Exception
    {
        sam.setPlayerIndex(new Index(10));
        sam.setPassword("sam");
        sam.setResources(new ResourceList(5,5,5,5,5));
        mark.setPlayerIndex(new Index(20));
        mark.setPassword("brooke");
        mark.setPlayerIndex(new Index(30));
        brooke.setPassword("pete");
        pete.setPlayerIndex(new Index(40));
        pete.setPassword("mark");
        allRegisteredUsers.add(sam);
        allRegisteredUsers.add(mark);
        allRegisteredUsers.add(brooke);
        allRegisteredUsers.add(pete);
        mygame = new CatanGame();
        mygame.setTitle("Empty Game");
        mygame.setID(1);
        mygame.addPlayer(new Player(allRegisteredUsers.get(0).getName(), allRegisteredUsers.get(0).getColor(), allRegisteredUsers.get(0).getPlayerID()));
        mygame.addPlayer(new Player(allRegisteredUsers.get(1).getName(), allRegisteredUsers.get(1).getColor(), allRegisteredUsers.get(1).getPlayerID()));
        mygame.addPlayer(new Player(allRegisteredUsers.get(2).getName(), allRegisteredUsers.get(2).getColor(), allRegisteredUsers.get(2).getPlayerID()));
        mygame.addPlayer(new Player(allRegisteredUsers.get(3).getName(), allRegisteredUsers.get(3).getColor(), allRegisteredUsers.get(3).getPlayerID()));

        mygame.mybank.setResourceCardslist(19,19,19,19,19); //it has 95 resource cards right?
        mygame.getMyplayers().get(new Index(0)).setPlayerIndex(new Index(0));
        mygame.getMyplayers().get(new Index(1)).setPlayerIndex(new Index(1));
        mygame.getMyplayers().get(new Index(2)).setPlayerIndex(new Index(2));
        mygame.getMyplayers().get(new Index(3)).setPlayerIndex(new Index(3));

        mygame.myrobber.setLocation(new HexLocation(0, -2));

        mygame.getModel().getTurntracker().setStatus(TurnStatus.FIRSTROUND);
        mygame.getModel().getTurntracker().setLongestRoad(new Index(-1));
        mygame.getModel().getTurntracker().setLargestArmy(new Index(-1));
        mygame.getModel().getTurntracker().setCurrentTurn(new Index(0), mygame.getMyplayers());
    }

  
    @After
    public void tearDown() throws Exception
    {

    }
    @Test
    public void mytest1()
    {
        MaritimeTradeCommand commandObject=new MaritimeTradeCommand("BRICK","WHEAT",10,3,0);
        assertEquals(sam.getResources().getWheat(),5);
    }
    @Test
    public void mytest2()
    {
        SendChatCommand chatCommand=new SendChatCommand("HI",10,0);
        assertEquals(mygame.getMychat().getChatMessages().getMessages().size(),0);
    }
    @Test
    public void mytest3()
    {
        BuildRoadCommand buildRoadCommand=new BuildRoadCommand(10,new HexLocation(0,0),new EdgeLocation(new HexLocation(0,0),EdgeDirection.North),true,0);
        assertEquals(mygame.getMyplayers().get(new Index(0)).getRoadPieces().size(),0);
    }
    @Test
    public void mytest4()
    {
        BuildSettlementCommand buildSettlementCommand=new BuildSettlementCommand(10, new HexLocation(0,0),new VertexLocation(new HexLocation(0,0), VertexDirection.East),true,0);
        assertEquals(mygame.getMyplayers().get(new Index(0)).getSettlements().size(),0);
    }
    @Test
    public void mytest5()
    {
        BuildCityCommand buildCityCommand=new BuildCityCommand(10, new HexLocation(0,0),new VertexLocation(new HexLocation(0,0), VertexDirection.East),0);
        assertEquals(mygame.getMyplayers().get(new Index(0)).getCities().size(),0);
    }
    @Test
    public void mytest6()
    {
        DiscardCardsCommand mydiscard=new DiscardCardsCommand(10, new ResourceList(1,1,1,1,1),0);
        assertEquals(sam.getResources().getWheat(),5);
    }
    @Test
    public void mytest7()
    {
       AcceptTradeCommand mytrade=new AcceptTradeCommand(0,10,true);
        assertEquals(sam.getResources().getWheat(),5);
    }
    @Test
    public void mytest8()
    {
        RobPlayerCommand myrob=new RobPlayerCommand(new HexLocation(0,0),10,30,0);
        assertEquals(sam.getResources().getWheat(),5);
    }
}