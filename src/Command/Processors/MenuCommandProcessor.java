/**
 * Command Processor used while on the main menu of the game.
 */
package Command.Processors;

import Command.Command;
import Main.GameState;
import Main.Risk;

/**
 * This class processes all commands entered in the main menu state.
 */
public class MenuCommandProcessor extends CommandProcessor{

    /**
     * Create a new main menu state command processor.
     *
     * @param game Game state to interact with.
     * @param command Command being executed by the player.
     */
    public MenuCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    /**
     * Given a command, process the command which is executed.
     */
    public void processCommand() {
        String commandWord = command.getCommand();

        if(commandWord.equals("start")){
            game.setState(GameState.NEW_GAME_SETTINGS);
            System.out.println("Starting Game! Type the command 'help' for details on starting a game.");
        } else if(commandWord.equals("help")){
            System.out.println("Help:");
            System.out.println("You are currently on the main menu of Risk: Global Domination.");
            System.out.println("Available Commands:");
            System.out.println("(start) Starts a new game.");
            System.out.println("(help) Info for available commands.");
            System.out.println("(quit) Quits the game.");

        } else if(commandWord.equals("quit")){
            game.setState(GameState.QUIT);
            System.out.println("Quitting");
        }
    }
}
