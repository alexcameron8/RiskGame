package Command.Processors;

import Command.Command;
import Main.GameState;
import Main.Risk;
import Map.Territory;
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
        } else if(commandWord.equals("help")) {
            System.out.println("Help:");
            System.out.println("You are currently in a game of Risk: Global Domination.");
            System.out.println("Available Commands:");
            System.out.println("(players) List player names.");
            System.out.println("(countries) List countries occupied by the active player.");
            System.out.println("(turn) Advance to next players turn.");
            System.out.println("(quit) Quits the game.");

        } else if(commandWord.equals("quit")){
            game.setState(GameState.QUIT);
        } else if(commandWord.equals("turn")){
            game.advanceTurn();
        } else if(commandWord.equals("countries")){
            
            System.out.println(game.getActivePlayer().getName() 
                    + " occupies " + game.getActivePlayer().getListOfTerritories().size()
                    + " territories:");
            
            for(Territory terr: game.getActivePlayer().getListOfTerritories()){
                System.out.println(terr.getName() + " - " + terr.getSoldiers());
            }
            System.out.print("\n");

        }

    }
}
