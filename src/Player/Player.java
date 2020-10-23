package Player;
import java.util.*;
import Map.*;

public class Player {
    private ArrayList<Territory> listOfTerritories;
    private ArrayList<Continent> listOfContinents;
    private String name;

    public Player(String name){
        this.name = name;
        this.listOfContinents = new ArrayList<>();
        this.listOfTerritories = new ArrayList<>();
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

}
