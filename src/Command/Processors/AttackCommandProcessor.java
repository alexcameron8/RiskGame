package Command.Processors;

import Command.Command;
import Main.Risk;

public class AttackCommandProcessor extends CommandProcessor{

    public AttackCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    @Override
    public void processCommand() {
        String commandWord = command.getCommand();

        if(commandWord.equals("")){

        }

    }
}
