package Command.Processors;
import Command.Command;
import Main.Risk;
import Main.GameState;

abstract public class CommandProcessor {
    protected Risk game;
    protected Command command;
    CommandProcessor(Risk game, Command command){
        this.game = game;
        this.command = command;
    }
    public abstract void processCommand();
}
