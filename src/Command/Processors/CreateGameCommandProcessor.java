package Command.Processors;

import Command.Command;
import Main.GameState;
import Main.Risk;
import Player.Player;

public class CreateGameCommandProcessor extends CommandProcessor{
    public CreateGameCommandProcessor(Risk game, Command command) {
        super(game, command);
    }

    public void processCommand() {
        String commandWord = command.getCommand();
        int players = 0;

        if(commandWord.equals("two")){
            players = 2;
        } else if(commandWord.equals("three")){
            players = 3;
        } else if(commandWord.equals("four")){
            players = 4;
        } else if(commandWord.equals("five")){
            players = 5;
        } else if(commandWord.equals("six")){
            players = 6;
        } else if(commandWord.equals("home")){
            game.setState(GameState.MAIN_MENU);
            return;
        } else if(commandWord.equals("quit")){
            game.setState(GameState.QUIT);
            return;
        }

        for(int i = 0; i < 2; i++){
            game.addPlayer(new Player(command.getArgument(i)));
        }
        System.out.println("Playing with " + players + " players.");
        game.setState(GameState.IN_GAME);

        // ADD COMMANDS HERE
    }
}
