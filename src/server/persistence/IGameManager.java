package server.persistence;

import org.json.JSONException;
import shared.game.CatanGame;

import shared.game.CatanGame;

import java.io.IOException;
import java.util.ArrayList;

import server.ourserver.commands.ICommand;
import shared.game.CatanGame;

import javax.activation.CommandObject;

/**
 * Created by williamjones on 6/7/16.
 * IGameManager class
 * The functions that are currently void will probably be changed.
 */
public interface IGameManager
{
	
	public void clearInfo();
	
	public void loadInfo();

	void storeGameModel(int gameid);

	void addCommand(ICommand commandObject) throws JSONException, IOException;

	ArrayList<CatanGame> getCommandsList();

	void getGameModel();

	Object getGameList();

	ArrayList<ICommand> getCommands(int idgame) throws JSONException;
	CatanGame getGameModel(int gameid);
}
