package Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

/**
 * This class creates the map from a Json file
 * @Author Ben
 */
public class MapImport {
    private ArrayList<Territory> territories;
    private ArrayList<Continent> continents;
    private Map map;
    InputStream path;

    /**
     * Constructor for MapImport which takes a path to a json file and makes the map
     * @param path
     */
    public MapImport(InputStream path){
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.path = path;
        this.map = new Map();
        populate();
    }

    /**
     * get a created territory from an id
     *
     * @param id
     * @return Territory
     */
    private Territory getTerritory(String id){
        for(Territory territory: territories) {
            if(territory.getId().equals(id)){
                return territory;
            }
        }
        return null;
    }

    /**
     * get a continent from an id
     *
     * @param id
     * @return Continent
     */
    private Continent getContinent(String id){
        for(Continent continent: continents) {
            if(continent.getId().equals(id)){
                return continent;
            }
        }
        return null;
    }

    /**
     * create the map from a json file
     */
    private void populate(){
        Gson gson = new Gson();
        MapImportJSONModel mapImportJSONModel = null;
        JsonReader reader = new JsonReader(new InputStreamReader(path));
        mapImportJSONModel = gson.fromJson(reader, MapImportJSONModel.class);
        for(MapModelContinent mmc: mapImportJSONModel.getContinents()) {
            continents.add(new Continent(mmc.getName(), mmc.getId(), mmc.getNumberOfReinforcement(), mmc.getColor()));
        }
        for (MapModelTerritory mmt: mapImportJSONModel.getTerritories()) {
            territories.add(new Territory(mmt.getName(), mmt.getId(), getContinent(mmt.getContinent()), mmt.getPathData()));
        }
        for(ArrayList<String> neighbours: mapImportJSONModel.getNeighbours()){
            Territory terr1 = getTerritory(neighbours.get(0));
            Territory terr2 = getTerritory(neighbours.get(1));
            if(terr1 != null && terr2 != null){
                terr1.addNeighbour(terr2);
                terr2.addNeighbour(terr1);
            }
        }
        for(Territory territory: territories){
            territory.getContinent().addTerritory(territory);
        }
        for(Continent continent: continents){
            map.addContinent(continent);
        }
        for(String waterCrossingPath: mapImportJSONModel.getWaterCrossings()){
            map.addWaterCrossing(waterCrossingPath);
        }

        map.setBackgroundColor(mapImportJSONModel.getBackgroundColor());
    }

    /**
     * get all made territories
     *
     * @return ArrayList<Territory>
     */
    public ArrayList<Territory> getTerritories(){
        return this.territories;
    }

    /**
     * get all made continents
     *
     * @return ArrayList<Continent>
     */
    public ArrayList<Continent> getContinents(){
        return this.continents;
    }

    /**
     * return the map that was created from the json file
     *
     * @return Map
     */
    public Map getMap(){
        return this.map;
    }
}
