package Command;

import java.util.ArrayList;

public class Command {
    private final String command;
    private final ArrayList<String> arguments;

    Command(String w1, ArrayList<String> args){
        this.command = w1;
        this.arguments = args;
    }

    public boolean isValid(){
        return command == null ? false : true;
    }

    public String getCommand(){
        return command;
    }

    public ArrayList<String> getArguments(){
        return arguments;
    }

    public String getArgument(int index){
        return arguments.get(index);
    }
}
