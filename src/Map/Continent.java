package Map;

import java.util.HashMap;

/**
 * Continent models a continent in the game of risk.
 * A continent has a name, and a group of territories
 *
 * @version 1.9
 * Thomas Dunnigan
 * 101115382
 */

public class Continent {

    private String name;
    private HashMap<String, Territory> territories;

    /**
     * Constructor for Continent. Creates a Continent
     * with a name and a group of territories which is empty
     *
     * @param name
     */
    public Continent(String name){
        this.name = name;
        territories = new HashMap<>();
    }

    /**
     * adds a territory to the continent
     *
     * @param territory
     */
    public void addTerritory(Territory territory){
        territories.put(territory.getName(),territory);
    }

    /**
     * adds multiple territory to the continent
     *
     * @param args
     */
    public void addTerritories(Territory... args){
        for(Territory arg : args) {
            addTerritory(arg);
        }
    }

    /**
     * get a territory from the continent
     * if not there return null
     *
     * @param territoryName
     * @return Territory
     */
    public Territory getTerritory(String territoryName) {
        return territories.get(territoryName);
    }

    /**
     * get the name of the continent
     *
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * checks to see if territory is in the continent
     * returns null otherwise
     *
     * @param territoryName
     * @return boolean
     */
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
