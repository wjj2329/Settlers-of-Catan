package client.map;

import java.util.*;

import client.State.State;
import client.model.ModelFacade;
import javafx.collections.SetChangeListener;
import org.json.JSONException;
import org.json.JSONObject;
import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.definitions.*;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.player.Player;
import shared.locations.*;
import client.base.*;
import client.data.*;
/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer
{
	private IServer server = new ServerProxy();
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView)
	{
		super(view);
		setRobView(robView);
		initFromModel();
		ModelFacade.facace_currentgame.addObserver(this);
		server.loginUser("Sam", "sam");
		server.JoinGame(0, "orange");
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
	protected void initFromModel() {
		ModelFacade.facace_currentgame.currentgame = new CatanGame();
		ModelFacade.facace_currentgame.currentgame.setMymap(new CatanMap(1));
		ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().setResources(new ResourceList(5,5,5,5,5));
		Map<HexLocation, Hex> mymap = ModelFacade.facace_currentgame.currentgame.getMymap().getHexes();
		for (HexLocation rec : mymap.keySet()) {
			HexType test = mymap.get(rec).getResourcetype();
			getView().addHex(rec, test);
			if (mymap.get(rec).getResourcenumber() != 0) {
				getView().addNumber(rec, mymap.get(rec).getResourcenumber());
			}
			if (mymap.get(rec).getPortType() != null) {
				getView().addPort(converttoedgelocation(mymap.get(rec).getPortLocation(), rec), mymap.get(rec).getPortType());
			}
		}
		getView().placeRobber(new HexLocation(0, -2));

	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		if (!ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().containsKey(edgeLoc.getHexLoc()))
		{
			return false;
		}
		return ModelFacade.facace_currentgame.currentgame.getCurrentState().canBuildRoadPiece
				(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(edgeLoc.getHexLoc()), edgeLoc);
	}



	//have these things in States
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		try
		{
			//System.out.println(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()).getLocation().getY());
			return ModelFacade.facace_currentgame.currentgame.getCurrentState().canBuildSettlement(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()), vertLoc);
		}
		catch (Exception e)
		{
			
		}
		return false;

	}

	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		try
		{
			return ModelFacade.facace_currentgame.currentgame.getCurrentState().canBuildCity(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()), vertLoc);

		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc)
	{
		if (ModelFacade.facace_currentgame.currentgame.getCurrentState() == State.SetUpState ||
				ModelFacade.facace_currentgame.currentgame.getCurrentState() == State.GamePlayingState)
		{
			System.out.println("I am in here");
			System.out.println("current player's color is: " + ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());
			ModelFacade.facace_currentgame.currentgame.getCurrentState().buildRoad(ModelFacade.facace_currentgame.getMymap().getHexes()
				.get(edgeLoc.getHexLoc()), edgeLoc);
			getView().placeRoad(edgeLoc, ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());
		}
		boolean insert=false;
		if(ModelFacade.facace_currentgame.currentgame.getCurrentState().equals(State.SetUpState))
		{
			insert=true;
		}
		String mytest=server.buildRoad("buildRoad",ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(),insert,edgeLoc).getResponse();
		try {
			JSONObject mine=new JSONObject(mytest);
			ModelFacade.facace_currentgame.updateFromJSON(mine);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		try {
			System.out.println("my current state is this "+ModelFacade.facace_currentgame.currentgame.getCurrentState().toString());
			ModelFacade.facace_currentgame.currentgame.getCurrentState().buildSettlement(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()),vertLoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getView().placeSettlement(vertLoc, ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());
		boolean insert=false;
		if(ModelFacade.facace_currentgame.currentgame.getCurrentState().equals(State.SetUpState))
		{
			insert=true;
		}
		String mytest=server.buildSettlement("buildSettlement", 0, true, vertLoc).getResponse();
		try {
			JSONObject mine=new JSONObject(mytest);
			ModelFacade.facace_currentgame.updateFromJSON(mine);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		Player currentPlayer=ModelFacade.facace_currentgame.currentgame.getCurrentPlayer();
		if(canPlaceCity(vertLoc))
		{
			try {
				currentPlayer.buildCity(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()), vertLoc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		getView().placeCity(vertLoc, currentPlayer.getColor());

		String response= server.buildCity("buildCity",0,vertLoc).getResponse();
		try {
			JSONObject mine=new JSONObject(response);
			ModelFacade.facace_currentgame.updateFromJSON(mine);
		} catch (JSONException e) {
			e.printStackTrace();
		}
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

	/**
	 * This function is important.
	 * Essential for updating GUI.
	 * @param o: modelFacade.
	 * @param arg: I *think* this will be our CatanGame. According to Java's
	 *           Observable's interface, arg is an argument passed to the
	 *           notifyObservers method.
     */
	@Override
	public void update(Observable o, Object arg)
	{
		//loads settlements on update
		for(HexLocation loc:ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().keySet())
		{
			for(int i=0; i<ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().size();i++)
			{
				getView().placeSettlement(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().get(i).getVertexLocation(), ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());
			}
		}
		//loads cities on update
		for(HexLocation loc:ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().keySet())
		{
			for(int i=0; i<ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(loc).getCities().size();i++)
			{
				getView().placeCity(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(loc).getCities().get(i).getVertexLocation(), ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());

			}
		}
		//loads roads on update
		for(HexLocation loc:ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().keySet())
		{
			for(int i=0; i<ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(loc).getRoads().size();i++)
			{
				getView().placeRoad(ModelFacade.facace_currentgame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getLocation(), ModelFacade.facace_currentgame.currentgame.getCurrentPlayer().getColor());

			}
		}
	}
}

