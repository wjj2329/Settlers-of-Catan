package client.map;

import java.util.*;

import client.State.State;
import client.join.JoinGameController;
import client.login.LoginController;
import client.model.Model;
import client.model.ModelFacade;
import client.model.TurnStatus;
import client.roll.RollController;
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
		/*
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
		*/

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
		if(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(hexLoc).getResourcetype().equals(HexType.WATER))
		{
			return false;
		}
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
		ModelFacade.facadeCurrentGame.getServer().buildRoad("buildRoad",ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(),insert,edgeLoc);

	}

	public void placeSettlement(VertexLocation vertLoc) {
		//if current state is set up then I can do this.
		try {
			//System.out.println("my current state is this "+ModelFacade.facadeCurrentGame.currentgame.getCurrentState().toString());
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
		Index idOfCurrentPlayer = null;
		// I think this is returning their player index instead of their player ID.
		for (Player cur : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values())
		{
			if (cur.getPlayerIndex().equals(currentTurn))
			{
				idOfCurrentPlayer = cur.getPlayerID();
			}
		}
		if (idOfCurrentPlayer == null)
		{
			return;
		}
		// this might have fixed some huge things!!
		Player player = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(idOfCurrentPlayer);
		test=player.getPlayerIndex().getNumber();
		//System.out.println("this is my PLayer Index  to try to test with "+test);
		ModelFacade.facadeCurrentGame.getServer().buildSettlement("buildSettlement",test , true, vertLoc);


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
		int test;
		Index currentTurn = ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getCurrentTurn();
		Index idOfCurrentPlayer = null;
		// I think this is returning their player index instead of their player ID.
		for (Player cur : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values())
		{
			if (cur.getPlayerIndex().equals(currentTurn))
			{
				idOfCurrentPlayer = cur.getPlayerID();
			}
		}
		if (idOfCurrentPlayer == null)
		{
			return;
		}
		// this might have fixed some huge things!!
		Player player = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(idOfCurrentPlayer);
		test=player.getPlayerIndex().getNumber();
		getView().placeCity(vertLoc, currentPlayer.getColor());

		 ModelFacade.facadeCurrentGame.getServer().buildCity("buildCity",test,vertLoc);

	}
	HexLocation myhexloc;
	public void placeRobber(HexLocation hexLoc) {

		myhexloc=hexLoc;
		//getView().placeRobber(hexLoc);

		//getRobView().showModal();
		HashSet<RobPlayerInfo>victims=new HashSet<>();
		Hex myhex=ModelFacade.facadeCurrentGame.getMymap().getHexes().get(hexLoc);
		for(int i=0; i<myhex.getCities().size(); i++)
		{
			Player playerwhoownscity=null;
			for(Index playerid:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
			{
				if(myhex.getCities().get(i).getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(playerid).getPlayerID().getNumber())
				{
					playerwhoownscity=ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(playerid);
				}
			}
			if(playerwhoownscity==null)
			{
				return;
			}
			RobPlayerInfo myplayer=new RobPlayerInfo();
			myplayer.setColor(playerwhoownscity.getColor());
			myplayer.setPlayerIndex(playerwhoownscity.getPlayerIndex().getNumber());
			myplayer.setNumCards(playerwhoownscity.getResources().size());
			myplayer.setName(playerwhoownscity.getName());
			myplayer.setId(playerwhoownscity.getPlayerID().getNumber());
			victims.add(myplayer);

		}
		for(int i=0; i<myhex.getSettlementlist().size(); i++)
		{
			Player player=null;
			for(Index playerid:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
			{
				if(myhex.getSettlementlist().get(i).getOwner().getNumber()==ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(playerid).getPlayerID().getNumber())
				{
					player=ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(playerid);
				}
			}
			if(player==null)
			{
				return;
			}
			RobPlayerInfo myplayer=new RobPlayerInfo();
			myplayer.setColor(player.getColor());
			myplayer.setPlayerIndex(player.getPlayerIndex().getNumber());
			myplayer.setNumCards(player.getResources().size());
			myplayer.setName(player.getName());
			myplayer.setId(player.getPlayerID().getNumber());
				victims.add(myplayer);

		}
		RobPlayerInfo[] victimsArray = new RobPlayerInfo[victims.size()];
		victims.toArray(victimsArray);

		//ModelFacade.facadeCurrentGame.currentgame.getMymap().
		getView().placeRobber(hexLoc);
		getRobView().setPlayers(victimsArray);
		getRobView().showModal();
		if(victims.size()==0)
		{
			ModelFacade.facadeCurrentGame.getServer().robPlayer("robPlayer",ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(),myhexloc,ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber());
		}
	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {

		if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.SetUpState)
		{
			getView().startDrop(pieceType, ModelFacade.facadeCurrentGame.getLocalPlayer().getColor(), false);
		}
		else
		{
			getView().startDrop(pieceType, ModelFacade.facadeCurrentGame.getLocalPlayer().getColor(), true);
		}
	}

	public void cancelMove() {

	}

	public void playSoldierCard() {

	}

	public void playRoadBuildingCard() {

	}

	public void robPlayer(RobPlayerInfo victim)
	{
		//System.out.println("I ROB THE PLAYER NOW and tell the server I have done so");
		ModelFacade.facadeCurrentGame.getServer().robPlayer("robPlayer",ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getPlayerIndex().getNumber(),myhexloc,victim.getPlayerIndex());


	}

	//private static boolean hasdonefirstturn=false;

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
			System.out.println("HIS ID IS THIS "+ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID().getNumber());
			System.out.println("HIS INDEX IS "+ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber());
			System.out.println("HIS SETTLEMENTS SIZE IS "+ModelFacade.facadeCurrentGame.getLocalPlayer().getSettlements().size());
			if(current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName()))//if the current player is the local one
			{
				if (current.getSettlements().size() == 0
						&& ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getStatus() == TurnStatus.FIRSTROUND
						&& current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName()))
				//first part of turn one
				{
					startMove(PieceType.SETTLEMENT, true, true);
					//System.out.println("I COME INTO THE FIRST BUILD SETTLMENTS"+current.getName());
					//getView().startDrop(PieceType.SETTLEMENT, current.getColor(), false);
					//System.out.println("I place a settlement");
					return;
				}
				if(current.getSettlements().size()==1&&current.getRoadPieces().size()==0
						&& ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getStatus() == TurnStatus.FIRSTROUND
						&& current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName())) //second part of turn two
				{
					startMove(PieceType.ROAD, true, true);
					//getView().startDrop(PieceType.ROAD, current.getColor(), false);
					//System.out.println("i place a road");
					//System.out.println("my road size is now "+current.getRoadPieces().size()+"my settlements size is now this "+current.getSettlements().size());
					return;
				}
				//System.out.print("MY PLAYERS IN THE GAME IS THIS "+ModelFacade.facadeCurrentGame.currentgame.get)
				System.out.println("DUDE DUDE ALEX THIS IS THE SIZE MAN DUDE BRO "+current.getRoadPieces().size()+current.getName());
				System.out.println("DUDE DUDE WILLIAM THIS IS THE SIZE MAN DUDE BRO "+current.getSettlements().size()+current.getName());
				//System.out.println("MY CURRENT STATS IS THIS"+ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getStatus());
				if(current.getSettlements().size()==1&&current.getRoadPieces().size()/2==1
					&& ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getStatus() == TurnStatus.FIRSTROUND
						&& current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName()))
					/*&&!hasdonefirstturn*/ //ends first turn
				{
					//System.out.println("I COME HERE TO END THE TURN");

					//System.out.println("I have finished my turn");
					ModelFacade.facadeCurrentGame.getServer().finishTurn("finishTurn",current.getPlayerIndex().getNumber()).getResponse();

					//hasdonefirstturn=true;
					return;
				}
				if(current.getSettlements().size()==1&&current.getRoadPieces().size()/2==1
						&& ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getStatus() == TurnStatus.SECONDROUND
						&& current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName()))
					/*&&hasdonefirstturn*///starts part 1 of second set up turn
				{
					//System.out.println("I COME INTO THE SECOND BUILD SETTLMENTS "+current.getName());

					startMove(PieceType.SETTLEMENT, true, true); // updateFromJSON
					//getView().startDrop(PieceType.SETTLEMENT, current.getColor(), false);
					return;
				}
