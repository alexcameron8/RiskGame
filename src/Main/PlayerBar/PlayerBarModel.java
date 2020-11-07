package Main.PlayerBar;

import java.util.*;
import Player.*;
import Main.*;

public class PlayerBarModel {
    private List<PlayerBarView> playerBarViews;
    private RiskModel rm;

    public PlayerBarModel(RiskModel rm){
        this.rm = rm;
        playerBarViews = new ArrayList<>();
    }

    public void addPlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.add(pbv);
    }

    public void removePlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.remove(pbv);
    }

    public ArrayList<Player> getPlayers(){
        return rm.getPlayers();
    }

    public Player getCurrentTurn(){
        return rm.getActivePlayer();
    }
}
