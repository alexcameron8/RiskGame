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
     * @param rm The current risk model
     */
    public PlayerBarModel(RiskModel rm){
        this.rm = rm;
        playerBarViews = new ArrayList<>();
    }

    /**
     * add a playerBarView
     * @param pbv The player bar view
     */
    public void addPlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.add(pbv);
    }

    /**
     * remove a player bar view
     * @param pbv The player bar view
     */
    public void removePlayerBarModelViews(PlayerBarView pbv){
        playerBarViews.remove(pbv);
    }

    /**
     * get the players
     *
     * @return ArrayList<Player> The ArrayList of all the list of players in RiskModel
     */
    public ArrayList<Player> getPlayers(){
        return rm.getPlayers();
    }

    /**
     * get the current player who's turn it is
     *
     * @return Player returns the player who is of the current turn
     */
    public Player getCurrentTurn(){
        return rm.getActivePlayer();
    }
}
