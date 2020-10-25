package Command;

import java.util.ArrayList;

/**
 * Class to represent a parsed command and its list of potential arguments.
 *
 * @author Benjamin Munro
 */
public class Command {
    private final String command;
    private final ArrayList<String> arguments;

    /**
     * Create a new command.
     * If a command is deemed invalid by a command validator, pass null as command.
     *
     * @param w1 Command entered by user. null if not validated
     * @param args List of string arguments after the entered command.
     */
    Command(String w1, ArrayList<String> args){
        this.command = w1;
        this.arguments = args;
    }

    /**
     * Checks if a command is valid.
     * @return true if valid, false otherwise.
     */
    public boolean isValid(){
        return command == null ? false : true;
    }

    /**
     * Return the string value of a command.
     * @return Command word.
     */
    public String getCommand(){
        return command;
    }

    /**
     * Returns the list of arguments for the command.
     * @return Command arguments.
     */
    public ArrayList<String> getArguments(){
        return arguments;
    }

    /**
     * Returns the argument at a specific position of the command.
     * @param index Index of the command argument to retrieve.
     * @return Command argument.
     */
    public String getArgument(int index){
        return arguments.get(index);
    }
}
