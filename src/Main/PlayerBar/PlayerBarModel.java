package Main.PlayerBar;

import java.util.*;
import Player.*;
import Main.*;

public class PlayerBarModel {
    private List<PlayerBarView> playerBarViews;
    private Risk game;

    public PlayerBarModel(){
        playerBarViews = new ArrayList<>();
    }

    public void addPlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.add(pbv);
    }

    public void removePlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.remove(pbv);
    }

    public ArrayList<Player> getPlayers(){
        return game.getPlayers();
    }

    public Player getCurrentTurn(){
        return game.getActivePlayer();
    }
}
