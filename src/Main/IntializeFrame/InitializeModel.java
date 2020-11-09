package Main.IntializeFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InitializeModel {

    private List<InitializeView> initializeViews;
    public static final int MAX_NUMBER_PLAYERS = 6;
    public static final int MIN_NUMBER_PLAYERS = 2;

    public static final HashMap<String, Color> COLOURS = new HashMap<>()
    {{
        put("Red", new Color(255, 123, 144));
        put("Green", new Color(155, 234, 205));
        put("Blue", new Color(140, 186, 255));
        put("Yellow", new Color(255, 246, 122));
        put("Orange", new Color(255, 206, 178));
        put("Purple", new Color(238, 176, 255));
    }};

    private ArrayList<PlayerInfo> playersInfo;
    private int numberOfPlayers;

    public InitializeModel(){
        initializeViews = new ArrayList<>();
        playersInfo = new ArrayList<>();
        for(int i = 0; i < MAX_NUMBER_PLAYERS; i++){
            playersInfo.add(new PlayerInfo(null, null));
        }
        numberOfPlayers = MIN_NUMBER_PLAYERS;
    }

    public void addInitializeView(InitializeView iv){
        initializeViews.add(iv);
    }

    public void setPlayerNumbers(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        for (InitializeView iv: initializeViews) iv.handleInitializeUpdate(new InitializeEvent(this,numberOfPlayers,null));
    }

    public void setPlayerName(int playerNumber, String playerName){
        playersInfo.get(playerNumber-1).setName(playerName);
    }

    public void setPlayerColour(int playerNumber, String playerColour){
        playersInfo.get(playerNumber-1).setColor(COLOURS.get(playerColour));
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public ArrayList<Color> getPlayersColours(){
        ArrayList<Color> playersColours = new ArrayList<>();
        for(PlayerInfo playerInfo: playersInfo){
            if(!playersColours.contains(playerInfo.getColor())) {
                playersColours.add(playerInfo.getColor());
            }
            else{
                playersColours.add(null);
            }
        }

        for(int i = 0; i < playersColours.size(); i++){
            if(playersColours.get(i) == null){
                for(Color colour: COLOURS.values()){
                    if(!playersColours.contains(colour)){
                        playersColours.set(i, colour);
                        break;
                    }
                }
            }
        }
        return new ArrayList<>(playersColours.subList(0,numberOfPlayers));
    }

    public ArrayList<String> getNamesOfPlayers() {
        ArrayList<String> namesOfPlayers = new ArrayList<>();
        for(PlayerInfo playerInfo: playersInfo){
            if(playerInfo.getName() == null){
                namesOfPlayers.add("Player");
            }
            else
                namesOfPlayers.add(playerInfo.getName());

        }

        for(String playerName: namesOfPlayers){
            ArrayList<Integer> positionOfSame = new ArrayList<>();

            for(int i = 0; i < namesOfPlayers.size(); i++){
                if(namesOfPlayers.get(i).equals(playerName)){
                    positionOfSame.add(i);
                }
            }

            if(positionOfSame.size()>1) {
                for (int i = 0; i < positionOfSame.size(); i++) {
                    namesOfPlayers.set(positionOfSame.get(i), namesOfPlayers.get(positionOfSame.get(i)) + " " + (i + 1));
                }
            }
        }
        return new ArrayList<>(namesOfPlayers.subList(0,numberOfPlayers));
    }
}

class PlayerInfo{

    private String name;
    private Color color;

    public PlayerInfo(String name, Color color){
        this.color=color;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
