package server.ourserver.commands;

import client.model.TurnStatus;
import org.json.JSONException;
import server.ourserver.ServerFacade;
import server.persistence.TextDBGameManagerDAO;
import shared.chat.GameHistoryLine;
import shared.game.CatanGame;
import shared.game.ResourceList;
import shared.game.map.Index;
import shared.game.player.Player;

import java.io.FileNotFoundException;

/**
 * The DiscardCardsCommand
 */
public class DiscardCardsCommand implements ICommand {

	/**
	 * Executes the task:
	 * 	player to discard cards if they have 7+ and gets rid of them in their resources
	 */
	int playerIndex;
	ResourceList cardsToDiscard;
	int gameid;
	public DiscardCardsCommand(int playerIndex, ResourceList cardsToDiscard, int gameid)
	{
		this.playerIndex=playerIndex;
		this.cardsToDiscard=cardsToDiscard;
		this.gameid=gameid;
	}

	@Override
	public Object execute() throws FileNotFoundException, JSONException {
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
		currentgame.getModel().getTurntracker().setStatus(TurnStatus.PLAYING);		return null;
	}

	@Override
	public String toString() {
		TextDBGameManagerDAO.commandNumber++;
		return "," +  TextDBGameManagerDAO.commandNumber+":"+"{" +
				"  \"type\": \"DiscardCardsCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"brick\":"+"\"" + cardsToDiscard.getBrick() +"\""+
				", \"wheat\":"+"\""+cardsToDiscard.getWheat()+"\""+
				", \"sheep\":"+"\""+cardsToDiscard.getSheep()+"\""+
				", \"ore\":"+"\""+cardsToDiscard.getOre()+"\""+
				", \"wood\":"+"\""+cardsToDiscard.getWood()+"\""+
				", \"gameid\":" + gameid +
				"}}";
	}

	@Override
	public String toJSON() {
		return "{" +
				"  \"type\": \"DiscardCardsCommand\"" +
				", \"playerIndex\":" + playerIndex +
				", \"brick\":"+"\"" + cardsToDiscard.getBrick() +"\""+
				", \"wheat\":"+"\""+cardsToDiscard.getWheat()+"\""+
				", \"sheep\":"+"\""+cardsToDiscard.getSheep()+"\""+
				", \"ore\":"+"\""+cardsToDiscard.getOre()+"\""+
				", \"wood\":"+"\""+cardsToDiscard.getWood()+"\""+
				", \"gameid\":" + gameid +
				"}";
	}

	@Override
	public int getGameid() {
		return gameid;

	}
}
