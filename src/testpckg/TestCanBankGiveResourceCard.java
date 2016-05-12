package testpckg;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ExpectedException;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.DevCardList;
import shared.game.ResourceList;

public class TestCanBankGiveResourceCard {
    /**
    This tests if the bank can give resource cards and throws exceptions if the bank contains negative
    amounts. Tests a variety of situations
     */

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
        Bank.getSingleton().setResourceCardslist(mylist);
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
        assertTrue(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.BRICK));
        assertFalse(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.WOOD));
        assertTrue(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.WHEAT));
        assertTrue(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.SHEEP));
        assertTrue(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.ORE));
    }

    @Test
    public void testBank2() throws Exception
    {
        Bank.getSingleton().clear();
        Bank.getSingleton().setResourceCardslist(0, 0 , 0 , 50, 25);
        assertFalse(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.BRICK));
        assertTrue(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.WOOD));
        assertTrue(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.ORE));
        assertFalse(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.WHEAT));
        assertFalse(Bank.getSingleton().CanBankGiveResourceCard(ResourceType.SHEEP));
    }

    @Test
    public void testBank3() throws Exception
    {
        Bank.getSingleton().clear();
        Bank.getSingleton().setResourceCardslist(-1, -9, -1 , -1, -1);
        exception.expect(Exception.class);
        Bank.getSingleton().CanBankGiveResourceCard(ResourceType.BRICK);
    }
}
