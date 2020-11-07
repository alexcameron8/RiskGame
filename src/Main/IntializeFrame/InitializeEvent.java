package Main.IntializeFrame;

import java.util.ArrayList;
import java.util.EventObject;

public class InitializeEvent extends EventObject {

    private int NumberOfPlayer;
    private ArrayList<String> nameOfPlayers;

    /**
     * Constructs a prototypical Event.
     *
     */
    public InitializeEvent(InitializeModel im, int NumberOfPlayer, ArrayList<String> nameOfPlayers) {
        super(im);
        this.NumberOfPlayer=NumberOfPlayer;
        this.nameOfPlayers = nameOfPlayers;
    }

    public int getNumberOfPlayer() {
        return NumberOfPlayer;
    }

    public ArrayList<String> getNameOfPlayers(){ return  nameOfPlayers;}
}
