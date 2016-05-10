/**
 * 
 */
package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * Tests the function that determines whether or not a dev card can be played.
 * @author Alex
 *
 */
public class TestCanBuyDevCard
{

	private Player william=new Player("william", CatanColor.BLUE, new Index(4));
	private Player alex=new Player("Alex",CatanColor.WHITE,new Index(2));
	private Player brian=new Player("Brain", CatanColor.BROWN, new Index(1));
	private Player spencer=new Player("Spencer", CatanColor.GREEN, new Index(3));
	private Player ryan=new Player("Ryan", CatanColor.ORANGE, new Index(4));

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		william.setResources(new ResourceList(1, 2, 3, 4, 5));
		alex.setResources(new ResourceList(0 ,1, 2, 0, 4));
		brian.setResources(new ResourceList(0, 0 , 0 ,0 ,0));
		spencer.setResources(new ResourceList(0, 0, 1,1, 1 ));
		ryan.setResources(new ResourceList(0 ,1, 1, 1, 0));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		
	}

	@Test
	public void test() 
	{
		assertTrue(william.canBuyDevCard());
		assertFalse(brian.canBuyDevCard());
		assertFalse(alex.canBuyDevCard());
		assertFalse(spencer.canBuyDevCard());
		assertTrue(ryan.canBuyDevCard());
	}

}
