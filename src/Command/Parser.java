package Command;

import java.util.ArrayList;
import java.util.Scanner;

import Command.Validators.*;
import Main.Risk;
import Main.GameState;

public class Parser {
    private Scanner reader;
    private Risk game;

    private ValidCommands commandValidator;

    private static final String MAIN_MENU_PROMPT = "Main Menu >";
    private static final String NEW_GAME_PROMPT = "New Game >";
    private static final String IN_GAME_PROMPT = "Risk Game >";
    private static final String QUIT_PROMPT = "Quit >";

    public Parser(Risk game){
        this.game = game;
        reader = new Scanner(System.in);
    }

    public Command getCommand(){
        String inputLine;
        String prompt = ">";
        GameState state = game.getState();
        String command = null;
        ArrayList<String> arguments = new ArrayList<>();

        if(state == GameState.MAIN_MENU){
            prompt = MAIN_MENU_PROMPT;
            commandValidator = new ValidMenuCommands();
        } else if(state == GameState.NEW_GAME_SETTINGS){
            prompt = NEW_GAME_PROMPT;
            commandValidator = new ValidCreateGameCommands();
        } else if(state == GameState.IN_GAME){
            prompt = IN_GAME_PROMPT;
            commandValidator = new ValidGameCommands();
        } else if(state == GameState.QUIT){
            prompt = QUIT_PROMPT;
            commandValidator = new ValidQuitCommands();
        }

        System.out.print(prompt);

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            command = tokenizer.next();
            while(tokenizer.hasNext()){
                arguments.add(tokenizer.next());
            }
        }

        if(commandValidator.isCommand(command)){
            return new Command(command, arguments);
        } else {
            return new Command(null, null);
        }

    }

}
