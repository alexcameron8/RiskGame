package Map;

import Map.Exceptions.TerritoryHasNoNeighbourException;
import Map.Exceptions.TerritoryIsDisconnectedException;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
    public MapImport(InputStream path) throws TerritoryHasNoNeighbourException, TerritoryIsDisconnectedException {
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
     * Depth-First Search recursive method
     * @param source Starting vertex index
     * @param adjList Lists of adjacent nodes of each node
     * @param visited Array of visited nodes
     */
    public void DFS(int source, LinkedList<Integer> adjList [], boolean[] visited){
        visited[source] = true;
        for(int i = 0; i < adjList[source].size(); i++){
            int neighbour = adjList[source].get(i);
            if(visited[neighbour]==false){
                DFS(neighbour, adjList, visited);
            }
        }
    }

    /**
     * Much of this method is modeled on work from
     * https://algorithms.tutorialhorizon.com/check-if-given-undirected-graph-is-connected-or-not/
     * and adapted to fit the need of validating an imported map.
     *
     * @param territories The arraylist of territories in the map
     * @param neighbours  The ArrayList of all neighbours of each other.
     * @return True if all territories are reachable
     */
    private boolean isMapValid(ArrayList<MapModelTerritory> territories, ArrayList<ArrayList<String>> neighbours) throws TerritoryHasNoNeighbourException, TerritoryIsDisconnectedException {

        // Build a list of territory IDs to be used by the graph
        // Also check to ensure each territory has at least one neighbour
        ArrayList<String> territoryIds = new ArrayList<>();
        for(MapModelTerritory territory: territories){
            boolean found = false;
            for(ArrayList<String> pair: neighbours){
                if(pair.contains(territory.getId())){
                    found = true;
                    break;
                }
            }
            if(!found){
                throw new TerritoryHasNoNeighbourException(territory.getName() + " has no neighbour.");
            }
            territoryIds.add(territory.getId());
        }

        int vertices = territories.size();

        LinkedList<Integer> adjList [] = new LinkedList[vertices];
        for(int i = 0; i < vertices; i++){
            adjList[i] = new LinkedList<>();
        }

        for(ArrayList<String> pairs: neighbours){
            try {
                // Add relation to graph in forward direction
                adjList[territoryIds.indexOf(pairs.get(0))].addFirst(territoryIds.indexOf(pairs.get(1)));
                // Add relation to graph in backwards direction
                adjList[territoryIds.indexOf(pairs.get(1))].addFirst(territoryIds.indexOf(pairs.get(0)));
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println(pairs.get(0) + " " + pairs.get(1));
            }
        }

        boolean[] visited = new boolean[vertices];

        DFS(0, adjList, visited);

        int count = 0;

        for(int i = 0; i < visited.length; i++){
            if(visited[i]){
                count++;
            } else {
                throw new TerritoryIsDisconnectedException(territories.get(i).getName() + " is disconnected.");
            }
        }

        return vertices==count;
    }

    /**
     * create the map from a json file
     */
    private void populate() throws TerritoryIsDisconnectedException, TerritoryHasNoNeighbourException {
        Gson gson = new Gson();
        MapImportJSONModel mapImportJSONModel = null;
        JsonReader reader = new JsonReader(new InputStreamReader(path));
        mapImportJSONModel = gson.fromJson(reader, MapImportJSONModel.class);

        // Validate Map
        if(!isMapValid(mapImportJSONModel.getTerritories(), mapImportJSONModel.getNeighbours())){
            map = null;
            return;
        }

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
        for(MapDecorativeShape mds: mapImportJSONModel.getDecorativeShapes()){
            map.addDecorativeShapes(new DecorativeShape(mds.getPathData(), mds.getColor()));
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
