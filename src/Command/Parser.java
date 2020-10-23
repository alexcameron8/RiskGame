package Command;

import java.util.ArrayList;
import java.util.Scanner;
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
        String argument = null;

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
            if(tokenizer.hasNext()){
                argument = tokenizer.next();
                // Note rest of line is ignored
            }
        }

        if(state == GameState.MAIN_MENU && commandValidator.isCommand(command)){
            return new Command(command, argument);
        } else {
            return new Command(null, null);
        }

    }


}
