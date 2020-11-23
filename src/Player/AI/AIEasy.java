package Player.AI;
import Attack.Attack;
import Main.IntializeFrame.InitializeModel;
import Main.NotificationView.NotificationModel;
import Main.RiskModel;
import Main.RiskView;
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
    public AIEasy(String name, Color playerColor, RiskView riskView) {
        super(name, playerColor, riskView);
    }

    public void advanceTurn(){
        AIPlaceTroops();
        AIAttack();
        AIMoveTroop();
    }


    public void AIPlaceTroops(){
        ArrayList<Territory> territoriesAlreadyAttacking = new ArrayList<>();

        Continent targetContinent = targetContinent();
        if(targetContinent == null){
            placeReinforcement(getListOfTerritories().get(0), getReinforcements());
        }
        for(Territory terr: targetContinent.getTerritories()){
            if(!hasTerritory(terr)){
                Territory attackingTerr = findAttackingTerritory(terr, territoriesAlreadyAttacking);
                if(attackingTerr != null && howManyToWin(terr, attackingTerr) < 0 && canPlaceReinforcement(attackingTerr, howManyToWin(terr, attackingTerr)*-1)){
                    this.riskView.getNotificationView().notifyUser(
                            "Placed " + howManyToWin(terr, attackingTerr)*-1 + " on " + attackingTerr.getName()+ " to take " +terr.getName(),
                             NotificationModel.NotificationType.INFO);
                    placeReinforcement(attackingTerr, howManyToWin(terr, attackingTerr)*-1);
                    territoriesAlreadyAttacking.add(attackingTerr);
                }
            }
        }
        if(getReinforcements() != 0){
            for(Territory terr : getListOfTerritories()){
                if(!terr.getContinent().equals(targetContinent) && !hasContinent(terr.getContinent())){
                    this.riskView.getNotificationView().notifyUser(
                            "Placed " + getReinforcements() + " on " + terr,
                            NotificationModel.NotificationType.INFO);
                    placeReinforcement(terr, getReinforcements());
                    break;
                }
            }
        }
    }

    public void AIMoveTroop(){
        for(Territory terr: getListOfTerritories()){
            if(hasUnownedNeighbour(terr) && !canTerritoryAttack(terr) && terr.getSoldiers() > 1){
                this.riskView.getNotificationView().notifyUser(
                        "Moved " + terr.getName() + " to " + findUnownedNeighbour(terr).getName(),
                        NotificationModel.NotificationType.INFO);
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
        while(canAttack()) {
            Object[] territories = getListOfTerritories().toArray();
            for (Object terr : territories) {
                for (Territory neighbour : ((Territory) terr).getNeighbours()) {
                    if (!hasTerritory(neighbour)) {
                        if (canWinTerritory(neighbour, ((Territory) terr))) {
                            this.riskView.getNotificationView().notifyUser(
                                    ((Territory) terr).getName() + " attacked " + neighbour.getName(),
                                    NotificationModel.NotificationType.WARNING);
                            attack(((Territory) terr), neighbour.getOwner(), neighbour, ((Territory) terr).getSoldiers() - 1);
                        }
                    }
                }
            }
        }
    }

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

    private int howManyToWin(Territory enemyTerritory, Territory aiTerritory){
        double requiredSoldiers = 1.2158*enemyTerritory.getSoldiers()+1.8842;
        return (int) (aiTerritory.getSoldiers() - Math.round(requiredSoldiers));
    }

    private boolean canWinTerritory(Territory enemyTerritory, Territory aiTerritory){
        double requiredSoldiers = 1.2158*enemyTerritory.getSoldiers()+1.8842;
        return Math.round(requiredSoldiers) <= aiTerritory.getSoldiers();
    }

    public static void main(String[] args) {
        AIEasy ai = new AIEasy("Bot 1", RiskModel.BACKGROUND, null);
        Player thomas = new Player("Thomas", InitializeModel.COLOURS.get("Red"), null);
        RiskModel rm = new RiskModel();
        rm.addPlayer(ai);
        rm.addPlayer(thomas);
        rm.play();
        //ai.AIPlaceTroops();
        //ai.AIAttack();
        //ai.AIMoveTroop();
    }
}
