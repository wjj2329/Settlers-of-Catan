/**
 * 
 */
package testpckg;

import static org.junit.Assert.*;

import client.main.Catan;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Hex.NumberToken;
import shared.game.map.Index;
import shared.game.map.Port;
import shared.game.player.Player;
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
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		
	}

	@Test
	public void test1() throws Exception {
		CatanGame.singleton=new CatanGame();
		CatanGame.singleton.addPlayer(new Player("William", CatanColor.ORANGE, new Index(3)));
		assertFalse(CatanGame.singleton.canStartGame());
		CatanGame.singleton.addPlayer(new Player("Dude", CatanColor.RED, new Index(2)));
		assertFalse(CatanGame.singleton.canStartGame());
		CatanGame.singleton.addPlayer(new Player("Brian", CatanColor.GREEN, new Index(4)));
		assertFalse(CatanGame.singleton.canStartGame());
		CatanGame.singleton.addPlayer(new Player("Ryan", CatanColor.WHITE, new Index(1)));
		assertTrue(CatanGame.singleton.canStartGame());
		CatanGame.singleton.setMymap(new CatanMap(1));
		assertTrue(CatanGame.singleton.canStartGame());
	}

	@Test
	public void test2() throws Exception
	{
		CatanGame.singleton.clear();
		CatanGame.singleton.setMymap(new CatanMap(2));
		CatanGame.singleton.addPlayer(new Player("William", CatanColor.ORANGE, new Index(3)));
		CatanGame.singleton.addPlayer(new Player("Brian", CatanColor.GREEN, new Index(4)));
		CatanGame.singleton.addPlayer(new Player("Ryan", CatanColor.WHITE, new Index(1)));
		CatanGame.singleton.addPlayer(new Player("Ryan", CatanColor.WHITE, new Index(1)));
		assertFalse(CatanGame.singleton.canStartGame());
		CatanGame.singleton.addPlayer(new Player("Dude", CatanColor.RED, new Index(2)));
		assertTrue(CatanGame.singleton.canStartGame());
		exception.expect(Exception.class);
		CatanGame.singleton.addPlayer(new Player("Chris", CatanColor.BLUE, new Index(5)));
		CatanGame.singleton.canStartGame();
	}
}
