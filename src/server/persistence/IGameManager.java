package server.persistence;

import server.ourserver.commands.ICommand;
import shared.game.CatanGame;

/**
 * Created by williamjones on 6/7/16.
 */
public interface IGameManager {
	
	public void storeGameModel();
	public void addCommand();
	public ICommand getCommand();
	public CatanGame getGameModel(int gameid);
	public Object getGameList();
}
