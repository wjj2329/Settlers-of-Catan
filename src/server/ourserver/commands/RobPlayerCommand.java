package server.ourserver.commands;

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
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
		Player criminal=null;
		Player victim=null;
		for(Player player: currentgame.getMyplayers().values())
		{
			if(player.getPlayerIndex().getNumber()==playerRobbing)
			{
				criminal=player;
			}
			if(player.getPlayerIndex().getNumber()==playerbeingrobbed)
			{
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
		Random myrandom=new Random();
		int index=myrandom.nextInt(possibiliestosteal.size());
		System.out.println(" my index to take is "+index+" the size of the array is "+possibiliestosteal.size());
		ResourceType tobestolen=possibiliestosteal.get(index);
		switch(tobestolen)
		{
			case BRICK:
			{
				victimslist.setBrick(victimslist.getBrick()-1);
				criminalslist.setBrick(criminalslist.getBrick()+1);
			}
			case WHEAT:
			{
				victimslist.setWheat(victimslist.getWheat()-1);
				criminalslist.setWheat(criminalslist.getWheat()+1);
			}
			case WOOD:
			{
				victimslist.setWood(victimslist.getWood()-1);
				criminalslist.setWood(criminalslist.getWood()+1);

			}
			case SHEEP:
			{
				victimslist.setSheep(victimslist.getSheep()-1);
				criminalslist.setSheep(criminalslist.getSheep()+1);
			}
			case ORE:
			{
				victimslist.setOre(victimslist.getOre()-1);
				criminalslist.setOre(criminalslist.getOre()+1);
			}
		}
		criminal.setResources(criminalslist);
		victim.setResources(victimslist);
		currentgame.myrobber.setLocation(location);


	}

}
