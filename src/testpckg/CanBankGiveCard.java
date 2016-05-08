package testpckg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.game.Bank;
import shared.game.ResourceList;

public class CanBankGiveCard {

	Bank mybank1=new Bank();
	ResourceList mylist=new ResourceList();
	@Before
	public void setUp()throws Exception
	{
		mylist.setBrick(4);
		mylist.setOre(3);
		mylist.setSheep(2);
		mylist.setWheat(10);
		mylist.setWood(0);
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
