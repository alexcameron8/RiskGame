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
            attackerTerritory.removeSoldiers(numOfAttackArmy);
            int returningArmy = attack(numOfAttackArmy);
            if(returningArmy != 0){
                System.out.println("Attack was successful");
                attackerTerritory.addSoldiers(returningArmy);
                defender.transferTerritory(attacker,defenderTerritory);
            }
            else{
                System.out.println("Attack was not successful");
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

    public ArrayList<LinkedList<Integer>> attackRoll(int attackerArmy, int defenderArmy){

        LinkedList<Integer> attacker = new LinkedList<Integer>();
        LinkedList<Integer> defender = new LinkedList<Integer>();

        int attackerNumberOfDice = attackerArmy;
        int defenderNumberOfDice = defenderArmy;
        if(attackerArmy > 3)attackerNumberOfDice = 3;
        if(defenderArmy > 2)defenderNumberOfDice = 2;

        for(int i=0; i<attackerNumberOfDice; i++){
            attacker.add((new Dice()).roll());
        }
        for(int i=0; i<defenderNumberOfDice; i++){
            defender.add((new Dice()).roll());
        }
        //Sorting Lists in ascending order.
        ArrayList<Integer> attackerSort = new ArrayList<>(attacker);
        Collections.sort(attackerSort, Collections.reverseOrder());
        ArrayList<Integer> defenderSort = new ArrayList<>(defender);
        Collections.sort(defenderSort, Collections.reverseOrder());
        attacker = new LinkedList<Integer>(attackerSort);
        defender = new LinkedList<Integer>(defenderSort);

        ArrayList<LinkedList<Integer>> output = new ArrayList<>();
        output.add(attacker);
        output.add(defender);
        return output;
    }

    /**
     * Simulates the attack of the attacker vs defender.
     */

    public int attack(int attackerArmySize){

        while(attackerArmySize > 0 && defenderTerritory.getSoldiers() > 0){
            ArrayList<LinkedList<Integer>> rolls = attackRoll(attackerArmySize,defenderTerritory.getSoldiers());
            //compare the first dice
            int lowestNumberOfDice = 2;
            if(rolls.get(0).size() > rolls.get(1).size()){
                lowestNumberOfDice = rolls.get(1).size();
            }
            else{
                lowestNumberOfDice = rolls.get(0).size();
            }
            for (int i = 0; i < lowestNumberOfDice; i++) {
                if(rolls.get(0).get(i) > rolls.get(1).get(i)){
                    //attacker won remove the first die of white
                    defenderTerritory.removeSoldiers(1);
                }
                else{
                    //white won or die remove the first die of red
                    attackerArmySize -= 1;
                }
            }
        }
        if(attackerArmySize == 0){
            return attackerArmySize;
        }
        else if(defenderTerritory.getSoldiers() == 0){
            return attackerArmySize;
        }
        else{
            return attackerArmySize;
        }
    }


    public static void main(String[] args) {
        Player attacker = new Player("Alex");
        Player defender = new Player("Thomas");
        Territory greatBritain = new Territory("Great Britain");
        Territory iceland = new Territory("Iceland");
        greatBritain.addNeighbours(iceland);
        iceland.addNeighbours(greatBritain);
        greatBritain.addSoldiers(12);
        iceland.addSoldiers(5);

        attacker.addTerritory(greatBritain);
        defender.addTerritory(iceland);


        attacker.attack(greatBritain,defender,iceland,9);
    }
}