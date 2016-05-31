package client.join;

import client.State.State;
import client.base.*;
import client.data.PlayerInfo;
import client.model.ModelFacade;
import client.model.ServerPoller;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import shared.game.player.Player;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController ,Observer
{
    private Timer timer;
    private int numPlayers = 0;
    private ServerPoller serverPoller;
	public PlayerWaitingController(IPlayerWaitingView view)
	{
		super(view);
	}

	@Override
	public IPlayerWaitingView getView()
	{

        ModelFacade.facadeCurrentGame.addObserver(this);
        return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() 
	{
		//System.out.println("Starting waiting");
		serverPoller = new ServerPoller(ModelFacade.facadeCurrentGame.currentgame,ModelFacade.facadeCurrentGame.getServer());
		serverPoller.startPoller();
        String[] aiChoices = new String[1];
        aiChoices[0] = "LARGEST_ARMY";
       // ModelFacade.facadeCurrentGame.listAI()).toArray(aiChoices);
        getView().setAIChoices(aiChoices);
        //getView().showModal();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                checkGame();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 2000);
	}

	@Override
	public void addAI() 
	{
			ModelFacade.facadeCurrentGame.getServer().addAIPlayer("LARGEST_ARMY");
	}
	
	public void checkGame()
    {
//		try
//		{
//			String stringy = ModelFacade.facadeCurrentGame.getModel().getServer().getGameCurrentState(0).getResponse();
//			System.out.println("This is stringy: " + stringy);
//			JSONObject obj = new JSONObject(stringy);
//			ModelFacade.facadeCurrentGame.updateFromJSON(obj);
//			
//		} catch (JSONException e)
//		{
//			System.out.println("I got an error");
//			e.printStackTrace();
//			
//		}
		
        if(this.numPlayers == ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size())
        {
        	//System.out.println("Oh noes");
            return;
        }
        if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size() == 4)
        {
            //this.timer.cancel();
            
            if(getView().isModalShowing())
            {
                getView().closeModal();
                ModelFacade.facadeCurrentGame.currentgame.setCurrentState(State.SetUpState);
                
            }
            this.timer.cancel();
            
//            serverPoller.startPoller();
            return;
        }
        this.numPlayers = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size();
        Collection<Player> currentPlayers = ModelFacade.facadeCurrentGame.currentgame.getMyplayers().values();
        PlayerInfo[] playerInfo = new PlayerInfo[currentPlayers.size()];
        int idx = 0;
        Set <Player>ordering=new TreeSet<>();
        for(Player player:currentPlayers)
        {
            ordering.add(player);
        }
        for(Player player: ordering)
        {
            System.out.println("I Add a player to waiting view "+player.getName()+"HIS ID IS "+player.getPlayerID().getNumber());
            playerInfo[idx] = new PlayerInfo();
            playerInfo[idx].setColor(player.getColor());
            playerInfo[idx].setId(player.getPlayerID().getNumber());
            playerInfo[idx].setName(player.getName());
            idx++;
        }
        getView().setPlayers(playerInfo);
        if(getView().isModalShowing())
        {
            getView().closeModal();
        }
        getView().showModal();
    }
	
	public ArrayList<String> listAI()
    {
        ArrayList<String> aiTypes = new ArrayList<String>();
        aiTypes.add("LARGEST_ARMY");
        return aiTypes;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(ModelFacade.facadeCurrentGame.currentgame.getMyplayers().size() == 4)
        {
            //this.timer.cancel();

            if(getView().isModalShowing()) {
                getView().closeModal();
                ModelFacade.facadeCurrentGame.currentgame.setCurrentState(State.SetUpState);

            }

//            serverPoller.startPoller();
            return;
        }
    }
}

