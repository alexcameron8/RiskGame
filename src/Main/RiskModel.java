package Main;

import Player.Player;

import java.util.ArrayList;

public class RiskModel {
    private ArrayList<Player> players;
    private Map.Map gameMap;
    private int activePlayerID;

    RiskModel(){
        players = new ArrayList<>();
    }



    boolean canAttack(){
        return true;
    }

    boolean canPlace(){
        return true;
    }

    boolean canAdvanceTurn(){
        return true;
    }
}
