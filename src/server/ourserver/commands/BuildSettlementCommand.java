package server.ourserver.commands;

import client.model.ModelFacade;
import client.model.TurnStatus;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.chat.GameHistoryLine;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Hex.Hex;
import shared.game.map.Index;
import shared.game.map.vertexobject.Settlement;
import shared.game.player.Player;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.util.ArrayList;

/**
 * The BuildSettlementCommand class
 */
public class BuildSettlementCommand implements ICommand ,java.io.Serializable{


	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return
				"," + TextDBGameManagerDAO.commandNumber+":"+"{" +
				"\"type\":\"BuildSettlementCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"x\":"+"\""+location.getX()+"\""+", \"y\":"+"\""+location.getY()+"\""+
				", \"dir\":"+"\""+vertex.getDir() +"\""+
				", \"free\":" + free +
				", \"gameid\":" + gameid +
				"}}";
	}

	/**
	 * Executes the task
	 * 	player builds a settlement at the vertex location 
	 */
	int playerIndex;
	HexLocation location;
	VertexLocation vertex;
	boolean free;
	int gameid;
	public BuildSettlementCommand(int playerIndex, HexLocation location, VertexLocation vertex, boolean free, int gameid)
	{
		this.playerIndex=playerIndex;
		this.location=location;
		this.vertex=vertex;
		this.free=free;
		this.gameid=gameid;
	}

	@Override
	public Object execute()
	{

//System.out.println("I BUILD A SETTLEMENT FOR THIS GAME");
		//System.out.println("THIS IS THE GAME ID FOR THE GAME I NEED TO UPDATE "+gameid);
		CatanGame currentgame=ServerFacade.getInstance().getGameByID(gameid);
		//System.out.println("this is the pointer to the game object" +currentgame);
		Index myindex = null;
		for (Player p : currentgame.getMyplayers().values())
		{
			if (p.getPlayerIndex().getNumber()==(new Index(playerIndex).getNumber()))
			{
				myindex = p.getPlayerID();
			}
		}
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		//System.out.println("THIS IS NOW THE NEW MODEL VERSION "+currentgame.getModel().getVersion());
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerIndex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		//System.out.println("I UPDATE THIS PLAYER "+playertoupdate.getName());
		if(free)
		{
			//System.out.println(" THIS IS BUILT FOR FREE!!!!");
		}
		else
		{
			//System.out.println(" THIS IS NOT BUILT FOR FREE!!!!");
		}
		if(!free) {
			ResourceList newlist = playertoupdate.getResources();
			newlist.setBrick(newlist.getBrick() - 1);
			newlist.setSheep(newlist.getSheep() - 1);
			newlist.setWheat(newlist.getWheat() - 1);
			newlist.setWood(newlist.getWood() - 1);
			playertoupdate.setResources(newlist);//not sure if this is necessary or not.

			//updates bank
			ResourceList mybankslist=currentgame.mybank.getCardslist();
			mybankslist.setBrick(mybankslist.getBrick()+1);
			mybankslist.setWood(mybankslist.getWood()+1);
			mybankslist.setWheat(mybankslist.getWheat()+1);
			mybankslist.setSheep(mybankslist.getSheep()+1);
			currentgame.mybank.setResourceCardslist(mybankslist);
		}
		vertex.setHassettlement(true);
		Settlement settle1 = new Settlement(location, vertex, playertoupdate.getPlayerIndex());
		Hex h = currentgame.getMymap().getHexes().get(location);
		//System.out.println("I BUILD A SETTLEMENT AT VERTEX LOCATION "+vertex.toString());
		//System.out.println("THE HEX I HAPPEN TO UPDATE IS A "+h.getResourcetype().toString()+" HIS Number token is "+h.getResourcenumber()+" his location is "+h.getLocation().toString());
		if(h==null)
		{
			//System.out.println("FAILURE");
		}
		try {
			//System.out.println(" I COME HERE TO TRY TO DO THIS");
			h.buildSettlement(vertex, playertoupdate.getPlayerIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("THE HEX HAS A SETTLEMENT with this" +h.getSettlementlist().get(0).getVertexLocation().getDir().toString());
		currentgame.getMymap().getSettlements().add(settle1);
		//System.out.println("I GIVE THE  SETTLEMENT THE INDEX WHICH IS THIS "+playertoupdate.getPlayerID().getNumber());
		settle1.setOwner(playertoupdate.getPlayerIndex());
		vertex.setSettlement(settle1);
		playertoupdate.addToSettlements(settle1);
		//System.out.println("I NOW UPDATE THIS player he now has "+playertoupdate.getSettlements().size());
		playertoupdate.setNumSettlementsRemaining(playertoupdate.getNumSettlementsRemaining()-1);
		playertoupdate.setNumVictoryPoints(playertoupdate.getNumVictoryPoints()+1);
		//System.out.println(" The Settlement I happen to have created has location "+settle1.getVertexLocation().toString());
		//System.out.println(" The Settlement I happen to have created has location after using getter "+settle1.getVertexLocation().getDir().toString());
		currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " builds a Settlement",playertoupdate.getName()));
		if(currentgame.getModel().getTurntracker().getStatus().equals(TurnStatus.SECONDROUND))
		{
			ArrayList<HexType>resourcestogive=new ArrayList<>();
			resourcestogive.add(h.getResourcetype());

			if(vertex.getDir().equals(VertexDirection.East))
			{
				HexLocation location1=new HexLocation(location.getX()+1, location.getY()-1);
				HexLocation locatoin2=new HexLocation(location.getX()+1, location.getY());
				Hex hextoupdate= currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=currentgame.getMymap().getHexes().get(locatoin2);
				resourcestogive.add(hextoupdate.getResourcetype());
				resourcestogive.add(hextoupdate2.getResourcetype());
			}
			else
			if(vertex.getDir().equals(VertexDirection.West))
			{
				HexLocation location1=new HexLocation(location.getX()-1, location.getY());
				HexLocation locatoin2=new HexLocation(location.getX()-1, location.getY()+1);
				Hex hextoupdate=currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=currentgame.getMymap().getHexes().get(locatoin2);
				resourcestogive.add(hextoupdate.getResourcetype());
				resourcestogive.add(hextoupdate2.getResourcetype());
			}
			else
			if(vertex.getDir().equals(VertexDirection.NorthEast))
			{
				HexLocation location1=new HexLocation(location.getX(), location.getY()-1);
				HexLocation locatoin2=new HexLocation(location.getX()+1, location.getY()-1);
				Hex hextoupdate=currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=currentgame.getMymap().getHexes().get(locatoin2);
				resourcestogive.add(hextoupdate.getResourcetype());
				resourcestogive.add(hextoupdate2.getResourcetype());
			}
			else
			if(vertex.getDir().equals(VertexDirection.NorthWest))
			{
				HexLocation location1=new HexLocation(location.getX()-1, location.getY());
				HexLocation locatoin2=new HexLocation(location.getX(), location.getY()-1);
				Hex hextoupdate=currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=currentgame.getMymap().getHexes().get(locatoin2);
				resourcestogive.add(hextoupdate.getResourcetype());
				resourcestogive.add(hextoupdate2.getResourcetype());
			}
			else
			if(vertex.getDir().equals(VertexDirection.SouthWest))
			{
				HexLocation location1=new HexLocation(location.getX(), location.getY()+1);
				HexLocation locatoin2=new HexLocation(location.getX()-1,location.getY()+1);
				Hex hextoupdate=currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2=currentgame.getMymap().getHexes().get(locatoin2);
				resourcestogive.add(hextoupdate.getResourcetype());
				resourcestogive.add(hextoupdate2.getResourcetype());
			}
			else
			if(vertex.getDir().equals(VertexDirection.SouthEast))
			{
				HexLocation location1 = new HexLocation(location.getX() + 1, location.getY());
				HexLocation locatoin2 = new HexLocation(location.getX(), location.getY() + 1);
				Hex hextoupdate = currentgame.getMymap().getHexes().get(location1);
				Hex hextoupdate2 = currentgame.getMymap().getHexes().get(locatoin2);
				resourcestogive.add(hextoupdate.getResourcetype());
				resourcestogive.add(hextoupdate2.getResourcetype());
			}
			ResourceList updating=playertoupdate.getResources();
			for(int i=0; i<resourcestogive.size(); i++)
			{
				switch(resourcestogive.get(i))
				{
					case BRICK:
					{
						updating.setBrick(updating.getBrick()+1);
						break;
					}
					case WATER:
					{
						break;
					}
					case WHEAT:
					{
						updating.setWheat(updating.getWheat()+1);
						break;
					}
					case ORE:
					{
						updating.setOre(updating.getOre()+1);
						break;
					}
					case SHEEP:
					{
						updating.setSheep(updating.getSheep()+1);
						break;
					}
					case WOOD:
					{
						updating.setWood(updating.getWood()+1);
						break;
					}
					case DESERT:
					{
						break;
					}
				}
			}
			playertoupdate.setResources(updating);
		}
		return null;
	}

}
