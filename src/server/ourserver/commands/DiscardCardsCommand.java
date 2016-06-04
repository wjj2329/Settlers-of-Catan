package server.ourserver.commands;

import client.model.TurnStatus;
import server.ourserver.ServerFacade;
import shared.chat.GameHistoryLine;
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
	public void discardCards(int playerIndex, ResourceList cardsToDiscard, int gameid)
	{
		CatanGame currentgame= ServerFacade.getInstance().getGameByID(gameid);
		currentgame.getModel().setVersion(currentgame.getModel().getVersion()+1);
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

		ResourceList mybankslist=currentgame.mybank.getCardslist();
		mybankslist.setBrick(mybankslist.getBrick()+cardsToDiscard.getBrick());
		mybankslist.setWood(mybankslist.getWood()+cardsToDiscard.getWood());
		mybankslist.setWheat(mybankslist.getWheat()+cardsToDiscard.getWheat());
		mybankslist.setSheep(mybankslist.getSheep()+cardsToDiscard.getSheep());
		mybankslist.setOre(mybankslist.getOre()+cardsToDiscard.getOre());
		currentgame.mybank.setResourceCardslist(mybankslist);

		Player playertoupdate=null;
		for(Index myind:currentgame.getMyplayers().keySet())
		{
			if(currentgame.getMyplayers().get(myind).getPlayerIndex().getNumber()==playerIndex)
			{
				playertoupdate=currentgame.getMyplayers().get(myind);
			}
		}
		currentgame.getMyGameHistory().addtolines(new GameHistoryLine(playertoupdate.getName()+ " discards cards ",playertoupdate.getName()));
		currentgame.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);

	}

}
