package Command;

import Main.Risk;

public class NewGameCommandProcessor extends CommandProcessor{
    public NewGameCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    public void processCommand() {
        String commandWord = command.getCommand();

        // ADD COMMANDS HERE
    }
}
