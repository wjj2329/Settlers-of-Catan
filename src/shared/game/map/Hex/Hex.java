package shared.game.map.Hex;

import client.model.ModelFacade;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.game.CatanGame;
import shared.game.map.Index;
import shared.game.map.Port;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.*;
import sun.print.resources.serviceui_fr;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author William
 * Hex class: Represents each individual hex on the playing board.
 */
public class Hex 
{
	private HexLocation location = null;
	private HexType resourcetype = HexType.DESERT;
	private NumberToken resourcenumber=null;
	private Port myport=null;
	private ArrayList<Settlement> settlements = new ArrayList<>();
	private ArrayList<City> cities = new ArrayList<>();
	private ArrayList<RoadPiece> roads=new ArrayList<>();

	private VertexLocation northeast=new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);
	private VertexLocation northwest=new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);
	private VertexLocation southwest=new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);
	private VertexLocation southeast=new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);
	private VertexLocation east=new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);
	private VertexLocation west=new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);

	private EdgeLocation nw = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North); // was null
	private EdgeLocation n = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North);
	private EdgeLocation ne = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North);
	private EdgeLocation se = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North);
	private EdgeLocation s = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North);
	private EdgeLocation sw = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North);
	
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
		nw.setHasRoad(false);
		n = new EdgeLocation(location, EdgeDirection.North);
		n.setHasRoad(false);
		ne = new EdgeLocation(location, EdgeDirection.NorthEast);
		ne.setHasRoad(false);
		se = new EdgeLocation(location, EdgeDirection.SouthEast);
		se.setHasRoad(false);
		s = new EdgeLocation(location, EdgeDirection.South);
		s.setHasRoad(false);
		sw = new EdgeLocation(location, EdgeDirection.SouthWest);
		sw.setHasRoad(false);
	}

	/**this function actually places cities on the hex and the corresponding hexes that intersect at that intersection.
	 * @param mylocation not null
	 */
	public void buildCity(VertexLocation mylocation, Index owner)
	{
		//dif(canBuildCityHere(mylocation.getDir()))
		{
			if(mylocation.getDir().equals(VertexDirection.East))
			{
				HexLocation location1=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY());
				Hex hextoupdate= ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				hextoupdate.getSouthwest().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.SouthWest),owner));
				hextoupdate.getNorthwest().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.NorthWest),owner));
				east.setCity(new City(this.location,new VertexLocation(this.location,VertexDirection.East),owner));

				hextoupdate.getSouthwest().setHassettlement(false);
				hextoupdate2.getNorthwest().setHassettlement(false);
				east.setHassettlement(false);
				east.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
				hextoupdate.getSouthwest().setHascity(true);
				hextoupdate2.getNorthwest().setHascity(true);
				east.setHascity(true);

			}
			if(mylocation.getDir().equals(VertexDirection.West))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				hextoupdate.getSoutheast().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.SouthEast),owner));
				hextoupdate.getNortheast().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.NorthEast),owner));
				west.setCity(new City(this.location,new VertexLocation(this.location,VertexDirection.West),owner));

				hextoupdate.getSoutheast().setHassettlement(false);
				hextoupdate2.getNortheast().setHassettlement(false);
				west.setHassettlement(false);
				west.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
				hextoupdate.getSoutheast().setHascity(true);
				hextoupdate2.getNortheast().setHascity(true);
				west.setHascity(true);
			}
			if(mylocation.getDir().equals(VertexDirection.NorthEast))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				hextoupdate.getSoutheast().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.SouthEast),owner));
				hextoupdate.getWest().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.West),owner));
				northeast.setCity(new City(this.location,new VertexLocation(this.location,VertexDirection.NorthEast),owner));

				hextoupdate.getSoutheast().setHassettlement(false);
				hextoupdate2.getWest().setHassettlement(false);
				northeast.setHassettlement(false);
				northeast.setHascity(true);
				northeast.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
				hextoupdate.getSouthwest().setHascity(true);
				hextoupdate2.getWest().setHascity(true);
			}
			if(mylocation.getDir().equals(VertexDirection.NorthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX(), this.location.getY()-1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				hextoupdate.getEast().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.East),owner));
				hextoupdate.getSouthwest().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.SouthWest),owner));
				northwest.setCity(new City(this.location,new VertexLocation(this.location,VertexDirection.NorthWest),owner));


				hextoupdate.getEast().setHassettlement(false);
				hextoupdate2.getSouthwest().setHassettlement(false);
				northwest.setHassettlement(false);
				northwest.setHascity(true);
				northwest.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
				hextoupdate.getEast().setHascity(true);
				hextoupdate2.getSouthwest().setHascity(true);
			}
			if(mylocation.getDir().equals(VertexDirection.SouthEast))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()+1);
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				hextoupdate.getNorthwest().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.NorthWest),owner));
				hextoupdate.getEast().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.East),owner));
				southeast.setCity(new City(this.location,new VertexLocation(this.location,VertexDirection.SouthEast),owner));

				hextoupdate.getNorthwest().setHassettlement(false);
				hextoupdate2.getEast().setHassettlement(false);
				southeast.setHassettlement(false);
				southeast.setHascity(true);
				southeast.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
				hextoupdate.getNorthwest().setHascity(true);
				hextoupdate2.getEast().setHascity(true);

			}
			if(mylocation.getDir().equals(VertexDirection.SouthWest)) {
				HexLocation location1 = new HexLocation(this.location.getX() + 1, this.location.getY());
				HexLocation locatoin2 = new HexLocation(this.location.getX(), this.location.getY() + 1);
				Hex hextoupdate = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2 = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				hextoupdate.getWest().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.West),owner));
				hextoupdate.getNortheast().setCity(new City(this.location,new VertexLocation(this.getLocation(),VertexDirection.NorthEast),owner));
				southwest.setCity(new City(this.location,new VertexLocation(this.location,VertexDirection.SouthWest),owner));

				hextoupdate.getWest().setHassettlement(false);
				hextoupdate2.getNortheast().setHassettlement(false);
				southwest.setHassettlement(false);
				southwest.setHascity(true);
				southwest.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
				hextoupdate.getWest().setHascity(true);
				hextoupdate2.getNortheast().setHascity(true);
			}
		}

	}

	/**this function actually places settlements on the hex and the corresponding hexes that intersect at that intersection.
	 * @param mylocation not null
     */
	public void buildSettlementNormal(VertexLocation mylocation, Index owner) throws Exception
	{
		if(canBuildSettlementHereNormal(mylocation))
		{
			//System.out.println("i can come and build");
			if(mylocation.getDir().equals(VertexDirection.East))
			{
				HexLocation location1=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY());
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.ne.hasRoad()||this.se.hasRoad()||hextoupdate.s.hasRoad())
				{
					if(ne.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||se.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID()))
					{
						if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
							if (!this.east.isHassettlement() && !this.east.isHascity()) {
								if (!hextoupdate.getSoutheast().isHascity() && !hextoupdate.getSoutheast().isHassettlement()) {
									hextoupdate.getSouthwest().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(),VertexDirection.SouthWest),owner));
									hextoupdate2.getNorthwest().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(),VertexDirection.NorthWest),owner));

									hextoupdate.getSouthwest().setHassettlement(true);
									hextoupdate2.getNorthwest().setHassettlement(true);
									east.setHassettlement(true);
									east.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
									east.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
									settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
									hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
									hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
								}
							}
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.West))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.nw.hasRoad()||this.sw.hasRoad()||hextoupdate.s.hasRoad())
				{
					if(nw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||sw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {
						if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
							if (!this.west.isHassettlement() && !this.west.isHascity()) {
								if (!hextoupdate.getSouthwest().isHascity() && !hextoupdate.getSouthwest().isHassettlement()) {
									hextoupdate.getSoutheast().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
									hextoupdate2.getNortheast().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));

									hextoupdate.getSoutheast().setHassettlement(true);
									hextoupdate2.getNortheast().setHassettlement(true);
									west.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
									west.setHassettlement(true);
									west.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
									settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
									hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
									hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
								}
							}
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.NorthEast))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.n.hasRoad()||this.nw.hasRoad()||hextoupdate.se.hasRoad())
				{
					if(n.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||se.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||nw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {
						if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
							if (!this.northeast.isHassettlement() && !this.northeast.isHascity()) {
								if (!hextoupdate.getEast().isHascity() && !hextoupdate.getEast().isHassettlement()) {
									hextoupdate.getSoutheast().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
									hextoupdate2.getWest().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));

									hextoupdate.getSoutheast().setHassettlement(true);
									hextoupdate2.getWest().setHassettlement(true);
									northeast.setHassettlement(true);
									northeast.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
									northeast.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
									settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
									hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
									hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
								}
							}
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.NorthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX(), this.location.getY()-1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.nw.hasRoad()||this.n.hasRoad()||hextoupdate.ne.hasRoad()) {
					if(nw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||n.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||ne.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID()))
					{
						if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
							if (!this.northwest.isHassettlement() && !this.northwest.isHascity()) {
								if (!hextoupdate.getNortheast().isHascity() && !hextoupdate.getNortheast().isHassettlement()) {
									hextoupdate.getEast().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
									hextoupdate2.getSouthwest().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));

									hextoupdate.getEast().setHassettlement(true);
									hextoupdate2.getSouthwest().setHassettlement(true);
									northwest.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
									northwest.setHassettlement(true);
									northwest.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
									settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
									hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
									hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
								}
							}
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.SouthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()+1);
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.sw.hasRoad()||this.s.hasRoad()||hextoupdate.nw.hasRoad()) {
					if(sw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||nw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID()))
					{
						if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
							if (!this.southwest.isHassettlement() && !this.southwest.isHascity()) {
								if (!hextoupdate.getWest().isHascity() && !hextoupdate.getWest().isHassettlement()) {
									hextoupdate.getNorthwest().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
									hextoupdate2.getEast().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));

									hextoupdate.getNorthwest().setHassettlement(true);
									hextoupdate2.getEast().setHassettlement(true);
									southwest.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
									southwest.setHassettlement(true);
									southwest.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
									settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
									hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
									hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
								}
							}
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.SouthEast)) {
				HexLocation location1 = new HexLocation(this.location.getX() + 1, this.location.getY());
				HexLocation locatoin2 = new HexLocation(this.location.getX(), this.location.getY() + 1);
				Hex hextoupdate = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2 = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				if(this.se.hasRoad()||this.s.hasRoad()||hextoupdate.sw.hasRoad())
				{
					if(s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||se.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||sw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {
						if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
							if (!this.southeast.isHassettlement() && !this.southeast.isHascity()) {
								if (!hextoupdate.getSoutheast().isHascity() && !hextoupdate.getSoutheast().isHassettlement()) {
									hextoupdate.getWest().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
									hextoupdate2.getNortheast().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));

									hextoupdate.getWest().setHassettlement(true);
									hextoupdate2.getNortheast().setHassettlement(true);
									southeast.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
									southeast.setHassettlement(true);
									southeast.getSettlement().setOwner(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID());
									settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
									hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
									hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
								}
							}
						}
					}

				}
			}
		}
	}

	public RoadPiece buildRoad(EdgeLocation edgeLocation, Index playerid)
	{
		RoadPiece roadPiece = new RoadPiece(playerid);
		if(edgeLocation==null)
		{
			//System.out.println("In the build road funciton in the HEX THE EDGE LOCATION IS NULL FOOOOLISH FOOL");
		}
		edgeLocation.setHasRoad(true);
		roadPiece.setLocation(edgeLocation);
		//roads.add(roadPiece); //this statement is called twice(this and in switch statement) so i comment this one out.
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
				break;
				//assert false;
		}
		return roadPiece;
	}

	public void buildSettlement(VertexLocation mylocation, Index owner) throws Exception
	{
		//System.out.println("MY LOCATION TO CHECK IN THIS THING IS "+mylocation.getDir().toString());
		//if(canBuildSettlementHereStartup(mylocation))
		{
		//	System.out.println("I PASS CAN BUILD SETTLEMENT START UP");
			if(mylocation.getDir().equals(VertexDirection.East))
			{
				HexLocation location1=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY());
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				/*
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				*/
				//if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER)
				{
					//if(!this.east.isHassettlement()&&!this.east.isHascity())
					{
						//if(!hextoupdate.getSoutheast().isHascity()&&!hextoupdate.getSoutheast().isHassettlement())
						{
							hextoupdate.getSouthwest().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(),VertexDirection.SouthWest),owner));
							hextoupdate2.getNorthwest().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(),VertexDirection.NorthWest),owner));

							hextoupdate.getSouthwest().setHassettlement(true);
							hextoupdate2.getNorthwest().setHassettlement(true);

							east.setHassettlement(true);
							east.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
							east.getSettlement().setOwner(owner);
							//System.out.println("i come to east and add settlements to 3 hexes");
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.East), owner));
							hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(), VertexDirection.SouthWest), owner));
							hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(), VertexDirection.NorthWest), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.West))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				/*
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				*/
				//if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER)
				{
				//	if(!this.west.isHassettlement()&&!this.west.isHascity())
					{
				//		if(!hextoupdate.getSouthwest().isHascity()&&!hextoupdate.getSouthwest().isHassettlement())
						{
							hextoupdate.getSoutheast().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(),VertexDirection.SouthEast),owner));
							hextoupdate2.getNortheast().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(),VertexDirection.NorthEast),owner));

							hextoupdate.getSoutheast().setHassettlement(true);
							hextoupdate2.getNortheast().setHassettlement(true);
							west.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
							west.setHassettlement(true);
							west.getSettlement().setOwner(owner);
							//System.out.println("i come to west and add settlements to 3 hexes");
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.West), owner));
							hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(), VertexDirection.SouthEast), owner));
							hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(), VertexDirection.NorthEast), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.NorthEast))
			{
				//System.out.println("I DO ENTER THE NORHT EAST THING");
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				//System.out.println(" I COME HERE");
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				//System.out.println(" I Come here too but");
				/*
				if(hextoupdate==null||hextoupdate2==null)
				{
					System.out.println("I DIE");
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				*/
				//System.out.println("I SURVIVE THIS ORDEAL");
				//if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=HexType.WATER||hextoupdate2.getResourcetype()!=HexType.WATER)
				{
					//if(!this.northeast.isHassettlement()&&!this.northeast.isHascity())
					{
						//if(!hextoupdate.getEast().isHascity()&&!hextoupdate.getEast().isHassettlement())
						{
							hextoupdate.getSoutheast().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(),VertexDirection.SouthEast),owner));
							hextoupdate2.getWest().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(),VertexDirection.West),owner));

							hextoupdate.getSoutheast().setHassettlement(true);
							hextoupdate2.getWest().setHassettlement(true);
							northeast.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
							northeast.setHassettlement(true);
							northeast.getSettlement().setOwner(owner);
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthEast), owner));
							//System.out.println("i come to northeast and add settlements to 3 hexes");
							hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(), VertexDirection.SouthEast), owner));
							hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(), VertexDirection.West), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.NorthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
				HexLocation locatoin2=new HexLocation(this.location.getX(), this.location.getY()-1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				/*
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				*/
				//if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER)
				{
				//	if(!this.northwest.isHassettlement()&&!this.northwest.isHascity())
					{
				//		if(!hextoupdate.getNortheast().isHascity()&&!hextoupdate.getNortheast().isHassettlement())
						{
							hextoupdate.getEast().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(),VertexDirection.East),owner));
							hextoupdate2.getSouthwest().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate.getLocation(),VertexDirection.SouthWest),owner));

							hextoupdate.getEast().setHassettlement(true);
							hextoupdate2.getSouthwest().setHassettlement(true);
							northwest.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
							northwest.setHassettlement(true);
							northwest.getSettlement().setOwner(owner);
							//System.out.println("i come to NorthWest and add settlements to 3 hexes");
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.NorthWest), owner));
							hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(), VertexDirection.East), owner));
							hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(), VertexDirection.SouthWest), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.SouthWest))
			{
				HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()+1);
				HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				/*
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				*/
				//if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER)
				{
				//	if(!this.southwest.isHassettlement()&&!this.southwest.isHascity())
					{
				//		if(!hextoupdate.getWest().isHascity()&&!hextoupdate.getWest().isHassettlement())
						{
							hextoupdate.getNorthwest().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(),VertexDirection.NorthWest),owner));
							hextoupdate2.getEast().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(),VertexDirection.East),owner));

							hextoupdate.getNorthwest().setHassettlement(true);
							hextoupdate2.getEast().setHassettlement(true);
							southwest.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
							southwest.setHassettlement(true);
							southwest.getSettlement().setOwner(owner);
							//System.out.println("i come to southwest and add settlements to 3 hexes");
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthWest), owner));
							hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(), VertexDirection.NorthWest), owner));
							hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(), VertexDirection.East), owner));
						}
					}
				}
			}
			if(mylocation.getDir().equals(VertexDirection.SouthEast)) {
				HexLocation location1 = new HexLocation(this.location.getX() + 1, this.location.getY());
				HexLocation locatoin2 = new HexLocation(this.location.getX(), this.location.getY() + 1);
				Hex hextoupdate = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2 = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				/*
				if(hextoupdate==null||hextoupdate2==null)
				{
					Exception e = new Exception();
					e.printStackTrace();
					throw e;
				}
				*/
				//if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER)
				{
				//	if(!this.southeast.isHassettlement()&&!this.southeast.isHascity())
					{
				//		if(!hextoupdate.getSoutheast().isHascity()&&!hextoupdate.getSoutheast().isHassettlement())
						{
							hextoupdate.getWest().setSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(),VertexDirection.West),owner));
							hextoupdate2.getNortheast().setSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(),VertexDirection.NorthEast),owner));

							hextoupdate.getWest().setHassettlement(true);
							hextoupdate2.getNortheast().setHassettlement(true);
							southeast.setSettlement(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
							southeast.setHassettlement(true);
							southeast.getSettlement().setOwner(owner);
							//System.out.println("i come to southwest and add settlements to 3 hexes");
							settlements.add(new Settlement(this.getLocation(), new VertexLocation(this.getLocation(), VertexDirection.SouthEast), owner));
							hextoupdate.addSettlement(new Settlement(hextoupdate.getLocation(), new VertexLocation(hextoupdate.getLocation(), VertexDirection.West), owner));
							hextoupdate2.addSettlement(new Settlement(hextoupdate2.getLocation(), new VertexLocation(hextoupdate2.getLocation(), VertexDirection.NorthEast), owner));
						}
					}

				}
			}
		}
	}

	public boolean canBuildSettlementHereNormal(VertexLocation mylocation)
	{
		//System.out.println("i come here to check for normal settlement placement");

		if(mylocation.getDir().equals(VertexDirection.East))
		{
			if(east.isHascity()||northeast.isHascity()||southeast.isHascity())
			{
				//System.out.println("I return false because of distance on same hex rule for city");
				return false;
			}
			if(east.isHassettlement()||northeast.isHassettlement()||southeast.isHassettlement())
			{
				//System.out.println("I return false because of distance on same hex rule for settlement");
				return false;
			}
			{
				HexLocation location1=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY());
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					//System.out.println("I RETURN FALSE BECAUSE TWO HEXES NEXT TO ME ARE NULL IN THE HEX CHCECK");
					return false;
				}
				if(hextoupdate.southeast.isHassettlement()||hextoupdate.southeast.isHascity())
				{
					return false;
				}
				if(this.ne.hasRoad()||this.se.hasRoad()||hextoupdate.s.hasRoad())
				{
						if(ne.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||se.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||hextoupdate.s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID()))
						{
							if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER)
							{
								if (!this.east.isHassettlement() && !this.east.isHascity())
								{
									//if (!hextoupdate.getSoutheast().isHascity() && !hextoupdate.getSoutheast().isHassettlement())
									{
										return true;
									}
								}
								return false;
							}
							return false;
						}
					return false;
				}
				//System.out.println("I RETURN FALSE BECAUSE OF ROAD REQUIREMENTS");
				return false;
			}
		}
		if(mylocation.getDir().equals(VertexDirection.West))
		{
			if(west.isHascity()||northwest.isHascity()||southwest.isHascity())
			{
				//System.out.println("I return false because of distance on same hex rule for city");

				return false;
			}
			if(west.isHassettlement()||northwest.isHassettlement()||southwest.isHassettlement())
			{
				//System.out.println("I return false because of distance on same hex rule for settlement");
				return false;
			}
			HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
			HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				//System.out.println("I RETURN FALSE BECAUSE TWO HEXES NEXT TO ME ARE NULL IN THE HEX CHCECK");
				Exception e = new Exception();
				e.printStackTrace();
				return false;
			}
			if(hextoupdate.southwest.isHassettlement()||hextoupdate.southwest.isHascity())
			{
				return false;
			}
			if(this.nw.hasRoad()||this.sw.hasRoad()||hextoupdate.s.hasRoad())
			{
				if(nw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||sw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||hextoupdate.s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {
					if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
						if (!this.west.isHassettlement() && !this.west.isHascity()) {
							//if (!hextoupdate.getSouthwest().isHascity() && !hextoupdate.getSouthwest().isHassettlement()) {
								//System.out.print("i come here and I have a road at nw or sw or s");
								return true;
							//}
							//else
								//System.out.println("THE HEX I'm on already has a settlement here");
							//return false;
						}
					}
				}
			}
			//System.out.println("nw and sw and s don't have roads");
			return false;
		}
		if(mylocation.getDir().equals(VertexDirection.NorthEast))
		{
			if (northeast.isHascity()||east.isHascity()||northwest.isHascity())
			{				//System.out.println("I return false because of distance on same hex rule for city");

				return false;
			}
			if(northeast.isHassettlement()||east.isHassettlement()||northwest.isHassettlement())
			{
				//System.out.println("I return false because of distance on same hex rule for settlement");

				return false;
			}
			HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()-1);
			HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY()-1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				//System.out.println("I RETURN FALSE BECAUSE TWO HEXES NEXT TO ME ARE NULL IN THE HEX CHCECK");

				Exception e = new Exception();
				//e.printStackTrace();
				return false;
			}
			if(hextoupdate.east.isHassettlement()||hextoupdate.east.isHascity())
			{
				return false;
			}
			if(this.n.hasRoad()||this.ne.hasRoad()||hextoupdate.se.hasRoad()) {
				if(ne.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||hextoupdate.se.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||n.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {
					if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
						if (!this.northeast.isHassettlement() && !this.northeast.isHascity()) {
							//if (!hextoupdate.getEast().isHascity() && !hextoupdate.getEast().isHassettlement()) //{
								//System.out.print("i come here and I have a road at ne or se or n");
								return true;
							//}
							//else
							//	System.out.println("THE HEX I'm on already has a settlement here");
							//return false;
						}
					}
				}
			}
			//System.out.println("I RETURN FALSE BECAUSE OF ROAD REQUIREMENTS");

			//System.out.println("n and ne and se don't have roads");

			return false;
		}
		if(mylocation.getDir().equals(VertexDirection.NorthWest))
		{
			if (northeast.isHascity()||northwest.isHascity()||west.isHascity())
			{
				//System.out.println("I return false because of distance on same hex rule for city");

				return false;
			}
			if(northwest.isHassettlement()||northeast.isHassettlement()||west.isHassettlement())
			{
				//System.out.println("I return false because of distance on same hex rule for settlement");

				return false;
			}
			HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
			HexLocation locatoin2=new HexLocation(this.location.getX(), this.location.getY()-1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				//System.out.println("I RETURN FALSE BECAUSE TWO HEXES NEXT TO ME ARE NULL IN THE HEX CHCECK");

				Exception e = new Exception();
				e.printStackTrace();
				return false;
			}
			if(hextoupdate.northeast.isHassettlement()||hextoupdate.northeast.isHascity())
			{
				return false;
			}
			if(this.nw.hasRoad()||this.n.hasRoad()||hextoupdate.ne.hasRoad()) {
				if(nw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||n.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||hextoupdate.ne.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {
					if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
						if (!this.northwest.isHassettlement() && !this.northwest.isHascity()) {
							//if (!hextoupdate.getNortheast().isHascity() && !hextoupdate.getNortheast().isHassettlement()) {
								//System.out.print("i come here and I have a road at ne or n or nw");
								return true;
							//}
							//else
								//System.out.println("THE HEX I'm on already has a settlement here");
							//return false;
						}
					}
				}
			}
			//System.out.println("nw and ne and n don't have roads");

			return false;
		}
		if(mylocation.getDir().equals(VertexDirection.SouthEast))
		{
			if(southwest.isHascity()||east.isHascity()||southeast.isHascity())
			{
				//System.out.println("I return false because of distance on same hex rule for city");

				return false;
			}
			if(southeast.isHassettlement()||southwest.isHassettlement()||east.isHassettlement())
			{
				//System.out.println("I return false because of distance on same hex rule for settlement");

				return false;
			}
			HexLocation location1 = new HexLocation(this.location.getX() + 1, this.location.getY());
			HexLocation locatoin2 = new HexLocation(this.location.getX(), this.location.getY() + 1);
			Hex hextoupdate = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2 = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				//System.out.println("I RETURN FALSE BECAUSE TWO HEXES NEXT TO ME ARE NULL IN THE HEX CHCECK");

				return false;
			}
			if(hextoupdate.southwest.isHassettlement()||hextoupdate.southwest.isHascity())
			{
				return false;
			}
			if(this.se.hasRoad()||this.s.hasRoad()||hextoupdate.sw.hasRoad())
			{
				if(s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||se.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||hextoupdate.sw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {

					if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
						if (!this.southeast.isHassettlement() && !this.southeast.isHascity()) {
							//if (!hextoupdate.getSoutheast().isHascity() && !hextoupdate.getSoutheast().isHassettlement()) {
								//System.out.print("i come here and I have a road at s or se or sw");
								return true;
						//	}
							//else
							//	System.out.println("THE HEX I'm on already has a settlement here");
							//return false;
						}
					}
				}
			}
			//System.out.println("se and sw and s don't have roads");

			return false;
		}
		if(mylocation.getDir().equals(VertexDirection.SouthWest))
		{
			if(southwest.isHascity()||southeast.isHascity()||west.isHascity())
			{
				//System.out.println("I return false because of distance on same hex rule for city");

				return false;
			}
			if(southwest.isHassettlement()||southeast.isHassettlement()||west.isHassettlement())
			{
				//System.out.println("I return false because of distance on same hex rule for settlement");

				return false;
			}
			HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()+1);
			HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				//System.out.println("I RETURN FALSE BECAUSE TWO HEXES NEXT TO ME ARE NULL IN THE HEX CHCECK");

				Exception e = new Exception();
				e.printStackTrace();
				return false;
			}
			if(hextoupdate.west.isHassettlement()||hextoupdate.west.isHascity())
			{
				return false;
			}
			if(this.sw.hasRoad()||this.s.hasRoad()||hextoupdate.nw.hasRoad()) {

				if(sw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||s.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())||hextoupdate.nw.getRoadPiece().getPlayerWhoOwnsRoad().equals(ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID())) {
					if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
						if (!this.southwest.isHassettlement() && !this.southwest.isHascity()) {
							//if (!hextoupdate.getWest().isHascity() && !hextoupdate.getWest().isHassettlement()) {
								//System.out.print("i come here and I have a road at nw or sw or s");
								return true;
							//}
							//else
							//	System.out.println("THE HEX I'm on already has a settlement here");
							//return false;
						}
					}
				}
			}
			//System.out.println("sw and s and nw don't have roads");

			return false;
		}

		return true;
	}

	/**
	 * This function checks to see if it is even possible to place Settlements on specific Hex
     */


	public boolean canBuildSettlementHereStartup(VertexLocation mylocation)
	{

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
			{
				HexLocation location1=new HexLocation(this.location.getX()+1, this.location.getY()-1);
				HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY());
				Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
				if(hextoupdate==null||hextoupdate2==null)
				{
					return false;
				}
				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER)
				{
					if(!this.east.isHassettlement()&&!this.east.isHascity()) {
						if(!hextoupdate.getSoutheast().isHascity()&&!hextoupdate.getSoutheast().isHassettlement()) {
							return true;
						}
					}
				}
			}
			return false;
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
			HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
			HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				Exception e = new Exception();
				e.printStackTrace();
				return false;
			}
			if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
				if(!this.west.isHassettlement()&&!this.west.isHascity()) {
					if(!hextoupdate.getSouthwest().isHascity()&&!hextoupdate.getSouthwest().isHassettlement()) {
						return true;
					}
				}
			}
			return false;
		}
		if(mylocation.getDir().equals(VertexDirection.NorthEast))
		{
			//System.out.println("I COME TO CHECK IF I CAN IN THE NORTH EAST");
			if (northeast.isHascity()||east.isHascity()||northwest.isHascity())
			{
				//System.out.println("I FAIL BECAUSE OF CITIES");
				return false;
			}
			if(northeast.isHassettlement()||east.isHassettlement()||northwest.isHassettlement())
			{
				//System.out.println("I FAIL BECAUSE I HAVE A SETTLEMENT");
				return false;
			}
			HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()-1);
			HexLocation locatoin2=new HexLocation(this.location.getX()+1, this.location.getY()-1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				//System.out.println("I FAIL BECAUSE OF NULL");
				Exception e = new Exception();
				e.printStackTrace();
				return false;
			}
				if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != HexType.WATER || hextoupdate2.getResourcetype() != HexType.WATER) {
					if (!this.northeast.isHassettlement() && !this.northeast.isHascity())
					{

						if (!hextoupdate.getEast().isHascity() && !hextoupdate.getEast().isHassettlement())
						{
							return true;
						}
						else{
							//System.out.println("I HAVE A CITY OR SETTLEMENT ON ADDJACENT HEX");
						}
					}
					else
					{
						//System.out.println("I HAVE CITY OR SETTLEMENT HERE ON HEX");
					}

				}

				return false;
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
			HexLocation location1=new HexLocation(this.location.getX()-1, this.location.getY());
			HexLocation locatoin2=new HexLocation(this.location.getX(), this.location.getY()-1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				Exception e = new Exception();
				//e.printStackTrace();
				return false;
			}
				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
					if (!this.northwest.isHassettlement() && !this.northwest.isHascity()) {
						if (!hextoupdate.getNortheast().isHascity() && !hextoupdate.getNortheast().isHassettlement()) {
							return true;
						}
					}

			}
			return false;
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
			HexLocation location1 = new HexLocation(this.location.getX() + 1, this.location.getY());
			HexLocation locatoin2 = new HexLocation(this.location.getX(), this.location.getY() + 1);
			Hex hextoupdate = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2 = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				return false;
			}
				if (this.resourcetype != HexType.WATER || hextoupdate.getResourcetype() != (HexType.WATER) || hextoupdate2.getResourcetype() != HexType.WATER) {
				if(!this.southeast.isHassettlement()&&!this.southeast.isHascity()) {
					if(!hextoupdate.getSoutheast().isHascity()&&!hextoupdate.getSoutheast().isHassettlement()) {
						return true;
					}

				}

			}
			return false;
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
			HexLocation location1=new HexLocation(this.location.getX(), this.location.getY()+1);
			HexLocation locatoin2=new HexLocation(this.location.getX()-1, this.location.getY()+1);
			Hex hextoupdate=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(location1);
			Hex hextoupdate2=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(locatoin2);
			if(hextoupdate==null||hextoupdate2==null)
			{
				Exception e = new Exception();
				e.printStackTrace();
				return false;
			}

				if(this.resourcetype!=HexType.WATER||hextoupdate.getResourcetype()!=(HexType.WATER)||hextoupdate2.getResourcetype()!=HexType.WATER) {
				if(!this.southwest.isHassettlement()&&!this.southwest.isHascity()) {
					if (!hextoupdate.getWest().isHascity() && !hextoupdate.getWest().isHassettlement()) {
						return true;
					}

				}
			}
			return false;
		}

		return true;
	}


	/**
	 * This function will need further implementation
	 */
	public boolean canBuildCityHere(VertexDirection mydirection)
	{
		//current player may not be correct.
		if(mydirection.equals(VertexDirection.SouthEast))
		{
			if(southeast.isHassettlement())
			{
				System.out.println("i check "+southeast.getSettlement().getOwner().getNumber()+ " with "+ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
				return (southeast.getSettlement().getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
			}
		}
		if(mydirection.equals(VertexDirection.SouthWest))
		{
			if(southwest.isHassettlement())
			{
				System.out.println("i check "+southwest.getSettlement().getOwner().getNumber()+ " with "+ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
				return (southwest.getSettlement().getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
			}
		}
		if(mydirection.equals(VertexDirection.West))
		{
			if(west.isHassettlement())
			{
				System.out.println("i check "+west.getSettlement().getOwner().getNumber()+ " with "+ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
				return (west.getSettlement().getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
			}
		}
		if(mydirection.equals(VertexDirection.East))
		{
			if(east.isHassettlement())
			{
				System.out.println("i check "+east.getSettlement().getOwner().getNumber()+ " with "+ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
				return (east.getSettlement().getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
			}
		}
		if(mydirection.equals(VertexDirection.NorthWest))
		{
			if(northwest.isHassettlement())
			{
				System.out.println("i check "+northwest.getSettlement().getOwner().getNumber()+ " with "+ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
				return (northwest.getSettlement().getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
			}
		}
		if(mydirection.equals(VertexDirection.NorthEast))
		{
			if(northeast.isHassettlement())
			{
				System.out.println("i check "+northeast.getSettlement().getOwner().getNumber()+ " with "+ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
				return (northeast.getSettlement().getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerID().getNumber());
			}
		}
		/*
		if (settlements.size() <= 0 || cities.size() >= 3)
		{
			return false;
		}
		*/
		return false;
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

	public void setResourcenumber(NumberToken resourcenumber)
	{
		this.resourcenumber=resourcenumber;
	}
	public int getResourcenumber()
	{
		return this.resourcenumber.getValue();
	}

	public void setPortType(PortType mytype)
	{
		if(myport!=null)
		{
			myport.setType(mytype);
		}
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
		//System.out.println("i add a settlement for hex at location "+this.getLocation().getX()+" "+this.getLocation().getY());
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

	public void setResourcetype(HexType resourcetype) {
		this.resourcetype = resourcetype;
	}
	
	public Port getPort(){
		return myport; 
	}
	
	public boolean hasSettlement(VertexDirection vertexDirection){
		for(Settlement settlement : settlements)
		{
			if(settlement.getVertexLocation().getDir().name().equals(vertexDirection.name())){
				return true;
			}
		}
		return false;
	}
	public boolean hasCity(VertexDirection vertexDirection){
		for(City city : cities)
		{
			if(city.getVertexLocation().getDir().name().equals(vertexDirection.name())){
				return true;
			}
		}
		return false;
	}
	
	public Settlement getSettlement(VertexDirection vertexDirection){
		for(Settlement settlement : settlements)
		{
			if(settlement.getVertexLocation().getDir().name().equals(vertexDirection.name())){
				return settlement;
			}
		}
		return null;
	}
	
	public City getCity(VertexDirection vertexDirection){
		for(City city : cities)
		{
			if(city.getVertexLocation().getDir().name().equals(vertexDirection.name())){
				return city;
			}
		}
		return null; 
	}
}
