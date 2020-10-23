package Command;

import Main.Risk;

public class QuitCommandProcessor extends CommandProcessor{
    public QuitCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    public void processCommand() {
        String commandWord = command.getCommand();

        // ADD COMMANDS HERE
    }
}
