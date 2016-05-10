package client.model;

import org.json.JSONObject;
import shared.game.CatanGame;

/**
 * 
 * Facade for the Model. Every object accessing the model classes should do so through here 
 *
 */
public class ModelFacade
{
	//CatanGame.singleton
	public ModelFacade() {
	}

	public JSONObject serializeModel()
	{
			JSONObject myobject=null;
			return myobject;
	}

		/**
		 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and
		 * puts it into the model.
		 */
	public void updateFromJSON(JSONObject myobject)
	{

	}
		// TODO Auto-generated constructor stub

	
}
