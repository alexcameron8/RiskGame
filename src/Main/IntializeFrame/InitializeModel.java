package Main.IntializeFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Main.RiskModel.MAX_NUMBER_PLAYERS;
import static Main.RiskModel.MIN_NUMBER_PLAYERS;

/**
 * This class is the model for the initialize frame that computes the inputted player name, colour and the number of players.
 * @author Thomas
 */
public class InitializeModel {
    public static final String DEFAULT_MAP = "Standard";

    private List<InitializeView> initializeViews;

    // Available game maps
    public static final HashMap<String, String> AVAILABLE_MAPS = new HashMap<>() {{
        put("Standard", "resources/worldmap.json");
        put("Space", "resources/spacemap.json");
        put("Standard America Unreachable", "resources/worldmapamericaunreachable.json");
    }};

    //default colours for the players
    public static final HashMap<String, Color> COLOURS = new HashMap<>() {{
        put("Red", new Color(255, 123, 144));
        put("Green", new Color(155, 234, 205));
        put("Blue", new Color(140, 186, 255));
        put("Yellow", new Color(255, 246, 122));
        put("Orange", new Color(255, 206, 178));
        put("Purple", new Color(238, 176, 255));
    }};

    private ArrayList<PlayerInfo> playersInfo;
    private int numberOfPlayers;

    private String mapPath;
    private String mapName;

    /**
     * Constructor for InitializeModel
     */
    public InitializeModel() {
        mapPath = AVAILABLE_MAPS.get(DEFAULT_MAP);
        initializeViews = new ArrayList<>();
        playersInfo = new ArrayList<>();
        for(int i = 0; i < MAX_NUMBER_PLAYERS; i++){
            playersInfo.add(new PlayerInfo("Player "+(i+1), ((Color)COLOURS.values().toArray()[i]), false));
        }
        numberOfPlayers = MIN_NUMBER_PLAYERS;
    }

    /**
     * add the view for InitializeModel
     *
     * @param iv the views that will listen to initializeviews
     */
    public void addInitializeView(InitializeView iv) {
        initializeViews.add(iv);
    }

    /**
     * set the number of players chosen by the user
     *
     * @param numberOfPlayers The number of players in game
     */
    public void setPlayerNumbers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        for (InitializeView iv : initializeViews)
            iv.handleInitializeUpdate(new InitializeEvent(this, numberOfPlayers, null));
    }

    /**
     * This method sets the player name
     *
     * @param playerNumber The player Number
     * @param playerName The players name
     */
    public void setPlayerName(int playerNumber, String playerName) {
        playersInfo.get(playerNumber - 1).setName(playerName);
    }

    /**
     * set a players color
     *
     * @param playerNumber The players number
     * @param playerColour The players colour
     */
    public void setPlayerColour(int playerNumber, String playerColour) {
        playersInfo.get(playerNumber - 1).setColor(COLOURS.get(playerColour));
    }

    /**
     * set if a player is an AI
     *
     * @param playerNumber The players number
     * @param playerIsAI boolean value if a player is an AI or not
     */
    public void setPlayerisAI(int playerNumber, boolean playerIsAI) {
        playersInfo.get(playerNumber - 1).setAI(playerIsAI);
    }

    /**
     * get the chosen number of players
     *
     * @return int The total number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Getter method for each player that is ai
     *
     * @return ArrayList<boolean>
     */
    public ArrayList<Boolean> getPlayersIsAI() {
        ArrayList<Boolean> playersIsAI = new ArrayList<>();
        for (PlayerInfo playerInfo : playersInfo) {
            playersIsAI.add(playerInfo.isAI());
        }
        return new ArrayList<>(playersIsAI.subList(0, numberOfPlayers));
    }

    /**
     * get each player colour and if multiple or not chosen choose for the player
     *
     * @return ArrayList<Color> The colours selected for each player
     */
    public ArrayList<Color> getPlayersColours() {
        ArrayList<Color> playersColours = new ArrayList<>();
        for (PlayerInfo playerInfo : playersInfo) {
            if (!playersColours.contains(playerInfo.getColor())) {
                playersColours.add(playerInfo.getColor());
            } else {
                playersColours.add(null);
            }
        }

        for (int i = 0; i < playersColours.size(); i++) {
            if (playersColours.get(i) == null) {
                for (Color colour : COLOURS.values()) {
                    if (!playersColours.contains(colour)) {
                        playersColours.set(i, colour);
                        break;
                    }
                }
            }
        }
        return new ArrayList<>(playersColours.subList(0, numberOfPlayers));
    }

    /**
     * get each player name and if multiple add an identifier if not chosen name player Player. This method
     * will create automatic names for players if not specified in init setup.
     *
     * @return ArrayList<String> Returns an arraylist of the names of the players
     */
    public ArrayList<String> getNamesOfPlayers() {
        ArrayList<String> namesOfPlayers = new ArrayList<>();
        for (PlayerInfo playerInfo : playersInfo) {
            if (playerInfo.getName() == null) {
                namesOfPlayers.add("Player");
            } else
                namesOfPlayers.add(playerInfo.getName());

        }

        for (String playerName : namesOfPlayers) {
            ArrayList<Integer> positionOfSame = new ArrayList<>();

            for (int i = 0; i < namesOfPlayers.size(); i++) {
                if (namesOfPlayers.get(i).equals(playerName)) {
                    positionOfSame.add(i);
                }
            }

            if (positionOfSame.size() > 1) {
                for (int i = 0; i < positionOfSame.size(); i++) {
                    namesOfPlayers.set(positionOfSame.get(i), namesOfPlayers.get(positionOfSame.get(i)) + " " + (i + 1));
                }
            }
        }
        return new ArrayList<>(namesOfPlayers.subList(0, numberOfPlayers));
    }

    /**
     * Get the path of the currently selected map
     *
     * @return path of the selected map
     */
    public String getMapPath() {
        return mapPath;
    }

    /**
     * Method sets the current map name
     * @param mapName The String that contains the name of map played
     */
    public void setMapName(String mapName){
        this.mapName = mapName;
    }

    /**
     * Returns the name of the current map
     * @return The current map that is being played name
     */
    public String getMapName(){
        return mapName;
    }

    /**
     * Set the mapPath to the path of a given map name
     *
     * @param map Map name to set path to
     */
    public void setMapPath(String map) {
        this.mapPath = AVAILABLE_MAPS.get(map);
    }


    /**
     * this class is for the chosen info of a player
     *
     * @author Thomas
     */
    class PlayerInfo {

        private String name;
        private Color color;
        private boolean isAI;

        /**
         * constructor for PlayerInfo
         *
         * @param name
         * @param color
         * @param isAI
         */
        public PlayerInfo(String name, Color color, boolean isAI) {
            this.color = color;
            this.name = name;
            this.isAI = isAI;
        }

        /**
         * get the name of the player
         *
         * @return Returns the name of the player
         */
        public String getName() {
            return name;
        }

        /**
         * set the name of the player
         *
         * @param name Sets the name of player
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * get the colour of the player
         *
         * @return colour of player
         */
        public Color getColor() {
            return color;
        }

        /**
         * set the colour of the player
         *
         * @param color colour of player to be set
         */
        public void setColor(Color color) {
            this.color = color;
        }

        /**
         * Checks if a player is an AI or not
         * @return Boolean of true if AI and false otherwise
         */
        public boolean isAI() {
            return isAI;
        }

        /**
         * If a player is an AI this sets the boolean to true if an AI
         * @param AI True if player is AI and false otherwise
         */
        public void setAI(boolean AI) {
            isAI = AI;
        }
    }
}
