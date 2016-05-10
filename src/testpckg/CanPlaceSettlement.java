package testpckg;

import static org.junit.Assert.*;

import client.main.Catan;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CanPlaceSettlement {


	Hex hex1;
	Hex hex2;
	Hex hex3;
	@Before
	public void setup()
	{
		CatanGame.singleton.setMymap(new CatanMap(5));
		hex1=CatanGame.singleton.getMymap().getHexes().get(new HexLocation(0, 1));//a genric regular hex
		hex2=CatanGame.singleton.getMymap().getHexes().get(new HexLocation(1, 0));//a water hex
		hex3= CatanGame.singleton.getMymap().getHexes().get(new HexLocation(1, 1));//a hex next to a water hex
	}

	@After
	public void teardown()
	{

	}

	@Test
	public void test()
	{
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.West)));
		hex1.buildSettlement(new VertexLocation(new HexLocation(1, 1), VertexDirection.West));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.West)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthWest)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthWest)));
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1),VertexDirection.East)));
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthEast)));
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthEast)));
		hex1.buildSettlement(new VertexLocation(new HexLocation(1, 1), VertexDirection.East));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.West)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthWest)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthWest)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.East)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthEast)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthEast)));
	}

	@Test
	public void test2()
	{

	}

}
