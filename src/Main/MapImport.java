package Main;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MapImport {
    private ArrayList<MapTerritory> territories;

    MapImport(){
        this.territories = new ArrayList<>();
        populate();
    }

    private void populate(){
        Gson gson = new Gson();
        MapModel mapmodel = null;
        try {
            JsonReader reader = new JsonReader(new FileReader("src/Main/worldmap.json"));
            mapmodel = gson.fromJson(reader, MapModel.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (MapModelTerritory mmt: mapmodel.getTerritories()) {
            territories.add(new MapTerritory(mmt.getId(), mmt.getId(), mmt.getContinent(), mmt.getPathData()));
        }
    }

    public ArrayList<MapTerritory> getTerritories(){
        return this.territories;
    }
}
