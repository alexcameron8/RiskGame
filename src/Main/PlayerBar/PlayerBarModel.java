package Main.PlayerBar;

import java.util.*;
import Player.*;
import Main.*;

/**
 * This class is the model for the player bar
 * @author Thomas
 */
public class PlayerBarModel {
    private List<PlayerBarView> playerBarViews;
    private RiskModel rm;

    /**
     * Constructor for PlayerBarModel
     * @param rm
     */
    public PlayerBarModel(RiskModel rm){
        this.rm = rm;
        playerBarViews = new ArrayList<>();
    }

    /**
     * add a playerBarView
     * @param pbv
     */
    public void addPlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.add(pbv);
    }

    /**
     * remove a player bar view
     * @param pbv
     */
    public void removePlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.remove(pbv);
    }

    /**
     * get the players
     *
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayers(){
        return rm.getPlayers();
    }

    /**
     * get the current player who's turn it is
     *
     * @return Player
     */
    public Player getCurrentTurn(){
        return rm.getActivePlayer();
    }
}
