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
	Bank mybank1=new Bank();
	Bank mybank2=new Bank();
	Bank mybank3=new Bank();
	@Before
	public void setUp()throws Exception
	{
		ResourceList mylist=new ResourceList();
		mylist.setBrick(4);
		mylist.setOre(3);
		mylist.setSheep(2);
		mylist.setWheat(10);
		mylist.setWood(0);
		mybank1.setCardslist(mylist);
		mybank2.setCardslist(0, 0 , 0 , 50, 25);
		mybank3.setCardslist(-1, -9, -1 , -1, -1);
	}

	@After
	public void tearDown() throws Exception
	{

	}
	@Test
	public void test()  throws Exception
	{
		assertTrue(true);
		assertTrue(mybank1.CanBankGiveCard(ResourceType.BRICK));
		assertFalse(mybank1.CanBankGiveCard(ResourceType.WOOD));
		assertTrue(mybank1.CanBankGiveCard(ResourceType.WHEAT));
		assertTrue(mybank1.CanBankGiveCard(ResourceType.SHEEP));
		assertTrue(mybank1.CanBankGiveCard(ResourceType.ORE));
		assertFalse(mybank2.CanBankGiveCard(ResourceType.BRICK));
		assertTrue(mybank2.CanBankGiveCard(ResourceType.WOOD));
		assertTrue(mybank2.CanBankGiveCard(ResourceType.ORE));
		assertFalse(mybank2.CanBankGiveCard(ResourceType.WHEAT));
		assertFalse(mybank2.CanBankGiveCard(ResourceType.SHEEP));
		exception.expect(Exception.class);
		mybank3.CanBankGiveCard(ResourceType.BRICK);
	}

}
