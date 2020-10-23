package Main;
import Command.Parser;
import Command.Command;

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

    private void processCommand(Command command){
        if(!command.isValid()){
            System.out.println("I'm not sure what you mean!");
        }

        String commandWord = command.getCommand();

        if(commandWord.equals("start")){
            state = GameState.NEW_GAME_SETTINGS;
            System.out.println("Starting Game!");
        } else if(commandWord.equals("quit")){
            state = GameState.QUIT;
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
