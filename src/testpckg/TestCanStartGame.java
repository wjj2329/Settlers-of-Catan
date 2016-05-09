/**
 * 
 */
package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.game.map.Port;
import shared.locations.HexLocation;

import java.util.ArrayList;

/**
 * TestCanStartGame: Tests our function that determines whether or not the 
 * game can be started.
 * @author Alex
 *
 */
public class TestCanStartGame 
{

	private CatanGame mygame=new CatanGame();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		CatanMap mymap=new CatanMap(0);
		CatanMap mymap2=new CatanMap(0);
		CatanMap mymap3=new CatanMap(0);
		ArrayList<Hex>myhexes=new ArrayList<>();
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
