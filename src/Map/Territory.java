package Map;

import java.util.HashMap;

/**
 * Territory models a territory in the game of risk.
 * A territory has a name, group of neighbours and
 * a number of soldier occupying it.
 *
 * @version 1.9
 * Thomas Dunnigan
 * 101115382
 */

public class Territory {

    private String name;
    private HashMap<String, Territory> neighbours;
    private int soldiers;

    /**
     * Constructor for Territory. Creates a territory
     * with a name, neighbours and soldiers
     *
     * @param name
     */
    public Territory(String name){
        this.name = name;
        neighbours = new  HashMap<>();
        soldiers = 0;
    }

    /**
     * addNeighbour adds a neighbour that's a territory
     *
     * @param neighbour
     */
    public void addNeighbour(Territory neighbour){
        neighbours.put(neighbour.getName(), neighbour);
    }

    /**
     * addNeighbours adds multiple neighbour
     *
     * @param args
     */
    public void addNeighbours(Territory... args){
        for(Territory arg: args){
            addNeighbour(arg);
        }
    }

    /**
     * return the name of the territory
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * if the territory is a neighbour return true,
     * false otherwise
     *
     * @param territoryName
     * @return boolean
     */
    public boolean isNeighbour(String territoryName){
        if(getNeighbour(territoryName) == null)
            return false;
        return true;
    }

    /**
     * get neighbour through the key
     *
     * @param territoryName
     * @return neighbour
     */
    public Territory getNeighbour(String territoryName) {
        return neighbours.get(territoryName);
    }

    /**
     * return the number of soldier on the territory
     *
     * @return int
     */
    public int getSoldiers() {
        return soldiers;
    }

    /**
     * territory gains soldiers
     *
     * @param add
     * @return int
     */
    public int addSoldiers(int add) {
        return soldiers + add;
    }

    /**
     * territory loses soldiers
     *
     * @param remove
     * @return int
     */
    public int removeSoldiers(int remove) {
        return soldiers - remove;
    }

    @Override
    public String toString() {
        return name;
    }
}
