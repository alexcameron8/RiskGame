package Main;

import Player.Player;

import java.util.ArrayList;
import java.util.EventObject;

public class RiskEvent extends EventObject {
    private Turn currentTurn;
    private int activePlayerID;
    private ArrayList<Player> players;
    public RiskEvent(RiskModel riskModel, int activePlayerID, ArrayList<Player> players, Turn currentTurn){
        super(riskModel);
        this.players = players;
        this.activePlayerID = activePlayerID;
        this.currentTurn = currentTurn;
    }

    public Turn getCurrentTurn(){
        return currentTurn;
    }
    public int getActivePlayerID(){
        return activePlayerID;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
