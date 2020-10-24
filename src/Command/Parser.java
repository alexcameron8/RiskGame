package Command;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Command.Validators.*;
import Main.Risk;
import Main.GameState;

public class Parser {
    private Scanner reader;
    private Risk game;

    private ValidCommands commandValidator;

    private static final String MAIN_MENU_PROMPT = "Main Menu";
    private static final String NEW_GAME_PROMPT = "New Game";
    private static final String IN_GAME_PROMPT = "Risk Game";
    private static final String QUIT_PROMPT = "Quit";

    public Parser(Risk game){
        this.game = game;
        reader = new Scanner(System.in);
    }

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
        } else if(state == GameState.QUIT){
            prompt = QUIT_PROMPT;
            commandValidator = new ValidQuitCommands();
        }

        System.out.print(prompt + "> ");

        inputLine = reader.nextLine();

        Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(inputLine);
        while(matcher.find()){
            parsedCommand.add(matcher.group(1).replace("\"", ""));
        }

        if(!parsedCommand.isEmpty() && commandValidator.isCommand(parsedCommand.get(0))){
            String command = parsedCommand.get(0);
            parsedCommand.remove(0);
            return new Command(command, parsedCommand);
        } else {
            return new Command(null, null);
        }

    }

}
