package Main;

import Player.Player;

import java.util.ArrayList;
import java.util.EventObject;

public class RiskEvent extends EventObject {
    private Turn currentTurn;
    private int activePlayerID;
    private ArrayList<Player> players;
    private Player winner;
    private Player eliminatedPlayer;

    public RiskEvent(RiskModel riskModel, int activePlayerID, ArrayList<Player> players, Turn currentTurn,Player winner, Player eliminatedPlayer){
        super(riskModel);
        this.players = players;
        this.activePlayerID = activePlayerID;
        this.currentTurn = currentTurn;
        if(winner!=null){
            this.winner = winner;
        }
        if(eliminatedPlayer!=null){
            this.eliminatedPlayer = eliminatedPlayer;
        }
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

    public Player getWinner() {
        return winner;
    }
    public Player getEliminatedPlayer(){
        return eliminatedPlayer;

    }}
