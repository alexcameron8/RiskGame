package Player.AI;
import Attack.Attack;
import Main.IntializeFrame.InitializeModel;
import Main.RiskModel;
import Map.Continent;
import Map.Territory;
import Player.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AIEasy extends Player{
    /**
     * Constructor for a player. Gives them
     * a name and create a list of territories
     * and continents controlled by the player
     *
     * @param name        Player name.
     * @param playerColor
     */
    public AIEasy(String name, Color playerColor) {
        super(name, playerColor);
    }

    public void advanceTurn(){
        AIPlaceTroops();
        AIAttack();
        AIMoveTroop();
    }

    public void AIPlaceTroops(){
        Continent targetContinent = targetContinent();
        for(Territory terr: targetContinent.getTerritories()){
            if(!hasTerritory(terr)){
                Territory attackingTerr = findAttackingTerritory(terr);
                if(howManyToWin(terr, attackingTerr) < 0 && canPlaceReinforcement(attackingTerr, howManyToWin(terr, attackingTerr)*-1)){
                    placeReinforcement(attackingTerr, howManyToWin(terr, attackingTerr)*-1);
                }
            }
        }

    }

    public void AIMoveTroop(){
        for(Territory terr: getListOfTerritories()){
            if(hasUnownedNeighbour(terr) && !canTerritoryAttack(terr)){
                moveTroops(terr, findUnownedNeighbour(terr), terr.getSoldiers() - 1);
                return;
            }
        }
    }

    private Territory findUnownedNeighbour(Territory territory){
        for(Territory terr: territory.getNeighbours()){
            Territory unownedNeighbour = null;
            for(Territory neighbour : terr.getNeighbours()){
                if(!hasTerritory(neighbour)) {
                    unownedNeighbour = terr;

                }
            }
            if(unownedNeighbour != null){
                return unownedNeighbour;
            }
        }
        return null;
    }

    private boolean canTerritoryAttack(Territory territory){
        boolean hasUnownedNeighbour = false;
        for(Territory terr: territory.getNeighbours()){
            if(!hasTerritory(terr)) {
                hasUnownedNeighbour = true;

            }
        }
        return hasUnownedNeighbour;
    }

    private boolean hasUnownedNeighbour(Territory territory){
        for(Territory terr: territory.getNeighbours()){
            boolean hasUnownedNeighbour = false;
            for(Territory neighbour : terr.getNeighbours()){
                if(!hasTerritory(neighbour)) {
                    hasUnownedNeighbour = true;

                }
            }
            if(hasUnownedNeighbour){
                return true;
            }
        }
        return false;
    }

    public void AIAttack(){
        Object[] territories = getListOfTerritories().toArray();
        for(Object terr: territories){
            for(Territory neighbour : ((Territory) terr).getNeighbours()){
                if(!hasTerritory(neighbour)) {
                    if (canWinTerritory(neighbour, ((Territory) terr))) {
                        attack(((Territory) terr), neighbour.getOwner(), neighbour, ((Territory) terr).getSoldiers()-1);
                    }
                }
            }
        }
    }

    private Continent targetContinent(){
        HashMap<Continent, Integer> territoriesInContinents = new HashMap<>();
        for(Territory terr : getListOfTerritories()){
            territoriesInContinents.putIfAbsent(terr.getContinent(), 1);
            territoriesInContinents.computeIfPresent(terr.getContinent(), (k, v) -> v + 1);
        }

        Continent mostTerritories = getListOfTerritories().get(0).getContinent();

        for(Continent continent: territoriesInContinents.keySet()){
            if(territoriesInContinents.get(mostTerritories)/mostTerritories.getTerritories().size() != 1) {
                if(territoriesInContinents.get(continent)/continent.getTerritories().size()>territoriesInContinents.get(mostTerritories)/mostTerritories.getTerritories().size()){
                    mostTerritories = continent;
                }
            }
        }
        return mostTerritories;
    }

    private Territory findAttackingTerritory(Territory defendingTerritory){
        Territory attackingTerritory = null;
        for(Territory terr: defendingTerritory.getNeighbours()){
            if(hasTerritory(terr)){
                if(attackingTerritory == null){
                    attackingTerritory = terr;
                }
                else if(attackingTerritory.getSoldiers() < terr.getSoldiers()){
                    attackingTerritory = terr;
                }
            }
        }
        return attackingTerritory;
    }

    private int howManyToWin(Territory enemyTerritory, Territory aiTerritory){
        double requiredSoldiers = (5/4)*(enemyTerritory.getSoldiers()-2)+Math.exp(1+(1/enemyTerritory.getSoldiers()))+1;
        return (int) (aiTerritory.getSoldiers() - Math.round(requiredSoldiers));
    }

    private boolean canWinTerritory(Territory enemyTerritory, Territory aiTerritory){
        double requiredSoldiers = (5/4)*(enemyTerritory.getSoldiers()-2)+Math.exp(1+(1/enemyTerritory.getSoldiers()))+1;
        return Math.round(requiredSoldiers) <= aiTerritory.getSoldiers();
    }

    public static void main(String[] args) {
        AIEasy ai = new AIEasy("Bot 1", RiskModel.BACKGROUND);
        Player thomas = new Player("Thomas", InitializeModel.COLOURS.get("Red"));
        RiskModel rm = new RiskModel();
        rm.addPlayer(ai);
        rm.addPlayer(thomas);
        rm.play();
        //ai.AIPlaceTroops();
        //ai.AIAttack();
        //ai.AIMoveTroop();
    }
}
