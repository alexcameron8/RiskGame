package Main.IntializeFrame;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * This class is an event class that creates the events of the initialize model class.
 * @author Thomas
 */
public class InitializeEvent extends EventObject {

    private int NumberOfPlayer;
    private ArrayList<String> nameOfPlayers;

    /**
     * Constructs a prototypical Event.
     */
    public InitializeEvent(InitializeModel im, int NumberOfPlayer, ArrayList<String> nameOfPlayers) {
        super(im);
        this.NumberOfPlayer=NumberOfPlayer;
        this.nameOfPlayers = nameOfPlayers;
    }

    /**
     * get the number of players chosen
     *
     * @return int returns the number of players
     */
    public int getNumberOfPlayer() {
        return NumberOfPlayer;
    }

    /**
     * get the chosen names of the players
     *
     * @return ArrayList<String> The names of the players
     */
    public ArrayList<String> getNameOfPlayers(){ return  nameOfPlayers;}
}
