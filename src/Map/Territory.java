package Map;

import java.util.HashMap;

public class Territory {

    private String name;
    private HashMap<String, Territory> neighbours;
    private int soldiers;

    public Territory(String name){
        this.name = name;
        neighbours = new  HashMap<>();
        soldiers = 0;
    }

    public void addNeighbour(Territory neighbour){
        neighbours.put(neighbour.getName(), neighbour);
    }

    public void addNeighbours(Territory... args){
        for(Territory arg: args){
            addNeighbour(arg);
        }
    }

    public String getName() {
        return name;
    }

    public Territory getNeighbour(String territoryName) {
        return neighbours.get(territoryName);
    }

    public int getSoldiers() {
        return soldiers;
    }

    public int addSoldiers(int add) {
        return soldiers + add;
    }

    public int removeSoldiers(int remove) {
        return soldiers - remove;
    }

    @Override
    public String toString() {
        return name;
    }
}
