package client.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import client.model.Model;
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
    private ArrayList<CatanColor> colorsTaken = null;
    public static  String colorforit;
    public static int gameindex;
    public static int playerindexforit=0;
	//private GameInfo[] games;
	
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
		ModelFacade.facace_currentgame.addObserver(this);
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
	
	private synchronized void refreshGameList()
	{
        ArrayList<CatanGame> gamesList = ModelFacade.facace_currentgame.getModel().listGames();

        if (gamesList == null)
        {
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
            startJoinGame(currentGame);
        }
        
        if(Arrays.equals(games, lastList))
        {
            return;
        }
        
        lastList = games;
        PlayerInfo localPlayer = new PlayerInfo();
        localPlayer.setId(playerindexforit);
        playerindexforit++;
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
                    if(gameInList.getPlayers().size() == 4)
                    {
                        for(PlayerInfo pInfo: gameInList.getPlayers())
                            if(pInfo.getId() == localPlayer.getId())
                            {
                            	if(getJoinGameView().isModalShowing())
                            	{
                            		getJoinGameView().closeModal();
                            	}
                            	if(getSelectColorView().isModalShowing())
                            	{
                            		getSelectColorView().closeModal();
                            	}
                                return;
                            }
                        cancelJoinGame();
                    }
                    else
                        startJoinGame(gameInList);
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
			ModelFacade.facace_currentgame.getModel().createGame(randomlyPlaceNumbers, randomlyPlaceHexes, randomPorts, title);
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
        ArrayList<CatanColor> currentColorsTaken = new ArrayList<CatanColor>();
        
        //What colors are already being used?
        for(PlayerInfo playerinfo: currentplayers)
        {
            if(playerinfo.getId() != ModelFacade.facace_currentgame.getLocalPlayer().getPlayerIndex().getNumber())
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
	}
    
	@Override
	public void cancelJoinGame() 
	{
	
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color)
	{
        colorforit=color.toString();
        ArrayList<CatanGame> games = ModelFacade.facace_currentgame.getModel().listGames();
        for(CatanGame game: games)
        {
            if(game.getGameId() == this.game.getId())
            {
                gameindex=game.getGameId();
                //ModelFacade.facace_currentgame.currentgame.getServer().JoinGame(gameindex,colorforit);  doesn't work find where it does.
                for(Player player: game.getMyplayers().values())
                {
                    getSelectColorView().setColorEnabled(color, false);
                    if(player.getColor().equals(color) && 
                    		player.getPlayerIndex().getNumber() != 
                    		ModelFacade.facace_currentgame.getLocalPlayer().getPlayerIndex().getNumber())
                    {
                        getMessageView().setMessage("Cannot join with that color.  Already taken.");
                        getMessageView().showModal();
                        return;
                    }
                }
                break;
            }
        }
        ModelFacade.facace_currentgame.getModel().joinGame(ModelFacade.facace_currentgame.getLocalPlayer(), game.getId());
        
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

