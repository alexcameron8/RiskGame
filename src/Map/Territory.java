package Map;

import Player.Player;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import java.awt.*;
import java.awt.geom.Point2D;

import java.util.ArrayList;
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

    private static PathParser pathParser = new PathParser();
    private static AWTPathProducer pathProducer = new AWTPathProducer();

    private String name;
    private String id;
    private Shape territoryShape;
    private Continent continent;

    private Player owner;
    private HashMap<String, Territory> neighbours;
    private int soldiers;

    /**
     * Constructor for Territory. Creates a territory
     * with a name, neighbours and soldiers
     *
     * @param name Name of territory.
     */
    public Territory(String name, String id,Continent continent, String path){
        this.name = name;
        this.id = id;
        this.continent = continent;
        neighbours = new  HashMap<>();
        soldiers = 0;
        owner = null;

        // Shape generation
        pathParser.setPathHandler(pathProducer);
        pathParser.parse(path);
        this.territoryShape = pathProducer.getShape();
    }

    public String getId() {
        return id;
    }

    public Shape getShape() {
        return territoryShape;
    }

    public Continent getContinent(){
        return this.continent;
    }

    public boolean contains(Point2D point){
        if(this.territoryShape.contains(point)){
            return true;
        } else{
            return false;
        }
    }

    /**
     * setOwner sets a owner for the territory
     *
     * @param owner new owner of territory.
     */
    public void setOwner(Player owner){
        this.owner=owner;
    }

    /**
     * getOwner owner for the territory
     *
     * @return player
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * addNeighbour adds a neighbour that's a territory
     *
     * @param neighbour Territory to add as neighbour.
     */
    public void addNeighbour(Territory neighbour){
        neighbours.put(neighbour.getName(), neighbour);
    }

    /**
     * addNeighbours adds multiple neighbour
     *
     * @param args Territories to add as neighbours.
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
     * @param territoryName Name to check if neighbour.
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
     * @param territoryName Neighbour territory name.
     * @return neighbour
     */
    public Territory getNeighbour(String territoryName) {
        return neighbours.get(territoryName);
    }

    /**
     * Get list of neighbours
     *
     * @return ArrayList
     */
    public ArrayList<Territory> getNeighbours(){
        ArrayList<Territory> neighbours = new ArrayList<Territory>();
        for(Territory terr: this.neighbours.values()){
            neighbours.add(terr);
        }
        return neighbours;
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
     * @param add Number of soldiers to add to territory
     */
    public void addSoldiers(int add) {
        soldiers += add;
    }

    /**
     * territory loses soldiers
     *
     * @param remove Number of soldiers to remove from territory
     */
    public void removeSoldiers(int remove) {
        soldiers -= remove;
    }

    @Override
    public String toString() {
        return name;
    }
}
