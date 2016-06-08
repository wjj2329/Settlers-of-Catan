package server.persistence;

<<<<<<< HEAD
import shared.game.CatanGame;

import java.util.ArrayList;

=======
import server.ourserver.commands.ICommand;
import shared.game.CatanGame;

>>>>>>> origin/master
/**
 * Created by williamjones on 6/7/16.
 * IGameManager class
 * The functions that are currently void will probably be changed.
 */
<<<<<<< HEAD
public interface IGameManager
{
	
	public void clearInfo();
	
	public void loadInfo();

	void storeGame();

	void addCommand();

	ArrayList<CatanGame> getCommandsList();

	void getGameModel();

	void getGameList();
=======
public interface IGameManager {
	public void storeGameModel();
	public void addCommand();
	public ICommand getCommand();
	public CatanGame getGameModel(int gameid);
	public Object getGameList();
>>>>>>> origin/master
}
