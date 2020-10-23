package Command;

import java.util.ArrayList;

public class ValidMenuCommands implements ValidCommands{
    private static ArrayList<String> validCommands;

    ValidMenuCommands(){
        validCommands = new ArrayList<String>();
        validCommands.add("start");
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
