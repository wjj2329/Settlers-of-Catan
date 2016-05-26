package client.maritime;

import client.State.State;
import client.model.ModelFacade;
import shared.definitions.*;
import client.base.*;
import shared.game.CatanGame;
import shared.game.map.Port;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the maritime trade controller
 * Alex is working on this currently.
 *
 * A few important points about trading here:
 * 1. The button will be disabled during the setup phase. It will be grayed out.
 * 	2. Need to call the canTradeWithBank method on the Player class.
 * 	3. 4:1 trades are implemented; 2:1 and 3:1 are not.
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer
{
	/**
	 * This variable represents the player who is making the trade with the bank.
	 * AKA the current player.
	 */
	private Player currentPlayer = ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer();
	private Player localPlayer = ModelFacade.facadeCurrentGame.getLocalPlayer();
	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType giveResource;
	private ResourceType getResource;
	private int tradeRatio = -1;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay)
	{
		super(tradeView);
		setTradeOverlay(tradeOverlay);
		tradeOverlay.setCancelEnabled(true); // this might be redundant
		ModelFacade.facadeCurrentGame.addObserver(this);
		tradeRatio = 4; // this is necessary; it was staying at -1 otherwise -_-
		//placePorts(); // let's see if this method call fixes it
	}
	
	public IMaritimeTradeView getTradeView()
	{
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade()
	{
		System.out.println("i start the trade");
		// Should the reset be called? I don't know
		//getTradeOverlay().reset();
		tradeRatio = 4;
		placePorts();
		// Do I need to call setGUI? It may be necessary
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade()
	{
		if (giveResource == null || getResource == null)
		{
			return;
		}
		System.out.println("i make the trade");
		// changing 4 to tradeRatio so it can be more dynamic
		ModelFacade.facadeCurrentGame.getServer().maritimeTrade("maritimeTrade",
				ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerIndex().getNumber(), tradeRatio,
				resourceTypeToString(giveResource), resourceTypeToString(getResource));
		getTradeOverlay().closeModal();
		setGUI();
	}

	@Override
	public void cancelTrade()
	{
		System.out.println("i cancel the trade");
		getTradeOverlay().closeModal();
		setGUI();
	}

	@Override
	public void setGetResource(ResourceType resource)
	{
		getResource = resource;
		System.out.println("This is the type "+resource.toString());
		getTradeOverlay().showGetOptions(new ResourceType[0]);
		getTradeOverlay().selectGetOption(getResource, 1);
		getTradeOverlay().setStateMessage("Trade!");
	}

	@Override
	public void setGiveResource(ResourceType resource)
	{
		giveResource = resource;
		System.out.println("This is the type "+resource.toString());
		getTradeOverlay().selectGiveOption(giveResource, tradeRatio); // what does this do eh? o.o
		// this may need to be replaced with showGetOptions for JUST that resource.
		ResourceType[] allResources = new ResourceType[5];
		allResources[0] = ResourceType.BRICK;
		allResources[1] = ResourceType.SHEEP;
		allResources[2] = ResourceType.WOOD;
		allResources[3] = ResourceType.ORE;
		allResources[4] = ResourceType.WHEAT;
		getTradeOverlay().showGetOptions(allResources);
		getTradeOverlay().setStateMessage("Choose what to get");
	}

	@Override
	public void unsetGetValue()
	{
		System.out.println("I unset the get value");
		getResource = null;
		//getTradeOverlay().setTradeEnabled(false);
		//getTradeOverlay().hideGetOptions();
		// Loading in a new ResourceType array - undoing the selection, essentially.
		ResourceType[] allResources = new ResourceType[5];
		allResources[0] = ResourceType.BRICK;
		allResources[1] = ResourceType.SHEEP;
		allResources[2] = ResourceType.WOOD;
		allResources[3] = ResourceType.ORE;
		allResources[4] = ResourceType.WHEAT;
		getTradeOverlay().showGetOptions(allResources);
		getTradeOverlay().setStateMessage("Choose what to get");
	}

	@Override
	public void unsetGiveValue()
	{
		System.out.println("I unset the give value");
		giveResource = null;
		//getTradeOverlay().setTradeEnabled(false);
		//getTradeOverlay().hideGiveOptions();
		setGUI();
	}

	/**
	 * Special function used for 3:1 and 2:1 ports.
	 * Port Types: WOOD, BRICK, SHEEP, WHEAT, ORE, THREE
	 * @param port: the port that is being used to influence
	 *               the trading ratio
	 *
	 *            Need to add the following algorithm: For all the ports,
	 *            if their type is one of the resourceTypes (i.e. not 3), then return
	 *            the ratio of said port (since we get that from the JSON).
     */
	private void setResourceAmount(Port port) // The problem with 2:1 still exists
	{
		System.out.println("Do I ever get here? Setting resource amount with trade ratio of..." + tradeRatio); // no
		switch (port.getType())
		{
			case THREE:
				tradeRatio = 3;
				break;
			case WOOD:
				tradeRatio = 2;
				// Also set the resourceType to be wood
				getResource = ResourceType.WOOD;
				break;
			case BRICK:
				tradeRatio = 2;
				getResource = ResourceType.BRICK;
				break;
			case SHEEP:
				tradeRatio = 2;
				getResource = ResourceType.SHEEP;
				break;
			case WHEAT:
				tradeRatio = 2;
				getResource = ResourceType.WHEAT;
				break;
			case ORE:
				tradeRatio = 2;
				getResource = ResourceType.ORE;
				break;
			default:
				break;
		}
	}

	/**
	 * Function to determine whether or not the settlement in question
	 * 		exists on the port in question.
	 * @param settlement: the settlement we want to see if on a port
	 * @param port: the port that the settlement needs to be on in order
	 *            to reduce the trade ratio from 4:1 to 3:1 or 2:1
     */
	private boolean isSettlementOnPort(Settlement settlement, Port port)
	{
		System.out.println("I am comparing the settlement location " + settlement.getHexLocation().getNeighborLoc
				(port.getDirection().getOppositeDirection()).toString()
			+ " with the hex location " + port.getLocation().toString());
		// The locations are never equal...Now they are! It's the second one that will have issues now...
		if (settlement.getHexLocation().getNeighborLoc(port.getDirection().getOppositeDirection()).equals(port.getLocation()))
		{
			System.out.println("Hex locations are the same for port and settlement's adjacent hex");
			// Then we need to check the edge direction / vertex location
			if (isVertexOnPortLocation(settlement.getVertexLocation(), port.getDirection().getOppositeDirection()))
			{
				System.out.println("CORRECT! The vertex is on the port location ");
				return true;
			}
		}
		System.out.println("Vertex is not on the port location :( ");
		return false;
	}

	private void placePorts()
	{
		ArrayList<Port> allPortsOnGameBoard = ModelFacade.facadeCurrentGame.getMymap().getPorts();
		// First: See if the settlement's location is equal to a port's location
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(
				ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getName()))
		{
			ArrayList<Settlement> settlementsForPlayer =
					ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getSettlements();
			for (int p = 0; p < settlementsForPlayer.size(); p++)
			{
				Settlement currentSettlement = settlementsForPlayer.get(p);
				for (int q = 0; q < allPortsOnGameBoard.size(); q++)
				{
					Port currentPort = allPortsOnGameBoard.get(q);
					if (currentPort.getType().equals(PortType.THREE) ||
							isSettlementOnPort(currentSettlement, currentPort))
					{
						setResourceAmount(currentPort);
					}
				}
			}
		}
	}

	/**
	 * Function to determine whether or not the vertex borders a port. If true,
	 * 		the player has a port there. Then, we will need to determine what type it
	 * 		is (3:1, or 2:1 with the various resource types).
	 *
	 * 	This function will only get called if the settlement and port exist
	 * 		on adjacent hex locations.
	 *
	 * @param vertex: the vertex on which the settlement lies
	 * @param edge: the edge on which the port is
     */
	private boolean isVertexOnPortLocation(VertexLocation vertex, EdgeDirection edge)
	{
		VertexDirection dir = vertex.getDir();
		if (edge == EdgeDirection.North)
		{
			if (dir == VertexDirection.NorthWest || dir == VertexDirection.NorthEast)
			{
				return true;
			}
		}
		else if (edge == EdgeDirection.NorthEast)
		{
			if (dir == VertexDirection.NorthEast || dir == VertexDirection.East)
			{
				return true;
			}
		}
		else if (edge == EdgeDirection.SouthEast)
		{
			if (dir == VertexDirection.East || dir == VertexDirection.SouthEast)
			{
				return true;
			}
		}
		else if (edge == EdgeDirection.South)
		{
			if (dir == VertexDirection.SouthEast || dir == VertexDirection.SouthWest)
			{
				return true;
			}
		}
		else if (edge == EdgeDirection.SouthWest)
		{
			if (dir == VertexDirection.SouthWest || dir == VertexDirection.West)
			{
				return true;
			}
		}
		else if (edge == EdgeDirection.NorthWest)
		{
			if (dir == VertexDirection.West || dir == VertexDirection.NorthWest)
			{
				return true;
			}
		}
		return false;
	}

	private String resourceTypeToString(ResourceType resType)
	{
		switch (resType)
		{
			case WOOD:
				return "wood";
			case SHEEP:
				return "sheep";
			case ORE:
				return "ore";
			case BRICK:
				return "brick";
			case WHEAT:
				return "wheat";
			default:
				return null;
		}
	}

	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}

	/**
	 * The ArrayList will be placed into the array.
	 * The list is of variable size; however, the array should end up
	 * 		being the same size as the arrayList.
	 */
	private void displayForCurrentTurn()
	{
		System.out.println("Displaying for current turn with trade ratio of " + tradeRatio);
		ArrayList<ResourceType> resourceTypes = new ArrayList<>();
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWood() >= tradeRatio)
		{
			resourceTypes.add(ResourceType.WOOD);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getOre() >= tradeRatio)
		{
			resourceTypes.add(ResourceType.ORE);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getBrick() >= tradeRatio)
		{
			resourceTypes.add(ResourceType.BRICK);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getSheep() >= tradeRatio)
		{
			resourceTypes.add(ResourceType.SHEEP);
		}
		if (ModelFacade.facadeCurrentGame.getLocalPlayer().getResources().getWheat() >= tradeRatio)
		{
			resourceTypes.add(ResourceType.WHEAT);
		}
		ResourceType[] whichResourcesToDisplay = new ResourceType[resourceTypes.size()];
		// I load the arrayList elements into the array because it's of VARIABLE SIZE
		for (int i = 0; i < resourceTypes.size(); i++)
		{
			whichResourcesToDisplay[i] = resourceTypes.get(i);
		}
		getTradeOverlay().showGiveOptions(whichResourcesToDisplay);
		if (whichResourcesToDisplay.length == 0)
		{
			getTradeOverlay().setStateMessage("You don't have enough resources.");
		}
		else
		{
			getTradeOverlay().setStateMessage("Choose what to give up");
			// I believe that this will only be called when you select something?
			// I *think* that you should be able to RECEIVE anything.
		}
	}

	/**
	 * Function enabling the GUI to show appropriate information and buttons
	 * 	to be selected.
	 */
	private void setGUI()
	{
		switch (ModelFacade.facadeCurrentGame.currentgame.getCurrentState())
		{
			case SetUpState:
				getTradeView().enableMaritimeTrade(false);
				break;
			case GamePlayingState:
				getTradeView().enableMaritimeTrade(true);
				if (ModelFacade.facadeCurrentGame.getLocalPlayer().getName().equals(
						ModelFacade.facadeCurrentGame.currentgame.getCurrentPlayer().getName()))
				{
					placePorts(); // maybe
					displayForCurrentTurn();
				}
				else
				{
					getTradeOverlay().showGiveOptions(new ResourceType[0]);
					getTradeOverlay().setStateMessage("not your turn");
				}
				break;
			default:
				getTradeView().enableMaritimeTrade(false);
				break;
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		//System.out.println("The game's current state is: " + ModelFacade.facadeCurrentGame.currentgame.getCurrentState().getState());
		setGUI();
	}
}

