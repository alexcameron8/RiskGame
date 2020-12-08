package Main;

import Player.Player;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * The RiskEvent class gets listens to the Risk model which then notifies it's listeners
 */
public class RiskEvent extends EventObject {
    private int activePlayerID;
    private ArrayList<Player> players;
    private Player winner;
    private Player eliminatedPlayer;

    /**
     * Risk Event which gets the values in the parameters from the current risk model.
     *
     * @param riskModel the current risk model
     * @param activePlayerID the active player ID
     * @param players the list of players in the game
     * @param winner the player who wins
     * @param eliminatedPlayer the player who has been eliminated
     */
    public RiskEvent(RiskModel riskModel, int activePlayerID, ArrayList<Player> players,Player winner, Player eliminatedPlayer){
        super(riskModel);
        this.players = players;
        this.activePlayerID = activePlayerID;
        if(winner!=null){
            this.winner = winner;
        }
        if(eliminatedPlayer!=null){
            this.eliminatedPlayer = eliminatedPlayer;
        }
    }

    /**
     * Gets the active player ID of who's turn it is
     * @return the active player ID
     */
    public int getActivePlayerID(){
        return activePlayerID;
    }

    /**
     * gets the Winning player
     * @return the winning player
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Gets the eliminated player
     * @return the eliminated player
     */
    public Player getEliminatedPlayer(){
        return eliminatedPlayer;

    }}
