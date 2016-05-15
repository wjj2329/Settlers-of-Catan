package client.State;

public enum State {

	LoginState{
		@Override
		public void playSoldierCard() {

		}

		@Override
		public void playRoadBuildingCard() {

		}

		@Override
		public void playMonumentCard() {

		}

		@Override
		public void playMonopolyCard() {

		}

		@Override
		public void buildCity() {

		}

		@Override
		public void buildSettlement() {

		}

		@Override
		public void buildRoad() {

		}

		@Override
		public void rollNumber() {

		}

		@Override
		public void createGame() {

		}

		@Override
		public void addAI() {

		}

		@Override
		public String getState() {
			return null;
		}
	},
	
	GamePlayingState{
		@Override
		public void playSoldierCard() {

		}

		@Override
		public void playRoadBuildingCard() {

		}

		@Override
		public void playMonumentCard() {

		}

		@Override
		public void playMonopolyCard() {

		}

		@Override
		public void buildCity() {

		}

		@Override
		public void buildSettlement() {

		}

		@Override
		public void buildRoad() {

		}

		@Override
		public void rollNumber() {

		}

		@Override
		public void createGame() {

		}

		@Override
		public void addAI() {

		}

		@Override
		public String getState() {
			return null;
		}
	},
	SetUpState{
		@Override
		public void playSoldierCard() {

		}

		@Override
		public void playRoadBuildingCard() {

		}

		@Override
		public void playMonumentCard() {

		}

		@Override
		public void playMonopolyCard() {

		}

		@Override
		public void buildCity() {

		}

		@Override
		public void buildSettlement() {

		}

		@Override
		public void buildRoad() {

		}

		@Override
		public void rollNumber() {

		}

		@Override
		public void createGame() {

		}

		@Override
		public void addAI() {

		}

		@Override
		public String getState() {
			return null;
		}
	},
	PlayerWaitingState{
		@Override
		public void playSoldierCard() {

		}

		@Override
		public void playRoadBuildingCard() {

		}

		@Override
		public void playMonumentCard() {

		}

		@Override
		public void playMonopolyCard() {

		}

		@Override
		public void buildCity() {

		}

		@Override
		public void buildSettlement() {

		}

		@Override
		public void buildRoad() {

		}

		@Override
		public void rollNumber() {

		}

		@Override
		public void createGame() {

		}

		@Override
		public void addAI() {

		}

		@Override
		public String getState() {
			return null;
		}
	},
	JoinGameState{
		@Override
		public void playSoldierCard() {

		}

		@Override
		public void playRoadBuildingCard() {

		}

		@Override
		public void playMonumentCard() {

		}

		@Override
		public void playMonopolyCard() {

		}

		@Override
		public void buildCity() {

		}

		@Override
		public void buildSettlement() {

		}

		@Override
		public void buildRoad() {

		}

		@Override
		public void rollNumber() {

		}

		@Override
		public void createGame() {

		}

		@Override
		public void addAI() {

		}

		@Override
		public String getState() {
			return null;
		}
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
