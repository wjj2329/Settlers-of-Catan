package server.ourserver.commands;

/**
 * The CreateGameCommand class
 */
public class CreateGameCommand implements ICommand {

	/**
	 * Executes the task: player to create a game with a title
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public String toString() {
		return "CreateGameCommand{}";
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGameid()
	{
	return -1;

	}
}
