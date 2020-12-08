package Player.AI;
import Main.NotificationView.NotificationModel;
import Main.RiskView;
import Map.Continent;
import Map.Territory;
import Player.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * AIEasy is an extension of the player class that is a AI.
 * This AI is simple it will try to take the easiest continent and will only fight battles it knows it's going to win
 * It will move troops to another territory if a territory doesn't have any enemy neighbours.
 *
 * @author Thomas Dunnigan
 */
public class AIEasy extends Player{
    /**
     * Constructor for a player. Gives them
     * a name and create a list of territories
     * and continents controlled by the player
     *
     * @param name        Player name.
     * @param playerColor
     */
    public AIEasy(String name, Color playerColor, RiskView riskView) {
        super(name, playerColor, riskView);
    }

    /**
     * Simulates an AI taking their turn
     */
    public void advanceTurn(){
        AIPlaceTroops();
        AIAttack();
        AIMoveTroop();
    }


    /**
     * This method makes the AI place troops in the best spots
     *
     */
    public void AIPlaceTroops(){
        ArrayList<Territory> territoriesAlreadyAttacking = new ArrayList<>();

        Continent targetContinent = targetContinent();
        if(targetContinent == null){
            placeReinforcement(getListOfTerritories().get(0), getRemainingReinforcements());
            return;
        }
        for(Territory terr: targetContinent.getTerritories()){
            if(!hasTerritory(terr)){
                Territory attackingTerr = findAttackingTerritory(terr, territoriesAlreadyAttacking);
                if(attackingTerr != null && howManyToWin(terr, attackingTerr) < 0 && canPlaceReinforcement(attackingTerr, howManyToWin(terr, attackingTerr)*-1)){
                    this.riskView.getNotificationView().notifyUser(
                            this.name + " Placed " + howManyToWin(terr, attackingTerr)*-1 + " on " + attackingTerr.getName()+ " to take " +terr.getName(),
                             NotificationModel.NotificationType.INFO);
                    placeReinforcement(attackingTerr, howManyToWin(terr, attackingTerr)*-1);
                    territoriesAlreadyAttacking.add(attackingTerr);
                }
            }
        }
        if(getRemainingReinforcements() != 0){
            for(Territory terr : getListOfTerritories()){
                if(!terr.getContinent().equals(targetContinent) && !hasContinent(terr.getContinent())){
                                        this.riskView.getNotificationView().notifyUser(
                            this.name + " Placed " + getRemainingReinforcements() + " on " + terr,
                            NotificationModel.NotificationType.INFO);
                    placeReinforcement(terr, getRemainingReinforcements());
                    return;
                }
            }
            for(Territory terr : getListOfTerritories()){
                if(canTerritoryAttack(terr)){
                    placeReinforcement(terr, getRemainingReinforcements());
                    break;
                }
            }
        }
    }

    /**
     * The AI moves troops from one territory to another
     */
    public void AIMoveTroop(){
        for(Territory terr: getListOfTerritories()){
            if(hasUnownedNeighbour(terr) && !canTerritoryAttack(terr) && terr.getSoldiers() > 1){
                this.riskView.getNotificationView().notifyUser(
                        this.name + " Moved " + terr.getName() + " to " + findUnownedNeighbour(terr).getName(),
                        NotificationModel.NotificationType.INFO);
                moveTroops(terr, findUnownedNeighbour(terr), terr.getSoldiers() - 1);
                return;
            }
        }
    }

    /**
     * Takes a territory and return a territory that's a neighbour of the input but also has an enemy territory
     *
     * @param territory
     * @return neighbouring territory
     */
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

    /**
     * returns true when the input territory has a neighbour enemy territory false otherwise
     *
     * @param territory
     * @return boolean
     */
    private boolean canTerritoryAttack(Territory territory){
        boolean hasUnownedNeighbour = false;
        for(Territory terr: territory.getNeighbours()){
            if(!hasTerritory(terr)) {
                hasUnownedNeighbour = true;

            }
        }
        return hasUnownedNeighbour;
    }

    /**
     * returns true of a territory that is a neighbour of the input neighbours has an enemy neighbour false otherwise
     *
     * @param territory
     * @return boolean
     */
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

