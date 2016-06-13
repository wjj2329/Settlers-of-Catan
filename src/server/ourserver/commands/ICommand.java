package server.ourserver.commands;

import org.json.JSONException;
import shared.game.CatanGame;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by william jones on 5/26/16.
 * Interface for the Command Objects 
 */
public interface ICommand {
	
	/**
	 * Execute depending on the implementation of the interface it will execute the corresponding task
	 */
	public Object execute() throws IOException, JSONException;

	public String toString();

	public String toJSON();

	public int getGameid();

	public Object executeversion2(CatanGame game);
}
