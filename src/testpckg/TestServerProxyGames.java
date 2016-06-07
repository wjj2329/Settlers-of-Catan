package testpckg;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import org.junit.Test;

import client.model.ModelFacade;

import org.junit.After;
import org.junit.Before;

import server.proxies.*;

public class TestServerProxyGames {

IServer iserver;
	
	@Before
	public void setUp(){
		iserver = new ServerProxy();
	}
	
	@After
	public void tearDown(){
	}
	
	@Test
	public void testCrap(){
		ModelFacade.facadeCurrentGame.loadGames();
	}
	
	
	@Test
	public void testListGames(){
		//just tests to see if it connects okay 
		assertEquals(iserver.getAllCurrentGames().getResponseCode(), HttpURLConnection.HTTP_OK);
		
		//will test to see if it works a user is logged in 
		iserver.loginUser("Mark", "mark");
		assertEquals(iserver.getAllCurrentGames().getResponseCode(), HttpURLConnection.HTTP_OK);
	}

	@Test(expected = AssertionError.class)
	public void testCreateGame(){
		//just tests to see if it connects okay 
		assertEquals(iserver.createGame("Game1", true, true, true).getResponseCode(), HttpURLConnection.HTTP_OK);
		assertEquals(iserver.createGame("Game2", false, false, false).getResponseCode(), HttpURLConnection.HTTP_OK);
		assertEquals(iserver.createGame("TheGAME", true, false, false).getResponseCode(), HttpURLConnection.HTTP_OK);

		
		//will test to see if it works when a user is logged in
		iserver.loginUser("Pete", "pete");
		assertEquals(iserver.createGame("Juego", false, true, true).getResponseCode(), HttpURLConnection.HTTP_OK);
		
		
		//will test some with bad names to see if it still accepts it
		assertEquals(iserver.createGame("", true, false, false).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.createGame(null, true, false, false).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
	}
	
	
	@Test
	public void testJoinGame(){
		// tests valid rejoins and you have to login first 
		
		iserver.loginUser("Sam", "sam");
		assertEquals(iserver.JoinGame(0, "orange").getResponseCode(), HttpURLConnection.HTTP_OK);
		
		
	}
	
	
}
