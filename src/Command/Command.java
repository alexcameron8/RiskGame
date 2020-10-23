package Command;

public class Command {
    private final String command;
    private final String argument;

    Command(String w1, String w2){
        this.command = w1;
        this.argument = w2;
    }

    public boolean isValid(){
        return command == null ? false : true;
    }

    public String getCommand(){
        return command;
    }

    public String getArgument(){
        return argument;
    }
}
