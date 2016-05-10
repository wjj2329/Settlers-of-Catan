package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.ResourceList;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.game.map.Index;
import shared.game.map.Robber;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests our function which determines whether or not a player can be robbed.
 * @author Alex
 *
 */
public class TestCanBeRobbed 
{
	/**
	 * Our sample player.
	 */
	private Player alex = null;

	/**
	 * All the hex locations!
	 */
	private HexLocation hexLoc1 = null;
	private HexLocation hexLoc2 = null;
	private HexLocation hexLoc3 = null;
	private HexLocation hexLoc4 = null;
	private HexLocation hexLoc5 = null;
	private HexLocation hexLoc6 = null;
	private HexLocation hexLoc7 = null;
	private HexLocation hexLoc8 = null;
	private HexLocation hexLoc9 = null;
	private HexLocation hexLoc10 = null;
	private HexLocation hexLoc11 = null;
	private HexLocation hexLoc12 = null;
	private HexLocation hexLoc13 = null;
	private HexLocation hexLoc14 = null;
	private HexLocation hexLoc15 = null;
	private HexLocation hexLoc16 = null;
	private HexLocation hexLoc17 = null;
	private HexLocation hexLoc18 = null;
	private HexLocation hexLoc19 = null;
	private HexLocation hexLoc20 = null;
	private HexLocation hexLoc21 = null;
	private HexLocation hexLoc22 = null;
	private HexLocation hexLoc23 = null;
	private HexLocation hexLoc24 = null;
	private HexLocation hexLoc25 = null;
	private HexLocation hexLoc26 = null;
	private HexLocation hexLoc27 = null;
	private HexLocation hexLoc28 = null;
	private HexLocation hexLoc29 = null;
	private HexLocation hexLoc30 = null;
	private HexLocation hexLoc31 = null;
	private HexLocation hexLoc32 = null;
	private HexLocation hexLoc33 = null;
	private HexLocation hexLoc34 = null;
	private HexLocation hexLoc35 = null;
	private HexLocation hexLoc36 = null;
	private HexLocation hexLoc37 = null;

	/**
	 * SetUp: Initializing the basic setup for the JUnit test.
     */
	@Before
	public void setUp() throws Exception 
	{
		Robber.getSingleton().clear();
		alex = new Player(NAME, CatanColor.BLUE, new Index(0));
	}

	/**
	 * Overriding tearDown function.
     */
	@After
	public void tearDown() throws Exception 
	{
		
	}

	/**
	 * If a user is the current player, they cannot be robbed.
     */
	@Test
	public void testCurrentPlayerFails() throws Exception
	{
		alex.setCurrentPlayer(true);
		assertFalse(alex.canBeRobbed());
	}

	/**
	 * If the player doesn't have any resources, they should be immune to robbing.
	 * You can't steal from someone who has nothing.
	 * Even hobos.
     */
	@Test
	public void testNoCardsFails() throws Exception
	{
		// Alex should have 0 resources by default, so this should return false successfully.
		alex.setCurrentPlayer(false);
		assertFalse(alex.canBeRobbed());
	}

	/**
	 * If the player does have cards, the Robber can rob them.
     */
	@Test
	public void testHasCardsSucceeds() throws Exception
	{
		ResourceList resList = new ResourceList();
		resList.setSheep(1);
		alex.setResources(resList);
		assertTrue(alex.canBeRobbed());
	}

	/**
	 * Will need another test when we see if current player has a Structure built on the hex that the
	 * Robber is currently on.
	 * In this test, the player will have a settlement that is nearby the robber, but not on the same hex,
	 * so they should not be able to be robbed.
	 */
	@Test
	public void testDoesNotHaveStructureFails() throws Exception
	{
		CatanMap sampleMap = new CatanMap(RADIUS);
		Map<HexLocation, Hex> theHexesOfMap = new HashMap<>();
		loadMyMap(theHexesOfMap);
		Settlement settle1 = new Settlement(hexLoc10, new VertexLocation(hexLoc10, VertexDirection.East));
		alex.addToSettlements(settle1);
		Robber.getSingleton().setLocation(hexLoc11);
		assertFalse(alex.canBeRobbed());
		City city1 = new City(hexLoc10, new VertexLocation(hexLoc10, VertexDirection.East));
		alex.addToCities(city1);
		assertFalse(alex.canBeRobbed());
	}

