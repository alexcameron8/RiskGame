package Main;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MapImport {
    private ArrayList<MapTerritory> territories;
    String path;

    MapImport(String path){
        this.territories = new ArrayList<>();
        this.path = path;
        populate();
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
        for (MapModelTerritory mmt: mapImportJSONModel.getTerritories()) {
            territories.add(new MapTerritory(mmt.getName(), mmt.getId(), mmt.getContinent(), mmt.getPathData()));
        }
    }

    public ArrayList<MapTerritory> getTerritories(){
        return this.territories;
    }
}
