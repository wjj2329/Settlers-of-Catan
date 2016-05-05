package server.proxies;

import client.data.*;
import shared.definitions.*;
import shared.locations.*;

import org.json.JSONObject;

/**
 * Implementation for the MockServerProxy
 */
public class MockServerProxy implements IServerProxy {

	private RealServerProxy realServerProxy;

	@Override
	public String loginUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllCurrentGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createAGame(String gameName, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addPlayertoGame(int gameID, CatanColor color, PlayerInfo player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveGame(int gameID, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadGame(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGameCurrentState(int version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String resetCurrentGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GET() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String POST() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAIList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addAIPlayer(String logLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeLogLevel(String logLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendChat(String type, int playerIndex, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptTrade(String type, int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub

	}

	@Override
	public void discardCards(String type, int playerIndex, JSONObject discardedCards) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollNumber(String type, int playerIndex, int number) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildRoad(String type, int playerIndex, boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildSettlement(String type, int playerIndex, boolean free, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildCity(String type, int playerIndex, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void offerTrade(String type, int playerIndex, server.proxies.JSONObject offer, int playerIndex2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void maritimeTrade(String type, int playerIndex, int ratio, String inputResource, String outputResource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void robPlayer(String type, HexLocation location, int playerIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishTurn(String type, int playerIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buyDevCard(String type, int playerIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSoldier(String type, int playerIndex, HexLocation location, int victimIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playYearofPlenty(String type, int playerIndex, String resource1, String resource2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playRoadBuilding(String type, int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playMonopoly(String type, int playerIndex, String resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playMonument(String type, int playerIndex) {
		// TODO Auto-generated method stub

	}

}
