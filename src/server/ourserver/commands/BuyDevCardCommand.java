package server.ourserver.commands;

import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.game.CatanGame;

import java.io.FileNotFoundException;

/**
 * The BuyDevCardCommand class
 */
public class BuyDevCardCommand implements ICommand 
{
	private int playerIndex;
	private int gameid;
	/**
	 * Executes the task 
	 * player buys a devcard if they have required resources
	 */
	@Override
	public Object execute() throws FileNotFoundException, JSONException {
		// TODO Auto-generated method stub
		System.out.println("NOW BUYING DEV CARD IN COMMAND");
		//try {
			ServerFacade.getInstance().buyDevCard(playerIndex, gameid);
		/*} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		System.out.println("Card was bought");

		return null;
	}
	
	public BuyDevCardCommand(int playerIndex, int gameid)
	{
		this.playerIndex = playerIndex;
		this.gameid = gameid;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," + TextDBGameManagerDAO.commandNumber+":"+"{" +
				"\"type\":\"BuyDevCardCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"gameid\":" + gameid +
				"}";
	}

	@Override
	public String toJSON() {
		return "{" +
				"\"type\":\"BuyDevCardCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"gameid\":" + gameid +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;
	}

	@Override
	public Object executeversion2(CatanGame game) {
		/*
		// TODO Auto-generated method stub
		System.out.println("NOW BUYING DEV CARD IN COMMAND");
		//try {
		game.buyDevCard(playerIndex, gameid);
		/*} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		System.out.println("Card was bought");

		return null;
	}
}
