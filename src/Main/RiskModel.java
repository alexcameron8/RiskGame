package Main;

import Map.Map;
import Player.Player;
import Map.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RiskModel {

    public static final Color BACKGROUND =  new Color(163,214,255);
    public static final int MAX_NUMBER_PLAYERS = 6;
    public static final int MIN_NUMBER_PLAYERS = 2;
    private ArrayList<Player> players;
    private int activePlayerID;
    private Turn currentTurn;
    private Map map;
    private List<RiskViewListener> riskViewListeners;
    private boolean turnComplete;
    private Player winner = null;
    private Player eliminatedPlayer = null;

    RiskModel(){
        MapImport mapImport = new MapImport("src/Map/worldmap.json");
        players = new ArrayList<Player>();
        activePlayerID = 0;
        map = mapImport.getMap();
        riskViewListeners = new ArrayList<>();
    }
    public void addRiskViewListeners(RiskViewListener riskViewListener){
        riskViewListeners.add(riskViewListener);
    }
    public void removeRiskViewListeners(RiskViewListener riskViewListener){
        riskViewListeners.remove(riskViewListener);
    }

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

        if(currentTurn.isTurnComplete(getActivePlayer())){
            if(players.size() == 1){
                winner = getActivePlayer();

                System.out.println(players.get(0).getName() + " has won");
            }
            for(Player player: players){
                if(player.getListOfTerritories().size()==0){
                    eliminatedPlayer = player;
                    if(players.indexOf(player)<=activePlayerID){
                        activePlayerID--;
                    }
                    players.remove(player);
                    break;
                }
            }
            if (activePlayerID + 1 < players.size()) {
                activePlayerID++;
                currentTurn = new Turn(players.get(activePlayerID));
                turnComplete = true;
            } else {
                activePlayerID = 0;
                currentTurn = new Turn(players.get(0));
                turnComplete = true;
            }

        } else{
            turnComplete = false;
        }
        for(RiskViewListener riskViewListener : riskViewListeners){
            riskViewListener.handleTurnUpdate(new RiskEvent (this,activePlayerID,players, currentTurn,winner,eliminatedPlayer));
        }
        //if a player was eliminated last round clear the player before continuing
        if(eliminatedPlayer !=null){
            eliminatedPlayer = null;
        }
    }
    public boolean isTurnComplete(){
        return turnComplete;
    }
    /**
     * Reset the turns to the first player.
     */
    public void resetTurns(){
        activePlayerID = 0;
    }

    /**
     * This method accesses the currentTurn.
     * @return The current Turn
     */
    public Turn getActivePlayerTurn(){
        return currentTurn;

    }

    /**
     * This method gets the current player whos turn it is.
     * @return The current player who's turn it is.
     */
    public Player getActivePlayer(){
        return players.get(activePlayerID);
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
     * This method plays the game and initializes the setup of the game.
     */
    public void play(){
        Random r = new Random();

        activePlayerID = r.nextInt(players.size());
        assignTroopsRandom();
        currentTurn = new Turn(players.get(activePlayerID));
    }

}
