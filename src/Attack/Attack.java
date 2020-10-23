package Attack;
import Player.Player;
import Map.Territory;
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

    public void doAttack(int numOfAttackerDice, int numOfDefenderDice){
        int red[] = new int[numOfAttackerDice-1];
        int white[] = new int[numOfDefenderDice-1];
        for(int i=0; i<numOfAttackerDice; i++){
            Dice die1 = new Dice();
            red[i]= die1.roll();
        }
        for(int i=0; i<numOfDefenderDice; i++){
            Dice die1 = new Dice();
            white[i]= die1.roll();
        }
        if(red.length == white.length){

        }
    }


    public static void main(String[] args) {
        Player attacker = new Player("Alex");


    }
}
