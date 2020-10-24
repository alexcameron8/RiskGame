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
        WorldMap map = new WorldMap();
        for(Continent continent : map.getWorldMap().getContinents()){
            int sizeControlsContinent = 0;
            for(Territory terr : continent.getTerritories()){
                if(hasTerritory(terr)){
                    sizeControlsContinent+=1;
                }
            }
            if(sizeControlsContinent == continent.getTerritories().size())
            addContinent(continent);
        }
    }

    public Territory removeTerritory(String name) {
        for (Territory ter : listOfTerritories) {
            if (ter.getName() == name) {
                for(Continent continent : listOfContinents){
                    if(continent.isTerritory(ter.getName()));
                        removeContinent(continent);
                        break;
                }
                listOfTerritories.remove(ter);
                return ter;
            }
        }
        return null;
    }

    public Territory removeTerritory(Territory territory) {
        return removeTerritory(territory.getName());
    }

    public Continent removeContinent(String name){
        for (Continent cont : listOfContinents) {
            if (cont.getName() == name) {
                listOfContinents.remove(cont);
                return cont;
            }
        }
        return null;
    }

    public Continent removeContinent(Continent continent) {
        return removeContinent(continent.getName());
    }

    public void transferTerritory(Player receiver,Territory territory){
        receiver.addTerritory(territory);
        removeTerritory(territory.getName());
    }

    public int getReinforcement(){
        int numberOfReinforcement = (int) listOfTerritories.size()/3;
        for (Continent continent: listOfContinents) {
            numberOfReinforcement += continent.getNumberOfReinforcement();
        }
        return numberOfReinforcement;
    }

    public void placeReinforcement(Territory territory, int numberOfReinforcement){
        if(hasTerritory(territory)){
            territory.addSoldiers(numberOfReinforcement);
        }
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

    public void addContinent(Continent continent){
        if(!hasContinent(continent)){
            listOfContinents.add(continent);
        }
    }

    public boolean hasContinent(Continent continent){
        return hasContinent(continent.getName());
    }

    public boolean hasContinent(String name){
        for (Continent cont : listOfContinents) {
            if (cont.getName() == name) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTerritory(Territory territory){
        return hasTerritory(territory.getName());
    }

    public boolean hasTerritory(String name){
        for (Territory ter : listOfTerritories) {
            if (ter.getName() == name) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Player player = new Player("");

        Territory argentina = new Territory("Argentina");
        Territory brazil = new Territory("Brazil");
        Territory peru = new Territory("Peru");
        Territory venezuela = new Territory("Venezuela");
        player.addTerritory(argentina);
        player.addTerritory(brazil);
        player.addTerritory(peru);
        System.out.println(player.getListOfContinents());
        player.addTerritory(venezuela);
        System.out.println(player.getListOfContinents());
        player.removeTerritory(venezuela);
        System.out.println(player.getListOfContinents());
    }
}
