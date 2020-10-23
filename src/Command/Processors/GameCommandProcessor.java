package Command.Processors;

import Command.Command;
import Main.GameState;
import Main.Risk;
import Player.Player;

public class GameCommandProcessor extends CommandProcessor{

    public GameCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    public void processCommand() {
        String commandWord = command.getCommand();

        if(commandWord.equals("players")){
            for(Player player: game.getPlayers()){
                System.out.print(player.getName() + " ");
            }
            System.out.print("\n");
        } else if(commandWord.equals("quit")){
            game.setState(GameState.QUIT);
        }

    }
}
