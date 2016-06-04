package server.ourserver.commands;

import server.ourserver.ServerFacade;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * The PlayMonopolyCommand class
 */
public class PlayMonopolyCommand implements ICommand {

	/**
	 * Executes the task:
	 * 	plays the monopoly card, other players give all of their resource cards 
	 * 	of the specified type. 
	 */
	@Override
	public Object execute()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public void playthemonopolycard( int playerindex,int gameid, String resource)
	{
		ServerFacade.getInstance().playMonument(playerindex, gameid);
		CatanGame currentgame=ServerFacade.getInstance().getGameByID(gameid);
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerindex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		ResourceList luckyguy=playertoupdate.getResources();
		for(Player player:currentgame.getMyplayers().values())
		{
			ResourceList unlucky=player.getResources();
			switch (resource)
			{
				case "BRICK":
				{
					luckyguy.setBrick(luckyguy.getBrick()+unlucky.getBrick());
					unlucky.setBrick(0);
					player.setResources(unlucky);
					playertoupdate.setResources(luckyguy);

					break;
				}
				case"WHEAT":
				{
					luckyguy.setWheat(luckyguy.getWheat()+unlucky.getWheat());
					unlucky.setWheat(0);
					player.setResources(unlucky);
					playertoupdate.setResources(luckyguy);


					break;
				}
				case "SHEEP":
				{
					luckyguy.setSheep(luckyguy.getSheep()+unlucky.getSheep());
					unlucky.setSheep(0);
					player.setResources(unlucky);
					playertoupdate.setResources(luckyguy);
					break;
				}
				case "WOOD":
				{
					luckyguy.setWood(luckyguy.getWood()+unlucky.getWood());
					unlucky.setWood(0);
					player.setResources(unlucky);
					playertoupdate.setResources(luckyguy);
					break;
				}
				case"ORE":
				{
					luckyguy.setOre(luckyguy.getOre()+unlucky.getOre());
					unlucky.setOre(0);
					player.setResources(unlucky);
					playertoupdate.setResources(luckyguy);
					break;
				}

			}
		}
	}

}
