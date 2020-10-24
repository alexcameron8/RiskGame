package Main;
import Command.*;
import Command.Processors.*;
import Player.Player;

import java.util.ArrayList;

public class Risk {
    private static GameState state;
    private Parser parser;

    private ArrayList<Player> players;

    Risk(){
        parser = new Parser(this);
        players = new ArrayList<Player>();
    }

    private void printMenu(){
        System.out.println("Welcome to Main.Risk: Global Domination!");
        System.out.println("To get started, please select and option:");
        System.out.println("(start) Start new game.");
        System.out.println("(help) Display help menu. Available anywhere.");
        System.out.println("(quit) Quit");
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
