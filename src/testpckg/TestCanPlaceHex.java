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
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.locations.HexLocation;

/**
 * Tests our function which determines whether or not a hex can be placed.
 * @author Alex
 *
 */
public class TestCanPlaceHex 
{

	/**
	 * @throws java.lang.Exception
	 */
	Hex hex1;
	Hex hex2;
	Hex hex3;
	@Before
	public void setUp() throws Exception 
	{
		hex1=new Hex(new HexLocation(3, 3), HexType.BRICK,new NumberToken(3), PortType.BLANK);
		hex2=new Hex(new HexLocation(3, 3), HexType.WATER,new NumberToken(3), PortType.WHEAT);
		hex3=new Hex(new HexLocation(3, 3), HexType.DESERT,new NumberToken(3), PortType.BLANK);
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
		assertTrue(hex1.CanPlaceHex());
		assertFalse(hex2.CanPlaceHex());
		assertFalse(hex3.CanPlaceHex());

	}

}
