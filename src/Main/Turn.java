package Main;
import Player.*;
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