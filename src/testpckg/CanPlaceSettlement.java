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

public class CanPlaceSettlement {


	Hex hex1;
	Hex hex2;
	Hex hex3;
	@Before
	public void setup()
	{
		hex1=new Hex(new HexLocation(1, 1), HexType.BRICK, new NumberToken(3), PortType.BLANK);
		hex2=new Hex(new HexLocation(0, 0), HexType.SHEEP, new NumberToken(2), PortType.BLANK);
		hex3=new Hex(new HexLocation(-1, 1), HexType.ORE, new NumberToken(1),PortType.BLANK);
	}

	@After
	public void teardown()
	{

	}

	@Test
	public void test()
	{
	}

}
