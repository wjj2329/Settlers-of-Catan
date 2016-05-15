package shared.game;

import shared.game.map.Index;

/**
 * Created by williamjones on 5/14/16.
 */
public class TurnTracker
{
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Index getLongestRoad() {
        return longestRoad;
    }

    public void setLongestRoad(Index longestRoad) {
        this.longestRoad = longestRoad;
    }

    public Index getLargestArmy() {
        return largestArmy;
    }

    public void setLargestArmy(Index largestArmy) {
        this.largestArmy = largestArmy;
    }

    private int currentTurn=0;
    private String status="";
    private Index longestRoad=new Index(0);
    private Index largestArmy=new Index(0);


}
