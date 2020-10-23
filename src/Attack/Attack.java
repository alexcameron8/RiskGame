package Attack;
import Player.Player;
import Map.Territory;
public class Attack {
    private Player attacker;
    private Territory attackerTerritory;
    private Territory defenderTerritory;
    private int numOfAttackArmy;


    public Attack(Player attacker, Territory attackerTerritory,Territory defenderTerritory, int numOfAttackArmy){
        this.attackerTerritory = attackerTerritory;
        this.defenderTerritory = defenderTerritory;
        this.attacker = attacker;
        this.numOfAttackArmy = numOfAttackArmy;

    }

    /**
     * If attacker territory is neighboring the attackers desired attack Territory then return true, false otherwise
     * @return
     */
    public boolean isTerritoryAdjacent(){
        if(attackerTerritory.isNeighbour(defenderTerritory)){
            return true;
        }else{
            System.out.println("You cannot attack" + defenderTerritory.getName() + " as this territory is not neighbouring" + attackerTerritory.getName());
            return false;
        }
    }

    public boolean isAttackAllowed(){
        if(attackerTerritory.getSoldiers() <=1){
            System.out.println("You cannot attack with this territory. Your territory must be occupied at all times.");
            return false;
        }else{
            return true;
        }
    }

    public static void main(String[] args) {
        Player attacker = new Player("Alex");


    }
}
