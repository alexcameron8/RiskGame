package Command.Processors;

import Command.Command;
import Main.GameState;
import Main.Risk;

public class GameCommandProcessor extends CommandProcessor{

    public GameCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    public void processCommand() {
        String commandWord = command.getCommand();

        // ADD COMMANDS HERE
    }
}
