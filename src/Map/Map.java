package Map;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Map models a map in the game of risk. A Map has a group of continents
 *
 * @version 1.9
 * Thomas Dunnigan
 * 101115382
 */

public class Map {

    private HashMap<String, Continent> continents;

    /**
     * Constructor of map creates a map with a
     * group of continents
     */
    public Map() {
        continents = new HashMap<>();
    }

    /**
     * Add a continent to the map
     *
     * @param continent Continent to add
     */
    public void addContinent(Continent continent){
        continents.put(continent.getName(), continent);
    }

    /**
     * Gets list of territories from all continents
     *
     * @return ArrayList
     */
    public ArrayList<Territory> getTerritories(){
        ArrayList<Territory> territories = new ArrayList<Territory>();
        for(Continent cont: this.continents.values()){
            territories.addAll(cont.getTerritories());
        }
        return territories;
    }

    /**
     * get a continent that's in the map
     * if not return null
     *
     * @param name Name of continent
     * @return Continent
     */
    public Continent getContinent(String name){
        return continents.get(name);
    }

    /**
     * Get list of continents
     *
     * @return ArrayList
     */
    public ArrayList<Continent> getContinents(){
        ArrayList<Continent> continents = new ArrayList<Continent>();
        for(Continent continent: this.continents.values()){
            continents.add(continent);
        }
        return continents;
    }
}
