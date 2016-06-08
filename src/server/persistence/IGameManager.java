package server.persistence;

import shared.game.CatanGame;

import shared.game.CatanGame;

import java.util.ArrayList;

import server.ourserver.commands.ICommand;
import shared.game.CatanGame;

/**
 * Created by williamjones on 6/7/16.
 * IGameManager class
 * The functions that are currently void will probably be changed.
 */
public interface IGameManager
{
	
	public void clearInfo();
	
	public void loadInfo();

	void storeGameModel();

	void addCommand();

	ArrayList<CatanGame> getCommandsList();

	void getGameModel();

	Object getGameList();

	ICommand getCommand();
	CatanGame getGameModel(int gameid);
}
