package Command.Processors;

import Command.Command;
import Main.GameState;
import Main.Risk;
import Map.Territory;
import Player.Player;

import Main.*;

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
            System.out.println("(reinforcements) Number of reinforcements the player has left to place.");
            System.out.println("(place <Number of Reinforcements> <Territory>) Places certain number of reinforcements in territory.");
            System.out.println("(turn) Advance to next players turn.");
            System.out.println("(neighbors <TERRITORY>) List the neighbors of a Territory.");
            System.out.println("(turn) Advance to next players turn.");
            System.out.println("(home) Return to main menu of the game.");
            System.out.println("(neighbors <TERRITORY>) List the neighbors of a Territory.");
            System.out.println("(attack <NUMBER OF TROOPS> <ATTACK TERRITORY> <DEFENDING TERRITORY>) Attack a Territory.");
            System.out.println("(quit) Quits the game.");

        } else if(commandWord.equals("quit")){
            game.setState(GameState.QUIT);
        } else if(commandWord.equals("turn")) {
            game.advanceTurn();
        } else if(commandWord.equals("reinforcements")){
                System.out.println(game.getActivePlayer().getName() + " has " + game.getActivePlayerTurn().getNumberOfReinforcements() + " reinforcements.");
        }else if(commandWord.equals("place")){
            int numOfReinforcements = Integer.parseInt(command.getArgument(0));
            String territoryName = command.getArgument(1);
            Territory territory = null;
            //Finds territory
            for(Territory findTerr: game.getMap().getWorldMap().getTerritories()) {
                if (findTerr.getName().equals(territoryName)) {
                    territory = findTerr;
                }
            }
            //if territory exists
            if(territory!=null) {
                //if activeplayer owns territory
                if (game.getActivePlayer().placeReinforcement(territory, numOfReinforcements)) {
                    game.getActivePlayer().placeReinforcement(territory, numOfReinforcements);
                    game.getActivePlayerTurn().setNumberOfReinforcements(game.getActivePlayerTurn().getNumberOfReinforcements() - numOfReinforcements);
                    System.out.println(game.getActivePlayer().getName() + " placed " + numOfReinforcements + " soldiers in " + territoryName);
                }
                }else{
                    System.out.println("This territory cannot be found. If territory is 2 words then use quotation marks e.g.\"South Africa\"");
                }
        } else if(commandWord.equals("countries")){
            
            System.out.println(game.getActivePlayer().getName() 
                    + " occupies " + game.getActivePlayer().getListOfTerritories().size()
                    + " territories:");
            
            for(Territory terr: game.getActivePlayer().getListOfTerritories()){
                System.out.println(terr.getName() + " - " + terr.getSoldiers());
            }
            System.out.print("\n");

        }  else if(commandWord.equals("neighbors")){
            String territory = command.getArgument(0);
            System.out.println("Neighbors of " + territory + ":");
            for(Territory findTerr: game.getMap().getWorldMap().getTerritories()){
                if(findTerr.getName().equals(territory)){
                    for(Territory terr: findTerr.getNeighbours()){
                        System.out.println(terr.getName());
                    }
                }
            }
        } else if(commandWord.equals("home")){
            game.setState(GameState.MAIN_MENU);
        } else if(commandWord.equals("attack")){
            int numTroops = Integer.parseInt(command.getArgument(0));
            String attackName = command.getArgument(1);
            String defendName = command.getArgument(2);

            Player attacker = game.getActivePlayer();
            Player defender = null;

            for(Player player: game.getPlayers()){
                if(player.hasTerritory(defendName)){
                    defender = player;
                    break;
                }
            }

            Territory attackTerritory = attacker.getTerritory(attackName);
            Territory defendTerritory = defender.getTerritory(defendName);

            attacker.attack(attackTerritory, defender, defendTerritory, numTroops);

        }

    }
}
