package client.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import shared.chat.Chat;
import shared.chat.ChatMessages;
import shared.chat.GameHistory;
import shared.game.Bank;
import shared.game.CatanGame;

/**
 * 
 * Facade for the Model. Every object accessing the model classes should do so through here 
 *
 */
public class ModelFacade
{
	//CatanGame.singleton
	public ModelFacade()
	{

	}

	public JSONObject serializeModel() throws JSONException
	{
		JSONObject myobject=new JSONObject();

		//bank code
		JSONObject bank=new JSONObject();
		bank.put("brick:", Bank.getSingleton().getCardslist().getBrick());
		bank.put("ore:", Bank.getSingleton().getCardslist().getOre());
		bank.put("sheep:", Bank.getSingleton().getCardslist().getSheep());
		bank.put("wheat:", Bank.getSingleton().getCardslist().getWheat());
		bank.put("wood:", Bank.getSingleton().getCardslist().getWood());
		myobject.put("bank:", bank);

		//chat code
		JSONObject chat=new JSONObject();
		JSONArray lines=new JSONArray();
		Chat mychat=CatanGame.singleton.getMychat();
		ChatMessages mymessages=mychat.getChatMessages();
		for(int i=0; i<mymessages.messages().size(); i++)
		{
			JSONObject messageandstring=new JSONObject();
			messageandstring.put("message:",mymessages.messages().get(i).getMessage());
			messageandstring.put("source:",mymessages.messages().get(i).getSource());
			lines.put(messageandstring);
		}
		chat.put("lines:",lines);
		myobject.put("chat:",chat);

		//logcode
		JSONObject log=new JSONObject();
		JSONArray lines2=new JSONArray();
		GameHistory myhistory=mychat.getGameHistory();
		//for(int i=0; i<)

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
