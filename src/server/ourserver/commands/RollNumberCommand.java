package server.ourserver.commands;



import org.json.JSONException;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableInterceptor.DISCARDING;
import com.sun.corba.se.spi.activation.Server;

import client.main.Catan;
//import com.sun.medialib.mlib.mediaLibImageJPanel;
import client.model.TurnStatus;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.definitions.HexType;
import shared.game.CatanGame;
import shared.game.map.CatanMap;
import shared.game.map.Hex.Hex;
import shared.game.map.vertexobject.City;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.io.FileNotFoundException;


/**
 * The RollNumberCommand class
 */

public class RollNumberCommand implements ICommand {

	int rollNumber;
	int gameid;

	public RollNumberCommand(int rollNumber, int gameid)
	{
		this.rollNumber = rollNumber;
		this.gameid = gameid; 
		//System.out.println("THIS IS THE ROLLED NUMBER " + rollNumber);
	}



	/**
	* Executes the task:
	* The model's status is Rolling and is the player's turn and then changes the 
	* model's status to 'Discarding' or 'Robbing' or 'Playing'
	*/
	@Override
	public Object execute() throws FileNotFoundException, JSONException
	{
		CatanGame game = ServerFacade.getInstance().getGameByID(gameid);		
		CatanMap map = game.getMymap();
		
		for(HexLocation hexLocation : map.getHexes().keySet())
		{	
			Hex estamera = map.getHexes().get(hexLocation);
			if(map.getHexes().get(hexLocation).getResourcenumber() == rollNumber)			
			{
				
				checkNorthWestVertex(estamera, map, hexLocation, game);				
				//System.out.println("CHECKED NORTHWEST");
				
				checkNorthEastVertex(estamera, map, hexLocation, game);	
				//System.out.println("CHECKED NORTHEAST");
				
				checkWestVertex(estamera, map, hexLocation, game);
				//System.out.println("CHECKED WEST");

				checkEastVertex(estamera, map, hexLocation, game);
				//System.out.println("CHECKED East");
				
				checkSouthWestVertext(estamera, map, hexLocation, game);
				//System.out.println("CHECKED SouthWest");

				checkSouthEastVertext(estamera, map, hexLocation, game);				
				//System.out.println("CHECKED SouthEast");

			
			}				
		}
		if (rollNumber == 7) 
		{			
			//System.out.println("ROLLED A 7 YO.");			
			game.getModel().getTurntracker().setStatus(TurnStatus.DISCARDING);
		}
		
		else 
		{
			game.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);
		}
		game.getModel().setVersion(game.getModel().getVersion()+1);
		return null;
	}
	
	
	public void checkNorthWestVertex(Hex estamera, CatanMap map, HexLocation hexLocation, CatanGame game)
	{
		if(estamera.hasCity(VertexDirection.NorthWest))
		{
			incrementPlayerCrap(estamera.getCity(VertexDirection.NorthWest).getOwner().getNumber(), estamera.getResourcetype(), 2, game);
		}
		else if(estamera.hasSettlement(VertexDirection.NorthWest))
		{	
			incrementPlayerCrap(estamera.getSettlement(VertexDirection.NorthWest).getOwner().getNumber(), estamera.getResourcetype(), 1, game);
		}			
		else
		{
			if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).hasCity(VertexDirection.SouthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).getCity(VertexDirection.SouthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).hasCity(VertexDirection.East))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).getCity(VertexDirection.East).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).hasSettlement(VertexDirection.SouthWest))
			{	
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).getSettlement(VertexDirection.SouthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).hasSettlement(VertexDirection.East))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).getSettlement(VertexDirection.East).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
		}
	}
	
	public void checkNorthEastVertex(Hex estamera, CatanMap map, HexLocation hexLocation, CatanGame game){
		if(estamera.hasCity(VertexDirection.NorthEast))
		{
			incrementPlayerCrap(estamera.getCity(VertexDirection.NorthEast).getOwner().getNumber(), estamera.getResourcetype(), 2, game);
		}
		else if(estamera.hasSettlement(VertexDirection.NorthEast))
		{
			incrementPlayerCrap(estamera.getSettlement(VertexDirection.NorthEast).getOwner().getNumber(), estamera.getResourcetype(), 1, game);
		}
		else
		{
			if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).hasCity(VertexDirection.SouthEast))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).getCity(VertexDirection.SouthEast).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).hasCity(VertexDirection.West))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).getCity(VertexDirection.West).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).hasSettlement(VertexDirection.SouthEast))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.North)).getSettlement(VertexDirection.SouthEast).getOwner().getNumber(), 
						estamera.getResourcetype(), 1, game);
			}
			
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).hasSettlement(VertexDirection.West))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).getSettlement(VertexDirection.West).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
		}
	}
	
	public void checkWestVertex(Hex estamera, CatanMap map, HexLocation hexLocation, CatanGame game){
		if(estamera.hasCity(VertexDirection.West))
		{
			incrementPlayerCrap(estamera.getCity(VertexDirection.West).getOwner().getNumber(), estamera.getResourcetype(), 2, game);
			//System.out.println("GOT HERE ELSE IF");
		}
		else if(estamera.hasSettlement(VertexDirection.West))
		{
			incrementPlayerCrap(estamera.getSettlement(VertexDirection.West).getOwner().getNumber(), estamera.getResourcetype(), 1, game);
			//System.out.println("GOT HERE");
		}
		else
		{
			if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).hasCity(VertexDirection.SouthEast))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).getCity(VertexDirection.SouthEast).getOwner().getNumber(), 
						estamera.getResourcetype(), 2, game);
			}
			
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).hasCity(VertexDirection.NorthEast))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).getCity(VertexDirection.NorthEast).getOwner().getNumber(), 
						estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).hasSettlement(VertexDirection.SouthEast))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)).getSettlement(VertexDirection.SouthEast).getOwner().getNumber(), 
						estamera.getResourcetype(), 1, game);
			}
			
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).hasSettlement(VertexDirection.NorthEast))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).getSettlement(VertexDirection.NorthEast).getOwner().getNumber(), 
						estamera.getResourcetype(), 1, game);
			}
		}
	}

	public void checkEastVertex(Hex estamera, CatanMap map, HexLocation hexLocation, CatanGame game){
		if(estamera.hasCity(VertexDirection.East))
		{
			incrementPlayerCrap(estamera.getCity(VertexDirection.East).getOwner().getNumber(), estamera.getResourcetype(), 2, game);
		}
		else if(estamera.hasSettlement(VertexDirection.East))
		{
			incrementPlayerCrap(estamera.getSettlement(VertexDirection.East).getOwner().getNumber(), estamera.getResourcetype(), 1, game);
		}
		else
		{
			if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).hasCity(VertexDirection.NorthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).getCity(VertexDirection.NorthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).hasCity(VertexDirection.SouthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).getCity(VertexDirection.SouthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).hasSettlement(VertexDirection.NorthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).getSettlement(VertexDirection.NorthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).hasSettlement(VertexDirection.SouthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)).getSettlement(VertexDirection.SouthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
		}
	}

	public void checkSouthWestVertext(Hex estamera, CatanMap map, HexLocation hexLocation, CatanGame game){
		if(estamera.hasCity(VertexDirection.SouthWest))
		{
			incrementPlayerCrap(estamera.getCity(VertexDirection.SouthWest).getOwner().getNumber(), estamera.getResourcetype(), 2, game);
		}
		else if(estamera.hasSettlement(VertexDirection.SouthWest))
		{
			incrementPlayerCrap(estamera.getSettlement(VertexDirection.SouthWest).getOwner().getNumber(), estamera.getResourcetype(), 1, game);
		}
		else
		{
			if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).hasCity(VertexDirection.East))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).getCity(VertexDirection.East).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).hasCity(VertexDirection.NorthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).getCity(VertexDirection.NorthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).hasSettlement(VertexDirection.East))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthWest)).getSettlement(VertexDirection.East).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).hasSettlement(VertexDirection.NorthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).getSettlement(VertexDirection.NorthWest).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}		
		}
	}
	
	public void checkSouthEastVertext(Hex estamera, CatanMap map, HexLocation hexLocation, CatanGame game){
		if(estamera.hasCity(VertexDirection.SouthEast))
		{
			incrementPlayerCrap(estamera.getCity(VertexDirection.SouthEast).getOwner().getNumber(), estamera.getResourcetype(), 2, game);
		}
		
		else if(estamera.hasSettlement(VertexDirection.SouthEast))
		{
			incrementPlayerCrap(estamera.getSettlement(VertexDirection.SouthEast).getOwner().getNumber(), estamera.getResourcetype(), 1, game);
		}
		else
		{
			if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).hasCity(VertexDirection.West))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).getCity(VertexDirection.West).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).hasCity(VertexDirection.NorthWest))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).getCity(VertexDirection.NorthEast).getOwner().getNumber(), 
				estamera.getResourcetype(), 2, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).hasSettlement(VertexDirection.West))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.SouthEast)).getSettlement(VertexDirection.West).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
			else if(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).hasSettlement(VertexDirection.NorthEast))
			{
				incrementPlayerCrap(map.getHexes().get(hexLocation.getNeighborLoc(EdgeDirection.South)).getSettlement(VertexDirection.NorthEast).getOwner().getNumber(), 
				estamera.getResourcetype(), 1, game);
			}
	
		}
	}
	
	
	public void incrementPlayerCrap(int index, HexType hexType, int times, CatanGame game)
	{	
		for(Player player : game.getMyplayers().values())		
		{		
			if(player.getPlayerIndex().getNumber() == index)	
			{
				//System.out.println(player.getName() + " GETS A " + hexType.name());		
				if(game.mybank.getResourceCard(hexType))
				{
					player.getResources().incrementBasedOnHexType(hexType);
				}
				if(times == 2 && game.mybank.getResourceCard(hexType))
				{
					//System.out.println(player.getName() + " GETS ANOTHER " + hexType.name());	
					player.getResources().incrementBasedOnHexType(hexType);
				}
				return;
	
			}
		}	
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," +"\""+ TextDBGameManagerDAO.commandNumber+"\""+":"+"{" +
				"\"type\":\"RollNumberCommand\"" +
				", \"rollNumber\":" + rollNumber +
				", \"gameid\":" + gameid +
				"}}";
	}
}
