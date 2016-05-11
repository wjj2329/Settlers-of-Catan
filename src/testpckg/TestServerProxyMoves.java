package testpckg;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;

import org.json.JSONException;
import org.junit.Test;

import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.game.ResourceList;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import org.junit.After;
import org.junit.Before;

public class TestServerProxyMoves {
	
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

	
	@Test
	public void TestSendChat(){
		assertEquals(iserver.sendChat("sendChat", 2, "YO").getResponseCode(), HttpURLConnection.HTTP_OK);
	
	}
	
	@Test
	public void TestRollNumber(){
		assertEquals(iserver.rollNumber("rollNumber", 2, 4).getResponseCode(), HttpURLConnection.HTTP_OK);		
	
	}
	
	@Test
	public void TestValidRobPlayer(){
		HexLocation location = new HexLocation(0,2);
		assertEquals(iserver.robPlayer("robPlayer", 0, location, 2).getResponseCode(), HttpURLConnection.HTTP_OK);
	}
	
	@Test 
	public void TestRobPlayer(){
		HexLocation hexLocation = new HexLocation(2,2);
		assertEquals(iserver.robPlayer("robPlayer", 0, hexLocation, 2).getResponseCode(), HttpURLConnection.HTTP_OK);		
		
	}
	
	@Test
	public void TestFinishTurn(){
		assertEquals(iserver.finishTurn("finishTurn", 0).getResponseCode(), HttpURLConnection.HTTP_OK);
	}
	
	@Test
	public void TestBuyDevCard(){
		assertEquals(iserver.buyDevCard("buyDevCard", 0).getResponseCode(), HttpURLConnection.HTTP_OK);
	}
	
	@Test
	public void TestPlayYearofPlenty(){
		assertEquals(iserver.playYearofPlenty("Year_of_Plenty", 0, "brick", "sheep").getResponseCode(), HttpURLConnection.HTTP_OK);
	}
	
	@Test
	public void TestPlayRoadBuilding(){
		EdgeLocation spot1 = new EdgeLocation(new HexLocation(0,1), EdgeDirection.North);
		EdgeLocation spot2 = new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthEast);
		
		assertEquals(iserver.playRoadBuilding("Road_Building", 0, spot1, spot2).getResponseCode(), HttpURLConnection.HTTP_OK);
	}
	
	@Test
	public void TestPlaySoldier(){
		HexLocation hexLocation = new HexLocation(2,2);
		assertEquals(iserver.playSoldier("Soldier", 0, hexLocation, 2).getResponseCode(), HttpURLConnection.HTTP_OK);				
	}
	
	@Test
	public void TestPlayMonopoly(){
		assertEquals(iserver.playMonopoly("Monopoly", 0, "brick").getResponseCode(), HttpURLConnection.HTTP_OK);		
		
	}
	
	@Test
	public void TestPlayMonument(){
		assertEquals(iserver.playMonument("Monument", 0).getResponseCode(), HttpURLConnection.HTTP_OK);		
	}
	
	@Test
	public void TestBuildRoad(){
		EdgeLocation roadLocation = new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthWest);
		
		assertEquals(iserver.buildRoad("buildRoad", 0, true, roadLocation).getResponseCode(), HttpURLConnection.HTTP_OK);		
	}
	
	@Test
	public void TestBuildSettlement(){
		VertexLocation vertexLocation = new VertexLocation(new HexLocation(2,0), VertexDirection.East);
		
		assertEquals(iserver.buildSettlement("buildSettlement", 0, true, vertexLocation).getResponseCode(), HttpURLConnection.HTTP_OK);		
	}
	
	@Test
	public void TestBuildCity(){
		VertexLocation vertexLocation = new VertexLocation(new HexLocation(2,0), VertexDirection.East);
		
		assertEquals(iserver.buildCity("buildCity", 0, vertexLocation).getResponseCode(), HttpURLConnection.HTTP_OK);		
	}
	
	@Test
	public void TestOfferTrade(){
		ResourceList offer = new ResourceList(1, 0, 0, 1, 0);
		assertEquals(iserver.offerTrade("offerTrade", 0, offer, 2).getResponseCode(), HttpURLConnection.HTTP_OK);		
	}
	
	@Test
	public void TestAcceptTrade(){
		assertEquals(iserver.acceptTrade("acceptTrade", 0, true).getResponseCode(), HttpURLConnection.HTTP_OK);		
		
	}
	
	@Test
	public void TestMaritimeTrade(){
		assertEquals(iserver.maritimeTrade("maritimeTrade", 0, 3, "brick", "sheep").getResponseCode(), HttpURLConnection.HTTP_OK);		
		
	}
	
	@Test
	public void TestDiscardCards(){
		ResourceList discardedCards = new ResourceList(1, 1, 0, 1, 0);
		assertEquals(iserver.discardCards("discardCards", 0, discardedCards).getResponseCode(), HttpURLConnection.HTTP_OK);
	
	}
	
}
