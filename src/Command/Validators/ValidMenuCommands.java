package Command.Validators;

import java.util.ArrayList;

public class ValidMenuCommands extends ValidCommands{

    public ValidMenuCommands(){
        super();
        validCommands.put("start", 0);
        validCommands.put("help", 0);
        validCommands.put("quit", 0);
    }

}
