package shared.game.map.Hex;

import shared.locations.HexLocation;

/**
 * @author William
 * Hex class: Represents each individual hex on the playing board.
 */
public class Hex 
{
	private HexLocation location = null;
	private String resourcetype = null;
	private int resourcenumber = 0;
	
	/**
	 * Hex Constructor
	 * @param location: where the hex is located on the map.
	 * @param resourcetype: what kind of resource can be found on the hex.
	 * @param resourcenumber: number of the resource
	 * @custom.mytag1 pre: HexLocation is not null
	 * 						resourceType is not null
	 * 						resourcenumber is not negative
	 * @custom.mytag2 post: same as above. 
	 */
	public Hex(HexLocation location, String resourcetype, int resourcenumber)
	{
		this.location=location;
		this.resourcetype = resourcetype;
		this.resourcenumber = resourcenumber;
	}
}
