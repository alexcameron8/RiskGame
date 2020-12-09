package Main;

import Main.IntializeFrame.InitializeModel;
import Map.Exceptions.TerritoryHasNoNeighbourException;
import Map.Exceptions.TerritoryIsDisconnectedException;
import Map.Map;
import Player.AI.AIEasy;
import Player.Player;
import Map.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
/**
 * This class is the risk model which plays the risk game
 */
public class RiskModel implements Serializable {

    public static final Color BACKGROUND =  new Color(163,214,255);
    public static final int MAX_NUMBER_PLAYERS = 6;
    public static final int MIN_NUMBER_PLAYERS = 2;
    @Expose
    private ArrayList<Player> players;
    @Expose
    private int activePlayerID;
    private Map map = null;
    private List<RiskViewListener> riskViewListeners;
    private Player winner = null;
    private Player eliminatedPlayer = null;
    @Expose
    private String currentMap;

    /**
     * This constructor initializes the world map, the list of players and creates a list of listeners
     */
    public RiskModel(){
        players = new ArrayList<Player>();
        activePlayerID = 0;
        riskViewListeners = new ArrayList<>();
    }

    /**
     * Load a map by its path
     * @param mapPath Path of the map to load
     */
    public void loadMap(String mapPath) throws TerritoryHasNoNeighbourException, TerritoryIsDisconnectedException {
        MapImport mapImport = new MapImport(getClass().getResourceAsStream(mapPath));
        map = mapImport.getMap();
    }

    /**
     * Set the current map name to the map that was loaded
     * @param mapName The name of the map
     */
    public void setCurrentMap(String mapName){
        if(mapName!=null && mapName!="") {
            currentMap = mapName;
        }
    }

    /**
     * Adds a risk view listener to a list of listeners
     * @param riskViewListener the view that is added to the list of listeners
     */
    public void addRiskViewListeners(RiskViewListener riskViewListener){
        riskViewListeners.add(riskViewListener);
    }

    /**
     * Removes a risk view listener from a list of listeners
     * @param riskViewListener the view that is being removed
     */
    public void removeRiskViewListeners(RiskViewListener riskViewListener){
        riskViewListeners.remove(riskViewListener);
    }

    /**
     * This method creates a new game of Risk
     * @return a new Risk Model
     */
    public RiskModel newGame(){
        return new RiskModel();
    }

    /**
     * This method is used to advance the turns in the auto setup phase.
     */
    public void advanceAutoTurn() {
        if (activePlayerID + 1 < players.size()) {
            activePlayerID++;
        } else {
            activePlayerID = 0;
        }
    }

    /**
     * This method creates a new Turn instance and changes which player is currently playing the game.
     */
    public void advanceTurn(){
        //checks if a player has won or a player is eliminated.
        if(getActivePlayer().isTurnComplete()){
            for(Player player: players){
                if(player.getListOfTerritories().size()==0){ //there is a winner of eliminated player because someone does not own any territories
                    if(hasWinner()){
                        return;
                    }
                    hasElimination(player);
                    break;
                }
            }
        }

        if(activePlayerID + 1 < players.size()) {
            activePlayerID++;
            getActivePlayer().getReinforcement();
            getActivePlayer().advanceTurn();
        } else {
            activePlayerID = 0;
            getActivePlayer().getReinforcement();
            getActivePlayer().advanceTurn();
        }
        //updates the RiskViewListeners
        for(RiskViewListener riskViewListener : riskViewListeners){
            riskViewListener.handleTurnUpdate(new RiskEvent (this,activePlayerID,players,winner,eliminatedPlayer));
        }
        //if a player was eliminated last round clear the player before continuing
        if(eliminatedPlayer !=null){
            eliminatedPlayer = null;
        }
    }

    /**
     * Checks if there is a winner when the advance turn checks if there are any players iwht 0 territories
     * @return True if there is a winner and false otherwise
     */
    public boolean hasWinner() {
        if (players.size() == 2) { //if only 2 players left
            winner = getActivePlayer();
            for (RiskViewListener riskViewListener : riskViewListeners) { //update listeners
                riskViewListener.handleTurnUpdate(new RiskEvent(this, activePlayerID, players, winner, eliminatedPlayer));
            }
            return true;
        }
        return false;
    }

    /**
     * This method checks if a player has been eliminated and removes them from the list of players
     * if a player has been eliminated
     * @param player The player that is being check to see if they are eliminated
     */
    public void hasElimination(Player player){
        eliminatedPlayer = player;
        if (players.indexOf(player) <= activePlayerID) {
            activePlayerID--;
        }
        players.remove(eliminatedPlayer);
    }

