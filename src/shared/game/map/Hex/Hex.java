package shared.game.map.Hex;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.game.map.Port;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.util.ArrayList;

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
	private ArrayList<Settlement> settlements = new ArrayList<>();
	private ArrayList<City> cities = new ArrayList<>();
	private VertexLocation northeast=new VertexLocation(location, VertexDirection.NorthEast);
	private VertexLocation northwest=new VertexLocation(location, VertexDirection.NorthWest);
	private VertexLocation southwest=new VertexLocation(location, VertexDirection.SouthWest);
	private VertexLocation southeast=new VertexLocation(location, VertexDirection.SouthEast);
	private VertexLocation east=new VertexLocation(location, VertexDirection.East);
	private VertexLocation west=new VertexLocation(location, VertexDirection.West);
	
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

	/**
	 * This function will need further implementation
     */
	public boolean canBuildSettlementHere(VertexLocation mylocation)
	{
		if(mylocation.getDir().equals(VertexDirection.East))
		{
			if(east.isHassettlement()||northeast.isHassettlement()||northwest.isHassettlement())
			{
				return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.West))
		{
			if(west.isHassettlement()||northwest.isHassettlement()||southwest.isHassettlement())
			{
				return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.NorthEast))
		{
			if(northeast.isHassettlement()||east.isHassettlement()||west.isHassettlement())
			{
				return false;
			}

		}
		if(mylocation.getDir().equals(VertexDirection.NorthWest))
		{
			if(northwest.isHassettlement()||northeast.isHassettlement()||west.isHassettlement())
			{
				return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.SouthEast))
		{
			if(southeast.isHassettlement()||southwest.isHassettlement()||east.isHassettlement())
			{
			return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.SouthWest))
		{
			if(southwest.isHassettlement()||southeast.isHassettlement()||west.isHassettlement())
			{
				return false;
			}
		}
		if (settlements.size() >= 3)
		{
			return false;
		}

		return true;
	}

	/**
	 * This function will need further implementation
	 */
	public boolean canBuildCityHere(VertexDirection mydirection)
	{
		if(mydirection.equals(VertexDirection.SouthEast))
		{
			return southeast.isHassettlement();
		}
		if(mydirection.equals(VertexDirection.SouthWest))
		{
			return southwest.isHassettlement();
		}
		if(mydirection.equals(VertexDirection.West))
		{
			return (west.isHassettlement());
		}
		if(mydirection.equals(VertexDirection.East))
		{
			return east.isHassettlement();
		}
		if(mydirection.equals(VertexDirection.NorthWest))
		{
			return northwest.isHassettlement();
		}
		if(mydirection.equals(VertexDirection.NorthEast))
		{
			return northeast.isHassettlement();
		}
		if (settlements.size() <= 0 || cities.size() >= 3)
		{
			return false;
		}
		return true;
	}
	public boolean CanPlaceRobber()
	{
		if(this.resourcetype.equals(HexType.WATER))
		{
			return false;
		}
		return true;
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
