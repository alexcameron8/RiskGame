package Main;
import Command.*;
import Command.Processors.*;
import Map.Territory;
import Map.WorldMap;
import Player.Player;

import Main.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This is the Risk Class.
 */
public class Risk {
    private static GameState state;
    private Parser parser;
    private ArrayList<Player> players;
    private int activePlayerID;
    private WorldMap map;
    private Turn currentTurn;


    /**
     * This class creates a new Risk game.
     */
    Risk(){
        parser = new Parser(this);
        players = new ArrayList<Player>();
        activePlayerID = 0;
        map = new WorldMap();
    }

    /**
     * the printMenu method prints all possible commands when in the opening menu when launching the game.
     */
    private void printMenu(){
        System.out.println("Welcome to Main.Risk: Global Domination!");
        System.out.println("To get started, please select and option:");
        System.out.println("(start) Start new game.");
        System.out.println("(help) Display help menu. Available anywhere.");
        System.out.println("(quit) Quit");
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
            if(players.get(activePlayerID).getListOfTerritories().size()==0){
                //They have no territories, therefore have been eliminated
                System.out.println(getActivePlayer().getName() + " has no more continents and has been eliminated from the game!");
                if (activePlayerID + 1 < players.size()) {
                    players.remove(activePlayerID);
                    activePlayerID++;
                    currentTurn = new Turn(players.get(activePlayerID));
                }else{
                    players.remove(activePlayerID);
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
    public WorldMap getMap(){
        return this.map;
    }

    /**
     * This method processes all potential commands entered when trying to perform actions in Risk.
     * @param command The command being processed
     */
    private void processCommand(Command command){
        CommandProcessor cp = null;
        if(!command.isValid()){
            System.out.println("Invalid command or wrong number of arguments. Type the command 'help' for list of valid commands.");
            return;
        }

        if(state == GameState.MAIN_MENU){
            cp = new MenuCommandProcessor(this, command);
        } else if(state == GameState.NEW_GAME_SETTINGS){
            cp = new CreateGameCommandProcessor(this, command);
        } else if(state == GameState.IN_GAME){
            cp = new GameCommandProcessor(this, command);
        }

        if(cp != null){
            cp.processCommand();
        }
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
        ArrayList<Territory> territories = map.getWorldMap().getTerritories();
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
        printMenu();
        while(state != GameState.QUIT){
            Command command = parser.getCommand();
            processCommand(command);

            if(state == GameState.GENERATE_GAME){

                Random r = new Random();

                activePlayerID = r.nextInt(players.size());
                assignTroopsRandom();
                currentTurn = new Turn(players.get(activePlayerID));

                setState(GameState.IN_GAME);
            }

        }
    }

    /**
     * Run a game of Risk
     * @param args Command Args
     */
    public static void main(String[] args) {
        Risk game = new Risk();
        game.play();

    }
}
