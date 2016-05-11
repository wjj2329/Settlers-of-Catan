package testpckg;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import server.proxies.MockServer;
import server.proxies.ServerProxy;
import shared.game.CatanGame;
import client.model.Model;
import client.model.ServerPoller;

public class TestServerPoller 
{
	ServerPoller poller;
	CatanGame testGame=new CatanGame();
	ServerProxy server;
	// temporarily putting in new mock server. so it compiles.
	@Before
	public void setUp() throws Exception
	{
		CatanGame testGame = new CatanGame();
		ServerProxy server = new ServerProxy();
		poller = new ServerPoller(testGame, server);
	}

	@After
	public void tearDown()
	{

	}

	
	@Test
	public void test() throws InterruptedException 
	{
		poller.startPoller();
		//Not sure how to check if poller began polling
		
		Model originalModel = testGame.getModel();
		
		try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(TestServerPoller.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		Model newModel = testGame.getModel();
		assertFalse(originalModel.getVersion() != newModel.getVersion());
	}
}
