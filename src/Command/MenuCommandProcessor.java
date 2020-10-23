package Command;

import Main.GameState;
import Main.Risk;

public class MenuCommandProcessor extends CommandProcessor{

    public MenuCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    public void processCommand() {
        String commandWord = command.getCommand();

        if(commandWord.equals("start")){
            game.setState(GameState.NEW_GAME_SETTINGS);
            System.out.println("Starting Game!");
        } else if(commandWord.equals("quit")){
            game.setState(GameState.QUIT);
            System.out.println("Quitting");
        }
    }
}
