package Command.Validators;

import Command.Command;

import java.util.ArrayList;

/**
 * ValidCommands is an interface to be used by other command validators
 */

public interface ValidCommands {
    public boolean isCommand(String command);
    public ArrayList<String> getCommands();
}