    /**
     * This method gets the current player whos turn it is.
     * @return The current player who's turn it is.
     */
    public Player getActivePlayer() {
        if (players.size() > 0) {
            return players.get(activePlayerID);
        }
        return null;
    }

    /**
     * This method returns the list of players in the game.
     * @return the list of players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * This method adds a new player to the game of Risk.
     * @param newPlayer the new player being added to the game
     */
    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }

    /**
     * This method returns the name of the specified player
     * @param name the name of the player
     * @return the specified player
     */
    public Player getPlayerByName(String name){
        for (Player player: players) {
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    /**
     * This returns the map in the game.
     *
     * @return The map of game of Risk
     */
    public Map getMap(){
        return this.map;
    }

    /**
     * Getter method returning number of troops each player has at beginning of game based on how many players
     * there are.
     * @return The number of troops each player has
     */
    private int getNumberOfInitialTroops(){
        int lowest = (int) Math.floor(3*map.getTerritories().size()/MAX_NUMBER_PLAYERS/10)*10;
        return lowest+5*(MAX_NUMBER_PLAYERS-players.size());
    }

    /**
     * Getter method for getting active player ID
     * @return the active players ID
     */
    public int getActivePlayerID() {
        return activePlayerID;
    }

    /**
     * This method is the auto-setup functionality of Risk where the players are
     */
    public void assignTroopsRandom(){
        ArrayList<Territory> territories = map.getTerritories();
        Collections.shuffle(territories);

        for(Territory terr: territories){
            terr.addSoldiers(1);
            players.get(activePlayerID).addTerritory(terr);
            advanceAutoTurn();

        }

        Random randomGenerator = new Random();

        for (Player player: players) {
            int numTerritories = player.getListOfTerritories().size();

            for(int i = 0; i < getNumberOfInitialTroops()-numTerritories; i++){
                player.getListOfTerritories().get(randomGenerator.nextInt(numTerritories)).addSoldiers(1);
            }
        }

    }

    /**
     * This methods saves the current Risk Game that is in current play. The players, map name and
     * who's current turn it is to a JSON file.
     *
     * @param path The file to where the JSON game file is saved to.
     */
    public void save(String path) {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.setPrettyPrinting().create();
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(gson.toJson(this));
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method loads a JSON file to resume the loaded game into the RiskView.
     *
     * @param fileName The file that is being loaded
     * @param rv The RiskView that this file is being loaded into
     */
    public void load(String fileName, RiskView rv){
        try {
            File f = new File(fileName);
            populate(new FileInputStream(f), rv);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * This method is called when loading a file into a game and it populates the RiskModel with the required data to be able
     * to resume the saved game back into it's original state. The current active player, players (including their territories and
     * continents they own) and the map name is recreated into the neccessary objects.
     *
     * @param path The file being read from
     * @param rv The RiskView in which the file is being loaded to
     */
    private void populate(FileInputStream path, RiskView rv) {
        Gson gson = new Gson();
        RiskModelImportJSONModel riskModelImportJSONModel = null;
        JsonReader reader = new JsonReader(new InputStreamReader(path));
        riskModelImportJSONModel = gson.fromJson(reader, RiskModelImportJSONModel.class);
        activePlayerID = riskModelImportJSONModel.getActivePlayerID();
        currentMap = riskModelImportJSONModel.getCurrentMap();
        try {
            loadMap(InitializeModel.AVAILABLE_MAPS.get(currentMap));
        } catch (TerritoryHasNoNeighbourException e) {
            e.printStackTrace();
        } catch (TerritoryIsDisconnectedException e) {
            e.printStackTrace();
        }
        for(RiskModelPlayer rmp: riskModelImportJSONModel.getPlayers()){
            if(rmp.isAi()){
                players.add(new AIEasy(rmp.getName(), new Color(rmp.getPlayerColorValue()),rv));
            }
            else{
                players.add(new Player(rmp.getName(), new Color(rmp.getPlayerColorValue()),rv));
            }
            for(RiskModelTerritory rmt: rmp.getListOfTerritories()){
                players.get(players.size()-1).addTerritory(map.getTerritory(rmt.getName()));
                map.getTerritory(rmt.getName()).setSoldiers(rmt.getSoldiers());
            }
        }
    }
    /**
     * This method plays the game and initializes the setup of the game.
     */
    public void randomPlay(){
        Random r = new Random();

        activePlayerID = r.nextInt(players.size());
        assignTroopsRandom();

        getActivePlayer().getReinforcement();
        getActivePlayer().advanceTurn();
    }

    /**
     * This method plays the game and initializes the setup of the game.
     */
    public void loadPlay(){
        getActivePlayer().advanceTurn();
    }
}
