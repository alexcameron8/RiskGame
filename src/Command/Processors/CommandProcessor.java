/**
 * CommandProcessor is an abstract class for other CommandProcessors.
 * A command processor is responsible for the execution of player commands.
 * Each "Stage" of the game, where different commands are valid have their own CommandProcessors.
 */

package Command.Processors;
import Command.Command;
import Main.Risk;

abstract public class CommandProcessor {
    protected Risk game;
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

    public abstract void processCommand();
}
