package Map;

import java.util.ArrayList;

class MapImportJSONModel {
    private String name;
    private ArrayList<ArrayList<String>> neighbours = new ArrayList<>();
    private ArrayList<MapModelTerritory> territories = new ArrayList<>();
    private ArrayList<MapModelContinent> continents = new ArrayList<>();
    private ArrayList<String> waterCrossings = new ArrayList<>();

    public String getName() {
        return name;
    }

    public ArrayList<ArrayList<String>> getNeighbours() {
        return neighbours;
    }

    public ArrayList<MapModelTerritory> getTerritories() {
        return territories;
    }

    public ArrayList<MapModelContinent> getContinents() {
        return continents;
    }

    public ArrayList<String> getWaterCrossings(){return waterCrossings; }

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

class MapModelContinent {
    private String id;
    private String name;
    private int numberOfReinforcement;


    public String getId() {
        return id;
    }

    public int getNumberOfReinforcement() {
        return numberOfReinforcement;
    }


    public String getName(){
        return name;
    }
}
