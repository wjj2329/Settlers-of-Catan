package server.persistence;

import shared.chat.GameHistoryLine;

/**
 * Created by williamjones on 6/7/16.
 */
public interface IFactory {
	
	//I think these have to be in here? 
	/**
	 * This gets the Game Manager thing
	 * @return
	 */
	public IGameManager getGameManager();
	
	/**
	 * This gets the User Account stuff
	 * @return
	 */
	public IUserAccount getUserAccount();
	
}
