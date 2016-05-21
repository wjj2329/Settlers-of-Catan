/**
 * 
 */
package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.ResourceList;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.game.map.Index;
import shared.game.map.Port;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

/**
 * Tests our function which determines whether or not a hex can be placed.AKA is the Hex a Valid hex when formed.
 * @author William
 *
 */
public class TestCanPlaceHex 
{

	/**
	 * @throws java.lang.Exception
	 */
	private Hex hex1;
	private Hex hex2;
	private Hex hex3;
	@Before
	public void setUp() throws Exception 
	{
		hex1=new Hex(new HexLocation(3, 3), HexType.BRICK,new NumberToken(3), null);
		hex2=new Hex(new HexLocation(3, 3), HexType.WATER,new NumberToken(3), new Port(new HexLocation(3, 3), EdgeDirection.North,3,PortType.BRICK));
		hex3=new Hex(new HexLocation(3, 3), HexType.DESERT,new NumberToken(3), null);
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
	@Test
	public void test3()
	{
		Player myplayer=new Player("BOB", CatanColor.BLUE,new Index(3));
		myplayer.setResources(new ResourceList(4,4,3,2,1));
		System.out.println(myplayer.getResources().toString());
myplayer.buildRoadPiece(new Hex(new HexLocation(7,6), null, null, null),null);
		System.out.println(myplayer.getResources().toString());
	}


}
