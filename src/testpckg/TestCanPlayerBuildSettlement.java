package testpckg;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by williamjones on 5/11/16.
 */
public class TestCanPlayerBuildSettlement
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private  Hex hex1=new Hex(new HexLocation(1, 1), HexType.BRICK, new NumberToken(4), PortType.BLANK);
    private  Player william=new Player("William", CatanColor.WHITE,new Index(3));
    private Hex hex2=new Hex(new HexLocation(1,0),HexType.ORE, new NumberToken(8), PortType.BLANK);

    @Before
    public void setup() throws Exception {
        CatanGame.singleton=new CatanGame();
        CatanGame.singleton.setMymap(new CatanMap(1));
        william.setResources(new ResourceList(1,1,1,1,1));
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void test() throws Exception
    {
        assertTrue( william.canBuildSettlement(hex1,new VertexLocation(hex1.getLocation(),VertexDirection.East)));
        william.buildSettlement(hex1,new VertexLocation(hex1.getLocation(),VertexDirection.East));
        assertFalse(william.canBuildSettlement(hex1,new VertexLocation(hex1.getLocation(),VertexDirection.East)));
        assertFalse(william.canBuildSettlement(hex1,new VertexLocation(hex1.getLocation(),VertexDirection.West)));
    }

    @Test
    public void test2()throws Exception
    {
        william.setResources(new ResourceList(3,3,3,3,3));
        assertTrue(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(),VertexDirection.East)));
        william.buildSettlement(hex2,new VertexLocation(hex2.getLocation(),VertexDirection.East));
        assertTrue(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(),VertexDirection.NorthWest)));
        assertTrue(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(), VertexDirection.West)));
        assertTrue(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(), VertexDirection.SouthWest)));
        william.buildSettlement(hex2, new VertexLocation(hex2.getLocation(), VertexDirection.NorthWest));
        assertFalse(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(), VertexDirection.NorthWest)));
        assertFalse(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(), VertexDirection.West)));
        assertTrue(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(), VertexDirection.SouthWest)));
        william.buildSettlement(hex2, new VertexLocation(hex2.getLocation(), VertexDirection.SouthWest));
        assertFalse(william.canBuildSettlement(hex2,new VertexLocation(hex2.getLocation(), VertexDirection.SouthWest)));
    }

    @Test
    public void test3()throws Exception
    {
        exception.expect(Exception.class);
        hex2.canBuildCityHere(null);
        exception.expect(Exception.class);
        william.canBuildCity(hex2, new VertexLocation(hex1.getLocation(), VertexDirection.East));
        exception.expect(Exception.class);
    }

}
