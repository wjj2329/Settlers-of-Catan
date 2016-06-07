package client.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import client.State.State;
import client.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

import shared.definitions.CatanColor;
import shared.game.CatanGame;
import shared.game.player.Player;
import client.base.*;
import client.data.*;
import client.misc.*;
import client.model.ModelFacade;


/**
 * Implementation for the join game controller!
 */
public class JoinGameController extends Controller implements IJoinGameController, Observer {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
    private Timer timer;
    private GameInfo game = null;
    private boolean shouldShowGameList = true;
    private GameInfo[] lastList = null;
    private Collection<CatanColor> colorsTaken = null;
	//private boolean cancelled;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		ModelFacade.facadeCurrentGame.addObserver(this);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

    //boolean startgame=true;
	private synchronized void refreshGameList()
	{
        ModelFacade.facadeCurrentGame.loadGames();
        ArrayList<CatanGame> gamesList = ModelFacade.facadeCurrentGame.getModel().listGames();

        if (gamesList == null || ModelFacade.facadeCurrentGame.getLocalPlayer() == null)
        {
            //System.out.println("I RETURN NULL AND DIE");
            return;
        }
        
        GameInfo[] games = new GameInfo[gamesList.size()];
        GameInfo currentGame = null;
        int idx = 0;
        for(CatanGame gameChoice: gamesList)
        {
            games[idx] = new GameInfo();
            games[idx].setId(gameChoice.getGameId());
            games[idx].setTitle(gameChoice.getTitle());
            int playerIndex = 0;
            for(Player player: gameChoice.getMyplayers().values())
            {
                PlayerInfo onePlayersInfo = new PlayerInfo();
                onePlayersInfo.setColor(player.getColor());
                onePlayersInfo.setId(player.getPlayerID().getNumber());
                onePlayersInfo.setName(player.getName());
                onePlayersInfo.setPlayerIndex(playerIndex++);
                games[idx].addPlayer(onePlayersInfo);
            }
            if(game != null && games[idx].getId() == game.getId())
            {
                currentGame = games[idx];
            }
            idx++;   
        }

        if(currentGame != null)
        {
            System.out.println("I COME HERE TO DIE");
            startJoinGame(currentGame);
        }

        
        if(Arrays.equals(games, lastList))
        {
            return;
        }
        
        lastList = games;
        PlayerInfo localPlayer = new PlayerInfo();
       // System.out.println("I come here and I DIE "+ModelFacade.facadeCurrentGame.getLocalPlayer().getName());
        if(ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID()==null)
        {
            //System.out.println("IT'S NULL NOOB");
            return;
        }
        //System.out.println("This is the player ID: " + ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID().getNumber());
        localPlayer.setId(ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID().getNumber());
        getJoinGameView().setGames(games, localPlayer);
        if(shouldShowGameList)
        {
        	if(getJoinGameView().isModalShowing())
        	{
        		getJoinGameView().closeModal();
        	}
            getJoinGameView().showModal();
        }
        else if(game != null)
        {
            for(GameInfo gameInList : lastList)
            {
                if(game.getId() == gameInList.getId())
                {
//                    if(gameInList.getPlayers().size() == 4)
//                    {
//                        for(PlayerInfo pInfo: gameInList.getPlayers())
//                        {
//                            if(pInfo.getId() == localPlayer.getId())
//                            {
//                            	if(getJoinGameView().isModalShowing())
//                            	{
//                            		getJoinGameView().closeModal();
//                            	}
//                            	if(getSelectColorView().isModalShowing())
//                            	{
//                            		getSelectColorView().closeModal();
//                            	}
//                                //return;
//                            }
//                        //cancelJoinGame();
//                        }
//                    }
//                    else
//                    {
                        startJoinGame(gameInList);
//                    }
                    return;
                }
            }
        }
    }

	@Override
	public void start() 
	{		
		getJoinGameView().showModal();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {

                refreshGameList();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1500);
	}

