package server.persistence;

import java.io.*;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
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
	private FileWriter playerFileWriter = new FileWriter(players, true);
	private FileWriter gameFileWriter = new FileWriter(games, true);
	private static int playerNumber = 0;

	public TextDBUserAccountsDAO() throws IOException, JSONException
	{
		System.out.println("I construct the TextDBUserAccountsDAO");
		// file reader, scanner, stringBuilder
		FileReader iReadFiles = new FileReader(players);
		Scanner iScanThings = new Scanner(iReadFiles);
		StringBuilder iBuildStrings = new StringBuilder();
		while (iScanThings.hasNext())
		{
			iBuildStrings.append(iScanThings.next());
		}
		iReadFiles.close();
		iScanThings.close();

		String theString = iBuildStrings.toString();
		System.out.println("What is the string? " + theString);
		if (!theString.contains("{"))
		{
			System.out.println("I am writing the first bracket");
			playerFileWriter.write("{"); // why isn't this writing??
			return;
		}
		System.out.println("I made it here");
		//System.out.println("What is th")
		if (theString.length() > 1 && theString.charAt(theString.length() - 1) != '}')
		{
			System.out.println("We need this");
			playerFileWriter.write("}");
			iBuildStrings.append("}");
		} // it doesn't make it past this
		System.out.println("What does the string for JSON look like? " + iBuildStrings.toString());
		JSONObject jason = new JSONObject(iBuildStrings.toString());
		ArrayList<Integer> numbersForPlayers = new ArrayList<>();

		// this is gonna break something
		for (int i = 0; i < MAX_NUM_PLAYERS; i++)
		{
			String playerObj = "player" + i;
			System.out.println("the number: " + playerObj);
			if (jason.has(playerObj))
			{
				System.out.println("JSON does have " + playerObj);
				JSONObject playerAttributes = jason.getJSONObject(playerObj);
				numbersForPlayers.add(playerAttributes.getInt("numberInFile"));
			}
			else
			{
				System.out.println("JSON does NOT have " + playerObj);
			}
		}
		System.out.println("Here we go, the arrayList of magicalness: " + numbersForPlayers.toString());
		if (numbersForPlayers.size() == 0)
		{
			playerFileWriter.write("{");
		}
		else
		{
			Collections.sort(numbersForPlayers);
			System.out.println("Here we go the sorted arrayList of the players: " + numbersForPlayers);
			int res = numbersForPlayers.get(numbersForPlayers.size() - 1);
			System.out.println("The res is " + res);
			playerNumber = res + 1;
		}

		//playerFileWriter.write("{\"players\": [");
		//playerFileWriter.write("{"); // only do this if it's the first time...
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
		playerFileWriter.write("}");
		playerFileWriter.flush(); // like a toilet
		// TOILET
	}

	/**
	 * Serializing it the other way
	 * @return the list of all players who exist
	 * @throws IOException: if there is an error writing to or reading from the file.
     */
	@Override
	public List<Player> getAllUsers() throws IOException
	{
		//playerFileWriter.write("}");

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
	 * Serialization function used to add the player to the text file DB.
	 *
	 * @param user: the player we are serializing
	 * @return: player's basic info, serialized to JSON.
     */
	private String serializeUserToJson(Player user) throws IOException
	{
		//System.out.println("I am serializing the user to JSON");
		// fixing the last brace:
		FileReader bookworm = new FileReader(players);
		Scanner meScanner = new Scanner(bookworm);
		StringBuilder iBuildStrings = new StringBuilder();
		while (meScanner.hasNextLine())
		{
			iBuildStrings.append(meScanner.nextLine());
		}
		String res = iBuildStrings.toString();
		System.out.println("The result string is: " + res);
		if (res.length() > 1 && res.charAt(res.length() - 1) == '}')
		{
			String cut = res.substring(0, res.length() - 1);
			System.out.println("Cut is " + cut);
			PrintWriter clearMyFile = new PrintWriter(players);
			clearMyFile.close();
			playerFileWriter.write(cut);
			System.out.println("This is the cut JSON: " + cut);
		}
		// it's stopping here for some reason

		String singMeASongOfJson;
		System.out.println("What is the player number? " + playerNumber);
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("\n\t\"player" + playerNumber + "\":\n"); // while the iterator goes to playerNumber - highest poss.
		jsonBuilder.append("\t{\n");
		jsonBuilder.append("\t\t\"username\": \"" + user.getName() + "\",\n");
		jsonBuilder.append("\t\t\"password\": \"" + user.getPassword() + "\",\n");
		jsonBuilder.append("\t\t\"playerID\": \"" + user.getPlayerID().getNumber() + "\",\n");
		jsonBuilder.append("\t\t\"numberInFile\": \"" + playerNumber + "\"\n");
		jsonBuilder.append("\t },\n");
		System.out.println("What does the json builder have in it? " + jsonBuilder.toString());
		singMeASongOfJson = jsonBuilder.toString();
		System.out.println("What is the player's number? " + playerNumber);
		playerNumber++;
		//printResults(singMeASongOfJson);
		return singMeASongOfJson;
	}

	private void printResults(String singMeASongOfJson)
	{
		System.out.println("I finished serializing the user to JSON, and this is it: ");
		System.out.println(singMeASongOfJson);
	}

	public static final int MAX_NUM_PLAYERS = 150;
   
}
