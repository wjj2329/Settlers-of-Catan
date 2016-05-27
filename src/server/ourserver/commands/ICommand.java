package server.ourserver.commands;

/**
 * Created by williamjones on 5/26/16.
 * Interface for the Command Objects 
 */
public interface ICommand {
	
	/**
	 * Execute depending on the implementation of the interface it will execute the corresponding task
	 */
	public Object execute(); 
}
