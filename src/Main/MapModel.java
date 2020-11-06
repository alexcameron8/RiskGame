package Main;

import java.util.ArrayList;

class MapModel {
    private String name;
    private ArrayList<MapModelTerritory> territories = new ArrayList<>();

    MapModel(){}

    public String getName() {
        return name;
    }

    public ArrayList<MapModelTerritory> getTerritories() {
        return territories;
    }

}

class MapModelTerritory {
    private String pathData;
    private String id;
    private String continent;

    public String getPathData() {
        return pathData;
    }

    public String getId() {
        return id;
    }

    public String getContinent() {
        return continent;
    }
}
