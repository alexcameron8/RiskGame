package Command;

import java.util.Scanner;
import Main.Risk;
import Main.GameState;

public class Parser {
    private Scanner reader;
    private Risk game;

    public Parser(Risk game){
        this.game = game;
        reader = new Scanner(System.in);
    }

    public Command getCommand(){
        String inputLine;
        String prompt = ">";
        GameState state = game.getState();

        if(state == GameState.MAIN_MENU){
            prompt = "Main Menu >";
        } else if(state == GameState.NEW_GAME_SETTINGS){
            prompt = "New Game >";
        } else if(state == GameState.IN_GAME){
            prompt = "Risk Game >";
        } else if(state == GameState.QUIT){
            prompt = "Quit >";
        }

        System.out.print(prompt);

        inputLine = reader.nextLine();
    }


}
