package server.persistence;

import shared.game.CatanGame;

import java.util.ArrayList;

/**
 * Created by williamjones on 6/7/16.
 * IGameManager class
 * The functions that are currently void will probably be changed.
 */
public interface IGameManager
{
	
	public void clearInfo();
	
	public void loadInfo();

	void storeGame();

	void addCommand();

	ArrayList<CatanGame> getCommandsList();

	void getGameModel();

	void getGameList();
}
