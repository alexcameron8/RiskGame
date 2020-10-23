package Command;

import java.util.ArrayList;

public class ValidQuitCommands implements ValidCommands{
    private static ArrayList<String> validCommands;

    ValidQuitCommands(){
        validCommands = new ArrayList<String>();
        validCommands.add("yes");
        validCommands.add("no");
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
