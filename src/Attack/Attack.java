package Attack;
import Player.Player;
import Map.Territory;

import java.util.LinkedList;

public class Attack {
    private Player attacker;
    private Territory attackerTerritory;
    private Territory defenderTerritory;
    private int numOfAttackArmy;


    public Attack(Player attacker, Territory attackerTerritory, Territory defenderTerritory, int numOfAttackArmy){
        this.attackerTerritory = attackerTerritory;
        this.defenderTerritory = defenderTerritory;
        this.attacker = attacker;
        this.numOfAttackArmy = numOfAttackArmy;

        //Checks to see if the attack is valid
        isTerritoryOccupied();
        isTerritoryAdjacent();
        isNumOfSoldiersAllowed();

        //If valid
        doAttack();


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
    public boolean isNumOfSoldiersAllowed(){
        if(attackerTerritory.getSoldiers() - numOfAttackArmy < 1){
            System.out.println("Cannot attack with this many soldiers because your territory must be occupied at all times");
            return false;
        }else{
            return true;
        }
    }

    /**
     * Simulates the attack of the attacker vs defender.
     */
    public void getNumberOfDice(){
        int numOfAttackerDice = 0;
        int numOfDefenderDice = 0;
        //Determines the number of dice the attacker gets to roll based on number of soldiers they choose to attack with.
        for (int i = 0; i<numOfAttackArmy || i<3; i++){
            numOfAttackerDice++;
        }
        //Determines how many dice the defender can roll
        if(defenderTerritory.getSoldiers()==1) {
            numOfDefenderDice = 1;
        }else{
            numOfDefenderDice = 2;
        }
    }

    public boolean doAttack(int numOfAttackerDice, int numOfDefenderDice){
        LinkedList<Integer> red = new LinkedList<Integer>();
        LinkedList<Integer> white = new LinkedList<Integer>();
        for(int i=0; i<numOfAttackerDice; i++){
            Dice die1 = new Dice();
            red.add(die1.roll());
        }
        for(int i=0; i<numOfDefenderDice; i++){
            Dice die1 = new Dice();
            white.add(die1.roll());
        }
        while(red.size() != 0 || white.size() != 0){
            //compare the first dice
            if(red.peek() > white.peek()){
                //red won remove the first die of white
                white.removeFirst();
            }
            else{
                //white won or die remove the first die of red
                red.removeFirst();
            }
        }
        //attacking was not successful
        if(red.size() == 0){
            return false;
        }
        //attacking was successful
        else{
            return true;
        }
    }

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
