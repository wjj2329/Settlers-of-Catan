package testpckg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Phase1Tests
{
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}
	
	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"testpckg.TestCanBankGiveDevelopmentCard",
				"testpckg.TestCanBankGiveResourceCard",
				"testpckg.TestCanBeRobbed",
				"testpckg.TestCanBuildRoadPiece",
				"testpckg.TestCanBuyDevCard",
				"testpckg.TestCanCreatePlayer",
				"testpckg.TestCanPerformBankTrade",
				"testpckg.TestCanPerformPlayerTrade",
				"testpckg.TestCanPlaceCity",
				"testpckg.TestCanPlaceHex",
				"testpckg.TestCanPlaceRobber",
				"testpckg.TestCanPlaceSettlement",
				"testpckg.TestCanStartGame",
				"testpckg.TestDoBuildRoadPiece",
				"testpckg.TestServerPoller",
				"testpckg.TestServerProxyGame",
				"testpckg.TestServerProxyGames",
				"testpckg.TestServerProxyMoves",
				"testpckg.TestServerProxyUser"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}
