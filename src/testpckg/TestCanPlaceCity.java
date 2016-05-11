package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class TestCanPlaceCity {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	private Hex hex1;
	private Hex hex2;
	private Hex hex3;
	@Before
	public void setup() throws Exception {
		CatanGame.singleton.setMymap(new CatanMap(5));
		hex1=CatanGame.singleton.getMymap().getHexes().get(new HexLocation(0, 1));//a genric regular hex
		hex1.buildSettlement(new VertexLocation(new HexLocation(0, 1), VertexDirection.East));
		hex1.buildSettlement(new VertexLocation(new HexLocation(0, 1), VertexDirection.West));
		hex2=CatanGame.singleton.getMymap().getHexes().get(new HexLocation(-3, 2));//a water hex tile
		hex2.buildSettlement(new VertexLocation(new HexLocation(-3, 2), VertexDirection.East));
		hex3= CatanGame.singleton.getMymap().getHexes().get(new HexLocation(-2, 1));//a hex next to a water hex
		hex3.buildSettlement(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthWest));
		hex3.buildSettlement(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthWest));
		hex3.buildSettlement(new VertexLocation(new HexLocation(-2, 1), VertexDirection.East));
	}

	@Test
	public void test1()
	{
		assertTrue(hex1.canBuildCityHere(VertexDirection.East));
		assertTrue(hex1.canBuildCityHere(VertexDirection.West));
		assertFalse(hex1.canBuildCityHere(VertexDirection.NorthEast));
		assertFalse(hex1.canBuildCityHere(VertexDirection.NorthWest));
		assertFalse(hex1.canBuildCityHere(VertexDirection.SouthEast));
		assertFalse(hex1.canBuildCityHere(VertexDirection.SouthEast));
		hex1.buildCity(new VertexLocation(new HexLocation(0, 1), VertexDirection.East));
		assertFalse(hex1.canBuildCityHere(VertexDirection.East));
		hex1.buildCity(new VertexLocation(new HexLocation(0,1),VertexDirection.West));
		assertFalse(hex1.canBuildCityHere(VertexDirection.East));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(0, 1), VertexDirection.East)));

	}

	@Test
	public void test2()
	{
		assertTrue(hex2.canBuildCityHere(VertexDirection.East));
		assertFalse(hex2.canBuildCityHere(VertexDirection.NorthEast));
		assertFalse(hex2.canBuildCityHere(VertexDirection.West));
		assertFalse(hex2.canBuildCityHere(VertexDirection.SouthEast));
		assertFalse(hex2.canBuildCityHere(VertexDirection.NorthWest));
		assertFalse(hex2.canBuildCityHere(VertexDirection.SouthWest));
		hex2.buildCity(new VertexLocation(new HexLocation(-3, 2), VertexDirection.East));
		assertFalse(hex2.canBuildCityHere(VertexDirection.East));
	}

	@Test
	public void test3()
	{
		assertTrue(hex3.canBuildCityHere(VertexDirection.East));

		assertTrue(hex3.canBuildCityHere(VertexDirection.NorthWest));
		assertTrue(hex3.canBuildCityHere(VertexDirection.SouthWest));
		assertFalse(hex3.canBuildCityHere(VertexDirection.West));
		assertFalse(hex3.canBuildCityHere(VertexDirection.NorthEast));
		assertFalse(hex3.canBuildCityHere(VertexDirection.SouthEast));
		hex3.buildCity(new VertexLocation(new HexLocation(-2, -1), VertexDirection.East));
		hex3.buildCity(new VertexLocation(new HexLocation(-2,-1), VertexDirection.NorthWest));
		hex3.buildCity(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthWest));
		assertFalse(hex3.canBuildCityHere(VertexDirection.East));
		assertFalse(hex3.canBuildCityHere(VertexDirection.NorthWest));
		assertFalse(hex3.canBuildCityHere(VertexDirection.SouthWest));

	}



	@After
	public void teardown()
	{

	}
}
