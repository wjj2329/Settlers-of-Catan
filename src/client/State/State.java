package client.State;

public enum State {

	LoginState{
		
	},
	
	GamePlayingState{
	
	},
	SetUpState{
		
	},
	PlayerWaitingState{
		
	},
	JoinGameState{
	
	};
	
	public abstract void playSoldierCard();
	public abstract void playRoadBuildingCard();
	public abstract void playMonumentCard();
	public abstract void playMonopolyCard();
	public abstract void buildCity();
	public abstract void buildSettlement();
	public abstract void buildRoad();
	public abstract void rollNumber();
	public abstract void createGame();
	public abstract void addAI();
	public abstract String getState();
	
	
	
	
}
