package testpckg;

import static org.junit.Assert.*;
import client.model.ModelFacade;

import org.junit.*;
import org.junit.rules.ExpectedException;

import server.ourserver.ServerFacade;
import server.ourserver.commands.*;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.CatanGame;
import shared.game.DevCardList;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class TestDevCardCommands {
    /**
    This testss the buy dev card command to make sure it works as intended
     */

	Player player = null;
    @Before
    public void setUp()throws Exception
    {
         player = new Player("Rob", CatanColor.BLUE, new Index(0));
    }

    @After
    public void tearDown() throws Exception
    {
        player = null;
    }

    @Test
    public void testBuyDevCard() throws Exception
    {
    	assertTrue(player.getNewDevCards().getTotalCardNum() + player.getOldDevCards().getTotalCardNum() == 0);
    	ServerFacade.getInstance().buyDevCard(player.getPlayerID().getNumber()+100, ModelFacade.facadeCurrentGame.currentgame.getGameId());
    }
    
    //Monopoly - Monument - RB - Soldier - YOP
    @Test
    public void testPlayMonument() throws Exception
    {
    	player.setOldDevCards(new DevCardList(0,1,0,0,0));
    	ICommand monument = new PlayMonumentCommand(player.getPlayerID().getNumber()+100,ModelFacade.facadeCurrentGame.currentgame.getGameId());
    	Object result = monument.execute();
    	assertNull(result);
    }
    
    @Test
    public void testPlayMonopoly() throws Exception
    {
    	player.setOldDevCards(new DevCardList(1,0,0,0,0));
    	ICommand monopoly = new PlayMonopolyCommand(player.getPlayerID().getNumber()+100,"wheat",ModelFacade.facadeCurrentGame.currentgame.getGameId());
    	Object result = monopoly.execute();
    	assertNull(result);
    }
    
    @Test
    public void testPlayRoadBuilding() throws Exception
    {
    	player.setOldDevCards(new DevCardList(0,0,1,0,0));
    	ICommand roadBuilding = new PlayRoadBuildingCommand(player.getPlayerID().getNumber()+100,
    			new HexLocation(1,1),new EdgeLocation(new HexLocation(1,1),EdgeDirection.South),true,
    			ModelFacade.facadeCurrentGame.currentgame.getGameId());
    	Object result = roadBuilding.execute();
    	assertNull(result);
    }
    
    @Test
    public void testPlayYearofPlenty() throws Exception
    {
    	player.setOldDevCards(new DevCardList(0,0,0,0,1));
    	ICommand yearofplenty = new PlayYearOfPlentyCommand(player.getPlayerID().getNumber()+100,"wheat","brick",
    			ModelFacade.facadeCurrentGame.currentgame.getGameId());
    	Object result = yearofplenty.execute();
    	assertNull(result);
    }
    
    @Test
    public void testPlaySoldier() throws Exception
    {
    	Player player2 = new Player("Robby", CatanColor.YELLOW, new Index(1));
    	player.setOldDevCards(new DevCardList(0,0,0,1,0));
    	
    	ICommand soldier = new PlaySoldierCommand(new HexLocation(1,1),player.getPlayerID().getNumber()+100,
    			player2.getPlayerID().getNumber()+100,
    			ModelFacade.facadeCurrentGame.currentgame.getGameId());
    	//Object result = soldier.execute();
    	//assertNull(result);
    }
}
