package Main;
import Command.*;

public class Risk {
    private static GameState state;
    private Parser parser;

    Risk(){
        parser = new Parser(this);
    }

    private void printMenu(){
        System.out.println("Welcome to Main.Risk: Global Domination!");
        System.out.println("To get started, please select and option:");
        System.out.println("1. Start new game.");
        System.out.println("2. Quit");
    }

    public GameState getState(){
        return state;
    }

    public void setState(GameState state){
        this.state = state;
    }

    private void processCommand(Command command){
        CommandProcessor cp = null;
        if(!command.isValid()){
            System.out.println("I'm not sure what you mean!");
        }

        if(state == GameState.MAIN_MENU){
            cp = new MenuCommandProcessor(this, command);
        } else if(state == GameState.NEW_GAME_SETTINGS){
            cp = new NewGameCommandProcessor(this, command);
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