    /**
     * The AI will attack any territory that it can beat
     */
    public void AIAttack(){
        while(canAttack()) {
            Object[] territories = getListOfTerritories().toArray();
            for (Object terr : territories) {
                for (Territory neighbour : ((Territory) terr).getNeighbours()) {
                    if (!hasTerritory(neighbour)) {
                        if (canWinTerritory(neighbour, ((Territory) terr))) {
                            this.riskView.getNotificationView().notifyUser(
                                    this.name + "'s " + ((Territory) terr).getName() + " attacked " + neighbour.getName(),
                                    NotificationModel.NotificationType.WARNING);
                            attack(((Territory) terr), neighbour.getOwner(), neighbour, ((Territory) terr).getSoldiers() - 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * returns true if the territory can attack a neighbour and win against them false otherwise
     *
     * @return boolean
     */
    private boolean canAttack(){
        boolean canAttack = false;
        for(Object terr: getListOfTerritories()){
            for(Territory neighbour : ((Territory) terr).getNeighbours()){
                if(!hasTerritory(neighbour)) {
                    if (canWinTerritory(neighbour, ((Territory) terr))) {
                        canAttack = true;
                    }
                }
            }
        }
        return canAttack;
    }

    /**
     * Finds the easiest continent to control
     *
     * @return the easiest continent to attack
     */
    private Continent targetContinent(){
        HashMap<Continent, Integer> territoriesInContinents = new HashMap<>();
        for(Territory terr : getListOfTerritories()){
            territoriesInContinents.putIfAbsent(terr.getContinent(), 0);
            territoriesInContinents.computeIfPresent(terr.getContinent(), (k, v) -> v + 1);
        }

        Continent mostTerritories = null;

        for(Continent continent: territoriesInContinents.keySet()){
            if(territoriesInContinents.get(continent)-continent.getTerritories().size() < 0) {
                if(mostTerritories == null){
                    mostTerritories = continent;
                }
                int continentSoldiers = 0;
                for(Territory terr : continent.getTerritories()){
                    if(!hasTerritory(terr)){
                        continentSoldiers += terr.getSoldiers();
                    }
                }

                int mostTerritoriesSoldiers = 0;
                for(Territory terr : mostTerritories.getTerritories()){
                    if(!hasTerritory(terr)){
                        mostTerritoriesSoldiers += terr.getSoldiers();
                    }
                }
                if(continentSoldiers <= mostTerritoriesSoldiers) {
                    if (territoriesInContinents.get(continent) - continent.getTerritories().size() > territoriesInContinents.get(mostTerritories) - mostTerritories.getTerritories().size()) {
                        mostTerritories = continent;
                    }
                }
            }
        }
        return mostTerritories;
    }

    /**
     * find a territory to attack from
     *
     * @param defendingTerritory
     * @param NotpossibleAttackingTerritories
     * @return the territory
     */
    private Territory findAttackingTerritory(Territory defendingTerritory, ArrayList<Territory> NotpossibleAttackingTerritories){
        Territory attackingTerritory = null;
        for(Territory terr: defendingTerritory.getNeighbours()){
            if(hasTerritory(terr)){
                boolean canHave = true;
                for(Territory notTerr : NotpossibleAttackingTerritories){
                    if(notTerr.equals(terr)){
                        canHave = false;
                    }
                }
                if(canHave) {
                    if (attackingTerritory == null) {
                        attackingTerritory = terr;
                    } else if (attackingTerritory.getSoldiers() < terr.getSoldiers()) {
                        attackingTerritory = terr;
                    }
                }
            }
        }
        return attackingTerritory;
    }

    /**
     * returns the number of soldier needed for the territory to beat the enemy territory
     *
     * @param enemyTerritory
     * @param aiTerritory
     * @return number of soldiers needed
     */
    private int howManyToWin(Territory enemyTerritory, Territory aiTerritory){
        double requiredSoldiers = 1.2158*enemyTerritory.getSoldiers()+1.8842;
        return (int) (aiTerritory.getSoldiers() - Math.round(requiredSoldiers));
    }

    /**
     * returns a boolean if the AI can take the territory with the current amount of soldiers
     *
     * @param enemyTerritory
     * @param aiTerritory
     * @return
     */
    private boolean canWinTerritory(Territory enemyTerritory, Territory aiTerritory){
        double requiredSoldiers = 1.2158*enemyTerritory.getSoldiers()+1.8842;
        return Math.round(requiredSoldiers) <= aiTerritory.getSoldiers();
    }
}
