package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
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
	Player p1 = new Player(NAME1, CatanColor.WHITE, new Index(0));

	Player p2 = new Player(NAME2, CatanColor.BROWN, new Index(1));

	/**
	 * Overriding the setup method.
     */
	@Before
	public void setUp() throws Exception 
	{

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
	 * Test method.
	 */
	@Test
	public void test() 
	{
		fail("Not yet implemented");
	}

	/**
	 * Saved constants follow.
	 */
	private static final String NAME1 = "IceBear";
	private static final String NAME2 = "Grizz";
	private static final String NAME3 = "Panda";
}
