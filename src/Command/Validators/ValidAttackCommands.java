package Command.Validators;

import java.util.ArrayList;

public class ValidAttackCommands implements ValidCommands{
    private static ArrayList<String> validCommands;

    public ValidAttackCommands(){
        validCommands = new ArrayList<String>();
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
