package shared.game.map.Hex;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.map.Port;
import shared.locations.HexLocation;

/**
 * @author William
 * Hex class: Represents each individual hex on the playing board.
 */
public class Hex 
{
	private HexLocation location = null;
	private HexType resourcetype = null;
	private NumberToken resourcenumber=null;
	private PortType myport=null;
	
	/**
	 * Hex Constructor
	 * @param location: where the hex is located on the map.
	 * @param resourcetype: what kind of resource can be found on the hex.
	 * @param resourcenumber: number of the resource
	 * @pre HexLocation is not null
	 * 						resourceType is not null
	 * 						resourcenumber is not negative
	 * @post same as above. 
	 */
	public Hex(HexLocation location, HexType resourcetype, NumberToken resourcenumber, PortType myport)
	{
		this.location=location;
		this.resourcetype = resourcetype;
		this.resourcenumber = resourcenumber;
		this.myport=myport;
	}
	public boolean CanPlaceHex()
	{
		if(resourcetype.equals(HexType.WATER)&&resourcenumber.getValue()>0)
		{
			return false;
		}
		if(resourcetype.equals(HexType.DESERT)&&resourcenumber.getValue()>0)
		{
			return false;
		}
		if(resourcetype!=(HexType.WATER)&&myport!=(PortType.BLANK))
		{
			return false;
		}
		return true;
	}

}
