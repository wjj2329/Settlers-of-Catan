package server.persistence;

import java.io.*;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import server.database.DatabaseException;
import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.player.Player;


import shared.game.player.Player;

import static shared.definitions.CatanColor.RED;

/**
 * Created by williamjones on 6/7/16.
 * @author Alex
 */
public class TextDBUserAccountsDAO implements IUserAccount
{
	private File players = new File("allPlayers.txt");
	private File games = new File("allGames.txt");
	private FileWriter playerFileWriter = new FileWriter(players, true);
	private FileWriter gameFileWriter = new FileWriter(games, true);
	private static int playerNumber = 0;
	private static int gameNumber = 0;

	public TextDBUserAccountsDAO() throws IOException, JSONException
	{
		// file reader, scanner, stringBuilder
		fixGameNumber();
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
		if (!theString.contains("{"))
		{
			playerFileWriter.write("{");
			return;
		}
		if (theString.length() > 1 && theString.charAt(theString.length() - 1) != '}')
		{
			playerFileWriter.write("}");
			iBuildStrings.append("}");
		}
		JSONObject jason = new JSONObject(iBuildStrings.toString());
		ArrayList<Integer> numbersForPlayers = new ArrayList<>();

		for (int i = 0; i < MAX_NUM_PLAYERS; i++)
		{
			String playerObj = "player" + i;
			if (jason.has(playerObj))
			{
				JSONObject playerAttributes = jason.getJSONObject(playerObj);
				numbersForPlayers.add(playerAttributes.getInt("numberInFile"));
			}
		}
		if (numbersForPlayers.size() == 0)
		{
			playerFileWriter.write("{");
		}
		else
		{
			Collections.sort(numbersForPlayers);
			int res = numbersForPlayers.get(numbersForPlayers.size() - 1);
			playerNumber = res + 1;
		}
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

	@Override
	public void addGameToGameList(CatanGame game) throws IOException, JSONException
	{
		FileReader gameFileReader = new FileReader(games);
		Scanner iLikeScanning = new Scanner(gameFileReader);
		StringBuilder braceChecker = new StringBuilder();
		while (iLikeScanning.hasNext())
		{
			braceChecker.append(iLikeScanning.next());
		}
		String doYouHaveAnyBraces = braceChecker.toString();
		if (doYouHaveAnyBraces.length() < 2)
		{
			gameFileWriter.write("{");
		}
		gameFileWriter.write(serializeGameToJson(game));
		gameFileWriter.flush();
	}

	private String playersToJson(CatanGame game)
	{
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("\t\t\"playersInGame\":\n\t\t[\n");
		Map<Index, Player> players = game.getMyplayers();
		for (Player p : players.values())
		{
			String name = p.getName();
			int id = p.getPlayerID().getNumber();
			toReturn.append("{\n\t\t\t\"name\": " + name + "\",\n");
			toReturn.append("\t\t\t\"id\": " + id + "\"\n"); // this format won't be completely correct
			toReturn.append("\t},");
		}
		toReturn.append("\n\t\t]\n\t");
		toReturn.append("},");
		return toReturn.toString();
	}

	private String serializeGameToJson(CatanGame game) throws IOException, JSONException
	{
		deleteTheStupidExtraBrace(games, gameFileWriter);
		StringBuilder jsonBuilder = new StringBuilder();
		//fixGameNumber();
		jsonBuilder.append("\n\t\"game" + gameNumber + "\":\n");
		jsonBuilder.append("\t{\n");
		jsonBuilder.append("\t\t\"id\": \"" + game.getGameId() + "\",\n");
		jsonBuilder.append("\t\t\"uniqueNumber\": \"" + gameNumber + "\",\n");
		jsonBuilder.append("\t\t\"title\": \"" + game.getTitle() + "\",\n");
		gameNumber++;
		String players = playersToJson(game);
		jsonBuilder.append(players);
		jsonBuilder.append("\n}");
		//jsonBuilder.append(",\n}");
		return jsonBuilder.toString();
	}

	private void fixGameNumber() throws IOException, JSONException
	{
		FileReader iReadFiles = new FileReader(games);
		Scanner iScanThings = new Scanner(iReadFiles);
		StringBuilder iBuildStrings = new StringBuilder();
		while (iScanThings.hasNext())
		{
			iBuildStrings.append(iScanThings.next());
		}
		iReadFiles.close();
		iScanThings.close();
		String testing = iBuildStrings.toString();
		if (testing.length() < 1)
		{
			return;
		}
		if (testing.length() > 1 && testing.charAt(testing.length() - 1) != '}')
		{
			iBuildStrings.append("}");
		}
		JSONObject jason = new JSONObject(iBuildStrings.toString());
		ArrayList<Integer> allGamesInFile = new ArrayList<>();
		for (int i = 0; i < MAX_NUM_GAMES; i++)
		{
			String gameObj = "game" + i;
			if (jason.has(gameObj))
			{
				JSONObject gameAttribute = jason.getJSONObject(gameObj);
				allGamesInFile.add(gameAttribute.getInt("uniqueNumber"));
			}
		}
		Collections.sort(allGamesInFile);
		int res = allGamesInFile.get(allGamesInFile.size() - 1);
		gameNumber = res + 1;
	}

	/**
	 * Serialization function used to add the player to the text file DB.
	 *
	 * @param user: the player we are serializing
	 * @return: player's basic info, serialized to JSON.
     */
	private String serializeUserToJson(Player user) throws IOException
	{
		deleteTheStupidExtraBrace(players, playerFileWriter);
		String singMeASongOfJson;
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("\n\t\"player" + playerNumber + "\":\n"); // while the iterator goes to playerNumber - highest poss.
		jsonBuilder.append("\t{\n");
		jsonBuilder.append("\t\t\"username\": \"" + user.getName() + "\",\n");
		jsonBuilder.append("\t\t\"password\": \"" + user.getPassword() + "\",\n");
		jsonBuilder.append("\t\t\"playerID\": \"" + user.getPlayerID().getNumber() + "\",\n");
		jsonBuilder.append("\t\t\"numberInFile\": \"" + playerNumber + "\"\n");
		jsonBuilder.append("\t },\n");
		singMeASongOfJson = jsonBuilder.toString();
		playerNumber++;
		//printResults(singMeASongOfJson);
		return singMeASongOfJson;
	}

	private void deleteTheStupidExtraBrace(File playersOrGames, FileWriter chosenFileWriter) throws IOException
	{
		FileReader bookworm = new FileReader(playersOrGames);
		Scanner meScanner = new Scanner(bookworm);
		meScanner.useDelimiter(System.getProperty("line.separator"));
		// Delimiter is ESSENTIAL for preserving formatting!
		meScanner.useDelimiter("\r\n");
		StringBuilder iBuildStrings = new StringBuilder();
		while(meScanner.hasNext())
		{
			iBuildStrings.append(meScanner.next());
		}

		/*while (meScanner.hasNextLine())
		{
			iBuildStrings.append(meScanner.nextLine());
		}*/  
		String res = iBuildStrings.toString();
		//System.out.println("resulting string is: " + res);
		if (res.length() > 1 && res.charAt(res.length() - 1) == '}')
		{
			String cut = res.substring(0, res.length() - 1);
			PrintWriter clearMyFile = new PrintWriter(playersOrGames);
			clearMyFile.close();
			chosenFileWriter.write(cut);
		}
	}

	private void printResults(String singMeASongOfJson)
	{
		System.out.println("I finished serializing the user to JSON, and this is it: ");
		System.out.println(singMeASongOfJson);
	}

	public File getPlayers()
	{
		return players;
	}

	public File getGames()
	{
		return games;
	}

	/**
	 * These variables can be adjusted as necessary.
	 * However, the programmer should adjust them manually.
	 * They are critical to the functioning of this class, so I
	 * 	don't want anything else messing with them.
	 */
	private static final int MAX_NUM_PLAYERS = 150;
	private static final int MAX_NUM_GAMES = 150;
}
