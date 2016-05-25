package client.communication;

import java.util.*;
import java.util.List;

import client.base.*;
import client.model.ModelFacade;
import shared.chat.GameHistory;
import shared.chat.GameHistoryLine;
import shared.definitions.*;
import shared.game.map.Index;
import shared.game.player.Player;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController,Observer {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		ModelFacade.facadeCurrentGame.addObserver(this);
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.BLUE, "Welcome to Catan! :D "));
		/*entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));*/
		
		getView().setEntries(entries);
	
		//</temp>
	}


	@Override
	public void update(Observable o, Object arg)
	{
		ArrayList<GameHistoryLine> gameHistories=ModelFacade.facadeCurrentGame.currentgame.getMyGameHistory().getLines();
		List<LogEntry> entries = new ArrayList<LogEntry>();

		for(int i=0; i<gameHistories.size(); i++)
		{
			CatanColor playercolor=CatanColor.PUCE;
			for(Index loc:ModelFacade.facadeCurrentGame.currentgame.getMyplayers().keySet())
			{
				if(gameHistories.get(i).getSource().equals(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(loc).getName()))
				{
					playercolor=ModelFacade.facadeCurrentGame.currentgame.getMyplayers().get(loc).getColor();
				}
			}
			entries.add(new LogEntry(playercolor,gameHistories.get(i).getLine()));
		}
		getView().setEntries(entries);
	}
}

