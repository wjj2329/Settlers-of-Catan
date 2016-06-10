package server.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import server.database.DatabaseException;
import shared.definitions.CatanColor;
import shared.game.player.Player;


import shared.game.player.Player;

import static shared.definitions.CatanColor.RED;

/**
 * Created by williamjones on 6/7/16.
 * @author Alex
 */
public class TextDBUserAccountsDAO implements IUserAccount
{
	private int gameID = -1;
	private File players = new File("allPlayers.txt");
	private File games = new File("allGames.txt");
	private FileWriter playerFileWriter = new FileWriter(players);
	private FileWriter gameFileWriter = new FileWriter(games);
	private static int playerNumber = 0;

	public TextDBUserAccountsDAO() throws IOException
	{
		//playerFileWriter.write("{\"players\": [");
		playerFileWriter.write("{");
		gameFileWriter.write("{");
	}

	@Override
	public Player validateUser(Player player)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player is getting added to large list of all users. This is independent of whatever games
	 * 	are in the server. The players can log in and play new games even when the server is shut
	 * 	down and restarted.
	 *
	 * @param user: the user we are adding to the list of all users
	 * @throws DatabaseException if the database is crap
	 * @throws IOException if the IO is crap
     */
	@Override
	public void addUser(Player user) throws DatabaseException, IOException
	{
		System.out.println("I'm adding the user " + user.getName() + " into the tawlet");
		System.out.println("THE JSON: " + serializeUserToJson(user));
		playerFileWriter.write(serializeUserToJson(user));
		playerFileWriter.flush(); // like a toilet
		// TOILET
	}

	@Override
	public List<Player> getAllUsers() throws IOException
	{
		playerFileWriter.write("}");

		return null;
	}

	@Override
	public void setColor(Player user) throws DatabaseException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUserInGame(Player user)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Will need to be programmed as though every command is the last one.
	 * Problem: this is happening twice. -_-
	 * problem 2: the file gets overwritten every time. -_-
	 *
	 * @param user: the player we are serializing
	 * @return: player's basic info, serialized to JSON.
     */
	private String serializeUserToJson(Player user)
	{
		System.out.println("I am serializing the user to JSON");
		String singMeASongOfJson;
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("\n\t\"player" + playerNumber + "\":\n");
		jsonBuilder.append("\t{\n");
		jsonBuilder.append("\t\t\"username\": \"" + user.getName() + "\",\n");
		jsonBuilder.append("\t\t\"password\": \"" + user.getPassword() + "\",\n");
		// jsonBuilder.append("\t\t\"color\": \"" + user.getColor().name().toLowerCase() + "\",\n");
		jsonBuilder.append("\t\t\"playerID\": \"" + user.getPlayerID().getNumber() + "\",\n");
		jsonBuilder.append("\t },\n");
		//jsonBuilder.append("\t\t\"playerIndex\": \"" + user.getPlayerIndex().getNumber() + "\"\n\t },\n");
		//jsonBuilder.append("}");
		singMeASongOfJson = jsonBuilder.toString();
		playerNumber++;
		//printResults(singMeASongOfJson);
		return singMeASongOfJson;
	}

	private void printResults(String singMeASongOfJson)
	{
		System.out.println("I finished serializing the user to JSON, and this is it: ");
		System.out.println(singMeASongOfJson);
	}
   
}
