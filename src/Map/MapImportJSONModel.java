package Map;

import java.util.ArrayList;

/**
 * this class gets the map information from the Json file
 *
 * @author ben and Thoams
 */
class MapImportJSONModel {
    private String name;
    private ArrayList<ArrayList<String>> neighbours = new ArrayList<>();
    private ArrayList<MapModelTerritory> territories = new ArrayList<>();
    private ArrayList<MapModelContinent> continents = new ArrayList<>();
    private ArrayList<String> waterCrossings = new ArrayList<>();
    private ArrayList<Integer> backgroundColor = new ArrayList<>(3);

    /**
     * get the Name of the map from the Json file
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * get the neighbours from the Json file
     *
     * @return
     */
    public ArrayList<ArrayList<String>> getNeighbours() {
        return neighbours;
    }

    /**
     * get the territories from the Json file
     * @return
     */
    public ArrayList<MapModelTerritory> getTerritories() {
        return territories;
    }

    /**
     * get the continents from the Json file
     *
     * @return
     */
    public ArrayList<MapModelContinent> getContinents() {
        return continents;
    }

    /**
     * get the water crossing paths from the json file
     * @return
     */
    public ArrayList<String> getWaterCrossings(){return waterCrossings; }

    /**
     * get the background colour from the json file
     * @return
     */
    public ArrayList<Integer> getBackgroundColor(){ return backgroundColor; }

}

/**
 * a model class for a territory
 * @author Thomas and Ben
 */
class MapModelTerritory {
    private String pathData;
    private String id;
    private String continent;
    private String name;

    /**
     * get the path of the territory
     * @return
     */
    public String getPathData() {
        return pathData;
    }

    /**
     * get the id of the territory
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * get the continent of the territory
     * @return
     */
    public String getContinent() {
        return continent;
    }

    /**
     * get the name of the territory
     * @return
     */
    public String getName(){
        return name;
    }
}

/**
 * a model class for a continent
 * @author Thomas
 */
class MapModelContinent {
    private String id;
    private String name;
    private int numberOfReinforcement;
    private ArrayList<Integer> color = new ArrayList<>(3);

    /**
     * get the id of the continent
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * get the number of reinforcements the continent gives
     * @return int
     */
    public int getNumberOfReinforcement() {
        return numberOfReinforcement;
    }


    /**
     * get the name of the continent
     * @return String
     */
    public String getName(){
        return name;
    }

    /**
     * get the colour of the continent
     *
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getColor(){ return color; }
}
