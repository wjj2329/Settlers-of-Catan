package server.ourserver.commands;

import client.model.TurnStatus;
import server.ourserver.ServerFacade;
import shared.definitions.ResourceType;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.player.Player;
import shared.locations.HexLocation;

import java.util.ArrayList;
import java.util.Random;

/**
 * The RobPlayerCommand class
 */
public class RobPlayerCommand implements ICommand {

	/**
	 * Executes the task:
	 * 	player robs another player and is randomly given one of their resource cards 
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub

		return null;
	}
	public void robplayerofresources(HexLocation location, int playerRobbing, int playerbeingrobbed ,int gameid)
	{
		System.out.println("I ROB! in that player with index "+playerRobbing+" Robs player with index "+playerbeingrobbed);
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		Player criminal=null;
		Player victim=null;
		for(Player player: currentgame.getMyplayers().values())
		{
			if(player.getPlayerIndex().getNumber()==playerRobbing)
			{
				System.out.println("I set the criminal");
				criminal=player;
			}
			if(player.getPlayerIndex().getNumber()==playerbeingrobbed)
			{
				System.out.println("I set the victim");
				victim=player;
			}
		}
		ResourceList victimslist=victim.getResources();
		ResourceList criminalslist=criminal.getResources();
		ArrayList<ResourceType>possibiliestosteal=new ArrayList<>();
		if(victimslist.getOre()>0)
		{
			possibiliestosteal.add(ResourceType.ORE);
		}
		if(victimslist.getSheep()>0)
		{
			possibiliestosteal.add(ResourceType.SHEEP);
		}
		if(victimslist.getBrick()>0)
		{
			possibiliestosteal.add(ResourceType.BRICK);
		}
		if(victimslist.getWheat()>0)
		{
			possibiliestosteal.add(ResourceType.WHEAT);
		}
		if(victimslist.getWood()>0)
		{
			possibiliestosteal.add(ResourceType.WOOD);
		}
		if(possibiliestosteal.size()==0)
		{
			currentgame.myrobber.setLocation(location);
			currentgame.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);
			return;
		}
		Random myrandom=new Random();
		int index=myrandom.nextInt(possibiliestosteal.size());
		System.out.println(" my index to take is "+index+" the size of the array is "+possibiliestosteal.size());
		System.out.println("I Steal this");

		ResourceType tobestolen=possibiliestosteal.get(index);
		switch(tobestolen)
		{
			case BRICK:
			{
				victimslist.setBrick(victimslist.getBrick()-1);
				criminalslist.setBrick(criminalslist.getBrick()+1);
				break;
			}
			case WHEAT:
			{
				victimslist.setWheat(victimslist.getWheat()-1);
				criminalslist.setWheat(criminalslist.getWheat()+1);
				break;
			}
			case WOOD:
			{
				victimslist.setWood(victimslist.getWood()-1);
				criminalslist.setWood(criminalslist.getWood()+1);
				break;

			}
			case SHEEP:
			{
				victimslist.setSheep(victimslist.getSheep()-1);
				criminalslist.setSheep(criminalslist.getSheep()+1);
				break;
			}
			case ORE:
			{
				victimslist.setOre(victimslist.getOre()-1);
				criminalslist.setOre(criminalslist.getOre()+1);
				break;
			}
		}
		criminal.setResources(criminalslist);
		victim.setResources(victimslist);
		System.out.println("I rob a player at"+location.toString());
		currentgame.myrobber.setLocation(location);
		currentgame.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);

	}

}
