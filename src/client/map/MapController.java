package client.map;

import java.util.*;

import client.State.State;
import client.join.JoinGameController;
import client.login.LoginController;
import client.model.Model;
import client.model.ModelFacade;
import org.json.JSONException;
import org.json.JSONObject;
import server.proxies.IServer;
import server.proxies.ServerProxy;
import shared.definitions.*;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.player.Player;
import shared.locations.*;
import client.base.*;
import client.data.*;
/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer
{
	private IRobView robView;
	//public static boolean

	public MapController(IMapView view, IRobView robView)
	{
		super(view);
		setRobView(robView);
		initFromModel();
		ModelFacade.facadeCurrentGame.addObserver(this);
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
		ModelFacade.facadeCurrentGame.currentgame = new CatanGame();
		ModelFacade.facadeCurrentGame.currentgame.setMymap(new CatanMap(1));
		ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().setResources(new ResourceList(5,5,5,5,5));
		Map<HexLocation, Hex> mymap = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes();
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
		if (!ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().containsKey(edgeLoc.getHexLoc()))
		{
			return false;
		}
		return ModelFacade.facadeCurrentGame.currentgame.getCurrentState().canBuildRoadPiece
				(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(edgeLoc.getHexLoc()), edgeLoc);
	}

	public static boolean canplace=true;
	//have these things in States
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		if(ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.SetUpState)&&canplace) {
			try {
				//System.out.println(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()).getLocation().getY());
				return ModelFacade.facadeCurrentGame.currentgame.getCurrentState().canBuildSettlement(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()), vertLoc);
			} catch (Exception e) {
			}
			canplace=false;//can place must be reset on 2nd turn start and State MUST be updated on next turn.
		}
			else{

				try {
					//System.out.println(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()).getLocation().getY());
					return ModelFacade.facadeCurrentGame.currentgame.getCurrentState().canBuildSettlement(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()), vertLoc);
				} catch (Exception e) {

				}
			}

		return false;

	}

	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		try
		{
			return ModelFacade.facadeCurrentGame.currentgame.getCurrentState().canBuildCity(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()), vertLoc);

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
		if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.SetUpState ||
				ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.GamePlayingState)
		{
			//ModelFacade.facadeCurrentGame.currentgame.getCurrentState().buildRoad(ModelFacade.facadeCurrentGame.getMymap().getHexes()
			//	.get(edgeLoc.getHexLoc()), edgeLoc);
			getView().placeRoad(edgeLoc, ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getColor());
		}
		boolean insert=false;
		if(ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.SetUpState))
		{
			insert=true;
		}
		String mytest=ModelFacade.facadeCurrentGame.getServer().buildRoad("buildRoad",ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(),insert,edgeLoc).getResponse();
		try {
			JSONObject mine=new JSONObject(mytest);
			ModelFacade.facadeCurrentGame.updateFromJSON(mine);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		//if current state is set up then I can do this.
		try {
			System.out.println("my current state is this "+ModelFacade.facadeCurrentGame.currentgame.getCurrentState().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean insert=false;
		if(ModelFacade.facadeCurrentGame.currentgame.getCurrentState().equals(State.SetUpState))
		{
			insert=true;
		}
		int test;
		Index currentTurn = ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getCurrentTurn();
		Player player = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(currentTurn);
		test=player.getPlayerIndex().getNumber();
		for(Index myindex:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
		{
			System.out.println("my players ID " +ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(myindex).getName()+" is this "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(myindex).getPlayerID().getNumber());
		}
		for(Index myindex:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
		{
			System.out.println("my player"+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(myindex).getName()+" index is this "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(myindex).getPlayerIndex().getNumber());
		}
		System.out.println("this is my PLayer Index  to try to test with "+test);
		String mytest=ModelFacade.facadeCurrentGame.getServer().buildSettlement("buildSettlement",test , true, vertLoc).getResponse();
		System.out.println(mytest);
		try {
			JSONObject mine=new JSONObject(mytest);
			System.out.println("I come in to place a settlement and should hopefully have server respond with some JSON");
			ModelFacade.facadeCurrentGame.updateFromJSON(mine);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		Player currentPlayer=ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
		if(canPlaceCity(vertLoc))
		{
			try {
				currentPlayer.buildCity(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(vertLoc.getHexLoc()), vertLoc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		getView().placeCity(vertLoc, currentPlayer.getColor());

		String response= ModelFacade.facadeCurrentGame.getServer().buildCity("buildCity",0,vertLoc).getResponse();
		try {
			JSONObject mine=new JSONObject(response);
			ModelFacade.facadeCurrentGame.updateFromJSON(mine);
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

	private static boolean hasdonefirstturn=false;

	private void doSetUpTurns()
	{
		Player current = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
		if (current == null)
		{
			return;
		}
		if(current.getName()==null)
		{
			return;
		}

		if (ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size() == 4)//if size isn't four don't start
		{
			System.out.println("In the set up turns thing I compare "+ModelFacade.facadeCurrentGame.getLocalPlayer().getName()+" "+current.getName());
			if(current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName()))//if the current player is the local one
			{
				if (current.getSettlements().size() == 0) //first part of turn one
				{
					getView().startDrop(PieceType.SETTLEMENT, current.getColor(), false);
					System.out.println("I place a settlement");
					return;
				}
				// it never gets here
				if(current.getSettlements().size()==1&&current.getRoadPieces().size()==0) //second part of turn two
				{
					getView().startDrop(PieceType.ROAD, current.getColor(), false);
					System.out.println("i place a road");
					System.out.println("my road size is now "+current.getRoadPieces().size()+"my settlements size is now this "+current.getSettlements().size());
					return;
				}
				//System.out.print("MY PLAYERS IN THE GAME IS THIS "+ModelFacade.facadeCurrentGame.currentgame.get)
				System.out.println("DUDE DUDE ALEX THIS IS THE SIZE MAN DUDE BRO "+current.getRoadPieces().size()+current.getName());
				System.out.println("DUDE DUDE WILLIAM THIS IS THE SIZE MAN DUDE BRO "+current.getSettlements().size()+current.getName());
				if(current.getSettlements().size()==1&&current.getRoadPieces().size()==1&&!hasdonefirstturn) //ends first turn
				{
					System.out.println("I COME HERE TO END THE TURN");
					hasdonefirstturn=true;
					System.out.println("I have finished my turn");
					String serverresponse=ModelFacade.facadeCurrentGame.getServer().finishTurn("finishTurn",current.getPlayerIndex().getNumber()).getResponse();
					try {
						JSONObject response=new JSONObject(serverresponse);
						ModelFacade.facadeCurrentGame.updateFromJSON(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return;
				}
				if(current.getSettlements().size()==1&&current.getRoadPieces().size()==1&&hasdonefirstturn)//starts part 1 of second set up turn
				{
					getView().startDrop(PieceType.SETTLEMENT, current.getColor(), false);
					return;
				}

				if(current.getSettlements().size()==2&&current.getRoadPieces().size()==1)//starts part 2 of second set up turn and then changes game playing state
				{
					getView().startDrop(PieceType.ROAD, current.getColor(), false);
					ModelFacade.facadeCurrentGame.currentgame.setCurrentState(State.GamePlayingState);
					return;
				}

			}
		}
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
		if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.SetUpState)
		{
			System.out.println("I come here to update the views and stuff");
			doSetUpTurns();
		}
		Index currentTurn = ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getCurrentTurn();
		Player player = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(currentTurn);

		System.out.println("I REFRESH MY MAP CONTROLLER");
		//loads settlements on update
		//getView().placeSettlement(new VertexLocation(new HexLocation(-1,1),VertexDirection.East),CatanColor.BLUE);
		for(HexLocation loc:ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().keySet())
		{
			for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().size();i++)
			{
				System.out.println("I have settlements and this hex settlement size is this "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().size()+"at location "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getLocation().getX()+" "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getLocation().getY());
				getView().placeSettlement(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().get(i).getVertexLocation(), ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getColor());
			}
		}
		//loads cities on update
		for(HexLocation loc:ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().keySet())
		{
			for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getCities().size();i++)
			{
				getView().placeCity(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getCities().get(i).getVertexLocation(), ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getColor());

			}
		}
		//loads roads on update
		for(HexLocation loc:ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().keySet())
		{
			System.out.println("Does this hex have a road? size: " + ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc)
			.getRoads().size()+"The hex location is is this"+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getLocation().getX()+" "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getLocation().getY());
			System.out.println("PLUS ITS TYPE IS THIS  "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcetype().toString());
			System.out.println("pluse its number token is this "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcenumber());
			/*for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().size();i++) {
			System.out.println(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).get)
			}*/

			for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().size();i++)
			{
				System.out.println("THIS HEX HAS THE ROAD I PLACED number is this ");
				System.out.println(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcenumber());
				System.out.println(	" the type is this "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcetype().toString());
				if(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i)==null)
				{
					System.out.println("THIS ROAD IS NULL FOOL");
				}
				if(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getLocation()==null)
				{
					System.out.println("THIS ROAD LOCATION IS NULL FOOL");
				}
				System.out.println(" this road happens to be located on this at postion"+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getLocation().toString());
				getView().placeRoad(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getLocation(), ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getColor());

			}
		}

	}
}

