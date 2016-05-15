package testpckg;

import static org.junit.Assert.*;

import client.main.Catan;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.game.map.Index;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.util.HashMap;
import java.util.Set;

/**
 * tests the hex for placement on hex as opposed to if the players has resources to build said settlement
 */
public class TestCanPlaceSettlement {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	private Hex hex1;
	private Hex hex2;
	private Hex hex3;
	private Hex hex4;

	@Before
	public void setup() {
		CatanGame.singleton.setMymap(new CatanMap(5));
		hex1 = CatanGame.singleton.getMymap().getHexes().get(new HexLocation(0, 1));//a genric regular hex
		hex2 = CatanGame.singleton.getMymap().getHexes().get(new HexLocation(-3, 2));//a water hex tile
		hex3 = CatanGame.singleton.getMymap().getHexes().get(new HexLocation(-2, 0));//A regular tile that borders a water tile
		hex4 = CatanGame.singleton.getMymap().getHexes().get(new HexLocation(500, 20));//invalid hex
	}

	@After
	public void teardown() {
		CatanGame.singleton.clear();
	}

	@Test
	public void test() throws Exception {
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.West)));
		hex1.buildSettlement(new VertexLocation(new HexLocation(1, 1), VertexDirection.West), new Index(2));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.West)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthWest)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthWest)));
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.East)));
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthEast)));
		assertTrue(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthEast)));
		hex1.buildSettlement(new VertexLocation(new HexLocation(1, 1), VertexDirection.East), new Index(2));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.West)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthWest)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthWest)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.East)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.SouthEast)));
		assertFalse(hex1.canBuildSettlementHere(new VertexLocation(new HexLocation(1, 1), VertexDirection.NorthEast)));
	}

	@Test
	public void test2() throws Exception {
		exception.expect(Exception.class);
		hex2.buildSettlement(new VertexLocation(new HexLocation(-3, 2), VertexDirection.West), new Index(2));
		exception.expect(Exception.class);
		hex2.buildSettlement(new VertexLocation(new HexLocation(-3, 2), VertexDirection.SouthWest), new Index(2));
		exception.expect(Exception.class);
		hex2.buildSettlement(new VertexLocation(new HexLocation(-3, 2), VertexDirection.NorthWest), new Index(2));
		assertTrue(hex2.canBuildSettlementHere(new VertexLocation(new HexLocation(-3, 2), VertexDirection.East)));
		assertTrue(hex2.canBuildSettlementHere(new VertexLocation(new HexLocation(-3, 2), VertexDirection.SouthEast)));
		assertTrue(hex2.canBuildSettlementHere(new VertexLocation(new HexLocation(-3, 2), VertexDirection.NorthEast)));
		hex2.buildSettlement(new VertexLocation(new HexLocation(-3, 2), VertexDirection.East), new Index(2));
		assertFalse(hex2.canBuildSettlementHere(new VertexLocation(new HexLocation(-3, 2), VertexDirection.East)));
		assertFalse(hex2.canBuildSettlementHere(new VertexLocation(new HexLocation(-3, 2), VertexDirection.SouthEast)));
		assertFalse(hex2.canBuildSettlementHere(new VertexLocation(new HexLocation(-3, 2), VertexDirection.NorthEast)));
	}

	@Test
	public void test3() throws Exception {
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.East)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthEast)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthWest)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthEast)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthWest)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.West)));
		hex3.buildSettlement(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthEast), new Index(2));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.East)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthEast)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthWest)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthEast)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthWest)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.West)));
		hex3.buildSettlement(new VertexLocation(new HexLocation(-2, 1), VertexDirection.West), new Index(2));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.East)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthEast)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthWest)));
		assertTrue(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthEast)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthWest)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.West)));
		hex3.buildSettlement(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthEast), new Index(2));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.East)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthEast)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.NorthWest)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthEast)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.SouthWest)));
		assertFalse(hex3.canBuildSettlementHere(new VertexLocation(new HexLocation(-2, 1), VertexDirection.West)));
	}

	@Test
	public void test4() throws Exception {
		exception.expect(Exception.class);
		hex4.buildSettlement(new VertexLocation(new HexLocation(500, 20), VertexDirection.East), new Index(2));
	}

}
