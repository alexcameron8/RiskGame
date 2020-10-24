package Command.Validators;

import java.util.ArrayList;

public class ValidGameCommands implements ValidCommands{
    private static ArrayList<String> validCommands;

    public ValidGameCommands(){
        validCommands = new ArrayList<String>();
        validCommands.add("home");
        validCommands.add("players");
        validCommands.add("help");
        validCommands.add("turn");
        validCommands.add("reinforcements");
        validCommands.add("place");
        validCommands.add("countries");
        validCommands.add("neighbors");
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
