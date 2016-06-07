package testpckg;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import server.proxies.*;

public class TestServerProxyUser {
	
	private IServer iserver;
	
	@Before
	public void setUp(){
		iserver = new ServerProxy();
	}
	
	@After
	public void tearDown(){
	}
	
	@Test
	public void testValidLogin() throws JSONException {
		//loginUser returns true when the login is successful using default users
		
		assertEquals(iserver.loginUser("Sam", "sam").getResponseCode(), HttpURLConnection.HTTP_OK);
	}
	
	@Test
	public void testBadLogin() throws JSONException{
		//tests for inputs as null and empty strings and when username and password don't match
		
		assertEquals(iserver.loginUser("Sam", "sa").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser(null, "asdf").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser("Sam", "").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser("Sam", null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser("", null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser(null, null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);	
	}
	
	@Test(expected = AssertionError.class)
	public void testBadRegisters() throws JSONException{
		//tests for inputs  empty strings and repeated username
		assertEquals(iserver.registerUser("Mark", "marco").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);		
		assertEquals(iserver.registerUser("Sam", "jj").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.registerUser("Sam", "Gangster").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertNotEquals(iserver.registerUser("jj", "jj").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertNotEquals(iserver.registerUser("Ryan", "Brittany").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
	}
}
