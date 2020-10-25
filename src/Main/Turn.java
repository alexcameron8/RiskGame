package Main;
import Player.*;

/**
 * Class to represent a players turn.
 * @author Alex Cameron
 */
public class Turn {

    private int numberOfReinforcements;
    private Player player;

    /**
     * Create a new Turn
     * @param player Player taking their turn.
     */
    public Turn(Player player) {
        numberOfReinforcements = player.getReinforcement();
    }

    /**
     * Set the number of reinforcements to be placed during a turn.
     * @param numberOfReinforcements Number of reinforcements.
     */
    public void setNumberOfReinforcements(int numberOfReinforcements) {
        this.numberOfReinforcements = numberOfReinforcements;
    }

    /**
     * Gets the number of reinforcements left for a player to place
     * @return Number of troops left to place.
     */
    public int getNumberOfReinforcements() {
        return numberOfReinforcements;
    }

    /**
     * Checks if all reinforcements have been placed and turn is complete.
     * @return true if turn is complete, false otherwise.
     */
    public boolean isTurnComplete(){
        if(numberOfReinforcements==0){
            return true;
        }else{
            return false;
        }
    }

}