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
	/**
	 * Our sample player.
	 */
	private Player alex = null;

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
	 *
	 * Will put it below this comment.
	 */

	/**
	 * Saved variables:
	 */
	private static final String NAME = "Alex";

}
