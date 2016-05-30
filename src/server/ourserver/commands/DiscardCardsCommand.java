package server.ourserver.commands;

import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;

/**
 * The DiscardCardsCommand
 */
public class DiscardCardsCommand implements ICommand {

	/**
	 * Executes the task:
	 * 	player to discard cards if they have 7+ and gets rid of them in their resources
	 */
	@Override
	public Object execute()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public void discardCards(int playerIndex, ResourceList cardsToDiscard)
	{
		CatanGame currentgame=new CatanGame();//Again needs to hook up to the current game object. 
		Player playertodiscard=null;
		for(Index id:currentgame.getMyplayers().keySet())
		{
			if(playerIndex==currentgame.getMyplayers().get(id).getPlayerIndex().getNumber())
			{
				playertodiscard=currentgame.getMyplayers().get(id);
			}
		}
		ResourceList mylist=playertodiscard.getResources();
		mylist.setWood(mylist.getWood()-cardsToDiscard.getWood());
		mylist.setWheat(mylist.getWheat()-cardsToDiscard.getWheat());
		mylist.setBrick(mylist.getBrick()-cardsToDiscard.getBrick());
		mylist.setSheep(mylist.getSheep()-cardsToDiscard.getSheep());
		mylist.setOre(mylist.getOre()-cardsToDiscard.getOre());
		playertodiscard.setResources(mylist);
	}

}
