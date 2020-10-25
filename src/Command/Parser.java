package Command;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Command.Validators.*;
import Main.Risk;
import Main.GameState;

/**
 * Command input processing and parsing.
 *
 * @author Benjamin Munro
 */
public class Parser {
    private Scanner reader;
    private Risk game;

    private ValidCommands commandValidator;

    private static final String MAIN_MENU_PROMPT = "Main Menu";
    private static final String NEW_GAME_PROMPT = "New Game";
    private static final String IN_GAME_PROMPT = "Risk Game";

    /**
     * Creates a new Parser to handle a command.
     * @param game Risk game used to reference state of current game.
     */
    public Parser(Risk game){
        this.game = game;
        reader = new Scanner(System.in);
    }

    /**
     * Prompt user for input and retrieve the parsed command.
     * @return Command entered by the user.
     */
    public Command getCommand(){
        String inputLine;
        String prompt = ">";
        GameState state = game.getState();
        ArrayList<String> parsedCommand = new ArrayList<>();

        if(state == GameState.MAIN_MENU){
            prompt = MAIN_MENU_PROMPT;
            commandValidator = new ValidMenuCommands();
        } else if(state == GameState.NEW_GAME_SETTINGS){
            prompt = NEW_GAME_PROMPT;
            commandValidator = new ValidCreateGameCommands();
        } else if(state == GameState.IN_GAME){
            prompt = IN_GAME_PROMPT + " (" + game.getActivePlayer().getName() + ")";
            commandValidator = new ValidGameCommands();
        }

        System.out.print(prompt + "> ");

        inputLine = reader.nextLine();

        /*
        Referenced
        https://stackoverflow.com/questions/7804335/split-string-on-spaces-in-java-except-if-between-quotes-i-e-treat-hello-wor
        For regex pattern
         */
        Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(inputLine);
        while(matcher.find()){
            parsedCommand.add(matcher.group(1).replace("\"", ""));
        }

        if(commandValidator.isCommand(parsedCommand)){
            String command = parsedCommand.get(0);
            parsedCommand.remove(0);
            return new Command(command, parsedCommand);
        } else {
            return new Command(null, null);
        }

    }

}
