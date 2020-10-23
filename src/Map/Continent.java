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

    public Territory getTerritory(String territoryName) {
        return territories.get(territoryName);
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
