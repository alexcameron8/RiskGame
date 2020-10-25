package Command.Validators;

import Command.Command;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ValidCommands {
    protected HashMap<String, Integer> validCommands;

    ValidCommands(){
        this.validCommands = new HashMap<>();
    }

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

    public HashMap<String, Integer> getCommands(){
        return validCommands;
    };
}
