package Command;

import java.util.ArrayList;

public interface ValidCommands {
    public boolean isCommand(String command);
    public ArrayList<String> getCommands();
}
