package Command.Validators;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract class used by command validators.
 *
 * @author Benjamin Munro
 */
public abstract class ValidCommands {
    /**
     * Valid commands.
     * String for command name
     * Integer for number of expected arguments
     */
    protected HashMap<String, Integer> validCommands;

    /**
     * Create new ValidCommands
     */
    ValidCommands(){
        this.validCommands = new HashMap<>();
    }

    /**
     * Check if a command is valid.
     * @param command List of string elements to check if is a valid command
     * @return true if command is valid, false otherwise
     */
    public boolean isCommand(ArrayList<String> command){
        if(!command.isEmpty()){
            if(validCommands.containsKey(command.get(0))){
                if(command.size()-1 == validCommands.get(command.get(0))){
                    return true;
                }
            }
        }
        return false;
    };

    /**
     * Gets list of valid commands
     * @return List of valid commands.
     */
    public HashMap<String, Integer> getCommands(){
        return validCommands;
    };
}
