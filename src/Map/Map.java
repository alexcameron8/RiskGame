package Map;

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
     * @param continent
     */
    public void addContinent(Continent continent){
        continents.put(continent.getName(), continent);
    }

    /**
     * get a continent that's in the map
     * if not return null
     *
     * @param name
     * @return Continent
     */
    public Continent getContinent(String name){
        return continents.get(name);
    }
}
