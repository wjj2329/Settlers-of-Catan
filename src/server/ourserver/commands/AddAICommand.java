package server.ourserver.commands;

import shared.game.CatanGame;

/**
 * The AddAICommand class
 */
public class AddAICommand implements ICommand {

	/**
	 * Executes the task
	 * 	adding an AI Player to the game. 
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "AddAICommand{}";
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGameid() {
		return -1;
	}

	@Override
	public Object executeversion2(CatanGame game)
	{
		return null;
	}
}
