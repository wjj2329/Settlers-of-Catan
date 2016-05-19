package client.join;

import client.base.*;
import client.data.PlayerInfo;
import client.model.ModelFacade;
import client.model.ServerPoller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.game.player.Player;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController 
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

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() 
	{
        String[] aiChoices = new String[1];
        aiChoices[0] = "LARGEST_ARMY";
       // ModelFacade.facace_currentgame.listAI()).toArray(aiChoices);
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
			ModelFacade.facace_currentgame.currentgame.getServer().addAIPlayer(getView().getSelectedAI());
	}
	
	public void checkGame()
    {
        if(this.numPlayers == ModelFacade.facace_currentgame.getMyplayers().size())
        {
            return;
        }
        if(ModelFacade.facace_currentgame.getMyplayers().size() == 4)
        {
            this.timer.cancel();
            serverPoller = new ServerPoller(ModelFacade.facace_currentgame.currentgame,ModelFacade.facace_currentgame.currentgame.getServer());
            if(getView().isModalShowing())
            {
                getView().closeModal();
            }
            serverPoller.startPoller();
            return;
        }
        this.numPlayers = ModelFacade.facace_currentgame.getMyplayers().size();
        ArrayList<Player> currentPlayers = (ArrayList<Player>) ModelFacade.facace_currentgame.getMyplayers().values();
        PlayerInfo[] playerInfo = new PlayerInfo[currentPlayers.size()];
        int idx = 0;
        for(Player player: currentPlayers)
        {
            playerInfo[idx] = new PlayerInfo();
            playerInfo[idx].setColor(player.getColor());
            playerInfo[idx].setId(player.getPlayerIndex().getNumber());
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

}

