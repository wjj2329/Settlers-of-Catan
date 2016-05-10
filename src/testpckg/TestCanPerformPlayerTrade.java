package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.game.map.Index;
import shared.game.player.Player;

import java.awt.*;

/**
 * Tests our function which determines whether or not a trade
 * between two players can be performed. 
 * 
 * @author Alex
 *
 */
public class TestCanPerformPlayerTrade 
{

	/**
	 * Sample players:
	 */
	private Player p1 = new Player(NAME1, CatanColor.WHITE, new Index(0));
	private Player p2 = new Player(NAME2, CatanColor.BROWN, new Index(1));
	private Player p3 = new Player(NAME3, CatanColor.RED, new Index(2));

	/**
	 * Overriding the setup method.
     */
	@Before
	public void setUp() throws Exception 
	{
		p1.getResources().setSheep(3);
		p2.getResources().setWood(2);
	}

	/**
	 * Overriding the teardown method.
	 * @throws Exception
     */
	@After
	public void tearDown() throws Exception 
	{

	}

	/**
	 * Test method. Should fail if it's not player 1's turn.
	 */
	@Test
	public void testFail_NotPlayer1Turn() throws Exception
	{
		p1.setCurrentPlayer(false);
		p1.getResources().setSheep(3);
		p2.getResources().setWood(2);
		assertFalse(p1.canRequestTrade(p2, 2, 2, ResourceType.WOOD, ResourceType.SHEEP));
	}

	@Test
	public void testSuccess_Player1Turn() throws Exception
	{
		p1.setCurrentPlayer(true);
		assertTrue(p1.canRequestTrade(p2, 2, 2, ResourceType.WOOD, ResourceType.SHEEP));
	}

	/**
	 * The trade cannot be performed if it's player 2's turn!
	 */
	@Test
	public void testFail_Player2Turn() throws Exception
	{
		p2.setCurrentPlayer(true);
		assertFalse(p1.canRequestTrade(p2, 2, 2, ResourceType.WOOD, ResourceType.SHEEP));
	}

	@Test
	public void testSuccess_NotPlayer2Turn() throws Exception
	{
		//p2.setCurrentPlayer(false);
		p1.setCurrentPlayer(true);
		assertTrue(p1.canRequestTrade(p2, 2, 2, ResourceType.WOOD, ResourceType.SHEEP));
	}

	/**
	 * Amount that the player is sending/offering must NOT be more than what they have!
	 */

	@Test
	public void testFail_OfferingTooMuch() throws Exception
	{
		p1.setCurrentPlayer(true);
		assertFalse(p1.canRequestTrade(p2, 2, 4, ResourceType.WOOD, ResourceType.SHEEP));
	}

	@Test
	public void testSuccess_OfferingEnough() throws Exception
	{
		//p1.setCurrentPlayer(false);
		p3.setCurrentPlayer(true);
		p3.getResources().setOre(2);
		assertTrue(p3.canRequestTrade(p1, 1, 2, ResourceType.SHEEP, ResourceType.ORE));
	}

	/**
	 * We may need to adjust the following two functions because the player actually can REQUEST as much as they want,
	 * but the other player can't accept it if they have insufficient resources.
	 */
	@Test
	public void testFail_OtherPlayerCannotTrade() throws Exception
	{
		p3.setCurrentPlayer(true);
		p3.getResources().setOre(2);
		assertFalse(p3.canRequestTrade(p2, 7, 2, ResourceType.SHEEP, ResourceType.ORE));
	}

	@Test
	public void testSuccess_OtherPlayerCanTrade() throws Exception
	{
		p3.setCurrentPlayer(true);
		p3.getResources().setOre(2);
		assertTrue(p3.canRequestTrade(p2, 2, 1, ResourceType.WOOD, ResourceType.ORE));
	}

	/**
	 * Saved constants follow.
	 */
	private static final String NAME1 = "IceBear";
	private static final String NAME2 = "Grizz";
	private static final String NAME3 = "Panda";
}
