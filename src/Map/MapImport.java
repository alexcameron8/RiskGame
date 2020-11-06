package Map;

import Map.MapImportJSONModel;
import Map.MapModelTerritory;
import Map.MapTerritory;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MapImport {
    private ArrayList<Territory> territories;
    private ArrayList<Continent> continents;
    private Map map;
    String path;

    public MapImport(String path){
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.path = path;
        this.map = new Map();
        populate();
    }

    private Territory getTerritory(String id){
        for(Territory territory: territories) {
            if(territory.getId().equals(id)){
                return territory;
            }
        }
        return null;
    }

    private Continent getContinent(String id){
        for(Continent continent: continents) {
            if(continent.getId().equals(id)){
                return continent;
            }
        }
        return null;
    }

    private void populate(){
        Gson gson = new Gson();
        MapImportJSONModel mapImportJSONModel = null;
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            mapImportJSONModel = gson.fromJson(reader, MapImportJSONModel.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(MapModelContinent mmc: mapImportJSONModel.getContinents()) {
            continents.add(new Continent(mmc.getName(), mmc.getId(), mmc.getNumberOfReinforcement()));
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
    }

    public ArrayList<Territory> getTerritories(){
        return this.territories;
    }

    public ArrayList<Continent> getContinents(){
        return this.continents;
    }

    public Map getMap(){
        return this.map;
    }
}
