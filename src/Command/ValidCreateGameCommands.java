package Command;

import java.util.ArrayList;

public class ValidCreateGameCommands implements ValidCommands{
    private static ArrayList<String> validCommands;

    ValidCreateGameCommands(){
        validCommands = new ArrayList<String>();
        validCommands.add("home");
        validCommands.add("2");
        validCommands.add("3");
        validCommands.add("4");
        validCommands.add("5");
        validCommands.add("6");
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
