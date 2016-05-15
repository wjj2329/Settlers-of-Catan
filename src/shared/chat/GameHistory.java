package shared.chat;

import java.util.ArrayList;

/**
 * @author Alex
 * GameHistory: class to encapsulate history of 
 * what has happened in the game
 */
public class GameHistory 
{
	public ArrayList<GameHistoryLine>getLines()
	{
	return lines;
	}
	/**
	 * List of all GameHistory lines, recording each action taken each turn
	 */
	private ArrayList<GameHistoryLine> lines = new ArrayList<>();
	/**
	 * GameHistory constructor
	 */
	public GameHistory()
	{
		
	}
	
	/**
	 * Display function
	 */
	public void viewGameHistory()
	{
		for (int i = 0; i < lines.size(); i++)
		{
			lines.get(i).display();
		}
	}

	public void setLines(ArrayList<GameHistoryLine> lines)
	{
		this.lines = lines;
	}
}
