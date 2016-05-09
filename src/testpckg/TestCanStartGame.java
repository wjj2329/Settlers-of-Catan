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

		myhexes.add(new Hex(new HexLocation(-3, 2), HexType.WATER, 0, null));
		myhexes.add(new Hex(new HexLocation(-3, 1), HexType.WATER, 0, PortType.WOOD));
		myhexes.add(new Hex(new HexLocation(-3, 0), HexType.WATER, 0, null));
		myhexes.add(new Hex(new HexLocation(-3, -1), HexType.WATER, 0, PortType.THREE));

		myhexes.add(new Hex(new HexLocation(-2, -2), HexType.WATER, 0, null));
		myhexes.add(new Hex(new HexLocation(-2, -1), HexType.ORE, 5, null));
		myhexes.add(new Hex(new HexLocation(-2, 0), HexType.WHEAT, 2, null));
		myhexes.add(new Hex(new HexLocation(-2,1), HexType.WOOD, 6, null));
		myhexes.add(new Hex(new HexLocation(-2, 2), HexType.WATER, 0, PortType.BRICK));

		myhexes.add(new Hex(new HexLocation(-1,3), HexType.WATER, 0, null));
		myhexes.add(new Hex(new HexLocation(-1, 2), HexType.ORE, 3, null));
		myhexes.add(new Hex(new HexLocation(-1, 1), HexType.SHEEP, 9, null));
		myhexes.add(new Hex(new HexLocation(-1, 0), HexType.SHEEP, 10, null));
		myhexes.add(new Hex(new HexLocation(-1, -1), HexType.BRICK, 8, null));
		myhexes.add(new Hex(new HexLocation(-1, -2), HexType.WATER, 0, PortType.WHEAT));

		myhexes.add(new Hex(new HexLocation(0,3), HexType.WATER,0, PortType.THREE));
		myhexes.add(new Hex(new HexLocation(0, 2), HexType.WHEAT, 8, null));
		myhexes.add(new Hex(new HexLocation(0, 1), HexType.WOOD, 4, null));
		myhexes.add(new Hex(new HexLocation(0, 0), HexType.WHEAT, 11, null));
		myhexes.add(new Hex(new HexLocation(0, -1), HexType.WOOD, 3, null));
		myhexes.add(new Hex(new HexLocation(0, -2),HexType.DESERT,0, null));
		myhexes.add(new Hex(new HexLocation(0, -3), HexType.WATER, 0, null));

		myhexes.add(new Hex(new HexLocation(1, -3), HexType.WATER, 0, PortType.ORE));
		myhexes.add(new Hex(new HexLocation(1, -2), HexType.BRICK, 4, null));
		myhexes.add(new Hex(new HexLocation(1, -1), HexType.ORE, 9, null));
		myhexes.add(new Hex(new HexLocation(1, 0), HexType.BRICK, 5, null));
		myhexes.add(new Hex(new HexLocation(1, 1), HexType.SHEEP, 10, null));
		myhexes.add(new Hex(new HexLocation(1, 2), HexType.WATER, 0, null));

		myhexes.add(new Hex(new HexLocation(2, -2), HexType.WATER, 0, null));
		myhexes.add(new Hex(new HexLocation(2, -1), HexType.WOOD, 11, null));
		myhexes.add(new Hex(new HexLocation(2,0),HexType.SHEEP, 12, null));
		myhexes.add(new Hex(new HexLocation(2,1),HexType.WHEAT, 6, null));
		myhexes.add(new Hex(new HexLocation(2, 2), HexType.WATER,0, PortType.THREE));

		myhexes.add(new Hex(new HexLocation(3, 2), HexType.WATER, 0, null));
		myhexes.add(new Hex(new HexLocation(3, 1), HexType.WATER, 0, PortType.SHEEP));
		myhexes.add(new Hex(new HexLocation(3, 0), HexType.WATER, 0, null));
		myhexes.add(new Hex(new HexLocation(3, -1), HexType.WATER, 0, PortType.THREE));
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
