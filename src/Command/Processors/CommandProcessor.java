package Command.Processors;
import Command.Command;
import Main.Risk;

/**
 * Command processors are responsible for matching commands with their functions.
 * Each "area" or "state" of the game has a different command processor.
 *
 * @author Benjamin Munro
 */
abstract public class CommandProcessor {
    /**
     * Game state to run command in.
     */
    protected Risk game;
    /**
     * Command to run.
     */
    protected Command command;

    /**
     * Create a new generic command processor.
     *
     * @param game Game state to interact with.
     * @param command Command being executed by the player.
     */
    CommandProcessor(Risk game, Command command){
        this.game = game;
        this.command = command;
    }

    /**
     * Handle processing the command.
     */
    public abstract void processCommand();
}
