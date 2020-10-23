package Command.Processors;

import Command.Command;
import Main.GameState;
import Main.Risk;

public class CreateGameCommandProcessor extends CommandProcessor{
    public CreateGameCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    public void processCommand() {
        String commandWord = command.getCommand();

        if(commandWord.equals("two")){
            System.out.println("Playing with two players.");
        } else if(commandWord.equals("home")){
            game.setState(GameState.MAIN_MENU);
        }

        // ADD COMMANDS HERE
    }
}
