package Main;
import Command.*;
import Command.Processors.*;
import Player.Player;

import java.util.ArrayList;

public class Risk {
    private static GameState state;
    private Parser parser;
    private ArrayList<Player> players;
    private int activePlayerID;

    Risk(){
        parser = new Parser(this);
        players = new ArrayList<Player>();
        activePlayerID = 0;
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

    public Player getPlayer(String name){
        for (Player player: players) {
            if(player.getName().equals(name)){
                return player;
            }
        }

        return null;
    }

    private void processCommand(Command command){
        CommandProcessor cp = null;
        if(!command.isValid()){
            System.out.println("I'm not sure what you mean!");
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

    public void play(){
        state = GameState.MAIN_MENU;
        printMenu();
        while(state != GameState.QUIT){
            Command command = parser.getCommand();
            processCommand(command);
        }
    }

    public static void main(String[] args) {
        Risk game = new Risk();
        game.play();

    }
}
