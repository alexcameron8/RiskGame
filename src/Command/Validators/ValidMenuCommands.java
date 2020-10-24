package Command.Validators;

import java.util.ArrayList;

/**
 * This is a class of all valid menu commands.
 */
public class ValidMenuCommands implements ValidCommands{
    private static ArrayList<String> validCommands;

    /**
     * The constructor returns an ArrayList of all the valid Strings pertaining to all valid commands accessible in the
     * menu.
     */
    public ValidMenuCommands(){
        validCommands = new ArrayList<String>();
        validCommands.add("start");
        validCommands.add("help");
        validCommands.add("quit");
    }

    @Override
    public boolean isCommand(String command) {
        for (String cmd: validCommands) {
            if(command.equals(cmd)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> getCommands() {
        return validCommands;
    }
}
