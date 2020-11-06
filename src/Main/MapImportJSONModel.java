package Main;

import java.util.ArrayList;

class MapImportJSONModel {
    private String name;
    private ArrayList<MapModelTerritory> territories = new ArrayList<>();

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
    private String name;

    public String getPathData() {
        return pathData;
    }

    public String getId() {
        return id;
    }

    public String getContinent() {
        return continent;
    }

    public String getName(){
        return name;
    }
}