	/**
	 * Should succeed because it's at the same location as the robber.
     */
	@Test
	public void testDoesHaveStructureSucceeds() throws Exception
	{
		CatanMap sampleMap = new CatanMap(RADIUS);
		Map<HexLocation, Hex> theHexesOfMap = new HashMap<>();
		loadMyMap(theHexesOfMap);
		Settlement settle1 = new Settlement(hexLoc8, new VertexLocation(hexLoc8, VertexDirection.SouthWest));
		alex.addToSettlements(settle1);
		Robber.getSingleton().setLocation(hexLoc8);
		assertTrue(alex.canBeRobbed());
		Settlement settle2 = new Settlement(hexLoc1, new VertexLocation(hexLoc1, VertexDirection.NorthWest));

		alex.getSettlements().clear();
		assertFalse(alex.canBeRobbed());
		alex.addToSettlements(settle2);
		City city1 = new City(hexLoc1, new VertexLocation(hexLoc1, VertexDirection.NorthWest));
		alex.addToCities(city1);
		Robber.getSingleton().setLocation(hexLoc1);
		assertTrue(alex.canBeRobbed());
	}

	private void loadMyMap(Map<HexLocation, Hex> hexes)
	{
		hexLoc1 = new HexLocation(-3, 2);
		hexLoc2 = new HexLocation(-3, 1);
		hexLoc3 = new HexLocation(-3, 0);
		hexLoc4 = new HexLocation(-3, -1);

		hexLoc5 = new HexLocation(-2, -2);
		hexLoc6 = new HexLocation(-2, -1);
		hexLoc7 = new HexLocation(-2, 0);
		hexLoc8 = new HexLocation(-2, 1);
		hexLoc9 = new HexLocation(-2, 2);

		hexLoc10 = new HexLocation(-1, 3);
		hexLoc11 = new HexLocation(-1, 2);
		hexLoc12 = new HexLocation(-1, 1);
		hexLoc13 = new HexLocation(-1, 0);
		hexLoc14 = new HexLocation(-1, -1);
		hexLoc15 = new HexLocation(-1, -2);

		hexLoc16 = new HexLocation(0,3);
		hexLoc17 =  new HexLocation(0, 2);
		hexLoc18 = new HexLocation(0, 1);
		hexLoc19 = new HexLocation(0, 0);
		hexLoc20 = new HexLocation(0, -1);
		hexLoc21 = new HexLocation(0, -2);
		hexLoc22 = new HexLocation(0, -3);

		hexLoc23 = new HexLocation(1, -3);
		hexLoc24 = new HexLocation(1, -2);
		hexLoc25 = new HexLocation(1, -1);
		hexLoc26 = new HexLocation(1, 0);
		hexLoc27 = new HexLocation(1, 1);
		hexLoc28 = new HexLocation(1, 2);

		hexLoc29 = new HexLocation(2, -2);
		hexLoc30 = new HexLocation(2, -1);
		hexLoc31 = new HexLocation(2,0);
		hexLoc32 = new HexLocation(2,1);
		hexLoc33 = new HexLocation(2, 2);

		hexLoc34 = new HexLocation(3, 2);
		hexLoc35 = new HexLocation(3, 1);
		hexLoc36 = new HexLocation(3, 0);
		hexLoc37 = new HexLocation(3, -1);

		hexes.put(hexLoc1, new Hex(hexLoc1, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc2, new Hex(hexLoc2, HexType.WATER, new NumberToken(0), PortType.WOOD));
		hexes.put(hexLoc3, new Hex(hexLoc3, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc4, new Hex(hexLoc4, HexType.WATER, new NumberToken(0), PortType.THREE));

		hexes.put(hexLoc5, new Hex(hexLoc5, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc6, new Hex(hexLoc6, HexType.ORE, new NumberToken(5), PortType.BLANK));
		hexes.put(hexLoc7, new Hex(hexLoc7, HexType.WHEAT, new NumberToken(2), PortType.BLANK));
		hexes.put(hexLoc8, new Hex(hexLoc8, HexType.WOOD, new NumberToken(6), PortType.BLANK));
		hexes.put(hexLoc9, new Hex(hexLoc9, HexType.WATER, new NumberToken(0), PortType.BRICK));

		hexes.put(hexLoc10, new Hex(hexLoc10, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc11, new Hex(hexLoc11, HexType.ORE, new NumberToken(3), PortType.BLANK));
		hexes.put(hexLoc12, new Hex(hexLoc12, HexType.SHEEP, new NumberToken(9), PortType.BLANK));
		hexes.put(hexLoc13, new Hex(hexLoc13, HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		hexes.put(hexLoc14, new Hex(hexLoc14, HexType.BRICK, new NumberToken(8), PortType.BLANK));
		hexes.put(hexLoc15, new Hex(hexLoc15, HexType.WATER, new NumberToken(0), PortType.WHEAT));

		hexes.put(hexLoc16, new Hex(hexLoc16, HexType.WATER,new NumberToken(0), PortType.THREE));
		hexes.put(hexLoc17, new Hex(hexLoc17, HexType.WHEAT, new NumberToken(8), PortType.BLANK));
		hexes.put(hexLoc18, new Hex(hexLoc18, HexType.WOOD, new NumberToken(4), PortType.BLANK));
		hexes.put(hexLoc19, new Hex(hexLoc19, HexType.WHEAT, new NumberToken(11), PortType.BLANK));
		hexes.put(hexLoc20, new Hex(hexLoc20, HexType.WOOD, new NumberToken(3), PortType.BLANK));
		hexes.put(hexLoc21, new Hex(hexLoc21,HexType.DESERT,new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc22, new Hex(hexLoc22, HexType.WATER, new NumberToken(0), PortType.BLANK));

		hexes.put(hexLoc23, new Hex(hexLoc23, HexType.WATER, new NumberToken(0), PortType.ORE));
		hexes.put(hexLoc24, new Hex(hexLoc24, HexType.BRICK, new NumberToken(4), PortType.BLANK));
		hexes.put(hexLoc25, new Hex(hexLoc25, HexType.ORE, new NumberToken(9), PortType.BLANK));
		hexes.put(hexLoc26, new Hex(hexLoc26, HexType.BRICK, new NumberToken(5), PortType.BLANK));
		hexes.put(hexLoc27, new Hex(hexLoc27, HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		hexes.put(hexLoc28, new Hex(hexLoc28, HexType.WATER, new NumberToken(0), PortType.BLANK));

		hexes.put(hexLoc29, new Hex(hexLoc29, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc30, new Hex(hexLoc30, HexType.WOOD, new NumberToken(11), PortType.BLANK));
		hexes.put(hexLoc31, new Hex(hexLoc31,HexType.SHEEP, new NumberToken(12), PortType.BLANK));
		hexes.put(hexLoc32, new Hex(hexLoc32,HexType.WHEAT, new NumberToken(6), PortType.BLANK));
		hexes.put(hexLoc33, new Hex(hexLoc33, HexType.WATER,new NumberToken(0), PortType.THREE));

		hexes.put(hexLoc34, new Hex(hexLoc34, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc35, new Hex(hexLoc35, HexType.WATER, new NumberToken(0), PortType.SHEEP));
		hexes.put(hexLoc36, new Hex(hexLoc36, HexType.WATER, new NumberToken(0), PortType.BLANK));
		hexes.put(hexLoc37, new Hex(hexLoc37, HexType.WATER, new NumberToken(0), PortType.THREE));
	}

	/**
	 * Saved variables:
	 */
	private static final String NAME = "Alex";
	// Just a sample size since it shouldn't really matter for this particular test
	private static final int RADIUS = 10;

}
