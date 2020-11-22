package Main;

import Map.Map;
import Player.Player;

import java.util.ArrayList;

public class GameState {
    private ArrayList<Player> players;
    private int activePlayerID;
    private Map map;
    private boolean turnComplete;
    private Player winner;

    GameState(){
        this.winner = null;
    }
}
