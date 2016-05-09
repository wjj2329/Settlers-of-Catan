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


		//this is the way the map will look by default if shuffling is not enabled. 
		myhexes.add(new Hex(new HexLocation(-3, 2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-3, 1), HexType.WATER, new NumberToken(0), PortType.WOOD));
		myhexes.add(new Hex(new HexLocation(-3, 0), HexType.WATER, new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-3, -1), HexType.WATER, new NumberToken(0), PortType.THREE));

		myhexes.add(new Hex(new HexLocation(-2, -2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-2, -1), HexType.ORE, new NumberToken(5), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-2, 0), HexType.WHEAT, new NumberToken(2), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-2,1), HexType.WOOD, new NumberToken(6), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-2, 2), HexType.WATER, new NumberToken(0), PortType.BRICK));

		myhexes.add(new Hex(new HexLocation(-1,3), HexType.WATER, new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-1, 2), HexType.ORE, new NumberToken(3), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-1, 1), HexType.SHEEP, new NumberToken(9), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-1, 0), HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-1, -1), HexType.BRICK, new NumberToken(8), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(-1, -2), HexType.WATER, new NumberToken(0), PortType.WHEAT));

		myhexes.add(new Hex(new HexLocation(0,3), HexType.WATER,new NumberToken(0), PortType.THREE));
		myhexes.add(new Hex(new HexLocation(0, 2), HexType.WHEAT, new NumberToken(8), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(0, 1), HexType.WOOD, new NumberToken(4), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(0, 0), HexType.WHEAT, new NumberToken(11), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(0, -1), HexType.WOOD, new NumberToken(3), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(0, -2),HexType.DESERT,new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(0, -3), HexType.WATER, new NumberToken(0), PortType.BLANK));

		myhexes.add(new Hex(new HexLocation(1, -3), HexType.WATER, new NumberToken(0), PortType.ORE));
		myhexes.add(new Hex(new HexLocation(1, -2), HexType.BRICK, new NumberToken(4), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(1, -1), HexType.ORE, new NumberToken(9), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(1, 0), HexType.BRICK, new NumberToken(5), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(1, 1), HexType.SHEEP, new NumberToken(10), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(1, 2), HexType.WATER, new NumberToken(0), PortType.BLANK));

		myhexes.add(new Hex(new HexLocation(2, -2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(2, -1), HexType.WOOD, new NumberToken(11), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(2,0),HexType.SHEEP, new NumberToken(12), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(2,1),HexType.WHEAT, new NumberToken(6), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(2, 2), HexType.WATER,new NumberToken(0), PortType.THREE));

		myhexes.add(new Hex(new HexLocation(3, 2), HexType.WATER, new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(3, 1), HexType.WATER, new NumberToken(0), PortType.SHEEP));
		myhexes.add(new Hex(new HexLocation(3, 0), HexType.WATER, new NumberToken(0), PortType.BLANK));
		myhexes.add(new Hex(new HexLocation(3, -1), HexType.WATER, new NumberToken(0), PortType.THREE));
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
