package testpckg;

import client.model.Model;
import client.model.ModelFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.proxies.IServer;
import server.response.ServerResponse;
import shared.definitions.CatanColor;
import shared.game.Bank;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Alex on 5/9/16.
 */
public class TestCanCreatePlayer
{
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;


    @Before
    public void setUp() throws Exception
    {
        p1 = new Player("William", CatanColor.WHITE, new Index(0));
        p2 = new Player("Matthew", CatanColor.BROWN, new Index(1));
        p3 = new Player("Alex", CatanColor.RED, new Index(2));
        p4 = new Player("Alex", CatanColor.RED, new Index(3));
       // ModelFacade.facadeCurrentGame.currentgame=new CatanGame();
        ModelFacade.facadeCurrentGame.currentgame.addPlayer(p1);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void test()
    {

        assertFalse(ModelFacade.facadeCurrentGame.currentgame.canCreatePlayer(p1));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.canCreatePlayer(p2));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.canCreatePlayer(p3));
        ModelFacade.facadeCurrentGame.currentgame.addPlayer(p3);
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.canCreatePlayer(p4));
    }

}