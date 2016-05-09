package testpckg;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ExpectedException;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.DevCardList;
import shared.game.ResourceList;

public class CanBankGiveCard {
    /*
    This tests if the bank can give resource objects and devleopment cards and throws exceptions if the bank contains negative
    amounts.
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

    @Test
    public void testBank4()throws Exception
    {
        Bank.getSingleton().clear();
        Bank.getSingleton().setDevCardList(-10, -10, -3, -4, -5);
        exception.expect(Exception.class);
        Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.MONOPOLY);

    }

    @Test
    public void testBank5() throws Exception
    {
        Bank.getSingleton().clear();
        Bank.getSingleton().setDevCardList(10, 15, 0, 0, 2);
        assertTrue(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.MONOPOLY));
        assertTrue(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.MONUMENT));
        assertFalse(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.ROAD_BUILD));
        assertFalse(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.SOLDIER));
        assertTrue(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.YEAR_OF_PLENTY));
    }

    @Test
    public void testBank6() throws Exception
    {
        Bank.getSingleton().clear();
        DevCardList mylist=new DevCardList();
        mylist.setYearOfPlenty(1);
        mylist.setMonopoly(0);
        mylist.setSoldier(1);
        mylist.setMonument(5);
        mylist.setRoadBuilding(0);
        Bank.getSingleton().setDevCardList(mylist);
        assertTrue( Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.YEAR_OF_PLENTY));
        assertFalse(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.MONOPOLY));
        assertTrue(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.SOLDIER));
        assertTrue(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.MONUMENT));
        assertFalse(Bank.getSingleton().CanBankGiveDevelopmentCard(DevCardType.ROAD_BUILD));




    }

}
