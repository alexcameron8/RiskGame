package Main;
import Command.*;
import Command.Processors.*;
import Map.Territory;
import Map.WorldMap;
import Player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Risk {
    private static GameState state;
    private Parser parser;
    private ArrayList<Player> players;
    private int activePlayerID;
    private WorldMap map;


    Risk(){
        parser = new Parser(this);
        players = new ArrayList<Player>();
        activePlayerID = 0;
        map = new WorldMap();
    }

    private void printMenu(){
        System.out.println("Welcome to Main.Risk: Global Domination!");
        System.out.println("To get started, please select and option:");
        System.out.println("(start) Start new game.");
        System.out.println("(help) Display help menu. Available anywhere.");
        System.out.println("(quit) Quit");
    }

    public void advanceTurn(){
        if(activePlayerID + 1 < players.size()){
            activePlayerID++;
        } else {
            activePlayerID = 0;
        }
    }

    public void resetTurns(){
        activePlayerID = 0;
    }

    public Player getActivePlayer(){
        return players.get(activePlayerID);
    }

    public GameState getState(){
        return state;
    }

    public void setState(GameState state){
        this.state = state;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }

    public Player getPlayerByName(String name){
        for (Player player: players) {
            if(player.getName().equals(name)){
                return player;
            }
        }

        return null;
    }

    public WorldMap getMap(){
        return this.map;
    }

    private void processCommand(Command command){
        CommandProcessor cp = null;
        if(!command.isValid()){
            System.out.println("I'm not sure what you mean! Type the command 'help' for list of valid commands.");
            return;
        }

        if(state == GameState.MAIN_MENU){
            cp = new MenuCommandProcessor(this, command);
        } else if(state == GameState.NEW_GAME_SETTINGS){
            cp = new CreateGameCommandProcessor(this, command);
        } else if(state == GameState.IN_GAME){
            cp = new GameCommandProcessor(this, command);
        } else if(state == GameState.QUIT){
            cp = new QuitCommandProcessor(this, command);
        }

        if(cp != null){
            cp.processCommand();
        }
    }

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

    public void assignTroopsRandom(){
        ArrayList<Territory> territories = map.getWorldMap().getTerritories();
        Collections.shuffle(territories);

        for(Territory terr: territories){
            terr.addSoldiers(1);
            players.get(activePlayerID).addTerritory(terr);
            advanceTurn();
        }

        Random randomGenerator = new Random();

        for (Player player: players) {
            int numTerritories = player.getListOfTerritories().size();

            for(int i = 0; i < getNumberOfInitialTroops()-numTerritories; i++){
                player.getListOfTerritories().get(randomGenerator.nextInt(numTerritories)).addSoldiers(1);
            }
        }

    }

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
                setState(GameState.IN_GAME);
            }

        }
    }

    public static void main(String[] args) {
        Risk game = new Risk();
        game.play();

    }
}
