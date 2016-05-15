/**
 * 
 */
package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.definitions.TradeType;
import shared.game.Bank;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * Tests our function which determines whether or not a maritime trade (or any
 * other kind of trade) can be
 * performed with the bank. For example, the bank (or the player) may not
 * have sufficient resources to perform the trade, in which case our 
 * canPerformMaritimeTrade() function will fail. This test should account for
 * both cases (i.e., success and failure of the function).
 *
 * @author Alex
 *
 */
public class TestCanPerformBankTrade 
{

	/**
	 * Our sample player
	 */
	private Player kesha = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		kesha = new Player(NAME, CatanColor.PUCE, new Index(0));
		Bank.getSingleton().clear();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		Bank.getSingleton().clear();
	}

	@Test
	public void test2WayTradeYes() throws Exception
	{
		kesha.getResources().setBrick(2);
		Bank.getSingleton().getCardslist().setWood(1);
		assertEquals(kesha.getResources().getBrick(), 2);
		assertEquals(Bank.getSingleton().getCardslist().getWood(), 1);
		assertTrue(kesha.canDoTradeWithBank(TradeType.BRICK, TradeType.BLANK, ResourceType.WOOD));
	}

	@Test
	public void test2WayTradeNo() throws Exception
	{
		assertFalse(kesha.canDoTradeWithBank(TradeType.SHEEP, TradeType.BLANK, ResourceType.WOOD));
		assertFalse(kesha.canDoTradeWithBank(TradeType.BRICK, TradeType.BLANK, ResourceType.SHEEP));
	}

	@Test
	public void test3WayTradeYes() throws Exception
	{
		kesha.getResources().setOre(3);
		Bank.getSingleton().getCardslist().setWood(1);
		assertTrue(kesha.canDoTradeWithBank(TradeType.THREE, TradeType.ORE, ResourceType.WOOD));
	}

	@Test
	public void test3WayTradeNo() throws Exception
	{
		kesha.getResources().setWood(2);
		assertFalse(kesha.canDoTradeWithBank(TradeType.THREE, TradeType.WOOD, ResourceType.WOOD));
		kesha.getResources().setWood(kesha.getResources().getWood() + 1);
		assertFalse(kesha.canDoTradeWithBank(TradeType.THREE, TradeType.WOOD, ResourceType.BRICK));
	}

	@Test
	public void test4WayTradeYes() throws Exception
	{
		kesha.getResources().setSheep(7);
		Bank.getSingleton().getCardslist().setWheat(1);
		assertTrue(kesha.canDoTradeWithBank(TradeType.FOUR, TradeType.SHEEP, ResourceType.WHEAT));
	}

	@Test
	public void test4WayTradeNo() throws Exception
	{
		kesha.getResources().setWheat(1);
		Bank.getSingleton().getCardslist().setSheep(1);
		assertFalse(kesha.canDoTradeWithBank(TradeType.FOUR, TradeType.WHEAT, ResourceType.SHEEP));
		assertFalse(kesha.canDoTradeWithBank(TradeType.FOUR, TradeType.SHEEP, ResourceType.BRICK));
	}

	private static final String NAME = "Ke$ha";
}
