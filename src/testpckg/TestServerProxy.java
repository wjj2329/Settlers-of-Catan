package testpckg;


import static org.junit.Assert.*;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.proxies.*;

public class TestServerProxy {
	
	
	IServer iserver;
	
	@Before
	public void setUp(){
		iserver = new ServerProxy();
	}

	
	@Test
	public void testLogin() throws JSONException {
		assertTrue(iserver.loginUser("Sam", "sam"));
	}
	
	@Test
	public void testGetAllGames(){
		
	}
	
}
