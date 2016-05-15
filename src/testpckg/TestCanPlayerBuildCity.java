package testpckg;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by williamjones on 5/11/16.
 * This class is pretty basic really just testing to see if player has enough resources in general to buy said city
 *  Actual testing of if city can be placed is done in Can Place City test
 */
public class TestCanPlayerBuildCity
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private Hex hex1=new Hex(new HexLocation(1, 1), HexType.BRICK, new NumberToken(4), null);
    private Player william=new Player("William", CatanColor.WHITE,new Index(3));

    @Before
    public void setup() throws Exception
    {
        CatanGame.singleton=new CatanGame();
        CatanGame.singleton.setMymap(new CatanMap(4));
        william.setResources(new ResourceList(3, 3, 3, 3, 3));
        hex1.buildSettlement(new VertexLocation(hex1.getLocation(),VertexDirection.East),new Index(2));
    }

    @After
    public void teardown()
    {
            CatanGame.singleton.clear();
    }

    @Test
    public void test() throws Exception
    {
        assertTrue(william.canBuildCity(hex1,new VertexLocation(hex1.getLocation(),VertexDirection.East)));
        william.buildCity(hex1,new VertexLocation(hex1.getLocation(),VertexDirection.East));
        assertFalse(william.canBuildCity(hex1,new VertexLocation(hex1.getLocation(),VertexDirection.East)));
        assertEquals(william.getResources().getOre(),0);
        assertEquals(william.getResources().getWheat(),1);
        assertEquals(william.getResources().getBrick(),3);
        assertEquals(william.getResources().getSheep(),3);
        assertEquals(william.getResources().getWood(),3);
        assertFalse(william.canBuildCity(hex1, new VertexLocation(hex1.getLocation(),VertexDirection.East)));
        assertFalse(william.canBuildCity(hex1, new VertexLocation(hex1.getLocation(),VertexDirection.West)));
    }

    @Test
    public void test2()throws Exception
    {
        william.setResources(new ResourceList(0, 0, 0, 0, 0));
        hex1.buildSettlement(new VertexLocation(hex1.getLocation(), VertexDirection.West ),new Index(2));
        assertFalse(william.canBuildCity(hex1, new VertexLocation(hex1.getLocation(), VertexDirection.West)));
    }

    @Test
    public void test3()throws Exception
    {
        william.setResources(new ResourceList(0, 3, 0, 2, 0));
        hex1.buildSettlement(new VertexLocation(hex1.getLocation(),VertexDirection.East),new Index(3));
        assertTrue(william.canBuildCity(hex1, new VertexLocation(hex1.getLocation(), VertexDirection.East)));
        hex1.buildCity(new VertexLocation(hex1.getLocation(), VertexDirection.East));
        assertFalse(william.canBuildCity(hex1, new VertexLocation(hex1.getLocation(), VertexDirection.East)));
    }

    @Test
    public void test4()throws Exception
    {
        Hex hex2=null;
        exception.expect(Exception.class);
        hex2.canBuildCityHere(null);
        exception.expect(Exception.class);
        william.canBuildCity(hex2, new VertexLocation(hex1.getLocation(), VertexDirection.East));
        exception.expect(Exception.class);


    }
}
