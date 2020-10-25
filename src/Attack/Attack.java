package Attack;
import Player.Player;
import Map.Territory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * This class is the attack class in the game of Risk. This class has several methods which check to see if the attack is valid
 * and simulates an attack between 2 players and upon attacking will update the game with the results and consequences of the attack.
 *
 */
public class Attack {
    private Player attacker;
    private Territory attackerTerritory;
    private Territory defenderTerritory;
    private int numOfAttackArmy;

    /**
     *  Constructor for attack class. This requires knowing who the attacking player is, the defending player, and their territories in the attack.
     *  As well, the number of soldiers the attacker chooses to use in his attack against the enemy is required. This constructor checks to see if the
     *  attacker is allowed to attack the territory he has chosen and simulates the attack.
     * @param attacker The attacking player
     * @param attackerTerritory the attacking player's territory
     * @param defender The defending player
     * @param defenderTerritory the defending players territory
     * @param numOfAttackArmy number of soldiers the attacker sends to attack.
     */
    public Attack(Player attacker, Territory attackerTerritory, Player defender, Territory defenderTerritory, int numOfAttackArmy){
        this.attackerTerritory = attackerTerritory;
        this.defenderTerritory = defenderTerritory;
        this.attacker = attacker;
        this.numOfAttackArmy = numOfAttackArmy;

        //Checks to see if the attack is valid

        if(isTerritoryOccupied() && isTerritoryAdjacent() && isNumOfSoldiersAllowed() && isNumOfSoldiersAllowed() && isTerritoryEnemy()) {
            attackerTerritory.removeSoldiers(numOfAttackArmy);
            int returningArmy = attack(numOfAttackArmy);
            if(returningArmy != 0){
                System.out.println(attacker.getName() + ", conquered " + defender.getName() + "'s territory, " + defenderTerritory.getName() + ".");
                defenderTerritory.addSoldiers(returningArmy);
                defender.transferTerritory(attacker,defenderTerritory);
            }
            else{
                System.out.println(attacker.getName() + ", failed to conquer " + defender.getName() + "'s territory, " + defenderTerritory.getName() + ".");
            }
        }
    }

    /**
     * If attacker territory is neighboring the attackers desired attack Territory then return true, false otherwise
     * @return Boolean value true if territory is a neighbor, false otherwise
     */
    public boolean isTerritoryAdjacent(){
        if(attackerTerritory.isNeighbour(defenderTerritory.getName())){
            return true;
        }else{
            System.out.println("You cannot attack " + defenderTerritory.getName() + " as this territory is not neighbouring " + attackerTerritory.getName());
            return false;
        }
    }

    /**
     * Checks to see if attacker has more than 1 person in their territory
     * @return Boolean value of true if more than 1 person is in territory, false otherwise.
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
     *
     * @return Boolean true if at least one person in territory remains.
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
     * @return Boolean of true if Territory doesn't belong to you.
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
     * This method determines the number of dice required for the attackers and defenders and returns an ArrayList where the
     * first index is the attackers LinkedList of sorted dice rolls from highest to lowest and the second index is the defenders LinkedList of sorted
     * dice rolls in ascending order.
     * @param attackerArmy Number of troops sent to battle from attackers
     * @param defenderArmy Number of troops who are sacrificing their life for their territory.
     * @return an ArrayList containing the dice rolls for the attacker and defender
     */
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
     * Simulates the attack of the attacker vs defender using the input of how many soldiers the attacker chooses to send to attack.
     * @param attackerArmySize number of soldiers attacker uses.
     * @return the remaining soldiers of the attacker after the attack.
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
}
