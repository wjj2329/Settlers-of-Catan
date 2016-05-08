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


	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp()throws Exception
	{
		Bank mybank1=new Bank();
		Bank mybank2=new Bank();
		Bank mybank3=new Bank();
		ResourceList mylist=new ResourceList();
		mylist.setBrick(4);
		mylist.setOre(3);
		mylist.setSheep(2);
		mylist.setWheat(10);
		mylist.setWood(0);
		mybank1.setCardslist(mylist);
		assertTrue(mybank1.CanBankGiveCard(ResourceType.BRICK));
		assertFalse(mybank1.CanBankGiveCard(ResourceType.WOOD));
		assertTrue(mybank1.CanBankGiveCard(ResourceType.WHEAT));
		assertTrue(mybank1.CanBankGiveCard(ResourceType.SHEEP));
		assertTrue(mybank1.CanBankGiveCard(ResourceType.ORE));
		mybank2.setCardslist(0, 0 , 0 , 50, 25);
		assertFalse(mybank2.CanBankGiveCard(ResourceType.BRICK));
		assertTrue(mybank2.CanBankGiveCard(ResourceType.WOOD));
		assertTrue(mybank2.CanBankGiveCard(ResourceType.ORE));
		assertFalse(mybank2.CanBankGiveCard(ResourceType.WHEAT));
		assertFalse(mybank2.CanBankGiveCard(ResourceType.SHEEP));
		exception.expect(IndexOutOfBoundsException.class);
	}

	@After
	public void tearDown() throws Exception
	{

	}
	@Test
	public void test()  throws Exception
	{
		assertTrue(true);
	}

}
