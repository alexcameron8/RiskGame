package Main;

import Player.Player;
import Map.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RiskModel {
    private static GameState state;
    private ArrayList<Player> players;
    private int activePlayerID;
    private Turn currentTurn;
    private Map map;

    RiskModel(){
        MapImport mapImport = new MapImport("src/Map/worldmap.json");
        players = new ArrayList<Player>();
        activePlayerID = 0;
        map = mapImport.getMap();
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
        if(currentTurn.isTurnComplete()){
            if(players.size() == 1){
                System.out.println(players.get(0).getName() + " has won");
                state = GameState.MAIN_MENU;
                return;
            }
            if(players.get(activePlayerID).getListOfTerritories().size()==0){
                //They have no territories, therefore have been eliminated
                players.remove(activePlayerID);
                if (activePlayerID + 1 < players.size()) {
                    activePlayerID++;
                    currentTurn = new Turn(players.get(activePlayerID));
                }else{
                    activePlayerID = 0;
                    currentTurn = new Turn(players.get(0));
                }
            }else {
                if (activePlayerID + 1 < players.size()) {
                    activePlayerID++;
                    currentTurn = new Turn(players.get(activePlayerID));
                } else {
                    activePlayerID = 0;
                    currentTurn = new Turn(players.get(0));
                }
            }
        } else{
            System.out.println(players.get(activePlayerID).getName() + " turn is not complete. There are " + players.get(activePlayerID).getReinforcement() + " soldiers left to place.");
        }
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
     * This method returns what state the game is (e.g IN_GAME , MAIN_MENU ...)
     * @return The state the game is in
     */
    public GameState getState(){
        return state;
    }

    /**
     * This method sets the state the game is in. (e.g IN_GAME, MAIN_MENU ..)
     * @param state the state to set the game
     */
    public void setState(GameState state){
        this.state = state;
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
        switch (players.size()){
            case 2:
                return 50;
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            case 6:
                return 20;
            default:
                return 0;
        }
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
     * This method plays the game and changes the game state and initializes the setup of the game.
     */
    public void play(){
        state = GameState.MAIN_MENU;
        while(state != GameState.QUIT){
            if(state == GameState.GENERATE_GAME){

                Random r = new Random();

                activePlayerID = r.nextInt(players.size());
                assignTroopsRandom();
                currentTurn = new Turn(players.get(activePlayerID));

                setState(GameState.IN_GAME);
            }

        }
    }

    public void addRiskView(RiskView riskView) {
    }
}
