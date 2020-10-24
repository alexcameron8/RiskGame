package Player;
import java.util.*;
import Map.*;
import Attack.*;

public class Player {
    private ArrayList<Territory> listOfTerritories;
    private ArrayList<Continent> listOfContinents;
    private String name;

    public Player(String name){
        this.name = name;
        this.listOfContinents = new ArrayList<>();
        this.listOfTerritories = new ArrayList<>();
    }

    public void attack(Territory attackerTerritory, Player defender, Territory defenderTerritory, int numOfAttackArmy){
        Attack attack = new Attack(this, attackerTerritory, defender, defenderTerritory,  numOfAttackArmy);
    }

    public void addTerritory(Territory territory){
        listOfTerritories.add(territory);
    }

    public Territory removeTerritory(String name) {
        for (Territory ter : listOfTerritories) {
            if (ter.getName() == name) {
                listOfTerritories.remove(ter);
                return ter;
            }
        }
        return null;
    }

    public void transferTerritory(Player receiver,Territory territory){
        receiver.addTerritory(territory);
        removeTerritory(territory.getName());
    }

    public String getName() {
        return name;
    }
    public ArrayList<Continent> getListOfContinents() {
        return listOfContinents;
    }

    public ArrayList<Territory> getListOfTerritories() {
        return listOfTerritories;
    }

    public void addTerritory(Territory terr){
        listOfTerritories.add(terr);
    }

    public boolean removeTerritory(Territory terr){
        return listOfTerritories.remove(terr);
    }

}
