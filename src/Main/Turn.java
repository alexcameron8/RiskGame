package Main;
import Player.*;

/**
 * Class to represent a players turn.
 * @author Alex Cameron
 */
public class Turn {

    private int numberOfReinforcements;
    private Player player;

    public Turn(Player player) {
        numberOfReinforcements = player.getReinforcement();
    }

    public void setNumberOfReinforcements(int numberOfReinforcements) {
        this.numberOfReinforcements = numberOfReinforcements;
    }

    public int getNumberOfReinforcements() {
        return numberOfReinforcements;
    }
    public boolean isTurnComplete(){
        if(numberOfReinforcements==0){
            return true;
        }else{
            return false;
        }
    }

}