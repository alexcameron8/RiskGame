package Map;

import java.util.HashMap;

public class Map {

    private HashMap<String, Continent> continents;

    public Map() {
        continents = new HashMap<>();
    }

    public void addContinent(Continent continent){
        continents.put(continent.getName(), continent);
    }

    public Continent getContinent(String name){
        return continents.get(name);
    }
}
