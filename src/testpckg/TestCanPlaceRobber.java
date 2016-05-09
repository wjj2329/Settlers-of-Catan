package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.locations.HexLocation;

/**
 * TestCanPlaceRobber: Tests our method that determines whether or not a 
 * robber can be placed in a particular hex location. 
 * @author Alex
 *
 */
public class TestCanPlaceRobber 
{

	private Hex hextest1;
	private Hex hextest2;
	private Hex hextest3;
	@Before
	public void setUp() throws Exception 
	{
		hextest1=new Hex(new HexLocation(-3, 3), HexType.BRICK, new NumberToken(4), PortType.BLANK);
		hextest2=new Hex(new HexLocation(-2, 2), HexType.WATER, new NumberToken(0), PortType.BLANK);
		hextest3=new Hex(new HexLocation(1,1), HexType.WATER, new NumberToken(0), PortType.BRICK);
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}
	
	@Test
	public void test() 
	{
		assertTrue(hextest1.CanPlaceRobber());
		assertFalse(hextest2.CanPlaceRobber());
		assertFalse(hextest3.CanPlaceRobber());
	}

}
