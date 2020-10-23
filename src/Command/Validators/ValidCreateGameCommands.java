package Command.Validators;

import java.util.ArrayList;

public class ValidCreateGameCommands implements ValidCommands{
    private static ArrayList<String> validCommands;

    public ValidCreateGameCommands(){
        validCommands = new ArrayList<String>();
        validCommands.add("home");
        validCommands.add("two");
        validCommands.add("three");
        validCommands.add("four");
        validCommands.add("five");
        validCommands.add("six");
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
