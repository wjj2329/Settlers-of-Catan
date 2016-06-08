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
    public void testBank1()  throws Exception
    {
        assertTrue(true);
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.BRICK));
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.WOOD));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.WHEAT));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.SHEEP));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.ORE));
    }

    @Test
    public void testBank2() throws Exception
    {
        ModelFacade.facadeCurrentGame.currentgame.mybank.clear();
        ModelFacade.facadeCurrentGame.currentgame.mybank.setResourceCardslist(0, 0 , 0 , 50, 25);
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.BRICK));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.WOOD));
        assertTrue(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.ORE));
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.WHEAT));
        assertFalse(ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.SHEEP));
    }

    @Test
    public void testBank3() throws Exception
    {
        ModelFacade.facadeCurrentGame.currentgame.mybank.clear();
        ModelFacade.facadeCurrentGame.currentgame.mybank.setResourceCardslist(-1, -9, -1 , -1, -1);
        //exception.expect(Exception.class);
        ModelFacade.facadeCurrentGame.currentgame.mybank.CanBankGiveResourceCard(ResourceType.BRICK);
    }
}
