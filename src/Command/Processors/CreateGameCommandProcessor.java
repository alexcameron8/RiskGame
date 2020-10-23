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
        } else if(commandWord.equals("help")){
            System.out.println("Help:");
            System.out.println("You are currently creating a new game of Risk: Global Domination.");
            System.out.println("Available Commands:");
            System.out.println("(two <PLAYER 1 NAME> <PLAYER 2 NAME>) Create game with 2 players.");
            System.out.println("(three <PLAYER 1 NAME> <PLAYER 2 NAME> <PLAYER 3 NAME>) Create game with 3 players.");
            System.out.println("(four <PLAYER 1 NAME> <PLAYER 2 NAME> <PLAYER 3 NAME> <PLAYER 4 NAME>) Create game with 4 players.");
            System.out.println("(five <PLAYER 1 NAME> <PLAYER 2 NAME> <PLAYER 3 NAME> <PLAYER 4 NAME> <PLAYER 5 NAME>) Create game with 5 players.");
            System.out.println("(six <PLAYER 1 NAME> <PLAYER 2 NAME> <PLAYER 3 NAME> <PLAYER 4 NAME> <PLAYER 5 NAME> <PLAYER 6 NAME>) Create game with 6 players.");
            System.out.println("(help) Info for available commands.");
            System.out.println("(quit) Quits the game.");

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