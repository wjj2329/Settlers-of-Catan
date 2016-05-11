package testpckg;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import server.proxies.*;

public class TestServerProxyUser {
	
	IServer iserver;
	
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
		assertEquals(iserver.loginUser("Mark","mark").getResponseCode(), HttpURLConnection.HTTP_OK);
		assertEquals(iserver.loginUser("Brooke", "brooke").getResponseCode(), HttpURLConnection.HTTP_OK);
		assertEquals(iserver.loginUser("Pete", "pete").getResponseCode(), HttpURLConnection.HTTP_OK);
	}
	
	@Test(expected = AssertionError.class)
	public void testBadLogin() throws JSONException{
		//tests for inputs as null and empty strings and when username and password don't match
		
		assertEquals(iserver.loginUser("Sam", "sa").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser(null, "asdf").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser("Sam", "").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser("Sam", null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser("", null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.loginUser(null, null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);	
	}
	
	@Test
	public void testBadRegisters() throws JSONException{
		//tests for inputs as null and empty strings and repeated username 
		
		assertEquals(iserver.registerUser("Mark", "marco").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);		
		assertEquals(iserver.registerUser(null, "asdf").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.registerUser("Sam", "").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.registerUser("Sam", null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.registerUser("", "").getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
		assertEquals(iserver.registerUser(null, null).getResponseCode(), HttpURLConnection.HTTP_BAD_REQUEST);
	}
}
