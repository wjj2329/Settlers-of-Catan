package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.CatanColor;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.*;

import java.util.Map;

/**
 * Testing the canBuildRoad function in the Player class.
 * This function determines whether or not a particular player can
 * build a road on a particular hex.
 */
public class TestCanBuildRoadPiece
{
	/**
	 * The map we will be using
	 */
	private CatanMap sampleMap = null;

	/**
	 * All the hexes on the map
	 */
	private Map<HexLocation, Hex> hexes;

	/**
	 * Sample player
	 */
	private Player p1 = null;

	/**
	 * Overriding setUp function.
	 */
	@Before
	public void setUp() throws Exception
	{
		sampleMap = new CatanMap(RADIUS);
		hexes = sampleMap.getHexes();
		assertNotNull(sampleMap);
		assertNotNull(hexes);
		assertEquals(hexes.size(), EXPECTED_SIZE);
		p1 = new Player(NAME1, CatanColor.RED, new Index(0));
	}

	/**
	 * Overriding tearDown function.
	 */
	@After
	public void tearDown()
	{
		sampleMap = null;
		hexes = null;
		p1 = null;
	}

	/**
	 * Test: player should be able to place the road piece.
	 * The following conditions should be satisfied:
	 * 1. There must NOT be a road piece on the particular hex.
	 * 2. There must be at least 1 road, city, or settlement that
	 * 		is adjacent to the edge on which you want to place the road piece.
	 * 3. You must be the owner of at least one of the adjacent roads, cities, or settlements.
	 * 4. You must have sufficient resources: 1 brick, 1 lumber, and at least 1 remaining
	 *  road that you are able to build (because there is a max of 15).
	 *
	 *  It is VERY IMPORTANT that the setOwner function is called!
	 */
	@Test
	public void testSuccessfulCase() throws Exception
	{
		HexLocation loc1 = new HexLocation(-2, 0);
		Hex hex1 = hexes.get(loc1);
		// ensuring that .equals method is working properly for HexLocation
		assertEquals(loc1, hex1.getLocation());
		Settlement settle1 = new Settlement(loc1, hex1.getNorthwest());
		hex1.getNorthwest().setHassettlement(true);
		hex1.getNorthwest().setSettlement(settle1);
		// Do NOT forget this!
		settle1.setOwner(p1.getPlayerID());

		p1.addToSettlements(settle1);
		p1.getResources().setBrick(MIN);
		p1.getResources().setWood(MIN);
		assertTrue(p1.canBuildRoadPiece(hex1, new EdgeLocation(loc1, EdgeDirection.NorthWest)));
	}

	private static final int MIN = 1;
	private static final int RADIUS = 10;
	private static final int EXPECTED_SIZE = 37;
	private static final String NAME1 = "TSwift";
}
