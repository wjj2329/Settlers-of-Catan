package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.locations.HexLocation;

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
	}

	/**
	 * Overriding tearDown function.
	 */
	@After
	public void tearDown()
	{
		sampleMap = null;
		hexes = null;
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
	 */
	@Test
	public void testSuccessfulCase() throws Exception
	{
		Hex hex1 = hexes.get(new HexLocation(-2, 0));
		//assertEquals(new HexLocation(-2, 0), )
	}

	private static final int RADIUS = 10;
	private static final int EXPECTED_SIZE = 37;
}
