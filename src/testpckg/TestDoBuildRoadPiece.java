package testpckg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import java.util.Map;

import static junit.framework.TestCase.assertFalse;
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
    private Hex hex2 = null;
    private HexLocation loc1 = null;
    private HexLocation loc2 = null;

    @Before
    public void setUp() throws Exception
    {
        p1.getResources().setBrick(1);
        p1.getResources().setWood(1);
        p2.getResources().setBrick(1);
        p2.getResources().setWood(1);
        loc1 = new HexLocation(-2, 0);
        // init
        CatanMap myMap = CatanGame.singleton.getMymap(); // this needs to be INITIALIZED.
        hex1 = myMap.getHexes().get(loc1);
    }

    @After
    public void tearDown() throws Exception
    {

    }

    private void initialize1() throws Exception
    {
        p1.setCurrentPlayer(true);
        assertEquals(loc1, hex1.getLocation());
        Settlement settle1 = new Settlement(loc1, hex1.getNorthwest(),new Index(2));
        hex1.getNorthwest().setHassettlement(true);
        hex1.getNorthwest().setSettlement(settle1);
        settle1.setOwner(p1.getPlayerID());

        p1.addToSettlements(settle1);
        assertTrue(p1.canBuildRoadPiece(hex1, new EdgeLocation(loc1, EdgeDirection.NorthWest)));
    }

    private void initialize2() throws Exception
    {
        p2.setCurrentPlayer(true);
        loc2 = new HexLocation(0, -1);
        hex2 = CatanGame.singleton.getMymap().getHexes().get(loc2);
        Settlement settle2 = new Settlement(loc2, hex2.getNortheast(),new Index(2));
        hex2.getNortheast().setHassettlement(true);
        hex2.getNortheast().setSettlement(settle2);
        settle2.setOwner(p2.getPlayerID());

        p2.addToSettlements(settle2);
        assertTrue(p2.canBuildRoadPiece(hex2, new EdgeLocation(loc2, EdgeDirection.NorthEast)));
    }

    private void initialize3(Hex hex3, HexLocation hexLoc3) throws Exception
    {
        p1.setCurrentPlayer(true);
        Settlement settle3 = new Settlement(hexLoc3, hex3.getSouthwest(), p1.getPlayerID());
        hex3.getSouthwest().setHassettlement(true);
        hex3.getSouthwest().setSettlement(settle3);
        settle3.setOwner(p1.getPlayerID());
        p1.addToSettlements(settle3);
    }

    /**
     * A test case that should run successfully. Both types should NOT be water for this case.
     */
    @Test
    public void testSuccess_NeitherHexIsWater() throws Exception
    {
        initialize2();
        assertTrue(hex2.getResourcetype().equals(HexType.WOOD));
        assertTrue(p2.buildRoadPiece(hex2, new EdgeLocation(loc2, EdgeDirection.NorthEast)));
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

    /**
     * Of course, the test case fails if you cannot place a road piece there.
     */
    @Test
    public void testFail_CannotBuildRoadPiece() throws Exception
    {
        assertFalse(p1.buildRoadPiece(hex1, new EdgeLocation(loc1, EdgeDirection.NorthWest)));
    }

    /**
     * Building a road fails if both hexes are of type water.
     */
    @Test
    public void testFail_BothHexesAreWater() throws Exception
    {
        HexLocation hexLoc3 = new HexLocation(-3, 0);
        Hex hex3 = CatanGame.singleton.getMymap().getHexes().get(hexLoc3);
        // south:
        assertTrue(hex3.getResourcetype().equals(HexType.WATER));
        initialize3(hex3, hexLoc3);
        EdgeLocation location = new EdgeLocation(hexLoc3, EdgeDirection.South);
        assertFalse(p1.buildRoadPiece(hex3, location));
    }

    private static final String NAME1 = "Jenny";
    private static final String NAME2 = "Joy";
}
