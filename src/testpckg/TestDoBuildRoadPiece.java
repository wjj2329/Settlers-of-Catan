package testpckg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.game.CatanGame;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Alex on 5/11/16.
 *
 * In this J-Unit test case, I do the testing of the ACTUAL road building.
 * The CanBuildRoadPiece function tests our CanBuild.
 * This test utilizes some of the CanBuild tests. More importantly, it ensures that
 * after the canBuild is successful, the ACTUAL ACT of building works as well.
 */
public class TestDoBuildRoadPiece
{
    /**
     * Two sample players.
     */
    private Player p1 = new Player(NAME1, CatanColor.BLUE, new Index(0));
    private Player p2 = new Player(NAME2, CatanColor.RED, new Index(1));
    private Hex hex1 = null;
    private HexLocation loc1 = null;

    @Before
    public void setUp()
    {
        p1.getResources().setBrick(1);
        p1.getResources().setWood(1);
        p2.getResources().setBrick(1);
        p2.getResources().setWood(1);
        loc1 = new HexLocation(-2, 0);
        hex1 = CatanGame.singleton.getMymap().getHexes().get(loc1);
    }

    @After
    public void tearDown()
    {

    }

    private void initialize1() throws Exception
    {
        p1.setCurrentPlayer(true);
        assertEquals(loc1, hex1.getLocation());
        Settlement settle1 = new Settlement(loc1, hex1.getNorthwest());
        hex1.getNorthwest().setHassettlement(true);
        hex1.getNorthwest().setSettlement(settle1);
        settle1.setOwner(p1.getPlayerID());

        p1.addToSettlements(settle1);
        assertTrue(p1.canBuildRoadPiece(hex1, new EdgeLocation(loc1, EdgeDirection.NorthWest)));
    }

    /**
     * If one hex is water but the other isn't, we are clear to build a road there.
     */
    @Test
    public void testSuccess_OneHexIsWater() throws Exception
    {
        initialize1();
        assertTrue(hex1.getResourcetype().equals(HexType.WHEAT));
        assertTrue(p1.buildRoadPiece(hex1, new EdgeLocation(loc1, EdgeDirection.NorthWest)));
    }

    private static final String NAME1 = "Jenny";
    private static final String NAME2 = "Joy";
}
