package Main.IntializeFrame;

import java.util.ArrayList;
import java.util.List;

public class InitializeModel {

    private List<InitializeView> initializeViews;
    public static final int MAX_NUMBER_PLAYERS = 6;
    public static final int MIN_NUMBER_PLAYERS = 2;
    private ArrayList<String> playerNames;
    private int numberOfPlayers;

    public InitializeModel(){
        initializeViews = new ArrayList<>();
        playerNames = new ArrayList<>();
        numberOfPlayers = MIN_NUMBER_PLAYERS;
    }

    public void addInitializeView(InitializeView iv){
        initializeViews.add(iv);
    }

    public void setPlayerNumbers(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        for (InitializeView iv: initializeViews) iv.handleInitializeUpdate(new InitializeEvent(this,numberOfPlayers,null));
    }

    public void playerNames(){
        for(InitializeView iv: initializeViews){
            for(String playerName :iv.getNameOfPlayers()){
                playerNames.add(playerName);
            }
        }
    }

    public ArrayList<String> getPlayerNames(){
        return playerNames;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
}
