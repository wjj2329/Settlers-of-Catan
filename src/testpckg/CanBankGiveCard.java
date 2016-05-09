package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.ResourceList;

public class CanBankGiveCard {


	/*
	Test the can bank give card function and I create 3 different
	kinds of bank objects to test this with.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/*private Bank mybank1=new Bank();
	private Bank mybank2=new Bank();
	private Bank mybank3=new Bank();*/

	@Before
	public void setUp()throws Exception
	{
		Bank.getSingleton().clear();
		ResourceList mylist=new ResourceList();
		mylist.setBrick(4);
		mylist.setOre(3);
		mylist.setSheep(2);
		mylist.setWheat(10);
		mylist.setWood(0);
		Bank.getSingleton().setCardslist(mylist);
	}

	@After
	public void tearDown() throws Exception
	{
		Bank.getSingleton().clear();
	}
	@Test
	public void testBank1()  throws Exception
	{
		assertTrue(true);
		assertTrue(Bank.getSingleton().CanBankGiveCard(ResourceType.BRICK));
		assertFalse(Bank.getSingleton().CanBankGiveCard(ResourceType.WOOD));
		assertTrue(Bank.getSingleton().CanBankGiveCard(ResourceType.WHEAT));
		assertTrue(Bank.getSingleton().CanBankGiveCard(ResourceType.SHEEP));
		assertTrue(Bank.getSingleton().CanBankGiveCard(ResourceType.ORE));
	}

	@Test
	public void testBank2() throws Exception
	{
		Bank.getSingleton().clear();
		Bank.getSingleton().setCardslist(0, 0 , 0 , 50, 25);
		assertFalse(Bank.getSingleton().CanBankGiveCard(ResourceType.BRICK));
		assertTrue(Bank.getSingleton().CanBankGiveCard(ResourceType.WOOD));
		assertTrue(Bank.getSingleton().CanBankGiveCard(ResourceType.ORE));
		assertFalse(Bank.getSingleton().CanBankGiveCard(ResourceType.WHEAT));
		assertFalse(Bank.getSingleton().CanBankGiveCard(ResourceType.SHEEP));
	}

	@Test
	public void testBank3() throws Exception
	{
		Bank.getSingleton().clear();
		Bank.getSingleton().setCardslist(-1, -9, -1 , -1, -1);
		exception.expect(Exception.class);
		Bank.getSingleton().CanBankGiveCard(ResourceType.BRICK);
	}

}
