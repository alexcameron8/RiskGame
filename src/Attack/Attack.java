package Attack;
import Player.Player;
import Map.Territory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Attack {
    private Player attacker;
    private Player defender;
    private Territory attackerTerritory;
    private Territory defenderTerritory;
    private int numOfAttackArmy;


    public Attack(Player attacker, Territory attackerTerritory, Player defender, Territory defenderTerritory, int numOfAttackArmy){
        this.defender = defender;
        this.attackerTerritory = attackerTerritory;
        this.defenderTerritory = defenderTerritory;
        this.attacker = attacker;
        this.numOfAttackArmy = numOfAttackArmy;

        //Checks to see if the attack is valid

        if(isTerritoryOccupied() && isTerritoryAdjacent() && isNumOfSoldiersAllowed() && isNumOfSoldiersAllowed()) {
            if(attack(numOfAttackArmy, defenderTerritory.getSoldiers())==true){
                defender.transferTerritory(attacker,defenderTerritory);
            }
        }
    }

    /**
     * If attacker territory is neighboring the attackers desired attack Territory then return true, false otherwise
     * @return
     */
    public boolean isTerritoryAdjacent(){
        if(attackerTerritory.isNeighbour(defenderTerritory.getName())){
            return true;
        }else{
            System.out.println("You cannot attack" + defenderTerritory.getName() + " as this territory is not neighbouring" + attackerTerritory.getName());
            return false;
        }
    }

    /**
     * Checks to see if attacker has more than 1 person in their territory
     * @return
     */
    public boolean isTerritoryOccupied(){
        if(attackerTerritory.getSoldiers() <=1){
            System.out.println("You cannot attack with this territory. Your territory must be occupied at all times.");
            return false;
        }else{
            return true;
        }
    }

    /**
     * Makes sure that there is at least 1 person left in the territory.
     */
    public boolean isNumOfSoldiersAllowed() {
        if (attackerTerritory.getSoldiers() - numOfAttackArmy < 1) {
            System.out.println("Cannot attack with this many soldiers because your territory must be occupied at all times");
            return false;
        } else if(numOfAttackArmy>attackerTerritory.getSoldiers()){
            System.out.println("Cannot attack with more troops than there are in this territory.");
            return false;
         }else{
            return true;
        }
    }
    /**
     *  Makes sure you do not attack your own Territory.
     *
     */
    public boolean isTerritoryEnemy() {
        for (Territory ter : attacker.getListOfTerritories()) {
            if (ter.equals(defenderTerritory)) {
                System.out.println("You cannot attack your own territory");
                return false;
            }
        }
        return true;
    }
    /**
     * Simulates the attack of the attacker vs defender.
     */

    public boolean attack(int attackerArmySize, int defenderArmySize){
        LinkedList<Integer> attacker = new LinkedList<Integer>();
        LinkedList<Integer> defender = new LinkedList<Integer>();

        int attackerNumberOfDice = attackerArmySize;
        if(attackerArmySize > 3){attackerNumberOfDice= 3;}
        int defenderNumberOfDice = defenderArmySize;
        if(defenderArmySize > 3){defenderNumberOfDice= 2;}

        for(int i=0; i<attackerNumberOfDice; i++){
            attacker.add((new Dice()).roll());
        }
        for(int i=0; i<defenderNumberOfDice; i++){
            defender.add((new Dice()).roll());
        }
        //Sorting Lists in ascending order.
        ArrayList<Integer> attackerSort = new ArrayList<>(attacker);
        Collections.sort(attackerSort);
        ArrayList<Integer> defenderSort = new ArrayList<>(defender);
        Collections.sort(defenderSort);
        attacker = new LinkedList<Integer>(attackerSort);
        defender = new LinkedList<Integer>(defenderSort);

        while(attacker.size() != 0 || defender.size() != 0){
            //compare the first dice
            if(attacker.peek() > defender.peek()){
                //attacker won remove the first die of white
                defenderArmySize -= 1;
                defender.removeFirst();
                attacker.removeFirst();
                attacker.add((new Dice()).roll());
            }
            else{
                //white won or die remove the first die of red
                attackerArmySize -= 1;
                defender.removeFirst();
                attacker.removeFirst();
                defender.add((new Dice()).roll());
            }
        }
        if(attackerArmySize == 0){
            return false;
        }
        else if(defenderArmySize == 0){
            return true;
        }
        else{
            return attack(attackerArmySize,defenderArmySize);
        }
    }


    public static void main(String[] args) {
        Player attacker = new Player("Alex");


    }
}
