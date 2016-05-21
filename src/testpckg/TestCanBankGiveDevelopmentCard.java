package testpckg;

import static org.junit.Assert.*;

import client.model.ModelFacade;
import org.junit.*;
import org.junit.rules.ExpectedException;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.game.Bank;
import shared.game.DevCardList;
import shared.game.ResourceList;

public class TestCanBankGiveDevelopmentCard {
    /**
    This tests if the bank can give and devleopment cards and throws exceptions if the bank contains negative
    amounts.  Tests a variety of situations.
     */

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp()throws Exception
    {
        ModelFacade.facadeCurrentGame.currentgame.mybank.clear();
        ResourceList mylist=new ResourceList();
        mylist.setBrick(4);
        mylist.setOre(3);
        mylist.setSheep(2);
        mylist.setWheat(10);
        mylist.setWood(0);
        ModelFacade.facadeCurrentGame.currentgame.mybank.setResourceCardslist(mylist);
    }

    @After
    public void tearDown() throws Exception
    {
        ModelFacade.facadeCurrentGame.currentgame.mybank.clear();
    }

    @Test
    public void testBank4()throws Exception
    {
        ModelFacade.facadeCurrentGame.currentgame.mybank.clear();
        ModelFacade.facadeCurrentGame.currentgame.mybank.setDevCardList(-10, -10, -3, -4, -5);
        exception.expect(Exception.class);
        ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.MONOPOLY);

    }

    @Test
    public void testBank5() throws Exception
    {
        ModelFacade.facadeCurrentGame.currentgame.mybank.clear();
        ModelFacade.facadeCurrentGame.currentgame.mybank.setDevCardList(10, 15, 0, 0, 2);
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.MONOPOLY));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.MONUMENT));
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.ROAD_BUILD));
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.SOLDIER));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.YEAR_OF_PLENTY));
    }

    @Test
    public void testBank6() throws Exception
    {
        ModelFacade.facadeCurrentGame.currentgame.mybank.clear();
        DevCardList mylist=new DevCardList();
        mylist.setYearOfPlenty(1);
        mylist.setMonopoly(0);
        mylist.setSoldier(1);
        mylist.setMonument(5);
        mylist.setRoadBuilding(0);
        ModelFacade.facadeCurrentGame.currentgame.mybank.setDevCardList(mylist);
        assertTrue( ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.YEAR_OF_PLENTY));
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.MONOPOLY));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.SOLDIER));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.MONUMENT));
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveDevelopmentCard(DevCardType.ROAD_BUILD));
    }

}
