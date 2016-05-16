package client.map;

import java.util.*;

import shared.definitions.*;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.vertexobject.Settlement;
import shared.locations.*;
import client.base.*;
import client.data.*;

import static com.sun.deploy.trace.TraceLevel.values;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		
		initFromModel();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}

	private EdgeLocation converttoedgelocation(String location, HexLocation hexLocation)
	{
		switch(location) {
			case ("North"):
				return new EdgeLocation(hexLocation, EdgeDirection.North);
			case("NorthWest"):
				return new EdgeLocation(hexLocation, EdgeDirection.NorthWest);
			case("NorthEast"):
				return new EdgeLocation(hexLocation, EdgeDirection.NorthEast);
			case("SouthEast"):
				return new EdgeLocation(hexLocation, EdgeDirection.SouthEast);
			case("SouthWest"):
				return new EdgeLocation(hexLocation, EdgeDirection.SouthWest);
			case("South"):
				return new EdgeLocation(hexLocation, EdgeDirection.South);

		}

		return new EdgeLocation(hexLocation, EdgeDirection.North);
	}
	protected void initFromModel()
	{
		CatanGame.singleton=new CatanGame();
		CatanGame.singleton.setMymap(new CatanMap(1));
		Map<HexLocation, Hex>mymap=CatanGame.singleton.getMymap().getHexes();
		for(HexLocation rec:mymap.keySet())
		{
			HexType test=mymap.get(rec).getResourcetype();
			getView().addHex(rec, test);
			if(mymap.get(rec).getResourcenumber()!=0) {
				getView().addNumber(rec, mymap.get(rec).getResourcenumber());
			}
			if(mymap.get(rec).getPortType()!=null)
			{
				getView().addPort(converttoedgelocation(mymap.get(rec).getPortLocation(), rec),mymap.get(rec).getPortType());
			}
		}
		getView().placeRobber(new HexLocation(0, -2));

		//<temp>
		/*
		Random rand = new Random();

		for (int x = 0; x <= 3; ++x) {
			
			int maxY = 3 - x;			
			for (int y = -3; y <= maxY; ++y) {				
				int r = rand.nextInt(HexType.values().length);
				HexType hexType = HexType.values()[r];
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
						CatanColor.RED);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
						CatanColor.BLUE);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
						CatanColor.ORANGE);
				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
			}
			
			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					int r = rand.nextInt(HexType.values().length);
					HexType hexType = HexType.values()[r];
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
							CatanColor.RED);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
							CatanColor.BLUE);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
							CatanColor.ORANGE);
					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
				}
			}
		}
		
		PortType portType = PortType.BRICK;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);
		
		getView().placeRobber(new HexLocation(0, 0));
		
		getView().addNumber(new HexLocation(-2, 0), 2);
		getView().addNumber(new HexLocation(-2, 1), 3);
		getView().addNumber(new HexLocation(-2, 2), 4);
		getView().addNumber(new HexLocation(-1, 0), 5);
		getView().addNumber(new HexLocation(-1, 1), 6);
		getView().addNumber(new HexLocation(1, -1), 8);
		getView().addNumber(new HexLocation(1, 0), 9);
		getView().addNumber(new HexLocation(2, -2), 10);
		getView().addNumber(new HexLocation(2, -1), 11);
		getView().addNumber(new HexLocation(2, 0), 12);
		*/
		//</temp>
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		// testing. this will eventually be deleted except for return statement.
		CatanGame.singleton.getCurrentPlayer().getResources().setBrick(1);
		CatanGame.singleton.getCurrentPlayer().getResources().setWood(1);
		CatanGame.singleton.getCurrentPlayer().addToSettlements(new Settlement(new HexLocation(-2, 0),
				CatanGame.singleton.getMymap().getHexes().get(new HexLocation(-2, 0)).getNorthwest(),
				CatanGame.singleton.getCurrentPlayer().getPlayerID()));
		HexLocation hexLoc5 = new HexLocation(-2, -1);
		HexLocation hexLoc7 = new HexLocation(-2, 1);
		HexLocation hexLoc8 = new HexLocation(-2, 2);
		HexLocation hexLoc9 = new HexLocation(-2, 3);
		CatanGame.singleton.getCurrentPlayer().addToSettlements(new Settlement(hexLoc7,
				CatanGame.singleton.getMymap().getHexes().get(hexLoc7).getNortheast(),
				CatanGame.singleton.getCurrentPlayer().getPlayerID()));
		CatanGame.singleton.getCurrentPlayer().addToSettlements(new Settlement(hexLoc8,
				CatanGame.singleton.getMymap().getHexes().get(hexLoc8).getNorthwest(),
				CatanGame.singleton.getCurrentPlayer().getPlayerID()));

		CatanGame.singleton.getCurrentPlayer().addToSettlements(new Settlement(hexLoc9,
				CatanGame.singleton.getMymap().getHexes().get(hexLoc9).getNorthwest(),
				CatanGame.singleton.getCurrentPlayer().getPlayerID()));

		return (CatanGame.singleton.getCurrentPlayer().canBuildRoadPiece
				(CatanGame.singleton.getMymap().getHexes().get(edgeLoc.getHexLoc()), edgeLoc));
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {

		// need to implement this a bit more.
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}
	
}

