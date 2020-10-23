package Map;

import java.util.HashMap;

public class Continent {

    private String name;
    private HashMap<String, Territory> territories;

    public Continent(String name){
        this.name = name;
        territories = new HashMap<>();
    }

    public void addTerritory(Territory territory){
        territories.put(territory.getName(),territory);
    }

    public void addTerritories(Territory... args){
        for(Territory arg : args) {
            addTerritory(arg);
        }
    }

    public Territory getTerritory(String territoryName) {
        return territories.get(territoryName);
    }

    public String getName(){
        return name;
    }

    public void viewTerritories(){
        for(String key: territories.keySet()){
            System.out.println(getTerritory(key));
        }
    }

    public boolean isTerritory(String territoryName){
        if(getTerritory(territoryName) == null)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
