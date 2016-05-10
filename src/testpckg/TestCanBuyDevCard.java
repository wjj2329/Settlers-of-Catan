/**
 * 
 */
package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * Tests the function that determines whether or not a dev card can be played.
 * @author Alex
 *
 */
public class TestCanBuyDevCard
{

	private Player William=new Player("william", CatanColor.BLUE, new Index(4));
	private Player Alex=new Player("Alex",CatanColor.WHITE,new Index(2));
	private Player Brian=new Player("Brain", CatanColor.BROWN, new Index(1));
	private Player Spencer=new Player("Spencer", CatanColor.GREEN, new Index(3));

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		
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

	}

}