// it doesn't get to this one with Pete
				if(current.getSettlements().size()==2&&current.getRoadPieces().size()/2==1
						&& ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getStatus() == TurnStatus.SECONDROUND
						&& current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName()))//starts part 2 of second set up turn and then changes game playing state
				{
					//System.out.println("I come into the second build roads with " + current.getName());
					startMove(PieceType.ROAD, true, true); // updateFromJSON
					//getView().startDrop(PieceType.ROAD, current.getColor(), false);
					//ModelFacade.facadeCurrentGame.currentgame.setCurrentState(State.GamePlayingState);

					/*String serverresponse=ModelFacade.facadeCurrentGame.getServer().finishTurn("finishTurn",current.getPlayerIndex().getNumber()).getResponse();

					try {
						JSONObject response=new JSONObject(serverresponse);
						ModelFacade.facadeCurrentGame.updateFromJSON(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}*/
					return;
				}

				if (current.getSettlements().size() == 2 && current.getRoadPieces().size()/2 == 2
						&& ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getStatus() == TurnStatus.SECONDROUND
						&& current.getName().equals(ModelFacade.facadeCurrentGame.getLocalPlayer().getName()))
				{
					ModelFacade.facadeCurrentGame.getServer().finishTurn("finishTurn",current.getPlayerIndex().getNumber());

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
		if(ModelFacade.facadeCurrentGame.currentgame.getMymap()!=null)
		{
			Map<HexLocation, Hex> mymap = ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes();
			for (HexLocation rec : mymap.keySet())
			{
				HexType test = mymap.get(rec).getResourcetype();
				getView().addHex(rec, test);
				if (mymap.get(rec).getResourcenumber() != 0) {
					getView().addNumber(rec, mymap.get(rec).getResourcenumber());
				}
				if (mymap.get(rec).getPortType() != null)
				{
					//System.out.println("I ADD A NON NULL PORT");
					getView().addPort(converttoedgelocation(mymap.get(rec).getPortLocation(), rec), mymap.get(rec).getPortType());
				}
			}
		}
		if(RollController.robberrolled)
		{
			getView().startDrop(PieceType.ROBBER,CatanColor.BROWN,true);
			RollController.robberrolled=false;
		}
		getView().placeRobber(ModelFacade.facadeCurrentGame.currentgame.myrobber.getLocation());
		boolean everyoneHasTheRightNumber = true;
		for (Player p : ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values())
		{
			if (p.getSettlements().size() != 2 || p.getRoadPieces().size() != 2)
			{
				everyoneHasTheRightNumber = false;
			}
		}
		if (everyoneHasTheRightNumber && (ModelFacade.facadeCurrentGame.getModel().getTurntracker().getStatus()
				== TurnStatus.ROLLING)) // maybe it changes from secondRound and is instead something else. rolling?
		{
			//System.out.println("Rolling now instead of second round");
			ModelFacade.facadeCurrentGame.currentgame.setCurrentState(State.GamePlayingState);
		}

		if (ModelFacade.facadeCurrentGame.currentgame.getCurrentState() == State.SetUpState)
		{
			//System.out.println("I come here to update the views and stuff");
			doSetUpTurns();
		}

		//Index currentTurn = ModelFacade.facadeCurrentGame.currentgame.getModel().getTurntracker().getCurrentTurn();
		//Player player = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(currentTurn);

		//System.out.println("I REFRESH MY MAP CONTROLLER");
		//loads settlements on update
		//getView().placeSettlement(new VertexLocation(new HexLocation(-1,1),VertexDirection.East),CatanColor.BLUE);
		for(HexLocation loc:ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().keySet())
		{
			for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().size();i++)
			{
				//System.out.println("I MY MAP LOADER THINGY I LOAD A SETTLEMENT");
				Index correctonecolor=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().get(i).getOwner();
				CatanColor mycolor=CatanColor.PUCE;
				//System.out.println("MY settlements index for owner is" +ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().get(i).getOwner().getNumber());
				for(Index id:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
				{
					//System.out.println("MY INDEX IS THIS FOR PLAYER "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getName()+" is this "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getPlayerIndex().getNumber());
					if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getPlayerID().getNumber()==correctonecolor.getNumber())
					{
						mycolor = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getColor();
					}

				}
				getView().placeSettlement(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().get(i).getVertexLocation(), mycolor);
			}
		}
		//loads cities on update
		for(HexLocation loc:ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().keySet())
		{

			for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getCities().size();i++)
			{
				Index correctonecolor=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getCities().get(i).getOwner();
				CatanColor mycolor=CatanColor.PUCE;
				//System.out.println("MY settlements index for owner is" +ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().get(i).getOwner().getNumber());
				for(Index id:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
				{
					//System.out.println("MY INDEX IS THIS FOR PLAYER "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getName()+" is this "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getPlayerIndex().getNumber());
					if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getPlayerID().getNumber()==correctonecolor.getNumber())
					{
						mycolor = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getColor();
					}
				}
				getView().placeCity(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getCities().get(i).getVertexLocation(), mycolor);

			}
		}
		//loads roads on update
		for(HexLocation loc:ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().keySet())
		{
			//System.out.println("Does this hex have a road? size: " + ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc)
			//.getRoads().size()+"The hex location is is this"+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getLocation().getX()+" "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getLocation().getY());
			//System.out.println("PLUS ITS TYPE IS THIS  "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcetype().toString());
			//System.out.println("pluse its number token is this "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcenumber());
			/*for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().size();i++) {
			System.out.println(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).get)
			}*/

			for(int i=0; i<ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().size();i++)
			{
				Index correctonecolor=ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getPlayerWhoOwnsRoad();
				CatanColor mycolor=CatanColor.PUCE;
				//System.out.println("MY settlements index for owner is" +ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getSettlementlist().get(i).getOwner().getNumber());
				for(Index id:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
				{
					//System.out.println("MY INDEX IS THIS FOR PLAYER "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getName()+" is this "+ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getPlayerIndex().getNumber());
					if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getPlayerID().getNumber()==correctonecolor.getNumber())
					{
						mycolor = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(id).getColor();
					}
				}
				//System.out.println("THIS HEX HAS THE ROAD I PLACED number is this ");
				//System.out.println(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcenumber());
				//System.out.println(	" the type is this "+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getResourcetype().toString());
				if(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i)==null)
				{
					//System.out.println("THIS ROAD IS NULL FOOL");
				}
				if(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getLocation()==null)
				{
					//System.out.println("THIS ROAD LOCATION IS NULL FOOL");
				}
				//System.out.println(" this road happens to be located on this at postion"+ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getLocation().toString());
				getView().placeRoad(ModelFacade.facadeCurrentGame.currentgame.getMymap().getHexes().get(loc).getRoads().get(i).getLocation(), mycolor);

			}
		}

	}
}

