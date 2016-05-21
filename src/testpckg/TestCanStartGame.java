/**
 * 
 */
package testpckg;

import static org.junit.Assert.*;

import client.main.Catan;
import client.model.ModelFacade;
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
		//ModelFacade.facadeCurrentGame.currentgame=new CatanGame();
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("William", CatanColor.ORANGE, new Index(3)));
		assertFalse(ModelFacade.facadeCurrentGame.currentgame.canStartGame());
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Dude", CatanColor.RED, new Index(2)));
		assertFalse(ModelFacade.facadeCurrentGame.currentgame.canStartGame());
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Brian", CatanColor.GREEN, new Index(4)));
		assertFalse(ModelFacade.facadeCurrentGame.currentgame.canStartGame());
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Ryan", CatanColor.WHITE, new Index(1)));
		assertTrue(ModelFacade.facadeCurrentGame.currentgame.canStartGame());
		ModelFacade.facadeCurrentGame.currentgame.setMymap(new CatanMap(1));
		assertTrue(ModelFacade.facadeCurrentGame.currentgame.canStartGame());
	}

	@Test
	public void test2() throws Exception
	{
		ModelFacade.facadeCurrentGame.currentgame.clear();
		ModelFacade.facadeCurrentGame.currentgame.setMymap(new CatanMap(2));
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("William", CatanColor.ORANGE, new Index(3)));
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Brian", CatanColor.GREEN, new Index(4)));
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Ryan", CatanColor.WHITE, new Index(1)));
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Ryan", CatanColor.WHITE, new Index(1)));
		assertFalse(ModelFacade.facadeCurrentGame.currentgame.canStartGame());
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Dude", CatanColor.RED, new Index(2)));
		assertTrue(ModelFacade.facadeCurrentGame.currentgame.canStartGame());
		exception.expect(Exception.class);
		ModelFacade.facadeCurrentGame.currentgame.addPlayer(new Player("Chris", CatanColor.BLUE, new Index(5)));
		ModelFacade.facadeCurrentGame.currentgame.canStartGame();
	}
}
