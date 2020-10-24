package Command.Validators;

import java.util.ArrayList;

public class ValidQuitCommands extends ValidCommands{
    public ValidQuitCommands(){
        super();
        validCommands.put("yes", 0);
        validCommands.put("no", 0);
        validCommands.put("help", 0);
    }

}