	@Override
	public void startCreateNewGame()
	{
    	if(getJoinGameView().isModalShowing())
    	{
    		getJoinGameView().closeModal();
    	}
        shouldShowGameList = false;
        getNewGameView().setTitle("");
        getNewGameView().setRandomlyPlaceHexes(false);
        getNewGameView().setRandomlyPlaceNumbers(false);
        getNewGameView().setUseRandomPorts(false);
        getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame()
	{
    	if(getNewGameView().isModalShowing())
    	{
    		getNewGameView().closeModal();
    	}
        shouldShowGameList = true;
        getJoinGameView().showModal();
	}

	@Override
	public void createNewGame() 
	{	
		//Establish game details
		boolean randomlyPlaceHexes = getNewGameView().getRandomlyPlaceHexes();
        boolean randomlyPlaceNumbers = getNewGameView().getRandomlyPlaceNumbers();
        String title = getNewGameView().getTitle();
        boolean randomPorts = getNewGameView().getUseRandomPorts();
        boolean validTitle = !(title == null || title.trim().equals(""));
        
        //Check title against other titles (needs to be unique)
        if(lastList != null)
            for(GameInfo info: lastList)
            {
                if(!validTitle || info.getTitle().equals(title.trim()))
                {
                    validTitle = false;
                    break;
                }

            }
        if(!validTitle)
        {
            getMessageView().setMessage("Invalid title -- check to see if a game with that name already exists.");
            getMessageView().showModal();
            return;
        }
        
        if(getNewGameView().isModalShowing())
        {
        	getNewGameView().closeModal();
        }
		try
		{
            //System.out.println("I Call the create game call to the server");
			ModelFacade.facadeCurrentGame.getModel().createGame(randomlyPlaceNumbers, randomlyPlaceHexes, randomPorts, title);
			ModelFacade.facadeCurrentGame.loadGames();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
        
        getJoinGameView().showModal();
        shouldShowGameList = true;
        refreshGameList();
    }

    @Override
    public void startJoinGame(GameInfo game) 
    {
        this.game = game;
        shouldShowGameList = false;
        Collection<PlayerInfo> currentplayers = game.getPlayers();
        Collection<CatanColor> currentColorsTaken = new HashSet<CatanColor>();
        
        //What colors are already being used?
        for(PlayerInfo playerinfo: currentplayers)
        {
        	//System.out.println("Name: " + playerinfo.getName());
        	//System.out.println("ID: " + playerinfo.getId());
        	//System.out.println("Index: " + playerinfo.getPlayerIndex());
        	//System.out.println("Color: " + playerinfo.getColor().name());
        	
        	//System.out.println("LocalPlayer: " + ModelFacade.facadeCurrentGame.getLocalPlayer().getName());
        	//System.out.println("LocalPlayerIndexSam: " + ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID());
        	
            if(playerinfo.getId() != ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID().getNumber())
            {
                currentColorsTaken.add(playerinfo.getColor());
            }
        }
        if(currentColorsTaken.equals(colorsTaken))
        {
            return;
        }
        
        //Color now assigned to client player
        colorsTaken = currentColorsTaken;
        for(CatanColor color: CatanColor.values())
        {
            getSelectColorView().setColorEnabled(color, !colorsTaken.contains(color));
        }
        if(!getMessageView().isModalShowing())
        {
            getSelectColorView().showModal();
        }
        //startgame=true;

    }
    
	@Override
	public void cancelJoinGame() 
	{
		//this.game = null;
        //this.shouldShowGameList = true;
        //this.colorsTaken = null;
        getJoinGameView().showModal();
	}

	@Override
	public void joinGame(CatanColor color)
	{
        ArrayList<CatanGame> games = ModelFacade.facadeCurrentGame.getModel().listGames();
        for(CatanGame game: games)
        {
        	
            if(game.getGameId() == this.game.getId())
            {
                for(Player player: game.getMyplayers().values())
                {
                    getSelectColorView().setColorEnabled(color, false);
                    if(player.getColor().equals(color) &&player.getPlayerID().getNumber() != ModelFacade.facadeCurrentGame.getLocalPlayer().getPlayerID().getNumber())
                    {
                        getMessageView().setMessage("Cannot join with that color.  Already taken.");
                        getMessageView().showModal();
                        return;
                    }
                    else
                    {
                        ModelFacade.facadeCurrentGame.getLocalPlayer().setColor(color);
                    }
                }
                break;
            }
        }
        //System.out.println("i join the game with this id "+game.getId());

        ModelFacade.facadeCurrentGame.getModel().joinGame(color, game.getId());


        //System.out.println(game.getPlayers().size());




        timer.cancel();
        if(getSelectColorView().isModalShowing())
        {
        	getSelectColorView().closeModal();
        }
        if(getJoinGameView().isModalShowing())
        {
        	getJoinGameView().closeModal();
        }
        joinAction.execute();

	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		if(arg1 != null)
        {
            if(getJoinGameView().isModalShowing())
            {
                getJoinGameView().closeModal();
            }
            if(getMessageView().isModalShowing())
            {
                getMessageView().closeModal();
            }
            if(getNewGameView().isModalShowing())
            {
                getNewGameView().closeModal();
            }
            if(getSelectColorView().isModalShowing())
            {
                getSelectColorView().closeModal();
            }
        }

	}

}

