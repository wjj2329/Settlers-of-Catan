package shared.game.map.Hex;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.map.Port;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.*;

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
	private Port myport=null;
	private ArrayList<Settlement> settlements = new ArrayList<>();
	private ArrayList<City> cities = new ArrayList<>();
	private ArrayList<RoadPiece> roads=new ArrayList<>();

	private VertexLocation northeast=null;
	private VertexLocation northwest=null;
	private VertexLocation southwest=null;
	private VertexLocation southeast=null;
	private VertexLocation east=null;
	private VertexLocation west=null;

	private EdgeLocation nw = null;
	private EdgeLocation n = null;
	private EdgeLocation ne = null;
	private EdgeLocation se = null;
	private EdgeLocation s = null;
	private EdgeLocation sw = null;
	
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
	public Hex(HexLocation location, HexType resourcetype, NumberToken resourcenumber, Port myport)
	{
		this.location=location;
		this.resourcetype = resourcetype;
		this.resourcenumber = resourcenumber;
		this.myport=myport;
		northeast = new VertexLocation(location, VertexDirection.NorthEast);
		northwest = new VertexLocation(location, VertexDirection.NorthWest);
		southwest = new VertexLocation(location, VertexDirection.SouthWest);
		southeast = new VertexLocation(location, VertexDirection.SouthEast);
		east = new VertexLocation(location, VertexDirection.East);
		west = new VertexLocation(location, VertexDirection.West);

		nw = new EdgeLocation(location, EdgeDirection.NorthWest);
		n = new EdgeLocation(location, EdgeDirection.North);
		ne = new EdgeLocation(location, EdgeDirection.NorthEast);
		se = new EdgeLocation(location, EdgeDirection.SouthEast);
		s = new EdgeLocation(location, EdgeDirection.South);
		sw = new EdgeLocation(location, EdgeDirection.SouthWest);
	}

	/**this function actually places cities on the hex and the corresponding hexes that intersect at that intersection.
	 * @param mylocation not null
	 */
	public void buildCity(VertexLocation mylocation)
	{
		if(canBuildCityHere(mylocation.getDir()))
		{
			if(mylocation.getDir().equals(VertexDirection.East))
			{
				HexLocation location1=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY());
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				hextoupdate.getSouthwest().setHassettlement(false);
				hextoupdate2.getNorthwest().setHassettlement(false);
				east.setHassettlement(false);
				hextoupdate.getSouthwest().setHascity(true);
				hextoupdate2.getNorthwest().setHascity(true);
				east.setHascity(true);
			}
			if(mylocation.getDir().equals(VertexDirection.West))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				hextoupdate.getSoutheast().setHassettlement(false);
				hextoupdate2.getNortheast().setHassettlement(false);
				west.setHassettlement(false);
				hextoupdate.getSoutheast().setHascity(true);
				hextoupdate2.getNortheast().setHascity(true);
				west.setHascity(true);
			}
			if(mylocation.getDir().equals(VertexDirection.NorthEast))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				hextoupdate.getSoutheast().setHassettlement(false);
				hextoupdate2.getWest().setHassettlement(false);
				northeast.setHassettlement(false);
				northeast.setHascity(true);
				hextoupdate.getSouthwest().setHascity(true);
				hextoupdate2.getWest().setHascity(true);
			}
			if(mylocation.getDir().equals(VertexDirection.NorthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX(), this.location.getY()-1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				hextoupdate.getEast().setHassettlement(false);
				hextoupdate2.getSouthwest().setHassettlement(false);
				northwest.setHassettlement(false);
				northwest.setHascity(true);
				hextoupdate.getEast().setHascity(true);
				hextoupdate2.getSouthwest().setHascity(true);
			}
			if(mylocation.getDir().equals(VertexDirection.SouthEast))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()+1);
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				hextoupdate.getNorthwest().setHassettlement(false);
				hextoupdate2.getEast().setHassettlement(false);
				southeast.setHassettlement(false);
				southeast.setHascity(true);
				hextoupdate.getNorthwest().setHascity(true);
				hextoupdate2.getEast().setHascity(true);

			}
			if(mylocation.getDir().equals(VertexDirection.SouthWest)) {
				HexLocation location1 = new HexLocation(this.location.getX() + 1, this.location.getY());
				HexLocation locatoin2 = new HexLocation(this.location.getX(), this.location.getY() + 1);
				Hex hextoupdate = CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2 = CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				hextoupdate.getWest().setHassettlement(false);
				hextoupdate2.getNortheast().setHassettlement(false);
				southwest.setHassettlement(false);
				southwest.setHascity(true);
				hextoupdate.getWest().setHascity(true);
				hextoupdate2.getNortheast().setHascity(true);
			}
		}

	}

	/**this function actually places settlements on the hex and the corresponding hexes that intersect at that intersection.
	 * @param mylocation not null
     */
	public void buildSettlement(VertexLocation mylocation, Index owner) throws Exception
	{
		if(canBuildSettlementHere(mylocation))
		{
			if(mylocation.getDir().equals(VertexDirection.East))
			{
				HexLocation location1=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY());
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
					if(!this.east.isHassettlement()&&!this.east.isHascity()) {
						if(!hextoupdate.getSoutheast().isHascity()&&!hextoupdate.getSoutheast().isHassettlement()) {
							hextoupdate.getSouthwest().setHassettlement(true);
							hextoupdate2.getNorthwest().setHassettlement(true);
							east.setHassettlement(true);
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
							hextoupdate.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
							hextoupdate2.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.West))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
					if(!this.west.isHassettlement()&&!this.west.isHascity()) {
						if(!hextoupdate.getSouthwest().isHascity()&&!hextoupdate.getSouthwest().isHassettlement()) {
							hextoupdate.getSoutheast().setHassettlement(true);
							hextoupdate2.getNortheast().setHassettlement(true);
							west.setHassettlement(true);
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
							hextoupdate.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
							hextoupdate2.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.NorthEast))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
					if(!this.northeast.isHassettlement()&&!this.northeast.isHascity()) {
						if(!hextoupdate.getEast().isHascity()&&!hextoupdate.getEast().isHassettlement()) {
							hextoupdate.getSoutheast().setHassettlement(true);
							hextoupdate2.getWest().setHassettlement(true);
							northeast.setHassettlement(true);
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
							hextoupdate.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
							hextoupdate2.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.NorthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX(), this.location.getY()-1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
					if(!this.northwest.isHassettlement()&&!this.northwest.isHascity()) {
						if(!hextoupdate.getNortheast().isHascity()&&!hextoupdate.getNortheast().isHassettlement()) {
							hextoupdate.getEast().setHassettlement(true);
							hextoupdate2.getSouthwest().setHassettlement(true);
							northwest.setHassettlement(true);
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
							hextoupdate.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
							hextoupdate2.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.SouthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()+1);
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2=CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
					if(!this.southwest.isHassettlement()&&!this.southwest.isHascity()) {
						if(!hextoupdate.getWest().isHascity()&&!hextoupdate.getWest().isHassettlement()) {
							hextoupdate.getNorthwest().setHassettlement(true);
							hextoupdate2.getEast().setHassettlement(true);
							southwest.setHassettlement(true);
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
							hextoupdate.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
							hextoupdate2.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.SouthEast)) {
				HexLocation location1 = new HexLocation(this.location.getX() + 1, this.location.getY());
				HexLocation locatoin2 = new HexLocation(this.location.getX(), this.location.getY() + 1);
				Hex hextoupdate = CatanGame.singleton.getMymap().getHexes().get(location1);
				Hex hextoupdate2 = CatanGame.singleton.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
					if(!this.southeast.isHassettlement()&&!this.southeast.isHascity()) {
						if(!hextoupdate.getSoutheast().isHascity()&&!hextoupdate.getSoutheast().isHassettlement()) {
							hextoupdate.getWest().setHassettlement(true);
							hextoupdate2.getNortheast().setHassettlement(true);
							southeast.setHassettlement(true);
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
							hextoupdate.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
							hextoupdate2.addSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
						}
					}

				}
			}
		}
	}

	public RoadPiece buildRoad(EdgeLocation edgeLocation, Index playerid)
	{
		RoadPiece roadPiece = new RoadPiece(playerid);
		// set hasRoad to true, then actually create the RoadPiece object and set that.
		switch (edgeLocation.getDir())
		{
			case NorthWest:
				nw.setHasRoad(true);
				nw.setRoadPiece(roadPiece);
				roads.add(roadPiece);
				break;
			case North:
				n.setHasRoad(true);
				n.setRoadPiece(roadPiece);
				roads.add(roadPiece);
				break;
			case NorthEast:
				ne.setHasRoad(true);
				ne.setRoadPiece(roadPiece);
				roads.add(roadPiece);
				break;
			case SouthEast:
				se.setHasRoad(true);
				se.setRoadPiece(roadPiece);
				roads.add(roadPiece);
				break;
			case South:
				s.setHasRoad(true);
				s.setRoadPiece(roadPiece);
				roads.add(roadPiece);
				break;
			case SouthWest:
				sw.setHasRoad(true);
				sw.setRoadPiece(roadPiece);
				roads.add(roadPiece);
				break;
			default:
				assert false;
		}
		return roadPiece;
	}

	/**
	 * This function checks to see if it is even possible to place Settlements on specific Hex
     */
	public boolean canBuildSettlementHere(VertexLocation mylocation)
	{
		if (settlements.size() >= 3)
		{
			return false;
		}
		if(mylocation.getDir().equals(VertexDirection.East))
		{
			if(east.isHascity()||northeast.isHascity()||southeast.isHascity())
			{
				return false;
			}
			if(east.isHassettlement()||northeast.isHassettlement()||southeast.isHassettlement())
			{
				return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.West))
		{
			if(west.isHascity()||northwest.isHascity()||southeast.isHascity())
			{
				return false;
			}
			if(west.isHassettlement()||northwest.isHassettlement()||southwest.isHassettlement())
			{
				return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.NorthEast))
		{
			if (northeast.isHascity()||east.isHascity()||northwest.isHascity())
			{
				return false;
			}
			if(northeast.isHassettlement()||east.isHassettlement()||northwest.isHassettlement())
			{
				return false;
			}

		}
		if(mylocation.getDir().equals(VertexDirection.NorthWest))
		{
			if (northeast.isHascity()||northwest.isHascity()||west.isHascity())
			{
				return false;
			}
			if(northwest.isHassettlement()||northeast.isHassettlement()||west.isHassettlement())
			{
				return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.SouthEast))
		{
			if(southwest.isHascity()||east.isHascity()||southeast.isHascity())
			{
				return false;
			}
			if(southeast.isHassettlement()||southwest.isHassettlement()||east.isHassettlement())
			{
			return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.SouthWest))
		{
			if(southwest.isHascity()||southeast.isHascity()||east.isHascity())
			{
				return false;
			}
			if(southwest.isHassettlement()||southeast.isHassettlement()||west.isHassettlement())
			{
				return false;
			}
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
		if(resourcetype!=(HexType.WATER)&&myport!=(null))
		{
			return false;
		}
		return true;
	}

	/**
	 * Getters and Setters:
	 */
	public HexType getResourcetype()
	{
		return resourcetype;
	}
	public VertexLocation getNortheast() {
		return northeast;
	}

	public void setNortheast(VertexLocation northeast) {
		this.northeast = northeast;
	}

	public VertexLocation getSoutheast() {
		return southeast;
	}

	public void setSoutheast(VertexLocation southeast) {
		this.southeast = southeast;
	}

	public VertexLocation getWest() {
		return west;
	}

	public void setWest(VertexLocation west) {
		this.west = west;
	}

	public VertexLocation getEast() {
		return east;
	}

	public void setEast(VertexLocation east) {
		this.east = east;
	}

	public VertexLocation getSouthwest() {
		return southwest;
	}

	public void setSouthwest(VertexLocation southwest) {
		this.southwest = southwest;
	}

	public VertexLocation getNorthwest() {
		return northwest;
	}

	public void setNorthwest(VertexLocation northwest) {
		this.northwest = northwest;
	}

	/**
	 * Getters and setters for EdgeLocations:
	 * Nw, n, ne, se, s, sw, nw = DIRECTIONS! :)
     */
	public EdgeLocation getNw()
	{
		return nw;
	}

	public void setNw(EdgeLocation nw)
	{
		this.nw = nw;
	}

	public EdgeLocation getN()
	{
		return n;
	}

	public void setN(EdgeLocation n)
	{
		this.n = n;
	}

	public EdgeLocation getNe()
	{
		return ne;
	}

	public void setNe(EdgeLocation ne)
	{
		this.ne = ne;
	}

	public EdgeLocation getSe()
	{
		return se;
	}

	public void setSe(EdgeLocation se)
	{
		this.se = se;
	}

	public EdgeLocation getS()
	{
		return s;
	}

	public void setS(EdgeLocation s)
	{
		this.s = s;
	}

	public EdgeLocation getSw()
	{
		return sw;
	}

	public void setSw(EdgeLocation sw)
	{
		this.sw = sw;
	}

	public HexLocation getLocation()
	{
		return location;
	}

	public void setLocation(HexLocation location)
	{
		this.location = location;
	}

	public int getResourcenumber()
	{
		return this.resourcenumber.getValue();
	}

	public PortType getPortType()
	{
		if(myport==null)
		{
			return null;
		}
		return myport.getType();
	}

	public void setPortlocation(Port port)
	{
		this.myport =port;
	}

	public String getPortLocation()
	{
		if(myport==null)
		{
			return "fail";
		}
		return myport.getDirection().toString();
	}

	public ArrayList<RoadPiece> getRoads()
	{
		return roads;
	}

	public void addSettlement(Settlement settlement)
	{
		settlements.add(settlement);
	}

	public ArrayList<Settlement> getSettlementlist()
	{
		return this.settlements;
	}

	public ArrayList<City> getCities()
	{
		return this.cities;
	}

}
