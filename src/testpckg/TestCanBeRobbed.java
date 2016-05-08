package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.map.Robber;
import shared.game.player.Player;

/**
 * Tests our function which determines whether or not a player can be robbed.
 * @author Alex
 *
 */
public class TestCanBeRobbed 
{
	private Player alex = null;
	@Before
	public void setUp() throws Exception 
	{
		Robber.getSingleton().clear();
		alex = new Player("Alex", CatanColor.BLUE, new Index(0));
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}

	@Test
	public void testCurrentPlayerFails() throws Exception
	{
		alex.setCurrentPlayer(true);
		assertFalse(alex.canBeRobbed());
	}

	@Test
	public void testNoCardsFails() throws Exception
	{
		// Alex should have 0 resources by default, so this should return false successfully.
		alex.setCurrentPlayer(false);
		assertFalse(alex.canBeRobbed());
	}

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
	 */

}
