package shared.game;

/**
 * @author William
 * Class to encapsulate the different cards that the players use.
 */
public class Card 
{
	String type = "";
	/**
	 * Constructor for Card.
	 * @param type: the type of the card. Will probably change to an enum
	 * from the premade classes.
	 */
	public Card(String type)
	{
		this.type=type;
	}
	public void setPlayerIndex(int playerIndex) {
		// TODO Auto-generated method stub
		
	}
	public int getPlayerIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
}
