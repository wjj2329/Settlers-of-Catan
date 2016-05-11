package shared.game.map;

/**
 * Index: A class that contains an Index number that can be accessed to grab people.  
 * Kind of pointless now but in the future may come in handy for exception handling. 
 */
public class Index 
{
	int number = 0;
	
	/**
	 * The constructor!
	 * @param number: the number itself    
	 * @current.mytag1 pre: number is greater than 0
	 * @exception: if the index number is not found. 
	 */
	public Index(int number)
	{
		this.number=number;
	}
	/**
	 * checks to see if the Index object has a number assigned to it~!
	 */
	private boolean hasNumber()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Index index = (Index) o;

		return number == index.number;

	}

	@Override
	public int hashCode()
	{
		return number;
	}

	/**
	 * Getters and setters follow:
	 */
	public int getNumber()
	{
		return number;
	}
	
	public void setNumber(int newIndex)
	{
		number = newIndex;
	}

}
