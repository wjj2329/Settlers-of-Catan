package shared.chat;

/**
 * @author Alex
 * Class to represent lines of game history
 * Might not need this
 */
public class GameHistoryLine 
{
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	/**
	 * The line of text that will be output
	 */
	private String source="";

	private String line = "";
	
	/**
	 * Constructor
	 */
	public GameHistoryLine()
	{
		
	}
	
	/**
	 * Display function
	 */
	public void display()
	{
		
	}
}
