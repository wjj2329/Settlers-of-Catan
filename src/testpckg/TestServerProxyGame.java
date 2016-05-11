package testpckg;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.junit.Test;

import server.proxies.IServer;
import server.proxies.ServerProxy;

import org.junit.After;
import org.junit.Before;


public class TestServerProxyGame {

	IServer iserver;
	
	@Before
	public void setUp(){
		//These will be needing cookies so it will login user and rejoin a game
		iserver = new ServerProxy();
		iserver.loginUser("Sam", "sam");
		iserver.JoinGame(0, "orange");
	}
	
	@After
	public void tearDown(){
	}

	
	@Test(expected=AssertionError.class)
	public void testGetGameCurrentState(){
		//test to see if it is working!
		assertEquals(iserver.getGameCurrentState(0).getResponseCode(), HttpURLConnection.HTTP_OK);

		
		//this was checking a bad version. 
		assertEquals(iserver.getGameCurrentState(-1).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
	}
	
	
	@Test
	public void testlistAI(){
		//tests to see if it is working, it is just a GET 
		assertEquals(iserver.listAI().getResponseCode(), HttpURLConnection.HTTP_OK);

	}
}
