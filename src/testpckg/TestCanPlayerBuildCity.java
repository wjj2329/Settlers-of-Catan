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
 * This class is pretty basic really just testing to see if player has enough resources in general to buy said city
 *  Actual testing of if  city can be placed is is done in Can Place City
 */
public class TestCanPlayerBuildCity
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    Hex hex1=new Hex(new HexLocation(1, 1), HexType.BRICK, new NumberToken(4), PortType.BLANK);
    Player william=new Player("William", CatanColor.WHITE,new Index(3));
    
    @Before
    public void setup()
    {

    }

    @After
    public void teardown()
    {

    }

    @Test
    public void test() throws Exception
    {

    }

    @Test
    public void test2()throws Exception
    {

    }

    @Test
    public void test3()throws Exception
    {

    }

    @Test
    public void test4()throws Exception
    {

    }
}
