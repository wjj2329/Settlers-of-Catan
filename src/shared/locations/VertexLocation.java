package shared.locations;

import shared.game.map.Index;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation
{

	public boolean isHassettlement() {
		return hassettlement;
	}

	public boolean isHascity() {
		return hascity;
	}

	private HexLocation hexLoc;
	private VertexDirection dir;

	private  VertexDirection getVertexDirection()
	{
		return dir;
	}

	public void setHassettlement(boolean hassettlement) {
		this.hassettlement = hassettlement;
	}

	public void setHascity(boolean hascity) {
		this.hascity = hascity;
	}

	private boolean hassettlement=false;
	private boolean hascity=false;
	private Settlement settlement = null;
	private City city = null;
	
	public VertexLocation(HexLocation hexLoc, VertexDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public HexLocation getHexLoc()
	{
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
	}
	
	public VertexDirection getDir()
	{
		return dir;
	}
	
	private void setDir(VertexDirection direction)
	{
		this.dir = direction;
	}
	
	@Override
	public String toString()
	{
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		VertexLocation other = (VertexLocation)obj;
		if(dir != other.dir)
			return false;
		if(hexLoc == null)
		{
			if(other.hexLoc != null)
				return false;
		}
		else if(!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation()
	{
		
		// Return location that has direction NW or NE
		
		switch (dir)
		{
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthWest),
										  VertexDirection.NorthEast);
			case SouthWest:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthWest);
			case SouthEast:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthEast);
			case East:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthEast),
										  VertexDirection.NorthWest);
			default:
				//assert false;
				return null;
		}
	}

	public Settlement getSettlement()
	{
		//assert(settlement != null);
		return settlement;
	}

	public void setSettlement(Settlement settlement)
	{
		// make sure that settlement isn't null
		if (this.settlement == null)
		{
			this.settlement = new Settlement(settlement.getHexLocation(), settlement.getVertexLocation(),new Index(2));
		}
		this.settlement = settlement;
	}

	public City getCity()
	{
		//assert(city != null);
		return city;
	}

	public void setCity(City city)
	{
		if (this.city == null)
		{
			this.city = new City(city.getHexLocation(), city.getVertexLocation(), new Index(2));
		}
		else
		{
			this.city = city;
		}
	}
}

