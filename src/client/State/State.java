package client.State;

import shared.game.CatanGame;
import shared.game.map.Hex.Hex;
import shared.game.player.Player;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

public enum State {


	LoginState{
		//player logins or registers 
		
		@Override
		public void login() {

		}

		@Override
		public void register() {

		}


		@Override
		public String getState() {
			return "LOGIN";
		}
	},
	
	GamePlayingState{
		@Override
		public boolean canBuildCity(Hex hex, VertexLocation mylocation) throws Exception {
			return (CatanGame.singleton.getCurrentPlayer().canBuildCity(hex, mylocation));
		}
		//player is actually playing the game.
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
		public void buildCity(Hex buildingon, VertexLocation myloaction) {
			try {
				CatanGame.singleton.getCurrentPlayer().buildCity(buildingon, myloaction);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void buildSettlement(Hex buildingon, VertexLocation vertexLocation)throws Exception
		{
			CatanGame.singleton.getCurrentPlayer().buildSettlementNormal(buildingon,vertexLocation);
		}

		@Override
		public void buildRoad(Hex hex, EdgeLocation edge)
		{
			CatanGame.singleton.getCurrentPlayer().buildRoadPiece(hex, edge);
		}

		@Override
		public void rollNumber()
		{

		}
		
		@Override
		public String getState() {
			return "GAMEPLAYING";
		}

		@Override
		public boolean canBuildRoadPiece(Hex hex, EdgeLocation edge)
		{
			return (CatanGame.singleton.getCurrentPlayer().canBuildRoadPiece(hex, edge));
		}

		@Override
		public boolean canBuildSettlement(Hex hex, VertexLocation mylocation) throws Exception
		{
			return (CatanGame.singleton.getCurrentPlayer().canBuildSettlementNormal(hex, mylocation));
		}
	},

	SetUpState
	{

		@Override
		public boolean canBuildSettlement(Hex hex, VertexLocation mylocation) throws Exception {
			return (CatanGame.singleton.getCurrentPlayer().canBuildSettlementStartup(hex, mylocation));
		}
		//First set up round player gets 2 free settlements and roads
		@Override
		public void buildSettlement(Hex buildingon, VertexLocation vertexLocation)throws Exception
		{
				CatanGame.singleton.getCurrentPlayer().buildSettlement(buildingon,vertexLocation);
		}

		@Override
		public void buildRoad(Hex hex, EdgeLocation edge)
		{
			CatanGame.singleton.getCurrentPlayer().buildRoadPieceSetupState(hex, edge);
		}
		@Override
		public String getState() {
			return "SETUP";
		}

		@Override
		public boolean canBuildRoadPiece(Hex hex, EdgeLocation edge)
		{
			return CatanGame.singleton.getCurrentPlayer().canBuildRoadPieceSetupState(hex, edge);
		}
	},
	PlayerWaitingState{
		//player waits for other players to join game can add AI player

		@Override
		public void addAI() {

		}

		@Override
		public String getState() {
			return "PLAYERWAITING";
		}
	},
	JoinGameState
	{
		//player can join or re-join games or create game
		@Override
		public void joinGame()
		{

		}
		@Override
		public void createGame()
		{

		}
		
		@Override
		public String getState() {
			return "JOINGAME";
		}
	};
	
	
	//default methods do nothing.
	public boolean canBuildCity(Hex hex, VertexLocation mylocation)throws Exception{return false;}
	public void login(){}
	public void joinGame(){}
	public void register(){}
	public void playSoldierCard(){}
	public void playRoadBuildingCard(){}
	public void playMonumentCard(){}
	public void playMonopolyCard(){}
	public void buildCity(Hex buildingon, VertexLocation myloaction){}
	public void buildSettlement(Hex buildingon, VertexLocation vertexLocation)throws Exception{}
	public void buildRoad(Hex hex, EdgeLocation edge){}
	public void placeRobber(){}
	public void rollNumber(){}
	public void createGame(){}
	public void addAI(){}
	public String getState(){ return null; }
	public boolean canBuildRoadPiece(Hex hex, EdgeLocation edge)
	{
		return false;
	}
	public boolean canBuildSettlement(Hex hex, VertexLocation mylocation) throws Exception{return false;}

}
