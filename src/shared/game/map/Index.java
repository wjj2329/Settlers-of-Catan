package shared.game.map;

public class Index 
{
	
	/**
	 * A class that contains an Index number that can be accessed to grab people.  Kind of pointless now
	 * but in the future may come in handy for exception handling. 
	 */
	int number;
	public Index(int number)
	{
		this.number=number;
	}
	/**
	 * checks to see if the Index object has a number assigned to it
	 * @return
	 */
	private boolean hasNumber()
	{
		return true;
	}
	public int getNumber()
	{
		return number;
	}
	
}
